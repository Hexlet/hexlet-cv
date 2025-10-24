package io.hexlet.cv.dto.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamCreateDTO {
    @NotBlank(message = "Имя обязательно")
    @JsonProperty("first_name")
    private String firsName;

    @NotBlank(message = "Фамилия обязательно")
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank(message = "Роль на сайте обязательна")
    @JsonProperty("site_rile")
    private String siteRole;

    @NotBlank(message = "Системная роль обязательна")
    @JsonProperty("system_role")
    private String systemRole;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("is_published")
    private Boolean isPublished = false;

    @NotNull
    @JsonProperty("show_on_homepage")
    private Boolean showOnHomepage;

    @JsonProperty("display_order")
    private Integer displayOrder = 0;
}
