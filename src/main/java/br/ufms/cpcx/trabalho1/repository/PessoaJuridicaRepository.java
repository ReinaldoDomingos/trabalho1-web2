package br.ufms.cpcx.trabalho1.repository;

import br.ufms.cpcx.trabalho1.entity.PessoaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {
    Boolean existsByCnpj(String cnpj);
}
