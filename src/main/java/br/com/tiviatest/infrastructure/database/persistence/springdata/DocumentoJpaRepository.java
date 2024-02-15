package br.com.tiviatest.infrastructure.database.persistence.springdata;

import br.com.tiviatest.infrastructure.database.schema.DocumentoSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentoJpaRepository extends JpaRepository<DocumentoSchema, Long> {

    List<DocumentoSchema> findAllByBeneficiarioSchemaId(Long beneficiarioId);
}
