package dev.elder.ms_user.controller;

import dev.elder.ms_user.domain.user.dto.CreateUser;
import dev.elder.ms_user.domain.user.dto.LoginRequest;
import dev.elder.ms_user.domain.user.dto.LoginResponse;
import dev.elder.ms_user.domain.user.dto.UserResponse;
import dev.elder.ms_user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/user/me/{id}")
    public ResponseEntity<UserResponse> me(@PathVariable("id") UUID userId, JwtAuthenticationToken token) {
        return ResponseEntity.ok(userService.me(userId, token));
    }


}
