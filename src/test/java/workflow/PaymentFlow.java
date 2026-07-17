package workflow;

import java.util.Map;

import io.restassured.response.Response;
import services.PaymentService;
import utils.ExtentReportManager;
import validator.DeletePaymentValidator;
import validator.OrderSetValidator;
import validator.PaymentCollectionValidator;
import validator.PaymentProvidersValidator;
import validator.PaymentSessionValidator;

/**
 * ============================================================================
 * PaymentFlow
 * ----------------------------------------------------------------------------
 * Handles all Payment-related business operations.
 *
 * Responsibilities: 1. Get Payment Provider 2. Create Payment Collection 3.
 * Create Payment Session 4. Complete Order
 *
 * This class should NOT contain Cart or Checkout logic.
 * ============================================================================
 */
public final class PaymentFlow {

	/**
	 * Store API URL
	 */
	private static final String STORE_URL = "https://staging-store.woloo.in";

	/**
	 * Prevent Object Creation
	 */
	private PaymentFlow() {

	}

	// ==========================================================================
	// PUBLIC METHODS
	// ==========================================================================

	/**
	 * Executes the complete payment process.
	 *
	 * Flow: 1. Get Payment Provider 2. Create Payment Collection 3. Create Payment
	 * Session
	 *
	 * @param data Test Data
	 */
	public static void processPayment(Map<String, String> data) {

		ExtentReportManager.info("=============== Payment Flow Started ===============");

		getPaymentProvider(data);

		createPaymentCollection(data);

		createPaymentSession(data);

		ExtentReportManager.pass("Payment Session Created Successfully");
	}

	/**
	 * Completes the Cart and creates Order Set.
	 *
	 * @param data Test Data
	 */
	public static void completeOrder(Map<String, String> data) {

		ExtentReportManager.info("Completing Order");

		Response response = PaymentService.createSplitCompleteCart(STORE_URL, data);

		OrderSetValidator.validate(response);

		String orderId = response.jsonPath().getString("order_set.id");

		data.put("order_id", orderId);

		ExtentReportManager.pass("Order Created Successfully : " + orderId);
	}

	// ==========================================================================
	// PRIVATE METHODS
	// ==========================================================================

	/**
	 * Fetches available payment providers.
	 *
	 * @param data Test Data
	 */
	private static void getPaymentProvider(Map<String, String> data) {

		ExtentReportManager.info("Fetching Payment Provider");

		Response response = PaymentService.getPaymentProviders(STORE_URL, data);

		PaymentProvidersValidator.validate(response);

		String providerId = response.jsonPath().getString("payment_providers[0].id");

		data.put("provider_id", providerId);

		ExtentReportManager.info("Payment Provider : " + providerId);
	}

	/**
	 * Creates Payment Collection.
	 *
	 * @param data Test Data
	 */
	private static void createPaymentCollection(Map<String, String> data) {

		ExtentReportManager.info("Creating Payment Collection");

		Response response = PaymentService.createPaymentCollection(STORE_URL, data);

		PaymentCollectionValidator.validate(response, Double.parseDouble(data.get("expectedAmount")), "inr");

		String paymentId = response.jsonPath().getString("payment_collection.id");

		data.put("payment_id", paymentId);

		ExtentReportManager.info("Payment Collection Id : " + paymentId);
	}

	/**
	 * Creates Payment Session.
	 *
	 * @param data Test Data
	 */
	private static void createPaymentSession(Map<String, String> data) {

		ExtentReportManager.info("Creating Payment Session");

		Response response = PaymentService.createPaymentSession(STORE_URL, data);

		PaymentSessionValidator.validate(response, data.get("expectedAmount"));

		String paymentSessionId = response.jsonPath().getString("payment_collection.payment_sessions[0].id");

		data.put("payment_session_id", paymentSessionId);

		ExtentReportManager.info("Payment Session Id : " + paymentSessionId);
	}

	/**
	 * Delete Payment Session.
	 *
	 * @param data Test Data
	 */
	public static void deletePaymentSession(Map<String, String> data) {

		ExtentReportManager.info(" Delete Payment Session validation Started");

		Response response = PaymentService.deleteProductPayment(STORE_URL, data);

		DeletePaymentValidator.validate(response, data.get("payment_session_id"));
		ExtentReportManager.pass("Successfully validated delete Payment Api");
	}

}