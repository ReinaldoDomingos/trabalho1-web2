package br.ufms.cpcx.trabalho1.controller;

import br.ufms.cpcx.trabalho1.entity.Cliente;
import br.ufms.cpcx.trabalho1.service.ClienteService;
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
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> buscarTodo() {
        return new ResponseEntity(clienteService.buscarTodos(), HttpStatus.OK);
    }

    @GetMapping("/optional")
    @ResponseBody
    public ResponseEntity<?> optionalTest() {
        return new ResponseEntity(clienteService.optionalTest(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {
        return new ResponseEntity(clienteService.buscarPorId(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/pedido")
    @ResponseBody
    public ResponseEntity<?> buscarPedidosPorCliente(@PathVariable("id") Long id) {
        return new ResponseEntity(clienteService.buscarPedidosPorCliente(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/produto")
    @ResponseBody
    public ResponseEntity<?> buscarProdutoPorCliente(@PathVariable("id") Long id) {
        return new ResponseEntity(clienteService.buscarProdutoPorCliente(id), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> salvar(@RequestBody Cliente body) {
        return new ResponseEntity(clienteService.salvar(body), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
        clienteService.deletar(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    @ResponseBody
    public ResponseEntity<?> alterar(@PathVariable("id") Long id,
                                     @RequestBody Cliente body) {

        return new ResponseEntity(clienteService.alterar(body), HttpStatus.ACCEPTED);
    }
}
