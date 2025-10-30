package io.hexlet.cv.repository;

import io.hexlet.cv.model.marketing.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByOrderByCreatedAtDesc();

    List<Review> findByShowOnHomepageTrueOrderByDisplayOrderAsc();

    Optional<Review> findByIdAndIsPublishedTrue(Long id);
}
