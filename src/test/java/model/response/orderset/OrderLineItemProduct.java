package model.response.orderset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.response.product.ProductCategory;
import model.response.product.ProductCollection;

import java.util.List;

/**
 * A sibling field to OrderLineItem.variant, not nested inside it. Where
 * variant.product.collection is a bare {id} stub (IdRef), this "product"
 * field carries the FULL category objects (name, handle, metadata, etc.)
 * and full collection object - reusing ProductCategory/ProductCollection
 * from model.response.product since both shapes match exactly.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderLineItemProduct {

    @JsonProperty("id")
    private String id;

    @JsonProperty("images")
    private List<OrderVariantImage> images;

    @JsonProperty("categories")
    private List<ProductCategory> categories;

    @JsonProperty("collection")
    private ProductCollection collection;
}