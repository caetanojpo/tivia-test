package br.com.tiviatest.infrastructure.http.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record JWTResponse(
        @Schema(description = "Token de autenticação")
        String token) {
}
