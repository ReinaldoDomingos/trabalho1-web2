package br.ufms.cpcx.trabalho1.utils;

public class ConstantesErros {
    public static class Generic {
        public final static String BUSCAR_EXECEPTION = "Não foi possivel buscar o(s) registro(s)";
        public final static String INSERIR_EXECEPTION = "Não foi possivel salvar o registro";
        public final static String ATUALIZAR_EXECEPTION = "Não foi atualizar salvar o registro";
        public final static String REGISTRO_INEXISTENTE_EXCEPTION = "Registro inexistente!";
        public final static String DADOS_OBRIGATORIOS_EXECEPTION = "Dados obrigatórios não preenchidos!";
        public final static String DADOS_INVALIDOS_EXECEPTION = "Dados inválidos!";
        public final static String DADOS_INCORRETOS_EXECEPTION = "Dados incorretos!";
    }

    public static class Pessoa {
        public static final String CNPJ_EXISTENTE_EXCEPTION = "CNPJ já cadastrado!";
        public static final String CPF_EXISTENTE_EXCEPTION = "CPF já cadastrado!";
        public static final String RESPONSAVEL_INEXISTENTE_EXCEPTION = "É obrigatório o cadastro de um responsável a menores de 18 anos";
    }

    public static class Usuario {
        public static final String LOGIN_EXISTENTE_EXCEPTION = "Login já existe!";
        public static final String DADOS_INCORRETOS_EXECEPTION = "Login ou senha incorretos!";
        public static final String PESSOA_EXISTENTE_EXCEPTION = "Já existe login cadastrado para essa pessoa!.";
        public static final String SEM_PERMISSAO_EXCEPTION = "Usuário não possui permissão para realizar essa operação!";
    }
}
