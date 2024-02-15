package br.com.tiviatest.usecase.documento;

import br.com.tiviatest.domain.model.Documento;
import br.com.tiviatest.domain.repository.DocumentoRepository;

public class UpdateDocumento {

    private final DocumentoRepository repository;
    private final FindDocumento find;

    public UpdateDocumento(DocumentoRepository repository, FindDocumento find) {
        this.repository = repository;
        this.find = find;
    }

    public Documento execute(Long id, Documento newDocumentoData) {
        Documento toUpdateDocumento = find.byId(id);

        toUpdateDocumento.setTipoDocumento(validateForEmptyValue(newDocumentoData.getTipoDocumento(), toUpdateDocumento.getTipoDocumento()));
        toUpdateDocumento.setDescricao(validateForEmptyValue(newDocumentoData.getDescricao(), toUpdateDocumento.getDescricao()));

        return repository.save(toUpdateDocumento);
    }

    private String validateForEmptyValue(String newValue, String currentValue) {
        return newValue != null && !newValue.trim().isEmpty() ? newValue : currentValue;
    }
}
