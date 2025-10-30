package io.hexlet.cv.repository;

import io.hexlet.cv.model.admin.marketing.PricingPlan;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingPlanRepository extends JpaRepository<PricingPlan, Long> {
    List<PricingPlan> findAllByOrderByIdAsc();

    boolean existsByName(String name);

    Optional<PricingPlan> findByName(String name);
}
