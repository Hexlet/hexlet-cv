package io.hexlet.cv.dto.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TeamUpdateDTO {
    @NotBlank(message = "Имя обязательно")
    @JsonProperty("first_name")
    private JsonNullable<String> firstName;

    @NotBlank(message = "Фамилия обязательна")
    @JsonProperty("last_name")
    private JsonNullable<String> lastName;

    @NotBlank(message = "Роль на сайте обязательна")
    @JsonProperty("site_role")
    private JsonNullable<String> siteRole;

    @NotBlank(message = "Системная роль обязательна")
    @JsonProperty("system_role")
    private JsonNullable<String> systemRole;

    @JsonProperty("avatar_url")
    private JsonNullable<String> avatarUrl;

    @JsonProperty("is_published")
    private JsonNullable<Boolean> isPublished;

    @JsonProperty("show_on_homepage")
    private JsonNullable<Boolean> showHomepage;

    @JsonProperty("display_order")
    private JsonNullable<Integer> displayOrder;
}
