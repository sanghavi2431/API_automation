package model.response.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Deliberately its own class rather than reusing category.CategoryMetadata:
 * that class carries fields (delivery_time, background_color, show_adv)
 * that never appear on a collection's metadata in any sample seen, and
 * conflating the two would make it unclear which fields are actually
 * populated for which entity type.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionMetadata {

    @JsonProperty("image")
    private String image;
}