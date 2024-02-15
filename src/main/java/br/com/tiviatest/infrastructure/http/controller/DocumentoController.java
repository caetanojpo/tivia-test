package br.com.tiviatest.infrastructure.http.controller;

import br.com.tiviatest.domain.model.Documento;
import br.com.tiviatest.infrastructure.http.dto.request.DocumentoCreateRequest;
import br.com.tiviatest.infrastructure.http.dto.request.DocumentoUpdatedRequest;
import br.com.tiviatest.infrastructure.http.dto.response.DocumentoResponse;
import br.com.tiviatest.infrastructure.mapper.DocumentoMapper;
import br.com.tiviatest.usecase.documento.CreateDocumento;
import br.com.tiviatest.usecase.documento.FindDocumento;
import br.com.tiviatest.usecase.documento.RemoveDocumento;
import br.com.tiviatest.usecase.documento.UpdateDocumento;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/documentos")
@RequiredArgsConstructor
public class DocumentoController {

    private final FindDocumento find;
    private final CreateDocumento create;
    private final UpdateDocumento update;
    private final RemoveDocumento remove;

    private static final DocumentoMapper mapper = DocumentoMapper.INSTANCE;
    private static final String ROUTE = "/documentos/{id}";


    @GetMapping("/{id}")
    public ResponseEntity<DocumentoResponse> byId(@PathVariable Long id) {
        var documentoResponse = mapper.toDocumentoResponse(find.byId(id));

        return ResponseEntity.ok(documentoResponse);
    }

    @GetMapping("/beneficiario/{beneficiarioId}")
    public ResponseEntity<List<DocumentoResponse>> allByBeneficiarioId(@PathVariable Long beneficiarioId) {
        List<Documento> documentos = find.allByBeneficiarioId(beneficiarioId);

        List<DocumentoResponse> documentoResponses = documentos.stream().map(DocumentoMapper.INSTANCE::toDocumentoResponse).toList();

        return ResponseEntity.ok(documentoResponses);
    }

    @GetMapping
    public ResponseEntity<List<DocumentoResponse>> all() {
        List<Documento> documentos = find.all();

        List<DocumentoResponse> documentoResponseList = documentos.stream().map(DocumentoMapper.INSTANCE::toDocumentoResponse).toList();

        return ResponseEntity.ok(documentoResponseList);
    }

    @PostMapping("{beneficiarioId}")
    public ResponseEntity<DocumentoResponse> save(@PathVariable Long beneficiarioId, @RequestBody @Valid DocumentoCreateRequest documentoCreateRequest, UriComponentsBuilder uriComponentsBuilder) {
        var documento = mapper.toDocumento(documentoCreateRequest);
        var createdDocumento = mapper.toDocumentoResponse(create.execute(beneficiarioId, documento));

        URI uri = uriComponentsBuilder.path(ROUTE).buildAndExpand(createdDocumento.id()).toUri();

        return ResponseEntity.created(uri).body(createdDocumento);
    }

    @PutMapping("{id}")
    public ResponseEntity<DocumentoResponse> update(@PathVariable Long id, @RequestBody @Valid DocumentoUpdatedRequest documentoUpdatedRequest) {
        var documento = mapper.toDocumento(documentoUpdatedRequest);
        var updatedDocument = mapper.toDocumentoResponse(update.execute(id, documento));

        return ResponseEntity.ok(updatedDocument);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        remove.execute(id);
        return ResponseEntity.noContent().build();
    }


}
