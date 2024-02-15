package br.com.tiviatest.usecase.beneficiario;

import br.com.tiviatest.domain.model.Beneficiario;
import br.com.tiviatest.domain.model.Documento;
import br.com.tiviatest.domain.repository.BeneficiarioRepository;
import br.com.tiviatest.usecase.documento.CreateDocumento;

import java.util.List;

public class CreateBeneficiario {

    private final BeneficiarioRepository repository;
    private final CreateDocumento createDocumento;

    public CreateBeneficiario(BeneficiarioRepository repository, CreateDocumento createDocumento) {
        this.repository = repository;
        this.createDocumento = createDocumento;
    }

    public Beneficiario execute(Beneficiario beneficiario) {

        var savedBeneficiario = repository.save(beneficiario);

        validateAndCreateDocumentos(savedBeneficiario);


        return savedBeneficiario;
    }

    private void validateAndCreateDocumentos(Beneficiario beneficiario){
        if (beneficiario.getDocumentos() != null && !beneficiario.getDocumentos().isEmpty()) {
            beneficiario.getDocumentos().forEach(documento -> {
                documento.setBeneficiario(beneficiario);
                createDocumento.execute(documento);
            });
        }
    }
}
