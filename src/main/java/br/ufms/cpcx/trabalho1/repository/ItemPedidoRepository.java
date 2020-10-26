package br.ufms.cpcx.trabalho1.repository;

import br.ufms.cpcx.trabalho1.entity.ItemPedido;
import br.ufms.cpcx.trabalho1.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    @Query("select ip.produto from ItemPedido ip where ip.pedido.id = ?1")
    List<Produto> buscarProdutosPorPedido(Long idPedido);

    Boolean existsByPedidoIdAndProdutoId(Long idPedido, Long idProduto);
}
