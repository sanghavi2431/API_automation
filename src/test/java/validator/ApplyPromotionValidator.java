package validator;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.cart.checkout.Adjustment;
import model.response.cart.checkout.ApplicationMethod;
import model.response.cart.checkout.CartCheckout;
import model.response.cart.checkout.CartCheckoutResponse;
import model.response.cart.checkout.CartItemCheckout;
import model.response.cart.checkout.Promotion;

public final class ApplyPromotionValidator {

	private ApplyPromotionValidator() {
	}

	public static void validate(Response apiResponse, String expectedPromotionCode) {

		Assert.assertEquals(apiResponse.statusCode(), 200);
		SchemaValidator.validate(apiResponse, "schemas/cart-checkout-response-schema.json");

		CartCheckoutResponse response = apiResponse.as(CartCheckoutResponse.class);
		Assert.assertNotNull(response);

		CartCheckout cart = response.getCart();

		CartCheckoutAssertions.validateCartShell(cart);

		for (CartItemCheckout item : cart.getItems()) {
			CartCheckoutAssertions.validateItem(item);
		}

		// Business rule: the promotion the caller applied must actually
		// appear in cart.promotions[] - a 200 response that silently
		// ignored the promotion code would otherwise look identical to
		// a successful application if we only checked the shape.
		Assert.assertFalse(cart.getPromotions().isEmpty(),
				"cart.promotions is empty - promotion may not have been applied");

//		Promotion appliedPromotion = cart.getPromotions().stream()
//				.filter(p -> expectedPromotionCode.equals(p.getCode())).findFirst()
//				.orElseThrow(() -> new AssertionError(
//						"Promotion code " + expectedPromotionCode + " not found in cart.promotions"));
//
//		CartCheckoutAssertions.validatePromotion(appliedPromotion);
//
//		CartCheckoutAssertions.validateDiscountTotalMatchesAdjustments(cart);
//
//		validatePromotionMathOnEachAdjustment(cart, appliedPromotion);
		
		// Validate one or multiple promotion codes
	    String[] expectedCodes = expectedPromotionCode.split(",");

	    for (String code : expectedCodes) {

	        String expectedCode = code.trim();

	        Promotion promotion = cart.getPromotions().stream()
	                .filter(p -> expectedCode.equalsIgnoreCase(p.getCode()))
	                .findFirst()
	                .orElseThrow(() -> new AssertionError(
	                        "Promotion code '" + expectedCode + "' not found in cart.promotions"));

	        CartCheckoutAssertions.validatePromotion(promotion);

	        validatePromotionMathOnEachAdjustment(cart, promotion);
	    }

	    CartCheckoutAssertions.validateDiscountTotalMatchesAdjustments(cart);
	}

	// =====================================================

	/**
	 * Business rule: for a percentage-type promotion, each item's adjustment amount
	 * must actually equal (value% of that item's unit_price * quantity). This is
	 * the check that catches a backend bug where the promotion is "applied"
	 * (adjustment record exists, id/code all look right) but the discount math
	 * itself is wrong - e.g. off-by-one in the percentage, or applied to the wrong
	 * base amount.
	 */
	private static void validatePromotionMathOnEachAdjustment(CartCheckout cart, Promotion promotion) {

		ApplicationMethod method = promotion.getApplicationMethod();

		if (!"percentage".equalsIgnoreCase(method.getType())) {
			// Fixed-amount promotions have a different expected-value formula;
			// only percentage math is verified here for now.
			return;
		}

		double percentage = method.getValue();
		double delta = 0.01;

		for (CartItemCheckout item : cart.getItems()) {

			for (Adjustment adjustment : item.getAdjustments()) {

				if (!promotion.getId().equals(adjustment.getPromotionId())) {
					continue;
				}

				double itemLineSubtotal = item.getUnitPrice() * item.getQuantity();
				double expectedAdjustmentAmount = itemLineSubtotal * (percentage / 100.0);

				Assert.assertEquals(adjustment.getAmount(), expectedAdjustmentAmount, delta,
						"Adjustment amount for item " + item.getId() + " does not match " + percentage
								+ "% of its line subtotal (" + itemLineSubtotal + ")");
			}
		}
	}
}