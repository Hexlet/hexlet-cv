package io.hexlet.cv.mapper;

import io.hexlet.cv.dto.marketing.ReviewCreateDTO;
import io.hexlet.cv.dto.marketing.ReviewDTO;
import io.hexlet.cv.dto.marketing.ReviewUpdateDTO;
import io.hexlet.cv.model.marketing.Review;
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
public abstract class ReviewMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Review map(ReviewCreateDTO dto);

    @Mapping(source = "author", target = "author")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "avatarUrl", target = "avatarUrl")
    @Mapping(source = "isPublished", target = "isPublished")
    @Mapping(source = "showOnHomepage", target = "showOnHomepage")
    @Mapping(source = "displayOrder", target = "displayOrder")
    @Mapping(source = "publishedAt", target = "publishedAt")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    public abstract ReviewDTO map(Review model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Review map(ReviewDTO model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract void update(ReviewUpdateDTO dto, @MappingTarget Review model);
}
