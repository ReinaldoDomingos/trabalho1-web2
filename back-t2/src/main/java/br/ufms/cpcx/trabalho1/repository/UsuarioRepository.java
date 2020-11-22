package br.ufms.cpcx.trabalho1.repository;

import br.ufms.cpcx.trabalho1.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Boolean existsByLogin(String login);

    Boolean existsByPessoaId(Long id);

    Optional<Usuario> findByLoginAndSenha(String login, String senha);
}
