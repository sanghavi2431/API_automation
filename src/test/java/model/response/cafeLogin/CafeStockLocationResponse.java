package model.response.cafeLogin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CafeStockLocationResponse {

    @JsonProperty("vendor")
    private Vendor vendor;

    @JsonProperty("stock_location")
    private StockLocation stockLocation;

    @JsonProperty("sales_channel")
    private SalesChannel salesChannel;

    @JsonProperty("publishable_api_key")
    private String publishableApiKey;
}