package model.response.product;


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
public class PriceRef {

    @JsonProperty("id")
    private String id;

    @JsonProperty("price_list_id")
    private String priceListId;

    @JsonProperty("price_list_type")
    private String priceListType;

    @JsonProperty("min_quantity")
    private Integer minQuantity;

    @JsonProperty("max_quantity")
    private Integer maxQuantity;
}
