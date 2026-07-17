package model.response.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Deliberately not reusing model.response.cart.Customer - that shape is
 * just {id, email, groups}, while this one (payment context) carries
 * first_name/last_name/phone/company_name/addresses/account_holders, none
 * of which the cart Customer model has.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentContextCustomer {

    @JsonProperty("id")
    private String id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("addresses")
    private List<Object> addresses;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("account_holders")
    private List<Object> accountHolders;
}