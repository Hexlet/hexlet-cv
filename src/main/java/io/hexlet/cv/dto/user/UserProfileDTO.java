package io.hexlet.cv.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileDTO {
    private String firstName;
    private String lastName;
    private String about;
}
