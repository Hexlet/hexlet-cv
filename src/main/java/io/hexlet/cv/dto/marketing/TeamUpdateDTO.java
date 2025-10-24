package io.hexlet.cv.dto.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamUpdateDTO {
    @NotBlank(message = "Имя обязательно")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "Фамилия обязательна")
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank(message = "Роль на сайте обязательна")
    @JsonProperty("site_role")
    private String siteRole;

    @NotBlank(message = "Системная роль обязательна")
    @JsonProperty("system_role")
    private String systemRole;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("is_published")
    private Boolean isPublished;

    @JsonProperty("show_homepage")
    private Boolean showHomepage;

    @JsonProperty("display_order")
    private Integer displayOrder;
}
