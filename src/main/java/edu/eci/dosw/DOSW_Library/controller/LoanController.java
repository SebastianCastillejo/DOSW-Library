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

    // Punto 7: Solo USER solicita préstamos
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<LoanDTO> loanBook(@RequestParam Long bookId,
                                            @RequestParam Long userId) {
        return ResponseEntity.ok(loanService.loanBook(bookId, userId));
    }

    // Punto 7: Solo LIBRARIAN gestiona todos los préstamos activos
    @GetMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<List<LoanDTO>> getActiveLoans() {
        return ResponseEntity.ok(loanService.getActiveLoans());
    }

    // Punto 7: LIBRARIAN ve todos, USER solo los suyos (#userId debe coincidir con el token)
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<List<LoanDTO>> getLoansByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(loanService.getLoansByUser(userId));
    }

    // Punto 7: USER devuelve libros
    @PutMapping("/return/{loanId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> returnBook(@PathVariable Long loanId) {
        loanService.returnBook(loanId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('USER', 'LIBRARIAN')")
    public ResponseEntity<Boolean> isBookAvailable(@RequestParam Long bookId) {
        return ResponseEntity.ok(loanService.isBookAvailable(bookId));
    }
}