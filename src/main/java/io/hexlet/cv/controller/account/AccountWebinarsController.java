package io.hexlet.cv.controller.account;


import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.service.AccountWebinarService;
import io.hexlet.cv.util.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
@PreAuthorize("isAuthenticated()")
public class AccountWebinarsController {

    private final Inertia inertia;
    private final AccountWebinarService service;
    private final UserUtils userUtils;


    @GetMapping("/webinars")
    public Object index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        HttpServletRequest request) {

        var userId = userUtils.currentUserId();  // id юзера который залогинился

        Pageable pageable = PageRequest.of(page, size);

        var props = service.indexWebinars(userId.get(), pageable);

        var flash = RequestContextUtils.getInputFlashMap(request);
        if (flash != null && !flash.isEmpty()) {
            props.put("flash", flash);
        }

        return inertia.render("Account/Webinars/Index", props);
    }

    @PostMapping("/webinars/{webinarId}/registrations")
    public Object registerToWebinar(@PathVariable Long webinarId,
                                    RedirectAttributes redirectAttributes) {

        var userId = userUtils.currentUserId();  // id юзера который залогинился
        service.registrationUserToWebinar(userId.get(), webinarId);

        redirectAttributes.addFlashAttribute("success", "webinar.registered.success");
        return inertia.redirect("/account/webinars");

    }

    @PostMapping("/webinars/{webinarId}/calendar-events")
    public Object addWebinarToCalendar(@PathVariable Long webinarId,
                                       RedirectAttributes redirectAttributes) {

        var userId = userUtils.currentUserId();  // id юзера который залогинился
        service.addWebinarToCalendar(userId.get(), webinarId);

        redirectAttributes.addFlashAttribute("success", "webinar.add.success");
        return inertia.redirect("/account/webinars");
    }
}
