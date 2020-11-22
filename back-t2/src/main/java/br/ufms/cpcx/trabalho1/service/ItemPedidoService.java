package br.ufms.cpcx.trabalho1.service;

import br.ufms.cpcx.trabalho1.entity.ItemPedido;
import br.ufms.cpcx.trabalho1.exception.DadosObrigatoriosException;
import br.ufms.cpcx.trabalho1.exception.ProdutoExistenteException;
import br.ufms.cpcx.trabalho1.exception.RegistroInexistenteException;
import br.ufms.cpcx.trabalho1.repository.ItemPedidoRepository;
import br.ufms.cpcx.trabalho1.repository.PedidoRepository;
import br.ufms.cpcx.trabalho1.repository.ProdutoRepository;
import br.ufms.cpcx.trabalho1.utils.ConstantesErros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<ItemPedido> buscarTodos() {
        return itemPedidoRepository.findAll();
    }

    public Object buscarPorId(Long id) {
        Optional<ItemPedido> optional = itemPedidoRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RegistroInexistenteException(ConstantesErros.Generic.REGISTRO_INEXISTENTE_EXCEPTION, null);
        }
    }

    public ItemPedido salvar(ItemPedido itemPedido) {
        try {
            validarProduto(itemPedido);
            return itemPedidoRepository.save(itemPedido);
        } catch (DataIntegrityViolationException e) {
            throw new DadosObrigatoriosException(ConstantesErros.Generic.DADOS_INVALIDOS_EXECEPTION, e);
        }
    }

    public ItemPedido alterar(ItemPedido itemPedido) {

        try {
            validarProdutoItemPedidoExistente(itemPedido);
            return itemPedidoRepository.save(itemPedido);
        } catch (EntityNotFoundException e) {
            throw new DadosObrigatoriosException(ConstantesErros.Generic.DADOS_INVALIDOS_EXECEPTION, e);
        }
    }

    public void deletar(Long id) {
        itemPedidoRepository.deleteById(id);
    }

    private void validarProduto(ItemPedido itemPedido) {
        Long idPedido = itemPedido.getPedido().getId();
        Long idProduto = itemPedido.getProduto().getId();

        validarExistencia(idProduto, idPedido);

        if (itemPedidoRepository.existsByPedidoIdAndProdutoId(idPedido, idProduto)) {
            throw new ProdutoExistenteException(ConstantesErros.ItemPedido.PRODUTO_EXISTENTE_EXCEPTION, null);
        }
    }

    private void validarProdutoItemPedidoExistente(ItemPedido itemPedido) {
        Long idPedido = itemPedido.getPedido().getId();
        Long idProduto = itemPedido.getProduto().getId();

        validarExistencia(idProduto, idPedido);

        Optional<ItemPedido> optional = itemPedidoRepository.findById(itemPedido.getId());
        Boolean jaExiste = itemPedidoRepository.existsByPedidoIdAndProdutoId(idPedido, idProduto);
        boolean ehEsse = optional.get().getProduto().getId().equals(idProduto) && optional.get().getPedido().getId().equals(idPedido);

        if (jaExiste && !ehEsse) {
            throw new ProdutoExistenteException(ConstantesErros.ItemPedido.PRODUTO_EXISTENTE_EXCEPTION, null);
        }
    }

    private void validarExistencia(Long idProduto, Long idPedido) {
        boolean existeProduto = produtoRepository.existsById(idProduto);
        boolean existePedido = pedidoRepository.existsById(idPedido);

        if (!existePedido || !existeProduto) {
            throw new RegistroInexistenteException(ConstantesErros.Generic.DADOS_INVALIDOS_EXECEPTION, null);
        }
    }
}
