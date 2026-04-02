package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.controller.dto.BookDTO;
import edu.eci.dosw.DOSW_Library.controller.mapper.BookMapper;
import edu.eci.dosw.DOSW_Library.core.exception.BookNotFoundException;
import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.persistence.repository.BookRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepositoryPort bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepositoryPort bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public BookDTO addBook(String title, String autor, String isbn, Integer totalCopies, Integer availableCopies) {
        if (totalCopies <= 0) throw new IllegalArgumentException("Total copies must be greater than 0");
        if (availableCopies < 0) throw new IllegalArgumentException("Available copies cannot be negative");
        if (availableCopies > totalCopies) throw new IllegalArgumentException("Available copies cannot exceed total copies");

        Book book = Book.builder()
                .title(title)
                .autor(autor)
                .isbn(isbn)
                .totalCopies(totalCopies)
                .availableCopies(availableCopies)
                .build();

        return bookMapper.toDTO(bookRepository.save(book));
    }

    public BookDTO getBookById(String id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public List<BookDTO> getAllBooks() {
        return bookMapper.toDTOList(bookRepository.findAll());
    }

    public List<BookDTO> getAvailableBooks() {
        return bookMapper.toDTOList(bookRepository.findAvailable());
    }

    public void deleteBook(String id) {
        bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.delete(id);
    }

    public BookDTO updateBook(String id, String title, String autor, Integer totalCopies, Integer availableCopies) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        if (title != null && !title.isBlank()) book.setTitle(title);
        if (autor != null && !autor.isBlank()) book.setAutor(autor);
        if (totalCopies != null) {
            if (totalCopies <= 0) throw new IllegalArgumentException("Total copies must be greater than 0");
            book.setTotalCopies(totalCopies);
        }
        if (availableCopies != null) {
            if (availableCopies < 0) throw new IllegalArgumentException("Available copies cannot be negative");
            if (availableCopies > book.getTotalCopies()) throw new IllegalArgumentException("Available copies cannot exceed total copies");
            book.setAvailableCopies(availableCopies);
        }

        return bookMapper.toDTO(bookRepository.save(book));
    }

    public List<BookDTO> getBooksByAutor(String autor) {
        List<Book> result = bookRepository.findByAutor(autor);
        if (result.isEmpty()) throw new BookNotFoundException(autor);
        return bookMapper.toDTOList(result);
    }

    public boolean existsBook(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }

    public Book getModelById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public void save(Book book) {
        bookRepository.save(book);
    }
}