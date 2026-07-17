package validator;

import java.util.Map;
import java.util.UUID;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.cafeLogin.SendOtpResponse;

public final class SendOtpValidator {

	private SendOtpValidator() {
	}

	// =====================================================

	public static void validate(Response response, Map<String, String> data) {

		// ==========================================
		// STATUS CODE
		// ==========================================

		Assert.assertEquals(response.statusCode(), 200, "Invalid status code");

		// ==========================================
		// SCHEMA VALIDATION
		// ==========================================

		SchemaValidator.validate(response, "schemas/send-otp-schema.json");

		// ==========================================
		// RESPONSE MODEL
		// ==========================================

		SendOtpResponse apiResponse = response.as(SendOtpResponse.class);

		Assert.assertNotNull(apiResponse, "Response model is null");

		// ==========================================
		// SUCCESS FLAG
		// ==========================================

		Assert.assertTrue(apiResponse.getSuccess(), "Success flag is false");

		// ==========================================
		// RESULTS
		// ==========================================

		Assert.assertNotNull(apiResponse.getResults(), "Results object is null");

		validateRequestId(apiResponse.getResults().getRequestId(), data);
	}

	// =====================================================

	private static void validateRequestId(String requestId, Map<String, String> data) {

		Assert.assertNotNull(requestId, "Request id is null");

		Assert.assertFalse(requestId.isBlank(), "Request id is empty");

		// ==========================================
		// UUID FORMAT VALIDATION
		// ==========================================

		try {

			UUID.fromString(requestId);

		} catch (Exception e) {

			Assert.fail("Invalid request id UUID format : " + requestId);
		}

	}
}