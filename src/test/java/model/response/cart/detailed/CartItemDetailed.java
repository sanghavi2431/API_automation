package model.response.cart.detailed;

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
public class CartItemDetailed {

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
    private List<Object> adjustments;

    @JsonProperty("product")
    private CartProduct product;

    @JsonProperty("variant")
    private CartVariant variant;
}