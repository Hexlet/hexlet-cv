package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class MainPageController {

    private final Inertia inertia;

    @GetMapping({"/", "/{locale}/"})
    public ResponseEntity<?> home(@PathVariable(required = false) String locale) {

        String defaultLocale = (locale == null || (!locale.equals("ru") && !locale.equals("en")))
                ? "ru"
                : locale;
        return ResponseEntity.ok(inertia.render("Home/Index", Map.of("locale", defaultLocale)));
    }
}
