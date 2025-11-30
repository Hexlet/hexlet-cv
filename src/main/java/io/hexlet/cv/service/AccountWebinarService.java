package io.hexlet.cv.service;

import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.handler.exception.WebinarAlreadyExistsException;
import io.hexlet.cv.model.account.CalendarEvent;
import io.hexlet.cv.model.account.PurchaseAndSubscription;
import io.hexlet.cv.model.enums.CalendarEventType;
import io.hexlet.cv.model.enums.ProductType;
import io.hexlet.cv.model.enums.StatePurchSubsType;
import io.hexlet.cv.model.webinars.Webinar;
import io.hexlet.cv.repository.CalendarEventRepository;
import io.hexlet.cv.repository.PurchaseAndSubscriptionRepository;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.repository.WebinarRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountWebinarService {

    private final PurchaseAndSubscriptionRepository subsRepository;
    private final WebinarRepository webinarRepository;
    private final UserRepository userRepository;
    private final CalendarEventRepository calendarEventRepository;

    public Map<String, Object> indexWebinars(Long userId, Pageable pageable) {

        Map<String, Object> props = new HashMap<>();


        Page<PurchaseAndSubscription> purchases = subsRepository
                .findByUserIdAndProductType(userId, ProductType.WEBINAR, pageable);

        Set<Long> webinarIds = purchases.getContent().stream()
                .map(subs -> subs.getReferenceId())
                .collect(Collectors.toSet());

        List<Webinar> webinars = webinarRepository.findAllByIdInOrderByWebinarDateAsc(webinarIds);


        props.put("currentPage", purchases.getNumber());
        props.put("totalPages", purchases.getTotalPages());
        props.put("totalItems", purchases.getTotalElements());
        props.put("webinars", webinars);


        return props;
    }

    public void registrationUserToWebinar(Long userId, Long webinarId) {
        var foundWebinar = webinarRepository.findById(webinarId).orElseThrow(
                () -> new ResourceNotFoundException("error.webinar.notFound")
        );
        var foundUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("error.user.notFound")
        );

        subsRepository.findByUserIdAndProductTypeAndReferenceId(userId, ProductType.WEBINAR, webinarId)
                .ifPresent((s) -> {
                    throw new WebinarAlreadyExistsException("error.webinar.alreadyRegistered");
                });

        var subscription = new PurchaseAndSubscription();
        subscription.setUser(foundUser);
        subscription.setOrderNum("free"); // без номера вебинар

        subscription.setReferenceId(foundWebinar.getId());
        subscription.setProductType(ProductType.WEBINAR);

        subscription.setItemName("Вебинар: " + foundWebinar.getWebinarName());
        subscription.setPurchasedAt(LocalDate.now());
        subscription.setAmount(BigDecimal.ZERO); // типа бесплатно
        subscription.setState(StatePurchSubsType.ACTIVE);

        subsRepository.save(subscription);
    }

    public void addWebinarToCalendar(Long userId, Long webinarId) {
        var foundWebinar = webinarRepository.findById(webinarId).orElseThrow(
                () -> new ResourceNotFoundException("error.webinar.notFound")
        );

        var foundUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("error.user.notFound")
        );

        calendarEventRepository.findByReferenceIdAndEventType(foundWebinar.getId(), CalendarEventType.WEBINAR)
                .ifPresent(s -> {
                    throw new WebinarAlreadyExistsException("error.webinar.alreadyAdded");
                });

        var event = new CalendarEvent();

        event.setUser(foundUser);
        event.setTitle("Вебинар: " + foundWebinar.getWebinarName());
        event.setStartAt(foundWebinar.getWebinarDate());
        //event.setFinishAt(foundWebinar.getWebinarDate()); // задать окончания вебинара ?

        event.setReferenceId(foundWebinar.getId());
        event.setEventType(CalendarEventType.WEBINAR);

        calendarEventRepository.save(event);

    }

}
