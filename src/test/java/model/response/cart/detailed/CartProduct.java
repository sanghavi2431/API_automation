package model.response.cart.detailed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This API eagerly expands product -> images -> variants -> product in a
 * repeating pattern several levels deep, with the set of populated fields
 * shrinking at each level (e.g. the top-level item.product has tags,
 * images, and categories; the product nested inside item.variant does
 * not; the product nested two levels deeper than that has neither).
 *
 * Rather than creating a separate near-duplicate class per depth (which
 * would need updating in lockstep every time the API's eager-loading
 * behavior changes), a single class is used with the deeper/optional
 * fields (tags, images, categories) simply left null where the API
 * doesn't populate them - Jackson handles this correctly on its own.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartProduct {

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

    // Only populated at the shallowest nesting (item.product); null deeper in the tree.
    @JsonProperty("tags")
    private List<String> tags;

    // Populated at most nesting levels seen so far; may be absent at the deepest level.
    @JsonProperty("images")
    private List<CartImage> images;

    // Only populated at the shallowest nesting (item.product); null deeper in the tree.
    @JsonProperty("categories")
    private List<IdRef> categories;
}