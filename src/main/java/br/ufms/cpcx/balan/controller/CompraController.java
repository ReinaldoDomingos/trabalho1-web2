package br.ufms.cpcx.balan.controller;

import br.ufms.cpcx.balan.pojo.CompraPojo;
import br.ufms.cpcx.balan.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/compra")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> salvar(@RequestBody CompraPojo body) {
        return new ResponseEntity(compraService.executar(body), HttpStatus.CREATED);
    }
}
