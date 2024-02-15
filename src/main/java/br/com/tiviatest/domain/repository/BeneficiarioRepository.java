package br.com.tiviatest.domain.repository;

import br.com.tiviatest.domain.model.Beneficiario;

import java.util.List;
import java.util.Optional;

public interface BeneficiarioRepository {

    Optional<Beneficiario> findById(Long id);

    List<Beneficiario> findAll();

    Beneficiario save(Beneficiario beneficiario);

    Beneficiario update(Beneficiario beneficiario);

    void delete(Long id);
}
