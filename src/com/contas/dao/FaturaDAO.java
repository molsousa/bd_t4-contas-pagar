/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contas.dao;

import com.contas.model.Bairro;
import com.contas.model.Cidade;
import com.contas.model.Endereco;
import com.contas.model.EnderecoEspecifico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.contas.model.Fatura;
import com.contas.model.Fornecedor;
import com.contas.model.Logradouro;
import com.contas.model.MotivoFatura;
import com.contas.model.TipoLogradouro;
import com.contas.model.UF;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author molsousa
 */
public class FaturaDAO {
    
    public boolean cadastrarFatura(Connection conn, int idFornecedor, Fatura fatura) throws Exception {
        String SQLInsert = "insert into fatura (data_lancamento,data_vencimento,valor_fatura,pagamento_feito,id_fornecedor,id_motivo_fatura) "
                + "values (?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(SQLInsert)) {
            stmt.setDate(1, fatura.getDataLancamento());
            stmt.setDate(2, fatura.getDataVencimento());
            stmt.setDouble(3, fatura.getValorFatura());
            stmt.setBoolean(4, fatura.isPagamentoFeito());
            stmt.setInt(5, idFornecedor);
            stmt.setInt(6, fatura.getMotivoFatura().getIdMotivoFatura());

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.err.println("Erro na transação");
            throw new SQLException(e);
        }
    }
    
    public Fatura obterFaturaPorNro(Connection conn, int nroFatura, int idFornecedor) throws Exception {
        String SQL = "select \n"
                + "    f.nro_fatura,\n"
                + "    f.valor_fatura,\n"
                + "    f.pagamento_feito,\n"
                + "    f.id_fornecedor\n"
                + "from fatura f\n"
                + "where f.nro_fatura = ?";
        
        Fatura fatura = new Fatura();
        
        try (PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setInt(1, nroFatura);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    if(idFornecedor == rs.getInt("id_fornecedor") && !rs.getBoolean("pagamento_feito")) {
                        fatura.setValorFatura(rs.getDouble("valor_fatura"));
                    
                        return fatura;
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar faturas no banco: " + e.getMessage(), e);
        }
        
        return null;
    }

    public List<Fatura> obterFaturas(Connection conn, int idFornecedor) throws Exception {

        String SQL = "select \n"
                + "    f.nro_fatura,\n"
                + "    f.data_lancamento,\n"
                + "    f.data_vencimento,\n"
                + "    f.valor_fatura,\n"
                + "    f.pagamento_feito,\n"
                + "    forn.id_fornecedor,\n"
                + "    forn.nome_fornecedor,\n"
                + "    forn.cnpj,\n"
                + "    forn.saldo_total,\n"
                + "    ende.cep,\n"
                + "    bai.id_bairro,\n"
                + "    bai.nome_bairro,\n"
                + "    cid.id_cidade,\n"
                + "    cid.nome_cidade,\n"
                + "    u.sigla_uf,\n"
                + "    u.nome_uf,\n"
                + "    logr.id_logradouro,\n"
                + "    logr.nome_logradouro,  \n"
                + "    tplg.sigla_tipo_logradouro,\n"
                + "    tplg.nome_tipo_logradouro,\n"
                + "    (forn.endereco_residencial).nro_endereco,\n"
                + "    (forn.endereco_residencial).complemento,\n"
                + "    (forn.endereco_residencial).id_endereco,\n"
                + "    mot.id_motivo_fatura,\n"
                + "    mot.descricao_motivo_fatura as motivo,\n"
                + "    case when f.pagamento_feito = true then 'Pago' else 'Pendente' end as status_pagamento\n "
                + "from fatura f\n"
                + "join fornecedor forn ON f.id_fornecedor = forn.id_fornecedor\n"
                + "join endereco ende on (forn.endereco_residencial).id_endereco = ende.id_endereco\n"
                + "join motivo_fatura mot ON f.id_motivo_fatura = mot.id_motivo_fatura\n"
                + "join bairro bai on ende.id_bairro = bai.id_bairro\n"
                + "join cidade cid on ende.id_cidade = cid.id_cidade\n"
                + "join logradouro logr on ende.id_logradouro = logr.id_logradouro \n"
                + "join uf u on cid.sigla_uf = u.sigla_uf\n"
                + "join tipo_logradouro tplg on tplg.sigla_tipo_logradouro = logr.sigla_tipo_logradouro\n"
                + "where f.id_fornecedor = ?";

        List<Fatura> faturas = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(SQL)) {

            stmt.setInt(1, idFornecedor);
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Fatura fatura = new Fatura();

                    fatura.setNroFatura(rs.getInt("nro_fatura"));
                    fatura.setDataLancamento(rs.getDate("data_lancamento"));
                    fatura.setDataVencimento(rs.getDate("data_vencimento"));
                    fatura.setValorFatura(rs.getDouble("valor_fatura"));
                    fatura.setPagamentoFeito(rs.getBoolean("pagamento_feito"));

                    MotivoFatura motivoFatura = new MotivoFatura(rs.getInt("id_motivo_fatura"));
                    motivoFatura.setDescricaoMotivoFatura(rs.getString("motivo"));

                    Fornecedor fornecedor = new Fornecedor();

                    fornecedor.setIdFornecedor(idFornecedor);
                    fornecedor.setNomeFornecedor(rs.getString("nome_fornecedor"));
                    fornecedor.setSaldoTotal(rs.getDouble("saldo_total"));
                    fornecedor.setCnpjFornecedor(rs.getString("cnpj"));

                    FornecedorDAO fornecedorDAO = new FornecedorDAO();

                    fornecedor.setEmails(fornecedorDAO.obterEmailFornecedores(conn, idFornecedor));
                    fornecedor.setFones(fornecedorDAO.obterFoneFornecedores(conn, idFornecedor));

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
                    fatura.setFornecedor(fornecedor);
                    fatura.setMotivoFatura(motivoFatura);

                    faturas.add(fatura);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar faturas no banco: ", e.getMessage(), e);
        }

        return faturas;
    }

    public void cadastrarFaturaSucesso(Connection conn, int idFornecedor, MotivoFatura motivoFatura) throws Exception {
        String SQLInsert = "insert into fatura (data_lancamento,data_vencimento,valor_fatura,pagamento_feito,id_fornecedor,id_motivo_fatura) "
                + "values (CURRENT_DATE, CURRENT_DATE+30, 500.00, false, ?, ?)";

        String SQLUpdate = "update fornecedor set saldo_total = saldo_total + 500.00 where id_fornecedor = ?";

        try (PreparedStatement stmtInsert = conn.prepareStatement(SQLInsert); PreparedStatement stmtUpdate = conn.prepareStatement(SQLUpdate)) {
            stmtInsert.setInt(1, idFornecedor);
            stmtInsert.setInt(2, motivoFatura.getIdMotivoFatura());
            stmtInsert.executeUpdate();

            stmtUpdate.setInt(1, idFornecedor);
            stmtUpdate.executeUpdate();

            System.out.println("Transação ok");
        } catch (SQLException e) {
            System.err.println("Erro na transação");
            throw new SQLException(e);
        }
    }

    public void cadastrarFaturaIncompleta(Connection conn, int idFornecedor, MotivoFatura motivoFatura) throws Exception {
        String SQLInsert = "insert into fatura (data_lancamento,data_vencimento,valor_fatura,pagamento_feito,id_fornecedor,id_motivo_fatura) "
                + "values (CURRENT_DATE, CURRENT_DATE+30, 500.00, false, ?, ?)";

        String SQLUpdate = "update fornecedor set saldo_total = saldo_total + 500.00 where id_fornecedor = ?";

        try (PreparedStatement stmtInsert = conn.prepareStatement(SQLInsert); PreparedStatement stmtUpdate = conn.prepareStatement(SQLUpdate)) {
            stmtInsert.setInt(1, idFornecedor);
            stmtInsert.setInt(2, motivoFatura.getIdMotivoFatura());
            stmtInsert.executeUpdate();

            stmtUpdate.setInt(1, idFornecedor);
            stmtUpdate.executeUpdate();

            System.err.println("Simulando queda do sistema");
            System.exit(1);
        }
    }

    public void cadastrarFaturaRollback(Connection conn, int idFornecedor, MotivoFatura motivoFatura) throws Exception {
        String SQLInsert = "insert into fatura (data_lancamento,data_vencimento,valor_fatura,pagamento_feito,id_fornecedor,id_motivo_fatura) "
                + "values (CURRENT_DATE, CURRENT_DATE+30, 500.00, false, ?, ?)";

        String SQLUpdate = "update fornecedor set saldo_total = saldo_total + 500.00 where id_fornecedor = ?";

        try (PreparedStatement stmtInsert = conn.prepareStatement(SQLInsert); PreparedStatement stmtUpdate = conn.prepareStatement(SQLUpdate)) {
            stmtInsert.setInt(1, idFornecedor);
            stmtInsert.setInt(2, motivoFatura.getIdMotivoFatura());
            stmtInsert.executeUpdate();

            stmtUpdate.setInt(1, idFornecedor);
            stmtUpdate.executeUpdate();

            double saldoPrevisto = 100000.0;

            if (saldoPrevisto > 10000.0) {
                throw new Exception("Saldo previsto muito alto");
            }
        }
    }
    
    public boolean atualizarPagamento(Connection conn, int idFornecedor, int nroFatura) throws Exception {
        String SQLUpdate = "update fatura set pagamento_feito = true where id_fornecedor = ? and nro_fatura = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(SQLUpdate)) {
            stmt.setInt(1, idFornecedor);
            stmt.setInt(2, nroFatura);
            
            int linhas = stmt.executeUpdate();
            
            return linhas > 0;
        } catch (SQLException e) {
            System.err.println("Erro na transação");
            throw new SQLException(e);
        }
    }
}
