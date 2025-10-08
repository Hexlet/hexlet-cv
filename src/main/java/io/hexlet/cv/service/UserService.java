package io.hexlet.cv.service;

import io.hexlet.cv.dto.user.UserProfileDTO;
import io.hexlet.cv.handler.exception.UserNotFoundException;
import io.hexlet.cv.mapper.UserMapper;
import io.hexlet.cv.model.User;
import io.hexlet.cv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserProfileDTO getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email: \"" + email + "\" not found"));
        return userMapper.map(user);
    }
}
