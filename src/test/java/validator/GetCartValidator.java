package validator;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.cart.detailed.CartDetailed;
import model.response.cart.detailed.CartDetailedResponse;
import model.response.cart.detailed.CartItemDetailed;


public final class GetCartValidator {

	private GetCartValidator() {
	}

	public static void validate(Response apiResponse) {

		Assert.assertEquals(apiResponse.statusCode(), 200);
		SchemaValidator.validate(apiResponse, "schemas/cart-detailed-response-schema.json");

		CartDetailedResponse response = apiResponse.as(CartDetailedResponse.class);
		Assert.assertNotNull(response);

		CartDetailed cart = response.getCart();

		CartDetailedAssertions.validateCartShell(cart);

		for (CartItemDetailed item : cart.getItems()) {
			CartDetailedAssertions.validateItem(item);
		}

		if (!cart.getItems().isEmpty()) {
			CartDetailedAssertions.validateTotalsMatchItems(cart);
		}
	}

	/**
	 * Overload for callers that want to confirm GetCart returns the exact
	 * same state as a preceding mutation (e.g. GetCart right after
	 * add-to-cart should reflect identical totals and item count) -
	 * catches a caching/staleness bug where GetCart serves an outdated cart.
	 */
	public static void validate(Response apiResponse, String expectedCartId, int expectedItemCount) {

		validate(apiResponse);

		CartDetailedResponse response = apiResponse.as(CartDetailedResponse.class);
		CartDetailed cart = response.getCart();

		Assert.assertEquals(cart.getId(), expectedCartId,
				"GetCart returned a different cart id than expected");

		Assert.assertEquals(cart.getItems().size(), expectedItemCount,
				"GetCart item count does not match expected count");
	}
}