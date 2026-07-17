package validator;

import org.testng.Assert;

import model.response.cart.Address;
import model.response.cart.Country;
import model.response.cart.Customer;
import model.response.cart.Region;
import model.response.cart.detailed.CartDetailed;
import model.response.cart.detailed.CartImage;
import model.response.cart.detailed.CartItemDetailed;
import model.response.cart.detailed.CartProduct;
import model.response.cart.detailed.CartVariant;

/**
 * Shared assertions for the "detailed" cart shape (CartDetailed), returned
 * by both the add-item-to-cart and delete-item-from-cart endpoints. Kept in
 * one place so a schema change to this shared shape only needs to be
 * reflected once, rather than in two near-identical validator classes.
 */
public final class CartDetailedAssertions {

	private CartDetailedAssertions() {
	}

	public static void validateCartShell(CartDetailed cart) {

		Assert.assertNotNull(cart, "cart object is null");

		Assert.assertNotNull(cart.getId());

		Assert.assertTrue(cart.getId().startsWith("cart_"));

		Assert.assertNotNull(cart.getCurrencyCode());

		Assert.assertFalse(cart.getCurrencyCode().trim().isEmpty());

		Assert.assertNotNull(cart.getRegionId());

		Assert.assertTrue(cart.getRegionId().startsWith("reg_"));

		Assert.assertNotNull(cart.getSalesChannelId());

		Assert.assertTrue(cart.getSalesChannelId().startsWith("sc_"));

		Assert.assertNotNull(cart.getItems(), "items list is null");

		validateAddress(cart.getShippingAddress());

		if (cart.getBillingAddress() != null) {
			validateAddress(cart.getBillingAddress());
		}

		validateCustomer(cart.getCustomer());

		validateRegion(cart.getRegion());
	}

	// =====================================================

	/**
	 * Business rule: the cart's aggregated totals must actually reflect the
	 * sum of its line items. This is the check schema validation can never
	 * catch, since a cart with a wildly wrong item_subtotal is still a
	 * perfectly valid-shaped JSON document.
	 */
	public static void validateTotalsMatchItems(CartDetailed cart) {

		double expectedItemSubtotal = 0.0;

		for (CartItemDetailed item : cart.getItems()) {
			expectedItemSubtotal += item.getUnitPrice() * item.getQuantity();
		}

		double delta = 0.01;

		Assert.assertEquals(cart.getItemSubtotal(), expectedItemSubtotal, delta,
				"item_subtotal does not match sum of (unit_price * quantity) across items");
	}

	// =====================================================

	public static void validateItem(CartItemDetailed item) {

		Assert.assertNotNull(item);

		Assert.assertNotNull(item.getId());

		Assert.assertTrue(item.getId().startsWith("cali_"));

		Assert.assertNotNull(item.getVariantId());

		Assert.assertTrue(item.getVariantId().startsWith("variant_"));

		Assert.assertNotNull(item.getProductId());

		Assert.assertTrue(item.getProductId().startsWith("prod_"));

		Assert.assertNotNull(item.getQuantity());

		Assert.assertTrue(item.getQuantity() > 0, "line item quantity should be greater than zero");

		Assert.assertNotNull(item.getUnitPrice());

		Assert.assertTrue(item.getUnitPrice() >= 0);

		Assert.assertNotNull(item.getIsTaxInclusive());

		validateProduct(item.getProduct());

		validateVariant(item.getVariant());

		// Cross-check: the item's own product_id/variant_id should agree with
		// the ids on its nested product/variant objects - catches a backend
		// bug where the flat fields and the expanded relation drift apart.
		Assert.assertEquals(item.getProduct().getId(), item.getProductId());

		Assert.assertEquals(item.getVariant().getId(), item.getVariantId());
	}

	// =====================================================

	private static void validateProduct(CartProduct product) {

		Assert.assertNotNull(product, "item.product is null");

		Assert.assertNotNull(product.getId());

		Assert.assertTrue(product.getId().startsWith("prod_"));

		Assert.assertNotNull(product.getTitle());

		Assert.assertFalse(product.getTitle().trim().isEmpty());

		Assert.assertNotNull(product.getHandle());

		Assert.assertFalse(product.getHandle().trim().isEmpty());

		// images/categories/tags are only populated at the shallowest nesting
		// (see CartProduct's own field comments) - only validate shape if present.
		if (product.getImages() != null) {
			for (CartImage image : product.getImages()) {
				validateImage(image);
			}
		}

		if (product.getCategories() != null) {
			product.getCategories().forEach(category ->
					Assert.assertTrue(category.getId().startsWith("pcat_")));
		}

		if (product.getCollection() != null) {
			Assert.assertTrue(product.getCollection().getId().startsWith("pcol_"));
		}
	}

	// =====================================================

	private static void validateImage(CartImage image) {

		Assert.assertNotNull(image.getId());

		Assert.assertTrue(image.getId().startsWith("img_"));

		Assert.assertNotNull(image.getUrl());

		Assert.assertTrue(image.getUrl().startsWith("https://"));

		Assert.assertNotNull(image.getRank());

		Assert.assertTrue(image.getRank() >= 0);
	}

	// =====================================================

	private static void validateVariant(CartVariant variant) {

		Assert.assertNotNull(variant, "item.variant is null");

		Assert.assertNotNull(variant.getId());

		Assert.assertTrue(variant.getId().startsWith("variant_"));

		Assert.assertNotNull(variant.getTitle());

		Assert.assertFalse(variant.getTitle().trim().isEmpty());

		Assert.assertNotNull(variant.getAllowBackorder());

		Assert.assertNotNull(variant.getManageInventory());

		Assert.assertNotNull(variant.getProductId());

		Assert.assertTrue(variant.getProductId().startsWith("prod_"));
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

		Assert.assertTrue(region.getId().startsWith("reg_"));

		Assert.assertNotNull(region.getCountries());

		Assert.assertFalse(region.getCountries().isEmpty());

		for (Country country : region.getCountries()) {
			Assert.assertEquals(country.getRegionId(), region.getId());
		}
	}
}