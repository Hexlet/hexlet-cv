package io.hexlet.cv.repository;

import io.hexlet.cv.model.marketing.Article;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByOrderByCreatedAtDesc();

    List<Article> findByShowOnHomepageTrueOrderByDisplayOrderAsc();
}
