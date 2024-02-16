package br.com.tiviatest.infrastructure.http.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record BeneficiarioResponse(
        @Schema(description = "{ID.DESCRIPTION}", example = "1")
        Long id,
        @Schema(description = "Nome completo do Beneficiario", example = "João Caetano")
        String nome,
        @Schema(description = "Número de telefone pessoal, deve ser acompanhado de prefixo e DDD", example = "+5518997970919")
        String telefone,
        @Schema(description = "Data de nascimento do beneficiario, deve ser enviado em modelo americano", example = "2000-01-01")
        LocalDateTime dataNascimento,
        @Schema(description = "Lista de documentos, pode ou não ser enviada")
        List<DocumentoResponse> documentos,
        LocalDateTime dataInclusao,
        LocalDateTime dataAtualizacao
) {
}
