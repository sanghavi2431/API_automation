package model.response.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentNotes {

    @JsonProperty("session_id")
    private String sessionId;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("customer_email")
    private String customerEmail;

}