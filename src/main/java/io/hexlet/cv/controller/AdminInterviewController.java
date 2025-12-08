package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.interview.InterviewCreateDTO;
import io.hexlet.cv.dto.interview.InterviewDTO;
import io.hexlet.cv.dto.interview.InterviewUpdateDTO;
import io.hexlet.cv.handler.exception.InterviewNotFoundException;
import io.hexlet.cv.service.FlashPropsService;
import io.hexlet.cv.service.InterviewService;
import io.hexlet.cv.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/{locale}/admin/interview")
@PreAuthorize("hasRole('ADMIN')")
public class AdminInterviewController {
    private final Inertia inertia;
    private final FlashPropsService flashPropsService;
    private final UserService userService;
    private final InterviewService interviewService;

    private static final int MAX_INTERVIEWS_ON_PAGE = 100;
    private static final String DEFAULT_PAGE_TO_SHOW = "0";
    private static final String DEFAULT_NUMBER_OF_INTERVIEW_ON_PAGE = "30";

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> index(@PathVariable("locale") String locale,
                                        @RequestParam(required = false) String interviewSearchWord,
                                        @RequestParam(defaultValue = DEFAULT_PAGE_TO_SHOW) int page,
                                        @RequestParam(defaultValue = DEFAULT_NUMBER_OF_INTERVIEW_ON_PAGE) int size,
                                        HttpServletRequest request) {

        Map<String, Object> props = flashPropsService.buildProps(locale, request);

        int safeSize = Math.min(size, MAX_INTERVIEWS_ON_PAGE);
        Pageable pageable = PageRequest.of(page, safeSize, Sort.by("createdAt").descending());

        Page<InterviewDTO> interviewPage = StringUtils.hasText(interviewSearchWord)
                ? interviewService.search(interviewSearchWord, pageable)
                : interviewService.getAll(pageable);

        props.putAll(Map.of(
                "interviews", interviewPage.getContent(),
                "searchQuery", interviewSearchWord != null ? interviewSearchWord : "",
                "pagination", Map.of(
                        "currentPage", interviewPage.getNumber(),
                        "totalPages", interviewPage.getTotalPages(),
                        "totalItems", interviewPage.getTotalElements(),
                        "pageSize", safeSize,
                        "hasPrevious", interviewPage.hasPrevious(),
                        "hasNext", interviewPage.hasNext()
                ),
                "pageTitle", StringUtils.hasText(interviewSearchWord)
                        ? "Результаты поиска: " + interviewSearchWord
                        : "Список интервью"
        ));

        return inertia.render("Admin/Interviews/Index", props);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<?> createInterview(@PathVariable("locale") String locale,
                                                  @Valid @RequestBody InterviewCreateDTO createDTO,
                                                  RedirectAttributes redirectAttributes) {

        interviewService.create(createDTO);
        redirectAttributes.addFlashAttribute("success", "Интервью успешно создано");

        return inertia.redirect("/" + locale + "/admin/interview");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> show(@PathVariable("locale") String locale,
                                       @PathVariable Long id,
                                       HttpServletRequest request) {
        try {
            Map<String, Object> props = flashPropsService.buildProps(locale, request);
            InterviewDTO interviewDTO = interviewService.findById(id);

            props.putAll(Map.of(
                    "interview", interviewDTO,
                    "pageTitle", "Просмотр интервью"
            ));

            return inertia.render("Admin/Interviews/Show", props);
        } catch (InterviewNotFoundException ex) {
            Map<String, Object> errorProps = flashPropsService.buildProps(locale, request);

            errorProps.put("status", 404);
            errorProps.put("message", ex.getMessage());
            errorProps.put("interviewId", id);
            errorProps.put("locale", locale);

            ResponseEntity<?> inertiaResponse = inertia.render("Error/InterviewNotFound", errorProps);

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .headers(inertiaResponse.getHeaders())
                    .body(inertiaResponse.getBody());
        }
    }

    @PutMapping("/{id}/edit")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<?> edit(@PathVariable("locale") String locale,
                                  @PathVariable Long id,
                                  @Valid @RequestBody InterviewUpdateDTO updateDTO,
                                  HttpServletRequest request,
                                  RedirectAttributes redirectAttributes) {
        try {
            interviewService.update(updateDTO, id);
            redirectAttributes.addFlashAttribute("success", "Интервью успешно обновлено");

            return inertia.redirect("/" + locale + "/admin/interview/" + id);
        } catch (InterviewNotFoundException ex) {
            Map<String, Object> errorProps = flashPropsService.buildProps(locale, request);

            errorProps.put("status", 404);
            errorProps.put("message", ex.getMessage());
            errorProps.put("interviewId", id);
            errorProps.put("locale", locale);

            ResponseEntity<?> inertiaResponse = inertia.render("Error/InterviewNotFound", errorProps);

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .headers(inertiaResponse.getHeaders())
                    .body(inertiaResponse.getBody());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<?> delete(@PathVariable("locale") String locale,
                                    @PathVariable Long id,
                                    HttpServletRequest request,
                                    RedirectAttributes redirectAttributes) {
        try {
            interviewService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Интервью успешно удалено");

            return inertia.redirect("/" + locale + "/admin/interview");
        } catch (InterviewNotFoundException ex) {
            Map<String, Object> errorProps = flashPropsService.buildProps(locale, request);

            errorProps.put("status", 404);
            errorProps.put("message", ex.getMessage());
            errorProps.put("interviewId", id);
            errorProps.put("locale", locale);

            ResponseEntity<?> inertiaResponse = inertia.render("Error/InterviewNotFound", errorProps);

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .headers(inertiaResponse.getHeaders())
                    .body(inertiaResponse.getBody());
        }
    }
}
