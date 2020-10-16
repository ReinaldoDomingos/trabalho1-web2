package br.ufms.cpcx.balan.repository;

import br.ufms.cpcx.balan.entity.Pedido;
import br.ufms.cpcx.balan.enuns.EStatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    //HQL
    @Query("select p from Pedido p where p.pessoa.id = ?1")
    List<Pedido> findByPessoaId(Long idCliente);

    //HQL
    @Query("select p.status from Pedido p where p.id = ?1")
    EStatusPedido buscarStatusPedido(Long id);

    Boolean existsByPessoaId(Long idPessoa);
}
