package dev.elder.ms_user.controller;

import dev.elder.ms_user.domain.user.dto.CreateUser;
import dev.elder.ms_user.domain.user.dto.LoginRequest;
import dev.elder.ms_user.domain.user.dto.LoginResponse;
import dev.elder.ms_user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok().body(userService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registro(@RequestBody @Valid CreateUser createUser) {
        userService.register(createUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
