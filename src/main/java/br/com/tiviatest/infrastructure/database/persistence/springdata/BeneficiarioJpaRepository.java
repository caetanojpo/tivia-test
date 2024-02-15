package br.com.tiviatest.infrastructure.database.persistence.springdata;

import br.com.tiviatest.infrastructure.database.schema.BeneficiarioSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiarioJpaRepository extends JpaRepository<BeneficiarioSchema, Long> {


}
