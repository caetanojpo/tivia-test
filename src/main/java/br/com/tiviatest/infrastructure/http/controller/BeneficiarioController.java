package br.com.tiviatest.infrastructure.http.controller;

import br.com.tiviatest.domain.model.Beneficiario;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/beneficiarios")
@RequiredArgsConstructor
@Tag(name = "beneficiario")
public class BeneficiarioController {


    private final FindBeneficiario find;
    private final CreateBeneficiario create;
    private final UpdateBeneficiario update;
    private final RemoveBeneficiario remove;

    private static final BeneficiarioMapper mapper = BeneficiarioMapper.INSTANCE;
    private static final String ROUTE = "/api/beneficiarios/{id}";

    @GetMapping("/{id}")
    @Operation(summary = "Obter um Beneficiário por ID", method = "GET", description = "Informe o ID do beneficiário na rota para ver os seus dados detalhadamente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beneficiário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Beneficiário não encontrado")
    })
    public ResponseEntity<BeneficiarioResponse> byId(@PathVariable Long id) {
        var beneficiario = find.byId(id);
        var beneficiarioResponse = mapper.toBeneficiarioResponse(beneficiario);

        return ResponseEntity.ok(beneficiarioResponse);
    }

    @GetMapping
    @Operation(summary = "Lista todos os Beneficiarios", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de beneficiados buscada com sucesso")
    })
    public ResponseEntity<List<BeneficiarioResponse>> all() {
        List<Beneficiario> beneficiarios = find.all();

        List<BeneficiarioResponse> beneficiarioResponses = beneficiarios.stream().map(BeneficiarioMapper.INSTANCE::toBeneficiarioResponse).toList();

        return ResponseEntity.ok(beneficiarioResponses);
    }

    @PostMapping
    @Operation(summary = "Salvar um novo Beneficiario no banco.", method = "POST", description = "Informe os dados do beneficiario e/ou, também, informe os documentos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Beneficiário cadastrado com sucesso")
    })
    public ResponseEntity<BeneficiarioResponse> save(@RequestBody @Valid BeneficiarioCreateRequest beneficiarioRequest, UriComponentsBuilder uriComponentsBuilder) {
        var beneficiario = mapper.toBeneficiario(beneficiarioRequest);
        var beneficiarioResponse = mapper.toBeneficiarioResponse(create.execute(beneficiario));

        URI uri = uriComponentsBuilder.path(ROUTE).buildAndExpand(beneficiarioResponse.id()).toUri();

        return ResponseEntity.created(uri).body(beneficiarioResponse);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar os dados cadastrais de um Beneficiário por ID", method = "PUT", description = "Informe o ID do beneficiário na rota e envio o corpo para atualização.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beneficiário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Beneficiário não encontrado")
    })
    public ResponseEntity<BeneficiarioResponse> update(@PathVariable Long id, @RequestBody @Valid BeneficiarioUpdateRequest beneficiarioRequest) {
        var beneficiario = mapper.toBeneficiario(beneficiarioRequest);
        var beneficiarioResponse = mapper.toBeneficiarioResponse(update.execute(id, beneficiario));

        return ResponseEntity.ok(beneficiarioResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover um Beneficiário por ID", method = "DELETE", description = "Informe o ID do beneficiário na rota para realizar sua exclusão.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Beneficiário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Beneficiário não encontrado")
    })
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        remove.execute(id);

        return ResponseEntity.noContent().build();
    }
}
