package validator;

import java.time.Instant;
import java.util.Map;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.cafeLogin.VerifyOtpResponse;

public final class VerifyOtpValidator {

	private VerifyOtpValidator() {
	}

	public static void validate(Response response, Map<String, String> data) {

		Assert.assertEquals(response.statusCode(), 200);

		SchemaValidator.validate(response, "schemas/verify-otp-schema.json");

		VerifyOtpResponse apiResponse = response.as(VerifyOtpResponse.class);

		Assert.assertTrue(apiResponse.getSuccess());

		Assert.assertNotNull(apiResponse.getResults());

		validateResults(apiResponse.getResults(), data);
	}

	// =====================================================

	private static void validateResults(

			VerifyOtpResponse.Results results,

			Map<String, String> data) {

		Assert.assertNotNull(results.getUser());

		Assert.assertFalse(results.getToken().isBlank());

		Assert.assertFalse(results.getMedusaToken().isBlank());

		Assert.assertFalse(results.getRegionId().isBlank());

		Assert.assertFalse(results.getCartId().isBlank());

		Assert.assertEquals(results.getMedusaSyncStatus(), "ok");

		Assert.assertEquals(results.getUserId(), results.getUser().getId());

		validateUser(results.getUser(), data);
	}

	// =====================================================

	private static void validateUser(

			VerifyOtpResponse.User user,

			Map<String, String> data) {

		Assert.assertTrue(user.getId() > 0);

		Assert.assertEquals(user.getMobile(), data.get("mobileNo"));

		Assert.assertTrue(user.getMobile().matches("\\d{10}"));

		Assert.assertFalse(user.getRefCode().isBlank());

		Assert.assertTrue(user.getRefCode().matches("[A-Z0-9]{10}"));

		Assert.assertNotNull(user.getShopPassword());

		Assert.assertFalse(user.getShopPassword().isBlank());

		Assert.assertTrue(user.getStatus().matches("[01]"));

		Assert.assertTrue(user.getIsFirstSession() == 0 || user.getIsFirstSession() == 1);

		Assert.assertTrue(user.getIsRegister() == 0 || user.getIsRegister() == 1);

		Assert.assertTrue(user.getIsFreeTrial() == 0 || user.getIsFreeTrial() == 1);

		if (user.getExpiryDate() != null) {

			Instant.parse(user.getExpiryDate());
		}

		Instant.parse(user.getCreatedAt());

		Instant.parse(user.getUpdatedAt());

		Assert.assertNotNull(user.getId().intValue());
	}
}