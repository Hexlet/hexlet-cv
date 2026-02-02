package io.hexlet.cv.mapper;

import io.hexlet.cv.dto.knowledge.KnowledgeArticleDto;
import io.hexlet.cv.dto.knowledge.KnowledgeInterviewDto;
import io.hexlet.cv.model.knowledge.KnowledgeArticle;
import io.hexlet.cv.model.knowledge.KnowledgeInterview;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class KnowledgeMapper {
    public abstract KnowledgeArticleDto toArticleDTO(KnowledgeArticle article);

    public abstract KnowledgeInterviewDto toInterviewDTO(KnowledgeInterview interview);
}
