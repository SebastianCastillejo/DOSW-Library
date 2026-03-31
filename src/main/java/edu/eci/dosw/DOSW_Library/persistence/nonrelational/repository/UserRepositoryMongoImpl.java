package edu.eci.dosw.DOSW_Library.persistence.nonrelational.repository;

import edu.eci.dosw.DOSW_Library.core.model.User;
import edu.eci.dosw.DOSW_Library.persistence.nonrelational.mapper.UserDocumentMapper;
import edu.eci.dosw.DOSW_Library.persistence.repository.UserRepositoryPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
public class UserRepositoryMongoImpl implements UserRepositoryPort {

    private final UserMongoRepository repository;
    private final UserDocumentMapper mapper;

    public UserRepositoryMongoImpl(UserMongoRepository repository, UserDocumentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        return mapper.toDomain(repository.save(mapper.toDocument(user)));
    }

    @Override
    public Optional<User> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username)
                .map(mapper::toDomain);
    }

    @Override
    public List<User> findAll() {
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
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }
}