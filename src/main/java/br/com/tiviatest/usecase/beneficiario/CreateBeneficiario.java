package br.com.tiviatest.usecase.beneficiario;

import br.com.tiviatest.domain.model.Beneficiario;
import br.com.tiviatest.domain.repository.BeneficiarioRepository;

public class CreateBeneficiario {

    private final BeneficiarioRepository repository;

    public CreateBeneficiario(BeneficiarioRepository repository) {
        this.repository = repository;
    }

    public Beneficiario execute(Beneficiario beneficiario) {
        return repository.save(beneficiario);
    }
}
