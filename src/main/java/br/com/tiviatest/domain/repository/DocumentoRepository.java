package br.com.tiviatest.domain.repository;

import br.com.tiviatest.domain.model.Documento;

import java.util.List;
import java.util.Optional;

public interface DocumentoRepository {

    Optional<Documento> findById(Long id);

    List<Documento> findAllByBeneficiarioId(Long beneficiarioId);

    List<Documento> findAll();

    Documento save(Documento documento);

    Documento update(Documento documento);

    void delete(Long id);
}
