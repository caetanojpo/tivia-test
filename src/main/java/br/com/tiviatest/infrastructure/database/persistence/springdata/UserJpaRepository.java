package br.com.tiviatest.infrastructure.database.persistence.springdata;

import br.com.tiviatest.infrastructure.database.schema.UserSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserSchema, Long> {

    Optional<UserDetails> findByEmail(String email);
}
