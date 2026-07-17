package validator;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.orderset.OrderSet;
import model.response.orderset.OrderSetListResponse;


public final class OrderSetListValidator {

	private OrderSetListValidator() {
	}

	public static void validate(Response apiResponse) {

		Assert.assertEquals(apiResponse.statusCode(), 200);
		SchemaValidator.validate(apiResponse, "schemas/order-set-list-response-schema.json");

		OrderSetListResponse response = apiResponse.as(OrderSetListResponse.class);
		Assert.assertNotNull(response);

		Assert.assertNotNull(response.getOrderSets(), "order_sets list is null");

		Assert.assertNotNull(response.getCount());

		Assert.assertNotNull(response.getOffset());

		Assert.assertNotNull(response.getLimit());

		Assert.assertTrue(response.getOffset() >= 0);

		Assert.assertTrue(response.getLimit() > 0);

		// Same pagination sanity check used throughout this suite: never
		// assert count == size() directly, since a paginated page
		// legitimately returns fewer items than the total count once
		// count exceeds limit.
		Assert.assertTrue(response.getOrderSets().size() == response.getCount());

		Assert.assertTrue(response.getOrderSets().size() <= response.getLimit());

		// Reuses the exact same per-order-set checks (cart_id/payment_collection_id
		// cross-references, order.total == sum(item.total), the string-vs-number
		// total consistency check) that the single-item GetOrderSet validator
		// applies - a schema or business-rule change to that shape only needs
		// to happen once, in OrderSetValidator, and both validators pick it up.
		for (OrderSet orderSet : response.getOrderSets()) {
			OrderSetValidator.validateOrderSetStructure(orderSet);
		}
	}

	/**
	 * Overload for callers that expect a specific order set (e.g. one just
	 * created via SplitCompleteCart) to actually appear in the list -
	 * catches a bug where a newly created order set is missing from
	 * subsequent list queries (e.g. an indexing/caching delay).
	 */
	public static void validate(Response apiResponse, String expectedOrderSetId) {

		validate(apiResponse);

		OrderSetListResponse response = apiResponse.as(OrderSetListResponse.class);

		boolean found = response.getOrderSets().stream()
				.anyMatch(os -> expectedOrderSetId.equals(os.getId()));

		Assert.assertTrue(found, "Expected order set " + expectedOrderSetId + " not found in order_sets list");
	}
}