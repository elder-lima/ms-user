package dev.elder.ms_user.domain.user.dto;

public record LoginResponse(
        String accessToken,
        Long expiresIn
) {
}
