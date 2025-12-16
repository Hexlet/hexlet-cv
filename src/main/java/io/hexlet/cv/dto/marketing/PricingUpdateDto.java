package io.hexlet.cv.dto.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class PricingUpdateDTO {

    @NotBlank(message = "Название тарифа обязательно")
    @Size(max = 100, message = "Название тарифа не должно превышать 100 символов")
    @JsonProperty("name")
    private JsonNullable<String> name;

    @NotNull(message = "Цена обязательна")
    @DecimalMin(value = "0.0", inclusive = false, message = "Цена должна быть больше 0")
    @JsonProperty("original_price")
    private JsonNullable<Double> originalPrice;

    @DecimalMin(value = "0.0", message = "Скидка не может быть отрицательной")
    @DecimalMax(value = "100.0", message = "Скидка не может превышать 100%")
    @JsonProperty("discount_percent")
    private JsonNullable<Double> discountPercent;

    @NotBlank(message = "Описание обязательно")
    @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
    @JsonProperty("description")
    private JsonNullable<String> description;

}
