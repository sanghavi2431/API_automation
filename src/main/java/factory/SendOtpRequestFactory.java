package factory;

import java.util.Map;
import java.util.Objects;

import requestModel.SendOtpRequest;

public final class SendOtpRequestFactory {

    private SendOtpRequestFactory() {
    }

    public static SendOtpRequest create(Map<String, String> data) {

        return SendOtpRequest.builder()
                .mobileNumber(Objects.requireNonNull(data.get("mobileNo"), "Mobile number is required").trim())
                .referralCode(data.getOrDefault("referral_code", ""))
                .build();
    }
}