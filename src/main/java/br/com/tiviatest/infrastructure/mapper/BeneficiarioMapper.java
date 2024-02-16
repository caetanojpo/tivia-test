package br.com.tiviatest.infrastructure.mapper;

import br.com.tiviatest.domain.model.Beneficiario;
import br.com.tiviatest.infrastructure.database.schema.BeneficiarioSchema;
import br.com.tiviatest.infrastructure.http.dto.request.BeneficiarioCreateRequest;
import br.com.tiviatest.infrastructure.http.dto.request.BeneficiarioUpdateRequest;
import br.com.tiviatest.infrastructure.http.dto.response.BeneficiarioResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BeneficiarioMapper {

    BeneficiarioMapper INSTANCE = Mappers.getMapper(BeneficiarioMapper.class);

    @Mapping(target = "documentos", source = "documentoSchemas")
    Beneficiario toBeneficiario(BeneficiarioSchema beneficiarioSchema);

    Beneficiario toBeneficiario(BeneficiarioCreateRequest beneficiarioCreateRequest);

    @Mapping(target = "documentos", ignore = true)
    Beneficiario toBeneficiario(BeneficiarioUpdateRequest beneficiarioUpdateRequest);

    @Mapping(target = "documentoSchemas", source = "documentos")
    BeneficiarioSchema toBeneficiarioSchema(Beneficiario beneficiario);

    BeneficiarioResponse toBeneficiarioResponse(Beneficiario beneficiario);

}
