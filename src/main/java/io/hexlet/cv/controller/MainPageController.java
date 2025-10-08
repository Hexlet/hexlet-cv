package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.service.PageSectionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        var sections = pageSectionService.findActiveByPageKey(PAGE_KEY);

        Map<String, Object> props = Map.of(
            "locale", actualLocale,
            "pageSections", sections
        );

        return ResponseEntity.ok(inertia.render("Home/Index", props));
    }

    private String validateAndGetLocale(String locale) {

        if (locale == null) {
            return DEFAULT_LOCALE;
        }

        return SUPPORTED_LOCALES.contains(locale.toLowerCase()) ? locale.toLowerCase() : DEFAULT_LOCALE;
    }
}
