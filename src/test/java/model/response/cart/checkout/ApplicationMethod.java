package model.response.cart.checkout;

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
public class ApplicationMethod {

    @JsonProperty("value")
    private Double value;

    @JsonProperty("type")
    private String type;

    @JsonProperty("currency_code")
    private String currencyCode;

    @JsonProperty("id")
    private String id;
}