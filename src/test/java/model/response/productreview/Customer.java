package model.response.productreview;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

	@JsonProperty("id")
	private String id;

	@JsonProperty("company_name")
	private String companyName;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;

	@JsonProperty("email")
	private String email;

	@JsonProperty("phone")
	private String phone;

	@JsonProperty("has_account")
	private Boolean hasAccount;

	@JsonProperty("metadata")
	private Object metadata;

	@JsonProperty("created_by")
	private Object createdBy;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("deleted_at")
	private Object deletedAt;

}