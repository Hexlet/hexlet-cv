package io.hexlet.cv.repository;

import io.hexlet.cv.model.marketing.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByOrderByCreatedAtDesc();

    List<Article> findByShowOnHomepageTrueOrderByDisplayOrderAsc();

    List<Article> findByHomeComponentIdOrderByDisplayOrderAsc(String homeComponentId);
}
