package br.ufms.cpcx.trabalho1.pojo;

import br.ufms.cpcx.trabalho1.entity.ItemPedido;
import br.ufms.cpcx.trabalho1.entity.Pedido;
import br.ufms.cpcx.trabalho1.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoPojo {
    private Long itemPedidoId;
    private Long produtoId;
    private Long quantidade;

    public ItemPedido gerarItemPedido (Pedido pedido){
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setId(this.getItemPedidoId());
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(new Produto(this.getProdutoId()));
        itemPedido.setQuantidade(this.getQuantidade());
        return itemPedido;
    }

    public static ItemPedidoPojo geraItemPedidoPojo (ItemPedido itemPedido){
        ItemPedidoPojo itemPedidoPojo = new ItemPedidoPojo();
        itemPedidoPojo.setItemPedidoId(itemPedido.getId());
        itemPedidoPojo.setProdutoId(itemPedido.getProduto().getId());
        itemPedidoPojo.setQuantidade(itemPedido.getQuantidade());
        return itemPedidoPojo;
    }
}
