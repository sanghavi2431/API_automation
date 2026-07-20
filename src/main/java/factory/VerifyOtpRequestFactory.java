package factory;

import java.util.Map;

import requestModel.VerifyOtpRequest;

public final class VerifyOtpRequestFactory {

	private VerifyOtpRequestFactory() {
	}

	// =====================================================

	public static VerifyOtpRequest create(Map<String, String> data) {

		VerifyOtpRequest request = new VerifyOtpRequest();

		// ==========================================
		// OTP
		// ==========================================

		request.setOtp(data.get("otp").trim());

		// ==========================================
		// REFERRAL CODE
		// ==========================================

		request.setReferralCode(data.getOrDefault("referral_code", ""));

		// ==========================================
		// REQUEST ID
		// ==========================================

		request.setRequestId(data.get("request_id").trim());

		return request;
	}
}