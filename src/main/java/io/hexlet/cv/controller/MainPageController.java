package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.pagesection.PageSectionCreateDTO;
import io.hexlet.cv.dto.pagesection.PageSectionDTO;
import io.hexlet.cv.dto.pagesection.PageSectionUpdateDTO;
import io.hexlet.cv.service.PageSectionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping({"/", "/{locale}"})
public class MainPageController {

    private final Inertia inertia;
    private final PageSectionService pageSectionService;

    private static final String DEFAULT_LOCALE = "ru";
    private static final Set<String> SUPPORTED_LOCALES = Set.of("ru", "en");

    private static final String PAGE_KEY = "main";

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> index(@PathVariable(required = false) String locale) {

        var actualLocale = validateAndGetLocale(locale);

        var sections = pageSectionService.findByPageKey(PAGE_KEY);

        Map<String, Object> props = Map.of(
            "locale", actualLocale,
            "pageSections", sections
        );

        return ResponseEntity.ok(inertia.render("Home/Index", props));
    }

    @GetMapping("/{sectionKey}")
    @ResponseStatus(HttpStatus.OK)
    public PageSectionDTO showSection(@PathVariable String sectionKey) {

        return pageSectionService.findByPageKeyAndSectionKey(PAGE_KEY, sectionKey);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PageSectionDTO createSection(@Valid @RequestBody PageSectionCreateDTO dto) {

        return pageSectionService.createForPage(PAGE_KEY, dto);
    }

    @PutMapping("/{sectionKey}")
    @ResponseStatus(HttpStatus.OK)
    public PageSectionDTO updateSection(@Valid @RequestBody PageSectionUpdateDTO dto,
                                        @PathVariable String sectionKey) {

        return pageSectionService.updateByPageKeyAndSectionKey(PAGE_KEY, sectionKey, dto);
    }

    @DeleteMapping("/{sectionKey}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSection(@PathVariable String sectionKey) {

        pageSectionService.deleteByPageKeyAndSectionKey(PAGE_KEY, sectionKey);
    }

    private String validateAndGetLocale(String locale) {

        if (locale == null) {
            return DEFAULT_LOCALE;
        }

        return SUPPORTED_LOCALES.contains(locale.toLowerCase()) ? locale.toLowerCase() : DEFAULT_LOCALE;
    }
}
