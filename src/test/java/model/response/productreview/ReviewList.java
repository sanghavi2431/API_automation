package model.response.productreview;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewList {
	@JsonProperty("id")
    private String id;

	@JsonProperty("rating")
    private Integer rating;

	@JsonProperty("comment")
    private String comment;

	@JsonProperty("approval")
    private Boolean approval;

	@JsonProperty("created_at")
    private String created_at;

	@JsonProperty("updated_at")
    private String updated_at;

	@JsonProperty("deleted_at")
    private Object deleted_at;

	@JsonProperty("customer")
    private Customer customer;

}