package br.com.tiviatest.infrastructure.http.controller;

import br.com.tiviatest.domain.model.Documento;
import br.com.tiviatest.domain.util.MessageUtil;
import br.com.tiviatest.infrastructure.http.dto.request.DocumentoCreateRequest;
import br.com.tiviatest.infrastructure.http.dto.request.DocumentoUpdatedRequest;
import br.com.tiviatest.infrastructure.http.dto.response.DocumentoResponse;
import br.com.tiviatest.infrastructure.mapper.DocumentoMapper;
import br.com.tiviatest.usecase.documento.CreateDocumento;
import br.com.tiviatest.usecase.documento.FindDocumento;
import br.com.tiviatest.usecase.documento.RemoveDocumento;
import br.com.tiviatest.usecase.documento.UpdateDocumento;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/documentos")
@RequiredArgsConstructor
@Tag(name = "documentos")
@SecurityRequirement(name = "bearer-key")
@Slf4j
public class DocumentoController {

    private final FindDocumento find;
    private final CreateDocumento create;
    private final UpdateDocumento update;
    private final RemoveDocumento remove;

    private static final DocumentoMapper mapper = DocumentoMapper.INSTANCE;
    private static final String ROUTE = "/api/documentos/{id}";


    @GetMapping("/{id}")
    @Operation(summary = "Obter um Documento por ID", method = "GET", description = "Informe o ID do documento na rota para ver os seus dados detalhadamente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado")
    })
    public ResponseEntity<DocumentoResponse> byId(@PathVariable Long id) {
        log.info(MessageUtil.BUSCANDO_OBJETO_BD, MessageUtil.DOCUMENTO_ENTIDADE_NOME);
        var documentoResponse = mapper.toDocumentoResponse(find.byId(id));

        log.info(MessageUtil.RETORNO_HTTP);
        return ResponseEntity.ok(documentoResponse);
    }

    @GetMapping("/beneficiario/{beneficiarioId}")
    @Operation(summary = "Listar todos os documentos de um Beneficiario", method = "GET", description = "Informe o ID do beneficiario na rota para ver os todos os seus documentos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documentos encontrado scom sucesso"),
            @ApiResponse(responseCode = "404", description = "Beneficiario não encontrado")
    })
    public ResponseEntity<List<DocumentoResponse>> allByBeneficiarioId(@PathVariable Long beneficiarioId) {
        log.info(MessageUtil.BUSCANDO_OBJETO_BD, MessageUtil.DOCUMENTO_ENTIDADE_NOME);
        List<Documento> documentos = find.allByBeneficiarioId(beneficiarioId);

        log.info(MessageUtil.MAP_ENT);
        List<DocumentoResponse> documentoResponses = documentos.stream().map(DocumentoMapper.INSTANCE::toDocumentoResponse).toList();

        log.info(MessageUtil.RETORNO_HTTP);
        return ResponseEntity.ok(documentoResponses);
    }

    @GetMapping
    @Operation(summary = "Listar todos os Documentos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documentos listados com sucesso"),
    })
    public ResponseEntity<List<DocumentoResponse>> all() {
        log.info(MessageUtil.BUSCANDO_OBJETO_BD, MessageUtil.DOCUMENTO_ENTIDADE_NOME);
        List<Documento> documentos = find.all();

        log.info(MessageUtil.MAP_ENT);
        List<DocumentoResponse> documentoResponseList = documentos.stream().map(DocumentoMapper.INSTANCE::toDocumentoResponse).toList();

        log.info(MessageUtil.RETORNO_HTTP);
        return ResponseEntity.ok(documentoResponseList);
    }

    @PostMapping("/{beneficiarioId}")
    @Operation(summary = "Criar um novo Documento para um Beneficiario", method = "POST", description = "Informe o ID do beneficiario na rota, para criar um novo documento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Documento criado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Beneficiario não encontrado")
    })
    public ResponseEntity<DocumentoResponse> save(@PathVariable Long beneficiarioId, @RequestBody @Valid DocumentoCreateRequest documentoCreateRequest, UriComponentsBuilder uriComponentsBuilder) {
        log.info(MessageUtil.MAP_ENT);
        var documento = mapper.toDocumento(documentoCreateRequest);

        log.info(MessageUtil.INSERINDO_OBJETO_BD, MessageUtil.DOCUMENTO_ENTIDADE_NOME);
        var createdDocumento = mapper.toDocumentoResponse(create.execute(beneficiarioId, documento));

        URI uri = uriComponentsBuilder.path(ROUTE).buildAndExpand(createdDocumento.id()).toUri();

        log.info(MessageUtil.RETORNO_HTTP);
        return ResponseEntity.created(uri).body(createdDocumento);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um Documento", method = "PUT", description = "Informe o ID do Documento na rota para atualizar os seus dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Documento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado")
    })
    public ResponseEntity<DocumentoResponse> update(@PathVariable Long id, @RequestBody @Valid DocumentoUpdatedRequest documentoUpdatedRequest) {

        log.info(MessageUtil.MAP_ENT);
        var documento = mapper.toDocumento(documentoUpdatedRequest);

        log.info(MessageUtil.ATUALIZANDO_OBJETO_BD, MessageUtil.DOCUMENTO_ENTIDADE_NOME);
        var updatedDocument = mapper.toDocumentoResponse(update.execute(id, documento));

        log.info(MessageUtil.RETORNO_HTTP);
        return ResponseEntity.ok(updatedDocument);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover um Documento", method = "DELETE", description = "Informe o ID do documento na rota para remove-lo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Documento removido"),
            @ApiResponse(responseCode = "404", description = "Documento não encontrado")
    })
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        log.info(MessageUtil.REMOVENDO_OBJETO_BD, MessageUtil.DOCUMENTO_ENTIDADE_NOME);
        remove.execute(id);

        log.info(MessageUtil.RETORNO_HTTP);
        return ResponseEntity.noContent().build();
    }


}
