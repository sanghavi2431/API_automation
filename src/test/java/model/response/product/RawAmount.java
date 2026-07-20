package model.response.product;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * "value" is deliberately a String, not a number: the API returns
 * high-precision decimal values as strings (e.g. "10") to avoid floating
 * point rounding issues. Convert with BigDecimal at the point of use,
 * not by changing this field to a numeric type.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RawAmount {

    @JsonProperty("value")
    private String value;

    @JsonProperty("precision")
    private Integer precision;
}
