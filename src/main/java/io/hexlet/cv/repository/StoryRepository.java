package io.hexlet.cv.repository;

import io.hexlet.cv.model.marketing.Story;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    List<Story> findAllByOrderByCreatedAtDesc();

    List<Story> findByShowOnHomepageTrueOrderByDisplayOrderAsc();

    Optional<Story> findByIdAndIsPublishedTrue(Long id);
}
