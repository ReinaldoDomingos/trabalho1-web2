package br.ufms.cpcx.trabalho1.repository;

import br.ufms.cpcx.trabalho1.entity.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {
    Boolean existsByCpf(String cpf);
}
