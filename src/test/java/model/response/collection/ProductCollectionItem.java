package model.response.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.response.category.SalesChannel;

import java.util.List;

/**
 * Named ProductCollectionItem (not ProductCollection) to avoid confusion
 * with model.response.product.ProductCollection - that class represents
 * the slim collection reference embedded inside a Product, while this one
 * is the fuller entity returned by the dedicated collections list endpoint
 * (it additionally carries sales_channels, which the product-embedded
 * version never does).
 *
 * SalesChannel is reused as-is from model.response.category, since the
 * shape returned here is identical (id, name, description, is_disabled,
 * metadata{mobile, estimateDeliveryTime}, timestamps) - duplicating it
 * under this package would just create two classes that silently drift
 * apart the next time either endpoint changes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCollectionItem {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("metadata")
    private CollectionMetadata metadata;

    @JsonProperty("sales_channels")
    private List<SalesChannel> salesChannels;
}