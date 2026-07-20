package model.response.orderset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * IMPORTANT: the totals fields at THIS level (total, subtotal, tax_total,
 * discount_total, etc.) are returned as STRINGS ("72", "80") by the API,
 * while the identically-named fields inside orders[] (Order.java) are
 * returned as NUMBERS (72, 80). This is not a typo introduced here - it
 * is exactly what the API returns at each level, confirmed across four
 * separate captured payloads. Do not "fix" this by making both numeric;
 * Jackson would fail to deserialize the top-level totals if typed as
 * Double, since JSON strings don't auto-coerce to Double by default.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderSet {

    @JsonProperty("id")
    private String id;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("sales_channel_id")
    private String salesChannelId;

    @JsonProperty("cart_id")
    private String cartId;

    @JsonProperty("payment_collection_id")
    private String paymentCollectionId;

    @JsonProperty("sales_channel")
    private OrderSetSalesChannel salesChannel;

    @JsonProperty("cart")
    private OrderSetCart cart;

    @JsonProperty("payment_collection")
    private PaymentCollectionSummary paymentCollection;

    @JsonProperty("orders")
    private List<Order> orders;

    @JsonProperty("status")
    private String status;

    @JsonProperty("payment_status")
    private String paymentStatus;

    @JsonProperty("fulfillment_status")
    private String fulfillmentStatus;

    // --- STRING-typed totals; see class-level note above ---

    @JsonProperty("tax_total")
    private String taxTotal;

    @JsonProperty("shipping_tax_total")
    private String shippingTaxTotal;

    @JsonProperty("shipping_total")
    private String shippingTotal;

    @JsonProperty("total")
    private String total;

    @JsonProperty("subtotal")
    private String subtotal;

    @JsonProperty("discount_total")
    private String discountTotal;

    @JsonProperty("discount_tax_total")
    private String discountTaxTotal;

    @JsonProperty("original_total")
    private String originalTotal;

    @JsonProperty("original_tax_total")
    private String originalTaxTotal;

    @JsonProperty("item_total")
    private String itemTotal;

    @JsonProperty("item_subtotal")
    private String itemSubtotal;

    @JsonProperty("item_tax_total")
    private String itemTaxTotal;

    @JsonProperty("original_item_total")
    private String originalItemTotal;

    @JsonProperty("original_item_subtotal")
    private String originalItemSubtotal;

    @JsonProperty("original_item_tax_total")
    private String originalItemTaxTotal;

    @JsonProperty("delivery_date")
    private String deliveryDate;
}