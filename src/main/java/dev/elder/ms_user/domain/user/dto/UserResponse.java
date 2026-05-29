package dev.elder.ms_user.domain.user.dto;

import dev.elder.ms_user.domain.roles.Roles;

import java.util.Set;
import java.util.UUID;

public record UserResponse(
        UUID userId,
        String email,
        Set<Roles> roles
) {
}
