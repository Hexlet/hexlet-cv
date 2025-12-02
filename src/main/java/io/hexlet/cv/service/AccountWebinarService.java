package io.hexlet.cv.service;

import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.model.enums.ProductType;
import io.hexlet.cv.model.enums.StatePurchaseSubscriptionType;
import io.hexlet.cv.model.webinars.Webinar;
import io.hexlet.cv.repository.PurchaseAndSubscriptionRepository;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.repository.WebinarRepository;
import io.hexlet.cv.util.UserUtils;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountWebinarService {

    private final PurchaseAndSubscriptionRepository purchaseAndSubscriptionRepository;
    private final WebinarRepository webinarRepository;
    private final UserRepository userRepository;
    private final UserUtils userUtils;

    public Map<String, Object> indexWebinars(Pageable pageable) {

        var userId = userUtils.currentUserId().get();

        Map<String, Object> props = new HashMap<>();

        Page<Webinar> webinars = purchaseAndSubscriptionRepository
                .findUserWebinars(userId, ProductType.WEBINAR, pageable);

        props.put("currentPage", webinars.getNumber());
        props.put("totalPages", webinars.getTotalPages());
        props.put("totalItems", webinars.getTotalElements());
        props.put("webinars", webinars.getContent());

        return props;
    }

    public void registrationUserToWebinar(Long webinarId) {
        var userId = userUtils.currentUserId().get();

        var subscription = purchaseAndSubscriptionRepository.findByUserIdAndProductTypeAndReferenceId(
                userId, ProductType.WEBINAR, webinarId)
                .orElseThrow(() -> new ResourceNotFoundException("error.subscription.notFound"));

        if (subscription.getState() != StatePurchaseSubscriptionType.AVAILABLE) {
            throw new ResourceNotFoundException("error.webinar.registrationNotAllowed");
        }

        subscription.setState(StatePurchaseSubscriptionType.REGISTERED);

        purchaseAndSubscriptionRepository.save(subscription);
    }
}
