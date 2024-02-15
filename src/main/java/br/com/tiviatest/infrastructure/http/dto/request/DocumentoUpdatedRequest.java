package br.com.tiviatest.infrastructure.http.dto.request;

public record DocumentoUpdatedRequest(
        String tipoDocumento,
        String descricao
) {
}
