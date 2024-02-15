package br.com.tiviatest.infrastructure.database.persistence.springdata;

import br.com.tiviatest.infrastructure.database.schema.UserSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserSchema, Long> {

    UserDetails findByEmail(String email);
}
