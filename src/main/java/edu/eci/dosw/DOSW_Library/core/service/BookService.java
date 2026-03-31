package edu.eci.dosw.DOSW_Library.core.service;

import edu.eci.dosw.DOSW_Library.controller.dto.BookDTO;
import edu.eci.dosw.DOSW_Library.controller.mapper.BookMapper;
import edu.eci.dosw.DOSW_Library.core.exception.BookNotFoundException;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.BookEntity;
import edu.eci.dosw.DOSW_Library.persistence.relational.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    // Solo LIBRARIAN puede crear libros
    public BookDTO addBook(String title, String autor, String isbn, Integer totalCopies, Integer availableCopies) {
        if (totalCopies <= 0) {
            throw new IllegalArgumentException("Total copies must be greater than 0");
        }
        if (availableCopies < 0) {
            throw new IllegalArgumentException("Available copies cannot be negative");
        }
        if (availableCopies > totalCopies) {
            throw new IllegalArgumentException("Available copies cannot exceed total copies");
        }

        BookEntity entity = BookEntity.builder()
                .title(title)
                .autor(autor)
                .isbn(isbn)
                .totalCopies(totalCopies)
                .availableCopies(availableCopies)
                .build();

        return bookMapper.toDTO(bookRepository.save(entity));
    }

    // Cualquier usuario autenticado puede ver libros
    public BookDTO getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(String.valueOf(id)));
    }

    public List<BookDTO> getAllBooks() {
        return bookMapper.toDTOList(bookRepository.findAll());
    }

    // Solo libros con copias disponibles > 0
    public List<BookDTO> getAvailableBooks() {
        return bookMapper.toDTOList(bookRepository.findByAvailableCopiesGreaterThan(0));
    }

    // Solo LIBRARIAN puede eliminar libros
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(String.valueOf(id));
        }
        bookRepository.deleteById(id);
    }

    // Solo LIBRARIAN puede actualizar libros (incluyendo stock)
    public BookDTO updateBook(Long id, String title, String autor, Integer totalCopies, Integer availableCopies) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.valueOf(id)));

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
        List<BookEntity> result = bookRepository.findByAutor(autor);
        if (result.isEmpty()) {
            throw new BookNotFoundException(autor);
        }
        return bookMapper.toDTOList(result);
    }

    public boolean existsBook(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }

    // Métodos internos usados por LoanService (trabajan con entidades)
    public BookEntity getEntityById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(String.valueOf(id)));
    }

    public void save(BookEntity book) {
        bookRepository.save(book);
    }
}