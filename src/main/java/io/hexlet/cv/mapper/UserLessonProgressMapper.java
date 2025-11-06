package io.hexlet.cv.mapper;

import io.hexlet.cv.dto.learning.UserLessonProgressDTO;
import io.hexlet.cv.model.learning.UserLessonProgress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserLessonProgressMapper {

    @Mapping(target = "lessonTitle", source = "lesson.title")
    public abstract UserLessonProgressDTO toDTO(UserLessonProgress progress);
}
