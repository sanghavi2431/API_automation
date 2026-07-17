package validator;

import java.util.List;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.payment.DeletePaymentResponse;

public final class DeletePaymentValidator {

	private DeletePaymentValidator() {
	}

	/**
	 * Validates Delete Payment business response.
	 *
	 * @param  API Response
	 * @param expectedPaymentSessionId Expected deleted payment session id
	 */
	public static void validate(Response response, String expectedPaymentSessionId) {

		Assert.assertEquals(response.statusCode(), 200);
		SchemaValidator.validate(response, "schemas/delete-payment-schema.json");

		DeletePaymentResponse deleteResponse = response.as(DeletePaymentResponse.class);

		Assert.assertNotNull(deleteResponse);

		Assert.assertTrue(deleteResponse.getDeleted(), "Payment should be deleted successfully.");

		List<String> ids = deleteResponse.getIds();

		Assert.assertNotNull(ids);

		Assert.assertFalse(ids.isEmpty(), "Deleted payment ids should not be empty.");

		Assert.assertEquals(ids.size(), 1);

		Assert.assertEquals(ids.get(0), expectedPaymentSessionId);

		Assert.assertTrue(ids.get(0).startsWith("payses_"), "Invalid Payment Session Id.");

	}

}