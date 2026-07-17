package model.response.product;


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
public class CalculatedPrice {

    @JsonProperty("id")
    private String id;

    @JsonProperty("is_calculated_price_price_list")
    private Boolean isCalculatedPricePriceList;

    @JsonProperty("is_calculated_price_tax_inclusive")
    private Boolean isCalculatedPriceTaxInclusive;

    @JsonProperty("calculated_amount")
    private Double calculatedAmount;

    @JsonProperty("raw_calculated_amount")
    private RawAmount rawCalculatedAmount;

    @JsonProperty("is_original_price_price_list")
    private Boolean isOriginalPricePriceList;

    @JsonProperty("is_original_price_tax_inclusive")
    private Boolean isOriginalPriceTaxInclusive;

    @JsonProperty("original_amount")
    private Double originalAmount;

    @JsonProperty("raw_original_amount")
    private RawAmount rawOriginalAmount;

    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("calculated_price")
    private PriceRef calculatedPrice;

    @JsonProperty("original_price")
    private PriceRef originalPrice;
}
