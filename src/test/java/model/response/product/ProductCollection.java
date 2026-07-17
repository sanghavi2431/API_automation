package model.response.product;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Named ProductCollection (not Collection) to avoid clashing with java.util.Collection.
 * Represents the brand/collection a product belongs to, e.g. "AMUL", "BAZANA".
 *
 * metadata shape varies per collection (sometimes just an image, sometimes more),
 * so it is intentionally kept as a generic map rather than a rigid POJO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCollection {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("handle")
    private String handle;

    @JsonProperty("external_id")
    private String externalId;

    @JsonProperty("metadata")
    private Map<String, Object> metadata;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;
}
