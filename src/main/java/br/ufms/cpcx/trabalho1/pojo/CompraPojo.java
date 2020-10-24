package br.ufms.cpcx.trabalho1.pojo;

import br.ufms.cpcx.trabalho1.entity.Pedido;
import br.ufms.cpcx.trabalho1.entity.PessoaFisica;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompraPojo {
    private Long pedidoId;
    private Long pessoaId;
    private LocalDate dataEntrega;
    private BigDecimal desconto;
    List<ItemPedidoPojo> itemPedidoDTOS;


    public Pedido gerarPedido() {
        Pedido pedido = new Pedido();
        pedido.setId(this.getPedidoId());
        PessoaFisica pessoa = new PessoaFisica();
        pessoa.setId(this.getPessoaId());
        pedido.setPessoa(pessoa);
        pedido.setPercentualDesconto(this.getDesconto());
        pedido.setDataCompra(LocalDate.now());
        pedido.setDataEntrega(this.getDataEntrega());

        return pedido;
    }

    public static CompraPojo gerarCompraPojo(Pedido pedido, List<ItemPedidoPojo> itemPedidoPojos) {
        return CompraPojo.builder()
                .pedidoId(pedido.getId())
                .pessoaId(pedido.getPessoa().getId())
                .dataEntrega(pedido.getDataEntrega())
                .desconto(pedido.getPercentualDesconto())
                .itemPedidoDTOS(itemPedidoPojos)
                .build();
    }
}
