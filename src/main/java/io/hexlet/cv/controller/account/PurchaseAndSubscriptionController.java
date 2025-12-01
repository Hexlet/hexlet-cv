package io.hexlet.cv.controller.account;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.service.PurchaseAndSubscriptionService;
import io.hexlet.cv.util.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class PurchaseAndSubscriptionController {

    private final Inertia inertia;
    private final PurchaseAndSubscriptionService service;
    private final UserUtils userUtils;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/purchase")
    public Object index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        HttpServletRequest request) {

        var userId = userUtils.currentUserId();  // id юзера который залогинился

        Pageable pageable = PageRequest.of(page, size);
        var props = service.indexPurchSubs(userId.get(), pageable);

        var flash = RequestContextUtils.getInputFlashMap(request);
        if (flash != null && !flash.isEmpty()) {
            props.put("flash", flash);
        }

        return inertia.render("Account/Purchase/Index", props);
    }
}
