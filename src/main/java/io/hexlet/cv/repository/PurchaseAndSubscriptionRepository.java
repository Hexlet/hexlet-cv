package io.hexlet.cv.repository;

import io.hexlet.cv.model.account.PurchaseAndSubscription;
import io.hexlet.cv.model.enums.ProductType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseAndSubscriptionRepository extends JpaRepository<PurchaseAndSubscription, Long> {

    Page<PurchaseAndSubscription> findByUserId(Long userId, Pageable pageable);

    Optional<PurchaseAndSubscription> findFirstByUserId(Long userId);

    Page<PurchaseAndSubscription> findByUserIdAndProductType(Long userId, ProductType productType, Pageable pageable);

    Optional<Object> findByUserIdAndProductTypeAndReferenceId(Long userId, ProductType productType, Long webinarId);
}
