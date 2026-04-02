package edu.eci.dosw.DOSW_Library.persistence.nonrelational.repository;

import edu.eci.dosw.DOSW_Library.persistence.nonrelational.document.BookDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookMongoRepository extends MongoRepository<BookDocument, String> {

    Optional<BookDocument> findByIsbn(String isbn);
    List<BookDocument> findByAvailableCopiesGreaterThan(int min);
    List<BookDocument> findByAutor(String autor);
    boolean existsByIsbn(String isbn);
}