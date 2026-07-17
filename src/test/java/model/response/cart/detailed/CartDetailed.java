package model.response.cart.detailed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.response.cart.Address;
import model.response.cart.Customer;
import model.response.cart.Region;

import java.util.List;

/**
 * Distinct from model.response.cart.Cart (the slim CreateCart shape).
 * This version appears once a cart has at least one item added, and
 * carries three extra fields (locale, item_discount_total,
 * shipping_discount_total) not present on a freshly created empty cart.
 *
 * Address, Customer, and Region are reused as-is from model.response.cart
 * since those shapes are identical between the two cart states - only the
 * item/product/variant nesting differs.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartDetailed {

    @JsonProperty("id")
    private String id;

    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("email")
    private String email;

    @JsonProperty("locale")
    private String locale;

    @JsonProperty("region_id")
    private String regionId;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("completed_at")
    private String completedAt;

    @JsonProperty("total")
    private Double total;

    @JsonProperty("subtotal")
    private Double subtotal;

    @JsonProperty("tax_total")
    private Double taxTotal;

    @JsonProperty("discount_total")
    private Double discountTotal;

    @JsonProperty("discount_subtotal")
    private Double discountSubtotal;

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

    @JsonProperty("item_discount_total")
    private Double itemDiscountTotal;

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

    @JsonProperty("shipping_discount_total")
    private Double shippingDiscountTotal;

    @JsonProperty("original_shipping_tax_total")
    private Double originalShippingTaxTotal;

    @JsonProperty("original_shipping_subtotal")
    private Double originalShippingSubtotal;

    @JsonProperty("original_shipping_total")
    private Double originalShippingTotal;

    @JsonProperty("credit_line_subtotal")
    private Double creditLineSubtotal;

    @JsonProperty("credit_line_tax_total")
    private Double creditLineTaxTotal;

    @JsonProperty("credit_line_total")
    private Double creditLineTotal;

    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("sales_channel_id")
    private String salesChannelId;

    @JsonProperty("shipping_address_id")
    private String shippingAddressId;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("items")
    private List<CartItemDetailed> items;

    @JsonProperty("shipping_methods")
    private List<Object> shippingMethods;

    @JsonProperty("shipping_address")
    private Address shippingAddress;

    @JsonProperty("billing_address")
    private Address billingAddress;

    @JsonProperty("credit_lines")
    private List<Object> creditLines;

    @JsonProperty("customer")
    private Customer customer;

    @JsonProperty("region")
    private Region region;

    @JsonProperty("promotions")
    private List<Object> promotions;
}