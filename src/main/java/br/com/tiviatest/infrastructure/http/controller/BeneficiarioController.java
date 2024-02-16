package br.com.tiviatest.infrastructure.http.controller;

import br.com.tiviatest.domain.model.Beneficiario;
import br.com.tiviatest.domain.util.MessageUtil;
import br.com.tiviatest.infrastructure.http.dto.request.BeneficiarioCreateRequest;
import br.com.tiviatest.infrastructure.http.dto.request.BeneficiarioUpdateRequest;
import br.com.tiviatest.infrastructure.http.dto.response.BeneficiarioResponse;
import br.com.tiviatest.infrastructure.mapper.BeneficiarioMapper;
import br.com.tiviatest.usecase.beneficiario.CreateBeneficiario;
import br.com.tiviatest.usecase.beneficiario.FindBeneficiario;
import br.com.tiviatest.usecase.beneficiario.RemoveBeneficiario;
import br.com.tiviatest.usecase.beneficiario.UpdateBeneficiario;
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
@RequestMapping("/api/beneficiarios")
@RequiredArgsConstructor
@Tag(name = "beneficiario")
@SecurityRequirement(name = "bearer-key")
@Slf4j
public class BeneficiarioController {


    private final FindBeneficiario find;
    private final CreateBeneficiario create;
    private final UpdateBeneficiario update;
    private final RemoveBeneficiario remove;

    private static final BeneficiarioMapper mapper = BeneficiarioMapper.INSTANCE;
    private static final String ROUTE = "/api/beneficiarios/{id}";

    @GetMapping("/{id}")
    @Operation(summary = "Obter um Beneficiario por ID", method = "GET", description = "Informe o ID do beneficiário na rota para ver os seus dados detalhadamente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beneficiario encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Beneficiario não encontrado")
    })
    public ResponseEntity<BeneficiarioResponse> byId(@PathVariable Long id) {
        log.info(MessageUtil.BUSCANDO_OBJETO_BD, MessageUtil.BENEFICIARIO_ENTIDADE_NOME);
        var beneficiario = find.byId(id);

        log.info(MessageUtil.MAP_ENT);
        var beneficiarioResponse = mapper.toBeneficiarioResponse(beneficiario);

        log.info(MessageUtil.RETORNO_HTTP);
        return ResponseEntity.ok(beneficiarioResponse);
    }

    @GetMapping
    @Operation(summary = "Lista todos os Beneficiarios", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de beneficiados buscada com sucesso")
    })
    public ResponseEntity<List<BeneficiarioResponse>> all() {
        log.info(MessageUtil.INICIANDO_BUSCA_POR_ID, MessageUtil.BENEFICIARIO_ENTIDADE_NOME);
        List<Beneficiario> beneficiarios = find.all();

        log.info(MessageUtil.MAP_ENT);
        List<BeneficiarioResponse> beneficiarioResponses = beneficiarios.stream().map(BeneficiarioMapper.INSTANCE::toBeneficiarioResponse).toList();

        log.info(MessageUtil.RETORNO_HTTP);
        return ResponseEntity.ok(beneficiarioResponses);
    }

    @PostMapping
    @Operation(summary = "Salvar um novo Beneficiario no banco.", method = "POST", description = "Informe os dados do beneficiario e/ou, também, informe os documentos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Beneficiario cadastrado com sucesso")
    })
    public ResponseEntity<BeneficiarioResponse> save(@RequestBody @Valid BeneficiarioCreateRequest beneficiarioRequest, UriComponentsBuilder uriComponentsBuilder) {
        log.info(MessageUtil.INSERINDO_OBJETO_BD, MessageUtil.BENEFICIARIO_ENTIDADE_NOME);
        var beneficiario = mapper.toBeneficiario(beneficiarioRequest);

        log.info(MessageUtil.MAP_ENT);
        var beneficiarioResponse = mapper.toBeneficiarioResponse(create.execute(beneficiario));

        URI uri = uriComponentsBuilder.path(ROUTE).buildAndExpand(beneficiarioResponse.id()).toUri();

        log.info(MessageUtil.RETORNO_HTTP);
        return ResponseEntity.created(uri).body(beneficiarioResponse);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar os dados cadastrais de um Beneficiario por ID", method = "PUT", description = "Informe o ID do beneficiário na rota e envio o corpo para atualização.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beneficiario atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Beneficiario não encontrado")
    })
    public ResponseEntity<BeneficiarioResponse> update(@PathVariable Long id, @RequestBody @Valid BeneficiarioUpdateRequest beneficiarioRequest) {
        log.info(MessageUtil.ATUALIZANDO_OBJETO_BD, MessageUtil.BENEFICIARIO_ENTIDADE_NOME);
        var beneficiario = mapper.toBeneficiario(beneficiarioRequest);

        log.info(MessageUtil.MAP_ENT);
        var beneficiarioResponse = mapper.toBeneficiarioResponse(update.execute(id, beneficiario));

        log.info(MessageUtil.RETORNO_HTTP);
        return ResponseEntity.ok(beneficiarioResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover um Beneficiario por ID", method = "DELETE", description = "Informe o ID do beneficiário na rota para realizar sua exclusão.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beneficiario deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Beneficiario não encontrado")
    })
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        log.info(MessageUtil.REMOVENDO_OBJETO_BD, MessageUtil.BENEFICIARIO_ENTIDADE_NOME);
        remove.execute(id);

        log.info(MessageUtil.RETORNO_HTTP);
        return ResponseEntity.noContent().build();
    }
}
