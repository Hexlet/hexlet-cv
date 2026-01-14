package io.hexlet.cv.repository;

import io.hexlet.cv.model.knowledge.KnowledgeInterview;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KnowledgeInterviewRepository extends JpaRepository<KnowledgeInterview, Long> {
    Page<KnowledgeInterview> findByIsPublishedTrueOrderByPublishedAtDesc(Pageable pageable);

    Page<KnowledgeInterview> findByIsPublishedTrueAndCategoryOrderByPublishedAtDesc(String category, Pageable pageable);

    Optional<KnowledgeInterview> findByIdAndIsPublishedTrue(Long id);

    List<KnowledgeInterview> findByIsPublishedTrueOrderByPublishedAtDesc();
}
