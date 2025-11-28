package io.hexlet.cv.mapper;

import io.hexlet.cv.dto.admin.WebinarDTO;
import io.hexlet.cv.model.webinars.Webinar;
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
public abstract class AdminWebinarMapper {

    public abstract Webinar map(WebinarDTO dto);
    public abstract WebinarDTO map(Webinar webinar);

    @Mapping(target = "id", ignore = true)
    public abstract void update(WebinarDTO dto, @MappingTarget Webinar model);
}
