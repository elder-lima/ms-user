package dev.elder.ms_user.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUser(

        @NotBlank(message = "Email é Obrigatório")
        @Email
        @Size(max = 100)
        String email,
        @NotBlank(message = "Senha é Obrigatória")
        String senha

) {
}
