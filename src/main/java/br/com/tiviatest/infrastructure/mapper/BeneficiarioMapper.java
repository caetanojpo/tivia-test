package br.com.tiviatest.infrastructure.mapper;

import br.com.tiviatest.domain.model.Beneficiario;
import br.com.tiviatest.infrastructure.database.schema.BeneficiarioSchema;
import br.com.tiviatest.infrastructure.http.dto.request.BeneficiarioCreateRequest;
import br.com.tiviatest.infrastructure.http.dto.request.BeneficiarioUpdateRequest;
import br.com.tiviatest.infrastructure.http.dto.response.BeneficiarioResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BeneficiarioMapper {

    BeneficiarioMapper INSTANCE = Mappers.getMapper(BeneficiarioMapper.class);

    Beneficiario toBeneficiario(BeneficiarioSchema beneficiarioSchema);

    Beneficiario toBeneficiario(BeneficiarioCreateRequest beneficiarioCreateRequest);

    Beneficiario toBeneficiario(BeneficiarioUpdateRequest beneficiarioUpdateRequest);

    BeneficiarioSchema toBeneficiarioSchema(Beneficiario beneficiario);


    BeneficiarioResponse toBeneficiarioResponse(Beneficiario beneficiario);

//    List<DocumentoResponse> toDocumentosResponse(List<Documento> documentoList);

//    @Named("mappingDocuments")
////    default List<DocumentoResponse> mappingDocuments(List<Documento> documentos) {
////        return toDocumentosResponse(documentos);
////    }
}
