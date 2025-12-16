package io.hexlet.cv.model.enums;

import lombok.Getter;

@Getter
public enum TeamMemberType {
    FOUNDER("Основатель"),
    CO_FOUNDER("Сооснователь"),
    MENTOR("Наставник"),
    ADVISOR("Консультант"),
    EXPERT("Эксперт"),
    PARTNER("Партнер"),
    INVESTOR("Инвестор"),
    AMBASSADOR("Амбассадор"),
    EMPLOYEE("Сотрудник"),
    INTERN("Стажер"),
    VOLUNTEER("Волонтер"),
    CONTRIBUTOR("Контрибьютор"),
    ALUMNI("Выпускник"),
    GUEST_SPEAKER("Приглашенный спикер");

    private final String displayName;

    TeamMemberType(String displayName) {
        this.displayName = displayName;
    }
    @Override
    public String toString() {
        return displayName;
    }
}
