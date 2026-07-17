package validator;

import java.util.List;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.category.CafeProductCategoriesResponse;
import model.response.category.Category;
import model.response.category.CategoryChild;
import model.response.category.CategoryMetadata;
import model.response.category.ParentCategory;
import model.response.category.SalesChannel;
import model.response.category.SalesChannelMetadata;

public final class CafeCategoryValidator {

	private CafeCategoryValidator() {
	}

	public static void validate(Response apiResponse) {

		Assert.assertEquals(apiResponse.statusCode(), 200);

		SchemaValidator.validate(apiResponse, "schemas/cafe-product-categories-schema.json");
		
		CafeProductCategoriesResponse response = apiResponse.as(CafeProductCategoriesResponse.class);
		Assert.assertNotNull(response);

		Assert.assertNotNull(response.getCategories());

		Assert.assertTrue(response.getCount() >= 0);

		Assert.assertTrue(response.getOffset() >= 0);

		Assert.assertTrue(response.getLimit() > 0);

		Assert.assertTrue(response.getCategories().size() <= response.getLimit());

		for (Category category : response.getCategories()) {

			validateCategory(category);
		}
	}

	private static void validateCategory(Category category) {

		Assert.assertNotNull(category);

		Assert.assertTrue(category.getId().startsWith("pcat_"));

		Assert.assertFalse(category.getName().isBlank());

		Assert.assertFalse(category.getHandle().isBlank());

		Assert.assertTrue(category.getRank() >= 0);

		validateMetadata(category.getMetadata());

		validateParentCategory(category.getParentCategory());

		validateChildren(category.getCategoryChildren());

		validateSalesChannels(category.getSalesChannels());
	}

	private static void validateMetadata(CategoryMetadata metadata) {

		if (metadata == null) {
			return;
		}

		if (metadata.getImage() != null) {

			Assert.assertTrue(metadata.getImage().startsWith("http"));
		}

		if (metadata.getDeliveryTime() != null) {

			Assert.assertTrue(metadata.getDeliveryTime() >= 0);
		}

		if (metadata.getEstimateDeliveryTime() != null) {

			Assert.assertTrue(metadata.getEstimateDeliveryTime() >= 0);
		}

		if (metadata.getBackgroundColor() != null) {

			Assert.assertTrue(metadata.getBackgroundColor().matches("^[A-Za-z0-9]{6}$"));
		}
	}

	private static void validateParentCategory(ParentCategory parent) {

		if (parent == null) {
			return;
		}

		Assert.assertTrue(parent.getId().startsWith("pcat_"));

		Assert.assertFalse(parent.getName().isBlank());

		Assert.assertFalse(parent.getHandle().isBlank());

		Assert.assertTrue(parent.getRank() >= 0);

		validateMetadata(parent.getMetadata());
	}

	private static void validateChildren(List<CategoryChild> children) {

		if (children == null) {
			return;
		}

		for (CategoryChild child : children) {

			Assert.assertTrue(child.getId().startsWith("pcat_"));

			Assert.assertFalse(child.getName().isBlank());

			Assert.assertFalse(child.getHandle().isBlank());

			Assert.assertTrue(child.getRank() >= 0);

			Assert.assertNotNull(child.getActive());

			Assert.assertNotNull(child.getInternal());

			if (child.getMpath() != null) {

				Assert.assertTrue(child.getMpath().contains("."));
			}

			validateMetadata(child.getMetadata());
		}
	}

	private static void validateSalesChannels(List<SalesChannel> channels) {

		Assert.assertNotNull(channels);

		for (SalesChannel channel : channels) {

			Assert.assertTrue(channel.getId().startsWith("sc_"));

			Assert.assertFalse(channel.getName().isBlank());

			Assert.assertFalse(channel.getDescription().isBlank());

			Assert.assertNotNull(channel.getDisabled());

			validateSalesChannelMetadata(channel.getMetadata());
		}
	}

	private static void validateSalesChannelMetadata(SalesChannelMetadata metadata) {

		if (metadata == null) {
			return;
		}

		if (metadata.getMobile() != null) {

			String mobile = String.valueOf(metadata.getMobile());

			Assert.assertTrue(mobile.matches("\\d{10}"));
		}

		if (metadata.getEstimateDeliveryTime() != null) {

			Assert.assertTrue(metadata.getEstimateDeliveryTime() >= 0);
		}
	}
}
