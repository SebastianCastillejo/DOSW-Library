package edu.eci.dosw.DOSW_Library.core.exception;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String id) {
        super("El libro con id " + id + " no está disponible para préstamo.");
    }
}
