package br.ufms.cpcx.balan.service;

import br.ufms.cpcx.balan.entity.Cliente;
import br.ufms.cpcx.balan.repository.ClienteRepository;
import br.ufms.cpcx.balan.repository.PedidoRepository;
import br.ufms.cpcx.balan.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    //Exemplo Optional
    public Object optionalTest() {
        Optional<Cliente> optional = clienteRepository.findById(5L);

        optional.orElseThrow(RuntimeException::new);

        optional.ifPresent(cliente1 -> {
            System.out.println(cliente1.getName());
        });

        if (optional.filter(cliente -> cliente.getName().equals("Pedro")).isPresent()) {

        } else {

        };

        Cliente cliente = optional.get();

        return optional;
    }

    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    public Object buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id).get();
        cliente.setRealizouAlgumPedido(pedidoRepository.existsByPessoaId(id));
        return cliente;
    }

    public Object buscarPedidosPorCliente(Long id) {
        return pedidoRepository.findByPessoaId(id);
    }

    public Object buscarProdutoPorCliente(Long id) {
        return produtoRepository.findByPessoa(id);
    }

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente alterar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

}
