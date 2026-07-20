package model.response.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Deliberately separate from CategoryMetadata - despite both being called
 * "metadata" in the JSON, this shape (mobile + estimateDeliveryTime) has
 * no overlap with CategoryMetadata's fields (image, delivery_time,
 * background_color, show_adv), so merging them would just create a class
 * full of fields that are always null depending on context.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesChannelMetadata {

    @JsonProperty("mobile")
    private Long mobile;

    @JsonProperty("estimateDeliveryTime")
    private Integer estimateDeliveryTime;
}