package br.com.tiviatest.infrastructure.database.schema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "documentos")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(name = "tipo_documento", nullable = false)
    private String tipoDocumento;

    @Column(nullable = false)
    private String descricao;

    @Column(name = "data_inclusao", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime dataInclusao;

    @Column(name = "data_atualizacao", nullable = false)
    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "beneficiario_id")
    private BeneficiarioSchema beneficiario;
}
