package model.response.product;


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
public class ProductVariant {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("sku")
    private String sku;

    @JsonProperty("barcode")
    private String barcode;

    @JsonProperty("ean")
    private String ean;

    @JsonProperty("upc")
    private String upc;

    @JsonProperty("allow_backorder")
    private Boolean allowBackorder;

    @JsonProperty("manage_inventory")
    private Boolean manageInventory;

    @JsonProperty("hs_code")
    private String hsCode;

    @JsonProperty("origin_country")
    private String originCountry;

    @JsonProperty("mid_code")
    private String midCode;

    @JsonProperty("material")
    private String material;

    @JsonProperty("weight")
    private Double weight;

    @JsonProperty("length")
    private Double length;

    @JsonProperty("height")
    private Double height;

    @JsonProperty("width")
    private Double width;

    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("variant_rank")
    private Integer variantRank;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;

    @JsonProperty("images")
    private List<ProductImage> images;

    @JsonProperty("options")
    private List<VariantOption> options;

    @JsonProperty("calculated_price")
    private CalculatedPrice calculatedPrice;

    @JsonProperty("inventory_quantity")
    private Integer inventoryQuantity;

    @JsonProperty("has_restock_subscription")
    private Boolean hasRestockSubscription;

    @JsonProperty("has_wishlisted")
    private Boolean hasWishlisted;

    @JsonProperty("wishlist_item_id")
    private String wishlistItemId;
}
