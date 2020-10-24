package br.ufms.cpcx.trabalho1.repository;

import br.ufms.cpcx.trabalho1.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
