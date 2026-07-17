package requestModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductReviewRequest {
	@JsonProperty("comment")
    private String comment;

	@JsonProperty("product_id")
    private String product_id;

	@JsonProperty("rating")
    private Integer rating;

}