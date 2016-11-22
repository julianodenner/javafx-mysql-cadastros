package com.jdenner.model;

import com.jdenner.controller.ExceptionValidacao;

/**
 *
 * @author Juliano
 */
public class Estado {

    private int codigo;
    private String nome;
    private String sigla;
    private Situacao situacao;

    public Estado() {
        this.codigo = 0;
    }

    public Estado(int codigo) {
        this.codigo = codigo;
    }

    public Estado(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws ExceptionValidacao {
        if (nome.isEmpty()) {
            throw new ExceptionValidacao("Nome obrigatório");
        }
        if (nome.trim().length() < 2) {
            throw new ExceptionValidacao("Nome muito curto");
        }
        this.nome = nome.trim();
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) throws ExceptionValidacao {
        if (sigla.isEmpty()) {
            throw new ExceptionValidacao("Sigla obrigatória");
        }
        if (sigla.trim().length() != 2) {
            throw new ExceptionValidacao("Sigla inválida");
        }
        this.sigla = sigla.trim().toUpperCase();
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    @Override
    public String toString() {
        return getNome();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Estado) {
            return ((Estado) obj).getCodigo() == getCodigo();
        }
        return false;
    }

}
