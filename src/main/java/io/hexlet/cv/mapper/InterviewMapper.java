package io.hexlet.cv.mapper;

import io.hexlet.cv.dto.interview.InterviewCreateDTO;
import io.hexlet.cv.dto.interview.InterviewDTO;
import io.hexlet.cv.dto.interview.InterviewUpdateDTO;
import io.hexlet.cv.dto.user.UserDTO;
import io.hexlet.cv.handler.exception.UserNotFoundException;
import io.hexlet.cv.model.Interview;
import io.hexlet.cv.model.User;
import io.hexlet.cv.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class InterviewMapper {
    @Autowired
    private UserRepository userRepository;

    @Mapping(target = "speaker", source = "speakerId", qualifiedByName = "speakerIdToSpeaker")
    @Mapping(target = "videoLink", source = "videoLink", qualifiedByName = "defaultVideoLink")
    @Mapping(target = "isPublished", source = "isPublished", qualifiedByName = "defaultIsPublished")
    public abstract Interview map(InterviewCreateDTO dto);

    @Mapping(target = "userInterviewSummary", source = "speaker", qualifiedByName = "speakerToSummary")
    public abstract InterviewDTO map(Interview model);

    @Mapping(target = "speaker", source = "userInterviewSummary", qualifiedByName = "summaryToSpeaker")
    public abstract Interview map(InterviewDTO dto);

    @Mapping(target = "speaker", ignore = true)
    @Mapping(target = "videoLink", expression = "java(mapVideoLinkForUpdate(dto.getVideoLink()))")
    @Mapping(target = "isPublished", expression = "java(mapIsPublishedForUpdate(dto.getIsPublished()))")
    public abstract void updateBasicFields(InterviewUpdateDTO dto, @MappingTarget Interview model);

    @Named("speakerIdToSpeaker")
    public User speakerIdToSpeaker(Long speakerId) {
        if (speakerId == null) {
            return null;
        }
        return userRepository.findById(speakerId)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + speakerId + " not found. " +
                        "Cannot convert speaker id to User."));
    }

    @Named("defaultVideoLink")
    public String defaultVideoLink(String videoLink) {
        return videoLink != null ? videoLink : "";
    }

    @Named("defaultIsPublished")
    public Boolean defaultPublished(Boolean published) {
        return published != null ? published : false;
    }

    @Named("speakerToSummary")
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

    @Named("summaryToSpeaker")
    public User summaryToSpeaker(UserDTO summary) {
        if (summary == null) {
            return null;
        }

        return userRepository.findById(summary.getId())
                .orElseThrow(() -> new UserNotFoundException("User with id: " + summary.getId() + " not found. " +
                        "Cannot convert id from summary to User."));
    }

    protected String mapVideoLinkForUpdate(JsonNullable<String> videoLink) {
        if (videoLink == null || !videoLink.isPresent()) {
            return null;
        }

        String link = videoLink.get();
        return link != null ? link : "";
    }

    protected Boolean mapIsPublishedForUpdate(JsonNullable<Boolean> published) {
        if (published == null || !published.isPresent()) {
            return null;
        }

        Boolean value = published.get();
        if (value == null) {
            throw new IllegalArgumentException("IsPublished flag in interview cannot be set to null");
        }

        return value;
    }

    public void updateInterview(InterviewUpdateDTO dto, Interview interview) {
        updateBasicFields(dto, interview);

        if (dto.getSpeakerId() != null && dto.getSpeakerId().isPresent()) {
            Long speakerIdValue = dto.getSpeakerId().get();
            if (speakerIdValue == null) {
                interview.setSpeaker(null);
            } else {
                User speaker = userRepository.findById(speakerIdValue)
                        .orElseThrow(() -> new UserNotFoundException("Speaker not found"));
                interview.setSpeaker(speaker);
            }
        }
    }
}
