package io.hexlet.cv.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class UserCabinetDto {
    private UserDto user;
    private List<MenuItemDto> menu;
    private Map<String, Boolean> notificationSettings;
}
