package com.jdenner.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Juliano
 */
public class Conexao {

    private final String SERVIDOR = "localhost";
    private final String PORTA = "3306";
    private final String USUARIO = "root";
    private final String SENHA = "root";
    private final String BANCO = "db_sistema";
    private final String URL = "jdbc:mysql://" + SERVIDOR + ":" + PORTA + "/" + BANCO;
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final Connection CONEXAO;

    public Conexao() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        CONEXAO = DriverManager.getConnection(URL, USUARIO, SENHA);
        CONEXAO.setAutoCommit(false);
    }

    public Connection getConexao() {
        return CONEXAO;
    }

    public void confirmar() throws SQLException {
        CONEXAO.commit();
        CONEXAO.close();
    }
}
