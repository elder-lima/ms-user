package dev.elder.ms_user.domain.user.exception;

public class UserConflicException extends RuntimeException{
    public UserConflicException(String msg) {
        super(msg);
    }
}
