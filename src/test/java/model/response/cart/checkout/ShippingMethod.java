package model.response.cart.checkout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShippingMethod {

    @JsonProperty("id")
    private String id;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("is_tax_inclusive")
    private Boolean isTaxInclusive;

    @JsonProperty("shipping_option_id")
    private String shippingOptionId;

    @JsonProperty("tax_lines")
    private List<Object> taxLines;

    @JsonProperty("adjustments")
    private List<Object> adjustments;
}