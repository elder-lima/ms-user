package dev.elder.ms_user.controller.exceptions;

import dev.elder.ms_user.domain.roles.exceptions.RoleExceptionNotFound;
import dev.elder.ms_user.domain.user.exception.AccessDeniedException;
import dev.elder.ms_user.domain.user.exception.UserConflicException;
import dev.elder.ms_user.domain.user.exception.BadCredentialsException;
import dev.elder.ms_user.domain.user.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class TratadorGlobalException {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErroApi> badCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErroApi(
                        Instant.now(),
                        401,
                        "Credenciais Inválidas",
                        ex.getMessage(),
                        request.getRequestURI(),
                        List.of()
                )
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroApi> accessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErroApi(
                        Instant.now(),
                        401,
                        "Acesso Negado.",
                        ex.getMessage(),
                        request.getRequestURI(),
                        List.of()
                )
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErroApi> userNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErroApi(
                        Instant.now(),
                        404,
                        "User não Encontrado",
                        ex.getMessage(),
                        request.getRequestURI(),
                        List.of()
                )
        );
    }

    @ExceptionHandler(RoleExceptionNotFound.class)
    public ResponseEntity<ErroApi> roleNotFoundException(RoleExceptionNotFound ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErroApi(
                        Instant.now(),
                        404,
                        "Role Not found.",
                        ex.getMessage(),
                        request.getRequestURI(),
                        List.of()
                )
        );
    }

    @ExceptionHandler(UserConflicException.class)
    public ResponseEntity<ErroApi> userConflicException(UserConflicException ex, HttpServletRequest request) {
        return ResponseEntity.status(409).body(
                new ErroApi(
                        Instant.now(),
                        409,
                        "Conflict Exception",
                        ex.getMessage(),
                        request.getRequestURI(),
                        List.of()
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroApi> erroValidacoes(MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<ErroApi.ErroCampo> campos = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new ErroApi.ErroCampo(fe.getField(), mensagem(fe)))
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErroApi(
                        Instant.now(),
                        400,
                        "Requisição Inválida.",
                        "Erro de validação.",
                        request.getRequestURI(),
                        campos
                )
        );
    }

    private String mensagem(FieldError fe) {
        return fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "inválido";
    }


}
