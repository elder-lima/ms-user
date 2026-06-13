package dev.elder.ms_user.domain.user.exception;

public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException(String msg) {
        super(msg);
    }
}
