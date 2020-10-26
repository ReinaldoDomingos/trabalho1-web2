package br.ufms.cpcx.trabalho1.service;

import br.ufms.cpcx.trabalho1.dto.ProdutoDTO;
import br.ufms.cpcx.trabalho1.entity.Produto;
import br.ufms.cpcx.trabalho1.entity.Usuario;
import br.ufms.cpcx.trabalho1.enuns.ETipoPessoa;
import br.ufms.cpcx.trabalho1.exception.DadosObrigatoriosException;
import br.ufms.cpcx.trabalho1.exception.RegistroInexistenteException;
import br.ufms.cpcx.trabalho1.exception.ValoresNegativoException;
import br.ufms.cpcx.trabalho1.repository.ProdutoRepository;
import br.ufms.cpcx.trabalho1.utils.ConstantesErros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Stream<ProdutoDTO> buscarTodos(Usuario usuario) {
        ETipoPessoa tipoPessoa = ETipoPessoa.valueOf(usuario.getPessoa().getTipo());

        return produtoRepository.buscarTodos(getIdade(usuario.getPessoa().getDataNascimento()))
                .stream().map(produto -> new ProdutoDTO(produto, tipoPessoa));
    }

    public Produto buscarPorId(Long id) {
        Optional<Produto> optional = produtoRepository.findById(id);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RegistroInexistenteException(ConstantesErros.Generic.REGISTRO_INEXISTENTE_EXCEPTION, null);
        }
    }

    public Produto salvar(Produto produto) {
        try {
            validarValores(produto);
            return produtoRepository.save(produto);
        } catch (DataIntegrityViolationException e) {
            throw new DadosObrigatoriosException(ConstantesErros.Generic.DADOS_OBRIGATORIOS_EXECEPTION, e);
        }
    }

    public Produto alterar(Produto produto) {
        boolean jaExiste = produtoRepository.existsById(produto.getId());
        if (jaExiste) {
            validarValores(produto);
            return produtoRepository.save(produto);
        } else {
            throw new RegistroInexistenteException(ConstantesErros.Generic.REGISTRO_INEXISTENTE_EXCEPTION, null);
        }
    }

    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    public Object buscarClientePorProduto(Long id) {
        return produtoRepository.buscarClientesPorProduto(id);
    }

    private boolean isNegativo(BigDecimal valor1) {
        return valor1.compareTo(new BigDecimal(0)) < 0;
    }

    private void validarValores(Produto produto) {
        if (produto.getIdadePermitida() < 0 || isNegativo(produto.getPrecoVendaFisica())
                || isNegativo(produto.getPrecoCompra()) || isNegativo(produto.getPrecoVendaJuridica())) {
            throw new ValoresNegativoException(ConstantesErros.Generic.DADOS_INVALIDOS_EXECEPTION, null);
        }
    }

    public Integer getIdade(final LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

}
