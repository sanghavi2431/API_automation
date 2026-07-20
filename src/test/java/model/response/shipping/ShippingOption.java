package model.response.shipping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.response.product.CalculatedPrice;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShippingOption {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price_type")
    private String priceType;

    @JsonProperty("service_zone_id")
    private String serviceZoneId;

    @JsonProperty("shipping_profile_id")
    private String shippingProfileId;

    @JsonProperty("provider_id")
    private String providerId;

    @JsonProperty("data")
    private Object data;

    @JsonProperty("service_zone")
    private ServiceZone serviceZone;

    @JsonProperty("type")
    private ShippingOptionType type;

    @JsonProperty("provider")
    private ShippingProvider provider;

    @JsonProperty("rules")
    private List<ShippingRule> rules;

    // Reused as-is from model.response.product - identical shape
    // (id, is_calculated_price_price_list, calculated_amount,
    // raw_calculated_amount{value,precision}, currency_code, etc.)
    @JsonProperty("calculated_price")
    private CalculatedPrice calculatedPrice;

    @JsonProperty("prices")
    private List<ShippingPrice> prices;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("is_tax_inclusive")
    private Boolean isTaxInclusive;

    @JsonProperty("insufficient_inventory")
    private Boolean insufficientInventory;

    @JsonProperty("vendor_name")
    private String vendorName;

    @JsonProperty("vendor_id")
    private String vendorId;
}