package io.hexlet.cv.config.properties.pricing;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@ConfigurationProperties(prefix = "app.pricing")
public class PricingProperties {
    private int scale = 2;
    private double freeThreshold = 0.01;
    private double maxDiscount = 100;
    private double minPrice = 0.0;
}
