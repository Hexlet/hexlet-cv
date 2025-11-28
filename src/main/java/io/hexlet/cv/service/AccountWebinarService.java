package io.hexlet.cv.service;

import io.hexlet.cv.model.account.PurchaseAndSubscription;
import io.hexlet.cv.model.enums.ProductType;
import io.hexlet.cv.repository.PurchaseAndSubscriptionRepository;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountWebinarService {

    private final PurchaseAndSubscriptionRepository subsRepository;

    public Map<String, Object> indexWebinars(Long id, Pageable pageable) {

        Map<String, Object> props = new HashMap<>();


        var webinars = subsRepository.findByUserIdAndProductType(id, ProductType.WEBINAR, pageable)
                .map(PurchaseAndSubscription::getWebinar);


        props.put("currentPage", webinars.getNumber());
        props.put("totalPages", webinars.getTotalPages());
        props.put("totalItems", webinars.getTotalElements());
        props.put("webinars", webinars.getContent());
        return props;

    }
}
