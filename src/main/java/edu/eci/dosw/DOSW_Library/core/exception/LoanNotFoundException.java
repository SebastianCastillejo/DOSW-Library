package edu.eci.dosw.DOSW_Library.core.exception;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(String bookId, String userId) {
        super("No se encontró préstamo activo del libro " + bookId + " para el usuario " + userId + ".");
    }
}
