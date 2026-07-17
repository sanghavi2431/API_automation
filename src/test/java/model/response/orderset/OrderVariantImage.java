package model.response.orderset;

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
public class OrderVariantImage {

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

    // Only present when this image is nested within variant.images[].
    @JsonProperty("product")
    private OrderVariantProduct product;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;

    // Empty in every sample seen so far; kept generic rather than typed
    // to OrderVariant, since no populated example has confirmed that shape
    // at this specific nesting point.
    @JsonProperty("variants")
    private List<Object> variants;
}