package br.com.tiviatest.infrastructure.http.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record DocumentoResponse(
        Long id,
        String tipoDocumento,
        String descricao,
        LocalDateTime dataInclusao,
        LocalDateTime dataAtualizacao
) {
}
