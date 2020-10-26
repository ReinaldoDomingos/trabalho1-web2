package br.ufms.cpcx.trabalho1.dto;

import br.ufms.cpcx.trabalho1.entity.Produto;
import br.ufms.cpcx.trabalho1.enuns.ETipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;
    private BigDecimal preco;
    private String descricao;
    private Long quantidadeEstoque;
    private Integer idadePermitida;

    public ProdutoDTO(Produto produto, ETipoPessoa tipoPessoa) {
        this.id = produto.getId();
        this.descricao = produto.getDescricao();
        this.quantidadeEstoque = produto.getQuantidadeEstoque();
        this.idadePermitida = produto.getIdadePermitida();

        if (tipoPessoa.equals(ETipoPessoa.FISICA)) {
            this.preco = produto.getPrecoVendaFisica();
        } else if (tipoPessoa.equals(ETipoPessoa.JURIDICA)) {
            this.preco = produto.getPrecoVendaJuridica();
        }
    }
}
