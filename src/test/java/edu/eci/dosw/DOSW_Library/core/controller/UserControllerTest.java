package edu.eci.dosw.DOSW_Library.core.controller;

import edu.eci.dosw.DOSW_Library.controller.UserController;
import edu.eci.dosw.DOSW_Library.controller.dto.UserDTO;
import edu.eci.dosw.DOSW_Library.core.exception.UserNotFoundException;
import edu.eci.dosw.DOSW_Library.core.service.UserService;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.UserEntity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
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
        when(userService.addUser(any(), any(), any(), any(), any())).thenReturn(userDTO);
        ResponseEntity<UserDTO> response = userController.addUser("Sebastian", "seba123", "pass123", "seba@mail.com", Role.USER);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetAllUsers_Exitoso() {
        when(userService.getAllUsers()).thenReturn(List.of(userDTO));
        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetAllUsers_ListaVacia() {
        when(userService.getAllUsers()).thenReturn(List.of());
        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetUserById_Exitoso() {
        when(userService.getUserById("1")).thenReturn(userDTO);
        ResponseEntity<UserDTO> response = userController.getUserById("1");
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Sebastian", response.getBody().getName());
    }

    @Test
    void testGetUserById_NoExiste_LanzaExcepcion() {
        when(userService.getUserById("99")).thenThrow(new UserNotFoundException("99"));
        assertThrows(UserNotFoundException.class, () -> userController.getUserById("99"));
    }

    @Test
    void testDeleteUser_Exitoso() {
        doNothing().when(userService).deleteUser("1");
        ResponseEntity<Void> response = userController.deleteUser("1");
        assertEquals(200, response.getStatusCode().value());
        verify(userService).deleteUser("1");
    }

    @Test
    void testDeleteUser_NoExiste_LanzaExcepcion() {
        doThrow(new UserNotFoundException("99")).when(userService).deleteUser("99");
        assertThrows(UserNotFoundException.class, () -> userController.deleteUser("99"));
    }

    @Test
    void testUpdateUser_Exitoso() {
        when(userService.updateUser(eq("1"), any(), any())).thenReturn(userDTO);
        ResponseEntity<UserDTO> response = userController.updateUser("1", "NuevoNombre", null);
        assertEquals(200, response.getStatusCode().value());
    }
}