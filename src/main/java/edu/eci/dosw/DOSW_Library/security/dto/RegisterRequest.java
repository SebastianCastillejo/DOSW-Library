package edu.eci.dosw.DOSW_Library.security.dto;

import edu.eci.dosw.DOSW_Library.persistence.relational.entity.UserEntity;

public record RegisterRequest(
        String username,
        String password,
        String name,
        String email,
        UserEntity.Role role
) {}