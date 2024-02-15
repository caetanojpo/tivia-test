package br.com.tiviatest.infrastructure.configuration;

import br.com.tiviatest.domain.repository.BeneficiarioRepository;
import br.com.tiviatest.domain.repository.DocumentoRepository;
import br.com.tiviatest.usecase.beneficiario.CreateBeneficiario;
import br.com.tiviatest.usecase.beneficiario.FindBeneficiario;
import br.com.tiviatest.usecase.beneficiario.RemoveBeneficiario;
import br.com.tiviatest.usecase.beneficiario.UpdateBeneficiario;
import br.com.tiviatest.usecase.documento.CreateDocumento;
import br.com.tiviatest.usecase.documento.FindDocumento;
import br.com.tiviatest.usecase.documento.RemoveDocumento;
import br.com.tiviatest.usecase.documento.UpdateDocumento;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public FindBeneficiario findBeneficiario(BeneficiarioRepository beneficiarioRepository) {
        return new FindBeneficiario(beneficiarioRepository);
    }

    @Bean
    public CreateBeneficiario createBeneficiario(BeneficiarioRepository beneficiarioRepository) {
        return new CreateBeneficiario(beneficiarioRepository);
    }

    @Bean
    public UpdateBeneficiario updateBeneficiario(BeneficiarioRepository beneficiarioRepository, FindBeneficiario findBeneficiario) {
        return new UpdateBeneficiario(beneficiarioRepository, findBeneficiario);
    }

    @Bean
    public RemoveBeneficiario removeBeneficiario(BeneficiarioRepository beneficiarioRepository, FindBeneficiario findBeneficiario) {
        return new RemoveBeneficiario(beneficiarioRepository, findBeneficiario);
    }

    @Bean
    public FindDocumento findDocumento(DocumentoRepository documentoRepository, FindBeneficiario findBeneficiario) {
        return new FindDocumento(documentoRepository, findBeneficiario);
    }

    @Bean
    public CreateDocumento createDocumento(DocumentoRepository documentoRepository, FindBeneficiario findBeneficiario) {
        return new CreateDocumento(documentoRepository, findBeneficiario);
    }

    @Bean
    public UpdateDocumento updateDocumento(DocumentoRepository documentoRepository, FindDocumento findDocumento) {
        return new UpdateDocumento(documentoRepository, findDocumento);
    }

    @Bean
    public RemoveDocumento removeDocumento(DocumentoRepository documentoRepository, FindDocumento findDocumento) {
        return new RemoveDocumento(documentoRepository, findDocumento);
    }
}
