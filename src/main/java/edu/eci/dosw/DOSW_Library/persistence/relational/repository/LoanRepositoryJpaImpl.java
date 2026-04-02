package edu.eci.dosw.DOSW_Library.persistence.relational.repository;

import edu.eci.dosw.DOSW_Library.core.model.Loan;
import edu.eci.dosw.DOSW_Library.core.model.Status;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.LoanEntity;
import edu.eci.dosw.DOSW_Library.persistence.relational.mapper.LoanEntityMapper;
import edu.eci.dosw.DOSW_Library.persistence.repository.LoanRepositoryPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("relational")
public class LoanRepositoryJpaImpl implements LoanRepositoryPort {

    private final LoanRepository repository;
    private final LoanEntityMapper mapper;

    public LoanRepositoryJpaImpl(LoanRepository repository, LoanEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Loan save(Loan loan) {
        return mapper.toDomain(repository.save(mapper.toEntity(loan)));
    }

    @Override
    public Optional<Loan> findById(String id) {
        return repository.findById(Long.valueOf(id))
                .map(mapper::toDomain);
    }

    @Override
    public List<Loan> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Loan> findByUserId(String userId) {
        return repository.findByUserId(Long.valueOf(userId))
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Loan> findActive() {
        return repository.findByStatus(LoanEntity.LoanStatus.ACTIVE)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(String id) {
        repository.deleteById(Long.valueOf(id));
    }
}