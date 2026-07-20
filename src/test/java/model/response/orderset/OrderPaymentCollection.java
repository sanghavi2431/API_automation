package model.response.orderset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.response.product.RawAmount;

import java.util.List;

/**
 * Same core fields as PaymentCollectionSummary (order_set-level), plus a
 * "payments" list this summary doesn't carry - kept as a separate class
 * rather than adding an optional "payments" field to PaymentCollectionSummary,
 * since that summary is used at the order_set level where payments are
 * never populated.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderPaymentCollection {

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

    @JsonProperty("payments")
    private List<OrderPayment> payments;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("authorized_amount")
    private Double authorizedAmount;

    @JsonProperty("captured_amount")
    private Double capturedAmount;

    @JsonProperty("refunded_amount")
    private Double refundedAmount;
}