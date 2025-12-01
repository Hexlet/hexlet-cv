package io.hexlet.cv.mapper;

import io.hexlet.cv.dto.account.PurchSubsDTO;
import io.hexlet.cv.model.account.PurchaseAndSubscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        uses = { JsonNullableMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class PurchSubsMapper {
    @Mapping(target = "userId", source = "user.id")
    public abstract PurchSubsDTO toDto(PurchaseAndSubscription entity);
}
