
package io.hexlet.cv.controller;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.service.FlashPropsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/{locale}/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final Inertia inertia;
    private final FlashPropsService flashPropsService;

    @GetMapping()
    public ResponseEntity<?> adminHomeController(@PathVariable("locale") String locale,
                                                 HttpServletRequest request) {

        var props = flashPropsService.buildProps(locale, request);
        return inertia.render("Admin/Index", props);
    }

    @GetMapping("/marketing")
    public ResponseEntity<?> adminMarketingController(@PathVariable("locale") String locale,
                                                      HttpServletRequest request) {
        var props = flashPropsService.buildProps(locale, request);
        return inertia.render("Admin/Marketing/Index", props);
    }

    @GetMapping("/webinars")
    public ResponseEntity<?> adminWebinarsController(@PathVariable("locale") String locale,
                                                     HttpServletRequest request) {
        var props = flashPropsService.buildProps(locale, request);
        return inertia.render("Admin/Webinars/Index", props);
    }

    @GetMapping("/knowledgebase")
    public ResponseEntity<?> adminKnowledgebaseController(@PathVariable("locale") String locale,
                                                          HttpServletRequest request) {
        var props = flashPropsService.buildProps(locale, request);
        return inertia.render("Admin/Knowledgebase/Index", props);

    }

    @GetMapping("/grading")
    public ResponseEntity<?> adminGradingController(@PathVariable("locale") String locale,
                                                    HttpServletRequest request) {
        var props = flashPropsService.buildProps(locale, request);
        return inertia.render("Admin/Grading/Index", props);
    }

    @GetMapping("/programs")
    public ResponseEntity<?> adminProgramsController(@PathVariable("locale") String locale,
                                                     HttpServletRequest request) {
        var props = flashPropsService.buildProps(locale, request);
        return inertia.render("Admin/Programs/Index", props);
    }

    @GetMapping("/users")
    public ResponseEntity<?> adminUsersController(@PathVariable("locale") String locale,
                                                  HttpServletRequest request) {
        var props = flashPropsService.buildProps(locale, request);
        return inertia.render("Admin/Users/Index", props);
    }

    @GetMapping("/settings")
    public ResponseEntity<?> adminSettingsController(@PathVariable("locale") String locale,
                                                     HttpServletRequest request) {
        var props = flashPropsService.buildProps(locale, request);
        return inertia.render("Admin/Settings/Index", props);
    }

    @GetMapping("/admin/help")
    public ResponseEntity<?> adminHelpController(@PathVariable("locale") String locale,
                                                 HttpServletRequest request) {
        var props = flashPropsService.buildProps(locale, request);
        return inertia.render("Admin/Help/Index", props);
    }
}
