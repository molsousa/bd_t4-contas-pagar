/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contas.view;

import com.contas.connection.ConnectionFactory;
import com.contas.dao.FaturaDAO;
import com.contas.dao.FornecedorDAO;
import java.sql.Connection;
import java.sql.SQLException;
import com.contas.model.Fatura;
import com.contas.model.Fornecedor;

/**
 *
 * @author molsousa
 */
public class Main {

    public static void main(String[] args) throws Exception {
        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        FaturaDAO faturaDAO = new FaturaDAO();
        
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                for(Fornecedor fornecedor : fornecedorDAO.obterFornecedores(conn)) {
                    System.out.println(fornecedor.getNomeFornecedor());
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.err.println(e.getCause());
            }
        }
    }
    
}
