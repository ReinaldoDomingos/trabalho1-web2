package br.ufms.cpcx.balan.repository;

import br.ufms.cpcx.balan.dto.ProdutoDTO;
import br.ufms.cpcx.balan.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

//    //Consulta por nome do método
//    List<Cliente> findByNameContains(String name);
//
//    List<Cliente> findByIdIn(List<Long> ids);
//
//    boolean existsByCpf(String cpf);
//
//    boolean existsByCpfAndIdade(String cpf, Long id);
//
//    boolean existsByCpfOrName(String cpf, String name);
//
//    //Consulta com HQL
//    @Query("SELECT c FROM Cliente c where c.name = :name and c.idade = :idade")
//    List<Cliente> consultaHql(@Param("name") String name, @Param("idade") Long idade);
//
//    //Consulta com SQL
//    @Query(nativeQuery = true, value = "SELECT CLI_NAME FROM TB_CLIENTE where CLI_NAME LIKE :name")
//    List<Cliente> consultaSql(@Param("name") String name);
}
