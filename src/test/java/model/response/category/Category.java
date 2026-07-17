package model.response.category;


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
public class Category {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("handle")
    private String handle;

    @JsonProperty("rank")
    private Integer rank;

    @JsonProperty("parent_category_id")
    private String parentCategoryId;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("metadata")
    private CategoryMetadata metadata;

    @JsonProperty("parent_category")
    private ParentCategory parentCategory;

    @JsonProperty("category_children")
    private List<CategoryChild> categoryChildren;

    @JsonProperty("sales_channels")
    private List<SalesChannel> salesChannels;
}
