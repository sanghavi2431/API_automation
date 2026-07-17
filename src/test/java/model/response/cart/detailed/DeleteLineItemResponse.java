package model.response.cart.detailed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response from deleting a line item from a cart. "parent" is the same
 * CartDetailed shape returned by other cart-mutation endpoints (add item,
 * delete item, etc.) - confirmed here since this response's parent cart
 * has locale/item_discount_total/shipping_discount_total, the same fields
 * that distinguish CartDetailed from the slimmer CreateCart shape.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteLineItemResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("object")
    private String object;

    @JsonProperty("deleted")
    private Boolean deleted;

    @JsonProperty("parent")
    private CartDetailed parent;
}