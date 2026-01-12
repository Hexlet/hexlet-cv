package io.hexlet.cv.service;

import io.hexlet.cv.dto.knowledge.KnowledgeArticleDto;
import io.hexlet.cv.dto.knowledge.KnowledgeInterviewDto;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.mapper.KnowledgeMapper;
import io.hexlet.cv.repository.KnowledgeArticleRepository;
import io.hexlet.cv.repository.KnowledgeInterviewRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class KnowledgeService {
    private final KnowledgeInterviewRepository knowledgeInterviewRepository;
    private final KnowledgeArticleRepository knowledgeArticleRepository;
    private final KnowledgeMapper knowledgeMapper;

    @Transactional
    public Page<KnowledgeArticleDto> getArticles(String category, Pageable pageable) {
        if (category != null && !category.trim().isEmpty()) {
            log.debug("Getting articles by category: {}, page: {}", category, pageable.getPageNumber());
            return knowledgeArticleRepository.findByIsPublishedTrueAndCategoryOrderByPublishedAtDesc(
                    category, pageable
            ).map(knowledgeMapper::toArticleDTO);
        } else {
            log.debug("Getting all articles, page: {}", pageable.getPageNumber());
            return knowledgeArticleRepository.findByIsPublishedTrueOrderByPublishedAtDesc(pageable)
                    .map(knowledgeMapper::toArticleDTO);
        }
    }

    @Transactional
    public KnowledgeArticleDto getArticleById(Long id) {
        log.debug("Getting article by id: {}", id);
        return knowledgeArticleRepository.findByIdAndIsPublishedTrue(id)
                .map(knowledgeMapper::toArticleDTO)
                .orElseThrow(() -> new ResourceNotFoundException("article.not.found.or.not.public"));
    }

    @Transactional
    public List<KnowledgeArticleDto> getRecentArticles(int limit) {
        log.debug("Getting {} recent articles", limit);
        return knowledgeArticleRepository.findByIsPublishedTrueOrderByPublishedAtDesc()
                .stream()
                .limit(limit)
                .map(knowledgeMapper::toArticleDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Page<KnowledgeInterviewDto> getInterviews(String category, Pageable pageable) {
        if (category != null && !category.trim().isEmpty()) {
            log.debug("Getting interviews by category: {}, page: {}", category, pageable.getPageNumber());
            return knowledgeInterviewRepository.findByIsPublishedTrueAndCategoryOrderByPublishedAtDesc(
                    category, pageable
            ).map(knowledgeMapper::toInterviewDTO);
        } else {
            log.debug("Getting all interviews, page: {}", pageable.getPageNumber());
            return knowledgeInterviewRepository.findByIsPublishedTrueOrderByPublishedAtDesc(pageable)
                    .map(knowledgeMapper::toInterviewDTO);
        }
    }

    @Transactional
    public KnowledgeInterviewDto getInterviewById(Long id) {
        log.debug("Getting interview by id: {}", id);
        return knowledgeInterviewRepository.findByIdAndIsPublishedTrue(id)
                .map(knowledgeMapper::toInterviewDTO)
                .orElseThrow(() -> new ResourceNotFoundException("interview.not.found.or.not.public"));
    }

    @Transactional
    public List<KnowledgeInterviewDto> getRecentInterviews(int limit) {
        log.debug("Getting {} recent interviews", limit);
        return knowledgeInterviewRepository.findByIsPublishedTrueOrderByPublishedAtDesc()
                .stream()
                .limit(limit)
                .map(knowledgeMapper::toInterviewDTO)
                .collect(Collectors.toList());
    }
}
