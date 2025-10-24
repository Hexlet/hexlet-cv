package io.hexlet.cv.mapper;

import io.hexlet.cv.dto.user.auth.RegistrationRequestDTO;
import io.hexlet.cv.dto.user.auth.RegistrationResponseDTO;
import io.hexlet.cv.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-28T14:50:31+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24.0.2 (Eclipse Adoptium)"
)
@Component
public class RegistrationMapperImpl extends RegistrationMapper {

    @Override
    public User map(RegistrationRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( dto.getEmail() );
        user.setFirstName( dto.getFirstName() );
        user.setLastName( dto.getLastName() );

        return user;
    }

    @Override
    public RegistrationResponseDTO map(User user) {
        if ( user == null ) {
            return null;
        }

        RegistrationResponseDTO registrationResponseDTO = new RegistrationResponseDTO();

        registrationResponseDTO.setId( user.getId() );
        registrationResponseDTO.setEmail( user.getEmail() );
        registrationResponseDTO.setFirstName( user.getFirstName() );
        registrationResponseDTO.setLastName( user.getLastName() );

        registrationResponseDTO.setRole( user.getRole().name().toLowerCase() );

        return registrationResponseDTO;
    }
}
