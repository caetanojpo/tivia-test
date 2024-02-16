package br.com.tiviatest.usecase.user;

import br.com.tiviatest.domain.exception.BadRequestException;
import br.com.tiviatest.domain.model.User;
import br.com.tiviatest.domain.repository.UserRepository;

public class CreateUser {

    private final UserRepository repository;

    public CreateUser(UserRepository repository) {
        this.repository = repository;
    }

    public User execute(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) throw new BadRequestException("Usuário já cadastrado");
        return repository.save(user);
    }
}
