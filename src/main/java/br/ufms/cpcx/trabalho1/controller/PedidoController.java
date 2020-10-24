package br.ufms.cpcx.trabalho1.controller;

import br.ufms.cpcx.trabalho1.entity.Pedido;
import br.ufms.cpcx.trabalho1.service.PedidoService;
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
@RequestMapping("/api/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> buscarTodos() {
        return new ResponseEntity(pedidoService.buscarTodos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {
        return new ResponseEntity(pedidoService.buscarPorId(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/status")
    @ResponseBody
    public ResponseEntity<?> buscarStatusPedido(@PathVariable("id") Long id) {
        return new ResponseEntity(pedidoService.buscarStatusPedido(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/produto")
    @ResponseBody
    public ResponseEntity<?> buscarProdutosPorPedido(@PathVariable("id") Long id) {
        return new ResponseEntity(pedidoService.buscarProdutosPorPedido(id), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> salvar(@RequestBody Pedido body) {
        return new ResponseEntity(pedidoService.salvar(body), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
        pedidoService.deletar(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    @ResponseBody
    public ResponseEntity<?> alterar(@PathVariable("id") Long id,
                                     @RequestBody Pedido body) {

        return new ResponseEntity(pedidoService.alterar(body), HttpStatus.ACCEPTED);
    }
}
