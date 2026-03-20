package edu.eci.dosw.DOSW_Library.controller;

import edu.eci.dosw.DOSW_Library.core.model.Book;
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

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteBookId(@PathVariable String id){
        bookService.deleteBookId(id);
        return ResponseEntity.ok().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable String id,
                                           @RequestParam String title,
                                           @RequestParam String autor) {
        bookService.updateBook(id, title, autor);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<Book>> getBooksByAutor(@PathVariable String autor) {
        return ResponseEntity.ok(bookService.getBooksByAutor(autor));
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsBook(@PathVariable String id) {
        return ResponseEntity.ok(bookService.existsBook(id));
    }
}