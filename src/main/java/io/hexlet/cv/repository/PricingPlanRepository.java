package io.hexlet.cv.repository;

import io.hexlet.cv.model.admin.marketing.PricingPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingPlanRepository extends JpaRepository<PricingPlan, Long> {
    Page<PricingPlan> findAllByOrderByIdAsc(Pageable pageable);

    boolean existsByName(String name);
}
