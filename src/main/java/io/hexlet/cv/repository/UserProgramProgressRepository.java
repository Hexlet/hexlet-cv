package io.hexlet.cv.repository;

import io.hexlet.cv.model.learning.UserProgramProgress;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProgramProgressRepository extends JpaRepository<UserProgramProgress, Long> {

    Page<UserProgramProgress> findByUserId(Long userId, Pageable pageable);

    Optional<UserProgramProgress> findByUserIdAndProgramId(Long userId, Long programId);

    boolean existsByUserIdAndProgramId(Long userId, Long programId);
}
