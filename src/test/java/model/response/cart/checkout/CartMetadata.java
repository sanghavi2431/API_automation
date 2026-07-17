package model.response.cart.checkout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Note the JSON key is "VehicleDetails" (capital V) despite every other
 * field in this API being snake_case - preserved exactly via @JsonProperty
 * rather than "corrected", since Jackson must match what the API actually
 * returns.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartMetadata {

    @JsonProperty("VehicleDetails")
    private VehicleDetails vehicleDetails;
}