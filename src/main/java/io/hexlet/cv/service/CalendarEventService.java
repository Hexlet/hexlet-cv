package io.hexlet.cv.service;

import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.handler.exception.WebinarAlreadyExistsException;
import io.hexlet.cv.model.account.CalendarEvent;
import io.hexlet.cv.model.enums.CalendarEventType;
import io.hexlet.cv.repository.CalendarEventRepository;
import io.hexlet.cv.repository.UserRepository;
import io.hexlet.cv.repository.WebinarRepository;
import io.hexlet.cv.util.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CalendarEventService {

    private final WebinarRepository webinarRepository;
    private final UserRepository userRepository;
    private final CalendarEventRepository calendarEventRepository;
    private final UserUtils userUtils;

    public void addWebinarToCalendar(Long webinarId) {

        var userId = userUtils.currentUserId().get();

        var foundWebinar = webinarRepository.findById(webinarId).orElseThrow(
                () -> new ResourceNotFoundException("error.webinar.notFound")
        );

        var foundUser = userRepository.findById(userId).get();

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
