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

// главная страницу по умолчанию ru передается в пропсах locale ru / en
// на frontend будет грузиться
// resources/js/Pages/Home/Index.vue   (для Vue)
// resources/js/Pages/Home/Index.jsx   (для React)
    @GetMapping({"/", "/{locale}/"})
    public ResponseEntity<?> home(@PathVariable(required = false) String locale) {
        if (locale == null || (!locale.equals("ru") && !locale.equals("en"))) {
            return ResponseEntity.ok(inertia.render("Home/Index", Map.of("locale", "ru")));
        }
        return ResponseEntity.ok(inertia.render("Home/Index", Map.of("locale", locale)));
    }
}
