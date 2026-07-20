package model.response.shipping;

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
public class PriceRule {

    @JsonProperty("id")
    private String id;

    @JsonProperty("attribute")
    private String attribute;

    @JsonProperty("value")
    private String value;

    @JsonProperty("operator")
    private String operator;

    @JsonProperty("priority")
    private Integer priority;

    @JsonProperty("price_id")
    private String priceId;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;
}