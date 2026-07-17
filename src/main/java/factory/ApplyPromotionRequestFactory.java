package factory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import requestModel.ApplyPromotionRequest;

public final class ApplyPromotionRequestFactory {

    private ApplyPromotionRequestFactory() {
    }

    public static ApplyPromotionRequest create(Map<String, String> data) {

        String promoCodes = Objects.requireNonNull(
                data.get("promo_codes"),
                "Promo codes are required");

        List<String> list = Arrays.stream(promoCodes.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        return ApplyPromotionRequest.builder()
                .promoCodes(list)
                .build();
    }
}