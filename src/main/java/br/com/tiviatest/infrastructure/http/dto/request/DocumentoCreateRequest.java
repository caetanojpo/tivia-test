package br.com.tiviatest.infrastructure.http.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DocumentoCreateRequest(

        @NotBlank(message = "{NOT.BLANK}")
        String tipoDocumento,

        @NotBlank(message = "{NOT.BLANK}")
        String descricao
) {
}
