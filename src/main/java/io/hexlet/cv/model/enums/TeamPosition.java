package io.hexlet.cv.model.enums;

import lombok.Getter;

@Getter
public enum TeamPosition {
    ANALYTIC("Аналитик", "Analyst"),
    ARCHITECT("Архитектор", "Architect"),
    CONTENT("Контент-мейкер", "Content Maker"),
    COPYWRITER("Копирайтер", "Copywriter"),
    DESIGNER("Дизайнер", "Designer"),
    DEVELOPER("Разработчик", "Developer"),
    DEVOPS("DevOps", "DevOps Engineer"),
    HR("HR специалист", "HR Specialist"),
    LEAD("Тимлид", "Team Lead"),
    MANAGER("Менеджер", "Manager"),
    MARKETING("Маркетолог", "Marketing Specialist"),
    PRODUCT("Продакт", "Product Manager"),
    QA("Тестировщик", "QA Engineer"),
    SEO("SEO специалист", "SEO Specialist"),
    SMM("SMM специалист", "SMM Specialist"),
    SUPPORT("Поддержка", "Support Specialist");

    private final String russianName;
    private final String englishName;

    TeamPosition(String russianName, String englishName) {
        this.russianName = russianName;
        this.englishName = englishName;
    }

    @Override
    public String toString() {
        return russianName;
    }
}
