package model.response.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Shared image model — used both at the product level (product.images[])
 * and inside each variant (variant.images[]).
 *
 * Note: variant-level images sometimes include a redundant "product" field
 * (a duplicate of product_id as a plain string) which is captured here too
 * since we can't assume it will never be relied upon.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductImage {

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

    @JsonProperty("product")
    private String product;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;
}
