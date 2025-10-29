package io.hexlet.cv.repository;

import io.hexlet.cv.model.admin.Webinar;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebinarRepository extends JpaRepository<Webinar, Long>  {
    List<Webinar> findByWebinarNameContainingIgnoreCaseOrWebinarRegLinkContainingIgnoreCase(
            String Name,
            LocalDateTime webinarDate
    );

    List<Webinar> findByWebinarDate(LocalDateTime webinarDate);


    Optional<Webinar> findFirstByWebinarNameOrWebinarDateOrWebinarRegLinkOrWebinarRecordLink(
            String webinarName,
            LocalDateTime webinarDate,
            String webinarRegLink,
            String webinarRecordLink
    );
}
