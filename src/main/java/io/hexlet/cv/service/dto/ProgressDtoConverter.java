package io.hexlet.cv.service.dto;

import io.hexlet.cv.dto.learning.UserProgramProgressDTO;
import io.hexlet.cv.mapper.UserProgramProgressMapper;
import io.hexlet.cv.model.learning.UserProgramProgress;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ProgressDtoConverter {

    private final UserProgramProgressMapper userProgramProgressMapper;

    public UserProgramProgressDTO convert(UserProgramProgress progress) {
        if (progress == null) {
            log.debug("Attempt to convert null progress entity");
            return null;
        }
        log.debug("Converting progress entity (id={}) to DTO", progress.getId());

        UserProgramProgressDTO dto = userProgramProgressMapper.toDto(progress);

        enrichTheCalculations(progress, dto);
        return dto;
    }

    private void enrichTheCalculations(UserProgramProgress progress, UserProgramProgressDTO dto) {

        int totalLessons = calculateTotalLessons(progress);
        int completedLessons = progress.getCompletedLessons();

        dto.setTotalLessons(totalLessons);
        dto.setCompletedLessons(completedLessons);
        dto.setProgressPercentage(calculateProgressPercentage(completedLessons, totalLessons));
    }

    private int calculateTotalLessons(UserProgramProgress progress) {
        return Optional.ofNullable(progress.getProgram())
                .flatMap(program -> Optional.ofNullable(program.getLessons()))
                .map(List::size)
                .orElse(0);
    }

    private int calculateProgressPercentage(int completedLessons, int totalLessons) {
        if (totalLessons == 0) {
            log.debug("Total lessons is 0, cannot calculate progress percentage");
            return 0;
        }

        int percentage = (completedLessons * 100) / totalLessons;
        log.trace("Progress percentage {}% ({} of {}",
                percentage, completedLessons, totalLessons);
        return percentage;
    }
}
