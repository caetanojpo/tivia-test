package br.com.tiviatest.infrastructure.http.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record BeneficiarioUpdateRequest(

        @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ ]+$", message = "{ONLY.ALPHABETICAL}")
        @Size(min = 2, max = 100)
        String nome,

        @Pattern(regexp = "^\\+\\d{1,4}\\d{1,14}$", message = "{PHONE.PATTERN}")
        @Size(min = 8, max = 14)
        String telefone,

        @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
        Date dataNascimento
) {
}
