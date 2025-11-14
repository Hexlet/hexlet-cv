package io.hexlet.cv.converter;

import io.hexlet.cv.model.enums.StatePurchSubsType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PurchSubsTypeConverter implements AttributeConverter<StatePurchSubsType, String>  {

    @Override
    public String convertToDatabaseColumn(StatePurchSubsType stateType) {
        return stateType == null ? null : stateType.name().toLowerCase();
    }

    @Override
    public StatePurchSubsType convertToEntityAttribute(String dbValue) {
        return dbValue == null ? null : StatePurchSubsType.valueOf(dbValue.toUpperCase());
    }
}
