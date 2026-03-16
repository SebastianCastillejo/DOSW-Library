package edu.eci.dosw.DOSW_Library.core.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String id) {
        super("Libro con id " + id + " no encontrado.");
    }
}
