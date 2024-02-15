package br.com.tiviatest.usecase.beneficiario;

import br.com.tiviatest.domain.model.Beneficiario;
import br.com.tiviatest.domain.repository.BeneficiarioRepository;

public class RemoveBeneficiario {

    private final BeneficiarioRepository repository;
    private final FindBeneficiario find;

    public RemoveBeneficiario(BeneficiarioRepository repository, FindBeneficiario find) {
        this.repository = repository;
        this.find = find;
    }

    public void execute(Long id) {
        Beneficiario beneficiario = find.byId(id);
        repository.delete(beneficiario.getId());
    }
}
