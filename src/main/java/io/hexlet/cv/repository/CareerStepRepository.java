package io.hexlet.cv.repository;

import io.hexlet.cv.model.CareerStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerStepRepository extends JpaRepository<CareerStep, Long> {
}
