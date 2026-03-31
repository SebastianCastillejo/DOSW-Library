package edu.eci.dosw.DOSW_Library.persistence.nonrelational.repository;

import edu.eci.dosw.DOSW_Library.core.model.Book;
import edu.eci.dosw.DOSW_Library.persistence.nonrelational.mapper.BookDocumentMapper;
import edu.eci.dosw.DOSW_Library.persistence.repository.BookRepositoryPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
public class BookRepositoryMongoImpl implements BookRepositoryPort {

    private final BookMongoRepository repository;
    private final BookDocumentMapper mapper;

    public BookRepositoryMongoImpl(BookMongoRepository repository, BookDocumentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Book save(Book book) {
        return mapper.toDomain(repository.save(mapper.toDocument(book)));
    }

    @Override
    public Optional<Book> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Book> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<Book> findByAutor(String autor) {
        return repository.findByAutor(autor)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Book> findAvailable() {
        return repository.findByAvailableCopiesGreaterThan(0)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return repository.existsByIsbn(isbn);
    }
}