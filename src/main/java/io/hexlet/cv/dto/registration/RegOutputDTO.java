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
/*
    public String getRole() {
        return role != null ? role.toLowerCase() : null;
    }
*/
}
