package io.hexlet.cv.repository;

import io.hexlet.cv.model.ResumeComment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeCommentRepository extends JpaRepository<ResumeComment, Long> {

    List<ResumeComment> findByResumeUserId(Long userId);

}
