package io.hexlet.cv.service;

import io.hexlet.cv.dto.marketing.StoryCreateDTO;
import io.hexlet.cv.dto.marketing.StoryDTO;
import io.hexlet.cv.dto.marketing.StoryUpdateDTO;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.mapper.StoryMapper;
import io.hexlet.cv.repository.StoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        if (updateDTO.getIsPublished() != null) {
            var newStatus = updateDTO.getIsPublished();
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

    public List<StoryDTO> getHomeStories() {
        return storyRepository.findByShowOnHomepageTrueOrderByDisplayOrderAsc()
                .stream()
                .map(storyMapper::map)
                .collect(Collectors.toList());
    }
}
