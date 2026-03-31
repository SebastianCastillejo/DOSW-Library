package edu.eci.dosw.DOSW_Library.persistence.repository;

import edu.eci.dosw.DOSW_Library.core.model.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanRepositoryPort {

    Loan save(Loan loan);
    Optional<Loan> findById(String id);
    List<Loan> findAll();
    List<Loan> findByUserId(String userId);
    List<Loan> findActive();
    void delete(String id);
}