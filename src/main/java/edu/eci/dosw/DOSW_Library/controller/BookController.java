package edu.eci.dosw.DOSW_Library.controller;

import edu.eci.dosw.DOSW_Library.controller.dto.BookDTO;
import edu.eci.dosw.DOSW_Library.core.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<BookDTO> addBook(@RequestParam String title,
                                           @RequestParam String autor,
                                           @RequestParam String isbn,
                                           @RequestParam Integer totalCopies,
                                           @RequestParam Integer availableCopies) {
        return ResponseEntity.ok(bookService.addBook(title, autor, isbn, totalCopies, availableCopies));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'LIBRARIAN')")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('USER', 'LIBRARIAN')")
    public ResponseEntity<List<BookDTO>> getAvailableBooks() {
        return ResponseEntity.ok(bookService.getAvailableBooks());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'LIBRARIAN')")
    public ResponseEntity<BookDTO> getBookById(@PathVariable String id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/autor/{autor}")
    @PreAuthorize("hasAnyRole('USER', 'LIBRARIAN')")
    public ResponseEntity<List<BookDTO>> getBooksByAutor(@PathVariable String autor) {
        return ResponseEntity.ok(bookService.getBooksByAutor(autor));
    }

    @GetMapping("/exists/{isbn}")
    @PreAuthorize("hasAnyRole('USER', 'LIBRARIAN')")
    public ResponseEntity<Boolean> existsBook(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.existsBook(isbn));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<Void> deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<BookDTO> updateBook(@PathVariable String id,
                                              @RequestParam(required = false) String title,
                                              @RequestParam(required = false) String autor,
                                              @RequestParam(required = false) Integer totalCopies,
                                              @RequestParam(required = false) Integer availableCopies) {
        return ResponseEntity.ok(bookService.updateBook(id, title, autor, totalCopies, availableCopies));
    }
}