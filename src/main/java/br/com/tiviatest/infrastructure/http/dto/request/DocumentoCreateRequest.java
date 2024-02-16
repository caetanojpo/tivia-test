package br.com.tiviatest.infrastructure.http.dto.request;

import br.com.tiviatest.domain.enums.TipoDocumento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DocumentoCreateRequest(

        @NotNull(message = "{NOT.BLANK}")
        @Schema(description = "Tipo especifico do documento", example = "CPF")
        TipoDocumento tipoDocumento,

        @NotBlank(message = "{NOT.BLANK}")
        @Schema(description = "Número, valor ou informação que identifique o documento", example = "111.111.111-11")
        String descricao
) {
}
