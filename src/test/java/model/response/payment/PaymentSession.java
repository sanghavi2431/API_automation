package model.response.payment;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.response.product.RawAmount;

/**
 * "data" is left as a generic Object rather than typed to Razorpay's shape
 * specifically - this API may support other payment providers in future,
 * and each provider's gateway payload will look completely different
 * (Razorpay's "data" has id/notes/entity/receipt/amount_due/etc., which
 * would be meaningless for a different provider). "context" is nullable:
 * absent/null right after payment-collection creation, populated once a
 * session is actually initiated.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentSession {

    @JsonProperty("id")
    private String id;

    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("provider_id")
    private String providerId;

    @JsonProperty("data")
    private Object  data;

    @JsonProperty("context")
    private PaymentSessionContext context;

    @JsonProperty("status")
    private String status;

    @JsonProperty("authorized_at")
    private String authorizedAt;

    @JsonProperty("payment_collection_id")
    private String paymentCollectionId;

    @JsonProperty("metadata")
    private Map<String,Object> metadata;

    @JsonProperty("raw_amount")
    private RawAmount rawAmount;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;

    @JsonProperty("amount")
    private Double amount;
}