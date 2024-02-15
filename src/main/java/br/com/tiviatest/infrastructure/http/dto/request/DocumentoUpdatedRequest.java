package br.com.tiviatest.infrastructure.http.dto.request;

import jakarta.validation.constraints.NotNull;

public record DocumentoUpdatedRequest(
        String tipoDocumento,
        String descricao
) {
}
