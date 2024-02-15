package br.com.tiviatest.infrastructure.database.persistence.repository;

import br.com.tiviatest.domain.model.Beneficiario;
import br.com.tiviatest.domain.repository.BeneficiarioRepository;
import br.com.tiviatest.infrastructure.database.persistence.springdata.BeneficiarioJpaRepository;
import br.com.tiviatest.infrastructure.mapper.BeneficiarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BeneficiarioH2Repository implements BeneficiarioRepository {

    private final BeneficiarioJpaRepository jpaRepository;
    private static final BeneficiarioMapper mapper = BeneficiarioMapper.INSTANCE;

    @Override
    public Optional<Beneficiario> findById(Long id) {
        return jpaRepository.findById(id).map(BeneficiarioMapper.INSTANCE::toBeneficiario);
    }

    @Override
    public List<Beneficiario> findAll() {
        return jpaRepository.findAll().stream().map(BeneficiarioMapper.INSTANCE::toBeneficiario).toList();
    }

    @Override
    public Beneficiario save(Beneficiario beneficiario) {
        return mapper.toBeneficiario(jpaRepository.save(mapper.toBeneficiarioSchema(beneficiario)));
    }

    @Override
    public Beneficiario update(Beneficiario beneficiario) {
        return mapper.toBeneficiario(jpaRepository.save(mapper.toBeneficiarioSchema(beneficiario)));
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

}
