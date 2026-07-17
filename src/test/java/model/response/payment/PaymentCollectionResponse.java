package model.response.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Same shape covers two observed states:
 *  - Create payment collection: payment_sessions=[], soft_reservation_status="created"
 *  - Initiate payment session:  payment_sessions=[one entry], soft_reservation_status="validated"
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentCollectionResponse {

    @JsonProperty("payment_collection")
    private PaymentCollectionDetail paymentCollection;

    @JsonProperty("soft_reservation_status")
    private String softReservationStatus;
}