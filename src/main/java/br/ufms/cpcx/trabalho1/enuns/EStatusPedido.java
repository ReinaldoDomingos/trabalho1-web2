package br.ufms.cpcx.trabalho1.enuns;

public enum EStatusPedido {
    REALIZADO("Realizado", "R"),
    CANCELADO("Cancelado", "C");

    private final String nome;
    private final String sigla;

    EStatusPedido(String nome, String sigla){
        this.nome = nome;
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }
}
