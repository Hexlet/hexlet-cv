package io.hexlet.cv.service;

import io.hexlet.cv.dto.marketing.ReviewCreateDto;
import io.hexlet.cv.dto.marketing.ReviewDto;
import io.hexlet.cv.dto.marketing.ReviewUpdateDto;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.mapper.ReviewMapper;
import io.hexlet.cv.model.admin.marketing.Review;
import io.hexlet.cv.repository.ReviewRepository;
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
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final Clock clock;

    public Page<ReviewDto> getAllReviews(Pageable pageable) {
        log.debug("Getting all reviews with pageable: {}", pageable);
        return reviewRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(reviewMapper::map);
    }

    public ReviewDto getReviewById(Long id) {
        var review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("review.not.found"));
        return reviewMapper.map(review);
    }

    @Transactional
    public ReviewDto createReview(ReviewCreateDto createDTO) {
        log.debug("Creating review: {}", createDTO.getAuthor());
        var review = reviewMapper.map(createDTO);

        if (createDTO.getIsPublished()) {
            review.setPublishedAt(LocalDateTime.now(clock));
        }

        var savedReview = reviewRepository.save(review);
        return reviewMapper.map(savedReview);
    }

    @Transactional
    public ReviewDto updateReview(Long id, ReviewUpdateDto updateDTO) {
        log.debug("Updating review id: {}", id);
        var review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("review.not.found"));

        JsonNullableUtils.ifPresent(updateDTO.getIsPublished(), newStatus -> {
            review.setIsPublished(newStatus);

            if (Boolean.TRUE.equals(newStatus) && review.getPublishedAt() == null) {
                review.setPublishedAt(LocalDateTime.now(clock));
            } else if (Boolean.FALSE.equals(newStatus)) {
                review.setPublishedAt(null);
            }
        });

        reviewMapper.update(updateDTO, review);
        var savedReview = reviewRepository.save(review);
        return reviewMapper.map(savedReview);
    }

    @Transactional
    public void deleteReview(Long id) {
        log.debug("Deleting review id: {}", id);
        reviewRepository.deleteById(id);
    }

    @Transactional
    public ReviewDto togglePublish(Long id) {
        log.debug("Toggling publish for review id: {}", id);
        var review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("review.not.found"));

        var newStatus = !review.getIsPublished();
        review.setIsPublished(newStatus);

        if (newStatus && review.getPublishedAt() == null) {
            review.setPublishedAt(LocalDateTime.now(clock));
        } else if (!newStatus) {
            review.setPublishedAt(null);
        }

        var savedReview = reviewRepository.save(review);
        return reviewMapper.map(savedReview);
    }

    public List<ReviewDto> getHomepageReviews() {
        return reviewRepository.findByShowOnHomepageTrue(
                Sort.by(Sort.Direction.DESC, Review.FIELD_CREATED_AT)
                )
                .stream()
                .map(reviewMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateReviewDisplayOrder(Long id, Integer displayOrder) {
        log.debug("Updating display order for review id: {} to {}", id, displayOrder);
        var review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("review.not.found"));
        review.setDisplayOrder(displayOrder);
        reviewRepository.save(review);
    }

    @Transactional
    public void toggleReviewHomepageVisibility(Long id) {
        log.debug("Toggling homepage visibility for review id: {}", id);
        var review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("review.not.found"));
        review.setShowOnHomepage(!review.getShowOnHomepage());
        reviewRepository.save(review);
    }
}
