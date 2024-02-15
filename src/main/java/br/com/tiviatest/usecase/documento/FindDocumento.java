package br.com.tiviatest.usecase.documento;

import br.com.tiviatest.domain.exception.ObjectNotFoundException;
import br.com.tiviatest.domain.model.Documento;
import br.com.tiviatest.domain.repository.DocumentoRepository;

import java.util.List;

public class FindDocumento {

    private final DocumentoRepository repository;

    public FindDocumento(DocumentoRepository repository) {
        this.repository = repository;
    }

    public Documento byId(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("O Documento não pode ser encontrado sob o id: " + id));
    }

    public List<Documento> allByBeneficiarioId(Long beneficiarioId) {
        return repository.findAllByBeneficiarioId(beneficiarioId);
    }

    public List<Documento> all() {
        return repository.findAll();
    }
}
