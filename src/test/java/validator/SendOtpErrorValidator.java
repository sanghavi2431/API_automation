package validator;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.authentication.SendOtpErrorResponse;

public final class SendOtpErrorValidator {

	private SendOtpErrorValidator() {

	}

	/**
	 * Validates Send OTP 400 Bad Request business response.
	 *
	 * @param response        API Response
	 * @param expectedDetails Expected validation error
	 */
	public static void validate(Response response, String expectedDetails) {

		Assert.assertEquals(response.statusCode(), 400);
		SchemaValidator.validate(response, "schemas/send-otp-error-schema.json");

		SendOtpErrorResponse errorResponse = response.as(SendOtpErrorResponse.class);

		Assert.assertNotNull(errorResponse);

		Assert.assertEquals(errorResponse.getCode().intValue(), 400);

		Assert.assertEquals(errorResponse.getMessage(), "Bad Request");

		Assert.assertEquals(errorResponse.getDetails(), expectedDetails);

	}

}