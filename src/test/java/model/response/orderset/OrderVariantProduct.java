package model.response.orderset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.response.cart.detailed.IdRef;

import java.util.List;

/**
 * Same shallow-then-deeper recursive pattern seen in the cart-detailed
 * family: variant.product has "images", but the product nested one level
 * deeper (inside variant.images[].product) does not. One flexible class
 * with an optional "images" field covers both depths, same reasoning as
 * CartProduct in model.response.cart.detailed.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderVariantProduct {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("handle")
    private String handle;

    @JsonProperty("subtitle")
    private String subtitle;

    @JsonProperty("description")
    private String description;

    @JsonProperty("is_giftcard")
    private Boolean isGiftcard;

    @JsonProperty("status")
    private String status;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("weight")
    private Double weight;

    @JsonProperty("length")
    private Double length;

    @JsonProperty("height")
    private Double height;

    @JsonProperty("width")
    private Double width;

    @JsonProperty("origin_country")
    private String originCountry;

    @JsonProperty("hs_code")
    private String hsCode;

    @JsonProperty("mid_code")
    private String midCode;

    @JsonProperty("material")
    private String material;

    @JsonProperty("discountable")
    private Boolean discountable;

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("type_id")
    private String typeId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("collection_id")
    private String collectionId;

    @JsonProperty("collection")
    private IdRef collection;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;

    // Only populated at the shallower nesting (variant.product); absent
    // on the product nested inside variant.images[].product.
    @JsonProperty("images")
    private List<OrderVariantImage> images;
}