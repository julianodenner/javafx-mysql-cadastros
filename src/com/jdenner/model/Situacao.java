package com.jdenner.model;

/**
 *
 * @author Juliano
 */
public enum Situacao {

    ATIVO("A", "Ativo"),
    INATIVO("I", "Inativo");

    private String sigla;
    private String descricao;

    public String getSigla() {
        return sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    private Situacao(String sigla, String descricao) {
        this.sigla = sigla;
        this.descricao = descricao;
    }

    public static Situacao get(String sigla) {
        for (Situacao situacao : Situacao.values()) {
            if (situacao.getSigla().equalsIgnoreCase(sigla)) {
                return situacao;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getDescricao();
    }

}
