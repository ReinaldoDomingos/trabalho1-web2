package br.ufms.cpcx.trabalho1.controller;

import br.ufms.cpcx.trabalho1.entity.Pessoa;
import br.ufms.cpcx.trabalho1.entity.PessoaFisica;
import br.ufms.cpcx.trabalho1.entity.PessoaJuridica;
import br.ufms.cpcx.trabalho1.exception.GenericException;
import br.ufms.cpcx.trabalho1.service.PessoaService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/pessoa")
public class PessoaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PessoaController.class);

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> buscarTodas() {
        return new ResponseEntity<>(pessoaService.buscarTodos(), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(pessoaService.buscarPorId(id), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/fisica")
    public ResponseEntity<?> salvar(@RequestBody PessoaFisica pessoaFisica) {
        try {
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
    public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
        pessoaService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @PutMapping("{id}")
    public ResponseEntity<?> alterar(@PathVariable("id") Long id, @RequestBody Pessoa body) {
        return new ResponseEntity<>(pessoaService.alterar(body), HttpStatus.ACCEPTED);
    }
}
