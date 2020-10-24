package br.ufms.cpcx.trabalho1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_PRODUTO")
public class Produto {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRO_ID")
    private Long id;

    @Column(name = "PRO_DESCRICAO")
    private String descricao;

    @Column(name = "PRO_QUANTIDADE_ESTOQUE")
    private Long quantidadeEstoque;

    @Column(name = "PRO_IDADE_PERMITIDA")
    private Integer idadePermitida;

    @Column(name = "PRO_PRECO_COMPRA", precision = 20, scale = 2)
    private BigDecimal precoCompra;

    @Column(name = "PRO_PRECO_VENDA_FISICA", precision = 20, scale = 2)
    private BigDecimal precoVendaFisica;

    @Column(name = "PRO_PRECO_VENDA_JURIDICA", precision = 20, scale = 2)
    private BigDecimal precoVendaJuridica;

    public Produto(Long id) {
        this.id = id;
    }
}
