package io.hexlet.cv.util;

import java.util.function.Consumer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.jackson.nullable.JsonNullable;

@Slf4j
@UtilityClass
public class JsonNullableUtils {
    public <T> void ifPresent(JsonNullable<T> nullable, Consumer<T> action) {
        if (nullable != null && nullable.isPresent()) {
            action.accept(nullable.get());
        }
    }

    public <T> void logIfChanged(String fieldName, JsonNullable<T> nullable, T oldValue) {
        ifPresent(nullable, newValue -> {
            if (!newValue.equals(oldValue)) {
                log.info("Field {} changed from {} to {}", fieldName, oldValue, newValue);
            }
        });
    }
}
