package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.core.exception.BookNotAvailableException;
import edu.eci.dosw.DOSW_Library.core.exception.LoanNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.Loan;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import edu.eci.dosw.DOSW_Library.core.model.*;

@Service
public class LoanService {
    private final List<Loan> loans = new ArrayList<>();

    public Loan loanBook(Book book, User user) {
        if (!isBookAvailable(book)) {
            throw new BookNotAvailableException(book.getId());
        }
        Loan loan = new Loan(book, user);
        loans.add(loan);
        return loan;
    }

    public boolean isBookAvailable(Book book) {
        return loans.stream()
                .noneMatch(l -> l.getBook().getId().equals(book.getId())
                        && l.getStatus() == Status.ACTIVE);
    }

    public List<Loan> getActiveLoans() {
        return loans.stream()
                .filter(l -> l.getStatus() == Status.ACTIVE)
                .collect(Collectors.toList());
    }

    public void returnBook(Book book, User user) {
        loans.stream()
                .filter(l -> l.getBook().getId().equals(book.getId())
                        && l.getUser().getId().equals(user.getId())
                        && l.getStatus() == Status.ACTIVE)
                .findFirst()
                .orElseThrow(() -> new LoanNotFoundException(book.getId(), user.getId()))
                .setStatus(Status.RETURNED);
    }
}