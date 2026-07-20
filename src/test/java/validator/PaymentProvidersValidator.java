package validator;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.payment.PaymentProvider;
import model.response.payment.PaymentProvidersResponse;


public final class PaymentProvidersValidator {

	private PaymentProvidersValidator() {
	}

	public static void validates(Response apiResponse) {

		Assert.assertEquals(apiResponse.statusCode(), 200);
		SchemaValidator.validate(apiResponse, "schemas/payment-providers-response-schema.json");

		PaymentProvidersResponse response = apiResponse.as(PaymentProvidersResponse.class);
		Assert.assertNotNull(response);

		Assert.assertNotNull(response.getPaymentProviders(), "payment_providers list is null");

		Assert.assertFalse(response.getPaymentProviders().isEmpty(),
				"no payment providers available - checkout cannot proceed without at least one");

		// Business rule: count/offset/limit should be internally consistent
		// with the actual returned list, same pagination sanity check
		// applied throughout this suite (never assert count == size()
		// blindly - only that size() never exceeds what was asked for/exists).
		Assert.assertTrue(response.getPaymentProviders().size() <= response.getCount());

		Assert.assertTrue(response.getPaymentProviders().size() <= response.getLimit());

		for (PaymentProvider provider : response.getPaymentProviders()) {

			Assert.assertNotNull(provider.getId());

			Assert.assertFalse(provider.getId().trim().isEmpty());

			Assert.assertNotNull(provider.getIsEnabled());
		}

		// Business rule: at least one provider must actually be enabled -
		// a list of providers that are all disabled would technically match
		// the schema but leave the customer with no way to pay.
		boolean anyEnabled = response.getPaymentProviders().stream()
				.anyMatch(PaymentProvider::getIsEnabled);

		Assert.assertTrue(anyEnabled, "no enabled payment provider available");
	}

	/**
	 * Overload confirming a specific provider (e.g. "pp_razorpay_razorpay")
	 * is present and enabled - use when a test depends on that specific
	 * provider being selectable.
	 */
	public static void validate(Response apiResponse) {
		
		String expectedProviderId="pp_razorpay_razorpay";

		validates(apiResponse);

		PaymentProvidersResponse response = apiResponse.as(PaymentProvidersResponse.class);

		boolean found = response.getPaymentProviders().stream()
				.anyMatch(p -> expectedProviderId.equals(p.getId()) && Boolean.TRUE.equals(p.getIsEnabled()));

		Assert.assertTrue(found, "Expected enabled provider " + expectedProviderId + " not found");
	}
}