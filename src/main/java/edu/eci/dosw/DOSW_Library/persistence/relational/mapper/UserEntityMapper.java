package edu.eci.dosw.DOSW_Library.persistence.relational.mapper;

import edu.eci.dosw.DOSW_Library.core.model.User;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;
        return User.builder()
                .id(String.valueOf(entity.getId()))
                .name(entity.getName())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(entity.getRole().name())
                .build();
    }

    public UserEntity toEntity(User user) {
        if (user == null) return null;
        return UserEntity.builder()
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}