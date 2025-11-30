package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.interview.InterviewCreateDTO;
import io.hexlet.cv.dto.interview.InterviewDTO;
import io.hexlet.cv.dto.interview.InterviewUpdateDTO;
import io.hexlet.cv.dto.user.UserDTO;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.service.FlashPropsService;
import io.hexlet.cv.service.InterviewService;
import io.hexlet.cv.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
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
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminInterviewController {
    private final Inertia inertia;
    private final FlashPropsService flashPropsService;
    private final UserService userService;
    private final InterviewService interviewService;

    private static final int MAX_INTERVIEWS_ON_PAGE = 100;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> index(@PathVariable("locale") String locale,
                                        @RequestParam(required = false) String interviewSearchWord,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "30") int size,
                                        HttpServletRequest request) {

        try {
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

        } catch (Exception e) {

            Map<String, Object> props = flashPropsService.buildProps(locale, request);
            props.putAll(Map.of(
                    "interviews", List.of(),
                    "searchQuery", interviewSearchWord != null ? interviewSearchWord : "",
                    "pagination", Map.of(
                            "currentPage", 0,
                            "totalPages", 0,
                            "totalItems", 0,
                            "pageSize", size,
                            "hasPrevious", false,
                            "hasNext", false
                    ),
                    "pageTitle", "Ошибка загрузки списка интервью",
                    "error", "Не удалось загрузить список интервью"
            ));

            return inertia.render("Admin/Interviews/Index", props);
        }
    }

    @GetMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> createForm(@PathVariable("locale") String locale,
                                             HttpServletRequest request) {
        try {
            Map<String, Object> props = flashPropsService.buildProps(locale, request);
            List<UserDTO> availableSpeakers = userService.getPotentialInterviewSpeakers();

            props.putAll(Map.of(
                    "availableSpeakers", availableSpeakers,
                    "pageTitle", "Создание интервью"
            ));

            return inertia.render("Admin/Interviews/Create", props);

        } catch (Exception e) {

            Map<String, Object> props = flashPropsService.buildProps(locale, request);
            props.putAll(Map.of(
                    "availableSpeakers", List.of(),
                    "pageTitle", "Ошибка загрузки",
                    "error", "Не удалось загрузить форму создания"
            ));

            return inertia.render("Admin/Interviews/Create", props);
        }
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<String> createInterview(@PathVariable("locale") String locale,
                                                  @Valid @RequestBody InterviewCreateDTO createDTO,
                                                  RedirectAttributes redirectAttributes) {
        try {
            interviewService.create(createDTO);
            redirectAttributes.addFlashAttribute("success", "Интервью успешно создано");

            return inertia.redirect("/" + locale + "/admin/interview");

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute("error", "Ошибка при создании интервью: " + e.getMessage());

            return inertia.redirect("/" + locale + "/admin/interview/create");
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> show(@PathVariable("locale") String locale,
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

        } catch (ResourceNotFoundException e) {

            Map<String, Object> props = flashPropsService.buildProps(locale, request);
            props.putAll(Map.of(
                    "pageTitle", "Интервью не найдено",
                    "error", "Запрашиваемое интервью не существует или было удалено",
                    "interviewId", id
            ));

            return inertia.render("Error/InterviewNotFound", props);

        } catch (Exception e) {

            Map<String, Object> props = flashPropsService.buildProps(locale, request);
            props.putAll(Map.of(
                    "pageTitle", "Ошибка загрузки",
                    "error", "Не удалось загрузить интервью"
            ));

            return inertia.render("Admin/Interviews/Show", props);
        }
    }

    @GetMapping("/{id}/edit")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> editForm(@PathVariable("locale") String locale,
                                           @PathVariable Long id,
                                           HttpServletRequest request) {
        try {
            Map<String, Object> props = flashPropsService.buildProps(locale, request);
            InterviewDTO interviewDTO = interviewService.findById(id);
            List<UserDTO> availableSpeakers = userService.getPotentialInterviewSpeakers();

            props.putAll(Map.of(
                    "interview", interviewDTO,
                    "availableSpeakers", availableSpeakers,
                    "pageTitle", "Редактирование интервью"
            ));

            return inertia.render("Admin/Interviews/Edit", props);

        } catch (ResourceNotFoundException e) {

            Map<String, Object> props = flashPropsService.buildProps(locale, request);
            props.putAll(Map.of(
                    "pageTitle", "Интервью не найдено",
                    "error", "Не удалось найти интервью для редактирования",
                    "interviewId", id
            ));

            return inertia.render("Error/InterviewNotFound", props);

        } catch (Exception e) {

            Map<String, Object> props = flashPropsService.buildProps(locale, request);
            props.putAll(Map.of(
                    "interview", null,
                    "availableSpeakers", List.of(),
                    "pageTitle", "Ошибка загрузки",
                    "error", "Не удалось загрузить форму редактирования"
            ));

            return inertia.render("Admin/Interviews/Edit", props);
        }
    }

    @PutMapping("/{id}/edit")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<String> edit(@PathVariable("locale") String locale,
                                       @PathVariable Long id,
                                       @Valid @RequestBody InterviewUpdateDTO updateDTO,
                                       RedirectAttributes redirectAttributes) {
        try {
            interviewService.update(updateDTO, id);
            redirectAttributes.addFlashAttribute("success", "Интервью успешно обновлено");

            return inertia.redirect("/" + locale + "/admin/interview/" + id);

        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Интервью не найдено");

            return inertia.redirect("/" + locale + "/admin/interview");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении: " + e.getMessage());

            return inertia.redirect("/" + locale + "/admin/interview/" + id + "/edit");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<String> delete(@PathVariable String locale,
                                         @PathVariable Long id,
                                         RedirectAttributes redirectAttributes) {
        try {
            interviewService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Интервью успешно удалено");

            return inertia.redirect("/" + locale + "/admin/interview");

        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Интервью не найдено");

            return inertia.redirect("/" + locale + "/admin/interview");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении: " + e.getMessage());

            return inertia.redirect("/" + locale + "/admin/interview");
        }
    }
}
