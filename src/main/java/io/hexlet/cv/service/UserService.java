package io.hexlet.cv.service;

import io.hexlet.cv.dto.registration.RegInputDTO;
import io.hexlet.cv.dto.registration.RegOutputDTO;
import io.hexlet.cv.exception.UserAlreadyExistsException;
import io.hexlet.cv.mapper.RegistrationMapper;
import io.hexlet.cv.model.enums.RoleType;
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

        userRepository.findByEmail(inputDTO.getEmail()).ifPresent(user -> {
            throw new UserAlreadyExistsException(user.getEmail());
        });

        // ✅ Вариант 1: Используем сеттеры (проще)
        var newUser = registrationMapper.map(inputDTO);
        newUser.setPassword(encoder.encode(inputDTO.getPassword())); // ✅ правильный сеттер
        newUser.setRole(RoleType.CANDIDATE); // ✅ правильный сеттер
        newUser.setEnabled(true);

        userRepository.save(newUser);
        var retDTO = registrationMapper.map(newUser);

        // TODO: реализовать генерацию токена (JWT или другой)
        var token = "тут выдается токен";
        retDTO.setToken(token);

        return retDTO;

//        var newUserData = registrationMapper.map(inputDTO);
//
//        // шифруем пароль
//        newUserData.setEncryptedPassword(encoder.encode(inputDTO.getPassword()));
//        // роль по умолчанию
//        newUserData.setRole(RoleType.CANDIDATE);
//
//        userRepository.save(newUserData);
//        var retDTO = registrationMapper.map(newUserData);
//        // генерация токена
//        var token = "тут выдается токен";
//        retDTO.setToken(token);
//
//        return retDTO;
    }
}
