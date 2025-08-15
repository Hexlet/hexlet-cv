package io.hexlet.cv.dto.registration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegOutputDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private String token;
}
