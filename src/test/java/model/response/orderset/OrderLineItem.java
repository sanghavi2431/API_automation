package model.response.orderset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.response.product.RawAmount;

import java.util.List;

/**
 * Every numeric total on this class has a matching "raw_*" counterpart
 * (e.g. total / raw_total) - the plain numeric field is a display-ready
 * convenience value, while raw_* preserves full decimal precision as a
 * string (see RawAmount's own javadoc for why). Both are kept, matching
 * what the API actually returns, rather than picking just one.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderLineItem {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("subtitle")
    private String subtitle;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("variant_id")
    private String variantId;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("product_title")
    private String productTitle;

    @JsonProperty("product_description")
    private String productDescription;

    @JsonProperty("product_subtitle")
    private String productSubtitle;

    @JsonProperty("product_type")
    private String productType;

    @JsonProperty("product_type_id")
    private String productTypeId;

    @JsonProperty("product_collection")
    private String productCollection;

    @JsonProperty("product_handle")
    private String productHandle;

    @JsonProperty("variant_sku")
    private String variantSku;

    @JsonProperty("variant_barcode")
    private String variantBarcode;

    @JsonProperty("variant_title")
    private String variantTitle;

    @JsonProperty("variant_option_values")
    private Object variantOptionValues;

    @JsonProperty("requires_shipping")
    private Boolean requiresShipping;

    @JsonProperty("is_giftcard")
    private Boolean isGiftcard;

    @JsonProperty("is_discountable")
    private Boolean isDiscountable;

    @JsonProperty("is_tax_inclusive")
    private Boolean isTaxInclusive;

    @JsonProperty("is_custom_price")
    private Boolean isCustomPrice;

    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("raw_compare_at_unit_price")
    private RawAmount rawCompareAtUnitPrice;

    @JsonProperty("raw_unit_price")
    private RawAmount rawUnitPrice;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;

    @JsonProperty("tax_lines")
    private List<Object> taxLines;

    @JsonProperty("adjustments")
    private List<OrderLineItemAdjustment> adjustments;

    @JsonProperty("compare_at_unit_price")
    private Double compareAtUnitPrice;

    @JsonProperty("unit_price")
    private Double unitPrice;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("raw_quantity")
    private RawAmount rawQuantity;

    @JsonProperty("detail")
    private OrderItemDetail detail;

    @JsonProperty("subtotal")
    private Double subtotal;

    @JsonProperty("total")
    private Double total;

    @JsonProperty("original_subtotal")
    private Double originalSubtotal;

    @JsonProperty("original_total")
    private Double originalTotal;

    @JsonProperty("discount_subtotal")
    private Double discountSubtotal;

    @JsonProperty("discount_tax_total")
    private Double discountTaxTotal;

    @JsonProperty("discount_total")
    private Double discountTotal;

    @JsonProperty("tax_total")
    private Double taxTotal;

    @JsonProperty("original_tax_total")
    private Double originalTaxTotal;

    @JsonProperty("refundable_total_per_unit")
    private Double refundableTotalPerUnit;

    @JsonProperty("refundable_total")
    private Double refundableTotal;

    @JsonProperty("fulfilled_total")
    private Double fulfilledTotal;

    @JsonProperty("shipped_total")
    private Double shippedTotal;

    @JsonProperty("return_requested_total")
    private Double returnRequestedTotal;

    @JsonProperty("return_received_total")
    private Double returnReceivedTotal;

    @JsonProperty("return_dismissed_total")
    private Double returnDismissedTotal;

    @JsonProperty("write_off_total")
    private Double writeOffTotal;

    @JsonProperty("raw_subtotal")
    private RawAmount rawSubtotal;

    @JsonProperty("raw_total")
    private RawAmount rawTotal;

    @JsonProperty("raw_original_subtotal")
    private RawAmount rawOriginalSubtotal;

    @JsonProperty("raw_original_total")
    private RawAmount rawOriginalTotal;

    @JsonProperty("raw_discount_subtotal")
    private RawAmount rawDiscountSubtotal;

    @JsonProperty("raw_discount_tax_total")
    private RawAmount rawDiscountTaxTotal;

    @JsonProperty("raw_discount_total")
    private RawAmount rawDiscountTotal;

    @JsonProperty("raw_tax_total")
    private RawAmount rawTaxTotal;

    @JsonProperty("raw_original_tax_total")
    private RawAmount rawOriginalTaxTotal;

    @JsonProperty("raw_refundable_total_per_unit")
    private RawAmount rawRefundableTotalPerUnit;

    @JsonProperty("raw_refundable_total")
    private RawAmount rawRefundableTotal;

    @JsonProperty("raw_fulfilled_total")
    private RawAmount rawFulfilledTotal;

    @JsonProperty("raw_shipped_total")
    private RawAmount rawShippedTotal;

    @JsonProperty("raw_return_requested_total")
    private RawAmount rawReturnRequestedTotal;

    @JsonProperty("raw_return_received_total")
    private RawAmount rawReturnReceivedTotal;

    @JsonProperty("raw_return_dismissed_total")
    private RawAmount rawReturnDismissedTotal;

    @JsonProperty("raw_write_off_total")
    private RawAmount rawWriteOffTotal;

    @JsonProperty("variant")
    private OrderVariant variant;

    @JsonProperty("product")
    private OrderLineItemProduct product;
}