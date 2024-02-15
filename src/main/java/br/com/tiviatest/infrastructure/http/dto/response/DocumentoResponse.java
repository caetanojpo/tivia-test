package br.com.tiviatest.infrastructure.http.dto.response;

import java.time.LocalDateTime;

public record DocumentoResponse(
        Long id,
        String tipoDocumento,
        String descricao,
        LocalDateTime dataInclusao,
        LocalDateTime dataAtualizacao
) {
}
