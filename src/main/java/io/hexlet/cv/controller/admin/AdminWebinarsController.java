package io.hexlet.cv.controller.admin;

import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.admin.WebinarDto;
import io.hexlet.cv.service.AdminWebinarService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;


@Controller
@AllArgsConstructor
@RequestMapping("/admin/webinars")
@PreAuthorize("hasRole('ADMIN')")
public class AdminWebinarsController {

    private final Inertia inertia;

    private final AdminWebinarService adminWebinarService;

    @GetMapping("")
    public  Object adminWebinarsIndex(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(name = "search", required = false) String searchStr,
                                      HttpServletRequest request) {

        Pageable pageable = PageRequest.of(page, size);
        var props = adminWebinarService.indexSearchWebinar(pageable, searchStr);

        var flash = RequestContextUtils.getInputFlashMap(request);

        if (flash != null && !flash.isEmpty()) {
            props.put("flash", flash);
        }

        return inertia.render("Admin/Webinars/Index", props);
    }


    @PostMapping("/create")
    public Object createWebinar(@RequestBody WebinarDto webinarDTO,
                                            RedirectAttributes redirectAttributes)  {



        adminWebinarService.createWebinar(webinarDTO);

        redirectAttributes.addFlashAttribute("success", "create.success");
        return inertia.redirect("/Admin/Webinars/Index");
    }

    @PutMapping("/{id}/update")
    public Object updateWebinar(@PathVariable Long id,
                                 @RequestBody WebinarDto webinarDTO,
                                 RedirectAttributes redirectAttributes)  {

        adminWebinarService.updateWebinar(id, webinarDTO);

        redirectAttributes.addFlashAttribute("success", "update.success");
        return inertia.redirect("/Admin/Webinars/Index");
    }

    @DeleteMapping("/{id}/delete")
    public Object deleteWebinar(@PathVariable Long id,
                                RedirectAttributes redirectAttributes)  {

        adminWebinarService.deleteWebinar(id);
        redirectAttributes.addFlashAttribute("success", "delete.success");
        return inertia.redirect("/Admin/Webinars/Index");
    }
}
