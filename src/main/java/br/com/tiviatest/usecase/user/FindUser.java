package br.com.tiviatest.usecase.user;

import br.com.tiviatest.domain.model.User;
import br.com.tiviatest.domain.repository.UserRepository;

public class FindUser {

    private final UserRepository repository;

    public FindUser(UserRepository repository) {
        this.repository = repository;
    }

    public User byEmail(String email) {
        return repository.findByEmail(email);
    }
}
