package factory;

import java.util.Map;
import java.util.Objects;

import requestModel.CreatePaymentCollectionRequest;

public final class CreatePaymentCollectionRequestFactory {

    private CreatePaymentCollectionRequestFactory() {
    }

    public static CreatePaymentCollectionRequest create(Map<String, String> data) {

        return CreatePaymentCollectionRequest.builder()
                .cartId(required(data, "cart_id"))
                .build();
    }

    private static String required(Map<String, String> data, String key) {

        return Objects.requireNonNull(data.get(key),
                key + " is required").trim();
    }
}