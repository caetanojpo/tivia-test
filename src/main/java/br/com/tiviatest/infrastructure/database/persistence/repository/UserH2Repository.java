package br.com.tiviatest.infrastructure.database.persistence.repository;

import br.com.tiviatest.domain.exception.ObjectNotFoundException;
import br.com.tiviatest.domain.model.User;
import br.com.tiviatest.domain.repository.UserRepository;
import br.com.tiviatest.infrastructure.database.persistence.springdata.UserJpaRepository;
import br.com.tiviatest.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserH2Repository implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private static final UserMapper mapper = UserMapper.INSTANCE;

    @Override
    public User save(User user) {
        var userSchema = jpaRepository.save(mapper.toUseSchema(user));
        return mapper.toUser(userSchema);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        var userDetails = jpaRepository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado sob o email: " + email));

        return Optional.ofNullable(mapper.toUser(userDetails));
    }
}
