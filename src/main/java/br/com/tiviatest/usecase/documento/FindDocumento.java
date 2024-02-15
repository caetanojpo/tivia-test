package br.com.tiviatest.usecase.documento;

import br.com.tiviatest.domain.exception.ObjectNotFoundException;
import br.com.tiviatest.domain.model.Beneficiario;
import br.com.tiviatest.domain.model.Documento;
import br.com.tiviatest.domain.repository.DocumentoRepository;
import br.com.tiviatest.usecase.beneficiario.FindBeneficiario;

import java.util.List;

public class FindDocumento {

    private final DocumentoRepository repository;
    private final FindBeneficiario findBeneficiario;

    public FindDocumento(DocumentoRepository repository, FindBeneficiario findBeneficiario) {
        this.repository = repository;
        this.findBeneficiario = findBeneficiario;
    }

    public Documento byId(Long id){
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("O Documento não pode ser encontrado sob o id: " + id));
    }

    public List<Documento> allByBeneficiarioId(Long beneficiarioId){
        return repository.findAllByBeneficiarioId(beneficiarioId);
    }
}
