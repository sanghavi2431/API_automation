package validator;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.payment.PaymentCollectionDetail;
import model.response.payment.PaymentCollectionResponse;
import model.response.payment.PaymentSession;


public final class PaymentCollectionValidator {

	private PaymentCollectionValidator() {
	}

	public static void validate(Response apiResponse) {

		Assert.assertEquals(apiResponse.statusCode(), 200);
		SchemaValidator.validate(apiResponse, "schemas/payment-collection-schema.json");

		PaymentCollectionResponse response = apiResponse.as(PaymentCollectionResponse.class);
		Assert.assertNotNull(response);

		PaymentCollectionDetail collection = response.getPaymentCollection();

		Assert.assertNotNull(collection, "payment_collection is null");

		Assert.assertNotNull(collection.getId());

		Assert.assertTrue(collection.getId().startsWith("pay_col_"));

		Assert.assertNotNull(collection.getCurrencyCode());

		Assert.assertFalse(collection.getCurrencyCode().trim().isEmpty());

		Assert.assertNotNull(collection.getAmount());

		Assert.assertTrue(collection.getAmount() >= 0);

		Assert.assertNotNull(collection.getPaymentSessions(), "payment_sessions list is null");

		Assert.assertNotNull(response.getSoftReservationStatus());

		if (collection.getPaymentSessions().isEmpty()) {

			// Business rule: right after creating the payment collection
			// (no session initiated yet), soft_reservation_status should
			// reflect that - "created", not a later-stage value.
			Assert.assertEquals(response.getSoftReservationStatus(), "created",
					"soft_reservation_status should be 'created' when no payment session exists yet");

		} else {

			// Business rule: once a session exists, soft_reservation_status
			// should have advanced past "created".
			Assert.assertNotEquals(response.getSoftReservationStatus(), "created",
					"soft_reservation_status still 'created' despite a payment session existing");

			for (PaymentSession session : collection.getPaymentSessions()) {
				validateSession(session, collection);
			}
		}
	}

	/**
	 * Overload for callers that know the expected cart/order amount and want
	 * to confirm the payment collection was created for the correct total -
	 * catches a checkout bug where payment is initiated against a stale or
	 * wrong cart total.
	 */
	public static void validate(Response apiResponse, double expectedAmount, String expectedCurrencyCode) {

		validate(apiResponse);

		PaymentCollectionResponse response = apiResponse.as(PaymentCollectionResponse.class);
		PaymentCollectionDetail collection = response.getPaymentCollection();

		double delta = 0.01;

		Assert.assertEquals(collection.getAmount(), expectedAmount, delta,
				"payment_collection.amount does not match the expected cart/order total");

		Assert.assertEquals(collection.getCurrencyCode(), expectedCurrencyCode);
	}

	// =====================================================

	private static void validateSession(PaymentSession session, PaymentCollectionDetail collection) {

		Assert.assertNotNull(session.getId());

		Assert.assertTrue(session.getId().startsWith("payses_"));

		Assert.assertNotNull(session.getProviderId());

		Assert.assertFalse(session.getProviderId().trim().isEmpty());

		Assert.assertNotNull(session.getStatus());

		Assert.assertNotNull(session.getPaymentCollectionId());

		Assert.assertEquals(session.getPaymentCollectionId(), collection.getId(),
				"session.payment_collection_id does not match the parent collection's id");

		Assert.assertNotNull(session.getAmount());

		double delta = 0.01;

		// Business rule: a payment session's amount must match its parent
		// collection's amount - the customer should never be charged a
		// different amount than what the collection was created for.
		Assert.assertEquals(session.getAmount(), collection.getAmount(), delta,
				"session.amount does not match payment_collection.amount");

		Assert.assertNotNull(session.getRawAmount());

		Assert.assertNotNull(session.getRawAmount().getValue());

		double rawAmountAsDouble = Double.parseDouble(session.getRawAmount().getValue());

		Assert.assertEquals(rawAmountAsDouble, session.getAmount(), delta,
				"raw_amount.value does not match amount");

		if (session.getContext() != null && session.getContext().getCustomer() != null) {

			Assert.assertNotNull(session.getContext().getCustomer().getId());

			Assert.assertTrue(session.getContext().getCustomer().getId().startsWith("cus_"));
		}
	}
}