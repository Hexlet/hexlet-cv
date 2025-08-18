package io.hexlet.cv.service;

import io.hexlet.cv.dto.registration.RegInputDTO;
import io.hexlet.cv.dto.registration.RegOutputDTO;
import io.hexlet.cv.exception.UserAlreadyExistsException;
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
public class UserService {

    public String passwordChange(UserPasswordDto userPasswordDto) {
        //сравнение старого пароля на корректность//
    private UserRepository userRepository;

    private RegistrationMapper registrationMapper;

    private BCryptPasswordEncoder encoder;

    public RegOutputDTO registration(RegInputDTO inputDTO) {

        userRepository.findByEmail(inputDTO.getEmail()).ifPresent(user -> {
            throw new UserAlreadyExistsException(user.getEmail());
        });

        var newUserData = registrationMapper.map(inputDTO);

        // шифруем пароль
        newUserData.setEncryptedPassword(encoder.encode(inputDTO.getPassword()));
        // роль по умолчанию
        newUserData.setRole(RoleType.CANDIDATE);

        userRepository.save(newUserData);
        var retDTO = registrationMapper.map(newUserData);
        // генерация токена
        var token = "тут выдается токен";
        retDTO.setToken(token);

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
