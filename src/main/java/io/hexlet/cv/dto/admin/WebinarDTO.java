package io.hexlet.cv.dto.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class WebinarDTO {
    private Long id;

    @NotBlank
    private String webinarName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime webinarDate;


    private String webinarRegLink;


    private String webinarRecordLink;

    private boolean feature;
    private boolean publicated;
}
