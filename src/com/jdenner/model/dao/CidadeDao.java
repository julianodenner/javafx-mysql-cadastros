package com.jdenner.model.dao;

import com.jdenner.model.Cidade;
import com.jdenner.model.Estado;
import com.jdenner.model.Situacao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Juliano
 */
public class CidadeDao {

    public static void salvar(Cidade cidade) throws Exception {
        if (cidade.getCodigo() == 0) {
            inserir(cidade);
        } else {
            alterar(cidade);
        }
    }

    public static void inserir(Cidade cidade) throws Exception {
        String sql = "insert into tb_cidade (nome, estado, situacao) values (?,?,?)";
        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, cidade.getNome());
        ps.setInt(2, cidade.getEstado().getCodigo());
        ps.setString(3, cidade.getSituacao().getSigla());
        ps.execute();
        con.confirmar();
    }

    public static void alterar(Cidade cidade) throws Exception {
        String sql = "update tb_cidade set nome=?, estado=?, situacao=? where codigo=?";
        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, cidade.getNome());
        ps.setInt(2, cidade.getEstado().getCodigo());
        ps.setString(3, cidade.getSituacao().getSigla());
        ps.setInt(4, cidade.getCodigo());
        ps.execute();
        con.confirmar();
    }

    public static ObservableList<Cidade> listar(String filtro, int paginaAtual, int quantidade) throws Exception {
        String sql = ""
                + " select *"
                + " from tb_cidade as c"
                + " inner join tb_estado as e on e.codigo = c.estado"
                + " where c.nome like ? or e.nome like ?"
                + " order by c.situacao, c.nome "
                + " limit ?,?";
        Conexao con = new Conexao();
        PreparedStatement ps = con.getConexao().prepareStatement(sql);
        ps.setString(1, "%" + filtro + "%");
        ps.setString(2, "%" + filtro + "%");
        ps.setInt(3, paginaAtual * quantidade);
        ps.setInt(4, quantidade);
        ResultSet rs = ps.executeQuery();
        ObservableList<Cidade> lista = FXCollections.observableArrayList();
        while (rs.next()) {
            Cidade cidade = new Cidade();
            cidade.setCodigo(rs.getInt("c.codigo"));
            cidade.setNome(rs.getString("c.nome"));
            cidade.setEstado(new Estado(rs.getInt("e.codigo"), rs.getString("e.nome")));
            cidade.setSituacao(Situacao.get(rs.getString("c.situacao")));
            lista.add(cidade);
        }
        return lista;
    }

    public static int quantidade(String filtro) throws Exception {
        String sql = ""
                + " select count(c.codigo)"
                + " from tb_cidade as c"
                + " inner join tb_estado as e on e.codigo = c.estado"
                + " where c.nome like ? or e.nome like ?";
        Conexao c = new Conexao();
        PreparedStatement ps = c.getConexao().prepareStatement(sql);
        ps.setString(1, "%" + filtro + "%");
        ps.setString(2, "%" + filtro + "%");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            return 0;
        }
    }
}
