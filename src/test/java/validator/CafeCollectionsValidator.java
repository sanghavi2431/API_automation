package validator;

import java.util.HashSet;
import java.util.Set;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.category.SalesChannel;
import model.response.collection.CafeProductCollectionsResponse;
import model.response.collection.ProductCollectionItem;


public class CafeCollectionsValidator {

	public static void validate(Response response) {

		Assert.assertEquals(response.statusCode(), 200);
		SchemaValidator.validate(response, "schemas/cafe-collections.json");
		CafeProductCollectionsResponse res = response.as(CafeProductCollectionsResponse.class);

		Assert.assertNotNull(res);

		Assert.assertNotNull(res.getCollections());

		Assert.assertFalse(res.getCollections().isEmpty());

		Assert.assertNotNull(res.getCount());

		Assert.assertNotNull(res.getOffset());

		Assert.assertNotNull(res.getLimit());

		// count == collections.size() only holds when the whole result set fits
		// on one page (count <= limit). Once count exceeds limit, a correct
		// paginated response legitimately returns fewer items than count.
		Assert.assertTrue(res.getCollections().size() <= res.getCount());

		Assert.assertTrue(res.getCollections().size() <= res.getLimit());

		Assert.assertTrue(res.getOffset() >= 0);

		Assert.assertTrue(res.getLimit() > 0);

		Set<String> collectionIds = new HashSet<>();

		for (ProductCollectionItem collection : res.getCollections()) {

			Assert.assertNotNull(collection.getId());

			Assert.assertTrue(collection.getId().startsWith("pcol_"));

			Assert.assertNotNull(collection.getTitle());

			Assert.assertFalse(collection.getTitle().trim().isEmpty());

			Assert.assertTrue(collectionIds.add(collection.getId()));

			Assert.assertNotNull(collection.getMetadata());

			Assert.assertNotNull(collection.getMetadata().getImage());

			Assert.assertTrue(collection.getMetadata().getImage().startsWith("https://"));

			Assert.assertNotNull(collection.getSalesChannels());

			Assert.assertFalse(collection.getSalesChannels().isEmpty());

			Set<String> channelIds = new HashSet<>();

			for (SalesChannel channel : collection.getSalesChannels()) {

				Assert.assertNotNull(channel.getId());

				Assert.assertTrue(channel.getId().startsWith("sc_"));

				Assert.assertTrue(channelIds.add(channel.getId()));

				Assert.assertNotNull(channel.getName());

				Assert.assertFalse(channel.getName().trim().isEmpty());

				// description is nullable per schema - guard before calling .trim()
				if (channel.getDescription() != null) {
					Assert.assertFalse(channel.getDescription().trim().isEmpty());
				}

				Assert.assertNotNull(channel.getDisabled());

				Assert.assertNotNull(channel.getCreatedAt());

				Assert.assertNotNull(channel.getUpdatedAt());

				if (channel.getMetadata() != null) {

					if (channel.getMetadata().getMobile() != null) {
						Assert.assertEquals(String.valueOf(channel.getMetadata().getMobile()).length(), 10);
					}

					if (channel.getMetadata().getEstimateDeliveryTime() != null) {
						Assert.assertTrue(channel.getMetadata().getEstimateDeliveryTime() >= 0);
					}
				}
			}
		}
	}
}