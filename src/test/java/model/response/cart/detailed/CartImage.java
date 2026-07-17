package model.response.cart.detailed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Shape varies by nesting depth: images at item.product.images[] are
 * simple (no "product" or "variants" fields), while images nested inside
 * variant.images[] additionally carry a back-reference to their product
 * and a "variants" list (the variants that use this image). Both shapes
 * are covered by one class, with the deeper fields left null where absent.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartImage {

    @JsonProperty("id")
    private String id;

    @JsonProperty("url")
    private String url;

    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("rank")
    private Integer rank;

    @JsonProperty("product_id")
    private String productId;

    // Only present when this image is nested within a variant's images[].
    @JsonProperty("product")
    private CartProduct product;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;

    // Only present at the deepest nesting level seen (sibling of "product" above,
    // not nested inside it) - represents the variants that use this image.
    @JsonProperty("variants")
    private List<CartVariant> variants;
}