package br.ufms.cpcx.trabalho1.controller;

import br.ufms.cpcx.trabalho1.entity.Produto;
import br.ufms.cpcx.trabalho1.entity.Usuario;
import br.ufms.cpcx.trabalho1.exception.GenericException;
import br.ufms.cpcx.trabalho1.service.ProdutoService;
import br.ufms.cpcx.trabalho1.service.UsuarioService;
import br.ufms.cpcx.trabalho1.utils.ConstantesErros;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/produto")
public class ProdutoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoController.class);

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> buscarTodos(@RequestHeader("usuario") String login, @RequestHeader("senha") String senha) {
        try {
            Usuario usuario = usuarioService.login(login, senha);
            return new ResponseEntity<>(produtoService.buscarTodos(usuario), HttpStatus.OK);
        } catch (GenericException e) {
            return new ResponseEntity<>(e.toJson(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error(ConstantesErros.Generic.BUSCAR_EXECEPTION, e);
            throw new GenericException(ConstantesErros.Generic.BUSCAR_EXECEPTION, e);
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id,
                                         @RequestHeader("usuario") String usuario,
                                         @RequestHeader("senha") String senha) {
        try {
            usuarioService.login(usuario, senha);
            return new ResponseEntity<>(produtoService.buscarPorId(id), HttpStatus.OK);
        } catch (GenericException e) {
            return new ResponseEntity<>(e.toJson(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error(ConstantesErros.Generic.BUSCAR_EXECEPTION, e);
            throw new GenericException(ConstantesErros.Generic.BUSCAR_EXECEPTION, e);
        }
    }

    @GetMapping("/{id}/cliente")
    @ResponseBody
    public ResponseEntity<?> buscarClientePorProduto(@PathVariable("id") Long id,
                                                     @RequestHeader("usuario") String usuario,
                                                     @RequestHeader("senha") String senha) {
        try {
            usuarioService.loginAdministrador(usuario, senha);
            return new ResponseEntity<>(produtoService.buscarClientePorProduto(id), HttpStatus.OK);
        } catch (GenericException e) {
            return new ResponseEntity<>(e.toJson(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error(ConstantesErros.Generic.ATUALIZAR_EXECEPTION, e);
            throw new GenericException(ConstantesErros.Generic.ATUALIZAR_EXECEPTION, e);
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> salvar(@RequestBody Produto body,
                                    @RequestHeader("usuario") String usuario,
                                    @RequestHeader("senha") String senha) {

        try {
            usuarioService.loginAdministrador(usuario, senha);
            return new ResponseEntity<>(produtoService.salvar(body), HttpStatus.CREATED);
        } catch (GenericException e) {
            return new ResponseEntity<>(e.toJson(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error(ConstantesErros.Generic.INSERIR_EXECEPTION, e);
            throw new GenericException(ConstantesErros.Generic.INSERIR_EXECEPTION, e);
        }
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity<?> deletar(@PathVariable("id") Long id,
                                     @RequestHeader("usuario") String usuario,
                                     @RequestHeader("senha") String senha) {
        try {
            usuarioService.loginAdministrador(usuario, senha);
            produtoService.deletar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (GenericException e) {
            return new ResponseEntity<>(e.toJson(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error(ConstantesErros.Generic.EXCLUIR_EXECEPTION, e);
            throw new GenericException(ConstantesErros.Generic.EXCLUIR_EXECEPTION, e);
        }
    }

    @PutMapping("{id}")
    @ResponseBody
    public ResponseEntity<?> alterar(@PathVariable("id") Long id, @RequestBody Produto usuario,
                                     @RequestHeader("usuario") String login,
                                     @RequestHeader("senha") String senha) {
        try {
            usuarioService.loginAdministrador(login, senha);
            usuario.setId(id);
            return new ResponseEntity<>(produtoService.alterar(usuario), HttpStatus.ACCEPTED);
        } catch (GenericException e) {
            return new ResponseEntity<>(e.toJson(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error(ConstantesErros.Generic.ATUALIZAR_EXECEPTION, e);
            throw new GenericException(ConstantesErros.Generic.ATUALIZAR_EXECEPTION, e);
        }
    }
}
