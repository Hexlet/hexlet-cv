package io.hexlet.cv.controller.account;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.service.PurchaseAndSubscriptionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class PurchaseAndSubscriptionController {

    private final Inertia inertia;
    private final PurchaseAndSubscriptionService service;


    @GetMapping("/{userId}/purchase")
    public Object index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @PathVariable Long userId,
                        HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page, size);
        var props = service.indexPurchSubs(userId, pageable);

        var flash = RequestContextUtils.getInputFlashMap(request);
        if (flash != null && !flash.isEmpty()) {
            props.put("flash", flash);
        }

        return inertia.render("Account/Purchase/Index", props);
    }
}
