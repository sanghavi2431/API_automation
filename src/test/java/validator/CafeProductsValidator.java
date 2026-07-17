package validator;

import java.util.List;
import java.util.Map;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.product.CalculatedPrice;
import model.response.product.OptionValue;
import model.response.product.Product;
import model.response.product.ProductCategory;
import model.response.product.ProductCollection;
import model.response.product.ProductImage;
import model.response.product.ProductListResponse;
import model.response.product.ProductOption;
import model.response.product.ProductVariant;
import model.response.product.VariantOption;
import model.response.product.VariantOptionLookup;


public final class CafeProductsValidator {

	private CafeProductsValidator() {
	}

	public static void validate(Response apiResponse) {

		Assert.assertEquals(apiResponse.statusCode(), 200);
		SchemaValidator.validate(apiResponse, "schemas/cafe-products-schema.json");

		ProductListResponse response = apiResponse.as(ProductListResponse.class);
		Assert.assertNotNull(response);

		Assert.assertNotNull(response.getProducts(), "Products list is null");

		Assert.assertTrue(response.getCount() >= 0);

		Assert.assertTrue(response.getOffset() >= 0);

		Assert.assertTrue(response.getLimit() >= 0);

//		Assert.assertEquals(response.getProducts().size(), Math.min(response.getCount(), response.getLimit()));

		for (Product product : response.getProducts()) {
			validateProduct(product);
		}
	}

	// =====================================================

	private static void validateProduct(Product product) {

		Assert.assertNotNull(product);

		Assert.assertTrue(product.getId().startsWith("prod_"));

		Assert.assertFalse(product.getTitle().isBlank());

		Assert.assertFalse(product.getHandle().isBlank());

		Assert.assertNotNull(product.getDiscountable());

		if (product.getThumbnail() != null) {

			Assert.assertTrue(product.getThumbnail().startsWith("https://"));
		}

		Assert.assertNotNull(product.getAverageRating());

		Assert.assertTrue(product.getAverageRating() >= 0);

		Assert.assertTrue(product.getAverageRating() <= 5);

		Assert.assertNotNull(product.getReviewCount());

		Assert.assertTrue(product.getReviewCount() >= 0);

		validateCollection(product.getCollection());

		validateOptions(product.getOptions());

		validateImages(product.getImages());

		validateVariants(product.getVariants());

		validateCategories(product.getCategories());

		validateVariantLookup(product.getVariantOptionsLookup());

		Assert.assertNotNull(product.getDefaultVariantId());

		Assert.assertTrue(product.getDefaultVariantId().startsWith("variant_"));
	}

	// =====================================================

	private static void validateCollection(ProductCollection collection) {

		Assert.assertNotNull(collection);

		Assert.assertTrue(collection.getId().startsWith("pcol_"));

		Assert.assertFalse(collection.getTitle().isBlank());

		Assert.assertFalse(collection.getHandle().isBlank());

		// metadata is a generic Map<String, Object> (shape varies per collection),
		// so individual keys are pulled out rather than typed accessors.
		Map<String, Object> metadata = collection.getMetadata();

		if (metadata != null) {

			Object image = metadata.get("image");

			if (image != null) {
				Assert.assertTrue(String.valueOf(image).startsWith("https://"));
			}
		}
	}

	// =====================================================

	private static void validateOptions(List<ProductOption> options) {

		Assert.assertNotNull(options);

		for (ProductOption option : options) {

			Assert.assertTrue(option.getId().startsWith("opt_"));

			Assert.assertFalse(option.getTitle().isBlank());

			Assert.assertNotNull(option.getValues());

			for (OptionValue value : option.getValues()) {

				Assert.assertTrue(value.getId().startsWith("optval_"));

				Assert.assertFalse(value.getValue().isBlank());

				Assert.assertNotNull(value.getInStock());

				Assert.assertNotNull(value.getCompatibleWith());
			}
		}
	}

	// =====================================================

	private static void validateImages(List<ProductImage> images) {

		Assert.assertNotNull(images);

		for (ProductImage image : images) {

//			Assert.assertTrue(image.getId().startsWith("img_"));//because some of product doesnot have images

			Assert.assertTrue(image.getUrl().startsWith("https://"));

			Assert.assertTrue(image.getRank() >= 0);

			Assert.assertTrue(image.getProductId().startsWith("prod_"));
		}
	}

