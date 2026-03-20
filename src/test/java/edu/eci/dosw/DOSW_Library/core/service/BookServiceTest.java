package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.core.exception.BookNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
    }

    @Test
    void testAddBook_Exitoso() {
        bookService.addBook("Clean Code", "Robert Martin", "B001");
        Book book = bookService.getBookById("B001");
        assertNotNull(book);
        assertEquals("B001", book.getId());
    }

    @Test
    void testAddBook_TituloVacio_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> bookService.addBook("", "Robert Martin", "B001"));
    }

    @Test
    void testAddBook_AutorVacio_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> bookService.addBook("Clean Code", "", "B001"));
    }

    @Test
    void testAddBook_IdVacio_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> bookService.addBook("Clean Code", "Robert Martin", ""));
    }

    @Test
    void testGetBookById_Exitoso() {
        bookService.addBook("Clean Code", "Robert Martin", "B001");
        assertEquals("Clean Code", bookService.getBookById("B001").getTitle());
    }

    @Test
    void testGetBookById_NoExiste_LanzaExcepcion() {
        assertThrows(BookNotFoundException.class,
                () -> bookService.getBookById("NOEXISTE"));
    }

    @Test
    void testGetAllBooks_Exitoso() {
        bookService.addBook("Clean Code", "Robert Martin", "B001");
        bookService.addBook("Refactoring", "Martin Fowler", "B002");
        assertEquals(2, bookService.getAllBooks().size());
    }

    @Test
    void testGetAllBooks_ListaVacia() {
        assertTrue(bookService.getAllBooks().isEmpty());
    }

    @Test
    void testDeleteBook_Exitoso() {
        bookService.addBook("Clean Code", "Robert Martin", "B001");
        bookService.deleteBookId("B001");
        assertThrows(BookNotFoundException.class,
                () -> bookService.getBookById("B001"));
    }

    @Test
    void testDeleteBook_NoExiste_LanzaExcepcion() {
        assertThrows(BookNotFoundException.class,
                () -> bookService.deleteBookId("NOEXISTE"));
    }

    @Test
    void testUpdateBook_Exitoso() {
        bookService.addBook("Clean Code", "Robert Martin", "B001");
        bookService.updateBook("B001", "Refactoring", "Martin Fowler");
        Book book = bookService.getBookById("B001");
        assertEquals("Refactoring", book.getTitle());
        assertEquals("Martin Fowler", book.getAutor());
    }

    @Test
    void testUpdateBook_NoExiste_LanzaExcepcion() {
        assertThrows(BookNotFoundException.class,
                () -> bookService.updateBook("NOEXISTE", "Titulo", "Autor"));
    }

    @Test
    void testGetBooksByAutor_Exitoso() {
        bookService.addBook("Clean Code", "Robert Martin", "B001");
        bookService.addBook("Clean Coder", "Robert Martin", "B002");
        List<Book> result = bookService.getBooksByAutor("Robert Martin");
        assertEquals(2, result.size());
    }

    @Test
    void testGetBooksByAutor_NoExiste_LanzaExcepcion() {
        assertThrows(BookNotFoundException.class,
                () -> bookService.getBooksByAutor("Autor Desconocido"));
    }


    @Test
    void testExistsBook_Existe() {
        bookService.addBook("Clean Code", "Robert Martin", "B001");
        assertTrue(bookService.existsBook("B001"));
    }

    @Test
    void testExistsBook_NoExiste() {
        assertFalse(bookService.existsBook("NOEXISTE"));
    }
}