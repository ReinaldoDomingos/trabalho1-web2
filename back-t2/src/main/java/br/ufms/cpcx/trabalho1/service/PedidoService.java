package br.ufms.cpcx.trabalho1.service;

import br.ufms.cpcx.trabalho1.entity.Pedido;
import br.ufms.cpcx.trabalho1.enuns.EStatusPedido;
import br.ufms.cpcx.trabalho1.exception.DadosIncorretosException;
import br.ufms.cpcx.trabalho1.exception.DadosInvalidosException;
import br.ufms.cpcx.trabalho1.exception.RegistroInexistenteException;
import br.ufms.cpcx.trabalho1.repository.ItemPedidoRepository;
import br.ufms.cpcx.trabalho1.repository.PedidoRepository;
import br.ufms.cpcx.trabalho1.repository.PessoaRepository;
import br.ufms.cpcx.trabalho1.utils.ConstantesErros;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pedido> buscarTodos() {
        return pedidoRepository.findAll();
    }

    public Object buscarPorId(Long id) {
        try {

            return pedidoRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new RegistroInexistenteException(ConstantesErros.Generic.REGISTRO_INEXISTENTE_EXCEPTION, null);
        }
    }

    public Object buscarProdutosPorPedido(Long id) {
        return itemPedidoRepository.buscarProdutosPorPedido(id);
    }

    public Pedido salvar(Pedido pedido) {
        return salvarPedido(pedido);
    }

    public Pedido alterar(Pedido pedido) {
        return salvarPedido(pedido);
    }

    private Pedido salvarPedido(Pedido pedido) {
        try {
            validarPessoa(pedido);
            validarDesconto(pedido);
            validarDataCompra(pedido.getDataCompra());

            return pedidoRepository.save(pedido);
        } catch (ConstraintViolationException e) {
            throw new DadosInvalidosException(ConstantesErros.Generic.DADOS_INVALIDOS_EXECEPTION, e);
        }
    }

    public void deletar(Long id) {
        pedidoRepository.deleteById(id);
    }

    public Map<String, String> buscarStatusPedido(Long id) {
        EStatusPedido statusPedido = pedidoRepository.buscarStatusPedido(id);
        Map<String, String> retorno = new HashMap<>();

        retorno.put("enum", statusPedido.toString());
        retorno.put("status", statusPedido.getNome());
        retorno.put("sigla", statusPedido.getSigla());

        return retorno;
    }

    private void validarPessoa(Pedido pedido) {
        boolean jaExiste = pessoaRepository.existsById(pedido.getPessoaId());

        if (!jaExiste) {
            throw new DadosInvalidosException(ConstantesErros.Pessoa.PESSOA_INEXISTENTE_EXCEPTION, null);
        }
    }

    private void validarDesconto(Pedido pedido) {
        if (pedido.getPercentualDesconto().compareTo(new BigDecimal(100)) > 0) {
            throw new DadosIncorretosException(ConstantesErros.Pedido.VALOR_DESCONTO_INVALIDO_EXCEPTION, null);
        }
    }

    private void validarDataCompra(final LocalDate data) {
        if (Period.between(data, LocalDate.now()).getDays() < 0) {
            throw new DadosIncorretosException(ConstantesErros.Pedido.DATA_COMPRA_INVALIDA, null);
        }
    }
}
