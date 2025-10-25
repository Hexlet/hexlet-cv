package io.hexlet.cv.dto.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PricingDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("original_price")
    private Double originalPrice;

    @JsonProperty("discount_percent")
    private Double discountPercent;

    @JsonProperty("final_price")
    private Double finalPrice;

    @JsonProperty("description")
    private String description;
}
