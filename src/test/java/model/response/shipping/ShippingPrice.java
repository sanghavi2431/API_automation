package model.response.shipping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.response.product.RawAmount;

import java.util.List;

/**
 * Distinct from model.response.product.PriceRef (which is just an id +
 * price_list stub used inside CalculatedPrice) - this is the fuller price
 * record with its own rules_count, price_rules[], and raw amounts, shaped
 * differently enough that reusing PriceRef would require bolting on
 * several fields it was never meant to carry.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShippingPrice {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("rules_count")
    private Integer rulesCount;

    @JsonProperty("price_set_id")
    private String priceSetId;

    @JsonProperty("price_list_id")
    private String priceListId;

    @JsonProperty("price_list")
    private Object priceList;

    @JsonProperty("raw_amount")
    private RawAmount rawAmount;

    @JsonProperty("raw_min_quantity")
    private RawAmount rawMinQuantity;

    @JsonProperty("raw_max_quantity")
    private RawAmount rawMaxQuantity;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;

    @JsonProperty("price_rules")
    private List<PriceRule> priceRules;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("min_quantity")
    private Integer minQuantity;

    @JsonProperty("max_quantity")
    private Integer maxQuantity;
}