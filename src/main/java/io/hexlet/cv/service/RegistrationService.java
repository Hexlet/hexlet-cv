package io.hexlet.cv.service;

import io.hexlet.cv.dto.user.RegistrationRequestDTO;
import io.hexlet.cv.dto.user.RegistrationResponseDTO;
import io.hexlet.cv.handler.exception.UserAlreadyExistsException;
import io.hexlet.cv.mapper.RegistrationMapper;
import io.hexlet.cv.model.enums.RoleType;
import io.hexlet.cv.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import io.hexlet.cv.dto.user.UserPasswordDto;
import io.hexlet.cv.handler.exception.MatchingPasswordsException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    public String passwordChange(UserPasswordDto userPasswordDto) {
        //сравнение старого пароля на корректность//
    private UserRepository userRepository;
    private RegistrationMapper registrationMapper;
    private BCryptPasswordEncoder encoder;

    public RegistrationResponseDTO registration(RegistrationRequestDTO inputDTO) {

        userRepository.findByEmail(inputDTO.getEmail()).ifPresent(user -> {
            throw new UserAlreadyExistsException("Пользователь с email " + user.getEmail() + " уже существует");
        });

        var newUserData = registrationMapper.map(inputDTO);

        newUserData.setEncryptedPassword(encoder.encode(inputDTO.getPassword()));
        // роль по умолчанию
        newUserData.setRole(RoleType.CANDIDATE);

        userRepository.save(newUserData);
        var retDTO = registrationMapper.map(newUserData);

        return retDTO;
    }
        public String passwordChange(UserPasswordDto userPasswordDto) {
            //сравнение старого пароля на корректность//

            if (!userPasswordDto.getNewPassword().equals(
                userPasswordDto.getRepeatNewPassword())) {
            throw new MatchingPasswordsException("passwords must match");
        } else {
            return "";//Сохранение в репозиторий//
        }
    }
}
