package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final Inertia inertia;

    @GetMapping("/{locale}/dashboard")
    public ResponseEntity<?> dashboard(@PathVariable String locale,
                                       HttpServletRequest request) {

        Map<String, Object> props = new HashMap<>();
        props.put("locale", locale);

        var session = request.getSession(false);
        if (session != null) {
            var flash = (Map<String, String>) session.getAttribute("flash");
            if (flash != null) {
                props.put("flash", flash);
                // удаляем flash
                session.removeAttribute("flash");
            }
        }

        // тут что у нас должно отдаваться и происходить на dashboard
        // и тут возвращаем какую-то страницу типа Dashboard/Index - во фронте виднее будет

        return inertia.render("Dashboard/Index", props);
    }
}
