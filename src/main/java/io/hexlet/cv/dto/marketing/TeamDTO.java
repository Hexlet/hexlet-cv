package io.hexlet.cv.dto.marketing;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TeamDTO {
    private Long id;
    private String firstName;
    private String lastName;

    @JsonProperty("fullName")
    public String getFullName() {
        return firstName + " " + lastName;
    }

    @JsonProperty("size_role")
    private String siteRole;

    @JsonProperty("system_role")
    private String systemRole;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("is_published")
    private Boolean isPublished;

    @JsonProperty("show_on_homepage")
    private Boolean showOnHomepage;

    @JsonProperty("published_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime publishedAt;

    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime updatedAt;
}
