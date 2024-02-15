package br.com.tiviatest.domain.repository;

import br.com.tiviatest.domain.model.Beneficiario;
import br.com.tiviatest.domain.model.Documento;

import java.util.List;
import java.util.Optional;

public interface DocumentoRepository {

    Optional<Documento> findById(Long id);

    List<Documento> findAllByBeneficiarioId(Beneficiario beneficiario);

    Documento save(Documento documento);

    Documento update(Documento documento);

    void delete(Long id);
}
