package io.hexlet.cv.service;

import io.hexlet.cv.dto.learning.UserProgramProgressDTO;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.mapper.UserProgramProgressMapper;
import io.hexlet.cv.model.learning.UserProgramProgress;
import io.hexlet.cv.repository.ProgramRepository;
import io.hexlet.cv.repository.UserProgramProgressRepository;
import io.hexlet.cv.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserProgramProgressService {

    private final UserProgramProgressRepository programProgressRepository;
    private final UserProgramProgressMapper programProgressMapper;
    private final UserRepository userRepository;
    private final ProgramRepository programRepository;

    public List<UserProgramProgressDTO> getUserProgress(Long userId) {
        List<UserProgramProgress> progressList = programProgressRepository.findByUserId(userId);

        return progressList.stream()
                .map(progress -> {
                    UserProgramProgressDTO dto = programProgressMapper.toDTO(progress);
                    dto.setProgressPercentage(calculateProgressPercentage(progress));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void startProgram(Long userId, Long programId) {
        if (programProgressRepository.findByUserIdAndProgramId(userId, programId).isPresent()) {
            return;
        }

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var program = programRepository.findById(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found"));

        var progress = new UserProgramProgress();
        progress.setUser(user);
        progress.setProgram(program);
        progress.setStartedAt(LocalDateTime.now());
        progress.setLastActivityAt(LocalDateTime.now());
        progress.setCompletedLessons(0);
        progress.setIsCompleted(false);

        programProgressRepository.save(progress);
    }

    @Transactional
    public void completeProgram(Long progressId) {
        var progress = programProgressRepository.findById(progressId)
                .orElseThrow(() -> new ResourceNotFoundException("Program progress not found"));

        progress.setCompletedLessons(progress.getProgram().getLessons().size());
        progress.setIsCompleted(true);
        progress.setCompletedAt(LocalDateTime.now());
        progress.setLastActivityAt(LocalDateTime.now());

        programProgressRepository.save(progress);
    }

    private Integer calculateProgressPercentage(UserProgramProgress progress) {
        if (progress.getProgram() == null || progress.getProgram().getLessons().isEmpty()) {
            return 0;
        }
        int totalLessons = progress.getProgram().getLessons().size();
        int completedLessons = progress.getCompletedLessons();

        return (completedLessons * 100) / totalLessons;
    }
}
