package io.hexlet.cv.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegistrationResponseDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
}
