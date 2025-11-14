package io.hexlet.cv.repository;

import io.hexlet.cv.model.learning.UserLessonProgress;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLessonProgressRepository extends JpaRepository<UserLessonProgress, Long> {
    List<UserLessonProgress> findByUserId(Long userId);

    Optional<UserLessonProgress> findByUserIdAndLessonId(Long userId, Long lessonId);

    List<UserLessonProgress> findByProgramProgressId(Long programProgressId);

    @Query("SELECT COUNT(ulp) FROM UserLessonProgress ulp WHERE ulp.programProgress.id "
            + "= :programProgressId AND ulp.isCompleted = true")
    Long countCompletedLessonsByProgramProgressId(@Param("programProgressId") Long programProgressId);
}
