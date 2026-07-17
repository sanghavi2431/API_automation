package model.response.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryChild {

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

    // Field kept as "active"/"internal" (not "isActive"/"isInternal") to
    // preserve the original getActive()/getInternal() accessor names used
    // elsewhere in the test suite, even though the JSON key is "is_active".
    @JsonProperty("is_active")
    private Boolean active;

    @JsonProperty("is_internal")
    private Boolean internal;

    @JsonProperty("rank")
    private Integer rank;

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("metadata")
    private CategoryMetadata metadata;

    @JsonProperty("parent_category_id")
    private String parentCategoryId;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;
}