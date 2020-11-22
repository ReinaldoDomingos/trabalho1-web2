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
@Table(name = "TB_PESSOA_JURIDICA")
@EqualsAndHashCode(callSuper = true)
public class PessoaJuridica extends Pessoa {

    private static final long serialVersionUID = 1L;
    public static final String DISCRIMINATOR_VALUE = "JURIDICA";

    @Column(name = "PJ_CNPJ", length = 14, nullable = false, unique = true)
    private String cnpj;
}
