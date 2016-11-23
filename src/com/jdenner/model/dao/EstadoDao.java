package com.jdenner.model.dao;

import com.jdenner.controller.util.ExceptionValidacao;
import com.jdenner.model.Estado;
import com.jdenner.model.Situacao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Juliano
 */
public class EstadoDao {

    public static void salvar(Estado estado) throws Exception {
        if (estado.getCodigo() == 0) {
            if (existe(estado)) {
                throw new ExceptionValidacao("O estado já está cadastrado");
            }
            inserir(estado);
        } else {
            alterar(estado);
        }
    }

    private static boolean existe(Estado estado) throws Exception {
        String sql = "select count(codigo) from tb_estado where nome=? or sigla=?";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setString(1, estado.getNome());
        ps.setString(2, estado.getSigla());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }

    private static void inserir(Estado estado) throws Exception {
        String sql = "insert into tb_estado (nome, sigla, situacao) values (?,?,?)";
        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, estado.getNome());
        ps.setString(2, estado.getSigla());
        ps.setString(3, estado.getSituacao().getSigla());
        ps.execute();
        con.confirmar();
    }

    private static void alterar(Estado estado) throws Exception {
        String sql = "update tb_estado set nome=?, sigla=?, situacao=? where codigo=?";
        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, estado.getNome());
        ps.setString(2, estado.getSigla());
        ps.setString(3, estado.getSituacao().getSigla());
        ps.setInt(4, estado.getCodigo());
        ps.execute();
        con.confirmar();
    }

    public static ObservableList<Estado> listar(String filtro, int paginaAtual, int quantidade) throws Exception {
        String sql = "select * from tb_estado where nome like ? or sigla like ? order by situacao, nome limit ?,?";
        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, "%" + filtro + "%");
        ps.setString(2, "%" + filtro + "%");
        ps.setInt(3, paginaAtual * quantidade);
        ps.setInt(4, quantidade);
        ResultSet rs = ps.executeQuery();
        ObservableList<Estado> lista = FXCollections.observableArrayList();
        while (rs.next()) {
            Estado estado = new Estado();
            estado.setCodigo(rs.getInt("codigo"));
            estado.setNome(rs.getString("nome"));
            estado.setSigla(rs.getString("sigla"));
            estado.setSituacao(Situacao.get(rs.getString("situacao")));
            lista.add(estado);
        }
        return lista;
    }

    public static int quantidade(String filtro) throws Exception {
        String sql = "select count(codigo) from tb_estado where nome like ? or sigla like ?";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setString(1, "%" + filtro + "%");
        ps.setString(2, "%" + filtro + "%");
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

}
