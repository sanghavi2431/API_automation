package factory;

import java.util.Map;
import java.util.Objects;

import requestModel.NotifyStockRequest;

public final class NotifyStockRequestFactory {

    private NotifyStockRequestFactory() {
    }

    public static NotifyStockRequest create(Map<String, String> data) {

        return NotifyStockRequest.builder()
                .variantId(Objects.requireNonNull(data.get("variant_id"),
                        "Variant ID is required").trim())
                .phone(Objects.requireNonNull(data.get("phone"),
                        "Phone number is required").trim())
                .build();
    }
}