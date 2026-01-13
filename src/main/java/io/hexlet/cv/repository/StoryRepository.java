package io.hexlet.cv.repository;

import io.hexlet.cv.model.admin.marketing.Story;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    Page<Story> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Story> findByShowOnHomepageTrue(Sort sort);
}
