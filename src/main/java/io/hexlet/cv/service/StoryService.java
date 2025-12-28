package io.hexlet.cv.service;

import io.hexlet.cv.dto.marketing.StoryCreateDto;
import io.hexlet.cv.dto.marketing.StoryDto;
import io.hexlet.cv.dto.marketing.StoryUpdateDto;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.mapper.StoryMapper;
import io.hexlet.cv.model.admin.marketing.Story;
import io.hexlet.cv.repository.StoryRepository;
import io.hexlet.cv.util.JsonNullableUtils;
import jakarta.transaction.Transactional;
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

@Service
@Slf4j
@AllArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final StoryMapper storyMapper;
    private final Clock clock;

    public Page<StoryDto> getAllStories(Pageable pageable) {
        log.debug("Getting all stories with pageable: {}", pageable);
        return storyRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(storyMapper::map);
    }

    public StoryDto getStoryById(Long id) {
        var story = storyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("story.not.found"));
        return storyMapper.map(story);
    }

    @Transactional
    public StoryDto createStory(StoryCreateDto createDTO) {
        log.debug("Creating story: {}", createDTO.getTitle());
        var story = storyMapper.map(createDTO);

        if (createDTO.getIsPublished()) {
            story.setPublishedAt(LocalDateTime.now(clock));
        }

        var savedStory = storyRepository.save(story);
        return storyMapper.map(savedStory);
    }

    @Transactional
    public StoryDto updateStory(Long id, StoryUpdateDto updateDTO) {
        log.debug("Updating story id: {}", id);
        var story = storyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("story.not.found"));

        JsonNullableUtils.ifPresent(updateDTO.getIsPublished(), newStatus -> {
            story.setIsPublished(newStatus);

            if (Boolean.TRUE.equals(newStatus) && story.getPublishedAt() == null) {
                story.setPublishedAt(LocalDateTime.now(clock));
            } else if (Boolean.FALSE.equals(newStatus)) {
                story.setPublishedAt(null);
            }
        });

        storyMapper.update(updateDTO, story);
        var savedStory = storyRepository.save(story);
        return storyMapper.map(savedStory);
    }

    @Transactional
    public void deleteStory(Long id) {
        log.debug("Deleting story id: {}", id);
        storyRepository.deleteById(id);
    }

    @Transactional
    public StoryDto togglePublish(Long id) {
        log.debug("Toggling publish for story id: {}", id);
        var story = storyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("story.not.found"));

        var newStatus = !story.getIsPublished();
        story.setIsPublished(newStatus);

        if (newStatus && story.getPublishedAt() == null) {
            story.setPublishedAt(LocalDateTime.now(clock));
        } else if (!newStatus) {
            story.setPublishedAt(null);
        }

        var savedStory = storyRepository.save(story);
        return storyMapper.map(savedStory);
    }

    public List<StoryDto> getHomepageStories() {
        return storyRepository.findByShowOnHomepageTrue(
                Sort.by(Sort.Direction.DESC, Story.FIELD_CREATED_AT)
                )
                .stream()
                .map(storyMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateStoryDisplayOrder(Long id, Integer displayOrder) {
        log.debug("Updating display order for story id: {} to {}", id, displayOrder);
        var story = storyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("story.not.found"));
        story.setDisplayOrder(displayOrder);
        storyRepository.save(story);
    }

    @Transactional
    public void toggleStoryHomepageVisibility(Long id) {
        log.debug("Toggling homepage visibility for story id: {}", id);
        var story = storyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("story.not.found"));
        story.setShowOnHomepage(!story.getShowOnHomepage());
        storyRepository.save(story);
    }
}
