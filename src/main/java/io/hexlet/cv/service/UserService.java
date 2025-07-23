package io.hexlet.cv.service;

import io.hexlet.cv.dto.registration.RegInputDTO;
import io.hexlet.cv.dto.registration.RegOutputDTO;
import io.hexlet.cv.exception.UserAlreadyExistsException;
import io.hexlet.cv.mapper.RegistrationMapper;
import io.hexlet.cv.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private RegistrationMapper registrationMapper;

    private BCryptPasswordEncoder encoder;

    public RegOutputDTO registration(RegInputDTO inputDTO) {

        userRepository.findByEmail(inputDTO.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException(user.getEmail());
                });

        var newUserData = registrationMapper.map(inputDTO);

        // шифруем пароль
        newUserData.setEncryptedPassword(encoder.encode(inputDTO.getPassword()));
        newUserData.setRole("candidate");

        userRepository.save(newUserData);
        return registrationMapper.map(newUserData);
    }
}
