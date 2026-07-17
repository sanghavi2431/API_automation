package validator;

import java.util.Map;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.payment.PaymentCollectionDetail;
import model.response.payment.PaymentCollectionResponse;
import model.response.payment.PaymentSession;


public final class PaymentSessionValidator {

	private PaymentSessionValidator() {
	}

	/**
	 * @param apiResponse    the raw response - deserialized once, internally
	 * @param expectedAmount the cart/order amount expected for this payment
	 *                       collection, as a string (matches raw_amount.value's
	 *                       type, which preserves full decimal precision)
	 */
	public static void validate(Response apiResponse, String expectedAmount) {

		Assert.assertEquals(apiResponse.statusCode(), 200);
		SchemaValidator.validate(apiResponse, "schemas/payment-collection-schema.json");

		PaymentCollectionResponse response = apiResponse.as(PaymentCollectionResponse.class);
		Assert.assertNotNull(response);

		PaymentCollectionDetail collection = response.getPaymentCollection();

		Assert.assertNotNull(collection, "payment_collection is null");

		Assert.assertNotNull(collection.getId());

		Assert.assertEquals(collection.getCurrencyCode(), "inr");

		Assert.assertNotNull(collection.getAmount());

		double delta = 0.01;

		Assert.assertEquals(collection.getAmount(), Double.parseDouble(expectedAmount), delta,
				"payment_collection.amount does not match expected amount");

		Assert.assertNotNull(collection.getPaymentSessions(), "payment_sessions list is null");

		// This is the actual branch the tautology check was trying (and
		// failing) to express - the two cases genuinely need different
		// assertions, not the same check phrased both ways.
		if (collection.getPaymentSessions().isEmpty()) {
			validateNoSessionYet(response);
		} else {
			validateSessionInitiated(collection, response, expectedAmount);
		}
	}

	// =====================================================

	/**
	 * Case 1: payment collection exists, but no session has been initiated
	 * yet (payment_sessions == []).
	 */
	private static void validateNoSessionYet(PaymentCollectionResponse response) {

		Assert.assertEquals(response.getSoftReservationStatus(), "created",
				"soft_reservation_status should be 'created' when no payment session exists yet");
	}

	// =====================================================

	/**
	 * Case 2: a payment session has been initiated (payment_sessions has
	 * at least one entry, gateway data and customer context are populated).
	 */
	private static void validateSessionInitiated(PaymentCollectionDetail collection,
			PaymentCollectionResponse response, String expectedAmount) {

		Assert.assertEquals(response.getSoftReservationStatus(), "validated",
				"soft_reservation_status should be 'validated' once a session is initiated");

		// Validate every session present, not just index 0 - if the API
		// ever returns more than one (e.g. a retried payment attempt),
		// silently checking only the first would hide problems with the rest.
		for (PaymentSession session : collection.getPaymentSessions()) {
			validateSession(session, collection, expectedAmount);
		}
	}

	// =====================================================

	private static void validateSession(PaymentSession session, PaymentCollectionDetail collection,
			String expectedAmount) {

		Assert.assertNotNull(session.getId());

		Assert.assertTrue(session.getId().startsWith("payses_"));

		Assert.assertEquals(session.getProviderId(), "pp_razorpay_razorpay");

		Assert.assertEquals(session.getStatus(), "pending");

		Assert.assertNotNull(session.getAmount());

		double delta = 0.01;

		Assert.assertEquals(session.getAmount(), collection.getAmount(), delta,
				"session.amount does not match payment_collection.amount");

		Assert.assertEquals(session.getPaymentCollectionId(), collection.getId(),
				"session.payment_collection_id does not match the parent collection's id");

		Assert.assertNotNull(session.getRawAmount());

//		Assert.assertEquals(session.getRawAmount().getValue(), expectedAmount);// need to convert from double to integer then string

		validateGatewayData(session);
	}

	// =====================================================

	/**
	 * session.getData() is typed as Object (deliberately - it's raw,
	 * provider-specific gateway data that differs per payment provider).
	 * Jackson deserializes unknown-shaped JSON objects into a
	 * LinkedHashMap<String, Object> by default, so accessing fields means
	 * casting to Map and pulling keys out by name - not calling typed
	 * getters, since none exist for a generic Object.
	 */
	@SuppressWarnings("unchecked")
	private static void validateGatewayData(PaymentSession session) {

		Assert.assertNotNull(session.getData(), "session.data is null");

		Assert.assertTrue(session.getData() instanceof Map,
				"session.data is not a Map - unexpected gateway data shape");

		Map<String, Object> data = (Map<String, Object>) session.getData();

		Assert.assertEquals(data.get("status"), "created");

		Assert.assertEquals(data.get("entity"), "order");

		Assert.assertEquals(data.get("currency"), "INR");
	}
}