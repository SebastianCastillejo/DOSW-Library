package edu.eci.dosw.DOSW_Library.controller;

import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.core.model.Loan;
import edu.eci.dosw.DOSW_Library.core.model.User;
import edu.eci.dosw.DOSW_Library.core.service.BookService;
import edu.eci.dosw.DOSW_Library.core.service.LoanService;
import edu.eci.dosw.DOSW_Library.core.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;
    private final BookService bookService;
    private final UserService userService;

    public LoanController(LoanService loanService, BookService bookService, UserService userService) {
        this.loanService = loanService;
        this.bookService = bookService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Loan> loanBook(@RequestParam String bookId,
                                         @RequestParam String userId) {
        Book book = bookService.getBookById(bookId);
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(loanService.loanBook(book, user));
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getActiveLoans() {
        return ResponseEntity.ok(loanService.getActiveLoans());
    }

    @PutMapping("/return")
    public ResponseEntity<Void> returnBook(@RequestParam String bookId,
                                           @RequestParam String userId) {
        Book book = bookService.getBookById(bookId);
        User user = userService.getUserById(userId);
        loanService.returnBook(book, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/available")
    public ResponseEntity<Boolean> isBookAvailable(@RequestParam String bookId) {
        Book book = bookService.getBookById(bookId);
        return ResponseEntity.ok(loanService.isBookAvailable(book));
    }
}