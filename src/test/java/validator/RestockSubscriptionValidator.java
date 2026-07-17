package validator;

import java.time.Instant;
import java.util.regex.Pattern;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.restock.RestockSubscription;
import model.response.restock.RestockSubscriptionListResponse;


public final class RestockSubscriptionValidator {

	// Crockford base32 ULID: 26 characters, uppercase alphanumeric excluding I, L, O, U
//	private static final Pattern ULID_PATTERN = Pattern.compile("^[0-9A-HJKMNP-TV-Z]{26}$");

	// 10-digit Indian mobile number, no country code or symbols
	private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");

	private RestockSubscriptionValidator() {
	}

	public static void validate(Response apiResponse) {

		Assert.assertEquals(apiResponse.statusCode(), 200);
		SchemaValidator.validate(apiResponse, "schemas/restock-subscription-response-schema.json");

		RestockSubscriptionListResponse response = apiResponse.as(RestockSubscriptionListResponse.class);
		Assert.assertNotNull(response);

		Assert.assertNotNull(response.getResult(), "result list is null");

		for (RestockSubscription subscription : response.getResult()) {
			validateSubscription(subscription);
		}
	}

	// =====================================================

	private static void validateSubscription(RestockSubscription subscription) {

		Assert.assertNotNull(subscription);

		Assert.assertNotNull(subscription.getId());

//		Assert.assertTrue(ULID_PATTERN.matcher(subscription.getId()).matches(),
//				"id is not a valid 26-character ULID: " + subscription.getId());

		Assert.assertNotNull(subscription.getVariantId());

		Assert.assertTrue(subscription.getVariantId().startsWith("variant_"));

		Assert.assertNotNull(subscription.getSalesChannelId());

		Assert.assertTrue(subscription.getSalesChannelId().startsWith("sc_"));

		Assert.assertNotNull(subscription.getCustomerId());

		Assert.assertTrue(subscription.getCustomerId().startsWith("cus_"));

		Assert.assertNotNull(subscription.getPhone());

		Assert.assertTrue(PHONE_PATTERN.matcher(subscription.getPhone()).matches(),
				"phone should be a 10-digit number: " + subscription.getPhone());

		// Business rule: a subscription returned by a "list active subscriptions"
		// endpoint should not be soft-deleted. If deleted_at is ever populated
		// here, the delete/unsubscribe flow has a bug leaking removed records
		// back into the active list - the JSON shape is still perfectly valid,
		// so schema validation alone would never catch this.
		Assert.assertNull(subscription.getDeletedAt(),
				"active subscription list should not include soft-deleted records");

		validateTimestampOrder(subscription);
	}

	// =====================================================

	/**
	 * Business rule: created_at must not be after updated_at. A record where
	 * creation is logged as happening after its last update indicates a
	 * timestamp-handling bug on the backend (e.g. fields swapped, or one of
	 * them set from the wrong clock/timezone source).
	 */
	private static void validateTimestampOrder(RestockSubscription subscription) {

		Assert.assertNotNull(subscription.getCreatedAt());

		Assert.assertNotNull(subscription.getUpdatedAt());

		Instant createdAt = Instant.parse(subscription.getCreatedAt());

		Instant updatedAt = Instant.parse(subscription.getUpdatedAt());

		Assert.assertFalse(createdAt.isAfter(updatedAt),
				"created_at (" + createdAt + ") is after updated_at (" + updatedAt + ")");
	}
}