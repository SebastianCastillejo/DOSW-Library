package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.core.exception.UserNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.User;
import edu.eci.dosw.DOSW_Library.core.validator.UserValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private List<User> users = new ArrayList<>();

    public void addUser(String name, String id) {
        UserValidator.validate(name, id);
        users.add(new User(name, id));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User getUserById(String id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public void deleteUserId(String id) {
        if (users.stream().noneMatch(u -> u.getId().equals(id))) {
            throw new UserNotFoundException(id);
        }
        users.removeIf(u -> u.getId().equals(id));
    }

    public void updateUserId(String id, String name) {
        User user = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setName(name);
    }
}
