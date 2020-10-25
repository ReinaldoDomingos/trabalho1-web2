package br.ufms.cpcx.trabalho1.utils;

public class ConstantesErros {
    public static class Generic {
        public final static String INSERIR_EXECEPTION = "Não foi possivel salvar o registro";
        public final static String ATUALIZAR_EXECEPTION = "Não foi atualizar salvar o registro";
        public final static String REGISTRO_INEXISTENTE_EXCEPTION = "Registro inexistente!";
        public final static String DADOS_OBRIGATORIOS_EXECEPTION = "Dados obrigatórios não preenchidos!";
        public final static String DADOS_INVALIDOS_EXECEPTION = "Dados inválidos!";
    }

    public static class Pessoa {
        public static final String CNPJ_EXISTENTE_EXCEPTION = "CNPJ já cadastrado!";
        public static final String CPF_EXISTENTE_EXCEPTION = "CPF já cadastrado!";
        public static final String RESPONSAVEL_INEXISTENTE_EXCEPTION = "É obrigatório o cadastro de um responsável a menores de 18 anos";
    }
}
