package validator;

import java.util.List;

import org.testng.Assert;

import io.restassured.response.Response;
import model.response.shipping.ShippingOption;
import model.response.shipping.ShippingOptionsResponse;
import model.response.shipping.ShippingRule;


public final class ShippingOptionsValidator {

	private ShippingOptionsValidator() {
	}

	public static void validate(Response apiResponse) {

		Assert.assertEquals(apiResponse.statusCode(), 200);
		SchemaValidator.validate(apiResponse, "schemas/shipping-options-response-schema.json");

		ShippingOptionsResponse response = apiResponse.as(ShippingOptionsResponse.class);
		Assert.assertNotNull(response);

		Assert.assertNotNull(response.getShippingOptions(), "shipping_options list is null");

		Assert.assertFalse(response.getShippingOptions().isEmpty(),
				"no shipping options returned for this cart");

		for (ShippingOption option : response.getShippingOptions()) {
			validateOption(option);
		}
	}

	// =====================================================

	private static void validateOption(ShippingOption option) {

		Assert.assertNotNull(option);

		Assert.assertNotNull(option.getId());

		Assert.assertTrue(option.getId().startsWith("so_"));

		Assert.assertNotNull(option.getName());

		Assert.assertFalse(option.getName().trim().isEmpty());

		Assert.assertNotNull(option.getServiceZoneId());

		Assert.assertTrue(option.getServiceZoneId().startsWith("serzo_"));

		Assert.assertNotNull(option.getAmount());

		Assert.assertTrue(option.getAmount() >= 0);

		Assert.assertNotNull(option.getVendorName());

		Assert.assertFalse(option.getVendorName().trim().isEmpty());

		Assert.assertNotNull(option.getVendorId());

		Assert.assertTrue(option.getVendorId().startsWith("vnd_"));

		Assert.assertNotNull(option.getInsufficientInventory());

		validateCalculatedPriceConsistency(option);

		validateRules(option.getRules());

		if (option.getServiceZone() != null
				&& option.getServiceZone().getFulfillmentSet() != null
				&& option.getServiceZone().getFulfillmentSet().getLocation() != null
				&& option.getServiceZone().getFulfillmentSet().getLocation().getAddress() != null) {

			Assert.assertNotNull(
					option.getServiceZone().getFulfillmentSet().getLocation().getAddress().getCity());
		}
	}

	// =====================================================

	/**
	 * Business rule: the option's top-level "amount" must agree with its
	 * own calculated_price.calculated_amount - both fields describe the
	 * same price, and a mismatch would mean the two were computed via
	 * different code paths that have drifted out of sync.
	 */
	private static void validateCalculatedPriceConsistency(ShippingOption option) {

		Assert.assertNotNull(option.getCalculatedPrice(), "calculated_price is null");

		Assert.assertNotNull(option.getCalculatedPrice().getCalculatedAmount());

		double delta = 0.01;

		Assert.assertEquals(option.getAmount(), option.getCalculatedPrice().getCalculatedAmount(), delta,
				"option.amount does not match calculated_price.calculated_amount");

		Assert.assertNotNull(option.getCalculatedPrice().getCurrencyCode());

		Assert.assertFalse(option.getCalculatedPrice().getCurrencyCode().trim().isEmpty());
	}

	// =====================================================

	private static void validateRules(List<ShippingRule> rules) {

		Assert.assertNotNull(rules, "rules list is null");

		for (ShippingRule rule : rules) {

			Assert.assertNotNull(rule.getAttribute());

			Assert.assertNotNull(rule.getOperator());

			Assert.assertNotNull(rule.getValue());
		}
	}
}