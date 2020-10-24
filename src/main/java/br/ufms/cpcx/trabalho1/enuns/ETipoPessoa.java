package br.ufms.cpcx.trabalho1.enuns;

public enum ETipoPessoa {
    FISICA("Fisica"),
    JURIDICA("Juridica");

    private String value;

    private ETipoPessoa(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

