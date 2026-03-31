package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.controller.dto.UserDTO;
import edu.eci.dosw.DOSW_Library.controller.mapper.UserMapper;
import edu.eci.dosw.DOSW_Library.core.exception.UserNotFoundException;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.UserEntity;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.UserEntity.Role;
import edu.eci.dosw.DOSW_Library.persistence.relational.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // Solo LIBRARIAN puede registrar usuarios
    public UserDTO addUser(String name, String username, String password, String email, Role role) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }

        UserEntity entity = UserEntity.builder()
                .name(name)
                .username(username)
                .password(password) // En Parte 2 esto se encripta con BCrypt
                .email(email)
                .role(role)
                .build();

        return userMapper.toDTO(userRepository.save(entity));
    }

    // Solo LIBRARIAN puede ver todos los usuarios
    public List<UserDTO> getAllUsers() {
        return userMapper.toDTOList(userRepository.findAll());
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
    }

    // Solo LIBRARIAN puede eliminar usuarios
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(String.valueOf(id));
        }
        userRepository.deleteById(id);
    }

    // Solo LIBRARIAN puede actualizar usuarios
    public UserDTO updateUser(Long id, String name, String email) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));

        if (name != null && !name.isBlank()) user.setName(name);
        if (email != null && !email.isBlank()) {
            if (userRepository.existsByEmail(email)) {
                throw new IllegalArgumentException("Email already in use: " + email);
            }
            user.setEmail(email);
        }

        return userMapper.toDTO(userRepository.save(user));
    }

    // Método interno para LoanService
    public UserEntity getEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
    }
}