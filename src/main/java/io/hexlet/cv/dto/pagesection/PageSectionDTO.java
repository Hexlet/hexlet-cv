package io.hexlet.cv.dto.pagesection;

import lombok.Data;

@Data
public class PageSectionDTO {
    private Long id;
    private String pageKey;
    private String sectionKey;
    private String title;
    private String content;
    private boolean active;
    private String createdAt;
    private String updatedAt;
}
