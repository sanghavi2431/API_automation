package model.response.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A deliberately slim product snapshot embedded in each cart item -
 * not the full Product model from the product-list endpoint. Only
 * carries the fields the cart actually needs (ids + category refs + tags).
 *
 * If a future cart response shows a fuller nested product/variant shape
 * (as seen in some GET-cart responses with populated items), that is a
 * different endpoint behavior and should get its own model rather than
 * expanding this one - don't guess at fields not present in this
 * endpoint's actual payloads.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItemProduct {

    @JsonProperty("id")
    private String id;

    @JsonProperty("collection_id")
    private String collectionId;

    @JsonProperty("type_id")
    private String typeId;

    @JsonProperty("categories")
    private List<CategoryRef> categories;

    @JsonProperty("tags")
    private List<String> tags;
}