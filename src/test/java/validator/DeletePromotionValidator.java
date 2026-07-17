package validator;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.cart.checkout.CartCheckout;
import model.response.cart.checkout.CartCheckoutResponse;
import model.response.cart.checkout.CartItemCheckout;

public final class DeletePromotionValidator {

    private DeletePromotionValidator() {
    }

    public static void validate(Response apiResponse) {

        //==================================================
        // HTTP Status
        //==================================================

        Assert.assertEquals(apiResponse.statusCode(), 200);

        //==================================================
        // Schema Validation
        //==================================================

        SchemaValidator.validate(
                apiResponse,
                "schemas/cart-checkout-response-schema.json");

        //==================================================
        // Deserialize
        //==================================================

        CartCheckoutResponse response =
                apiResponse.as(CartCheckoutResponse.class);

        Assert.assertNotNull(response);

        CartCheckout cart = response.getCart();

        CartCheckoutAssertions.validateCartShell(cart);

        //==================================================
        // Items
        //==================================================

        for (CartItemCheckout item : cart.getItems()) {

            CartCheckoutAssertions.validateItem(item);

            // Promotion adjustments must be removed

            Assert.assertTrue(
                    item.getAdjustments().isEmpty(),
                    "Adjustments should be empty after removing promotion");
        }

        //==================================================
        // Promotions
        //==================================================

        Assert.assertTrue(
                cart.getPromotions().isEmpty(),
                "Promotions should be empty");

        //==================================================
        // Discount Validation
        //==================================================

        Assert.assertEquals(
                cart.getDiscountTotal().doubleValue(),
                0.0);

        Assert.assertEquals(
                cart.getDiscountSubtotal().doubleValue(),
                0.0);

        Assert.assertEquals(
                cart.getItemDiscountTotal().doubleValue(),
                0.0);

        //==================================================
        // Cart Total Validation
        //==================================================

        Assert.assertEquals(
                cart.getTotal().doubleValue(),
                cart.getSubtotal().doubleValue(),
                "Total should equal subtotal after removing promotion");

        Assert.assertEquals(
                cart.getOriginalTotal().doubleValue(),
                cart.getTotal().doubleValue(),
                "Original total should equal total");

    }

}