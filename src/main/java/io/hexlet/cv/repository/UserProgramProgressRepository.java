package io.hexlet.cv.repository;

import io.hexlet.cv.model.learning.UserProgramProgress;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProgramProgressRepository extends JpaRepository<UserProgramProgress, Long> {
    List<UserProgramProgress> findByUserId(Long userId);

    Optional<UserProgramProgress> findByUserIdAndProgramId(Long userId, Long programId);
}
