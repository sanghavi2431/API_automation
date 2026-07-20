package factory;

import java.util.Map;
import java.util.Objects;

import requestModel.EcomCoinUpdateRequest;

public final class EcomCoinUpdateRequestFactory {

    private EcomCoinUpdateRequestFactory() {
    }

    public static EcomCoinUpdateRequest create(Map<String, String> data) {

        return EcomCoinUpdateRequest.builder()
                .coins(Integer.parseInt(required(data, "coins")))
                .orderId(required(data, "orderid"))
                .type(required(data, "type"))
                .build();
    }

    private static String required(Map<String, String> data, String key) {

        return Objects.requireNonNull(data.get(key),
                key + " is required").trim();
    }
}