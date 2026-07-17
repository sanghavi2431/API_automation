package factory;

import java.util.Map;
import java.util.Objects;

import requestModel.Address;
import requestModel.UpdateCartAddressRequest;

public final class UpdateCartAddressRequestFactory {

	private UpdateCartAddressRequestFactory() {
	}

	public static UpdateCartAddressRequest create(Map<String, String> data) {

		Address address = Address.builder().address1(required(data, "address_1")).city(required(data, "city"))
				.countryCode(required(data, "country_code")).firstName(required(data, "user_name"))
				.lastName(data.getOrDefault("last_name", "")).phone(required(data, "mobileNo"))
				.postalCode(required(data, "postal_code")).province(required(data, "province")).build();

		return UpdateCartAddressRequest.builder().billingAddress(address).shippingAddress(address).build();
	}

	private static String required(Map<String, String> data, String key) {
		return Objects.requireNonNull(data.get(key), key + " is required").trim();
	}
}