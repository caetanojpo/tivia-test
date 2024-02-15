package br.com.tiviatest.infrastructure.database.persistence.repository;

import br.com.tiviatest.domain.model.User;
import br.com.tiviatest.domain.repository.UserRepository;
import br.com.tiviatest.infrastructure.database.persistence.springdata.UserJpaRepository;
import br.com.tiviatest.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public User findByEmail(String email) {
        var userDetails = jpaRepository.findByEmail(email);

        return mapper.toUser(userDetails);
    }
}
