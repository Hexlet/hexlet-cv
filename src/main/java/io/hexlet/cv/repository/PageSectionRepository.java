package io.hexlet.cv.repository;

import io.hexlet.cv.model.PageSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PageSectionRepository extends JpaRepository<PageSection, Long> {
    Optional<PageSection> findBySectionKey(String key);
}