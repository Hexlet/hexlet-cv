package io.hexlet.cv.service;

import io.hexlet.cv.dto.marketing.TeamCreateDTO;
import io.hexlet.cv.dto.marketing.TeamDTO;
import io.hexlet.cv.dto.marketing.TeamUpdateDTO;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.mapper.TeamMapper;
import io.hexlet.cv.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public List<TeamDTO> getAllTeamMembers() {
        return teamRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(teamMapper::map)
                .collect(Collectors.toList());
    }

    public TeamDTO getTeamMemberById(Long id) {
        var team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team member not found"));
        return teamMapper.map(team);
    }

    public TeamDTO getPublishedTeamMemberById(Long id) {
        var team = teamRepository.findByIdAndIsPublishedTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Published team member not found"));
        return teamMapper.map(team);
    }

    @Transactional
    public TeamDTO createTeamMember(TeamCreateDTO createDTO) {
        var team = teamMapper.map(createDTO);

        if (createDTO.getIsPublished()) {
            team.setPublishedAt(LocalDateTime.now());
        }

        var savedTeam = teamRepository.save(team);
        return teamMapper.map(savedTeam);
    }

    @Transactional
    public TeamDTO updateTeamMember(Long id, TeamUpdateDTO updateDTO) {
        var team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team member not found"));

        if (updateDTO.getIsPublished() != null) {
            var newStatus = updateDTO.getIsPublished();
            team.setIsPublished(newStatus);

            if (newStatus && team.getPublishedAt() == null) {
                team.setPublishedAt(LocalDateTime.now());
            } else if (!newStatus) {
                team.setPublishedAt(null);
            }
        }

        teamMapper.update(updateDTO, team);
        var savedTeam = teamRepository.save(team);
        return teamMapper.map(savedTeam);
    }

    @Transactional
    public void deleteTeamMember(Long id) {
        teamRepository.deleteById(id);
    }

    @Transactional
    public TeamDTO togglePublish(Long id) {
        var team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team member not found"));

        var newStatus = !team.getIsPublished();
        team.setIsPublished(newStatus);

        if (newStatus && team.getPublishedAt() == null) {
            team.setPublishedAt(LocalDateTime.now());
        } else if (!newStatus) {
            team.setPublishedAt(null);
        }

        var savedTeam = teamRepository.save(team);
        return teamMapper.map(savedTeam);
    }

    public List<TeamDTO> getHomepageTeamMembers() {
        return teamRepository.findByShowOnHomepageTrueOrderByDisplayOrderAsc()
                .stream()
                .map(teamMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateTeamMemberDisplayOrder(Long id, Integer displayOrder) {
        var team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team member not found"));
        team.setDisplayOrder(displayOrder);
        teamRepository.save(team);
    }

    @Transactional
    public void toggleTeamMemberHomepageVisibility(Long id) {
        var team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team member not found"));
        team.setShowOnHomepage(!team.getShowOnHomepage());
        teamRepository.save(team);
    }
}
