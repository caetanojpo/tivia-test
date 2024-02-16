package br.com.tiviatest.infrastructure.http.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRequest(
        @NotBlank(message = "{NOT.BLANK}")
        @Email
        @Schema(description = "E-Mail do usuário para login no sistema", example = "teste@teste.com.br")
        String email,

        @NotBlank(message = "{NOT.BLANK}")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "A Senha deve ter pelo menos 8 caracteres de comprimento, conter pelo menos uma letra maiúscula, um dígito e um caractere especial."
        )
        @Schema(description = "A senha pessoal do usuário. Deve ter pelo menos 8 caracteres de comprimento, conter pelo menos uma letra maiúscula, um dígito e um caractere especial.",
                example = "!123Teste")
        String password
) {
}
