package io.hexlet.cv.dto.marketing;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class PricingUpdateDto {

    @Size(max = 100, message = "Name must not exceed 100 characters")
    private JsonNullable<String> name;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private JsonNullable<Double> originalPrice;

    @DecimalMin(value = "0.0", message = "Discount cannot be negative")
    @DecimalMax(value = "100.0", message = "Discount cannot exceed 100%")
    private JsonNullable<Double> discountPercent;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private JsonNullable<String> description;

}
