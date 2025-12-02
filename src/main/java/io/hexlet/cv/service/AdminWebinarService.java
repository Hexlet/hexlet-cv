package io.hexlet.cv.service;

import io.hexlet.cv.dto.admin.WebinarDto;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.handler.exception.WebinarAlreadyExistsException;
import io.hexlet.cv.mapper.AdminWebinarMapper;
import io.hexlet.cv.model.webinars.Webinar;
import io.hexlet.cv.repository.WebinarRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminWebinarService {

    private final WebinarRepository webinarRepository;
    private final AdminWebinarMapper adminWebinarMapper;

    public Map<String, Object> indexSearchWebinar(Pageable pageable, String searchStr) {

        Map<String, Object> props = new HashMap<>();

        String searchString = (searchStr == null) ? null : searchStr.trim();

        Page<Webinar> allWebinars;

        if (searchString == null || searchString.isBlank()) {
            allWebinars = webinarRepository.findAll(pageable);
        } else {

            LocalDateTime from = null;
            LocalDateTime to = null;

            // 2000 ... 2099
            if (searchString.matches("^20\\d{2}$")) {
                int year = Integer.parseInt(searchString);

                from = LocalDateTime.of(year, 1, 1, 0, 0);
                to = LocalDateTime.of(year, 12, 31, 23, 59);
            }

            if (searchString.matches("\\d{4}-\\d{2}")) {
                var ym = YearMonth.parse(searchString, DateTimeFormatter.ofPattern("yyyy-MM"));
                from = ym.atDay(1).atTime(0, 0, 0);
                to = ym.atEndOfMonth().atTime(23, 59, 0);
            }

            if (searchString.matches("\\d{4}-\\d{2}-\\d{2}$")) {
                var ymd = LocalDate.parse(searchString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                from = ymd.atTime(0, 0, 0);
                to = ymd.atTime(23, 59, 0);
            }

            if (searchString.matches(
                    "^\\s*(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])\\s+(?:[01]\\d|2[0-3]):[0-5]\\d\\s*$")) {
                from = LocalDateTime.parse(searchString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                to = from;
            }

            if (from != null && to != null) {
                allWebinars = webinarRepository.findAllByWebinarDateBetween(from, to, pageable);
            } else {
                allWebinars = webinarRepository.searchByText(searchString, pageable);
            }
        }

        props.put("currentPage", allWebinars.getNumber());
        props.put("totalPages", allWebinars.getTotalPages());
        props.put("totalItems", allWebinars.getTotalElements());

        props.put("webinars", allWebinars.getContent());

        return props;
    }

    @Transactional
    public void createWebinar(WebinarDto webinar) {

    // можно потом переделать чтобы в одном исключении приходили все совпадения
        if (webinarRepository.existsByWebinarName(webinar.getWebinarName())) {
            throw new WebinarAlreadyExistsException("webinar.nameExists");
        }
        if (webinarRepository.existsByWebinarRecordLink(webinar.getWebinarRecordLink())) {
            throw new WebinarAlreadyExistsException("webinar.recordUrExists");
        }
        if (webinarRepository.existsByWebinarRegLink(webinar.getWebinarRegLink())) {
            throw new WebinarAlreadyExistsException("webinar.registrationUrlExists");
        }
        if (webinarRepository.existsByWebinarDate(webinar.getWebinarDate())) {
            throw new WebinarAlreadyExistsException("webinar.dateExists");
        }

        webinarRepository.save(adminWebinarMapper.map(webinar));
    }

    @Transactional
    public void updateWebinar(Long id, WebinarDto webinar) {

        var webinarToUpdate = webinarRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("error.webinarNotFound")
        );
        adminWebinarMapper.update(webinar, webinarToUpdate);
        webinarRepository.save(webinarToUpdate);
    }

    @Transactional
    public void deleteWebinar(Long id) {
        if (!webinarRepository.existsById(id)) {
            throw new ResourceNotFoundException("error.webinarNotFound");
        }
        webinarRepository.deleteById(id);
    }

}
