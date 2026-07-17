package validator;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.cart.Address;
import model.response.cart.Cart;
import model.response.cart.CartResponse;
import model.response.cart.Country;
import model.response.cart.Customer;
import model.response.cart.Region;


public final class CreateCartValidator {

	private CreateCartValidator() {
	}

	public static void validate(Response apiResponse) {

		Assert.assertEquals(apiResponse.statusCode(), 200);
		SchemaValidator.validate(apiResponse, "schemas/cart-response-schema.json");

		CartResponse response = apiResponse.as(CartResponse.class);
		Assert.assertNotNull(response);

		Cart cart = response.getCart();
		Assert.assertNotNull(cart, "cart object is null");

		Assert.assertNotNull(cart.getId());

		Assert.assertTrue(cart.getId().startsWith("cart_"));

		Assert.assertNotNull(cart.getCurrencyCode());

		Assert.assertFalse(cart.getCurrencyCode().trim().isEmpty());

		Assert.assertNotNull(cart.getRegionId());

		Assert.assertTrue(cart.getRegionId().startsWith("reg_"));

		Assert.assertNotNull(cart.getSalesChannelId());

		Assert.assertTrue(cart.getSalesChannelId().startsWith("sc_"));

		// Business rule: a freshly created cart must start empty and at zero -
		// schema validation alone would happily accept a "created" cart that
		// somehow already has items or a non-zero total, which would indicate
		// the create endpoint leaked state from a previous cart.
		Assert.assertNotNull(cart.getItems(), "items list is null");

		Assert.assertTrue(cart.getItems().isEmpty(), "newly created cart should have no items");

		Assert.assertNotNull(cart.getTotal());

		Assert.assertEquals(cart.getTotal(), 0.0, "newly created cart should have zero total");

		Assert.assertNotNull(cart.getSubtotal());

		Assert.assertEquals(cart.getSubtotal(), 0.0, "newly created cart should have zero subtotal");

		validateAddress(cart.getShippingAddress());

		// billing_address is nullable at creation time - only assert shape if present
		if (cart.getBillingAddress() != null) {
			validateAddress(cart.getBillingAddress());
		}

		validateCustomer(cart.getCustomer());

		validateRegion(cart.getRegion());
	}

	// =====================================================

	private static void validateAddress(Address address) {

		Assert.assertNotNull(address, "shipping_address is null");

		Assert.assertNotNull(address.getId());

		Assert.assertTrue(address.getId().startsWith("caaddr_"));
	}

	// =====================================================

	private static void validateCustomer(Customer customer) {

		Assert.assertNotNull(customer, "customer is null");

		Assert.assertNotNull(customer.getId());

		Assert.assertTrue(customer.getId().startsWith("cus_"));

		Assert.assertNotNull(customer.getGroups());
	}

	// =====================================================

	private static void validateRegion(Region region) {

		Assert.assertNotNull(region, "region is null");

		Assert.assertNotNull(region.getId());

		Assert.assertTrue(region.getId().startsWith("reg_"));

		Assert.assertNotNull(region.getCurrencyCode());

		Assert.assertNotNull(region.getCountries());

		Assert.assertFalse(region.getCountries().isEmpty());

		for (Country country : region.getCountries()) {

			Assert.assertNotNull(country.getIso2());

			Assert.assertFalse(country.getIso2().trim().isEmpty());

			Assert.assertEquals(country.getRegionId(), region.getId());
		}
	}
}