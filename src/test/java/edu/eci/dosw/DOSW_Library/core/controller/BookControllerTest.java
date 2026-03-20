package edu.eci.dosw.DOSW_Library.core.controller;

import edu.eci.dosw.DOSW_Library.controller.BookController;
import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.core.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BookControllerTest {

    private BookController bookController;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
        bookController = new BookController(bookService);
    }

    @Test
    void testAddBook_Exitoso() {
        ResponseEntity<Void> response = bookController.addBook("Clean Code", "Martin", "B001");
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testGetAllBooks_Exitoso() {
        bookController.addBook("Clean Code", "Martin", "B001");
        ResponseEntity<List<Book>> response = bookController.getAllBooks();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetBookById_Exitoso() {
        bookController.addBook("Clean Code", "Martin", "B001");
        ResponseEntity<Book> response = bookController.getBookById("B001");
        assertEquals(200, response.getStatusCode().value());
        assertEquals("B001", response.getBody().getId());
    }

    @Test
    void testDeleteBook_Exitoso() {
        bookController.addBook("Clean Code", "Martin", "B001");
        ResponseEntity<Void> response = bookController.deleteBookId("B001");
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testUpdateBook_Exitoso() {
        bookController.addBook("Clean Code", "Martin", "B001");
        ResponseEntity<Void> response = bookController.updateBook("B001", "Refactoring", "Fowler");
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testExistsBook_Exitoso() {
        bookController.addBook("Clean Code", "Martin", "B001");
        ResponseEntity<Boolean> response = bookController.existsBook("B001");
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody());
    }

    @Test
    void testGetBooksByAutor_Exitoso() {
        bookController.addBook("Clean Code", "Martin", "B001");
        ResponseEntity<List<Book>> response = bookController.getBooksByAutor("Martin");
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }
}
