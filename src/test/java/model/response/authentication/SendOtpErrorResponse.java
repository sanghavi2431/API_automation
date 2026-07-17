package model.response.authentication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendOtpErrorResponse {

    @JsonProperty("details")
    private String details;

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("message")
    private String message;

}