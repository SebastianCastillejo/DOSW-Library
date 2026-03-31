package edu.eci.dosw.DOSW_Library.security.service;

import edu.eci.dosw.DOSW_Library.persistence.relational.entity.UserEntity;
import edu.eci.dosw.DOSW_Library.persistence.relational.repository.UserRepository;
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

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        var user = UserEntity.builder()
                .username(request.username())
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password())) // Ciframos la clave
                .role(request.role())
                .build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    public AuthResponse login(LoginRequest request) {
        // El AuthenticationManager usa el Provider que configuramos para validar
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        var user = repository.findByUsername(request.username())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user); // Genera token con ID y Rol
        return new AuthResponse(jwtToken);
    }
}