/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contas.view;

import com.contas.connection.AdminTotal;
import com.contas.connection.ConnectionFactory;
import com.contas.connection.Root;
import com.contas.connection.UsuarioInsert;
import com.contas.dao.FaturaDAO;
import com.contas.dao.FornecedorDAO;
import com.contas.model.EmailFornecedor;
import java.sql.Connection;
import java.sql.SQLException;
import com.contas.model.Fatura;
import com.contas.model.FoneFornecedor;
import com.contas.model.Fornecedor;
import com.contas.model.MotivoFatura;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author molsousa
 */
public class Main {

    public static void main(String[] args) throws Exception {
        
        queryFatura(1);
    }
    
    private static void queryFornecedores() throws Exception {
        
        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        List<Fornecedor> fornecedores = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection(Root.USER, Root.PASS)) {
            
            try {
                fornecedores = fornecedorDAO.obterFornecedores(conn);
                
                for (Fornecedor fornecedor : fornecedores) {
                    System.out.print("ID: " + fornecedor.getIdFornecedor() + " | ");
                    System.out.print("Nome: " + fornecedor.getNomeFornecedor() + " | ");
                    System.out.println("CNPJ: " + fornecedor.getCnpjFornecedor());
                }
                
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.err.println(e);
            }
        }
    }
    
    private static void queryFatura(int idFornecedor) throws Exception {
        FaturaDAO faturaDAO = new FaturaDAO();
        List<Fatura> faturas;
        
        try (Connection conn = ConnectionFactory.getConnection(Root.USER, Root.PASS)) {
            
            try {
                faturas = faturaDAO.obterFaturas(conn, idFornecedor);
                
                if(!faturas.isEmpty()) {
                    Fornecedor fornecedor = faturas.get(0).getFornecedor();
                    
                    System.out.print("ID: " + fornecedor.getIdFornecedor() + " | ");
                    System.out.print("Nome: " + fornecedor.getNomeFornecedor() + " | ");
                    System.out.println("CNPJ: " + fornecedor.getCnpjFornecedor());
                    System.out.println();
                    
                    System.out.println("Endereço: " + montarEndereco(fornecedor));
                    
                    System.out.println();
                    System.out.println("Emails");
                    for (EmailFornecedor emailFornecedor : fornecedor.getEmails()) {
                        System.out.println(emailFornecedor.getEmailFornecedor());
                    }
                    System.out.println();
                    
                    System.out.println("Fones");
                    for(FoneFornecedor foneFornecedor : fornecedor.getFones()) {
                        System.out.println("+" + foneFornecedor.getDdi().getNroDDI() + 
                                " (" + foneFornecedor.getDdd().getNroDDD() + ") " + foneFornecedor.getNroFone());
                    }
                    System.out.println();
                    
                    System.out.println("Saldo a pagar: " + fornecedor.getSaldoTotal());
                    System.out.println();
                }
                
                for(Fatura fatura : faturas) {
                    System.out.print("NRO: " + fatura.getNroFatura() + " ");
                    System.out.println("Motivo da fatura: " + fatura.getMotivoFatura().getDescricaoMotivoFatura() + " ");
                    System.out.print("Data de vencimento: " + fatura.getDataVencimento() + " ");
                    System.out.print("Valor a pagar: R$ " + fatura.getValorFatura() + " -> ");
                    
                    if (fatura.isPagamentoFeito()) {
                        System.out.println("Pago");
                    } else {
                        System.out.println("Pendente");
                    }
                    System.out.println();
                }
                
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.err.println(e);
            }
        }
    }
    
    private static void simularUsuarioInsert() {
        
    }
    
    private static void simularUsuarioQuery() {
        
    }
    
    private static void simularAdminTotal() {
        
    }
    
    private static void simularTransacaoCompleta() throws Exception {
        FaturaDAO faturaDAO = new FaturaDAO();
        
        try (Connection conn = ConnectionFactory.getConnection(Root.USER, Root.PASS)) {
            try {
                faturaDAO.cadastrarFaturaSucesso(conn, 1, new MotivoFatura(1));
                conn.commit();
            } catch (SQLException e) { 
                conn.rollback();
                System.err.println(e);
            }
        }
    }
    
    private static void simularTransacaoIncompleta() throws Exception {
        
        FaturaDAO faturaDAO = new FaturaDAO();
        
        try (Connection conn = ConnectionFactory.getConnection(Root.USER, Root.PASS)) {
            try {
                faturaDAO.cadastrarFaturaIncompleta(conn, 1, new MotivoFatura(1));
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.err.println(e);
            }
        }
    }
    
    private static void simularTransacaoRollback() throws Exception {
        FaturaDAO faturaDAO = new FaturaDAO();
        
        try (Connection conn = ConnectionFactory.getConnection(Root.USER, Root.PASS)) {
            try {
                faturaDAO.cadastrarFaturaRollback(conn, 1, new MotivoFatura(1));
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.err.println(e);
            }
        }
    }
    
    private static String montarEndereco(Fornecedor fornecedor) {
        String siglaTipLog = fornecedor.getEnderecoEspecifico().getEndereco().getLogradouro().getTipoLogradouro().getSiglaTipoLogradouro();
        String Log = fornecedor.getEnderecoEspecifico().getEndereco().getLogradouro().getNomeLogradouro();
        long nro = fornecedor.getEnderecoEspecifico().getNroEndereco();
        String comp = fornecedor.getEnderecoEspecifico().getComplemento();
        String bai = fornecedor.getEnderecoEspecifico().getEndereco().getBairro().getNomeBairro();
        String cid = fornecedor.getEnderecoEspecifico().getEndereco().getCidade().getNomeCidade();
        String u = fornecedor.getEnderecoEspecifico().getEndereco().getCidade().getUf().getSiglaUF();
        
        if(comp.equals("null")) {
            return siglaTipLog + ". " + Log + ", " + nro + ". " + bai + ", " + cid + ". " + u + ".";
        }
        
        return siglaTipLog + ". " + Log + ", " + nro + ", " + comp + ". " + bai + ", " + cid + ". " + u + ".";
    }
    
}
