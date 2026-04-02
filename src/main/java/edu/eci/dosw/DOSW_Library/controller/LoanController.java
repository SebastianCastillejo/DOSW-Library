package edu.eci.dosw.DOSW_Library.controller;

import edu.eci.dosw.DOSW_Library.controller.dto.LoanDTO;
import edu.eci.dosw.DOSW_Library.core.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<LoanDTO> loanBook(@RequestParam String bookId,
                                            @RequestParam String userId) {
        return ResponseEntity.ok(loanService.loanBook(bookId, userId));
    }

    @GetMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<List<LoanDTO>> getActiveLoans() {
        return ResponseEntity.ok(loanService.getActiveLoans());
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<List<LoanDTO>> getLoansByUser(@PathVariable String userId) {
        return ResponseEntity.ok(loanService.getLoansByUser(userId));
    }

    @PutMapping("/return/{loanId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> returnBook(@PathVariable String loanId) {
        loanService.returnBook(loanId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('USER', 'LIBRARIAN')")
    public ResponseEntity<Boolean> isBookAvailable(@RequestParam String bookId) {
        return ResponseEntity.ok(loanService.isBookAvailable(bookId));
    }
}