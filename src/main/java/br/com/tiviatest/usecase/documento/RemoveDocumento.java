package br.com.tiviatest.usecase.documento;

import br.com.tiviatest.domain.model.Documento;
import br.com.tiviatest.domain.repository.DocumentoRepository;

public class RemoveDocumento {

    private final DocumentoRepository repository;
    private final FindDocumento find;

    public RemoveDocumento(DocumentoRepository repository, FindDocumento find) {
        this.repository = repository;
        this.find = find;
    }

    public void execute(Long id) {
        Documento documento = find.byId(id);
        repository.delete(documento.getId());
    }
}
