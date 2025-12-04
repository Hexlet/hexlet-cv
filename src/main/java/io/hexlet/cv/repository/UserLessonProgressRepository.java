package io.hexlet.cv.repository;

import io.hexlet.cv.model.learning.UserLessonProgress;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLessonProgressRepository extends JpaRepository<UserLessonProgress, Long> {

    Page<UserLessonProgress> findByUserIdAndProgramProgressId(
            Long userId, Long programProgressId, Pageable pageable);

    Optional<UserLessonProgress> findByUserIdAndLessonId(Long userId, Long lessonId);
    @Query("""
            SELECT COUNT(ulp)
            FROM UserLessonProgress ulp
            WHERE ulp.programProgress.id = :programProgressId AND ulp.isCompleted = true
            """)
    Long countCompletedLessonsByProgramProgressId(@Param("programProgressId") Long programProgressId);
}
