package br.ufms.cpcx.trabalho1.service;

import br.ufms.cpcx.trabalho1.pojo.CompraPojo;
import br.ufms.cpcx.trabalho1.pojo.ItemPedidoPojo;
import br.ufms.cpcx.trabalho1.entity.ItemPedido;
import br.ufms.cpcx.trabalho1.entity.Pedido;
import br.ufms.cpcx.trabalho1.repository.ItemPedidoRepository;
import br.ufms.cpcx.trabalho1.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CompraService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public CompraPojo executar(CompraPojo compraPojo) {
        Pedido pedidoSalvo = pedidoRepository.save(compraPojo.gerarPedido());

        ArrayList<ItemPedidoPojo> resutadoIpPojo = new ArrayList<>();

        for (ItemPedidoPojo ipPojo : compraPojo.getItemPedidoDTOS()) {
            ItemPedido itemPedidoSalvo = itemPedidoRepository.save(ipPojo.gerarItemPedido(pedidoSalvo));
            resutadoIpPojo.add(ItemPedidoPojo.geraItemPedidoPojo(itemPedidoSalvo));
        }

        return CompraPojo.gerarCompraPojo(pedidoSalvo, resutadoIpPojo);
    }
}
