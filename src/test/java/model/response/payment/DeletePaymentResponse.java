package model.response.payment;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeletePaymentResponse {

    @JsonProperty("deleted")
    private Boolean deleted;

    @JsonProperty("ids")
    private List<String> ids;

}