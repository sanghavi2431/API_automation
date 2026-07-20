package model.response.shipping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Deliberately not reusing model.response.cart.Address - that shape is a
 * customer's shipping/billing address (first_name, last_name, province,
 * phone as a customer contact); this is a warehouse/store's own address
 * (company, no first_name/last_name) and picks up deleted_at/timestamps
 * that the cart Address never has.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationAddress {

    @JsonProperty("id")
    private String id;

    @JsonProperty("address_1")
    private String address1;

    @JsonProperty("address_2")
    private String address2;

    @JsonProperty("company")
    private String company;

    @JsonProperty("city")
    private String city;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("province")
    private String province;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;
}