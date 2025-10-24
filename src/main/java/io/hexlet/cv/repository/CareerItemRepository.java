package io.hexlet.cv.repository;

import io.hexlet.cv.model.CareerItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerItemRepository extends JpaRepository<CareerItem, Long> {
    int countByCareerId(Long careerId);
}
