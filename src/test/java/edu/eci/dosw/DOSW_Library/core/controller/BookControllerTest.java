package edu.eci.dosw.DOSW_Library.core.controller;

import edu.eci.dosw.DOSW_Library.controller.BookController;
import edu.eci.dosw.DOSW_Library.controller.dto.BookDTO;
import edu.eci.dosw.DOSW_Library.core.exception.BookNotFoundException;
import edu.eci.dosw.DOSW_Library.core.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private BookDTO bookDTO;

    @BeforeEach
    void setUp() {
        bookDTO = BookDTO.builder()
                .id("1L")
                .title("Clean Code")
                .autor("Robert Martin")
                .isbn("ISBN-001")
                .totalCopies(5)
                .availableCopies(5)
                .build();
    }

    @Test
    void testAddBook_Exitoso() {
        when(bookService.addBook(any(), any(), any(), any(), any())).thenReturn(bookDTO);
        ResponseEntity<BookDTO> response = bookController.addBook("Clean Code", "Robert Martin", "ISBN-001", 5, 5);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetAllBooks_Exitoso() {
        when(bookService.getAllBooks()).thenReturn(List.of(bookDTO));
        ResponseEntity<List<BookDTO>> response = bookController.getAllBooks();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetAllBooks_ListaVacia() {
        when(bookService.getAllBooks()).thenReturn(List.of());
        ResponseEntity<List<BookDTO>> response = bookController.getAllBooks();
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetBookById_Exitoso() {
        when(bookService.getBookById("1")).thenReturn(bookDTO);
        ResponseEntity<BookDTO> response = bookController.getBookById("1");
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Clean Code", response.getBody().getTitle());
    }

    @Test
    void testGetBookById_NoExiste_LanzaExcepcion() {
        when(bookService.getBookById("99")).thenThrow(new BookNotFoundException("99"));
        assertThrows(BookNotFoundException.class, () -> bookController.getBookById("99"));
    }

    @Test
    void testDeleteBook_Exitoso() {
        doNothing().when(bookService).deleteBook("1");
        ResponseEntity<Void> response = bookController.deleteBook("1");
        assertEquals(200, response.getStatusCode().value());
        verify(bookService).deleteBook("1");
    }

    @Test
    void testDeleteBook_NoExiste_LanzaExcepcion() {
        doThrow(new BookNotFoundException("99")).when(bookService).deleteBook("99");
        assertThrows(BookNotFoundException.class, () -> bookController.deleteBook("99"));
    }

    @Test
    void testUpdateBook_Exitoso() {
        when(bookService.updateBook(eq("1"), any(), any(), any(), any())).thenReturn(bookDTO);
        ResponseEntity<BookDTO> response = bookController.updateBook("1", "Nuevo", "Autor", 10, 8);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testGetAvailableBooks_Exitoso() {
        when(bookService.getAvailableBooks()).thenReturn(List.of(bookDTO));
        ResponseEntity<List<BookDTO>> response = bookController.getAvailableBooks();
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetBooksByAutor_Exitoso() {
        when(bookService.getBooksByAutor("Robert Martin")).thenReturn(List.of(bookDTO));
        ResponseEntity<List<BookDTO>> response = bookController.getBooksByAutor("Robert Martin");
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testExistsBook_Existe() {
        when(bookService.existsBook("ISBN-001")).thenReturn(true);
        ResponseEntity<Boolean> response = bookController.existsBook("ISBN-001");
        assertEquals(Boolean.TRUE, response.getBody());
    }
}