package io.hexlet.cv.repository;

import io.hexlet.cv.model.admin.marketing.Review;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Review> findByShowOnHomepageTrue(Sort sort);
}
