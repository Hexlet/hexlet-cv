package io.hexlet.cv.repository;

import io.hexlet.cv.model.ResumeAnswerLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


// лайки на ответы юзеров
@Repository
public interface ResumeAnswerLikeRepository extends JpaRepository<ResumeAnswerLike, Long> {
    int countByAnswerUserId(Long userId);
}
