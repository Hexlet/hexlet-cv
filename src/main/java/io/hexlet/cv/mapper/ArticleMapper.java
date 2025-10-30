package io.hexlet.cv.mapper;

import io.hexlet.cv.dto.marketing.ArticleCreateDTO;
import io.hexlet.cv.dto.marketing.ArticleDTO;
import io.hexlet.cv.dto.marketing.ArticleUpdateDTO;
import io.hexlet.cv.model.marketing.Article;
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
public abstract class ArticleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Article map(ArticleCreateDTO dto);

    @Mapping(source = "imageUrl", target = "imageUrl")
    @Mapping(source = "readingTime", target = "readingTime")
    @Mapping(source = "isPublished", target = "isPublished")
    @Mapping(source = "homeComponentId", target = "homeComponentId")
    @Mapping(source = "displayOrder", target = "displayOrder")
    @Mapping(source = "publishedAt", target = "publishedAt")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    public abstract ArticleDTO map(Article model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Article map(ArticleDTO model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract void update(ArticleUpdateDTO dto, @MappingTarget Article model);
}
