package io.hexlet.cv.service;

import io.hexlet.cv.dto.user.UserDTO;
import io.hexlet.cv.mapper.UserMapper;
import io.hexlet.cv.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public List<UserDTO> getPotentialInterviewSpeakers() {
        return userRepository.findAll()
                .stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }
}
