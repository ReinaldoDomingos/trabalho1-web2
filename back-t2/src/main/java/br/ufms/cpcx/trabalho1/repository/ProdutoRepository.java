package br.ufms.cpcx.trabalho1.repository;

import br.ufms.cpcx.trabalho1.entity.Produto;
import br.ufms.cpcx.trabalho1.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    //HQL
    @Query("select ip.produto from ItemPedido ip " +
            " where ip.pedido in (select p.id from Pedido p where p.pessoa.id = ?1)" +
            " group by ip.produto.id ")
    List<Produto> findByPessoa(Long id);

    //HQL
    @Query(" select distinct ip.pedido.pessoa from ItemPedido ip where ip.produto.id = ?1")
    List<Usuario> buscarClientesPorProduto(Long idProduto);

    @Query("SELECT pr FROM Produto pr WHERE pr.idadePermitida <= :idadeUsuario")
    List<Produto> buscarTodos(@Param("idadeUsuario") Integer idadeUsuario);
}
