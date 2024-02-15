package br.com.tiviatest.usecase.beneficiario;

import br.com.tiviatest.domain.model.Beneficiario;
import br.com.tiviatest.domain.repository.BeneficiarioRepository;
import br.com.tiviatest.usecase.documento.CreateDocumento;

public class CreateBeneficiario {

    private final BeneficiarioRepository repository;
    private final CreateDocumento createDocumento;

    public CreateBeneficiario(BeneficiarioRepository repository, CreateDocumento createDocumento) {
        this.repository = repository;
        this.createDocumento = createDocumento;
    }

    public Beneficiario execute(Beneficiario beneficiario) {

        var savedBeneficiario = repository.save(beneficiario);

        if (!savedBeneficiario.getDocumentos().isEmpty()) {
            savedBeneficiario.getDocumentos().forEach(documento -> {
                documento.setBeneficiario(savedBeneficiario);
                createDocumento.execute(documento);
            });
        }
        return savedBeneficiario;
    }
}
