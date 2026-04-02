package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.controller.dto.BookDTO;
import edu.eci.dosw.DOSW_Library.controller.mapper.BookMapper;
import edu.eci.dosw.DOSW_Library.core.exception.BookNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.persistence.repository.BookRepositoryPort;
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
    private BookRepositoryPort bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        book = Book.builder()
                .id("1")
                .title("Clean Code")
                .autor("Robert Martin")
                .isbn("B001")
                .totalCopies(5)
                .availableCopies(5)
                .build();

        bookDTO = BookDTO.builder()
                .id("1L")
                .title("Clean Code")
                .autor("Robert Martin")
                .isbn("B001")
                .totalCopies(5)
                .availableCopies(5)
                .build();
    }

    @Test
    void testAddBook_Exitoso() {
        when(bookRepository.save(any())).thenReturn(book);
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);

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

    @Test
    void testGetBookById_Exitoso() {
        when(bookRepository.findById("1")).thenReturn(Optional.of(book));
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);

        BookDTO result = bookService.getBookById("1");

        assertNotNull(result);
        assertEquals("Clean Code", result.getTitle());
    }

    @Test
    void testGetBookById_NoExiste_LanzaExcepcion() {
        when(bookRepository.findById("99")).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class,
                () -> bookService.getBookById("99"));
    }

    @Test
    void testGetAllBooks_Exitoso() {
        when(bookRepository.findAll()).thenReturn(List.of(book));
        when(bookMapper.toDTOList(any())).thenReturn(List.of(bookDTO));

        assertEquals(1, bookService.getAllBooks().size());
    }

    @Test
    void testGetAllBooks_ListaVacia() {
        when(bookRepository.findAll()).thenReturn(List.of());
        when(bookMapper.toDTOList(any())).thenReturn(List.of());

        assertTrue(bookService.getAllBooks().isEmpty());
    }

    @Test
    void testGetAvailableBooks_Exitoso() {
        when(bookRepository.findAvailable()).thenReturn(List.of(book));
        when(bookMapper.toDTOList(any())).thenReturn(List.of(bookDTO));

        assertEquals(1, bookService.getAvailableBooks().size());
    }

    @Test
    void testDeleteBook_Exitoso() {
        when(bookRepository.findById("1")).thenReturn(Optional.of(book));

        bookService.deleteBook("1");

        verify(bookRepository).delete("1");
    }

    @Test
    void testDeleteBook_NoExiste_LanzaExcepcion() {
        when(bookRepository.findById("99")).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class,
                () -> bookService.deleteBook("99"));
    }

    @Test
    void testUpdateBook_Exitoso() {
        when(bookRepository.findById("1")).thenReturn(Optional.of(book));
        when(bookRepository.save(any())).thenReturn(book);
        when(bookMapper.toDTO(any())).thenReturn(bookDTO);

        BookDTO result = bookService.updateBook("1", "Nuevo", "Autor", null, null);

        assertNotNull(result);
        verify(bookRepository).save(book);
    }

    @Test
    void testGetBooksByAutor_Exitoso() {
        when(bookRepository.findByAutor("Robert Martin")).thenReturn(List.of(book));
        when(bookMapper.toDTOList(any())).thenReturn(List.of(bookDTO));

        assertEquals(1, bookService.getBooksByAutor("Robert Martin").size());
    }

    @Test
    void testExistsBook_Existe() {
        when(bookRepository.existsByIsbn("B001")).thenReturn(true);
        assertTrue(bookService.existsBook("B001"));
    }
}