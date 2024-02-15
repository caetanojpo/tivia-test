package br.com.tiviatest.usecase.beneficiario;

import br.com.tiviatest.domain.exception.ObjectNotFoundException;
import br.com.tiviatest.domain.model.Beneficiario;
import br.com.tiviatest.domain.repository.BeneficiarioRepository;

import java.util.List;

public class FindBeneficiario {

    private final BeneficiarioRepository repository;

    public FindBeneficiario(BeneficiarioRepository repository) {
        this.repository = repository;
    }

    public Beneficiario byId(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("O Beneficiário não pode ser encontrado sob o id: " + id));
    }

    public List<Beneficiario> all() {
        return repository.findAll();
    }
}
