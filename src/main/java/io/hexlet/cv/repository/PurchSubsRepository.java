package io.hexlet.cv.repository;

import io.hexlet.cv.model.account.PurchSubs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PurchSubsRepository extends JpaRepository<PurchSubs, Long> {
    Page<PurchSubs> findByUserId(Long userId, Pageable pageable);
}
