package workflow;

import java.util.Map;

import io.restassured.response.Response;
import services.AuthService;
import utils.ExtentReportManager;

public final class AuthenticationFlow {

	private static final String BASE_URL = "https://staging-api.woloo.in";

	private AuthenticationFlow() {
	}

	/**
	 * Authenticates the user and stores required tokens in the test data map.
	 *
	 * Tokens Generated: - Medusa Token - Client Token - Region Id - User Id
	 *
	 * @param data Test data
	 */
	public static void authenticate(Map<String, String> data) {

		Response response = AuthService.login(BASE_URL, data);

		data.put("medusa_token", "Bearer " + response.jsonPath().getString("results.medusa_token"));

		data.put("clientToken", response.jsonPath().getString("results.token"));

		data.put("region_id", response.jsonPath().getString("results.region_id"));

		data.put("user_id", response.jsonPath().getString("results.user_id"));
	}

	/**
	 * Authenticates the user and stores required tokens in the test data map.
	 * Invalid OTP
	 */

	public static void invalidOTPError(Map<String, String> data) {
		
		data.put("mobileNo", "123456");
		data.put("otp", "1235");

		ExtentReportManager.info("OTP Error validation Started");
		String requestId = AuthService.sendOtp(BASE_URL, data);
		data.put("request_id", requestId);

		AuthService.verifyOtpError(BASE_URL, data);

		ExtentReportManager.pass("Successfully validated OTP Error");

	}
	
	/**
	 * Authenticates the user and stores required tokens in the test data map.
	 * Invalid Mobile number
	 */

	public static void invalidMobileNoError(Map<String, String> data) {
		data.put("mobileNo", "xyz");

		ExtentReportManager.info("Invalid Mobile no Error validation Started");
		AuthService.sendOtpError(BASE_URL, data);

		ExtentReportManager.pass("Successfully validated Invalid Mobile Error");

	}
}