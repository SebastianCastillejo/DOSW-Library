package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.controller.dto.LoanDTO;
import edu.eci.dosw.DOSW_Library.controller.mapper.LoanMapper;
import edu.eci.dosw.DOSW_Library.core.exception.BookNotAvailableException;
import edu.eci.dosw.DOSW_Library.core.exception.LoanNotFoundException;
import edu.eci.dosw.DOSW_Library.persistence.entity.BookEntity;
import edu.eci.dosw.DOSW_Library.persistence.entity.LoanEntity;
import edu.eci.dosw.DOSW_Library.persistence.entity.LoanEntity.LoanStatus;
import edu.eci.dosw.DOSW_Library.persistence.entity.UserEntity;
import edu.eci.dosw.DOSW_Library.persistence.entity.UserEntity.Role;
import edu.eci.dosw.DOSW_Library.persistence.repository.LoanRepository;
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
    private LoanRepository loanRepository;

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private LoanService loanService;

    private BookEntity bookEntity;
    private UserEntity userEntity;
    private LoanEntity loanEntity;
    private LoanDTO loanDTO;

    @BeforeEach
    void setUp() {
        bookEntity = BookEntity.builder()
                .id(1L)
                .title("Clean Code")
                .autor("Robert Martin")
                .isbn("B001")
                .totalCopies(5)
                .availableCopies(3)
                .build();

        userEntity = UserEntity.builder()
                .id(1L)
                .name("Sebastian")
                .username("seba123")
                .password("pass")
                .email("seba@mail.com")
                .role(Role.USER)
                .build();

        loanEntity = LoanEntity.builder()
                .id(1L)
                .book(bookEntity)
                .user(userEntity)
                .loanDate(LocalDate.now())
                .status(LoanStatus.ACTIVE)
                .build();

        loanDTO = LoanDTO.builder()
                .id(1L)
                .loanDate(LocalDate.now())
                .status(LoanStatus.ACTIVE)
                .build();
    }

    // ── loanBook ───────────────────────────────────────────────────────────────

    @Test
    void testLoanBook_Exitoso() {
        when(bookService.getEntityById(1L)).thenReturn(bookEntity);
        when(userService.getEntityById(1L)).thenReturn(userEntity);
        when(loanRepository.save(any())).thenReturn(loanEntity);
        when(loanMapper.toDTO(loanEntity)).thenReturn(loanDTO);

        LoanDTO result = loanService.loanBook(1L, 1L);

        assertNotNull(result);
        assertEquals(LoanStatus.ACTIVE, result.getStatus());
        // availableCopies debe haberse reducido: 3 -> 2
        assertEquals(2, bookEntity.getAvailableCopies());
        verify(bookService).save(bookEntity);
        verify(loanRepository).save(any());
    }

    @Test
    void testLoanBook_SinCopiaDisponible_LanzaExcepcion() {
        bookEntity.setAvailableCopies(0);
        when(bookService.getEntityById(1L)).thenReturn(bookEntity);
        when(userService.getEntityById(1L)).thenReturn(userEntity);

        assertThrows(BookNotAvailableException.class,
                () -> loanService.loanBook(1L, 1L));

        verify(loanRepository, never()).save(any());
    }

    // ── isBookAvailable ────────────────────────────────────────────────────────

    @Test
    void testIsBookAvailable_ConCopias_RetornaTrue() {
        when(bookService.getEntityById(1L)).thenReturn(bookEntity); // availableCopies = 3

        assertTrue(loanService.isBookAvailable(1L));
    }

    @Test
    void testIsBookAvailable_SinCopias_RetornaFalse() {
        bookEntity.setAvailableCopies(0);
        when(bookService.getEntityById(1L)).thenReturn(bookEntity);

        assertFalse(loanService.isBookAvailable(1L));
    }

    // ── getActiveLoans ─────────────────────────────────────────────────────────

    @Test
    void testGetActiveLoans_Exitoso() {
        when(loanRepository.findByStatus(LoanStatus.ACTIVE)).thenReturn(List.of(loanEntity));
        when(loanMapper.toDTOList(any())).thenReturn(List.of(loanDTO));

        List<LoanDTO> result = loanService.getActiveLoans();
        assertEquals(1, result.size());
    }

    @Test
    void testGetActiveLoans_Vacia() {
        when(loanRepository.findByStatus(LoanStatus.ACTIVE)).thenReturn(List.of());
        when(loanMapper.toDTOList(any())).thenReturn(List.of());

        assertTrue(loanService.getActiveLoans().isEmpty());
    }

    // ── getLoansByUser ─────────────────────────────────────────────────────────

    @Test
    void testGetLoansByUser_Exitoso() {
        when(loanRepository.findByUserId(1L)).thenReturn(List.of(loanEntity));
        when(loanMapper.toDTOList(any())).thenReturn(List.of(loanDTO));

        List<LoanDTO> result = loanService.getLoansByUser(1L);
        assertEquals(1, result.size());
    }

    // ── returnBook ─────────────────────────────────────────────────────────────

    @Test
    void testReturnBook_Exitoso() {
        when(loanRepository.findByIdAndStatus(1L, LoanStatus.ACTIVE))
                .thenReturn(Optional.of(loanEntity));
        when(loanRepository.save(any())).thenReturn(loanEntity);

        loanService.returnBook(1L);

        assertEquals(LoanStatus.RETURNED, loanEntity.getStatus());
        assertNotNull(loanEntity.getReturnDate());
        // availableCopies debe haber subido: 3 -> 4 (sin superar totalCopies=5)
        assertEquals(4, bookEntity.getAvailableCopies());
        verify(bookService).save(bookEntity);
    }

    @Test
    void testReturnBook_NoExistePrestamoActivo_LanzaExcepcion() {
        when(loanRepository.findByIdAndStatus(99L, LoanStatus.ACTIVE))
                .thenReturn(Optional.empty());

        assertThrows(LoanNotFoundException.class,
                () -> loanService.returnBook(99L));

        verify(loanRepository, never()).save(any());
    }

    @Test
    void testReturnBook_NoAumentaDisponibilidadSiYaEstaEnMaximo() {
        bookEntity.setAvailableCopies(5); // ya está al máximo (totalCopies = 5)
        when(loanRepository.findByIdAndStatus(1L, LoanStatus.ACTIVE))
                .thenReturn(Optional.of(loanEntity));
        when(loanRepository.save(any())).thenReturn(loanEntity);

        loanService.returnBook(1L);

        // No debe superar totalCopies
        assertEquals(5, bookEntity.getAvailableCopies());
        verify(bookService, never()).save(bookEntity);
    }
}