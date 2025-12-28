package io.hexlet.cv.service;

import io.hexlet.cv.model.enums.TeamMemberType;
import io.hexlet.cv.model.enums.TeamPosition;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EnumService {

    public Map<String, List<String>> getAllEnums() {
        return Map.of(
                "teamPositions", getTeamPositionValues(),
                "teamMemberTypes", getTeamMemberTypeValues()
        );
    }

    public List<String> getTeamPositionValues() {
        return Arrays.stream(TeamPosition.values())
                .map(Enum::name)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> getTeamMemberTypeValues() {
        return Arrays.stream(TeamMemberType.values())
                .map(Enum::name)
                .sorted()
                .collect(Collectors.toList());
    }

    public Map<String, List<String>> getTeamEnums() {
        return Map.of(
                "positions", getTeamPositionValues(),
                "memberTypes", getTeamMemberTypeValues()
        );
    }

    public TeamPosition parseTeamPosition(String value) {
        try {
            return TeamPosition.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid TeamPosition value: " + value);
        }
    }

    public TeamMemberType parseTeamMemberType(String value) {
        try {
            return TeamMemberType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid TeamMemberType value: " + value);
        }
    }
}
