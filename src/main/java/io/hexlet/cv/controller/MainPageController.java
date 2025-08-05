package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainPageController {

    private final Inertia inertia;

    @GetMapping("/")
    public ResponseEntity<String> index() {
        // когда будет понятно как на frontend отдать через inertia то ->
        // return inertia.render("HomePage");
        // пока что просто заглушка main page
        return ResponseEntity.ok("main page");
    }
}
