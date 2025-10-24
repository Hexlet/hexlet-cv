package io.hexlet.cv.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private String firstName;
    private String lastName;
    private String role;
    private String avatarUrl;
}
