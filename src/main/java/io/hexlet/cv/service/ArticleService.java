package io.hexlet.cv.service;

import io.hexlet.cv.dto.marketing.ArticleCreateDto;
import io.hexlet.cv.dto.marketing.ArticleDto;
import io.hexlet.cv.dto.marketing.ArticleUpdateDto;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.mapper.ArticleMapper;
import io.hexlet.cv.model.admin.marketing.Article;
import io.hexlet.cv.repository.ArticleRepository;
import io.hexlet.cv.util.JsonNullableUtils;
import jakarta.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final Clock clock;

    public Page<ArticleDto> getAllArticles(Pageable pageable) {
        log.debug("Getting all articles with pageable: {}", pageable);
        return articleRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(articleMapper::map);
    }

    public ArticleDto getArticleById(Long id) {
        var article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("article.not.found"));
        return articleMapper.map(article);
    }

    @Transactional
    public ArticleDto createArticle(ArticleCreateDto createDTO) {
        log.debug("Creating article: {}", createDTO.getTitle());
        var article = articleMapper.map(createDTO);

        if (createDTO.getIsPublished()) {
            article.setPublishedAt(LocalDateTime.now(clock));
        }

        var savedArticle = articleRepository.save(article);
        return articleMapper.map(savedArticle);
    }

    @Transactional
    public ArticleDto updateArticle(Long id, ArticleUpdateDto updateDTO) {
        log.debug("Updating article id: {}", id);
        var article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("article.not.found"));

        JsonNullableUtils.ifPresent(updateDTO.getIsPublished(), newStatus -> {
            article.setIsPublished(newStatus);

            if (Boolean.TRUE.equals(newStatus) && article.getPublishedAt() == null) {
                article.setPublishedAt(LocalDateTime.now(clock));
            } else if (Boolean.FALSE.equals(newStatus)) {
                article.setPublishedAt(null);
            }
        });

        articleMapper.update(updateDTO, article);
        var savedArticle = articleRepository.save(article);

        return articleMapper.map(savedArticle);
    }

    @Transactional
    public void deleteArticle(Long id) {
        log.debug("Deleting article id: {}", id);
        articleRepository.deleteById(id);

    }
    @Transactional
    public ArticleDto togglePublish(Long id) {
        log.debug("Toggling publish for article id: {}", id);
        var article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("article.not.found"));

        var newStatus = !article.getIsPublished();
        article.setIsPublished(newStatus);

        if (newStatus && article.getPublishedAt() == null) {
            article.setPublishedAt(LocalDateTime.now(clock));
        }

        var savedArticle = articleRepository.save(article);
        return articleMapper.map(savedArticle);
    }

    public List<ArticleDto> getHomepageArticles() {
        log.debug("Getting homepage articles");
        return articleRepository.findByShowOnHomepageTrue(
                Sort.by(Sort.Direction.DESC, Article.FIELD_CREATED_AT))
                .stream()
                .map(articleMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateArticleDisplayOrder(Long id, Integer displayOrder) {
        log.debug("Updating display order for article id: {} to {}", id, displayOrder);
        var article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("article.not.found"));
        article.setDisplayOrder(displayOrder);
        articleRepository.save(article);
    }

    @Transactional
    public void toggleArticleHomepageVisibility(Long id) {
        log.debug("Toggling homepage visibility for article id: {}", id);
        var article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("article.not.found"));
        article.setShowOnHomepage(!article.getShowOnHomepage());
        articleRepository.save(article);
    }
}
