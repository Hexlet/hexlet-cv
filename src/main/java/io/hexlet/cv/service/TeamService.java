package io.hexlet.cv.service;

import io.hexlet.cv.dto.marketing.TeamCreateDto;
import io.hexlet.cv.dto.marketing.TeamDto;
import io.hexlet.cv.dto.marketing.TeamUpdateDto;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.mapper.TeamMapper;
import io.hexlet.cv.model.admin.marketing.Team;
import io.hexlet.cv.repository.TeamRepository;
import io.hexlet.cv.util.JsonNullableUtils;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final Clock clock;

    public Page<TeamDto> getAllTeamMembers(Pageable pageable) {
        log.debug("Getting all teams with pageable: {}", pageable);
        return teamRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(teamMapper::map);
    }

    public TeamDto getTeamMemberById(Long id) {
        var team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("team.member.not.found"));
        return teamMapper.map(team);
    }

    @Transactional
    public TeamDto createTeamMember(TeamCreateDto createDTO) {
        log.debug("Creating team: {} {}", createDTO.getFirstName(), createDTO.getLastName());
        var team = teamMapper.map(createDTO);

        if (createDTO.getIsPublished()) {
            team.setPublishedAt(LocalDateTime.now(clock));
        }

        log.debug("Creating member with position: {}, memberType: {}",
                createDTO.getPosition(), createDTO.getMemberType());
        var savedTeam = teamRepository.save(team);
        return teamMapper.map(savedTeam);
    }

    @Transactional
    public TeamDto updateTeamMember(Long id, TeamUpdateDto updateDTO) {
        log.debug("Updating team id: {}", id);
        var team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("team.member.not.found"));

        JsonNullableUtils.ifPresent(updateDTO.getIsPublished(), newStatus -> {
            team.setIsPublished(newStatus);

            if (Boolean.TRUE.equals(newStatus) && team.getPublishedAt() == null) {
                team.setPublishedAt(LocalDateTime.now(clock));
            } else if (Boolean.FALSE.equals(newStatus)) {
                team.setPublishedAt(null);
            }
        });

        teamMapper.update(updateDTO, team);

        JsonNullableUtils.logIfChanged("position", updateDTO.getPosition(),
                team.getPosition());
        JsonNullableUtils.logIfChanged("memberType", updateDTO.getMemberType(),
                team.getMemberType());

        var savedTeam = teamRepository.save(team);
        return teamMapper.map(savedTeam);
    }

    @Transactional
    public void deleteTeamMember(Long id) {
        log.debug("Deleting team id: {}", id);
        teamRepository.deleteById(id);
    }

    @Transactional
    public TeamDto togglePublish(Long id) {
        log.debug("Toggling publish for team id: {}", id);
        var team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("team.member.not.found"));

        var newStatus = !team.getIsPublished();
        team.setIsPublished(newStatus);

        if (newStatus && team.getPublishedAt() == null) {
            team.setPublishedAt(LocalDateTime.now(clock));
        } else if (!newStatus) {
            team.setPublishedAt(null);
        }

        var savedTeam = teamRepository.save(team);
        return teamMapper.map(savedTeam);
    }

    public List<TeamDto> getHomepageTeamMembers() {
        return teamRepository.findByShowOnHomepageTrue(
                        Sort.by(Sort.Direction.DESC, Team.FIELD_CREATED_AT)
                )
                .stream()
                .map(teamMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateTeamMemberDisplayOrder(Long id, Integer displayOrder) {
        var team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("team.member.not.found"));
        team.setDisplayOrder(displayOrder);
        teamRepository.save(team);
    }

    @Transactional
    public void toggleTeamMemberHomepageVisibility(Long id) {
        var team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("team.member.not.found"));
        team.setShowOnHomepage(!team.getShowOnHomepage());
        teamRepository.save(team);
    }
}
