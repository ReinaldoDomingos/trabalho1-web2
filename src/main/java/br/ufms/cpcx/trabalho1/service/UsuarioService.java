package br.ufms.cpcx.trabalho1.service;

import br.ufms.cpcx.trabalho1.entity.Usuario;
import br.ufms.cpcx.trabalho1.exception.DadosIncorretosException;
import br.ufms.cpcx.trabalho1.exception.DadosInvalidosException;
import br.ufms.cpcx.trabalho1.exception.DadosObrigatoriosException;
import br.ufms.cpcx.trabalho1.exception.RegistroInexistenteException;
import br.ufms.cpcx.trabalho1.exception.UsuarioSemPermissaoException;
import br.ufms.cpcx.trabalho1.repository.PedidoRepository;
import br.ufms.cpcx.trabalho1.repository.ProdutoRepository;
import br.ufms.cpcx.trabalho1.repository.UsuarioRepository;
import br.ufms.cpcx.trabalho1.utils.ConstantesErros;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Usuario login(String login, String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByLoginAndSenha(login, senha);

        if (usuario.isPresent()) {
            return usuario.get();
        } else {
            throw new DadosIncorretosException(ConstantesErros.Usuario.DADOS_INCORRETOS_EXECEPTION, null);
        }
    }

    public Usuario loginAdministrador(String login, String senha) {
        Usuario usuario = login(login, senha);

        if (usuario.getIsAdministrador()) {
            return usuario;
        } else {
            throw new UsuarioSemPermissaoException(ConstantesErros.Usuario.SEM_PERMISSAO_EXCEPTION, null);
        }
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Object buscarPorId(Long id) {
        try {

            return usuarioRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new RegistroInexistenteException(ConstantesErros.Generic.REGISTRO_INEXISTENTE_EXCEPTION, null);
        }
    }

    public Object buscarPedidosPorUsuario(Long id) {
        return pedidoRepository.findByPessoaId(id);
    }

    public Object buscarProdutoPorUsuario(Long idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isPresent()) {
            return produtoRepository.findByPessoa(usuario.get().getPessoaId());
        } else {
            throw new RegistroInexistenteException(ConstantesErros.Generic.REGISTRO_INEXISTENTE_EXCEPTION, null);
        }
    }

    public Usuario salvar(Usuario usuario) {
        validarPessoa(usuario);
        validarLogin(usuario);

        try {
            if (isNull(usuario.getIsAdministrador())) {
                usuario.setIsAdministrador(false);
            }

            return usuarioRepository.save(usuario);
        } catch (ConstraintViolationException e) {
            throw new DadosObrigatoriosException(ConstantesErros.Generic.DADOS_OBRIGATORIOS_EXECEPTION, e);
        } catch (DataIntegrityViolationException e) {
            throw new DadosInvalidosException(ConstantesErros.Generic.DADOS_INVALIDOS_EXECEPTION, e);
        }
    }

    public Usuario alterar(Usuario usuario) {
        validarPessoaUsuarioExistente(usuario);
        validarLoginUsuarioExistente(usuario);

        return usuarioRepository.save(usuario);
    }

    private void validarLogin(Usuario usuario) {
        Boolean jaExiste = usuarioRepository.existsByLogin(usuario.getLogin());

        if (jaExiste) {
            throw new DadosInvalidosException(ConstantesErros.Usuario.LOGIN_EXISTENTE_EXCEPTION, null);
        }
    }

    private void validarLoginUsuarioExistente(Usuario usuario) {
        Boolean jaExiste = usuarioRepository.existsByLogin(usuario.getLogin());
        Optional<Usuario> existente = usuarioRepository.findById(usuario.getId());

        if (jaExiste && !existente.get().getLogin().equals(usuario.getLogin())) {
            throw new DadosInvalidosException(ConstantesErros.Usuario.LOGIN_EXISTENTE_EXCEPTION, null);
        }
    }

    private void validarPessoa(Usuario usuario) {
        Boolean jaExiste = usuarioRepository.existsByPessoaId(usuario.getPessoaId());

        if (jaExiste) {
            throw new DadosInvalidosException(ConstantesErros.Usuario.PESSOA_EXISTENTE_EXCEPTION, null);
        }
    }

    private void validarPessoaUsuarioExistente(Usuario usuario) {
        Boolean jaExiste = usuarioRepository.existsByPessoaId(usuario.getPessoaId());
        Optional<Usuario> existente = usuarioRepository.findById(usuario.getId());

        if (jaExiste && !existente.get().getPessoaId().equals(usuario.getPessoaId())) {
            throw new DadosInvalidosException(ConstantesErros.Usuario.PESSOA_EXISTENTE_EXCEPTION, null);
        }
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }

}
