package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.interview.InterviewCreateDTO;
import io.hexlet.cv.dto.interview.InterviewDTO;
import io.hexlet.cv.dto.interview.InterviewUpdateDTO;
import io.hexlet.cv.dto.user.UserDTO;
import io.hexlet.cv.service.InterviewService;
import io.hexlet.cv.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/{locale}/admin/interview")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminInterviewController {
    private final Inertia inertia;
    private final UserService userService;
    private final InterviewService interviewService;

    private static final int MAX_INTERVIEWS_ON_PAGE = 100;

    @GetMapping()
    public ResponseEntity<String> index(@PathVariable("locale") String locale,
                                        @RequestParam(required = false) String interviewSearchWord,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "30") int size) {
        int safeSize = Math.min(size, MAX_INTERVIEWS_ON_PAGE);
        Pageable pageable = PageRequest.of(page, safeSize, Sort.by("createdAt").descending());

        Page<InterviewDTO> interviewPage;

        if (StringUtils.hasText(interviewSearchWord)) {
            interviewPage = interviewService.search(interviewSearchWord, pageable);
        } else {
            interviewPage = interviewService.getAll(pageable);
        }

        Map<String, Object> props = Map.of(
                "locale", locale,
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
                "pageTitle", StringUtils.hasText(interviewSearchWord) ?
                        "Результаты поиска: " + interviewSearchWord : "Список интервью"
        );

        return inertia.render("Admin/Interviews/Index", props);
    }

    @GetMapping("/create")
    public ResponseEntity<String> createForm(@PathVariable("locale") String locale) {
        List<UserDTO> availableSpeakers = userService.getPotentialInterviewSpeakers();

        Map<String, Object> props = Map.of(
                "locale", locale,
                "availableSpeakers", availableSpeakers,
                "pageTitle", "Создание интервью"
        );

        return inertia.render("Admin/Interviews/Create", props);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createInterview(@PathVariable String locale,
                                                  @Valid @RequestBody InterviewCreateDTO createDTO) {
        interviewService.create(createDTO);

        return inertia.redirect("/" + locale + "/admin/interview");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> show(@PathVariable("locale") String locale,
                                       @PathVariable Long id) {
        InterviewDTO interviewDTO = interviewService.findById(id);

        Map<String, Object> props = Map.of(
                "locale", locale,
                "interview", interviewDTO,
                "pageTitle", "Просмотр интервью"
        );

        return inertia.render("Admin/Interviews/Show", props);
    }

    @GetMapping("/{id}/edit")
    public ResponseEntity<String> editForm(@PathVariable("locale") String locale,
                                       @PathVariable Long id) {
        InterviewDTO interviewDTO = interviewService.findById(id);
        List<UserDTO> availableSpeakers = userService.getPotentialInterviewSpeakers();

        Map<String, Object> props = Map.of(
                "locale", locale,
                "interview", interviewDTO,
                "availableSpeakers", availableSpeakers,
                "pageTitle", "Редактирование интервью"
        );

        return inertia.render("Admin/Interviews/Edit", props);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<String> edit(@PathVariable("locale") String locale,
                                       @PathVariable Long id,
                                       @Valid @RequestBody InterviewUpdateDTO updateDTO) {
        InterviewDTO updatedInterview = interviewService.update(updateDTO, id);

        return inertia.redirect("/" + locale + "/admin/interview/" + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("locale") String locale,
                                       @PathVariable Long id) {
        interviewService.delete(id);

        return inertia.redirect("/" + locale + "/admin/interview");
    }
}
