package io.hexlet.cv.dto.marketing;

import io.hexlet.cv.model.enums.TeamMemberType;
import io.hexlet.cv.model.enums.TeamPosition;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
public class TeamCreateDto {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Position is required")
    private TeamPosition position;

    @NotNull(message = "Member type is required")
    private TeamMemberType memberType;

    @URL(message = "Avatar URL must be valid")
    private String avatarUrl;

    private Boolean isPublished = false;

    @NotNull(message = "Homepage visibility is required")
    private Boolean showOnHomepage;

    @Min(value = 0, message = "Display order must be positive")
    private Integer displayOrder = 0;
}
