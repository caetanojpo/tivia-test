package br.com.tiviatest.domain.repository;

import br.com.tiviatest.domain.model.User;

public interface UserRepository {

    User save(User user);

    User findByEmail(String email);
}
