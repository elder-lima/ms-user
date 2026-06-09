package dev.elder.ms_user.controller.exceptions;

import java.time.Instant;
import java.util.List;

public record ErroApi(
        Instant timestamp,
        int status,
        String erro,
        String mensagem,
        String caminho,
        List<ErroCampo> erros
) {
    public record ErroCampo(String campo, String mensagem) {

    }
}
