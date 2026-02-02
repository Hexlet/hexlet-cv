package io.hexlet.cv.repository;

import io.hexlet.cv.model.knowledge.KnowledgeArticle;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KnowledgeArticleRepository extends JpaRepository<KnowledgeArticle, Long> {
    Page<KnowledgeArticle> findByIsPublishedTrueOrderByPublishedAtDesc(Pageable pageable);

    Page<KnowledgeArticle> findByIsPublishedTrueAndCategoryOrderByPublishedAtDesc(String category, Pageable pageable);

    Optional<KnowledgeArticle> findByIdAndIsPublishedTrue(Long id);

    List<KnowledgeArticle> findByIsPublishedTrueOrderByPublishedAtDesc();
}
