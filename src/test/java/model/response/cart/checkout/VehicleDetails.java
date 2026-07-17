package model.response.cart.checkout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleDetails {

    @JsonProperty("id")
    private String id;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("vehicle_type")
    private String vehicleType;

    @JsonProperty("vehicle_number")
    private String vehicleNumber;
}