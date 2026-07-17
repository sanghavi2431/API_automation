package factory;

import java.util.Map;
import java.util.Objects;

import requestModel.CreatePaymentSessionRequest;

public final class CreatePaymentSessionRequestFactory {

    private CreatePaymentSessionRequestFactory() {
    }

    public static CreatePaymentSessionRequest create(Map<String, String> data) {

        return CreatePaymentSessionRequest.builder()
                .providerId(required(data, "provider_id"))
                .build();
    }

    private static String required(Map<String, String> data, String key) {

        return Objects.requireNonNull(data.get(key),
                key + " is required").trim();
    }
}