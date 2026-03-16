package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.core.exception.BookNotAvailableException;
import edu.eci.dosw.DOSW_Library.core.exception.LoanNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    // ── Exitosos ──────────────────────────────

    @Test
    void testLoanBook_Exitoso() {
        Loan loan = loanService.loanBook(book, user);
        assertNotNull(loan);
        assertEquals(Status.ACTIVE, loan.getStatus());
        assertEquals(book.getId(), loan.getBook().getId());
        assertEquals(user.getId(), loan.getUser().getId());
        assertNotNull(loan.getLoanDate());
    }

    @Test
    void testIsBookAvailable_CuandoNoHayPrestamos() {
        assertTrue(loanService.isBookAvailable(book));
    }

    @Test
    void testIsBookAvailable_CuandoEstaActivo_RetornaFalse() {
        loanService.loanBook(book, user);
        assertFalse(loanService.isBookAvailable(book));
    }

    @Test
    void testIsBookAvailable_DespuesDeDevolver_RetornaTrue() {
        loanService.loanBook(book, user);
        loanService.returnBook(book, user);
        assertTrue(loanService.isBookAvailable(book));
    }

    @Test
    void testGetActiveLoans_RetornaPrestamoActivo() {
        loanService.loanBook(book, user);
        List<Loan> activos = loanService.getActiveLoans();
        assertEquals(1, activos.size());
        assertEquals(Status.ACTIVE, activos.get(0).getStatus());
    }

    @Test
    void testGetActiveLoans_DespuesDevolucion_ListaVacia() {
        loanService.loanBook(book, user);
        loanService.returnBook(book, user);
        List<Loan> activos = loanService.getActiveLoans();
        assertTrue(activos.isEmpty());
    }

    @Test
    void testReturnBook_CambiaStatusAReturned() {
        loanService.loanBook(book, user);
        loanService.returnBook(book, user);
        List<Loan> activos = loanService.getActiveLoans();
        assertTrue(activos.isEmpty());
    }

    // ── Error ─────────────────────────────────

    @Test
    void testLoanBook_LibroNoDisponible_LanzaExcepcion() {
        loanService.loanBook(book, user);
        assertThrows(BookNotAvailableException.class, () -> loanService.loanBook(book, user));
    }

    @Test
    void testLoanBook_MensajeExcepcionCorrecto() {
        loanService.loanBook(book, user);
        BookNotAvailableException ex = assertThrows(BookNotAvailableException.class,
                () -> loanService.loanBook(book, user));
        assertTrue(ex.getMessage().contains("B001"));
    }

    @Test
    void testReturnBook_SinPrestamoActivo_LanzaExcepcion() {
        assertThrows(LoanNotFoundException.class, () -> loanService.returnBook(book, user));
    }

    @Test
    void testReturnBook_MensajeExcepcionCorrecto() {
        LoanNotFoundException ex = assertThrows(LoanNotFoundException.class,
                () -> loanService.returnBook(book, user));
        assertTrue(ex.getMessage().contains("B001"));
        assertTrue(ex.getMessage().contains("U001"));
    }
}