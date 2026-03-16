package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.core.exception.BookNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookServicceTest {

    private BookServicce bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookServicce();
    }

    // ── Exitosos ──────────────────────────────

    @Test
    void testAddBookYGetById_Exitoso() {
        bookService.addBook("Clean Code", "Robert Martin", "B001");
        Book book = bookService.getBookById("B001");
        assertNotNull(book);
        assertEquals("B001", book.getId());
        assertEquals("Clean Code", book.getTitle());
        assertEquals("Robert Martin", book.getAutor());
    }

    @Test
    void testGetAllBooks_RetornaListaConLibros() {
        bookService.addBook("Clean Code", "Robert Martin", "B001");
        bookService.addBook("Refactoring", "Martin Fowler", "B002");
        List<Book> lista = bookService.getAllBooks();
        assertEquals(2, lista.size());
    }

    @Test
    void testGetAllBooks_ListaVacia() {
        List<Book> lista = bookService.getAllBooks();
        assertTrue(lista.isEmpty());
    }

    @Test
    void testAddBook_SobreescribeLibroMismoId() {
        bookService.addBook("Clean Code", "Robert Martin", "B001");
        bookService.addBook("Nuevo Titulo", "Otro Autor", "B001");
        Book book = bookService.getBookById("B001");
        assertEquals("Nuevo Titulo", book.getTitle());
    }

    // ── Error ─────────────────────────────────

    @Test
    void testGetBookById_NoExiste_LanzaExcepcion() {
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById("NOEXISTE"));
    }

    @Test
    void testGetBookById_MensajeExcepcionCorrecto() {
        BookNotFoundException ex = assertThrows(BookNotFoundException.class,
                () -> bookService.getBookById("X99"));
        assertTrue(ex.getMessage().contains("X99"));
    }
}