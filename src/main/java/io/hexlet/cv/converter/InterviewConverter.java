package io.hexlet.cv.converter;

import io.hexlet.cv.dto.interview.InterviewCreateDTO;
import io.hexlet.cv.dto.interview.InterviewDTO;
import io.hexlet.cv.dto.interview.InterviewUpdateDTO;
import io.hexlet.cv.dto.user.UserDTO;
import io.hexlet.cv.handler.exception.ResourceNotFoundException;
import io.hexlet.cv.handler.exception.UserNotFoundException;
import io.hexlet.cv.model.Interview;
import io.hexlet.cv.model.User;
import io.hexlet.cv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class InterviewConverter {
    private final UserRepository userRepository;

    public Interview convertCreateDtoToEntity(InterviewCreateDTO dto) {
        Interview model = new Interview();
        User userFromDto = speakerIdToSpeaker(dto.getSpeakerId());

        model.setTitle(dto.getTitle());
        model.setSpeaker(userFromDto);
        model.setVideoLink(dto.getVideoLink());
        model.setIsPublished(dto.getIsPublished());

        return model;
    }

    public InterviewDTO convertEntityToDto(Interview model) {
        UserDTO userDTO = speakerToSummary(model.getSpeaker());

        InterviewDTO dto = InterviewDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .speaker(userDTO)
                .videoLink(model.getVideoLink())
                .isPublished(model.getIsPublished())
                .build();

        return dto;
    }

    public Interview convertDtoToEntity(InterviewDTO dto) {
        Interview model = new Interview();
        User user = summaryToSpeaker(dto.getSpeaker());

        model.setId(dto.getId());
        model.setTitle(dto.getTitle());
        model.setSpeaker(user);
        model.setVideoLink(dto.getVideoLink());
        model.setIsPublished(dto.getIsPublished());

        return model;
    }

    public void updateEntityWithUpdateDto(InterviewUpdateDTO dto, Interview model) {
        if (dto.getTitle() != null && dto.getTitle().isPresent()) {
            String titleFromUpdateDto = dto.getTitle().get();
            if (titleFromUpdateDto == null) {
                throw new IllegalArgumentException("Title in InterviewUpdateDTO cannot be null");
            }
            model.setTitle(titleFromUpdateDto);
        }
        if (dto.getSpeakerId() != null && dto.getSpeakerId().isPresent()) {
            Long speakerIdFromDto = dto.getSpeakerId().get();
            model.setSpeaker(
                    speakerIdFromDto != null
                            ? userRepository.findById(speakerIdFromDto)
                                    .orElseThrow(() -> new ResourceNotFoundException("User with id "
                                            + speakerIdFromDto + " not found."))
                            : null);
        }
        if (dto.getVideoLink() != null && dto.getVideoLink().isPresent()) {
            String videoLinkFromDto = dto.getVideoLink().get();
            if (videoLinkFromDto == null) {
                throw new IllegalArgumentException("VideoLink in UpdateInterviewDto cannot be null");
            }
            model.setVideoLink(videoLinkFromDto);
        }
        if (dto.getIsPublished() != null && dto.getIsPublished().isPresent()) {
            Boolean isPublishedFromDto = dto.getIsPublished().get();
            if (isPublishedFromDto == null) {
                throw new IllegalArgumentException("IsPublished flag in InterviewUpdateDto cannot be null.");
            }
            model.setIsPublished(isPublishedFromDto);
        }
    }

    public User speakerIdToSpeaker(Long speakerId) {
        if (speakerId == null) {
            return null;
        }
        return userRepository.findById(speakerId)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + speakerId
                        + " not found. " + "Cannot convert speaker id to User."));
    }

    public UserDTO speakerToSummary(User speaker) {
        UserDTO result = new UserDTO();

        if (speaker == null) {
            return null;
        } else {
            result.setEmail(speaker.getEmail());
            result.setId(speaker.getId());
            result.setFirstName(speaker.getFirstName());
            result.setLastName(speaker.getLastName());
        }

        return result;
    }

    public User summaryToSpeaker(UserDTO speakerFromDTO) {
        if (speakerFromDTO == null || speakerFromDTO.getId() == null) {
            return null;
        }

        return userRepository.findById(speakerFromDTO.getId())
                .orElseThrow(() -> new UserNotFoundException("User with id: " + speakerFromDTO.getId()
                        + " not found. " + "Cannot convert id from speakerFromDTO to User."));
    }
}
