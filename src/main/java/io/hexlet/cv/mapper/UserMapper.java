package io.hexlet.cv.mapper;

import io.hexlet.cv.dto.user.UserDTO;
import io.hexlet.cv.model.User;
import io.hexlet.cv.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {
    @Autowired
    private UserRepository userRepository;

    public abstract UserDTO map(User model);
}
