package br.ufms.cpcx.balan.entity;

import br.ufms.cpcx.balan.enuns.EnumSituacao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
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

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_PESSOA")
@DiscriminatorColumn(name = "PES_TIPO")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PES_ID")
    private Long id;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PES_RESPONSAVEL", updatable = false, insertable = false)
    private Pessoa responsavel;

    @Column(name = "PES_RESPONSAVEL")
    private Long responsavelId;

    @Column(name = "PES_SITUACAO")
    private EnumSituacao situacao;

    @Column(name = "PES_NOME", length = 256, nullable = false)
    private String nome;

    @Column(name = "PES_APELIDO")
    private String apelido;

    @Column(name = "PES_DATA_NASCIMENTO")
    private LocalDate dataNascimento;
}
