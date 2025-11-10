package io.hexlet.cv.repository;

import io.hexlet.cv.model.Tagging;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaggingRepository extends JpaRepository<Tagging, Long> {
    List<Tagging> findByTaggableTypeAndTaggableId(String taggableType, Long taggableId);
}
