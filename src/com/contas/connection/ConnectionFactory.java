/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contas.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author molsousa
 */
public class ConnectionFactory {
    
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5432/ContasAPagar";
    
    public static Connection getConnection(String user, String password) {
        try {
            Class.forName(DRIVER);

            Connection conn = DriverManager.getConnection(URL, user, password);
            conn.setAutoCommit(false);
            
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Erro na conexão: " + ex);
        }
    }
}
