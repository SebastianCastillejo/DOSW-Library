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
                .id(1L)
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

        ResponseEntity<BookDTO> response = bookController.addBook(
                "Clean Code", "Robert Martin", "ISBN-001", 5, 5);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("ISBN-001", response.getBody().getIsbn());
    }

    @Test
    void testGetAllBooks_Exitoso() {
        when(bookService.getAllBooks()).thenReturn(List.of(bookDTO));

        ResponseEntity<List<BookDTO>> response = bookController.getAllBooks();

        assertEquals(200, response.getStatusCode().value());
        assert response.getBody() != null;
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetAllBooks_ListaVacia() {
        when(bookService.getAllBooks()).thenReturn(List.of());

        ResponseEntity<List<BookDTO>> response = bookController.getAllBooks();

        assertEquals(200, response.getStatusCode().value());
        assert response.getBody() != null;
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testGetBookById_Exitoso() {
        when(bookService.getBookById(1L)).thenReturn(bookDTO);

        ResponseEntity<BookDTO> response = bookController.getBookById(1L);

        assertEquals(200, response.getStatusCode().value());
        assert response.getBody() != null;
        assertEquals("Clean Code", response.getBody().getTitle());
    }

    @Test
    void testGetBookById_NoExiste_LanzaExcepcion() {
        when(bookService.getBookById(99L)).thenThrow(new BookNotFoundException("99"));

        assertThrows(BookNotFoundException.class,
                () -> bookController.getBookById(99L));
    }

    @Test
    void testDeleteBook_Exitoso() {
        doNothing().when(bookService).deleteBook(1L);

        ResponseEntity<Void> response = bookController.deleteBook(1L);

        assertEquals(200, response.getStatusCode().value());
        verify(bookService).deleteBook(1L);
    }

    @Test
    void testDeleteBook_NoExiste_LanzaExcepcion() {
        doThrow(new BookNotFoundException("99")).when(bookService).deleteBook(99L);

        assertThrows(BookNotFoundException.class,
                () -> bookController.deleteBook(99L));
    }

    @Test
    void testUpdateBook_Exitoso() {
        when(bookService.updateBook(eq(1L), any(), any(), any(), any())).thenReturn(bookDTO);

        ResponseEntity<BookDTO> response = bookController.updateBook(
                1L, "Nuevo Titulo", "Nuevo Autor", 10, 8);

        assertEquals(200, response.getStatusCode().value());
        verify(bookService).updateBook(1L, "Nuevo Titulo", "Nuevo Autor", 10, 8);
    }

    @Test
    void testGetAvailableBooks_Exitoso() {
        when(bookService.getAvailableBooks()).thenReturn(List.of(bookDTO));

        ResponseEntity<List<BookDTO>> response = bookController.getAvailableBooks();

        assertEquals(200, response.getStatusCode().value());
        assert response.getBody() != null;
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetBooksByAutor_Exitoso() {
        when(bookService.getBooksByAutor("Robert Martin")).thenReturn(List.of(bookDTO));

        ResponseEntity<List<BookDTO>> response = bookController.getBooksByAutor("Robert Martin");

        assertEquals(200, response.getStatusCode().value());
        assert response.getBody() != null;
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetBooksByAutor_NoExiste_LanzaExcepcion() {
        when(bookService.getBooksByAutor("Desconocido"))
                .thenThrow(new BookNotFoundException("Desconocido"));

        assertThrows(BookNotFoundException.class,
                () -> bookController.getBooksByAutor("Desconocido"));
    }

    @Test
    void testExistsBook_Existe() {
        when(bookService.existsBook("ISBN-001")).thenReturn(true);

        ResponseEntity<Boolean> response = bookController.existsBook("ISBN-001");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(Boolean.TRUE, response.getBody());
    }

    @Test
    void testExistsBook_NoExiste() {
        when(bookService.existsBook("NOEXISTE")).thenReturn(false);

        ResponseEntity<Boolean> response = bookController.existsBook("NOEXISTE");

        assertNotEquals(Boolean.TRUE, response.getBody());
    }
}