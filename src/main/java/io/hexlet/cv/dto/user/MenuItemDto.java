package io.hexlet.cv.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MenuItemDto {
    private String name;
    private String url;
    private String icon;
}
