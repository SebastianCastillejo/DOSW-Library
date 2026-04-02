package edu.eci.dosw.DOSW_Library.security.service;

import edu.eci.dosw.DOSW_Library.core.model.User;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.UserEntity;
import edu.eci.dosw.DOSW_Library.persistence.repository.UserRepositoryPort;
import edu.eci.dosw.DOSW_Library.security.JwtService;
import edu.eci.dosw.DOSW_Library.security.dto.AuthResponse;
import edu.eci.dosw.DOSW_Library.security.dto.LoginRequest;
import edu.eci.dosw.DOSW_Library.security.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepositoryPort repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.username())
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role().name())
                .build();

        repository.save(user);

        UserEntity userEntity = UserEntity.builder()
                .username(request.username())
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();

        var jwtToken = jwtService.generateToken(userEntity);
        return new AuthResponse(jwtToken);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        User user = repository.findByUsername(request.username())
                .orElseThrow();

        UserEntity userEntity = UserEntity.builder()
                .username(user.getUsername())
                .role(UserEntity.Role.valueOf(user.getRole()))
                .build();

        var jwtToken = jwtService.generateToken(userEntity);
        return new AuthResponse(jwtToken);
    }
}