package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.controller.dto.UserDTO;
import edu.eci.dosw.DOSW_Library.controller.mapper.UserMapper;
import edu.eci.dosw.DOSW_Library.core.exception.UserNotFoundException;
import edu.eci.dosw.DOSW_Library.persistence.entity.UserEntity;
import edu.eci.dosw.DOSW_Library.persistence.entity.UserEntity.Role;
import edu.eci.dosw.DOSW_Library.persistence.repository.UserRepository;
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
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UserEntity userEntity;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder()
                .id(1L)
                .name("Sebastian")
                .username("seba123")
                .password("pass123")
                .email("seba@mail.com")
                .role(Role.USER)
                .build();

        userDTO = UserDTO.builder()
                .id(1L)
                .name("Sebastian")
                .username("seba123")
                .email("seba@mail.com")
                .role(Role.USER)
                .build();
    }

    // ── addUser ────────────────────────────────────────────────────────────────

    @Test
    void testAddUser_Exitoso() {
        when(userRepository.existsByUsername("seba123")).thenReturn(false);
        when(userRepository.existsByEmail("seba@mail.com")).thenReturn(false);
        when(userRepository.save(any())).thenReturn(userEntity);
        when(userMapper.toDTO(userEntity)).thenReturn(userDTO);

        UserDTO result = userService.addUser("Sebastian", "seba123", "pass123", "seba@mail.com", Role.USER);

        assertNotNull(result);
        assertEquals("Sebastian", result.getName());
        verify(userRepository).save(any());
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

    // ── getUserById ────────────────────────────────────────────────────────────

    @Test
    void testGetUserById_Exitoso() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDTO(userEntity)).thenReturn(userDTO);

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("Sebastian", result.getName());
    }

    @Test
    void testGetUserById_NoExiste_LanzaExcepcion() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.getUserById(99L));
    }

    // ── getAllUsers ────────────────────────────────────────────────────────────

    @Test
    void testGetAllUsers_Exitoso() {
        UserEntity user2 = UserEntity.builder().id(2L).name("Maria")
                .username("maria99").password("pass").email("maria@mail.com").role(Role.USER).build();

        when(userRepository.findAll()).thenReturn(List.of(userEntity, user2));
        when(userMapper.toDTOList(any())).thenReturn(List.of(userDTO,
                UserDTO.builder().id(2L).name("Maria").build()));

        assertEquals(2, userService.getAllUsers().size());
    }

    @Test
    void testGetAllUsers_ListaVacia() {
        when(userRepository.findAll()).thenReturn(List.of());
        when(userMapper.toDTOList(any())).thenReturn(List.of());

        assertTrue(userService.getAllUsers().isEmpty());
    }

    // ── deleteUser ─────────────────────────────────────────────────────────────

    @Test
    void testDeleteUser_Exitoso() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    void testDeleteUser_NoExiste_LanzaExcepcion() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThrows(UserNotFoundException.class,
                () -> userService.deleteUser(99L));
    }

    // ── updateUser ─────────────────────────────────────────────────────────────

    @Test
    void testUpdateUser_Exitoso() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any())).thenReturn(userEntity);
        when(userMapper.toDTO(any())).thenReturn(userDTO);

        UserDTO result = userService.updateUser(1L, "NuevoNombre", null);

        assertNotNull(result);
        verify(userRepository).save(userEntity);
    }

    @Test
    void testUpdateUser_NoExiste_LanzaExcepcion() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.updateUser(99L, "Nombre", null));
    }

    @Test
    void testUpdateUser_EmailDuplicado_LanzaExcepcion() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userRepository.existsByEmail("otro@mail.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> userService.updateUser(1L, null, "otro@mail.com"));
    }
}