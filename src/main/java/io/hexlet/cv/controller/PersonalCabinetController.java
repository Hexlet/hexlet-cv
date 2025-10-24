package io.hexlet.cv.controller;

import io.hexlet.cv.dto.user.UserCabinetDto;
import io.hexlet.cv.service.PersonalCabinetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cabinet")
@RequiredArgsConstructor
public class PersonalCabinetController {

    private final PersonalCabinetService personalCabinetService;

    @GetMapping
    public UserCabinetDto getCabinetData(Authentication auth) {
        if (auth == null) {
            // вернуть заглушку, если пользователь не авторизован
            return new UserCabinetDto(null, List.of(), Map.of());
        }
        return personalCabinetService.getCabinetData(auth);
    }
}
