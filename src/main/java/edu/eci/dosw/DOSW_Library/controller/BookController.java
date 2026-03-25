package edu.eci.dosw.DOSW_Library.controller;

import edu.eci.dosw.DOSW_Library.controller.dto.BookDTO;
import edu.eci.dosw.DOSW_Library.core.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // LIBRARIAN only
    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestParam String title,
                                           @RequestParam String autor,
                                           @RequestParam String isbn,
                                           @RequestParam Integer totalCopies,
                                           @RequestParam Integer availableCopies) {
        return ResponseEntity.ok(bookService.addBook(title, autor, isbn, totalCopies, availableCopies));
    }

    // All authenticated users
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // All authenticated users
    @GetMapping("/available")
    public ResponseEntity<List<BookDTO>> getAvailableBooks() {
        return ResponseEntity.ok(bookService.getAvailableBooks());
    }

    // All authenticated users
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    // LIBRARIAN only
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    // LIBRARIAN only
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id,
                                              @RequestParam(required = false) String title,
                                              @RequestParam(required = false) String autor,
                                              @RequestParam(required = false) Integer totalCopies,
                                              @RequestParam(required = false) Integer availableCopies) {
        return ResponseEntity.ok(bookService.updateBook(id, title, autor, totalCopies, availableCopies));
    }

    // All authenticated users
    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<BookDTO>> getBooksByAutor(@PathVariable String autor) {
        return ResponseEntity.ok(bookService.getBooksByAutor(autor));
    }

    @GetMapping("/exists/{isbn}")
    public ResponseEntity<Boolean> existsBook(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.existsBook(isbn));
    }
}