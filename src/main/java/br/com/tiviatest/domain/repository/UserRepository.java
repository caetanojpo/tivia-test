package br.com.tiviatest.domain.repository;

import br.com.tiviatest.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(String email);
}
