package model.response.cafeLogin;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperatingHours {

    @JsonProperty("monday")
    private DayHours monday;

    @JsonProperty("tuesday")
    private DayHours tuesday;

    @JsonProperty("wednesday")
    private DayHours wednesday;

    @JsonProperty("thursday")
    private DayHours thursday;

    @JsonProperty("friday")
    private DayHours friday;

    @JsonProperty("saturday")
    private DayHours saturday;

    @JsonProperty("sunday")
    private DayHours sunday;
}
