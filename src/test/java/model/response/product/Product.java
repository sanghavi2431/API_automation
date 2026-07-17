package model.response.product;


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
public class Product {

    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("subtitle")
    private String subtitle;

    @JsonProperty("description")
    private String description;

    @JsonProperty("handle")
    private String handle;

    @JsonProperty("is_giftcard")
    private Boolean isGiftcard;

    @JsonProperty("discountable")
    private Boolean discountable;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("collection_id")
    private String collectionId;

    @JsonProperty("type_id")
    private String typeId;

    @JsonProperty("weight")
    private Double weight;

    @JsonProperty("length")
    private Double length;

    @JsonProperty("height")
    private Double height;

    @JsonProperty("width")
    private Double width;

    @JsonProperty("hs_code")
    private String hsCode;

    @JsonProperty("origin_country")
    private String originCountry;

    @JsonProperty("mid_code")
    private String midCode;

    @JsonProperty("material")
    private String material;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("type")
    private String type;

    @JsonProperty("collection")
    private ProductCollection collection;

    @JsonProperty("options")
    private List<ProductOption> options;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("images")
    private List<ProductImage> images;

    @JsonProperty("variants")
    private List<ProductVariant> variants;

    @JsonProperty("categories")
    private List<ProductCategory> categories;

    @JsonProperty("average_rating")
    private Double averageRating;

    @JsonProperty("review_count")
    private Integer reviewCount;

    @JsonProperty("variant_options_lookup")
    private List<VariantOptionLookup> variantOptionsLookup;

    @JsonProperty("default_variant_id")
    private String defaultVariantId;
}
