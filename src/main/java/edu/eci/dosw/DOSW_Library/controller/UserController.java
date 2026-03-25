package edu.eci.dosw.DOSW_Library.controller;

import edu.eci.dosw.DOSW_Library.controller.dto.UserDTO;
import edu.eci.dosw.DOSW_Library.core.service.UserService;
import edu.eci.dosw.DOSW_Library.persistence.entity.UserEntity.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // LIBRARIAN only
    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestParam String name,
                                           @RequestParam String username,
                                           @RequestParam String password,
                                           @RequestParam String email,
                                           @RequestParam Role role) {
        return ResponseEntity.ok(userService.addUser(name, username, password, email, role));
    }

    // LIBRARIAN only
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // LIBRARIAN only (or own user in Part 2)
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // LIBRARIAN only
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    // LIBRARIAN only
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,
                                              @RequestParam(required = false) String name,
                                              @RequestParam(required = false) String email) {
        return ResponseEntity.ok(userService.updateUser(id, name, email));
    }
}