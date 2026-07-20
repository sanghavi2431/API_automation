package model.response.orderset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.response.product.RawAmount;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentCollectionSummary {

    @JsonProperty("id")
    private String id;

    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("completed_at")
    private String completedAt;

    @JsonProperty("status")
    private String status;

    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("raw_amount")
    private RawAmount rawAmount;

    @JsonProperty("raw_authorized_amount")
    private RawAmount rawAuthorizedAmount;

    @JsonProperty("raw_captured_amount")
    private RawAmount rawCapturedAmount;

    @JsonProperty("raw_refunded_amount")
    private RawAmount rawRefundedAmount;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("authorized_amount")
    private Double authorizedAmount;

    @JsonProperty("captured_amount")
    private Double capturedAmount;

    @JsonProperty("refunded_amount")
    private Double refundedAmount;
}