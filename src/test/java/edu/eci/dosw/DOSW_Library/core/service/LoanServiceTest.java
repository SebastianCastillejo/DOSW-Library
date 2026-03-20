package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.core.exception.BookNotAvailableException;
import edu.eci.dosw.DOSW_Library.core.exception.LoanNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoanServiceTest {

    private LoanService loanService;
    private Book book;
    private User user;

    @BeforeEach
    void setUp() {
        loanService = new LoanService();
        book = new Book("Clean Code", "Robert Martin", "B001");
        user = new User("Sebastian", "U001");
    }

    @Test
    void testLoanBook_Exitoso() {
        Loan loan = loanService.loanBook(book, user);
        assertNotNull(loan);
        assertEquals(Status.ACTIVE, loan.getStatus());
        assertNotNull(loan.getLoanDate());
    }

    @Test
    void testLoanBook_LibroNoDisponible_LanzaExcepcion() {
        loanService.loanBook(book, user);
        assertThrows(BookNotAvailableException.class,
                () -> loanService.loanBook(book, user));
    }

    @Test
    void testLoanBook_BookNulo_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> loanService.loanBook(null, user));
    }

    @Test
    void testLoanBook_UserNulo_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> loanService.loanBook(book, null));
    }

    @Test
    void testIsBookAvailable_SinPrestamos() {
        assertTrue(loanService.isBookAvailable(book));
    }

    @Test
    void testIsBookAvailable_ConPrestamoActivo() {
        loanService.loanBook(book, user);
        assertFalse(loanService.isBookAvailable(book));
    }

    @Test
    void testIsBookAvailable_DespuesDevolucion() {
        loanService.loanBook(book, user);
        loanService.returnBook(book, user);
        assertTrue(loanService.isBookAvailable(book));
    }

    @Test
    void testGetActiveLoans_Exitoso() {
        loanService.loanBook(book, user);
        assertEquals(1, loanService.getActiveLoans().size());
    }

    @Test
    void testGetActiveLoans_DespuesDevolucion_Vacia() {
        loanService.loanBook(book, user);
        loanService.returnBook(book, user);
        assertTrue(loanService.getActiveLoans().isEmpty());
    }

    @Test
    void testReturnBook_SinPrestamoActivo_LanzaExcepcion() {
        assertThrows(LoanNotFoundException.class,
                () -> loanService.returnBook(book, user));
    }

    @Test
    void testReturnBook_MensajeExcepcionCorrecto() {
        LoanNotFoundException ex = assertThrows(LoanNotFoundException.class,
                () -> loanService.returnBook(book, user));
        assertTrue(ex.getMessage().contains("B001"));
        assertTrue(ex.getMessage().contains("U001"));
    }
}