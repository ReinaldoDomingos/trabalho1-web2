package br.ufms.cpcx.trabalho1.service;

import br.ufms.cpcx.trabalho1.entity.Pessoa;
import br.ufms.cpcx.trabalho1.entity.PessoaFisica;
import br.ufms.cpcx.trabalho1.entity.PessoaJuridica;
import br.ufms.cpcx.trabalho1.enuns.ETipoPessoa;
import br.ufms.cpcx.trabalho1.exception.DadosObrigatoriosException;
import br.ufms.cpcx.trabalho1.repository.PessoaRepository;
import br.ufms.cpcx.trabalho1.utils.ConstantesErros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> buscarTodos() {
        return pessoaRepository.findAll();
    }

    public Object buscarPorId(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id).get();
        return pessoa;
    }

    public Object salvar(PessoaFisica pessoaFisica) {
        try {
            pessoaFisica.setTipo(ETipoPessoa.FISICA.getValue());
            return pessoaRepository.save(pessoaFisica);
        } catch (DataIntegrityViolationException e) {
            throw new DadosObrigatoriosException(ConstantesErros.Generic.DADOS_OBRIGATORIOS_EXECEPTION, e);
        }
    }

    public Object salvar(PessoaJuridica pessoaJuridica) {
        try {
            pessoaJuridica.setTipo(ETipoPessoa.JURIDICA.getValue());
            return pessoaRepository.save(pessoaJuridica);
        } catch (DataIntegrityViolationException e) {
            throw new DadosObrigatoriosException(ConstantesErros.Generic.DADOS_OBRIGATORIOS_EXECEPTION, e);
        }
    }

    public Pessoa alterar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public void deletar(Long id) {
        pessoaRepository.deleteById(id);
    }

}
