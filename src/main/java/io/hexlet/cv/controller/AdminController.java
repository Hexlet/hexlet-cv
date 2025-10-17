
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
    public ResponseEntity<?> AdminHomeController(@PathVariable("locale") String locale,
                                                 HttpServletRequest request) {

        var props = flashPropsService.buildProps(locale, request);;
        return inertia.render("Admin/Index", props);
    }

    @GetMapping("/marketing")
    public ResponseEntity<?> AdminMarketingController(@PathVariable("locale") String locale,
                                                      HttpServletRequest request) {
        var props = flashPropsService.buildProps(locale, request);;
        return inertia.render("Admin/Marketing/Index", props);
    }

    @GetMapping("/webinars")
    public ResponseEntity<?> AdminWebinarsController(@PathVariable("locale") String locale,
                                                     HttpServletRequest request) {
        var props = flashPropsService.buildProps(locale, request);;
        return inertia.render("Admin/Webinars/Index", props);
    }

    @GetMapping("/knowledgebase")
    public ResponseEntity<?> AdminKnowledgebaseController(@PathVariable("locale") String locale,
                                                          HttpServletRequest request) {
        var props = flashPropsService.buildProps(locale, request);;
        return inertia.render("Admin/Knowledgebase/Index", props);

    }

    @GetMapping("/interview")
    public ResponseEntity<?> AdminInterviewController(@PathVariable("locale") String locale,
                                                      HttpServletRequest request) {
        var props = flashPropsService.buildProps(locale, request);;
        return inertia.render("Admin/Interview/Index", props);

    }

    @GetMapping("/grading")
    public ResponseEntity<?> AdminGradingController(@PathVariable("locale") String locale,
                                                    HttpServletRequest request) {
        var props = flashPropsService.buildProps(locale, request);;
        return inertia.render("Admin/Grading/Index", props);
    }

    @GetMapping("/programs")
    public ResponseEntity<?> AdminProgramsController(@PathVariable("locale") String locale,
                                                     HttpServletRequest request) {
        var props = flashPropsService.buildProps(locale, request);;
        return inertia.render("Admin/Programs/Index", props);
    }

    @GetMapping("/users")
    public ResponseEntity<?> AdminUsersController(@PathVariable("locale") String locale,
                                                  HttpServletRequest request) {
        var props = flashPropsService.buildProps(locale, request);;
        return inertia.render("Admin/Users/Index", props);
    }

    @GetMapping("/settings")
    public ResponseEntity<?> AdminSettingsController(@PathVariable("locale") String locale,
                                                     HttpServletRequest request) {
        var props = flashPropsService.buildProps(locale, request);;
        return inertia.render("Admin/Settings/Index", props);
    }

    @GetMapping("/admin/help")
    public ResponseEntity<?> AdminHelpController(@PathVariable("locale") String locale,
                                                 HttpServletRequest request) {
        var props = flashPropsService.buildProps(locale, request);;
        return inertia.render("Admin/Help/Index", props);
    }
}

