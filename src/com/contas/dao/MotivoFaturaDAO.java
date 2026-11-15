/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contas.dao;

import com.contas.model.MotivoFatura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author molsousa
 */
public class MotivoFaturaDAO {
    public boolean cadastrarMotivoFatura(Connection conn, MotivoFatura motivoFatura) throws Exception {
        String SQLInsert = "insert into motivo_fatura (descricao_motivo_fatura) values (?)";

        try (PreparedStatement stmt = conn.prepareStatement(SQLInsert)) {
            stmt.setString(1, motivoFatura.getDescricaoMotivoFatura());

            int linhas = stmt.executeUpdate();

            return linhas > 0;
        } catch (SQLException e) {
            System.err.println("Erro na transação: " + e.getMessage());
            throw new SQLException(e);
        }
    }

    public int obterIDMotivoFatura(Connection conn, MotivoFatura motivoFatura) throws Exception {
        String SQLSelect = "select * from motivo_fatura where descricao_motivo_fatura = ?";

        try (PreparedStatement stmt = conn.prepareStatement(SQLSelect)) {
            stmt.setString(1, motivoFatura.getDescricaoMotivoFatura());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_motivo_fatura");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro na busca: " + e.getMessage());
            throw new SQLException(e);
        }

        throw new Exception("Id não encontrado no banco");
    }

    public boolean removerMotivoFatura(Connection conn, MotivoFatura motivoFatura) throws Exception {
        String SQLDelete = "delete from motivo_fatura where id_motivo_fatura = ?";

        try (PreparedStatement stmt = conn.prepareStatement(SQLDelete)) {
            stmt.setInt(1, motivoFatura.getIdMotivoFatura());

            int linhas = stmt.executeUpdate();

            return linhas > 0;
        } catch (SQLException e) {
            System.err.println("Erro na transação: " + e.getMessage());
            throw new SQLException(e);
        }
    }
}
