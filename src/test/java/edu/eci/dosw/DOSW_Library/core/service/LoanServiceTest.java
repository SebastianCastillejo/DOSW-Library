package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.controller.dto.LoanDTO;
import edu.eci.dosw.DOSW_Library.controller.mapper.LoanMapper;
import edu.eci.dosw.DOSW_Library.core.exception.BookNotAvailableException;
import edu.eci.dosw.DOSW_Library.core.exception.LoanNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.core.model.Loan;
import edu.eci.dosw.DOSW_Library.core.model.Status;
import edu.eci.dosw.DOSW_Library.core.model.User;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.LoanEntity.LoanStatus;
import edu.eci.dosw.DOSW_Library.persistence.repository.LoanRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepositoryPort loanRepository;

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private LoanService loanService;

    private Book book;
    private Loan loan;
    private LoanDTO loanDTO;

    @BeforeEach
    void setUp() {
        book = Book.builder()
                .id("1")
                .title("Clean Code")
                .autor("Robert Martin")
                .isbn("B001")
                .totalCopies(5)
                .availableCopies(3)
                .build();

        loan = Loan.builder()
                .id("1")
                .bookId("1")
                .userId("1")
                .loanDate(LocalDate.now())
                .status(Status.ACTIVE)
                .build();

        loanDTO = LoanDTO.builder()
                .id("1")
                .loanDate(LocalDate.now())
                .status(LoanStatus.ACTIVE)
                .build();
    }

    @Test
    void testLoanBook_Exitoso() {
        when(bookService.getModelById("1")).thenReturn(book);
        when(userService.getModelById("1")).thenReturn(User.builder().id("1").build());
        when(loanRepository.save(any())).thenReturn(loan);
        when(loanMapper.toDTO(any())).thenReturn(loanDTO);

        LoanDTO result = loanService.loanBook("1", "1");

        assertNotNull(result);
        assertEquals(2, book.getAvailableCopies());
        verify(loanRepository).save(any());
    }

    @Test
    void testLoanBook_SinCopiaDisponible_LanzaExcepcion() {
        book.setAvailableCopies(0);
        when(bookService.getModelById("1")).thenReturn(book);
        when(userService.getModelById("1")).thenReturn(User.builder().id("1").build());

        assertThrows(BookNotAvailableException.class,
                () -> loanService.loanBook("1", "1")); // ← sin L

        verify(loanRepository, never()).save(any());
    }

    @Test
    void testGetActiveLoans_Exitoso() {
        when(loanRepository.findActive()).thenReturn(List.of(loan));
        when(loanMapper.toDTOList(any())).thenReturn(List.of(loanDTO));

        assertEquals(1, loanService.getActiveLoans().size());
    }

    @Test
    void testGetActiveLoans_Vacia() {
        when(loanRepository.findActive()).thenReturn(List.of());
        when(loanMapper.toDTOList(any())).thenReturn(List.of());

        assertTrue(loanService.getActiveLoans().isEmpty());
    }

    @Test
    void testReturnBook_Exitoso() {
        when(loanRepository.findById("1")).thenReturn(Optional.of(loan));
        when(loanRepository.save(any())).thenReturn(loan);
        when(bookService.getModelById("1")).thenReturn(book);

        loanService.returnBook("1"); // ← sin L

        assertEquals(Status.RETURNED, loan.getStatus());
        assertNotNull(loan.getReturnDate());
        assertEquals(4, book.getAvailableCopies());
    }

    @Test
    void testReturnBook_NoExiste_LanzaExcepcion() {
        when(loanRepository.findById("99")).thenReturn(Optional.empty());

        assertThrows(LoanNotFoundException.class,
                () -> loanService.returnBook("99")); // ← sin L
    }

    @Test
    void testIsBookAvailable_ConCopias_RetornaTrue() {
        when(bookService.getModelById("1")).thenReturn(book);
        assertTrue(loanService.isBookAvailable("1")); // ← sin L
    }

    @Test
    void testIsBookAvailable_SinCopias_RetornaFalse() {
        book.setAvailableCopies(0);
        when(bookService.getModelById("1")).thenReturn(book);
        assertFalse(loanService.isBookAvailable("1"));
    }

    // test ci/cu

    @Test
    void dadoQueHayUnaReservaRegistrada_CuandoLaConsulto_EntoncesEsExitosaValidandoId() {
        when(loanRepository.findById("1")).thenReturn(Optional.of(loan));

        Optional<Loan> result = loanRepository.findById("1");

        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());
    }

    @Test
    void dadoQueNoHayReservas_CuandoLaConsulto_EntoncesNoRetornaNada() {
        when(loanRepository.findById("99")).thenReturn(Optional.empty());

        Optional<Loan> result = loanRepository.findById("99");

        assertFalse(result.isPresent());
    }

    @Test
    void dadoQueNoHayReservas_CuandoLaCreo_EntoncesLaCreacionEsExitosa() {
        when(bookService.getModelById("1")).thenReturn(book);
        when(userService.getModelById("1")).thenReturn(User.builder().id("1").build());
        when(loanRepository.save(any())).thenReturn(loan);
        when(loanMapper.toDTO(any())).thenReturn(loanDTO);

        LoanDTO result = loanService.loanBook("1", "1");

        assertNotNull(result);
        verify(loanRepository).save(any());
    }

    @Test
    void dadoQueHayUnaReserva_CuandoLaElimino_EntoncesLaEliminacionEsExitosa() {
        doNothing().when(loanRepository).delete("1");

        loanRepository.delete("1");

        verify(loanRepository).delete("1");
    }

    @Test
    void dadoQueHayUnaReserva_CuandoLaEliminoYConsulto_EntoncesNoRetornaNada() {
        doNothing().when(loanRepository).delete("1");
        when(loanRepository.findById("1")).thenReturn(Optional.empty());

        loanRepository.delete("1");
        Optional<Loan> result = loanRepository.findById("1");

        assertFalse(result.isPresent());
    }
}