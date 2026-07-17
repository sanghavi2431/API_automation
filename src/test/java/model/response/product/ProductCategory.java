package model.response.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCategory {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("handle")
    private String handle;

    @JsonProperty("mpath")
    private String mpath;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("is_internal")
    private Boolean isInternal;

    @JsonProperty("rank")
    private Integer rank;

    @JsonProperty("external_id")
    private String externalId;

    // metadata shape varies (image / delivery_time / background_color / estimateDeliveryTime,
    // in different combinations per category) - kept generic rather than modeled rigidly.
    @JsonProperty("metadata")
    private Map<String, Object> metadata;

    @JsonProperty("parent_category_id")
    private String parentCategoryId;

    // Recursive by nature (a category can have a parent category of the same shape).
    // Left as Object here since it is null in every sample seen; promote to
    // ProductCategory if nested parent data is ever populated.
    @JsonProperty("parent_category")
    private Object parentCategory;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;
}
