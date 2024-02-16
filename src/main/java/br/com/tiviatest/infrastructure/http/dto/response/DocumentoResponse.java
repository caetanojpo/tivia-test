package br.com.tiviatest.infrastructure.http.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record DocumentoResponse(
        @Schema(description = "{ID.DESCRIPTION}", example = "1")
        Long id,
        @Schema(description = "Tipo especifico do documento", example = "CPF")
        String tipoDocumento,
        @Schema(description = "Número, valor ou informação que identifique o documento", example = "111.111.111-11")
        String descricao,
        LocalDateTime dataInclusao,
        LocalDateTime dataAtualizacao
) {
}
