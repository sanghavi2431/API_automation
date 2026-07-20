package utils;

import io.restassured.response.Response;
import model.response.shipping.ShippingOption;
import model.response.shipping.ShippingOptionsResponse;

/**
 * Given the list of shipping options returned by GET shipping-options, finds
 * the matching option's id (so_...) by name, for use in the add-shipping-method
 * request body.
 */
public final class ShippingOptionFinder {

	private ShippingOptionFinder() {
	}

	/**
	 * @param shippingOptions the list from
	 *                        ShippingOptionsResponse.getShippingOptions()
	 * @param name            the option name as the caller knows it, e.g.
	 *                        "Ghatkopar - Woloo Powder Room Shipping Option"
	 *                        (case-insensitive)
	 * @return the matching option's id (so_...)
	 * @throws IllegalArgumentException if zero or more than one option matches
	 */
	public static String findShippingOptionId(Response response, String name) {

		ShippingOptionsResponse options = response.as(ShippingOptionsResponse.class);

		ShippingOption match = null;

		for (ShippingOption option : options.getShippingOptions()) {

			if (option.getName() != null && option.getName().equalsIgnoreCase(name)) {

				if (match != null) {
					throw new IllegalArgumentException("Ambiguous shipping option match for name '" + name
							+ "' - matched both " + match.getId() + " and " + option.getId());
				}

				match = option;
			}
		}

		if (match == null) {
			throw new IllegalArgumentException("No shipping option found matching name '" + name + "'");
		}

		return match.getId();
	}
}