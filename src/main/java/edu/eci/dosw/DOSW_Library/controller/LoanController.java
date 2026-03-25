package edu.eci.dosw.DOSW_Library.controller;

import edu.eci.dosw.DOSW_Library.controller.dto.LoanDTO;
import edu.eci.dosw.DOSW_Library.core.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    // USER can request a loan
    @PostMapping
    public ResponseEntity<LoanDTO> loanBook(@RequestParam Long bookId,
                                            @RequestParam Long userId) {
        return ResponseEntity.ok(loanService.loanBook(bookId, userId));
    }

    // LIBRARIAN can see all active loans
    @GetMapping
    public ResponseEntity<List<LoanDTO>> getActiveLoans() {
        return ResponseEntity.ok(loanService.getActiveLoans());
    }

    // USER can see only their own loans
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanDTO>> getLoansByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(loanService.getLoansByUser(userId));
    }

    // USER can return a book
    @PutMapping("/return/{loanId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long loanId) {
        loanService.returnBook(loanId);
        return ResponseEntity.ok().build();
    }

    // Any authenticated user
    @GetMapping("/available")
    public ResponseEntity<Boolean> isBookAvailable(@RequestParam Long bookId) {
        return ResponseEntity.ok(loanService.isBookAvailable(bookId));
    }
}