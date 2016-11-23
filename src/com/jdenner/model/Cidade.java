package com.jdenner.model;

import com.jdenner.controller.util.ExceptionValidacao;

/**
 *
 * @author Juliano
 */
public class Cidade {

    private int codigo;
    private String nome;
    private Estado estado;
    private Situacao situacao;

    public Cidade() {
        this.codigo = 0;
    }

    public Cidade(int codigo) {
        this.codigo = codigo;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
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
        if (obj instanceof Cidade) {
            return ((Cidade) obj).getCodigo() == getCodigo();
        }
        return false;
    }
}
