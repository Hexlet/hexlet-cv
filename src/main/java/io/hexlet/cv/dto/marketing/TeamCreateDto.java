package io.hexlet.cv.dto.marketing;

import io.hexlet.cv.model.enums.TeamMemberType;
import io.hexlet.cv.model.enums.TeamPosition;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamCreateDto {
    private String firstName;
    private String lastName;
    private TeamPosition position;
    private TeamMemberType memberType;
    private String avatarUrl;
    private Boolean isPublished = false;

    @NotNull
    private Boolean showOnHomepage;
    private Integer displayOrder = 0;
}
