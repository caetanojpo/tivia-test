package br.com.tiviatest.infrastructure.mapper;

import br.com.tiviatest.domain.model.Documento;
import br.com.tiviatest.infrastructure.database.schema.DocumentoSchema;
import br.com.tiviatest.infrastructure.http.dto.request.DocumentoCreateRequest;
import br.com.tiviatest.infrastructure.http.dto.request.DocumentoUpdatedRequest;
import br.com.tiviatest.infrastructure.http.dto.response.DocumentoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocumentoMapper {

    DocumentoMapper INSTANCE = Mappers.getMapper(DocumentoMapper.class);


    Documento toDocumento(DocumentoSchema documentoSchema);

    Documento toDocumento(DocumentoCreateRequest documentoCreateRequest);

    Documento toDocumento(DocumentoUpdatedRequest documentoUpdatedRequest);

    @Mapping(target = "beneficiarioSchema", source = "beneficiario")
    DocumentoSchema toDocumentoSchema(Documento documento);

    DocumentoResponse toDocumentoResponse(Documento documento);


}
