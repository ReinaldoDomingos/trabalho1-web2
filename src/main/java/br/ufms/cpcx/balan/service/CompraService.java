package br.ufms.cpcx.balan.service;

import br.ufms.cpcx.balan.pojo.CompraPojo;
import br.ufms.cpcx.balan.pojo.ItemPedidoPojo;
import br.ufms.cpcx.balan.entity.ItemPedido;
import br.ufms.cpcx.balan.entity.Pedido;
import br.ufms.cpcx.balan.repository.ItemPedidoRepository;
import br.ufms.cpcx.balan.repository.PedidoRepository;
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

        ArrayList<ItemPedidoPojo> resutadoIpPojo = new ArrayList();

        for (ItemPedidoPojo ipPojo : compraPojo.getItemPedidoDTOS()) {
            ItemPedido itemPedidoSalvo = itemPedidoRepository.save(ipPojo.gerarItemPedido(pedidoSalvo));
            resutadoIpPojo.add(ItemPedidoPojo.geraItemPedidoPojo(itemPedidoSalvo));
        }

        return CompraPojo.gerarCompraPojo(pedidoSalvo, resutadoIpPojo);
    }
}
