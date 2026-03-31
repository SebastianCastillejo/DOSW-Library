package edu.eci.dosw.DOSW_Library.persistence.nonrelational.mapper;

import edu.eci.dosw.DOSW_Library.core.model.User;
import edu.eci.dosw.DOSW_Library.persistence.nonrelational.document.UserDocument;
import org.springframework.stereotype.Component;

@Component
public class UserDocumentMapper {

    public User toDomain(UserDocument document) {
        if (document == null) return null;
        return User.builder()
                .id(document.getId())
                .name(document.getName())
                .username(document.getUsername())
                .email(document.getEmail())
                .password(document.getPassword())
                .role(document.getRole())
                .build();
    }

    public UserDocument toDocument(User user) {
        if (user == null) return null;
        return UserDocument.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}