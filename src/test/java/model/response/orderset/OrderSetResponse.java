package model.response.orderset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Confirmed to cover both the standalone GetOrderSet-by-id endpoint and
 * the SplitCompleteCart endpoint - both return an identical order_set
 * shape, just with fresh ids for a different cart/checkout run.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderSetResponse {

    @JsonProperty("order_set")
    private OrderSet orderSet;
}