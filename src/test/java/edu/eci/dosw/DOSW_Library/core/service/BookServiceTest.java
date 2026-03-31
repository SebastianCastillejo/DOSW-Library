package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.controller.dto.BookDTO;
import edu.eci.dosw.DOSW_Library.controller.mapper.BookMapper;
import edu.eci.dosw.DOSW_Library.core.exception.BookNotFoundException;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.BookEntity;
import edu.eci.dosw.DOSW_Library.persistence.relational.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    private BookEntity bookEntity;
    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        bookEntity = BookEntity.builder()
                .id(1L)
                .title("Clean Code")
                .autor("Robert Martin")
                .isbn("B001")
                .totalCopies(5)
                .availableCopies(5)
                .build();

        bookDTO = BookDTO.builder()
                .id(1L)
                .title("Clean Code")
                .autor("Robert Martin")
                .isbn("B001")
                .totalCopies(5)
                .availableCopies(5)
                .build();
    }

    // ── addBook ────────────────────────────────────────────────────────────────

    @Test
    void testAddBook_Exitoso() {
        when(bookRepository.save(any())).thenReturn(bookEntity);
        when(bookMapper.toDTO(bookEntity)).thenReturn(bookDTO);

        BookDTO result = bookService.addBook("Clean Code", "Robert Martin", "B001", 5, 5);

        assertNotNull(result);
        assertEquals("B001", result.getIsbn());
        verify(bookRepository).save(any());
    }

    @Test
    void testAddBook_TotalCopiesCeroONegativo_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> bookService.addBook("Clean Code", "Robert Martin", "B001", 0, 0));
    }

    @Test
    void testAddBook_AvailableCopiesNegativas_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> bookService.addBook("Clean Code", "Robert Martin", "B001", 5, -1));
    }

    @Test
    void testAddBook_AvailableSuperaTotalCopies_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> bookService.addBook("Clean Code", "Robert Martin", "B001", 3, 5));
    }

    // ── getBookById ────────────────────────────────────────────────────────────

    @Test
    void testGetBookById_Exitoso() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
        when(bookMapper.toDTO(bookEntity)).thenReturn(bookDTO);

        BookDTO result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals("Clean Code", result.getTitle());
    }

    @Test
    void testGetBookById_NoExiste_LanzaExcepcion() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class,
                () -> bookService.getBookById(99L));
    }

    // ── getAllBooks ────────────────────────────────────────────────────────────

    @Test
    void testGetAllBooks_Exitoso() {
        BookEntity book2 = BookEntity.builder().id(2L).title("Refactoring")
                .autor("Martin Fowler").isbn("B002").totalCopies(3).availableCopies(3).build();

        when(bookRepository.findAll()).thenReturn(List.of(bookEntity, book2));
        when(bookMapper.toDTOList(any())).thenReturn(List.of(bookDTO,
                BookDTO.builder().id(2L).title("Refactoring").build()));

        assertEquals(2, bookService.getAllBooks().size());
    }

    @Test
    void testGetAllBooks_ListaVacia() {
        when(bookRepository.findAll()).thenReturn(List.of());
        when(bookMapper.toDTOList(any())).thenReturn(List.of());

        assertTrue(bookService.getAllBooks().isEmpty());
    }

    // ── getAvailableBooks ──────────────────────────────────────────────────────

    @Test
    void testGetAvailableBooks_SoloRetornaConDisponibilidad() {
        when(bookRepository.findByAvailableCopiesGreaterThan(0)).thenReturn(List.of(bookEntity));
        when(bookMapper.toDTOList(any())).thenReturn(List.of(bookDTO));

        List<BookDTO> result = bookService.getAvailableBooks();
        assertEquals(1, result.size());
    }

    // ── deleteBook ─────────────────────────────────────────────────────────────

    @Test
    void testDeleteBook_Exitoso() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        bookService.deleteBook(1L);

        verify(bookRepository).deleteById(1L);
    }

    @Test
    void testDeleteBook_NoExiste_LanzaExcepcion() {
        when(bookRepository.existsById(99L)).thenReturn(false);

        assertThrows(BookNotFoundException.class,
                () -> bookService.deleteBook(99L));
    }

    // ── updateBook ─────────────────────────────────────────────────────────────

    @Test
    void testUpdateBook_Exitoso() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
        when(bookRepository.save(any())).thenReturn(bookEntity);
        when(bookMapper.toDTO(any())).thenReturn(bookDTO);

        BookDTO result = bookService.updateBook(1L, "Nuevo Titulo", "Nuevo Autor", null, null);

        assertNotNull(result);
        verify(bookRepository).save(bookEntity);
    }

    @Test
    void testUpdateBook_NoExiste_LanzaExcepcion() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class,
                () -> bookService.updateBook(99L, "T", "A", null, null));
    }

    @Test
    void testUpdateBook_TotalCopiesNegativo_LanzaExcepcion() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));

        assertThrows(IllegalArgumentException.class,
                () -> bookService.updateBook(1L, null, null, -1, null));
    }

    @Test
    void testUpdateBook_AvailableSuperaTotalCopies_LanzaExcepcion() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));

        // totalCopies = 5, intentamos poner availableCopies = 10
        assertThrows(IllegalArgumentException.class,
                () -> bookService.updateBook(1L, null, null, null, 10));
    }

    // ── getBooksByAutor ────────────────────────────────────────────────────────

    @Test
    void testGetBooksByAutor_Exitoso() {
        when(bookRepository.findByAutor("Robert Martin")).thenReturn(List.of(bookEntity));
        when(bookMapper.toDTOList(any())).thenReturn(List.of(bookDTO));

        List<BookDTO> result = bookService.getBooksByAutor("Robert Martin");
        assertEquals(1, result.size());
    }

    @Test
    void testGetBooksByAutor_NoExiste_LanzaExcepcion() {
        when(bookRepository.findByAutor("Desconocido")).thenReturn(List.of());

        assertThrows(BookNotFoundException.class,
                () -> bookService.getBooksByAutor("Desconocido"));
    }

    // ── existsBook ─────────────────────────────────────────────────────────────

    @Test
    void testExistsBook_Existe() {
        when(bookRepository.existsByIsbn("B001")).thenReturn(true);
        assertTrue(bookService.existsBook("B001"));
    }

    @Test
    void testExistsBook_NoExiste() {
        when(bookRepository.existsByIsbn("NOEXISTE")).thenReturn(false);
        assertFalse(bookService.existsBook("NOEXISTE"));
    }
}