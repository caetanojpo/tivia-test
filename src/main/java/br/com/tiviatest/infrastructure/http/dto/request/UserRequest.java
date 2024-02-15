package br.com.tiviatest.infrastructure.http.dto.request;

public record UserRequest(
        String email,
        String password
) {
}
