package edu.eci.dosw.DOSW_Library.persistence.repository;

import edu.eci.dosw.DOSW_Library.core.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryPort {

    Book save(Book book);
    Optional<Book> findById(String id);
    List<Book> findAll();
    void delete(String id);
    List<Book> findByAutor(String autor);
    List<Book> findAvailable();
    boolean existsByIsbn(String isbn);
}