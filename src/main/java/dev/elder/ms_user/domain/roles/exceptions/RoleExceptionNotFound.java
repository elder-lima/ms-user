package dev.elder.ms_user.domain.roles.exceptions;

public class RoleExceptionNotFound extends RuntimeException{
    public RoleExceptionNotFound(String msg) {
        super(msg);
    }
}
