package edu.eci.dosw.DOSW_Library.controller;

import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.core.service.BookServicce;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookServicce bookService;

    public BookController(BookServicce bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Void> addBook(@RequestParam String title,
                                        @RequestParam String autor,
                                        @RequestParam String id) {
        bookService.addBook(title, autor, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }
}