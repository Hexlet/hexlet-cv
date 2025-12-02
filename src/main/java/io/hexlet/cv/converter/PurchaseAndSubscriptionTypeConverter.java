package io.hexlet.cv.converter;

import io.hexlet.cv.model.enums.StatePurchaseSubscriptionType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PurchaseAndSubscriptionTypeConverter implements AttributeConverter<StatePurchaseSubscriptionType, String> {

    @Override
    public String convertToDatabaseColumn(StatePurchaseSubscriptionType stateType) {
        return stateType == null ? null : stateType.name().toLowerCase();
    }

    @Override
    public StatePurchaseSubscriptionType convertToEntityAttribute(String dbValue) {
        return dbValue == null ? null : StatePurchaseSubscriptionType.valueOf(dbValue.toUpperCase());
    }
}
