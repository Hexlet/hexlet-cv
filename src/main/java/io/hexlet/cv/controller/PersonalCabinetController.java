package io.hexlet.cv.controller;

import io.hexlet.cv.dto.user.UserCabinetDto;
import io.hexlet.cv.service.PersonalCabinetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/personal-cabinet")
@RequiredArgsConstructor
public class PersonalCabinetController {

    private final PersonalCabinetService personalCabinetService;

    @GetMapping
    public UserCabinetDto getCabinetData(Authentication auth) {
        if (auth == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }
        return personalCabinetService.getCabinetData(auth);
    }
}
