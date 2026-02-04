package io.hexlet.cv.dto.marketing;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamDto {
    private Long id;
    private String firstName;
    private String lastName;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    private String position;
    private String memberType;
    private String avatarUrl;
    private Boolean isPublished;
    private Boolean showOnHomepage;
    private Integer displayOrder;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
