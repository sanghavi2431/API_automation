package model.response.inventory;

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
public class InventoryItem {

    @JsonProperty("inventory_item_id")
    private String inventoryItemId;

    @JsonProperty("required_quantity")
    private Integer requiredQuantity;

    @JsonProperty("allow_backorder")
    private Boolean allowBackorder;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("location_ids")
    private List<String> locationIds;
}