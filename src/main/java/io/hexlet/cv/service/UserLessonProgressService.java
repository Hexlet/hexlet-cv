package io.hexlet.cv.service;

import io.hexlet.cv.dto.learning.UserLessonProgressDTO;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.mapper.UserLessonProgressMapper;
import io.hexlet.cv.model.learning.UserLessonProgress;
import io.hexlet.cv.repository.LessonRepository;
import io.hexlet.cv.repository.UserLessonProgressRepository;
import io.hexlet.cv.repository.UserProgramProgressRepository;
import io.hexlet.cv.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserLessonProgressService {
    private static final String LESSON_NOT_FOUND = "Урок не найден с id: ";
    private static final String USER_NOT_FOUND = "Пользователь не найден с id: ";
    private static final String PROGRESS_NOT_FOUND = "Прогресс не найден с id: ";

    private final UserLessonProgressRepository userLessonProgressRepository;
    private final UserProgramProgressRepository userProgramProgressRepository;
    private final UserLessonProgressMapper userLessonProgressMapper;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    public Page<UserLessonProgressDTO> getLessonProgress(Long userId, Long programProgressId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("startedAt").descending());
        Page<UserLessonProgress> progressPage = userLessonProgressRepository
                .findByProgramProgressId(programProgressId, pageable);

        return progressPage.map(userLessonProgressMapper::toDTO);
    }

    @Transactional
    public void startLesson(Long userId, Long programProgressId, Long lessonId) {
        if (userLessonProgressRepository.findByUserIdAndLessonId(userId, lessonId).isPresent()) {
            return;
        }

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + userId));

        var lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(LESSON_NOT_FOUND + lessonId));
        var programProgress = userProgramProgressRepository.findById(programProgressId)
                .orElseThrow(() -> new ResourceNotFoundException("Прогресс программы не найден с id: "
                        + programProgressId));

        var progress = new UserLessonProgress();
        progress.setUser(user);
        progress.setLesson(lesson);
        progress.setProgramProgress(programProgress);
        progress.setStartedAt(LocalDateTime.now());
        progress.setIsCompleted(false);
        progress.setTimeSpentMinutes(0);

        userLessonProgressRepository.save(progress);
    }

    @Transactional
    public void completeLesson(Long progressId) {
        var progress = userLessonProgressRepository.findById(progressId)
                .orElseThrow(() -> new ResourceNotFoundException(PROGRESS_NOT_FOUND + progressId));

        progress.setIsCompleted(true);
        progress.setCompletedAt(LocalDateTime.now());

        userLessonProgressRepository.save(progress);

        updateProgramProgress(progress.getProgramProgress().getId());
    }

    private void updateProgramProgress(Long programProgressId) {
        var programProgress = userProgramProgressRepository.findById(programProgressId)
                .orElseThrow(() -> new ResourceNotFoundException("Прогресс программы не найден: "
                        + programProgressId));

        Long completedLessonsCount =
                userLessonProgressRepository.countCompletedLessonsByProgramProgressId(programProgressId);

        programProgress.setCompletedLessons(completedLessonsCount.intValue());
        programProgress.setLastActivityAt(LocalDateTime.now());

        int totalLessons = programProgress.getProgram().getLessons().size();
        if (completedLessonsCount == totalLessons) {
            programProgress.setIsCompleted(true);
            programProgress.setCompletedAt(LocalDateTime.now());
        }

        userProgramProgressRepository.save(programProgress);
    }
}
