package io.hexlet.cv.dto.marketing;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PricingDto {
    private Long id;
    private String name;
    private Double originalPrice;
    private Double discountPercent;
    private Double finalPrice;
    private String description;
    private Double discountAmount;
    private Double savings;
    private Boolean hasDiscount;
    private Boolean isFree;
}
