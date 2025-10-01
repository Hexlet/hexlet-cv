package io.hexlet.cv.repository;

import io.hexlet.cv.model.Impression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImpressionRepository extends JpaRepository<Impression, Long> {

    Long countByImpressionableTypeAndImpressionableId(String impressionableType, Long impressionableId);
}
