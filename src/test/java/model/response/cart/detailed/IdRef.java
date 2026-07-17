package model.response.cart.detailed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The API returns bare {"id": "..."} stubs in several places within this
 * nested cart structure (product.collection, product.categories[],
 * variant.options[].option). Rather than creating a separate one-field
 * class for each of those three call sites, they all share this single
 * reusable reference type.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdRef {

    @JsonProperty("id")
    private String id;
}