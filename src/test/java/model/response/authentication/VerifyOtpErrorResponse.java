package model.response.authentication;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyOtpErrorResponse {

    @JsonProperty("data")
    private List<Object> data;

    @JsonProperty("message")
    private String message;

    @JsonProperty("success")
    private Boolean success;

}