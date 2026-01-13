package io.hexlet.cv.model.enums;

import lombok.Getter;

@Getter
public enum TeamMemberType {
    FOUNDER,
    CO_FOUNDER,
    MENTOR,
    ADVISOR,
    EXPERT,
    PARTNER,
    INVESTOR,
    AMBASSADOR,
    EMPLOYEE,
    INTERN,
    VOLUNTEER,
    CONTRIBUTOR,
    ALUMNI,
    GUEST_SPEAKER;

    @Override
    public String toString() {
        return name().toLowerCase().replace("_", " ");
    }
}
