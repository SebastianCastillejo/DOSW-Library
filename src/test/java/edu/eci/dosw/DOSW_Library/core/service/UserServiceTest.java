package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.controller.dto.UserDTO;
import edu.eci.dosw.DOSW_Library.controller.mapper.UserMapper;
import edu.eci.dosw.DOSW_Library.core.exception.UserNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.User;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.UserEntity.Role;
import edu.eci.dosw.DOSW_Library.persistence.repository.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id("1")
                .name("Sebastian")
                .username("seba123")
                .password("pass123")
                .email("seba@mail.com")
                .role("USER")
                .build();

        userDTO = UserDTO.builder()
                .id("1L")
                .name("Sebastian")
                .username("seba123")
                .email("seba@mail.com")
                .role(Role.USER)
                .build();
    }

    @Test
    void testAddUser_Exitoso() {
        when(userRepository.existsByUsername("seba123")).thenReturn(false);
        when(userRepository.existsByEmail("seba@mail.com")).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.addUser("Sebastian", "seba123", "pass123", "seba@mail.com", Role.USER);

        assertNotNull(result);
        assertEquals("Sebastian", result.getName());
    }

    @Test
    void testAddUser_UsernameYaExiste_LanzaExcepcion() {
        when(userRepository.existsByUsername("seba123")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> userService.addUser("Sebastian", "seba123", "pass123", "seba@mail.com", Role.USER));
    }

    @Test
    void testAddUser_EmailYaExiste_LanzaExcepcion() {
        when(userRepository.existsByUsername("seba123")).thenReturn(false);
        when(userRepository.existsByEmail("seba@mail.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> userService.addUser("Sebastian", "seba123", "pass123", "seba@mail.com", Role.USER));
    }

    @Test
    void testGetUserById_Exitoso() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.getUserById("1");

        assertNotNull(result);
        assertEquals("Sebastian", result.getName());
    }

    @Test
    void testGetUserById_NoExiste_LanzaExcepcion() {
        when(userRepository.findById("99")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.getUserById("99"));
    }

    @Test
    void testGetAllUsers_Exitoso() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDTOList(any())).thenReturn(List.of(userDTO));

        assertEquals(1, userService.getAllUsers().size());
    }

    @Test
    void testGetAllUsers_ListaVacia() {
        when(userRepository.findAll()).thenReturn(List.of());
        when(userMapper.toDTOList(any())).thenReturn(List.of());

        assertTrue(userService.getAllUsers().isEmpty());
    }

    @Test
    void testDeleteUser_Exitoso() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        userService.deleteUser("1");

        verify(userRepository).delete("1");
    }

    @Test
    void testDeleteUser_NoExiste_LanzaExcepcion() {
        when(userRepository.findById("99")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.deleteUser("99"));
    }

    @Test
    void testUpdateUser_Exitoso() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.toDTO(any())).thenReturn(userDTO);

        UserDTO result = userService.updateUser("1", "NuevoNombre", null);

        assertNotNull(result);
        verify(userRepository).save(user);
    }

    @Test
    void testUpdateUser_NoExiste_LanzaExcepcion() {
        when(userRepository.findById("99")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.updateUser("99", "Nombre", null));
    }
}