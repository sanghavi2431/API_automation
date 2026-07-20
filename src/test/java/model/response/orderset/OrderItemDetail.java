package model.response.orderset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.response.product.RawAmount;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemDetail {

    @JsonProperty("id")
    private String id;

    @JsonProperty("version")
    private Integer version;

    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("raw_unit_price")
    private RawAmount rawUnitPrice;

    @JsonProperty("raw_compare_at_unit_price")
    private RawAmount rawCompareAtUnitPrice;

    @JsonProperty("raw_quantity")
    private RawAmount rawQuantity;

    @JsonProperty("raw_fulfilled_quantity")
    private RawAmount rawFulfilledQuantity;

    @JsonProperty("raw_delivered_quantity")
    private RawAmount rawDeliveredQuantity;

    @JsonProperty("raw_shipped_quantity")
    private RawAmount rawShippedQuantity;

    @JsonProperty("raw_return_requested_quantity")
    private RawAmount rawReturnRequestedQuantity;

    @JsonProperty("raw_return_received_quantity")
    private RawAmount rawReturnReceivedQuantity;

    @JsonProperty("raw_return_dismissed_quantity")
    private RawAmount rawReturnDismissedQuantity;

    @JsonProperty("raw_written_off_quantity")
    private RawAmount rawWrittenOffQuantity;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;

    @JsonProperty("item_id")
    private String itemId;

    @JsonProperty("unit_price")
    private Double unitPrice;

    @JsonProperty("compare_at_unit_price")
    private Double compareAtUnitPrice;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("fulfilled_quantity")
    private Integer fulfilledQuantity;

    @JsonProperty("delivered_quantity")
    private Integer deliveredQuantity;

    @JsonProperty("shipped_quantity")
    private Integer shippedQuantity;

    @JsonProperty("return_requested_quantity")
    private Integer returnRequestedQuantity;

    @JsonProperty("return_received_quantity")
    private Integer returnReceivedQuantity;

    @JsonProperty("return_dismissed_quantity")
    private Integer returnDismissedQuantity;

    @JsonProperty("written_off_quantity")
    private Integer writtenOffQuantity;
}