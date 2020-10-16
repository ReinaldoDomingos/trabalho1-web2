package br.ufms.cpcx.balan.repository;

import br.ufms.cpcx.balan.dto.ProdutoDTO;
import br.ufms.cpcx.balan.entity.Cliente;
import br.ufms.cpcx.balan.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    //HQL
    @Query("select new br.ufms.cpcx.balan.dto.ProdutoDTO(ip.produto, count(ip.produto.id), sum(ip.produto.precoCompra), sum(ip.produto.precoVendaFisica)) from ItemPedido ip " +
            " where ip.pedido in (select p.id from Pedido p where p.pessoa.id = ?1) group by ip.produto.id ")
    List<ProdutoDTO> findByPessoa(Long id);

    //HQL
    @Query(" select distinct ip.pedido.pessoa from ItemPedido ip where ip.produto.id = ?1")
    List<Cliente> buscarClientesPorProduto(Long idProduto);
}
