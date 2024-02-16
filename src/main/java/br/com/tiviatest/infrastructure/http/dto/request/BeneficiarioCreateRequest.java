package br.com.tiviatest.infrastructure.http.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public record BeneficiarioCreateRequest(

        @NotBlank(message = "{NOT.BLANK}")
        @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ ]+$", message = "{ONLY.ALPHABETICAL}")
        @Size(min = 2, max = 100, message = "Deve ter entre 2 a 100 caracteres")
        @Schema(description = "Nome completo do Beneficiario", example = "João Caetano")
        String nome,

        @NotBlank(message = "{NOT.BLANK}")
        @Pattern(regexp = "^\\+\\d{1,4}\\d{1,14}$", message = "{PHONE.PATTERN}")
        @Size(min = 8, max = 14, message = "Deve ter de 8 a 14 digitos")
        @Schema(description = "Número de telefone pessoal, deve ser acompanhado de prefixo e DDD", example = "+5518997970919")
        String telefone,

        @NotNull(message = "{NOT.NULL}")
        @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
        @Schema(description = "Data de nascimento do beneficiario, deve ser enviado em modelo americano", example = "2000-01-01")
        Date dataNascimento,

        List<DocumentoCreateRequest> documentos
) {
}
