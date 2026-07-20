package validator;

import java.util.regex.Pattern;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.cart.checkout.CartCheckout;
import model.response.cart.checkout.CartCheckoutResponse;
import model.response.cart.checkout.CartItemCheckout;
import model.response.cart.checkout.VehicleDetails;


public final class AddVehicleDetailsValidator {

	private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");

	private AddVehicleDetailsValidator() {
	}

	/**
	 * @param apiResponse    the API response to validate
	 * @param expectedPhone  the phone number submitted in the request, to
	 *                       confirm it was actually persisted rather than
	 *                       silently dropped or overwritten server-side
	 * @param expectedVehicleNumber same idea, for the vehicle number field
	 */
	public static void validate(Response apiResponse, String expectedPhone, String expectedVehicleNumber) {

		Assert.assertEquals(apiResponse.statusCode(), 200);
		SchemaValidator.validate(apiResponse, "schemas/cart-checkout-response-schema.json");

		CartCheckoutResponse response = apiResponse.as(CartCheckoutResponse.class);
		Assert.assertNotNull(response);

		CartCheckout cart = response.getCart();

		CartCheckoutAssertions.validateCartShell(cart);

		for (CartItemCheckout item : cart.getItems()) {
			CartCheckoutAssertions.validateItem(item);
		}

		Assert.assertNotNull(cart.getMetadata(), "cart.metadata is null - vehicle details were not saved");

		VehicleDetails vehicleDetails = cart.getMetadata().getVehicleDetails();

		Assert.assertNotNull(vehicleDetails, "metadata.VehicleDetails is null");

		Assert.assertNotNull(vehicleDetails.getId());

		Assert.assertFalse(vehicleDetails.getId().trim().isEmpty());

		Assert.assertNotNull(vehicleDetails.getUserName());

		Assert.assertFalse(vehicleDetails.getUserName().trim().isEmpty());

		Assert.assertNotNull(vehicleDetails.getVehicleType());

		Assert.assertFalse(vehicleDetails.getVehicleType().trim().isEmpty());

		Assert.assertNotNull(vehicleDetails.getPhone());

		Assert.assertTrue(PHONE_PATTERN.matcher(vehicleDetails.getPhone()).matches(),
				"vehicle details phone should be a 10-digit number: " + vehicleDetails.getPhone());

		// Business rule: what was submitted must be what comes back - a 200
		// response with a different phone/vehicle number than what was sent
		// would mean the update silently applied to the wrong field or was
		// partially ignored.
		Assert.assertEquals(vehicleDetails.getPhone(), expectedPhone,
				"persisted phone does not match what was submitted");

		Assert.assertEquals(vehicleDetails.getVehicleNumber(), expectedVehicleNumber,
				"persisted vehicle number does not match what was submitted");
	}
}