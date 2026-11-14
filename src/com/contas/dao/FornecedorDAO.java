/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contas.dao;

import com.contas.model.Bairro;
import com.contas.model.Cidade;
import com.contas.model.DDD;
import com.contas.model.DDI;
import com.contas.model.EmailFornecedor;
import com.contas.model.Endereco;
import com.contas.model.EnderecoEspecifico;
import com.contas.model.FoneFornecedor;
import com.contas.model.Fornecedor;
import com.contas.model.Logradouro;
import com.contas.model.TipoLogradouro;
import com.contas.model.UF;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author molsousa
 */
public class FornecedorDAO {
    
    public List<Fornecedor> obterFornecedores(Connection conn) throws Exception {
        
        String SQL = "select\n" +
                    "	f.id_fornecedor,\n" +
                    "	f.nome_fornecedor,\n" +
                    "	f.cnpj,\n" +
                    "	f.saldo_total,\n" +
                    "	(f.endereco_residencial).nro_endereco,\n" +
                    "	(f.endereco_residencial).complemento,\n" +
                    "	(f.endereco_residencial).id_endereco,\n" +
                    "   e.cep,\n" +
                    "	e.id_bairro,\n" +
                    "	e.id_cidade,\n" +
                    "	e.id_logradouro,\n" +
                    "	b.nome_bairro,\n" +
                    "	l.nome_logradouro,\n" +
                    "	l.sigla_tipo_logradouro,\n" +
                    "	tl.nome_tipo_logradouro,\n" +
                    "	c.nome_cidade,\n" +
                    "	c.sigla_uf,\n" +
                    "	u.nome_uf\n" +
                    "from fornecedor f\n" +
                    "join endereco e on e.id_endereco = (f.endereco_residencial).id_endereco\n" +
                    "join bairro b on b.id_bairro = e.id_bairro \n" +
                    "join cidade c on c.id_cidade = e.id_cidade \n" +
                    "join logradouro l on l.id_logradouro = e.id_logradouro \n" +
                    "join tipo_logradouro tl on tl.sigla_tipo_logradouro = l.sigla_tipo_logradouro \n" +
                    "join uf u on u.sigla_uf = c.sigla_uf";
        
        List<Fornecedor> fornecedores = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(SQL)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    Fornecedor fornecedor = new Fornecedor();
                    
                    fornecedor.setIdFornecedor(rs.getInt("id_fornecedor"));
                    fornecedor.setNomeFornecedor(rs.getString("nome_fornecedor"));
                    fornecedor.setCnpjFornecedor(rs.getString("cnpj"));
                    fornecedor.setSaldoTotal(rs.getDouble("saldo_total"));
                    
                    EnderecoEspecifico enderecoEspecifico = new EnderecoEspecifico(rs.getInt("nro_endereco"), rs.getString("complemento"));
                    
                    Endereco endereco = new Endereco();
                    
                    endereco.setIdEndereco(rs.getInt("id_endereco"));
                    endereco.setCep(rs.getString("cep"));
                    
                    Bairro bairro = new Bairro();
                    
                    bairro.setIdBairro(rs.getInt("id_bairro"));
                    bairro.setNomeBairro(rs.getString("nome_bairro"));
                    
                    Cidade cidade = new Cidade();
                    
                    cidade.setIdCidade(rs.getInt("id_cidade"));
                    cidade.setNomeCidade(rs.getString("nome_cidade"));
                    
                    UF uf = new UF();
                    
                    uf.setSiglaUF(rs.getString("sigla_uf"));
                    uf.setNomeUF(rs.getString("nome_uf"));
                    
                    Logradouro logradouro = new Logradouro();
                    
                    logradouro.setIdLogradouro(rs.getInt("id_logradouro"));
                    logradouro.setNomeLogradouro(rs.getString("nome_logradouro"));
                    
                    TipoLogradouro tipoLogradouro = new TipoLogradouro();
                    
                    tipoLogradouro.setSiglaTipoLogradouro(rs.getString("sigla_tipo_logradouro"));
                    tipoLogradouro.setNomeTipoLogradouro(rs.getString("nome_tipo_logradouro"));
                    
                    logradouro.setTipoLogradouro(tipoLogradouro);
                    cidade.setUf(uf);
                    endereco.setLogradouro(logradouro);
                    endereco.setCidade(cidade);
                    endereco.setBairro(bairro);
                    enderecoEspecifico.setEndereco(endereco);
                    fornecedor.setEnderecoEspecifico(enderecoEspecifico);
                    
                    fornecedor.setEmails(obterEmailFornecedores(conn, fornecedor.getIdFornecedor()));
                    fornecedor.setFones(obterFoneFornecedores(conn, fornecedor.getIdFornecedor()));
                     
                    fornecedores.add(fornecedor);
                }
            } catch (SQLException e) {
                throw new SQLException("Erro ao buscar fornecedores no banco: ", e.getMessage(), e);
            }
        }
        return fornecedores;
    }
    
    public List<EmailFornecedor> obterEmailFornecedores(Connection conn, int idFornecedor) throws Exception {
        
        String SQL = "select\n" +
                    "	e.id_email_fornecedor,\n" +
                    "	e.email_fornecedor\n" +
                    "from email_fornecedor e where e.id_fornecedor = ?";
        
        List<EmailFornecedor> emailFornecedores = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setInt(1, idFornecedor);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    EmailFornecedor emailFornecedor = new EmailFornecedor();
                    
                    emailFornecedor.setIdEmailFornecedor(rs.getInt("id_email_fornecedor"));
                    emailFornecedor.setEmailFornecedor(rs.getString("email_fornecedor"));
                    
                    emailFornecedores.add(emailFornecedor);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar emails no banco: ", e.getMessage(), e);
        }
        
        return emailFornecedores;
    }

    public List<FoneFornecedor> obterFoneFornecedores(Connection conn, int idFornecedor) throws Exception {

        String SQL = "select\n"
                + "	ff.id_fone_fornecedor,\n"
                + "	ff.nro_ddi,\n"
                + "	ff.nro_ddd,\n"
                + "	ff.nro_fone\n"
                + "from fone_fornecedor ff\n"
                + "join fornecedor f on f.id_fornecedor = ff.id_fornecedor\n"
                + "where f.id_fornecedor = ?";

        List<FoneFornecedor> foneFornecedores = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setInt(1, idFornecedor);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    FoneFornecedor foneFornecedor = new FoneFornecedor();
                    DDD ddd = new DDD();
                    DDI ddi = new DDI();
                    
                    foneFornecedor.setIdFoneFornecedor(rs.getInt("id_fone_fornecedor"));
                    foneFornecedor.setNroFone(rs.getString("nro_fone"));
                    ddd.setNroDDD(rs.getLong("nro_ddd"));
                    ddi.setNroDDI(rs.getLong("nro_ddi"));
                    
                    foneFornecedor.setDdd(ddd);
                    foneFornecedor.setDdi(ddi);
                    
                    foneFornecedores.add(foneFornecedor);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar telefones no banco: ", e.getMessage(), e);
        }
        
        return foneFornecedores;
    }
    
    public boolean atualizarSaldoFornecedor(Connection conn, double saldoAdicionado, int idFornecedor) throws Exception {
        String SQLUpdate = "update fornecedor set saldo_total = saldo_total + ? where id_fornecedor = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(SQLUpdate)) {
            stmt.setDouble(1, saldoAdicionado);
            stmt.setInt(2, idFornecedor);
            
            int linhas = stmt.executeUpdate();
            
            return linhas > 0;
        } catch (SQLException e) {
            System.err.println("Erro na transação");
            throw new SQLException(e);
        }
    }
}
