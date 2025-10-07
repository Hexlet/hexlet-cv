package io.hexlet.cv.mapper;

import io.hexlet.cv.dto.pagesection.PageSectionCreateDTO;
import io.hexlet.cv.dto.pagesection.PageSectionDTO;
import io.hexlet.cv.dto.pagesection.PageSectionUpdateDTO;
import io.hexlet.cv.model.PageSection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
    uses = { JsonNullableMapper.class },
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class PageSectionMapper {

    @Mapping(target = "active", expression = "java(true)")
    public abstract PageSection map(PageSectionCreateDTO dto);

    public abstract PageSectionDTO map(PageSection model);
    public abstract void update(PageSectionUpdateDTO dto, @MappingTarget PageSection model);
}
