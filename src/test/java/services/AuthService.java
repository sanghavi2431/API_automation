package services;

import static io.restassured.RestAssured.given;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import factory.SendOtpRequestFactory;
import factory.VerifyOtpRequestFactory;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import util.JsonUtils;
import utils.ApiExtentLogger;
import validator.SendOtpErrorValidator;
import validator.SendOtpValidator;
import validator.VerifyOtpErrorValidator;
import validator.VerifyOtpValidator;

public final class AuthService {

	private static final String SEND_OTP_ENDPOINT = "/api/wolooGuest/sendOTP";

	private static final String VERIFY_OTP_ENDPOINT = "/api/wolooGuest/verifyOTP";

	private static final String DEFAULT_OTP = "1234";

	private AuthService() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Generates a random 10-digit mobile number starting with 4.
	 *
	 * Example: 4987654321
	 *
	 * @return random mobile number
	 */
	public static String generateMobileNumber() {
	    long randomPart = ThreadLocalRandom.current().nextLong(100_000_000L, 1_000_000_000L);
	    return "4" + randomPart;
	}

	/**
	 * Performs complete login flow.
	 *
	 * Flow: Send OTP -> Verify OTP
	 *
	 * @param baseUrl  API Base URL
	 * @param mobileNo Existing mobile number. If null, random number will be
	 *                 generated.
	 *
	 * @return LoginResponseData
	 */
	public static Response login(String baseUrl, Map<String, String> data) {

		String mobile = (data.get("mobileNo") == null || data.get("mobileNo").isBlank()) ? generateMobileNumber()
				: data.get("mobileNo");

		data.put("mobileNo", mobile);
		String requestId = sendOtp(baseUrl, data);
		data.put("request_id", requestId);
		data.put("otp", DEFAULT_OTP);

		return verifyOtp(baseUrl, data);
	}

	/**
	 * Sends OTP to mobile number.
	 *
	 * API: POST /api/wolooGuest/sendOTP
	 *
	 * @param baseUrl API Base URL
	 * @param mobile  Mobile Number
	 *
	 * @return requestId
	 */
	public static String sendOtp(String baseUrl, Map<String, String> data) {

		String endpoint = baseUrl + SEND_OTP_ENDPOINT;

		String requestBody = JsonUtils.toJson(SendOtpRequestFactory.create(data));

		ApiExtentLogger.logRequest(endpoint, "POST", "Content-Type: application/json", requestBody);

		Response response = given().contentType(ContentType.JSON).body(requestBody).post(endpoint);

		ApiExtentLogger.logResponse(response);

		SendOtpValidator.validate(response, data);

		String requestId = response.jsonPath().getString("results.request_id");

		return requestId;
	}

	/**
	 * Verifies OTP and returns login data.
	 *
	 * API: POST /api/wolooGuest/verifyOTP
	 *
	 * @param baseUrl   API Base URL
	 * @param requestId OTP Request ID
	 *
	 * @return Login Response Data
	 */
	private static Response verifyOtp(String baseUrl, Map<String, String> data) {

		String endpoint = baseUrl + VERIFY_OTP_ENDPOINT;

		String requestBody = JsonUtils.toJson(VerifyOtpRequestFactory.create(data));

		ApiExtentLogger.logRequest(endpoint, "POST", "Content-Type: application/json", requestBody);

		Response response = given().contentType(ContentType.JSON).body(requestBody).post(endpoint);

		ApiExtentLogger.logResponse(response);

		VerifyOtpValidator.validate(response, data);

		return response;
	}

	/**
	 * Verifies OTP and returns login data.
	 *
	 * API: POST /api/wolooGuest/verifyOTP
	 *
	 * @param baseUrl   API Base URL
	 * @param requestId OTP Request ID
	 *
	 * @return Verify OTP Error Response Data
	 */
	public static Response verifyOtpError(String baseUrl, Map<String, String> data) {

		String endpoint = baseUrl + VERIFY_OTP_ENDPOINT;

		String requestBody = JsonUtils.toJson(VerifyOtpRequestFactory.create(data));

		ApiExtentLogger.logRequest(endpoint, "POST", "Content-Type: application/json", requestBody);

		Response response = given().contentType(ContentType.JSON).body(requestBody).post(endpoint);

		ApiExtentLogger.logResponse(response);

		VerifyOtpErrorValidator.validate(response, "Incorrect OTP ");

		return response;
	}

	/**
	 * Sends OTP to Invalid mobile number.
	 *
	 * API: POST /api/wolooGuest/sendOTP
	 *
	 * @param baseUrl API Base URL
	 * @param mobile  Mobile Number
	 *
	 * @return requestId
	 */
	public static Response sendOtpError(String baseUrl, Map<String, String> data) {

		String endpoint = baseUrl + SEND_OTP_ENDPOINT;

		String requestBody = JsonUtils.toJson(SendOtpRequestFactory.create(data));

		ApiExtentLogger.logRequest(endpoint, "POST", "Content-Type: application/json", requestBody);

		Response response = given().contentType(ContentType.JSON).body(requestBody).post(endpoint);

		ApiExtentLogger.logResponse(response);

		SendOtpErrorValidator.validate(response, "\"mobileNumber\" must be a number");

		return response;
	}
}