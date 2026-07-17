package model.response.product;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * variant_options_lookup entries have one fixed field (variant_id) plus a
 * variable set of attribute keys depending on the product's option types
 * (e.g. "flavour" and "size" for ice cream, "type" for perfumes, "size"
 * only for single-option products). Rather than modeling every possible
 * key as a nullable field, dynamic keys are captured into an attributes
 * map via @JsonAnySetter/@JsonAnyGetter.
 *
 * NOT using @Data here since Lombok's generated accessor for the map
 * would not automatically carry the @JsonAnyGetter/@JsonAnySetter
 * annotations Jackson needs - those are applied manually below instead.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class VariantOptionLookup {

    @JsonProperty("variant_id")
    private String variantId;

    @Default
    private Map<String, String> attributes = new HashMap<>();

    @JsonAnySetter
    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }
}
