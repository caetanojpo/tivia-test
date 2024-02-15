package br.com.tiviatest.usecase.documento;

import br.com.tiviatest.domain.model.Documento;
import br.com.tiviatest.domain.repository.DocumentoRepository;
import br.com.tiviatest.usecase.beneficiario.FindBeneficiario;

public class CreateDocumento {

    private final DocumentoRepository repository;
    private final FindBeneficiario findBeneficiario;

    public CreateDocumento(DocumentoRepository repository, FindBeneficiario findBeneficiario) {
        this.repository = repository;
        this.findBeneficiario = findBeneficiario;
    }

    public Documento execute(Documento documento) {
        return repository.save(documento);
    }

    public Documento execute(Long beneficiarioId, Documento documento) {
        var beneficiario = findBeneficiario.byId(beneficiarioId);
        documento.setBeneficiario(beneficiario);
        return repository.save(documento);
    }
}
