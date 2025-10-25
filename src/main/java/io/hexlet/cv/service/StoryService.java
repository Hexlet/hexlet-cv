package io.hexlet.cv.service;

import io.hexlet.cv.dto.marketing.StoryCreateDTO;
import io.hexlet.cv.dto.marketing.StoryDTO;
import io.hexlet.cv.dto.marketing.StoryUpdateDTO;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.mapper.StoryMapper;
import io.hexlet.cv.repository.StoryRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StoryService {

    private final StoryRepository storyRepository;
    private final StoryMapper storyMapper;

    public List<StoryDTO> getAllStories() {
        return storyRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(storyMapper::map)
                .collect(Collectors.toList());
    }

    public StoryDTO getStoryById(Long id) {
        var story = storyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found"));
        return storyMapper.map(story);
    }

    public StoryDTO getPublishedStoryById(Long id) {
        var story = storyRepository.findByIdAndIsPublishedTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Published story not found"));
        return storyMapper.map(story);
    }

    @Transactional
    public StoryDTO createStory(StoryCreateDTO createDTO) {
        var story = storyMapper.map(createDTO);
        var savedStory = storyRepository.save(story);
        return storyMapper.map(savedStory);
    }

    @Transactional
    public StoryDTO updateStory(Long id, StoryUpdateDTO updateDTO) {
        var story = storyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found"));

        if (updateDTO.getIsPublished() != null && updateDTO.getIsPublished().isPresent()) {
            var newStatus = updateDTO.getIsPublished().get();
            story.setIsPublished(newStatus);

            if (newStatus && story.getPublishedAt() == null) {
                story.setPublishedAt(LocalDateTime.now());
            } else if (!newStatus) {
                story.setPublishedAt(null);
            }
        }
        storyMapper.update(updateDTO, story);
        var savedStory = storyRepository.save(story);
        return storyMapper.map(savedStory);
    }

    @Transactional
    public void deleteStory(Long id) {
        storyRepository.deleteById(id);
    }

    @Transactional
    public StoryDTO togglePublish(Long id) {
        var story = storyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found"));

        var newStatus = !story.getIsPublished();
        story.setIsPublished(newStatus);

        if (newStatus && story.getPublishedAt() == null) {
            story.setPublishedAt(LocalDateTime.now());
        } else if (!newStatus) {
            story.setPublishedAt(null);
        }

        var savedStory = storyRepository.save(story);
        return storyMapper.map(savedStory);
    }

    public List<StoryDTO> getHomepageStories() {
        return storyRepository.findByShowOnHomepageTrueOrderByDisplayOrderAsc()
                .stream()
                .map(storyMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateStoryDisplayOrder(Long id, Integer displayOrder) {
        var story = storyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found"));
        story.setDisplayOrder(displayOrder);
        storyRepository.save(story);
    }

    @Transactional
    public void toggleStoryHomepageVisibility(Long id) {
        var story = storyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found"));
        story.setShowOnHomepage(!story.getShowOnHomepage());
        storyRepository.save(story);
    }
}
