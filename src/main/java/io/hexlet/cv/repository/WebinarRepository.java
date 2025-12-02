package io.hexlet.cv.repository;

import io.hexlet.cv.model.webinars.Webinar;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WebinarRepository extends JpaRepository<Webinar, Long>  {

    @Query("""
   SELECT w FROM Webinar w
   WHERE lower(w.webinarName) LIKE lower(concat('%', :searchSt, '%'))
      OR lower(w.webinarRegLink) LIKE lower(concat('%', :searchSt, '%'))
      OR lower(w.webinarRecordLink) LIKE lower(concat('%', :searchSt, '%'))
         """)
    Page<Webinar> searchByText(@Param("searchSt") String searchSt, Pageable pageable);

/*
    List<Webinar>
    findByWebinarNameContainingIgnoreCaseOrWebinarRegLinkContainingIgnoreCaseOrWebinarRecordLinkContainingIgnoreCase(
            String searchStr,
            Pageable pageable);
*/

    // List<Webinar> findByWebinarDate(LocalDateTime webinarDate);

    Page<Webinar> findAllByWebinarDateBetween(LocalDateTime from,
                                              LocalDateTime to,
                                              Pageable pageable);

    Optional<Webinar> findFirstByWebinarNameOrWebinarDateOrWebinarRegLinkOrWebinarRecordLink(
            String webinarName,
            LocalDateTime webinarDate,
            String webinarRegLink,
            String webinarRecordLink
    );


    boolean existsByWebinarName(String name);
    boolean existsByWebinarRecordLink(String link);
    boolean existsByWebinarRegLink(String link);
    boolean existsByWebinarDate(LocalDateTime date);


    Optional<Webinar> findFirstByWebinarName(String name);
    Optional<Webinar> findFirstByOrderByIdAsc();

    List<Webinar> findAllByIdInOrderByWebinarDateAsc(Iterable<Long> webinarIds);

    Webinar findFirstBy();
}
