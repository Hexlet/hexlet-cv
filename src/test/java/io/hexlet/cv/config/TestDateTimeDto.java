package io.hexlet.cv.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestDateTimeDto {
    private LocalDateTime dateTime;
    private LocalDate date;
    private LocalTime time;
    private String name;
}
