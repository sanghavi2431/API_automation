package model.response.cart;

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
public class Cart {

    @JsonProperty("id")
    private String id;

    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("email")
    private String email;

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
    private Object metadata;//vehicle details object needs to be pass

    @JsonProperty("sales_channel_id")
    private String salesChannelId;

    @JsonProperty("shipping_address_id")
    private String shippingAddressId;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("items")
    private List<CartItem> items;

    // Empty in every sample seen so far; promote to a typed ShippingMethod
    // model if the cart ever has an actual shipping method attached.
    @JsonProperty("shipping_methods")
    private List<Object> shippingMethods;

    @JsonProperty("shipping_address")
    private Address shippingAddress;

    @JsonProperty("billing_address")
    private Address billingAddress;

    // Empty in every sample seen so far; promote to a typed CreditLine
    // model if the cart ever carries actual credit line entries.
    @JsonProperty("credit_lines")
    private List<Object> creditLines;

    @JsonProperty("customer")
    private Customer customer;

    @JsonProperty("region")
    private Region region;

    // Empty in every sample seen so far; promote to a typed Promotion
    // model if the cart ever has an applied promotion.
    @JsonProperty("promotions")
    private List<Object> promotions;
}