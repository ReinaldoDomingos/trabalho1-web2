package br.ufms.cpcx.trabalho1.entity;

import br.ufms.cpcx.trabalho1.enuns.EnumSituacao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

import static java.util.Objects.nonNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_PESSOA")
@DiscriminatorColumn(name = "PES_TIPO", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PES_ID")
    private Long id;

    @Transient
    @Column(name = "PES_TIPO", updatable = false, insertable = false)
    String tipo;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PES_RESPONSAVEL", updatable = false, insertable = false)
    private Pessoa responsavel;

    @Column(name = "PES_RESPONSAVEL")
    private Long responsavelId;

    @Transient
    private String responsavelNome;

    @Column(name = "PES_SITUACAO")
    private EnumSituacao situacao;

    @Column(name = "PES_NOME", length = 256, nullable = false)
    private String nome;

    @Column(name = "PES_APELIDO")
    private String apelido;

    @Column(name = "PES_DATA_NASCIMENTO")
    private LocalDate dataNascimento;

    public String getTipo() {
        return this.tipo.toUpperCase().replace("PESSOA", "");
    }

    public String getResponsavelNome() {
        return nonNull(responsavel) ? responsavel.getNome() : null;
    }
}
