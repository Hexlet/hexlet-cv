package io.hexlet.cv.repository;

import io.hexlet.cv.model.marketing.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    List<Story> findAllByOrderByCreatedAtDesc();

    List<Story> findByShowOnHomepageTrueOrderByDisplayOrderAsc();

    Optional<Story> findByIdAndIsPublishedTrue(Long id);
}
