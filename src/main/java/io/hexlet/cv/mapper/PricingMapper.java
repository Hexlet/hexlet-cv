package io.hexlet.cv.mapper;

import io.hexlet.cv.dto.marketing.PricingCreateDto;
import io.hexlet.cv.dto.marketing.PricingDto;
import io.hexlet.cv.dto.marketing.PricingUpdateDto;
import io.hexlet.cv.model.admin.marketing.PricingPlan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class PricingMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "finalPrice", ignore = true)
    public abstract PricingPlan map(PricingCreateDto dto);

    public abstract PricingDto map(PricingPlan model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "finalPrice", ignore = true)
    public abstract void update(PricingUpdateDto dto, @MappingTarget PricingPlan model);
}
