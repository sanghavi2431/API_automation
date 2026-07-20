package model.response.cart.checkout;

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
 * Covers four endpoints observed to return this exact shape at different
 * points in checkout, with fields simply going from null/empty to populated
 * as the flow progresses (same principle as CartProduct's depth-dependent
 * fields in the detailed cart family):
 *
 *  - Apply promotion:      metadata=null, billing_address_id absent, shipping_methods=[]
 *  - Add vehicle details:  metadata populated, billing_address_id still absent
 *  - Add billing address:  billing_address_id now present, billing_address populated
 *  - Add shipping method:  shipping_methods[] now has a real entry
 *
 * Distinct from model.response.cart.detailed.CartDetailed: this shape's
 * item.product is the SLIM CartItemProduct (id/collection_id/type_id/
 * categories/tags, no "variant" key at all on the item) - not the richly
 * nested product+variant tree used by the add-to-cart/get-cart responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartCheckout {

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

    // null until add-vehicle-details is called; typed rather than left as
    // a generic Object/Map since VehicleDetails is a real, meaningful shape.
    @JsonProperty("metadata")
    private CartMetadata metadata;

    @JsonProperty("sales_channel_id")
    private String salesChannelId;

    @JsonProperty("shipping_address_id")
    private String shippingAddressId;

    // Only appears once a billing address has actually been set - absent
    // entirely (not just null) on earlier checkout states.
    @JsonProperty("billing_address_id")
    private String billingAddressId;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("items")
    private List<CartItemCheckout> items;

    // Typed (not List<Object>) since we've now seen a populated example.
    @JsonProperty("shipping_methods")
    private List<ShippingMethod> shippingMethods;

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

    // Typed (not List<Object>) since we've now seen a populated example.
    @JsonProperty("promotions")
    private List<Promotion> promotions;
}