package validator;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.cart.detailed.CartDetailed;
import model.response.cart.detailed.CartItemDetailed;
import model.response.cart.detailed.DeleteLineItemResponse;


public final class DeleteItemFromCartValidator {

	private DeleteItemFromCartValidator() {
	}

	public static void validate(Response apiResponse, String deletedLineItemId) {

		Assert.assertEquals(apiResponse.statusCode(), 200);
		SchemaValidator.validate(apiResponse, "schemas/delete-line-item-response-schema.json");

		DeleteLineItemResponse response = apiResponse.as(DeleteLineItemResponse.class);
		Assert.assertNotNull(response);

		Assert.assertNotNull(response.getId());

		Assert.assertEquals(response.getId(), deletedLineItemId,
				"deleted line item id in response does not match the id that was deleted");

		Assert.assertEquals(response.getObject(), "line-item");

		Assert.assertNotNull(response.getDeleted());

		Assert.assertTrue(response.getDeleted(), "deleted flag should be true");

		CartDetailed cart = response.getParent();

		CartDetailedAssertions.validateCartShell(cart);

		// Business rule: the deleted item must actually be gone from the
		// parent cart's items list - a 200 + deleted:true response that still
		// lists the "deleted" item would mean the delete silently no-op'd.
		for (CartItemDetailed item : cart.getItems()) {

			Assert.assertNotEquals(item.getId(), deletedLineItemId,
					"deleted line item is still present in parent cart's items");
		}

		for (CartItemDetailed item : cart.getItems()) {
			CartDetailedAssertions.validateItem(item);
		}

		if (!cart.getItems().isEmpty()) {
			CartDetailedAssertions.validateTotalsMatchItems(cart);
		} else {
			// Cart emptied out entirely - totals should have reset to zero.
			Assert.assertEquals(cart.getItemSubtotal(), 0.0, 0.01,
					"item_subtotal should be zero once the last item is removed");

			Assert.assertEquals(cart.getTotal(), 0.0, 0.01,
					"total should be zero once the last item is removed");
		}
	}
}