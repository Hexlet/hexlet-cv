package io.hexlet.cv.repository;

import io.hexlet.cv.model.ResumeAnswer;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeAnswerRepository extends JpaRepository<ResumeAnswer, Long>  {
    int countByUserId(Long userId);
    List<ResumeAnswer> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
