package model.response.restock;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestockSubscription {

    @JsonProperty("id")
    private String id;

    @JsonProperty("variant_id")
    private String variantId;

    @JsonProperty("sales_channel_id")
    private String salesChannelId;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("deleted_at")
    private String deletedAt;

}
