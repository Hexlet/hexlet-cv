package io.hexlet.cv.service;

import io.hexlet.cv.mapper.PurchSubsMapper;
import io.hexlet.cv.repository.PurchaseAndSubscriptionRepository;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PurchaseAndSubscriptionService {

    private final PurchaseAndSubscriptionRepository subsRepository;
    private final PurchSubsMapper mapper;

    public Map<String, Object> indexPurchSubs(Long userId, Pageable pageable) {

        Map<String, Object> props = new HashMap<>();

        var allSubs = subsRepository.findByUserId(userId, pageable);

        props.put("currentPage", allSubs.getNumber());
        props.put("totalPages", allSubs.getTotalPages());
        props.put("totalItems", allSubs.getTotalElements());

        props.put("purchase", allSubs.map(mapper::toDto));
        return props;
    }
}
