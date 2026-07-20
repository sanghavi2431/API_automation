package model.response.cafeLogin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyOtpResponse {

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("results")
    private Results results;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Results {

        @JsonProperty("user")
        private User user;

        @JsonProperty("token")
        private String token;

        @JsonProperty("user_id")
        private Integer userId;

        @JsonProperty("medusa_token")
        private String medusaToken;

        @JsonProperty("region_id")
        private String regionId;

        @JsonProperty("cart_id")
        private String cartId;

        @JsonProperty("medusa_sync_status")
        private String medusaSyncStatus;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {

        @JsonProperty("id")
        private Integer id;

        @JsonProperty("role_id")
        private Integer roleId;

        @JsonProperty("name")
        private String name;

        @JsonProperty("email")
        private String email;

        @JsonProperty("shop_password")
        private String shopPassword;

        @JsonProperty("remember_token")
        private String rememberToken;

        @JsonProperty("mobile")
        private String mobile;

        @JsonProperty("city")
        private String city;

        @JsonProperty("pincode")
        private Integer pincode;

        @JsonProperty("address")
        private String address;

        @JsonProperty("avatar")
        private String avatar;

        @JsonProperty("fb_id")
        private String fbId;

        @JsonProperty("gp_id")
        private String gpId;

        @JsonProperty("ref_code")
        private String refCode;

        @JsonProperty("sponsor_id")
        private Integer sponsorId;

        @JsonProperty("woloo_id")
        private Integer wolooId;

        @JsonProperty("subscription_id")
        private Integer subscriptionId;

        @JsonProperty("expiry_date")
        private String expiryDate;

        @JsonProperty("voucher_id")
        private Integer voucherId;

        @JsonProperty("gift_subscription_id")
        private Integer giftSubscriptionId;

        @JsonProperty("lat")
        private Double lat;

        @JsonProperty("lng")
        private Double lng;

        @JsonProperty("otp")
        private String otp;

        @JsonProperty("status")
        private String status;

        @JsonProperty("settings")
        private String settings;

        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("updated_at")
        private String updatedAt;

        @JsonProperty("deleted_at")
        private String deletedAt;

        @JsonProperty("gender")
        private String gender;

        @JsonProperty("is_first_session")
        private Integer isFirstSession;

        @JsonProperty("dob")
        private String dob;

        @JsonProperty("is_thirst_reminder")
        private Integer isThirstReminder;

        @JsonProperty("IsVtionUser")
        private String isVtionUser;

        @JsonProperty("thirst_reminder_hours")
        private Integer thirstReminderHours;

        @JsonProperty("is_blog_content_notification")
        private Integer isBlogContentNotification;

        @JsonProperty("aadhar_url")
        private String aadharUrl;

        @JsonProperty("pan_url")
        private String panUrl;

        @JsonProperty("state")
        private String state;

        @JsonProperty("alternate_mob")
        private String alternateMob;

        @JsonProperty("isRegister")
        private Integer isRegister;

        @JsonProperty("isFreeTrial")
        private Integer isFreeTrial;
    }
}