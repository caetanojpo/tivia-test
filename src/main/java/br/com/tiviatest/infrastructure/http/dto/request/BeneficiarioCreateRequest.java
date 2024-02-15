package br.com.tiviatest.infrastructure.http.dto.request;

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
        @Size(min = 2, max = 100)
        String nome,

        @NotBlank(message = "{NOT.BLANK}")
        @Pattern(regexp = "^\\+\\d{1,4}\\d{1,14}$", message = "{PHONE.PATTERN}")
        @Size(min = 8, max = 14)
        String telefone,

        @NotNull(message = "{NOT.NULL}")
        @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
        Date dataNascimento,

        List<DocumentoCreateRequest> documentos
) {
}
