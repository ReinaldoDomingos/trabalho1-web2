package br.ufms.cpcx.trabalho1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USUARIO")
public class Usuario {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "US_ID")
    private Long id;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PES_ID", updatable = false, insertable = false)
    private Pessoa pessoa;

    @Column(name = "PES_ID", unique = true, nullable = false)
    private Long pessoaId;

    @Column(name = "US_LOGIN", unique = true, nullable = false)
    private String login;

    @Column(name = "US_SENHA", nullable = false)
    private String senha;

    @Column(name = "US_IS_ADMINISTRADOR")
    private Boolean isAdministrador;
}
