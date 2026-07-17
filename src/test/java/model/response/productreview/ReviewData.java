package model.response.productreview;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewData {

	@JsonProperty("product_id")
    private String product_id;

	@JsonProperty("reviews")
    private List<ReviewList> reviews;

}