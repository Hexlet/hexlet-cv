package io.hexlet.cv.repository;

import io.hexlet.cv.model.account.PurchaseAndSubscription;
import io.hexlet.cv.model.enums.ProductType;
import io.hexlet.cv.model.webinars.Webinar;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseAndSubscriptionRepository extends JpaRepository<PurchaseAndSubscription, Long> {

    Page<PurchaseAndSubscription> findByUserId(Long userId, Pageable pageable);

    Optional<PurchaseAndSubscription> findFirstByUserId(Long userId);

    Page<PurchaseAndSubscription> findByUserIdAndProductType(Long userId, ProductType productType, Pageable pageable);

    @Query("""
        SELECT webinar
        FROM PurchaseAndSubscription purchase
        JOIN Webinar webinar ON purchase.referenceId = webinar.id
        WHERE purchase.user.id = ?1
        AND purchase.productType = ?2
        ORDER BY webinar.webinarDate ASC
        """)
    Page<Webinar> findUserWebinars(Long userId, ProductType productType, Pageable pageable);

    Optional<PurchaseAndSubscription> findByUserIdAndProductTypeAndReferenceId(
            Long userId,
            ProductType productType,
            Long referenceId);
}
