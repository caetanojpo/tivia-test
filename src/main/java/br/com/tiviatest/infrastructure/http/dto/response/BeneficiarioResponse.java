package br.com.tiviatest.infrastructure.http.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record BeneficiarioResponse(
        Long id,
        String nome,
        String telefone,
        LocalDateTime dataNascimento,
        List<DocumentoResponse> documentos,
        LocalDateTime dataInclusao,
        LocalDateTime dataAtualizacao
) {
}
