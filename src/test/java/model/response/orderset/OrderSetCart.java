package model.response.orderset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.response.cart.checkout.CartMetadata;

/**
 * Distinct from model.response.cart.checkout.CartCheckout: this snapshot
 * has no "items" array and no "region" object - by the time an order set
 * exists, the cart's items have already become order line items, so this
 * is just a lightweight summary of the cart the order was created from.
 *
 * Reuses CartMetadata from model.response.cart.checkout since the
 * VehicleDetails wrapper shape is identical.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderSetCart {

    @JsonProperty("id")
    private String id;

    @JsonProperty("region_id")
    private String regionId;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("sales_channel_id")
    private String salesChannelId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("locale")
    private String locale;

    @JsonProperty("metadata")
    private CartMetadata metadata;

    @JsonProperty("completed_at")
    private String completedAt;

    @JsonProperty("shipping_address")
    private OrderSetAddress shippingAddress;

    @JsonProperty("billing_address")
    private OrderSetAddress billingAddress;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;

    @JsonProperty("shipping_address_id")
    private String shippingAddressId;

    @JsonProperty("billing_address_id")
    private String billingAddressId;
}