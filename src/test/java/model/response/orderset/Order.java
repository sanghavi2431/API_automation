package model.response.orderset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Same field names as OrderSet's totals, but typed as Double here since
 * the API returns them as JSON numbers at this nesting level - see the
 * type-inconsistency note on OrderSet.java for the full explanation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    @JsonProperty("id")
    private String id;

    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("status")
    private String status;

    @JsonProperty("total")
    private Double total;

    @JsonProperty("subtotal")
    private Double subtotal;

    @JsonProperty("tax_total")
    private Double taxTotal;

    @JsonProperty("discount_total")
    private Double discountTotal;

    @JsonProperty("discount_tax_total")
    private Double discountTaxTotal;

    @JsonProperty("original_total")
    private Double originalTotal;

    @JsonProperty("original_tax_total")
    private Double originalTaxTotal;

    @JsonProperty("item_total")
    private Double itemTotal;

    @JsonProperty("item_subtotal")
    private Double itemSubtotal;

    @JsonProperty("item_tax_total")
    private Double itemTaxTotal;

    @JsonProperty("sales_channel_id")
    private String salesChannelId;

    @JsonProperty("original_item_total")
    private Double originalItemTotal;

    @JsonProperty("original_item_subtotal")
    private Double originalItemSubtotal;

    @JsonProperty("original_item_tax_total")
    private Double originalItemTaxTotal;

    @JsonProperty("shipping_total")
    private Double shippingTotal;

    @JsonProperty("shipping_subtotal")
    private Double shippingSubtotal;

    @JsonProperty("shipping_tax_total")
    private Double shippingTaxTotal;

    @JsonProperty("items")
    private List<OrderLineItem> items;

    @JsonProperty("fulfillments")
    private List<Object> fulfillments;

    @JsonProperty("payment_collections")
    private List<OrderPaymentCollection> paymentCollections;

    @JsonProperty("payment_status")
    private String paymentStatus;

    @JsonProperty("fulfillment_status")
    private String fulfillmentStatus;
}