package model.response.productreview;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetProductReviewResponse {
	@JsonProperty("success")
    private Boolean success;

	@JsonProperty("data")
    private ReviewData data;

}