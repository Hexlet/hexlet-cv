package io.hexlet.cv.model.enums;

import lombok.Getter;

@Getter
public enum TeamPosition {
    ANALYTIC,
    ARCHITECT,
    CONTENT,
    COPYWRITER,
    DESIGNER,
    DEVELOPER,
    DEVOPS,
    HR,
    LEAD,
    MANAGER,
    MARKETING,
    PRODUCT,
    QA,
    SEO,
    SMM,
    SUPPORT;

    @Override
    public String toString() {
        return name().toLowerCase().replace("_", " ");
    }
}
