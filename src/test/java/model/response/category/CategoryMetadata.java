package model.response.category;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Same metadata shape used for Category, ParentCategory, and CategoryChild -
 * factored into its own class rather than duplicated three times, since a
 * fix or new field here (e.g. a future metadata key) only needs to change
 * once and immediately applies everywhere it's used.
 *
 * Note the inconsistent key casing coming from the API itself:
 * "delivery_time" (snake_case) vs "estimateDeliveryTime" (camelCase).
 * This is preserved exactly via @JsonProperty rather than "fixed",
 * since the mapping must match what the API actually returns.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryMetadata {

    @JsonProperty("image")
    private String image;

    @JsonProperty("delivery_time")
    private Integer deliveryTime;

    @JsonProperty("background_color")
    private String backgroundColor;

    @JsonProperty("estimateDeliveryTime")
    private Integer estimateDeliveryTime;

    @JsonProperty("show_adv")
    private Boolean showAdv;
}
