package io.hexlet.cv.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.inertia4j.spring.Inertia;
import io.hexlet.cv.dto.admin.WebinarDTO;
import io.hexlet.cv.repository.WebinarRepository;
import io.hexlet.cv.service.AdminWebinarService;
import io.hexlet.cv.service.FlashPropsService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
// @PreAuthorize("hasRole('ADMIN')")
public class AdminWebinarsController {

    private final Inertia inertia;
    private final FlashPropsService flashPropsService;
    private final WebinarRepository webinarRepository;
    private final AdminWebinarService adminWebinarService;

    @GetMapping("")
    public Object adminWebinarsIndex(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                HttpServletRequest request) {

        Map<String, Object> props = new HashMap<>();

        var flash = RequestContextUtils.getInputFlashMap(request);

        if (flash != null && !flash.isEmpty()) {
            props.put("flash", flash);
        }

        Pageable pageable = PageRequest.of(page, size);
        var allWebinars = webinarRepository.findAll(pageable);

        var webinarsList = allWebinars.getContent().stream()
                .map(webinar -> {
                    WebinarDTO dto = new WebinarDTO();
                    dto.setId(webinar.getId());
                    dto.setWebinarName(webinar.getWebinarName());
                    dto.setWebinarDate(webinar.getWebinarDate());
                    dto.setWebinarRegLink(webinar.getWebinarRegLink());
                    dto.setWebinarRecordLink(webinar.getWebinarRecordLink());
                    dto.setFeature(webinar.isFeature());
                    dto.setPublicated(webinar.isPublicated());
                    return dto;
                })
                .toList();

        props.put("currentPage", allWebinars.getNumber());
        props.put("totalPages", allWebinars.getTotalPages());
        props.put("totalItems", allWebinars.getTotalElements());



        props.put("webinars", webinarsList);

        return inertia.render("Admin/Webinars/Index", props);
    }

    @PostMapping("/create")
    public Object createWebinar(@RequestBody WebinarDTO webinarDTO,
                                            RedirectAttributes redirectAttributes)  {

        adminWebinarService.createWebinar(webinarDTO);

        redirectAttributes.addFlashAttribute("success", "create.success");
        return inertia.redirect("/Admin/Webinars/Index");
    }

    @PutMapping("/{id}/update")
    private Object updateWebinar(@PathVariable Long id,
                                 @RequestBody WebinarDTO webinarDTO,
                                 RedirectAttributes redirectAttributes)  {

        adminWebinarService.updateWebinar(id, webinarDTO);

        redirectAttributes.addFlashAttribute("success", "update.success");
        return inertia.redirect("/Admin/Webinars/Index");
    }

    @DeleteMapping("/{id}/delete")
    private ResponseEntity<?> deleteWebinar(@PathVariable Long id,
                                            RedirectAttributes redirectAttributes)  {

        adminWebinarService.deleteWebinar(id);
        redirectAttributes.addFlashAttribute("success", "delete.success");
        return inertia.redirect("/Admin/Webinars/Index");
    }
}
