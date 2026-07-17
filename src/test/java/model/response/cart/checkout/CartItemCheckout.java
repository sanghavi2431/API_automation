package model.response.cart.checkout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.response.cart.CartItemProduct;

import java.util.List;

/**
 * Same base fields as model.response.cart.CartItem, but with "adjustments"
 * populated once a promotion applies (rather than always empty), and no
 * "variant" key at all - this endpoint family never expands the variant.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItemCheckout {

    @JsonProperty("id")
    private String id;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("variant_id")
    private String variantId;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("product_type_id")
    private String productTypeId;

    @JsonProperty("product_title")
    private String productTitle;

    @JsonProperty("product_description")
    private String productDescription;

    @JsonProperty("product_subtitle")
    private String productSubtitle;

    @JsonProperty("product_type")
    private String productType;

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

    @JsonProperty("requires_shipping")
    private Boolean requiresShipping;

    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("title")
    private String title;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("unit_price")
    private Double unitPrice;

    @JsonProperty("compare_at_unit_price")
    private Double compareAtUnitPrice;

    @JsonProperty("is_tax_inclusive")
    private Boolean isTaxInclusive;

    @JsonProperty("tax_lines")
    private List<Object> taxLines;

    @JsonProperty("adjustments")
    private List<Adjustment> adjustments;

    // Reused from model.response.cart - same slim shape (id, collection_id,
    // type_id, categories[], tags[]) seen in the original CreateCart family.
    @JsonProperty("product")
    private CartItemProduct product;
}