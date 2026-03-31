package edu.eci.dosw.DOSW_Library.core.controller;

import edu.eci.dosw.DOSW_Library.controller.LoanController;
import edu.eci.dosw.DOSW_Library.controller.dto.LoanDTO;
import edu.eci.dosw.DOSW_Library.core.exception.BookNotAvailableException;
import edu.eci.dosw.DOSW_Library.core.exception.LoanNotFoundException;
import edu.eci.dosw.DOSW_Library.core.service.LoanService;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.LoanEntity.LoanStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanControllerTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    private LoanDTO loanDTO;

    @BeforeEach
    void setUp() {
        loanDTO = LoanDTO.builder()
                .id(1L)
                .loanDate(LocalDate.now())
                .status(LoanStatus.ACTIVE)
                .build();
    }

    @Test
    void testLoanBook_Exitoso() {
        when(loanService.loanBook(1L, 1L)).thenReturn(loanDTO);

        ResponseEntity<LoanDTO> response = loanController.loanBook(1L, 1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(LoanStatus.ACTIVE, response.getBody().getStatus());
    }

    @Test
    void testLoanBook_LibroSinDisponibilidad_LanzaExcepcion() {
        when(loanService.loanBook(1L, 1L))
                .thenThrow(new BookNotAvailableException("1"));

        assertThrows(BookNotAvailableException.class,
                () -> loanController.loanBook(1L, 1L));
    }

    @Test
    void testGetActiveLoans_Exitoso() {
        when(loanService.getActiveLoans()).thenReturn(List.of(loanDTO));

        ResponseEntity<List<LoanDTO>> response = loanController.getActiveLoans();

        assertEquals(200, response.getStatusCode().value());
        assert response.getBody() != null;
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetActiveLoans_Vacia() {
        when(loanService.getActiveLoans()).thenReturn(List.of());

        ResponseEntity<List<LoanDTO>> response = loanController.getActiveLoans();

        assert response.getBody() != null;
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetLoansByUser_Exitoso() {
        when(loanService.getLoansByUser(1L)).thenReturn(List.of(loanDTO));

        ResponseEntity<List<LoanDTO>> response = loanController.getLoansByUser(1L);

        assertEquals(200, response.getStatusCode().value());
        assert response.getBody() != null;
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testReturnBook_Exitoso() {
        doNothing().when(loanService).returnBook(1L);

        ResponseEntity<Void> response = loanController.returnBook(1L);

        assertEquals(200, response.getStatusCode().value());
        verify(loanService).returnBook(1L);
    }

    @Test
    void testReturnBook_PrestamoNoActivo_LanzaExcepcion() {
        doThrow(new LoanNotFoundException("1", "active"))
                .when(loanService).returnBook(99L);

        assertThrows(LoanNotFoundException.class,
                () -> loanController.returnBook(99L));
    }

    @Test
    void testIsBookAvailable_Disponible() {
        when(loanService.isBookAvailable(1L)).thenReturn(true);

        ResponseEntity<Boolean> response = loanController.isBookAvailable(1L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(Boolean.TRUE, response.getBody());
    }

    @Test
    void testIsBookAvailable_NoDisponible() {
        when(loanService.isBookAvailable(1L)).thenReturn(false);

        ResponseEntity<Boolean> response = loanController.isBookAvailable(1L);

        assertNotEquals(Boolean.TRUE, response.getBody());
    }
}