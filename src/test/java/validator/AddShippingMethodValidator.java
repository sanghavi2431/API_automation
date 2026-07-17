package validator;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.cart.checkout.CartCheckout;
import model.response.cart.checkout.CartCheckoutResponse;
import model.response.cart.checkout.CartItemCheckout;
import model.response.cart.checkout.ShippingMethod;


public final class AddShippingMethodValidator {

	private AddShippingMethodValidator() {
	}

	public static void validate(Response apiResponse) {

		Assert.assertEquals(apiResponse.statusCode(), 200);
		SchemaValidator.validate(apiResponse, "schemas/cart-checkout-response-schema.json");

		CartCheckoutResponse response = apiResponse.as(CartCheckoutResponse.class);
		Assert.assertNotNull(response);

		CartCheckout cart = response.getCart();

		CartCheckoutAssertions.validateCartShell(cart);

		for (CartItemCheckout item : cart.getItems()) {
			CartCheckoutAssertions.validateItem(item);
		}

		// Business rule: a 200 response from add-shipping-method must
		// actually leave a shipping method on the cart - an empty list
		// here would mean the selection silently failed.
		Assert.assertFalse(cart.getShippingMethods().isEmpty(),
				"cart.shipping_methods is empty after adding a shipping method");

		double expectedShippingTotal = 0.0;

		for (ShippingMethod method : cart.getShippingMethods()) {
			validateShippingMethod(method);
			expectedShippingTotal += method.getAmount();
		}

		// Business rule: cart.shipping_total must equal the sum of the
		// selected shipping methods' amounts - a mismatch means the cart's
		// aggregate total wasn't recalculated correctly after selection.
		double delta = 0.01;

		Assert.assertEquals(cart.getShippingTotal(), expectedShippingTotal, delta,
				"shipping_total does not match the sum of shipping_methods amounts");
	}

	/**
	 * Overload for callers that selected a specific shipping option and want
	 * to confirm it's the one that actually landed on the cart, with the
	 * expected amount - stronger than just "some method exists".
	 */
	public static void validate(Response apiResponse, String expectedShippingOptionId, double expectedAmount) {

		validate(apiResponse);

		CartCheckoutResponse response = apiResponse.as(CartCheckoutResponse.class);
		CartCheckout cart = response.getCart();

		boolean found = cart.getShippingMethods().stream()
				.anyMatch(m -> expectedShippingOptionId.equals(m.getShippingOptionId())
						&& Math.abs(m.getAmount() - expectedAmount) < 0.01);

		Assert.assertTrue(found, "Expected shipping option " + expectedShippingOptionId
				+ " with amount " + expectedAmount + " not found in cart.shipping_methods");
	}

	// =====================================================

	private static void validateShippingMethod(ShippingMethod method) {

		Assert.assertNotNull(method);

		Assert.assertNotNull(method.getId());

		Assert.assertTrue(method.getId().startsWith("casm_"));

		Assert.assertNotNull(method.getShippingOptionId());

		Assert.assertTrue(method.getShippingOptionId().startsWith("so_"));

		Assert.assertNotNull(method.getAmount());

		Assert.assertTrue(method.getAmount() >= 0);

		Assert.assertNotNull(method.getIsTaxInclusive());
	}
}