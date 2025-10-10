package io.hexlet.cv.repository;

import io.hexlet.cv.model.PageSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageSectionRepository extends JpaRepository<PageSection, Long> {
    Optional<PageSection> findBySectionKey(String sectionKey);
    Optional<PageSection> findByPageKeyAndSectionKey(String pageKey, String sectionKey);
    List<PageSection> findByPageKey(String key);
    List<PageSection> findByPageKeyAndActive(String key, Boolean isActive);
    List<PageSection> findByActive(Boolean isActive);
    boolean existsByPageKeyAndSectionKey(String pageKey, String sectionKey);
    void deleteByPageKeyAndSectionKey(String pageKey, String sectionKey);
}
