package dev.elder.ms_user.service;

import dev.elder.ms_user.domain.roles.Roles;
import dev.elder.ms_user.domain.roles.enums.RoleName;
import dev.elder.ms_user.domain.user.User;
import dev.elder.ms_user.domain.user.dto.CreateUser;
import dev.elder.ms_user.domain.user.dto.LoginRequest;
import dev.elder.ms_user.domain.user.dto.LoginResponse;
import dev.elder.ms_user.domain.user.dto.UserResponse;
import dev.elder.ms_user.domain.user.exception.AccessDeniedException;
import dev.elder.ms_user.domain.user.exception.UserConflicException;
import dev.elder.ms_user.domain.user.exception.BadCredentialsException;
import dev.elder.ms_user.domain.user.exception.UserNotFoundException;
import dev.elder.ms_user.domain.user.mapper.UserMapper;
import dev.elder.ms_user.producer.UserCreatedProducer;
import dev.elder.ms_user.producer.dto.UserCreatedEvent;
import dev.elder.ms_user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final UserMapper userMapper;
    private final UserCreatedProducer producer;

    public UserService(UserRepository userRepository, RoleService roleService, BCryptPasswordEncoder passwordEncoder, JwtEncoder jwtEncoder, UserMapper userMapper, UserCreatedProducer producer) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.userMapper = userMapper;
        this.producer = producer;
    }

    @Transactional
    public void register(CreateUser dto) {

        verificarEmail(dto.email());
        Roles role = roleService.findByNome(RoleName.BASIC);

        User user = userMapper.toEntity(dto, passwordEncoder);
        user.addRoles(Set.of(role));
        userRepository.save(user);

        producer.publish(
                "user.created",
                new UserCreatedEvent(
                        user.getUserId(),
                        user.getEmail()
                )
        );

    }

    private void verificarEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new UserConflicException("Email já cadastrado!");
        }
    }

    public UserResponse findById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User não Encontrado. Id:" + id));
        return userMapper.toDto(user);
    }

    public UserResponse me(UUID userId, JwtAuthenticationToken token) {
        UUID userIdToken = UUID.fromString(token.getName());
        if (!userId.equals(userIdToken)) {
            throw new AccessDeniedException("Acesso negado.");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User não Encontrado. Id:"+userId));
        return userMapper.toDto(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow(() -> new BadCredentialsException("Email ou Senha Inválidos!"));

        if (!user.loginEstaCorreto(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("Email ou Senha Inválidos!");
        }

        var now = Instant.now();
        var expiresIn = 1000L;

        var scopes = user.getRoles().stream().map(role -> role.getNome().name()).collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.getUserId().toString())
                .expiresAt(now.plusSeconds(expiresIn))
                .issuedAt(now)
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        producer.publish(
                "user.logged",
                new UserCreatedEvent(
                        user.getUserId(),
                        user.getEmail()
                )
        );

        return new LoginResponse(jwtValue, expiresIn);
    }

}
