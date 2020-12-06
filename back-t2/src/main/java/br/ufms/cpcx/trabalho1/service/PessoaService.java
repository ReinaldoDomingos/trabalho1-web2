package br.ufms.cpcx.trabalho1.service;

import br.ufms.cpcx.trabalho1.entity.Pessoa;
import br.ufms.cpcx.trabalho1.entity.PessoaFisica;
import br.ufms.cpcx.trabalho1.entity.PessoaJuridica;
import br.ufms.cpcx.trabalho1.enuns.ETipoPessoa;
import br.ufms.cpcx.trabalho1.enuns.EnumSituacao;
import br.ufms.cpcx.trabalho1.exception.CNPJExistenteException;
import br.ufms.cpcx.trabalho1.exception.CPFExistenteException;
import br.ufms.cpcx.trabalho1.exception.DadosIncorretosException;
import br.ufms.cpcx.trabalho1.exception.DadosInvalidosException;
import br.ufms.cpcx.trabalho1.exception.DadosObrigatoriosException;
import br.ufms.cpcx.trabalho1.exception.RegistroInexistenteException;
import br.ufms.cpcx.trabalho1.exception.ResponsavelCadastradoException;
import br.ufms.cpcx.trabalho1.repository.PessoaFisicaRepository;
import br.ufms.cpcx.trabalho1.repository.PessoaJuridicaRepository;
import br.ufms.cpcx.trabalho1.repository.PessoaRepository;
import br.ufms.cpcx.trabalho1.utils.ConstantesErros;
import br.ufms.cpcx.trabalho1.utils.ValidaCNPJ;
import br.ufms.cpcx.trabalho1.utils.ValidaCPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;
    @Autowired
    private PessoaJuridicaRepository pessoaJuridicaRepository;

    public List<? extends Pessoa> buscarTodosPorFiltro(ETipoPessoa tipo, EnumSituacao situacao, Long idResponsavel, String nomeResponsavel) {
        List<? extends Pessoa> retorno = null;
        List pessoasFisica = pessoaFisicaRepository.findAll();
        List pessoaJuridicas = pessoaJuridicaRepository.findAll();

        if (ETipoPessoa.FISICA.equals(tipo)) {
            retorno = pessoasFisica;
        } else if (ETipoPessoa.JURIDICA.equals(tipo)) {
            retorno = pessoaJuridicas;
        } else {
            pessoaJuridicas.addAll(pessoasFisica);
            retorno = pessoaJuridicas;
        }

        if (isNull(idResponsavel) && isNull(tipo) && isNull(situacao) && isNull(nomeResponsavel)) {
            return retorno.stream()
                    .sorted(Comparator.comparing(Pessoa::getId))
                    .collect(Collectors.toList());
        }
        return retorno.stream()
                .filter(p -> isNull(situacao) || p.getSituacao().equals(situacao))
                .filter(p -> isNull(idResponsavel) || (nonNull(p.getResponsavelId()) && p.getResponsavelId().equals(idResponsavel)))
                .filter(p -> isNull(nomeResponsavel) || (nonNull(p.getResponsavelId()) && p.getResponsavel().getNome().toLowerCase().contains(nomeResponsavel.toLowerCase())))
                .collect(Collectors.toList());

    }

    public List<? extends Pessoa> buscarTodosPorTipo(ETipoPessoa tipo) {
        if (ETipoPessoa.FISICA.equals(tipo)) {
            return pessoaFisicaRepository.findAll();
        } else if (ETipoPessoa.JURIDICA.equals(tipo)) {
            return pessoaJuridicaRepository.findAll();
        }
        throw new DadosIncorretosException(ConstantesErros.Generic.DADOS_INCORRETOS_EXECEPTION, null);
    }

    public Optional<Pessoa> buscarPorIdPessoa(Long id) {
        return pessoaRepository.findById(id);
    }

    public Optional<PessoaFisica> buscarPorIdPessoaFisica(Long id) {
        return pessoaFisicaRepository.findById(id);
    }

    public Optional<PessoaJuridica> buscarPorIdPessoaJuridica(Long id) {
        return pessoaJuridicaRepository.findById(id);
    }

    public PessoaFisica salvar(PessoaFisica pessoaFisica) {
        validarCPF(pessoaFisica);

        boolean jaExiste = pessoaFisicaRepository.existsByCpf(pessoaFisica.getCpf());
        if (jaExiste) {
            throw new CPFExistenteException(ConstantesErros.Pessoa.CPF_EXISTENTE_EXCEPTION, null);
        }
        validarIdadade(pessoaFisica);

        try {
            pessoaFisica.setTipo(ETipoPessoa.FISICA.getValue());
            return pessoaRepository.save(pessoaFisica);
        } catch (DataIntegrityViolationException e) {
            throw new DadosObrigatoriosException(ConstantesErros.Generic.DADOS_OBRIGATORIOS_EXECEPTION, e);
        }
    }

    public PessoaJuridica salvar(PessoaJuridica pessoaJuridica) {
        validarCNPJ(pessoaJuridica);

        Boolean jaExiste = pessoaJuridicaRepository.existsByCnpj(pessoaJuridica.getCnpj());

        if (jaExiste) {
            throw new CNPJExistenteException(ConstantesErros.Pessoa.CNPJ_EXISTENTE_EXCEPTION, null);
        }

        validarIdadade(pessoaJuridica);

        try {
            pessoaJuridica.setTipo(ETipoPessoa.JURIDICA.getValue());
            return pessoaRepository.save(pessoaJuridica);
        } catch (DataIntegrityViolationException e) {
            throw new DadosObrigatoriosException(ConstantesErros.Generic.DADOS_OBRIGATORIOS_EXECEPTION, e);
        }
    }

    public PessoaFisica alterar(PessoaFisica pessoaFisica) {
        boolean jaExiste = pessoaFisicaRepository.existsById(pessoaFisica.getId());

        if (!jaExiste) {
            throw new RegistroInexistenteException(ConstantesErros.Generic.REGISTRO_INEXISTENTE_EXCEPTION, null);
        }

        validarIdadade(pessoaFisica);

        try {
            Optional<PessoaFisica> existente = pessoaFisicaRepository.findById(pessoaFisica.getId());
            if (existente.isPresent()) {
                pessoaFisica.setCpf(existente.get().getCpf());
                return pessoaFisicaRepository.save(pessoaFisica);
            } else {
                throw new DadosObrigatoriosException(ConstantesErros.Generic.REGISTRO_INEXISTENTE_EXCEPTION, null);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DadosObrigatoriosException(ConstantesErros.Generic.DADOS_OBRIGATORIOS_EXECEPTION, e);
        }
    }

    public Pessoa alterar(PessoaJuridica pessoaJuridica) {
        boolean jaExiste = pessoaJuridicaRepository.existsById(pessoaJuridica.getId());

        if (!jaExiste) {
            throw new RegistroInexistenteException(ConstantesErros.Generic.REGISTRO_INEXISTENTE_EXCEPTION, null);
        }

        validarIdadade(pessoaJuridica);

        try {
            Optional<PessoaJuridica> existente = pessoaJuridicaRepository.findById(pessoaJuridica.getId());
            if (existente.isPresent()) {
                pessoaJuridica.setCnpj(existente.get().getCnpj());
                return pessoaJuridicaRepository.save(pessoaJuridica);
            } else {
                throw new DadosObrigatoriosException(ConstantesErros.Generic.REGISTRO_INEXISTENTE_EXCEPTION, null);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DadosObrigatoriosException(ConstantesErros.Generic.DADOS_OBRIGATORIOS_EXECEPTION, e);
        }
    }

    public void deletar(Long id) {
        pessoaRepository.deleteById(id);
    }

    public static int getIdade(final LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    private void validarIdadade(Pessoa pessoapessoa) {
        int idade = getIdade(pessoapessoa.getDataNascimento());

        if (idade < 18 && isNull(pessoapessoa.getResponsavelId())) {
            throw new ResponsavelCadastradoException(ConstantesErros.Pessoa.RESPONSAVEL_INEXISTENTE_EXCEPTION, null);
        }
    }

    private void validarCPF(PessoaFisica pessoaFisica) {
        String cpf = pessoaFisica.getCpf();

        if (isNull(cpf)) {
            throw new DadosObrigatoriosException(ConstantesErros.Generic.DADOS_OBRIGATORIOS_EXECEPTION, null);
        }

        if (!ValidaCPF.isCPF(pessoaFisica.getCpf())) {
            throw new DadosInvalidosException(ConstantesErros.Generic.DADOS_INVALIDOS_EXECEPTION, null);
        }
    }

    private void validarCNPJ(PessoaJuridica pessoaJuridica) {
        String cnpj = pessoaJuridica.getCnpj();

        if (isNull(cnpj)) {
            throw new DadosObrigatoriosException(ConstantesErros.Generic.DADOS_OBRIGATORIOS_EXECEPTION, null);
        }

        if (!ValidaCNPJ.isCNPJ(cnpj)) {
            throw new DadosInvalidosException(ConstantesErros.Generic.DADOS_INVALIDOS_EXECEPTION, null);
        }
    }
}
