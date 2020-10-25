package br.ufms.cpcx.trabalho1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_PESSOA_FISICA")
@EqualsAndHashCode(callSuper = true)
public class PessoaFisica extends Pessoa {

    private static final long serialVersionUID = 1L;
    public static final String DISCRIMINATOR_VALUE = "FISICA";

    @Column(name = "PF_RG", length = 11, nullable = false)
    private String rg;

    @Column(name = "PF_CPF", length = 14, nullable = false, unique = true)
    private String cpf;
}
