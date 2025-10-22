package io.hexlet.cv.service;

import io.hexlet.cv.dto.marketing.ReviewCreateDTO;
import io.hexlet.cv.dto.marketing.ReviewDTO;
import io.hexlet.cv.dto.marketing.ReviewUpdateDTO;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.mapper.ReviewMapper;
import io.hexlet.cv.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(reviewMapper::map)
                .collect(Collectors.toList());
    }

    public ReviewDTO getReviewById(Long id) {
        var review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        return reviewMapper.map(review);
    }

    public ReviewDTO getPublishedReviewById(Long id) {
        var review = reviewRepository.findByIdAndIsPublishedTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Published review not found"));
        return reviewMapper.map(review);
    }

    @Transactional
    public ReviewDTO createReview(ReviewCreateDTO createDTO) {
        var review = reviewMapper.map(createDTO);

        if (createDTO.getIsPublished()) {
            review.setPublishedAt(LocalDateTime.now());
        }

        var savedReview = reviewRepository.save(review);
        return reviewMapper.map(savedReview);
    }

    @Transactional
    public ReviewDTO updateReview(Long id, ReviewUpdateDTO updateDTO) {
        var review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        if (updateDTO.getIsPublished() != null) {
            var newStatus = updateDTO.getIsPublished();
            review.setIsPublished(newStatus);

            if (newStatus && review.getPublishedAt() == null) {
                review.setPublishedAt(LocalDateTime.now());
            } else if (!newStatus) {
                review.setPublishedAt(null);
            }
        }

        reviewMapper.update(updateDTO, review);
        var savedReview = reviewRepository.save(review);
        return reviewMapper.map(savedReview);
    }

    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Transactional
    public ReviewDTO togglePublish(Long id) {
        var review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        var newStatus = !review.getIsPublished();
        review.setIsPublished(newStatus);

        if (newStatus && review.getPublishedAt() == null) {
            review.setPublishedAt(LocalDateTime.now());
        } else if (!newStatus) {
            review.setPublishedAt(null);
        }

        var savedReview = reviewRepository.save(review);
        return reviewMapper.map(savedReview);
    }

    public List<ReviewDTO> getHomepageReviews() {
        return reviewRepository.findByShowOnHomepageTrueOrderByDisplayOrderAsc()
                .stream()
                .map(reviewMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateReviewDisplayOrder(Long id, Integer displayOrder) {
        var review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        review.setDisplayOrder(displayOrder);
        reviewRepository.save(review);
    }

    @Transactional
    public void toggleReviewHomepageVisibility(Long id) {
        var review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        review.setShowOnHomepage(!review.getShowOnHomepage());
        reviewRepository.save(review);
    }
}
