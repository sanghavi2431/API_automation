package model.response.orderset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.response.cart.detailed.IdRef;
import model.response.payment.PaymentData;
import model.response.product.RawAmount;

import java.util.List;

/**
 * "data" is left generic (Object), same reasoning as PaymentSession.data
 * in model.response.payment: this is raw, provider-specific gateway data
 * (Razorpay's shape here) that would look completely different for any
 * other payment provider this API might support in future.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderPayment {

    @JsonProperty("id")
    private String id;

    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("provider_id")
    private String providerId;

    @JsonProperty("data")
    private PaymentData data;

    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("captured_at")
    private String capturedAt;

    @JsonProperty("canceled_at")
    private String canceledAt;

    @JsonProperty("payment_collection_id")
    private String paymentCollectionId;

    @JsonProperty("payment_session")
    private IdRef paymentSession;

    @JsonProperty("raw_amount")
    private RawAmount rawAmount;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;

    @JsonProperty("payment_session_id")
    private String paymentSessionId;

    @JsonProperty("refunds")
    private List<Object> refunds;

    @JsonProperty("amount")
    private Double amount;
}