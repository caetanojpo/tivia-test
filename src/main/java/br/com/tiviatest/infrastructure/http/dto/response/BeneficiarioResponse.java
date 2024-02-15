package br.com.tiviatest.infrastructure.http.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record BeneficiarioResponse(
        Long id,
        String nome,
        String telefone,
        LocalDate dataNascimento,
        List<DocumentoResponse> documentos,
        LocalDateTime dataInclusao,
        LocalDateTime dataAtualizacao
) {
}
