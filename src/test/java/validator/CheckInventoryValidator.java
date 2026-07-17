package validator;


import org.testng.Assert;

import io.restassured.response.Response;
import model.response.inventory.CheckInventoryResponse;
import model.response.inventory.InventoryData;
import model.response.inventory.InventoryItem;


public final class CheckInventoryValidator {

	private CheckInventoryValidator() {
	}

	public static void validate(Response apiResponse) {

		Assert.assertEquals(apiResponse.statusCode(), 200);
		SchemaValidator.validate(apiResponse, "schemas/check-inventory-response-schema.json");

		CheckInventoryResponse response = apiResponse.as(CheckInventoryResponse.class);
		Assert.assertNotNull(response);

		Assert.assertNotNull(response.getSuccess());

		Assert.assertNotNull(response.getMessage());

		Assert.assertFalse(response.getMessage().trim().isEmpty());

		InventoryData data = response.getData();

		Assert.assertNotNull(data, "data object is null");

		Assert.assertNotNull(data.getItems(), "items list is null");

		Assert.assertFalse(data.getItems().isEmpty(), "items list is empty");

		boolean allItemsAvailable = true;

		for (InventoryItem item : data.getItems()) {

			validateItem(item);

			if (!isAvailable(item)) {
				allItemsAvailable = false;
			}
		}

		// Business rule: the top-level "success" flag must actually reflect
		// whether every item has enough stock (or allows backorder). This
		// catches a backend bug where the summary flag says "in stock" but
		// an individual item's quantity doesn't actually support it, or
		// vice versa - the kind of inconsistency schema validation alone
		// can never catch, since the shape is still perfectly valid.
		Assert.assertEquals(response.getSuccess().booleanValue(), allItemsAvailable,
				"success flag does not match actual per-item stock availability");
	}

	// =====================================================

	private static void validateItem(InventoryItem item) {

		Assert.assertNotNull(item);

		Assert.assertNotNull(item.getInventoryItemId());

		Assert.assertTrue(item.getInventoryItemId().startsWith("iitem_"));

		Assert.assertNotNull(item.getRequiredQuantity());

		Assert.assertTrue(item.getRequiredQuantity() >= 0);

		Assert.assertNotNull(item.getAllowBackorder());

		Assert.assertNotNull(item.getQuantity());

		Assert.assertTrue(item.getQuantity() >= 0);

		Assert.assertNotNull(item.getLocationIds());

		Assert.assertFalse(item.getLocationIds().isEmpty());

		for (String locationId : item.getLocationIds()) {

			Assert.assertNotNull(locationId);

			Assert.assertTrue(locationId.startsWith("sloc_"));
		}
	}

	// =====================================================

	/**
	 * An item is considered available if there's enough on-hand quantity to
	 * cover what's required, or backorder is explicitly allowed (in which
	 * case insufficient quantity is not actually a blocker).
	 */
	private static boolean isAvailable(InventoryItem item) {

		if (Boolean.TRUE.equals(item.getAllowBackorder())) {
			return true;
		}

		return item.getQuantity() >= item.getRequiredQuantity();
	}
}