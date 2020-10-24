package br.ufms.cpcx.trabalho1.service;

import br.ufms.cpcx.trabalho1.entity.Pedido;
import br.ufms.cpcx.trabalho1.enuns.EStatusPedido;
import br.ufms.cpcx.trabalho1.repository.ItemPedidoRepository;
import br.ufms.cpcx.trabalho1.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public List<Pedido> buscarTodos() {
        return pedidoRepository.findAll();
    }

    public Object buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id).get();
        pedido.setStatusNome(pedido.getStatus().getNome());
        return pedido;
    }

    public Object buscarProdutosPorPedido(Long id) {
        return itemPedidoRepository.buscarProdutosPorPedido(id);
    }

//    public Object buscarProdutosPorPedido(Long id) {
//        List<ItemPedido> itemPedidos = itemPedidoRepository.findByPedidoId(id);
//        List<Produto> produtos = new ArrayList<>();
//
//        for (ItemPedido itemPedido : itemPedidos) {
//            produtos.add(itemPedido.getProduto());
//        }
//
//        return produtos;
//    }

    public Pedido salvar(Pedido pedido) {
            return pedidoRepository.save(pedido);
    }

    public Pedido alterar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void deletar(Long id) {
        pedidoRepository.deleteById(id);
    }

    public Map buscarStatusPedido(Long id) {
        EStatusPedido statusPedido = pedidoRepository.buscarStatusPedido(id);
        Map<String,String> retorno = new HashMap<>();

        retorno.put("enum", statusPedido.toString());
        retorno.put("status", statusPedido.getNome());
        retorno.put("sigla", statusPedido.getSigla());

        return retorno;
    }
}
