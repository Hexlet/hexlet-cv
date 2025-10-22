package io.hexlet.cv.repository;

import io.hexlet.cv.model.ResumeAnswerComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeAnswerCommentRepository extends JpaRepository<ResumeAnswerComment, Long> {
    int countByUserId(Long userId);
    List<ResumeAnswerComment> findByUserId(Long resumeId);
}
