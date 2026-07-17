package utils;

import java.util.List;

import io.restassured.response.Response;
import model.response.cart.checkout.CartItemCheckout;
import model.response.cart.detailed.CartDetailedResponse;
import model.response.cart.detailed.CartItemDetailed;

/**
 * Given a cart's items (from GetCart or any cart-mutation response) and
 * what the caller is looking for - product title + variant_id - finds the
 * matching line item's id (cali_...), for use in delete-line-item or
 * update-quantity calls.
 *
 * Two methods exist (not overloads) because CartItemDetailed (the rich
 * add-to-cart/get-cart shape) and CartItemCheckout (the slim promotion/
 * checkout shape) are separate classes. They can't be true overloads of
 * the same method name here: List<CartItemDetailed> and List<CartItemCheckout>
 * both erase to the same raw List type at compile time, so Java would
 * reject them as duplicate signatures - hence the distinct method names
 * below rather than a dummy parameter to force different erasure.
 */
public final class CartItemFinder {

    private CartItemFinder() {
    }

    /** For the rich cart shape (model.response.cart.detailed.CartItemDetailed). */
    public static String findLineItemIdInDetailedCart(Response response,
            String productTitle, String variantId) {

    	CartDetailedResponse getCartResponse=response.as(CartDetailedResponse.class);
    	List<CartItemDetailed> items=getCartResponse.getCart().getItems();
    	
        CartItemDetailed match = null;

        for (CartItemDetailed item : items) {

            if (matches(item.getProductTitle(), item.getVariantId(), productTitle, variantId)) {

                if (match != null) {
                    throw new IllegalArgumentException(
                            "Ambiguous line item match for productTitle='" + productTitle
                                    + "', variantId='" + variantId + "' - matched both "
                                    + match.getId() + " and " + item.getId());
                }

                match = item;
            }
        }

        if (match == null) {
            throw new IllegalArgumentException(
                    "No cart line item found for productTitle='" + productTitle
                            + "', variantId='" + variantId + "'");
        }

        return match.getId();
    }

    /** For the slim checkout cart shape (model.response.cart.checkout.CartItemCheckout). */
    public static String findLineItemIdInCheckoutCart(List<CartItemCheckout> items,
            String productTitle, String variantId) {

        CartItemCheckout match = null;

        for (CartItemCheckout item : items) {

            if (matches(item.getProductTitle(), item.getVariantId(), productTitle, variantId)) {

                if (match != null) {
                    throw new IllegalArgumentException(
                            "Ambiguous line item match for productTitle='" + productTitle
                                    + "', variantId='" + variantId + "' - matched both "
                                    + match.getId() + " and " + item.getId());
                }

                match = item;
            }
        }

        if (match == null) {
            throw new IllegalArgumentException(
                    "No cart line item found for productTitle='" + productTitle
                            + "', variantId='" + variantId + "'");
        }

        return match.getId();
    }

    // =====================================================

    private static boolean matches(String actualTitle, String actualVariantId,
            String expectedTitle, String expectedVariantId) {

        boolean titleMatches = actualTitle != null && actualTitle.equalsIgnoreCase(expectedTitle);
        boolean variantMatches = actualVariantId != null && actualVariantId.equals(expectedVariantId);

        return titleMatches && variantMatches;
    }
}