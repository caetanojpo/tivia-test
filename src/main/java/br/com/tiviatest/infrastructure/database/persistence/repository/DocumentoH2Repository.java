package br.com.tiviatest.infrastructure.database.persistence.repository;

import br.com.tiviatest.domain.model.Documento;
import br.com.tiviatest.domain.repository.DocumentoRepository;
import br.com.tiviatest.infrastructure.database.persistence.springdata.DocumentoJpaRepository;
import br.com.tiviatest.infrastructure.mapper.DocumentoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DocumentoH2Repository implements DocumentoRepository {

    private final DocumentoJpaRepository jpaRepository;
    private static final DocumentoMapper mapper = DocumentoMapper.INSTANCE;

    @Override
    public Optional<Documento> findById(Long id) {
        return jpaRepository.findById(id).map(DocumentoMapper.INSTANCE::toDocumento);
    }

    @Override
    public List<Documento> findAllByBeneficiarioId(Long beneficiarioId) {
        return jpaRepository.findAllByBeneficiarioSchemaId(beneficiarioId).stream().map(DocumentoMapper.INSTANCE::toDocumento).toList();
    }

    @Override
    public List<Documento> findAll() {
        return jpaRepository.findAll().stream().map(DocumentoMapper.INSTANCE::toDocumento).toList();
    }

    @Override
    public Documento save(Documento documento) {
        return mapper.toDocumento(jpaRepository.save(mapper.toDocumentoSchema(documento)));
    }

    @Override
    public Documento update(Documento documento) {
        return mapper.toDocumento(jpaRepository.save(mapper.toDocumentoSchema(documento)));
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }
}
