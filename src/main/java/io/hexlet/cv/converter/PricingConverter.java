package io.hexlet.cv.converter;

import io.hexlet.cv.config.properties.pricing.PricingProperties;
import io.hexlet.cv.dto.marketing.PricingDto;
import io.hexlet.cv.model.admin.marketing.PricingPlan;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class PricingConverter {

    private final PricingProperties pricingProperties;

    public PricingDto convert(PricingPlan pricingPlan) {
        if (pricingPlan == null) {
            log.debug("Attempt to convert null pricing entity");
            return null;
        }

        log.debug("Converting pricing entity (id={}, name={})",
                pricingPlan.getId(), pricingPlan.getName());

        Double discountAmount = pricingPlan.getDiscountPercent() == null
                || pricingPlan.getDiscountPercent() == 0 ? 0.0
                : Math.round(pricingPlan.getOriginalPrice() * pricingPlan.getDiscountPercent()) / 100.0;


        Double savings = Math.max(
                pricingPlan.getOriginalPrice() - pricingPlan.getFinalPrice(),
                0.0
        );

        return PricingDto.builder()
                .id(pricingPlan.getId())
                .name(pricingPlan.getName())
                .originalPrice(pricingPlan.getOriginalPrice())
                .discountPercent(pricingPlan.getDiscountPercent())
                .finalPrice(pricingPlan.getFinalPrice())
                .description(pricingPlan.getDescription())
                .discountAmount(discountAmount)
                .savings(savings)
                .hasDiscount(pricingPlan.getDiscountPercent() > 0)
                .isFree(pricingPlan.getFinalPrice() < pricingProperties.getFreeThreshold())
                .build();
    }
}
