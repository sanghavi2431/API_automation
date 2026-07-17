package model.response.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Only an id is returned here (unlike the full ProductCategory model
 * from the product-list endpoint). Kept as its own class rather than
 * reusing ProductCategory, since that model expects many required
 * fields this shape does not provide.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryRef {

    @JsonProperty("id")
    private String id;
}