package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.core.exception.UserNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void testAddUser_Exitoso() {
        userService.addUser("Sebastian", "U001");
        User user = userService.getUserById("U001");
        assertNotNull(user);
        assertEquals("Sebastian", user.getName());
    }

    @Test
    void testAddUser_NombreVacio_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> userService.addUser("", "U001"));
    }

    @Test
    void testAddUser_IdVacio_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> userService.addUser("Sebastian", ""));
    }

    @Test
    void testGetUserById_Exitoso() {
        userService.addUser("Sebastian", "U001");
        assertEquals("U001", userService.getUserById("U001").getId());
    }

    @Test
    void testGetUserById_NoExiste_LanzaExcepcion() {
        assertThrows(UserNotFoundException.class,
                () -> userService.getUserById("NOEXISTE"));
    }

    @Test
    void testGetAllUsers_Exitoso() {
        userService.addUser("Sebastian", "U001");
        userService.addUser("Maria", "U002");
        assertEquals(2, userService.getAllUsers().size());
    }

    @Test
    void testGetAllUsers_ListaVacia() {
        assertTrue(userService.getAllUsers().isEmpty());
    }

    @Test
    void testDeleteUser_Exitoso() {
        userService.addUser("Sebastian", "U001");
        userService.deleteUserId("U001");
        assertThrows(UserNotFoundException.class,
                () -> userService.getUserById("U001"));
    }

    @Test
    void testDeleteUser_NoExiste_LanzaExcepcion() {
        assertThrows(UserNotFoundException.class,
                () -> userService.deleteUserId("NOEXISTE"));
    }

    @Test
    void testUpdateUser_Exitoso() {
        userService.addUser("Sebastian", "U001");
        userService.updateUserId("U001", "Maria");
        assertEquals("Maria", userService.getUserById("U001").getName());
    }

    @Test
    void testUpdateUser_NoExiste_LanzaExcepcion() {
        assertThrows(UserNotFoundException.class,
                () -> userService.updateUserId("NOEXISTE", "Maria"));
    }
}