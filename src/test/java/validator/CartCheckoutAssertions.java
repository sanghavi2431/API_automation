package validator;

import org.testng.Assert;

import model.response.cart.Address;
import model.response.cart.Country;
import model.response.cart.Customer;
import model.response.cart.Region;
import model.response.cart.checkout.Adjustment;
import model.response.cart.checkout.CartCheckout;
import model.response.cart.checkout.CartItemCheckout;
import model.response.cart.checkout.Promotion;

/**
 * Shared assertions for the checkout-flow cart shape (CartCheckout),
 * returned by apply-promotion, add-vehicle-details, add-billing-address,
 * and add-shipping-method. Mirrors CartDetailedAssertions for the other
 * cart family - kept in one place so a schema change to this shared shape
 * only needs to be reflected once.
 */
public final class CartCheckoutAssertions {

	private CartCheckoutAssertions() {
	}

	public static void validateCartShell(CartCheckout cart) {

		Assert.assertNotNull(cart, "cart object is null");

		Assert.assertNotNull(cart.getId());

		Assert.assertTrue(cart.getId().startsWith("cart_"));

		Assert.assertNotNull(cart.getCurrencyCode());

		Assert.assertNotNull(cart.getRegionId());

		Assert.assertTrue(cart.getRegionId().startsWith("reg_"));

		Assert.assertNotNull(cart.getItems(), "items list is null");

		Assert.assertNotNull(cart.getPromotions(), "promotions list is null");

		validateAddress(cart.getShippingAddress());

		if (cart.getBillingAddress() != null) {
			validateAddress(cart.getBillingAddress());
		}

		validateCustomer(cart.getCustomer());

		validateRegion(cart.getRegion());
	}

	// =====================================================

	public static void validateItem(CartItemCheckout item) {

		Assert.assertNotNull(item);

		Assert.assertNotNull(item.getId());

		Assert.assertTrue(item.getId().startsWith("cali_"));

		Assert.assertNotNull(item.getVariantId());

		Assert.assertTrue(item.getVariantId().startsWith("variant_"));

		Assert.assertNotNull(item.getProductId());

		Assert.assertTrue(item.getProductId().startsWith("prod_"));

		Assert.assertNotNull(item.getQuantity());

		Assert.assertTrue(item.getQuantity() > 0);

		Assert.assertNotNull(item.getUnitPrice());

		Assert.assertTrue(item.getUnitPrice() >= 0);

		Assert.assertNotNull(item.getProduct(), "item.product is null");

		Assert.assertEquals(item.getProduct().getId(), item.getProductId());

		Assert.assertNotNull(item.getAdjustments(), "item.adjustments is null");

		for (Adjustment adjustment : item.getAdjustments()) {
			validateAdjustment(adjustment);
		}
	}

	// =====================================================

	private static void validateAdjustment(Adjustment adjustment) {

		Assert.assertNotNull(adjustment.getId());

		Assert.assertTrue(adjustment.getId().startsWith("caliadj_"));

		Assert.assertNotNull(adjustment.getCode());

		Assert.assertFalse(adjustment.getCode().trim().isEmpty());

		Assert.assertNotNull(adjustment.getPromotionId());

		Assert.assertTrue(adjustment.getPromotionId().startsWith("promo_"));

		Assert.assertNotNull(adjustment.getAmount());

		Assert.assertTrue(adjustment.getAmount() >= 0);
	}

	// =====================================================

	public static void validatePromotion(Promotion promotion) {

		Assert.assertNotNull(promotion);

		Assert.assertNotNull(promotion.getId());

		Assert.assertTrue(promotion.getId().startsWith("promo_"));

		Assert.assertNotNull(promotion.getCode());

		Assert.assertFalse(promotion.getCode().trim().isEmpty());

		Assert.assertNotNull(promotion.getApplicationMethod());

		Assert.assertNotNull(promotion.getApplicationMethod().getValue());

		Assert.assertTrue(promotion.getApplicationMethod().getValue() >= 0);

		Assert.assertNotNull(promotion.getApplicationMethod().getType());
	}

	// =====================================================

	/**
	 * Business rule: the cart's discount_total must equal the sum of every
	 * adjustment amount across all line items. This is the cross-field
	 * check schema validation can never catch, since a cart with a
	 * mismatched discount_total is still perfectly valid-shaped JSON.
	 */
	public static void validateDiscountTotalMatchesAdjustments(CartCheckout cart) {

		double expectedDiscountTotal = 0.0;

		for (CartItemCheckout item : cart.getItems()) {
			for (Adjustment adjustment : item.getAdjustments()) {
				expectedDiscountTotal += adjustment.getAmount();
			}
		}

		double delta = 0.01;

		Assert.assertEquals(cart.getDiscountTotal(), expectedDiscountTotal, delta,
				"discount_total does not match the sum of item adjustment amounts");
	}

	// =====================================================

	private static void validateAddress(Address address) {

		Assert.assertNotNull(address, "address is null");

		Assert.assertNotNull(address.getId());

		Assert.assertTrue(address.getId().startsWith("caaddr_"));
	}

	// =====================================================

	private static void validateCustomer(Customer customer) {

		Assert.assertNotNull(customer, "customer is null");

		Assert.assertNotNull(customer.getId());

		Assert.assertTrue(customer.getId().startsWith("cus_"));
	}

	// =====================================================

	private static void validateRegion(Region region) {

		Assert.assertNotNull(region, "region is null");

		Assert.assertNotNull(region.getId());

		Assert.assertNotNull(region.getCountries());

		Assert.assertFalse(region.getCountries().isEmpty());

		for (Country country : region.getCountries()) {
			Assert.assertEquals(country.getRegionId(), region.getId());
		}
	}
}