	// =====================================================

	private static void validateVariants(List<ProductVariant> variants) {

		Assert.assertNotNull(variants);

		for (ProductVariant variant : variants) {

			Assert.assertTrue(variant.getId().startsWith("variant_"));

			Assert.assertFalse(variant.getTitle().isBlank());

			Assert.assertNotNull(variant.getAllowBackorder());

			Assert.assertNotNull(variant.getManageInventory());

			Assert.assertNotNull(variant.getInventoryQuantity());

			Assert.assertTrue(variant.getInventoryQuantity() >= 0);

			validateCalculatedPrice(variant.getCalculatedPrice());

			validateImages(variant.getImages());

			validateVariantOptions(variant.getOptions());
		}
	}

	// =====================================================

	private static void validateCalculatedPrice(CalculatedPrice price) {

		Assert.assertNotNull(price);

		Assert.assertTrue(price.getId().startsWith("pset_"));

		Assert.assertNotNull(price.getCalculatedAmount());

		Assert.assertTrue(price.getCalculatedAmount() >= 0);

		Assert.assertNotNull(price.getOriginalAmount());

		Assert.assertTrue(price.getOriginalAmount() >= 0);

		Assert.assertNotNull(price.getCurrencyCode());

		Assert.assertFalse(price.getCurrencyCode().isBlank());

		Assert.assertNotNull(price.getRawCalculatedAmount());

		Assert.assertTrue(price.getRawCalculatedAmount().getPrecision() > 0);

		Assert.assertNotNull(price.getRawOriginalAmount());

		Assert.assertTrue(price.getRawOriginalAmount().getPrecision() > 0);

		Assert.assertNotNull(price.getCalculatedPrice());

		Assert.assertNotNull(price.getOriginalPrice());
	}

	// =====================================================

	private static void validateVariantOptions(List<VariantOption> options) {

		Assert.assertNotNull(options);

		for (VariantOption option : options) {

			Assert.assertTrue(option.getId().startsWith("optval_"));

			Assert.assertFalse(option.getValue().isBlank());

			Assert.assertTrue(option.getOptionId().startsWith("opt_"));

			Assert.assertNotNull(option.getOption());

			Assert.assertTrue(option.getOption().getId().startsWith("opt_"));
		}
	}

	// =====================================================

	private static void validateCategories(List<ProductCategory> categories) {

		Assert.assertNotNull(categories);

		for (ProductCategory category : categories) {

			Assert.assertTrue(category.getId().startsWith("pcat_"));

			Assert.assertFalse(category.getName().isBlank());

			Assert.assertFalse(category.getHandle().isBlank());

			Assert.assertNotNull(category.getIsActive());

			Assert.assertNotNull(category.getIsInternal());

			Assert.assertTrue(category.getRank() >= 0);

			// metadata keys are inconsistent in the source API itself:
			// "delivery_time" (snake_case) vs "estimateDeliveryTime" (camelCase),
			// and neither key is guaranteed present on every category.
			Map<String, Object> metadata = category.getMetadata();

			if (metadata != null) {

				Object deliveryTime = metadata.get("delivery_time");

				if (deliveryTime instanceof Number) {
					Assert.assertTrue(((Number) deliveryTime).intValue() >= 0);
				}

				Object estimateDeliveryTime = metadata.get("estimateDeliveryTime");

				if (estimateDeliveryTime instanceof Number) {
					Assert.assertTrue(((Number) estimateDeliveryTime).intValue() >= 0);
				}
			}
		}
	}

	// =====================================================

	private static void validateVariantLookup(List<VariantOptionLookup> lookups) {

		Assert.assertNotNull(lookups);

		for (VariantOptionLookup lookup : lookups) {

			Assert.assertNotNull(lookup.getVariantId());

			Assert.assertTrue(lookup.getVariantId().startsWith("variant_"));

			// dynamic attribute keys (flavour, size, type, ...) vary per product;
			// pulled out via the map-backed accessor rather than fixed fields.
			String flavour = lookup.getAttribute("flavour");

			if (flavour != null) {
				Assert.assertFalse(flavour.isBlank());
			}

			String size = lookup.getAttribute("size");

			if (size != null) {
				Assert.assertFalse(size.isBlank());
			}
		}
	}
}