package io.hexlet.cv.dto.admin;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class WebinarDTO {

    private Long id;

    private String name;

    private LocalDateTime startDate;

    private String registrationUrl;
    private String recordUrl;

    private boolean feature;
    private boolean publicated;
}
