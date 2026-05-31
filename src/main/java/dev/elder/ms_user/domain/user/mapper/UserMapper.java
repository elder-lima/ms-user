package dev.elder.ms_user.domain.user.mapper;

import dev.elder.ms_user.domain.user.User;
import dev.elder.ms_user.domain.user.dto.CreateUser;
import dev.elder.ms_user.domain.user.dto.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(CreateUser dto, PasswordEncoder passwordEncoder) {
        return new User(dto.email(), passwordEncoder.encode(dto.senha()));
    }

    public UserResponse toDto(User user) {
        return new UserResponse(user.getUserId(), user.getEmail(), user.getRoles());
    }

}
