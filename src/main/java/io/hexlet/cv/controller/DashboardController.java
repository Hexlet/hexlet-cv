package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.service.FlashPropsService;
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
    private final FlashPropsService flashPropsService;

    @GetMapping("/{locale}/dashboard")
    public ResponseEntity<?> dashboard(@PathVariable String locale,
                                       HttpServletRequest request) {


        var props = flashPropsService.buildProps(locale, request);;

        return inertia.render("Dashboard/Index", props);
    }
}
