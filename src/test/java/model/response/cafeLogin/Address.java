package model.response.cafeLogin;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    @JsonProperty("id")
    private String id;

    @JsonProperty("address_1")
    private String address1;

    @JsonProperty("address_2")
    private String address2;

    @JsonProperty("city")
    private String city;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("phone")
    private String phone;
}
