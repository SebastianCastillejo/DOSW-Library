package edu.eci.dosw.DOSW_Library.persistence.nonrelational.repository;

import edu.eci.dosw.DOSW_Library.core.model.Loan;
import edu.eci.dosw.DOSW_Library.persistence.nonrelational.mapper.LoanDocumentMapper;
import edu.eci.dosw.DOSW_Library.persistence.repository.LoanRepositoryPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
public class LoanRepositoryMongoImpl implements LoanRepositoryPort {

    private final LoanMongoRepository repository;
    private final LoanDocumentMapper mapper;

    public LoanRepositoryMongoImpl(LoanMongoRepository repository, LoanDocumentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Loan save(Loan loan) {
        return mapper.toDomain(repository.save(mapper.toDocument(loan)));
    }

    @Override
    public Optional<Loan> findById(String id) {
        return repository.findById(id)
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
        return repository.findByUserId(userId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Loan> findActive() {
        return repository.findByStatus("ACTIVE")
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}