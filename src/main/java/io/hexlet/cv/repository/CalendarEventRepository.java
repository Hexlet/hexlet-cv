package io.hexlet.cv.repository;

import io.hexlet.cv.model.account.CalendarEvent;
import io.hexlet.cv.model.enums.CalendarEventType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {

    Optional<CalendarEvent> findByReferenceIdAndEventType(Long referenceId, CalendarEventType eventType);


    Optional<CalendarEvent> findFirstByUserId(Long id);
}
