package io.hexlet.cv.mapper;


import io.hexlet.cv.dto.marketing.StoryCreateDTO;
import io.hexlet.cv.dto.marketing.StoryDTO;
import io.hexlet.cv.dto.marketing.StoryUpdateDTO;
import io.hexlet.cv.model.admin.marketing.Story;
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
public abstract class StoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    public abstract Story map(StoryCreateDTO dto);

    @Mapping(source = "imageUrl", target = "imageUrl")
    @Mapping(source = "isPublished", target = "isPublished")
    @Mapping(source = "showOnHomepage", target = "showOnHomepage")
    @Mapping(source = "displayOrder", target = "displayOrder")
    @Mapping(source = "publishedAt", target = "publishedAt")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updateAt", target = "updatedAt")
    public abstract StoryDTO map(Story model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    public abstract Story map(StoryDTO model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    public abstract void update(StoryUpdateDTO dto, @MappingTarget Story model);

}
