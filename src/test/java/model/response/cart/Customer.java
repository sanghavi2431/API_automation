package model.response.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {

    @JsonProperty("id")
    private String id;

    @JsonProperty("email")
    private String email;

    // Empty in every sample seen so far; promote to a typed CustomerGroup
    // model if the customer is ever returned with actual group membership.
    @JsonProperty("groups")
    private List<Object> groups;
}