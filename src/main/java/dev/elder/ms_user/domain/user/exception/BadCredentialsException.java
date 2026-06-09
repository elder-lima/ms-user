package dev.elder.ms_user.domain.user.exception;

public class BadCredentialsException extends RuntimeException{
    public BadCredentialsException(String msg) {
        super(msg);
    }
}
