package io.hexlet.cv.repository;

import io.hexlet.cv.model.CareerMemberVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerMemberVersionRepository extends JpaRepository<CareerMemberVersion, Long> {
}
