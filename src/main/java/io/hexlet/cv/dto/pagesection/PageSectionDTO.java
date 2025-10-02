package io.hexlet.cv.dto.pagesection;

import java.time.LocalDateTime;

public class PageSectionDTO {
    private Long id;
    private String sectionKey;
    private String title;
    private String content;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
