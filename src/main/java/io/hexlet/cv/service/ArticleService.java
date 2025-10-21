package io.hexlet.cv.service;

import io.hexlet.cv.dto.marketing.ArticleCreateDTO;
import io.hexlet.cv.dto.marketing.ArticleDTO;
import io.hexlet.cv.dto.marketing.ArticleUpdateDTO;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.mapper.ArticleMapper;
import io.hexlet.cv.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public List<ArticleDTO> getAllArticles() {
        return articleRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(articleMapper::map)
                .collect(Collectors.toList());
    }

    public ArticleDTO getArticleById(Long id) {
        var article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));
        return articleMapper.map(article);
    }

    @Transactional
    public ArticleDTO createArticle(ArticleCreateDTO createDTO) {
        var article = articleMapper.map(createDTO);

        if (createDTO.getIsPublished()) {
            article.setPublishedAt(LocalDateTime.now());
        }

        var savedArticle = articleRepository.save(article);
        return articleMapper.map(savedArticle);
    }

    @Transactional
    public ArticleDTO updateArticle(Long id, ArticleUpdateDTO updateDTO) {
        var article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        if (updateDTO.getIsPublished() != null && updateDTO.getIsPublished().isPresent()) {
            var newStatus = updateDTO.getIsPublished().get();
            article.setIsPublished(newStatus);

            if (newStatus && article.getPublishedAt() == null) {
                article.setPublishedAt(LocalDateTime.now());
            } else if (!newStatus) {
                article.setPublishedAt(null);
            }
        }
        articleMapper.update(updateDTO, article);
        var savedArticle = articleRepository.save(article);

        return articleMapper.map(savedArticle);
    }

    @Transactional
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);

    }
    @Transactional
    public ArticleDTO togglePublish(Long id) {
        var article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        var newStatus = !article.getIsPublished();
        article.setIsPublished(newStatus);

        if (newStatus && article.getPublishedAt() == null) {
            article.setPublishedAt(LocalDateTime.now());
        }

        var savedArticle = articleRepository.save(article);
        return articleMapper.map(savedArticle);
    }

    public List<ArticleDTO> getHomepageArticles() {
        return articleRepository.findByShowOnHomepageTrueOrderByDisplayOrderAsc()
                .stream()
                .map(articleMapper::map)
                .collect(Collectors.toList());
    }
}
