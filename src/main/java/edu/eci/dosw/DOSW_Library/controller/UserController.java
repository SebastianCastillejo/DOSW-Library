package edu.eci.dosw.DOSW_Library.controller;

import edu.eci.dosw.DOSW_Library.controller.dto.UserDTO;
import edu.eci.dosw.DOSW_Library.core.service.UserService;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.UserEntity.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Punto 7: Solo LIBRARIAN registra nuevos usuarios
    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<UserDTO> addUser(@RequestParam String name,
                                           @RequestParam String username,
                                           @RequestParam String password,
                                           @RequestParam String email,
                                           @RequestParam Role role) {
        return ResponseEntity.ok(userService.addUser(name, username, password, email, role));
    }

    // Punto 7: Solo LIBRARIAN lista todos los usuarios
    @GetMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Punto 7: Restricción de acceso a info propia o admin
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')") // Operación administrativa
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,
                                              @RequestParam(required = false) String name,
                                              @RequestParam(required = false) String email) {
        return ResponseEntity.ok(userService.updateUser(id, name, email));
    }
}