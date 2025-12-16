package io.hexlet.cv.dto.marketing;

import io.hexlet.cv.model.enums.TeamMemberType;
import io.hexlet.cv.model.enums.TeamPosition;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class TeamUpdateDto {

    private JsonNullable<String> firstName;
    private JsonNullable<String> lastName;
    private JsonNullable<TeamPosition> position;
    private JsonNullable<TeamMemberType> memberType;
    private JsonNullable<String> avatarUrl;
    private JsonNullable<Boolean> isPublished;
    private JsonNullable<Boolean> showOnHomepage;
    private JsonNullable<Integer> displayOrder;
}
