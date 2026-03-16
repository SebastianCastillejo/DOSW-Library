package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.core.exception.UserNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    // ── Exitosos ──────────────────────────────

    @Test
    void testAddUserYGetById_Exitoso() {
        userService.addUser("Sebastian", "U001");
        User user = userService.getUserById("U001");
        assertNotNull(user);
        assertEquals("U001", user.getId());
        assertEquals("Sebastian", user.getName());
    }

    @Test
    void testGetAllUsers_RetornaListaConUsuarios() {
        userService.addUser("Sebastian", "U001");
        userService.addUser("Maria", "U002");
        List<User> lista = userService.getAllUsers();
        assertEquals(2, lista.size());
    }

    @Test
    void testGetAllUsers_ListaVacia() {
        List<User> lista = userService.getAllUsers();
        assertTrue(lista.isEmpty());
    }

    // ── Error ─────────────────────────────────

    @Test
    void testGetUserById_NoExiste_LanzaExcepcion() {
        assertThrows(UserNotFoundException.class, () -> userService.getUserById("NOEXISTE"));
    }

    @Test
    void testGetUserById_MensajeExcepcionCorrecto() {
        UserNotFoundException ex = assertThrows(UserNotFoundException.class,
                () -> userService.getUserById("X99"));
        assertTrue(ex.getMessage().contains("X99"));
    }
}