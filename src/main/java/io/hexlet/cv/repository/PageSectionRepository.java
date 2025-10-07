package io.hexlet.cv.repository;

import io.hexlet.cv.model.PageSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageSectionRepository extends JpaRepository<PageSection, Long> {
    Optional<PageSection> findByPageKeyAndSectionKey(String pageKey, String sectionKey);
    Optional<PageSection> findBySectionKey(String key);
    List<PageSection> findByPageKey(String key);
    List<PageSection> findByPageKeyAndActiveTrue(String key);
    boolean existsByPageKeyAndSectionKey(String pageKey, String sectionKey);
    void deleteByPageKeyAndSectionKey(String pageKey, String sectionKey);
}
