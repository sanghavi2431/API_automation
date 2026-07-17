package validator;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.authentication.VerifyOtpErrorResponse;

public final class VerifyOtpErrorValidator {

	private VerifyOtpErrorValidator() {

	}

	/**
	 * Validates Verify OTP 400 Bad Request response.
	 *
	 * @param response        API Response
	 * @param expectedMessage Expected error message
	 */
	public static void validate(Response response, String expectedMessage) {

		Assert.assertEquals(response.statusCode(), 400);

		SchemaValidator.validate(response, "schemas/verify-otp-error-schema.json");

		VerifyOtpErrorResponse errorResponse = response.as(VerifyOtpErrorResponse.class);

		Assert.assertNotNull(errorResponse);

		Assert.assertFalse(errorResponse.getSuccess());

		Assert.assertNotNull(errorResponse.getData());

		Assert.assertTrue(errorResponse.getData().isEmpty(), "Data array should be empty.");

		Assert.assertEquals(errorResponse.getMessage(), expectedMessage);

	}

}