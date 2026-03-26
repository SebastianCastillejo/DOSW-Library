package edu.eci.dosw.DOSW_Library.security.controller;

import edu.eci.dosw.DOSW_Library.security.dto.AuthResponse;
import edu.eci.dosw.DOSW_Library.security.dto.LoginRequest;
import edu.eci.dosw.DOSW_Library.security.dto.RegisterRequest;
import edu.eci.dosw.DOSW_Library.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
