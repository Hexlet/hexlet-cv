package io.hexlet.cv.service;

import com.fasterxml.jackson.core.JsonPointer;
import io.hexlet.cv.dto.user.UserPasswordDto;
import io.hexlet.cv.handler.exception.MatchingPasswordsException;
import io.hexlet.cv.handler.exception.WrongPasswordException;
import io.hexlet.cv.model.User;
import io.hexlet.cv.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswordService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;
    
    public void passwordChange(UserPasswordDto userPasswordDto) {
        User user = userRepository.findByEmail(getCurrentUsername()).get();
        if (!encoder.matches(userPasswordDto.getOldPassword(), user.getEncryptedPassword())) {
            throw new WrongPasswordException("incorrect password entered");
        } else if (!userPasswordDto.getNewPassword().equals(
                userPasswordDto.getRepeatNewPassword())) {
            throw new MatchingPasswordsException("passwords must match");
        } else {
            user.setEncryptedPassword(encoder.encode(userPasswordDto.getNewPassword()));
            userRepository.save(user);
            System.out.println("password change success");
        }
    }

    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
}
