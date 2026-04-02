package edu.eci.dosw.DOSW_Library.core.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleBookNotFound() {
        ResponseEntity<Map<String, Object>> response = handler.handleBookNotFound(new BookNotFoundException("B001"));
        assertEquals(404, response.getStatusCode().value());
        assertTrue(response.getBody().get("message").toString().contains("B001"));
    }

    @Test
    void testHandleUserNotFound() {
        ResponseEntity<Map<String, Object>> response = handler.handleUserNotFound(new UserNotFoundException("U001"));
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testHandleBookNotAvailable() {
        ResponseEntity<Map<String, Object>> response = handler.handleBookNotAvailable(new BookNotAvailableException("B001"));
        assertEquals(409, response.getStatusCode().value());
    }

    @Test
    void testHandleLoanNotFound() {
        ResponseEntity<Map<String, Object>> response = handler.handleLoanNotFound(new LoanNotFoundException("B001", "U001"));
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void testHandleGeneral() {
        ResponseEntity<Map<String, Object>> response = handler.handleGeneral(new Exception("error inesperado"));
        assertEquals(500, response.getStatusCode().value());
        assertEquals("Error interno del servidor.", response.getBody().get("message"));
    }
}