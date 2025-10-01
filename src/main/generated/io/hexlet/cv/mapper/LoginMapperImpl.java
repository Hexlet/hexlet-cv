package io.hexlet.cv.mapper;

import io.hexlet.cv.dto.user.auth.LoginResponseDTO;
import io.hexlet.cv.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-28T14:50:31+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.2 (Eclipse Adoptium)"
)
@Component
public class LoginMapperImpl extends LoginMapper {

    @Override
    public LoginResponseDTO map(User user) {
        if ( user == null ) {
            return null;
        }

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();

        loginResponseDTO.setId( user.getId() );
        loginResponseDTO.setEmail( user.getEmail() );
        loginResponseDTO.setFirstName( user.getFirstName() );
        loginResponseDTO.setLastName( user.getLastName() );

        loginResponseDTO.setRole( user.getRole().name().toLowerCase() );

        return loginResponseDTO;
    }
}
