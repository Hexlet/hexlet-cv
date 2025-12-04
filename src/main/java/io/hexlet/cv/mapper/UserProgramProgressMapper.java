package io.hexlet.cv.mapper;

import io.hexlet.cv.dto.learning.UserProgramProgressDTO;
import io.hexlet.cv.model.learning.UserProgramProgress;
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
public abstract class UserProgramProgressMapper {

    @Mapping(target = "programTitle", source = "program.title")
    @Mapping(target = "lastLessonTitle", source = "lastLesson.title")
    @Mapping(target = "totalLessons", ignore = true)
    @Mapping(target = "progressPercentage", ignore = true)
    @Mapping(target = "completedLessons", ignore = true)
    public abstract UserProgramProgressDTO toDto(UserProgramProgress progress);
}
