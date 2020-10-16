package br.ufms.cpcx.balan.dto;

import br.ufms.cpcx.balan.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;
    private String descricao;
    private Long quantidadeEstoque;
    private BigDecimal precoCompra;
    private BigDecimal precoVendaFisica;
    private Long quantidadeCompras;
    private BigDecimal totalPrecoCompra;
    private BigDecimal totalPrecoVenda;

    public ProdutoDTO(Produto produto) {
        this.id = produto.getId();
        this.descricao = produto.getDescricao();
        this.quantidadeEstoque = produto.getQuantidadeEstoque();
        this.precoCompra = produto.getPrecoCompra();
        this.precoVendaFisica = produto.getPrecoVendaFisica();
    }

    public ProdutoDTO(Produto produto, Long quantidadeCompras) {
        this.id = produto.getId();
        this.descricao = produto.getDescricao();
        this.quantidadeEstoque = produto.getQuantidadeEstoque();
        this.precoCompra = produto.getPrecoCompra();
        this.precoVendaFisica = produto.getPrecoVendaFisica();
        this.quantidadeCompras = quantidadeCompras;
    }

    public ProdutoDTO(Produto produto, Long quantidadeCompras, BigDecimal totalPrecoCompra, BigDecimal totalPrecoVenda) {
        this.id = produto.getId();
        this.descricao = produto.getDescricao();
        this.quantidadeEstoque = produto.getQuantidadeEstoque();
        this.precoCompra = produto.getPrecoCompra();
        this.precoVendaFisica = produto.getPrecoVendaFisica();
        this.quantidadeCompras = quantidadeCompras;
        this.totalPrecoCompra = totalPrecoCompra;
        this.totalPrecoVenda = totalPrecoVenda;
    }
}
