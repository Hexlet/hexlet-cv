package io.hexlet.cv.mapper;

import io.hexlet.cv.dto.marketing.PricingCreateDTO;
import io.hexlet.cv.dto.marketing.PricingDTO;
import io.hexlet.cv.dto.marketing.PricingUpdateDTO;
import io.hexlet.cv.model.marketing.PricingPlan;
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
    public abstract PricingPlan map(PricingCreateDTO dto);

    public abstract PricingDTO map(PricingPlan model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "finalPrice", ignore = true)
    public abstract void update(PricingUpdateDTO dto, @MappingTarget PricingPlan model);
}
