package dev.elder.ms_user.producer.dto;

import java.util.UUID;

public record UserCreatedEvent(
        UUID userId,
        String email,
        String subject,
        String text
) {
}
