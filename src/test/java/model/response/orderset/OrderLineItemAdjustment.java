package model.response.orderset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.response.product.RawAmount;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderLineItemAdjustment {

    @JsonProperty("id")
    private String id;

    @JsonProperty("version")
    private Integer version;

    @JsonProperty("description")
    private String description;

    @JsonProperty("promotion_id")
    private String promotionId;

    @JsonProperty("code")
    private String code;

    @JsonProperty("provider_id")
    private String providerId;

    @JsonProperty("is_tax_inclusive")
    private Boolean isTaxInclusive;

    @JsonProperty("item_id")
    private String itemId;

    @JsonProperty("raw_amount")
    private RawAmount rawAmount;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("subtotal")
    private Double subtotal;

    @JsonProperty("total")
    private Double total;

    @JsonProperty("raw_subtotal")
    private RawAmount rawSubtotal;

    @JsonProperty("raw_total")
    private RawAmount rawTotal;
}