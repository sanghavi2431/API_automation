package model.response.orderset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Deliberately not reusing model.response.cart.Address - this shape has
 * an extra customer_id field (always null in samples so far) and company,
 * neither of which the cart Address model carries.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderSetAddress {

    @JsonProperty("id")
    private String id;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("company")
    private String company;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("address_1")
    private String address1;

    @JsonProperty("address_2")
    private String address2;

    @JsonProperty("city")
    private String city;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("province")
    private String province;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;
}