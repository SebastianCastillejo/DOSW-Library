package edu.eci.dosw.DOSW_Library.persistence.repository;

import edu.eci.dosw.DOSW_Library.persistence.entity.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    List<LoanEntity> findByUserId(Long userId);
    List<LoanEntity> findByStatus(LoanEntity.LoanStatus status);
    Optional<LoanEntity> findByIdAndStatus(Long id, LoanEntity.LoanStatus status);
}