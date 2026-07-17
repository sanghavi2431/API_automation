package model.response.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentData {

    @JsonProperty("id")
    private String id;

    @JsonProperty("notes")
    private PaymentNotes notes;

    @JsonProperty("amount")
    private Integer amount;

    @JsonProperty("entity")
    private String entity;

    @JsonProperty("status")
    private String status;

    @JsonProperty("receipt")
    private String receipt;

    @JsonProperty("attempts")
    private Integer attempts;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("offer_id")
    private Object offerId;

    @JsonProperty("amount_due")
    private Integer amountDue;

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("session_id")
    private String sessionId;

    @JsonProperty("amount_paid")
    private Integer amountPaid;

}