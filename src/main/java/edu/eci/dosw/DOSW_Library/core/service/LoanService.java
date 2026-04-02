package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.controller.dto.LoanDTO;
import edu.eci.dosw.DOSW_Library.controller.mapper.LoanMapper;
import edu.eci.dosw.DOSW_Library.core.exception.BookNotAvailableException;
import edu.eci.dosw.DOSW_Library.core.exception.LoanNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.core.model.Loan;
import edu.eci.dosw.DOSW_Library.core.model.Status;
import edu.eci.dosw.DOSW_Library.core.model.User;
import edu.eci.dosw.DOSW_Library.persistence.repository.LoanRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepositoryPort loanRepository;
    private final BookService bookService;
    private final UserService userService;
    private final LoanMapper loanMapper;

    public LoanService(LoanRepositoryPort loanRepository,
                       BookService bookService,
                       UserService userService,
                       LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.bookService = bookService;
        this.userService = userService;
        this.loanMapper = loanMapper;
    }

    @Transactional
    public LoanDTO loanBook(String bookId, String userId) {
        Book book = bookService.getModelById(String.valueOf(bookId));
        User user = userService.getModelById(String.valueOf(userId));

        if (book.getAvailableCopies() <= 0) {
            throw new BookNotAvailableException(String.valueOf(bookId));
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookService.save(book);

        Loan loan = Loan.builder()
                .bookId(String.valueOf(bookId))
                .userId(String.valueOf(userId))
                .loanDate(LocalDate.now())
                .status(Status.ACTIVE)
                .build();

        return loanMapper.toDTO(loanRepository.save(loan));
    }

    public List<LoanDTO> getActiveLoans() {
        return loanMapper.toDTOList(loanRepository.findActive());
    }

    public List<LoanDTO> getLoansByUser(String userId) {
        return loanMapper.toDTOList(loanRepository.findByUserId(String.valueOf(userId)));
    }

    @Transactional
    public void returnBook(String loanId) {
        Loan loan = loanRepository.findById(String.valueOf(loanId))
                .filter(l -> l.getStatus() == Status.ACTIVE)
                .orElseThrow(() -> new LoanNotFoundException(String.valueOf(loanId), "active"));

        Book book = bookService.getModelById(loan.getBookId());
        if (book.getAvailableCopies() < book.getTotalCopies()) {
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookService.save(book);
        }

        loan.setStatus(Status.RETURNED);
        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);
    }

    public boolean isBookAvailable(String bookId) {
        Book book = bookService.getModelById(String.valueOf(bookId));
        return book.getAvailableCopies() > 0;
    }
}