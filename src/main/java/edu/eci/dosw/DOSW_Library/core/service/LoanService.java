package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.controller.dto.LoanDTO;
import edu.eci.dosw.DOSW_Library.controller.mapper.LoanMapper;
import edu.eci.dosw.DOSW_Library.core.exception.BookNotAvailableException;
import edu.eci.dosw.DOSW_Library.core.exception.LoanNotFoundException;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.BookEntity;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.LoanEntity;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.LoanEntity.LoanStatus;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.UserEntity;
import edu.eci.dosw.DOSW_Library.persistence.relational.repository.LoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookService bookService;
    private final UserService userService;
    private final LoanMapper loanMapper;

    public LoanService(LoanRepository loanRepository,
                       BookService bookService,
                       UserService userService,
                       LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.bookService = bookService;
        this.userService = userService;
        this.loanMapper = loanMapper;
    }

    // USER puede solicitar préstamo
    @Transactional
    public LoanDTO loanBook(Long bookId, Long userId) {
        BookEntity book = bookService.getEntityById(bookId);
        UserEntity user = userService.getEntityById(userId);

        if (book.getAvailableCopies() <= 0) {
            throw new BookNotAvailableException(String.valueOf(bookId));
        }

        // Reducir disponibilidad y guardar
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookService.save(book);

        LoanEntity loan = LoanEntity.builder()
                .book(book)
                .user(user)
                .loanDate(LocalDate.now())
                .status(LoanStatus.ACTIVE)
                .build();

        return loanMapper.toDTO(loanRepository.save(loan));
    }

    // LIBRARIAN puede ver todos los préstamos activos
    public List<LoanDTO> getActiveLoans() {
        return loanMapper.toDTOList(loanRepository.findByStatus(LoanStatus.ACTIVE));
    }

    // USER puede ver solo sus propios préstamos
    public List<LoanDTO> getLoansByUser(Long userId) {
        return loanMapper.toDTOList(loanRepository.findByUserId(userId));
    }

    // USER puede devolver un libro
    @Transactional
    public void returnBook(Long loanId) {
        LoanEntity loan = loanRepository.findByIdAndStatus(loanId, LoanStatus.ACTIVE)
                .orElseThrow(() -> new LoanNotFoundException(String.valueOf(loanId), "active"));

        // Aumentar disponibilidad, sin superar totalCopies
        BookEntity book = loan.getBook();
        if (book.getAvailableCopies() < book.getTotalCopies()) {
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookService.save(book);
        }

        loan.setStatus(LoanStatus.RETURNED);
        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);
    }

    public boolean isBookAvailable(Long bookId) {
        BookEntity book = bookService.getEntityById(bookId);
        return book.getAvailableCopies() > 0;
    }
}