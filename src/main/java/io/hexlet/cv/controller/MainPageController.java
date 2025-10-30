package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.service.PageSectionService;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

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
    public ResponseEntity<String> index(@PathVariable(required = false) String locale) {

        var actualLocale = validateAndGetLocale(locale);

        var sections = pageSectionService.findAllOnPage(PAGE_KEY, true);

        Map<String, Object> props = Map.of(
            "locale", actualLocale,
            "pageSections", sections
        );

        return inertia.render("Home/Index", props);
    }

    private String validateAndGetLocale(String locale) {

        if (locale == null) {
            return DEFAULT_LOCALE;
        }

        return SUPPORTED_LOCALES.contains(locale.toLowerCase()) ? locale.toLowerCase() : DEFAULT_LOCALE;
    }
}
