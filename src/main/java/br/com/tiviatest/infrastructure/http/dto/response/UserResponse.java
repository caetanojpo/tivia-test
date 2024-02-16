package br.com.tiviatest.infrastructure.http.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponse(
        @Schema(description = "{ID.DESCRIPTION}", example = "1")
        Long id,
        @Schema(description = "E-Mail do usuário para login no sistema", example = "teste@teste.com.br")
        String email
) {
}
