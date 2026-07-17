package validator;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.cart.Address;
import model.response.cart.checkout.CartCheckout;
import model.response.cart.checkout.CartCheckoutResponse;
import model.response.cart.checkout.CartItemCheckout;

public final class AddBillingAddressValidator {

	private AddBillingAddressValidator() {
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

		Assert.assertNotNull(cart.getBillingAddressId(), "billing_address_id is null after setting a billing address");

		Assert.assertTrue(cart.getBillingAddressId().startsWith("caaddr_"));

		Assert.assertNotNull(cart.getBillingAddress(), "billing_address object is null");

		// Business rule: billing_address_id must actually point at the same
		// record as the expanded billing_address object - a mismatch here
		// would mean the two were updated independently and drifted apart.
		Assert.assertEquals(cart.getBillingAddress().getId(), cart.getBillingAddressId(),
				"billing_address.id does not match billing_address_id");

		validateAddressIsFullyPopulated(cart.getBillingAddress(), "billing_address");

		// Regression check: setting the billing address should not have
		// wiped or corrupted the previously-set shipping address.
		Assert.assertNotNull(cart.getShippingAddress(), "shipping_address is null");

		validateAddressIsFullyPopulated(cart.getShippingAddress(), "shipping_address");
	}

	// =====================================================

	/**
	 * Once an address has actually been filled in (as opposed to the all-null
	 * placeholder seen right after CreateCart), its core fields should no longer be
	 * blank.
	 */
	private static void validateAddressIsFullyPopulated(Address address, String label) {

		Assert.assertNotNull(address.getFirstName(), label + ".first_name is null");

		Assert.assertFalse(address.getFirstName().trim().isEmpty(), label + ".first_name is blank");

		Assert.assertNotNull(address.getAddress1(), label + ".address_1 is null");

		Assert.assertFalse(address.getAddress1().trim().isEmpty(), label + ".address_1 is blank");

		Assert.assertNotNull(address.getCity(), label + ".city is null");

		Assert.assertFalse(address.getCity().trim().isEmpty(), label + ".city is blank");

		Assert.assertNotNull(address.getPostalCode(), label + ".postal_code is null");

		Assert.assertFalse(address.getPostalCode().trim().isEmpty(), label + ".postal_code is blank");

		Assert.assertNotNull(address.getPhone(), label + ".phone is null");

		Assert.assertFalse(address.getPhone().trim().isEmpty(), label + ".phone is blank");
	}
}