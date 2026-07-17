package factory;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import requestModel.AddShippingMethodRequest;
import requestModel.ShippingOption;

public final class AddShippingMethodRequestFactory {

	private AddShippingMethodRequestFactory() {
	}

	public static AddShippingMethodRequest create(Map<String, String> data) {

		ShippingOption option = ShippingOption.builder()
				.id(Objects.requireNonNull(data.get("shipping_option_id"), "Shipping Option ID is required").trim())
				.build();

		return AddShippingMethodRequest.builder().options(Collections.singletonList(option)).build();
	}
}