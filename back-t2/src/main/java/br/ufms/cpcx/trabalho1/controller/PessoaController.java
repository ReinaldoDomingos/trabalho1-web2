package br.ufms.cpcx.trabalho1.controller;

import br.ufms.cpcx.trabalho1.exception.GenericException;
import br.ufms.cpcx.trabalho1.service.PessoaService;
import br.ufms.cpcx.trabalho1.service.UsuarioService;
import br.ufms.cpcx.trabalho1.entity.PessoaFisica;
import br.ufms.cpcx.trabalho1.entity.PessoaJuridica;
import br.ufms.cpcx.trabalho1.enuns.ETipoPessoa;
import br.ufms.cpcx.trabalho1.enuns.EnumSituacao;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("/api/pessoa")
public class PessoaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PessoaController.class);

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private UsuarioService usuarioService;

    private ResponseEntity<?> buscarTodos(String usuario, String senha, ETipoPessoa tipoPessoa) {
        try {
            usuarioService.login(usuario, senha);
            return new ResponseEntity<>(pessoaService.buscarTodosPorTipo(tipoPessoa), HttpStatus.OK);
        } catch (GenericException e) {
            return new ResponseEntity<>(e.toJson(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error(ConstantesErros.Generic.BUSCAR_EXECEPTION, e);
            throw new GenericException(ConstantesErros.Generic.BUSCAR_EXECEPTION, e);
        }
    }

    @ResponseBody
    @GetMapping
    public ResponseEntity<?> buscarTodosPorFiltro(@RequestHeader("usuario") String usuario,
                                                  @RequestHeader("senha") String senha,
                                                  @RequestParam(value = "tipo", required = false) ETipoPessoa tipo,
                                                  @RequestParam(value = "situacao", required = false) EnumSituacao situacao,
                                                  @RequestParam(value = "idResponsavel", required = false) Long idResponsavel,
                                                  @RequestParam(value = "nomeResponsavel", required = false) String nomeResponsavel) {
        try {
            usuarioService.login(usuario, senha);
            return new ResponseEntity<>(pessoaService.buscarTodosPorFiltro(tipo, situacao, idResponsavel, nomeResponsavel), HttpStatus.OK);
        } catch (GenericException e) {
            return new ResponseEntity<>(e.toJson(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error(ConstantesErros.Generic.BUSCAR_EXECEPTION, e);
            throw new GenericException(ConstantesErros.Generic.BUSCAR_EXECEPTION, e);
        }
    }

    @ResponseBody
    @GetMapping("/fisica")
    public ResponseEntity<?> buscarTodosPessoaFisica(@RequestHeader("usuario") String usuario,
                                                     @RequestHeader("senha") String senha) {
        return buscarTodos(usuario, senha, ETipoPessoa.FISICA);
    }

    @ResponseBody
    @GetMapping("/juridica")
    public ResponseEntity<?> buscarTodosPessoaJuridica(@RequestHeader("usuario") String usuario,
                                                       @RequestHeader("senha") String senha) {
        return buscarTodos(usuario, senha, ETipoPessoa.JURIDICA);
    }

    @ResponseBody
    @GetMapping("/fisica/{id}")
    public ResponseEntity<?> buscarPorIdPessoaFisica(@PathVariable("id") Long id,
                                                     @RequestHeader("usuario") String usuario,
                                                     @RequestHeader("senha") String senha) {
        try {
            Optional<PessoaFisica> optional = pessoaService.buscarPorIdPessoaFisica(id);

            if (optional.isPresent()) {
                usuarioService.login(usuario, senha);
                return new ResponseEntity<>(optional.get(), HttpStatus.OK);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (GenericException e) {
            return new ResponseEntity<>(e.toJson(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error(ConstantesErros.Generic.BUSCAR_EXECEPTION, e);
            throw new GenericException(ConstantesErros.Generic.BUSCAR_EXECEPTION, e);
        }
    }

    @ResponseBody
    @GetMapping("/juridica/{id}")
    public ResponseEntity<?> buscarPorIdPessoaJuridica(@PathVariable("id") Long id,
                                                       @RequestHeader("usuario") String usuario,
                                                       @RequestHeader("senha") String senha) {
        try {
            usuarioService.login(usuario, senha);
            Optional<PessoaJuridica> optional = pessoaService.buscarPorIdPessoaJuridica(id);

            if (optional.isPresent()) {
                return new ResponseEntity<>(optional.get(), HttpStatus.OK);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (GenericException e) {
            return new ResponseEntity<>(e.toJson(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error(ConstantesErros.Generic.BUSCAR_EXECEPTION, e);
            throw new GenericException(ConstantesErros.Generic.BUSCAR_EXECEPTION, e);
        }
    }

    @ResponseBody
    @PostMapping("/fisica")
    public ResponseEntity<?> salvar(@RequestBody PessoaFisica pessoaFisica) {
        try {
            pessoaFisica.setId(null);
            return new ResponseEntity<>(pessoaService.salvar(pessoaFisica), HttpStatus.CREATED);
        } catch (GenericException e) {
            return new ResponseEntity<>(e.toJson(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error(ConstantesErros.Generic.INSERIR_EXECEPTION, e);
            throw new GenericException(ConstantesErros.Generic.INSERIR_EXECEPTION, e);
        }
    }

    @ResponseBody
    @PostMapping("/juridica")
    public ResponseEntity<?> salvar(@RequestBody PessoaJuridica pessoaJuridica) {
        try {
            pessoaJuridica.setId(null);
            return new ResponseEntity<>(pessoaService.salvar(pessoaJuridica), HttpStatus.CREATED);
        } catch (GenericException e) {
            return new ResponseEntity<>(e.toJson(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error(ConstantesErros.Generic.INSERIR_EXECEPTION, e);
            throw new GenericException(ConstantesErros.Generic.INSERIR_EXECEPTION, e);
        }
    }

    @ResponseBody
    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") Long id,
                                     @RequestHeader("usuario") String usuario,
                                     @RequestHeader("senha") String senha) {
        try {
            usuarioService.loginAdministrador(usuario, senha);
            pessoaService.deletar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (GenericException e) {
            return new ResponseEntity<>(e.toJson(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error(ConstantesErros.Generic.EXCLUIR_EXECEPTION, e);
            throw new GenericException(ConstantesErros.Generic.EXCLUIR_EXECEPTION, e);
        }
    }

    @ResponseBody
    @PutMapping("/fisica/{id}")
    public ResponseEntity<?> alterar(@PathVariable("id") Long id, @RequestBody PessoaFisica usuario,
                                     @RequestHeader("usuario") String login,
                                     @RequestHeader("senha") String senha) {
        try {
            usuarioService.loginAdministrador(login, senha);
            usuario.setId(id);
            return new ResponseEntity<>(pessoaService.alterar(usuario), HttpStatus.ACCEPTED);
        } catch (GenericException e) {
            return new ResponseEntity<>(e.toJson(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error(ConstantesErros.Generic.ATUALIZAR_EXECEPTION, e);
            throw new GenericException(ConstantesErros.Generic.ATUALIZAR_EXECEPTION, e);
        }
    }

    @ResponseBody
    @PutMapping("/juridica/{id}")
    public ResponseEntity<?> alterar(@PathVariable("id") Long id, @RequestBody PessoaJuridica body,
                                     @RequestHeader("usuario") String usuario,
                                     @RequestHeader("senha") String senha) {
        try {
            usuarioService.loginAdministrador(usuario, senha);
            return new ResponseEntity<>(pessoaService.alterar(body), HttpStatus.ACCEPTED);
        } catch (GenericException e) {
            return new ResponseEntity<>(e.toJson(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error(ConstantesErros.Generic.ATUALIZAR_EXECEPTION, e);
            throw new GenericException(ConstantesErros.Generic.ATUALIZAR_EXECEPTION, e);
        }
    }
}
