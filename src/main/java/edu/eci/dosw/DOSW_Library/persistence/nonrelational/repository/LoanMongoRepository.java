package edu.eci.dosw.DOSW_Library.persistence.nonrelational.repository;

import edu.eci.dosw.DOSW_Library.persistence.nonrelational.document.LoanDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanMongoRepository extends MongoRepository<LoanDocument, String> {

    List<LoanDocument> findByStatus(String status);
    List<LoanDocument> findByUserId(String userId);
    Optional<LoanDocument> findByBookIdAndUserId(String bookId, String userId);
}