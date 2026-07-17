package validator;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.cart.detailed.CartDetailed;
import model.response.cart.detailed.CartDetailedResponse;
import model.response.cart.detailed.CartItemDetailed;


public final class AddItemToCartValidator {

	private AddItemToCartValidator() {
	}

	public static void validate(Response apiResponse) {

		Assert.assertEquals(apiResponse.statusCode(), 200);
		SchemaValidator.validate(apiResponse, "schemas/cart-detailed-response-schema.json");

		CartDetailedResponse response = apiResponse.as(CartDetailedResponse.class);
		Assert.assertNotNull(response);

		CartDetailed cart = response.getCart();

		CartDetailedAssertions.validateCartShell(cart);

		// Business rule: after adding an item, the cart must actually contain
		// at least one item - a 200 response with an empty items list would
		// mean the add silently failed while still reporting success.
		Assert.assertFalse(cart.getItems().isEmpty(), "cart has no items after add-to-cart");

		for (CartItemDetailed item : cart.getItems()) {
			CartDetailedAssertions.validateItem(item);
		}

		CartDetailedAssertions.validateTotalsMatchItems(cart);
	}

	/**
	 * Overload for callers that added a specific variant and want to assert
	 * it actually landed in the cart with the expected quantity - stronger
	 * than just checking "some item exists".
	 */
	public static void validate(Response apiResponse, String expectedVariantId, int expectedQuantity) {

		validate(apiResponse);

		CartDetailedResponse response = apiResponse.as(CartDetailedResponse.class);
		CartDetailed cart = response.getCart();

		boolean found = false;

		for (CartItemDetailed item : cart.getItems()) {

			if (expectedVariantId.equals(item.getVariantId())) {

				found = true;

				Assert.assertEquals(item.getQuantity().intValue(), expectedQuantity,
						"quantity for added variant does not match expected value");
			}
		}

		Assert.assertTrue(found, "expected variant " + expectedVariantId + " not found in cart items");
	}
}