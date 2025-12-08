package io.hexlet.cv.converter;

import io.hexlet.cv.dto.learning.UserLessonProgressDTO;
import io.hexlet.cv.dto.learning.UserProgramProgressDTO;
import io.hexlet.cv.model.learning.UserLessonProgress;
import io.hexlet.cv.model.learning.UserProgramProgress;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ProgressDtoConverter {

    public UserProgramProgressDTO convertProgramProgress(UserProgramProgress progress) {
        if (progress == null) {
            log.debug("Attempt to convert null progress entity");
            return null;
        }
        log.debug("Converting progress entity (id={}) to DTO", progress.getId());

        int totalLessons = calculateTotalLessons(progress);

        return UserProgramProgressDTO.builder()
                        .id(progress.getId())
                .completedLessons(progress.getCompletedLessons())
                .isCompleted(progress.getIsCompleted())
                .startedAt(progress.getStartedAt())
                .lastActivityAt(progress.getLastActivityAt())
                .totalLessons(totalLessons)
                .programTitle(progress.getProgram() != null ? progress.getProgram().getTitle() : null)
                .lastLessonTitle(progress.getLastLesson() != null ? progress.getLastLesson().getTitle() : null)
                .progressPercentage(calculateProgressPercentage(
                        progress.getCompletedLessons(),
                        totalLessons
                ))
                .build();
    }

    public UserLessonProgressDTO convertLessonProgress(UserLessonProgress progress) {
        if (progress == null) {
            log.debug("Attempt to convert null lesson progress entity");
            return null;
        }
        log.debug("Converting lesson progress entity (id={}) to DTO", progress.getId());

        return UserLessonProgressDTO.builder()
                .id(progress.getId())
                .isCompleted(progress.getIsCompleted())
                .startedAt(progress.getStartedAt())
                .completedAt(progress.getCompletedAt())
                .timeSpentMinutes(progress.getTimeSpentMinutes())
                .lessonId(progress.getLesson() != null ? progress.getLesson().getId() : null)
                .programProgressId(progress.getProgramProgress() != null
                        ? progress.getProgramProgress().getId() : null)
                .userId(progress.getUser() != null ? progress.getUser().getId() : null)
                .lessonTitle(progress.getLesson() != null ? progress.getLesson().getTitle() : null)
                .build();
    }

    private int calculateTotalLessons(UserProgramProgress progress) {
        if (progress.getProgram() == null || progress.getProgram().getLessons() == null) {
            return 0;
        }
        return progress.getProgram().getLessons().size();
    }

    private int calculateProgressPercentage(int completedLessons, int totalLessons) {
        if (totalLessons == 0) {
            log.debug("Total lessons is 0, cannot calculate progress percentage");
            return 0;
        }

        int percentage = (completedLessons * 100) / totalLessons;
        log.trace("Progress percentage {}% ({} of {})",
                percentage, completedLessons, totalLessons);
        return percentage;
    }
}
