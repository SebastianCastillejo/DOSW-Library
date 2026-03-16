package edu.eci.dosw.DOSW_Library.core.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String id) {
        super("Usuario con id " + id + " no encontrado.");
    }
}
