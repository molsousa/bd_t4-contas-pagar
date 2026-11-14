/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contas.view;

import com.contas.connection.AdminTotal;
import com.contas.connection.ConnectionFactory;
import com.contas.connection.Root;
import com.contas.connection.UsuarioInsert;
import com.contas.connection.UsuarioQuery;
import com.contas.dao.FaturaDAO;
import com.contas.dao.FornecedorDAO;
import com.contas.model.EmailFornecedor;
import java.sql.Connection;
import java.sql.SQLException;
import com.contas.model.Fatura;
import com.contas.model.FoneFornecedor;
import com.contas.model.Fornecedor;
import com.contas.model.MotivoFatura;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author molsousa
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        int opcao = 1;

        while (opcao != 0) {

            System.out.println();
            System.out.println("------------ SISTEMA DE CONTAS A PAGAR ------------");
            System.out.println("| 1 - Obter fornecedores                          |");
            System.out.println("| 2 - Obter faturas de um fornecedor              |");
            System.out.println("| 3 - Efetuar transação completa                  |");
            System.out.println("| 4 - Efetuar transação incompleta                |");
            System.out.println("| 5 - Efetuar tansação incompleta com rollback    |");
            System.out.println("| 6 - Simular usuário query                       |");
            System.out.println("| 7 - Simular usuário insert                      |");
            System.out.println("| 8 - Simular admin                               |");
            System.out.println("| 9 - Inserir fatura                              |");
            System.out.println("| 0 - Sair                                        |");
            System.out.println("---------------------------------------------------");
            System.out.print("Selecione uma opção: ");
            opcao = scanner.nextInt();
            System.out.println();

            int idFornecedor;

            switch (opcao) {
                case 1:
                    queryFornecedores();
                    break;
                case 2:
                    System.out.print("Insira o ID do fornecedor: ");
                    idFornecedor = scanner.nextInt();
                    System.out.println();
                    queryFatura(idFornecedor);
                    break;
                case 3:
                    System.out.print("Insira o ID do fornecedor: ");
                    idFornecedor = scanner.nextInt();
                    simularTransacaoCompleta(idFornecedor);
                    System.out.println();
                    break;
                case 4:
                    System.out.print("Insira o ID do fornecedor: ");
                    idFornecedor = scanner.nextInt();
                    simularTransacaoIncompleta(idFornecedor);
                    System.out.println();
                    break;
                case 5:
                    System.out.print("Insira o ID do fornecedor: ");
                    idFornecedor = scanner.nextInt();
                    simularTransacaoRollback(idFornecedor);
                    System.out.println();
                    break;
                case 6:
                    System.out.println("Conexão com Usuário Query");
                    System.out.print("Insira o ID do fornecedor: ");
                    idFornecedor = scanner.nextInt();
                    System.out.println();
                    simularUsuarioQuery(idFornecedor);
                    break;
                case 7:
                    System.out.println("Conexão com Usuário Insert");
                    System.out.println();
                    simularUsuarioInsert();
                    break;
                case 8:
                    System.out.println("Conexão com Admin");
                    System.out.println();
                    simularAdminTotal();
                    break;
                case 9:
                    insertFatura();
                    System.out.println();
                    break;
                case 0:
                    System.out.println("Obrigado");
                    break;
            }
        }
    }

    private static void insertFatura() throws Exception {

        Fatura fatura = new Fatura();
        FaturaDAO faturaDAO = new FaturaDAO();

        System.out.print("Insira a data de lançamento: ");
        String data = scanner.next();
        fatura.setDataLancamento(Date.valueOf(data));

        System.out.print("Insira a data de vencimento: ");
        data = scanner.next();
        fatura.setDataVencimento(Date.valueOf(data));

        System.out.print("Insira o valor da fatura: ");
        fatura.setValorFatura(scanner.nextDouble());
        scanner.nextLine();

        MotivoFatura motivoFatura = new MotivoFatura();
        System.out.print("Insira o motivo da fatura: ");
        motivoFatura.setDescricaoMotivoFatura(scanner.nextLine());

        try (Connection conn = ConnectionFactory.getConnection(Root.USER, Root.PASS)) {
            try {
                motivoFatura.setIdMotivoFatura(faturaDAO.obterIDMotivoFatura(conn, motivoFatura));
                conn.commit();
            } catch (Exception e) {

                if (e instanceof SQLException) {
                    conn.rollback();
                    System.err.println(e);
                }

                faturaDAO.cadastrarMotivoFatura(conn, motivoFatura);
                motivoFatura.setIdMotivoFatura(faturaDAO.obterIDMotivoFatura(conn, motivoFatura));
                conn.commit();
            }
        }

        fatura.setMotivoFatura(motivoFatura);

        System.out.print("Insira o ID do fornecedor: ");
        int idFornecedor = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Pagamento já foi feito? sim ou nao: ");
        String pagamentoFeito = scanner.nextLine();

        if (pagamentoFeito.equals("sim")) {
            fatura.setPagamentoFeito(true);
        } else {
            fatura.setPagamentoFeito(false);
        }

        try (Connection conn = ConnectionFactory.getConnection(Root.USER, Root.PASS)) {
            try {
                if (faturaDAO.cadastrarFatura(conn, idFornecedor, fatura)) {
                    System.out.println("Cadastro concluído");
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException(e);
            }
        }
    }

    private static void queryFornecedores() throws Exception {

        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        List<Fornecedor> fornecedores;

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

                if (!faturas.isEmpty()) {
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
                    for (FoneFornecedor foneFornecedor : fornecedor.getFones()) {
                        System.out.println("+" + foneFornecedor.getDdi().getNroDDI()
                                + " (" + foneFornecedor.getDdd().getNroDDD() + ") " + foneFornecedor.getNroFone());
                    }
                    System.out.println();

                    System.out.println("Saldo a pagar: " + fornecedor.getSaldoTotal());
                    System.out.println();
                }

                for (Fatura fatura : faturas) {
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

    private static void simularAdminTotal() throws Exception {
        FaturaDAO faturaDAO = new FaturaDAO();

        try (Connection conn = ConnectionFactory.getConnection(AdminTotal.USER, AdminTotal.PASS)) {
            try {
                MotivoFatura motivoFatura = new MotivoFatura();
                motivoFatura.setDescricaoMotivoFatura("novo_motivo_fatura");

                if (faturaDAO.cadastrarMotivoFatura(conn, motivoFatura)) {
                    System.out.println("cadastro feito");
                }

                int idMotivoFatura = faturaDAO.obterIDMotivoFatura(conn, motivoFatura);

                System.out.println("ID inserido: " + idMotivoFatura);

                motivoFatura.setIdMotivoFatura(idMotivoFatura);

                if (faturaDAO.removerMotivoFatura(conn, motivoFatura)) {
                    System.out.println("remoção feita");
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException(e);
            }
        }
    }

    private static void simularUsuarioQuery(int idFornecedor) throws Exception {
        FaturaDAO faturaDAO = new FaturaDAO();

        try (Connection conn = ConnectionFactory.getConnection(UsuarioQuery.USER, UsuarioQuery.PASS)) {
            try {
                List<Fatura> faturas = faturaDAO.obterFaturas(conn, idFornecedor);

                for (Fatura fatura : faturas) {
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

                MotivoFatura motivoFatura = new MotivoFatura();
                motivoFatura.setDescricaoMotivoFatura("Compra de alimentos");

                faturaDAO.cadastrarMotivoFatura(conn, motivoFatura);

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException(e);
            }
        }
    }

    private static void simularUsuarioInsert() throws Exception {
        FaturaDAO faturaDAO = new FaturaDAO();

        try (Connection conn = ConnectionFactory.getConnection(UsuarioInsert.USER, UsuarioInsert.PASS)) {
            try {
                MotivoFatura motivoFatura = new MotivoFatura();
                motivoFatura.setIdMotivoFatura(1);
                motivoFatura.setDescricaoMotivoFatura("novo_motivo_fatura");

                if (faturaDAO.cadastrarMotivoFatura(conn, motivoFatura)) {
                    System.out.println("cadastro feito");
                }

                int idMotivoFatura = faturaDAO.obterIDMotivoFatura(conn, motivoFatura);

                System.out.println("ID inserido: " + idMotivoFatura);

                motivoFatura.setIdMotivoFatura(idMotivoFatura);

                if (faturaDAO.removerMotivoFatura(conn, motivoFatura)) {
                    System.out.println("remoção feita");
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException(e);
            }
        }
    }

    private static void simularTransacaoCompleta(int idFornecedor) throws Exception {
        FaturaDAO faturaDAO = new FaturaDAO();

        try (Connection conn = ConnectionFactory.getConnection(Root.USER, Root.PASS)) {
            try {
                faturaDAO.cadastrarFaturaSucesso(conn, idFornecedor, new MotivoFatura(1));
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.err.println(e);
            }
        }
    }

    private static void simularTransacaoIncompleta(int idFornecedor) throws Exception {

        FaturaDAO faturaDAO = new FaturaDAO();

        try (Connection conn = ConnectionFactory.getConnection(Root.USER, Root.PASS)) {
            try {
                faturaDAO.cadastrarFaturaIncompleta(conn, idFornecedor, new MotivoFatura(1));
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                System.err.println(e);
            }
        }
    }

    private static void simularTransacaoRollback(int idFornecedor) throws Exception {
        FaturaDAO faturaDAO = new FaturaDAO();

        try (Connection conn = ConnectionFactory.getConnection(Root.USER, Root.PASS)) {
            try {
                faturaDAO.cadastrarFaturaRollback(conn, idFornecedor, new MotivoFatura(1));
                conn.commit();
            } catch (Exception e) {
                System.out.println("Executando o rollback");
                conn.rollback();
                throw new Exception(e);
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

        if (comp.equals("null")) {
            return siglaTipLog + ". " + Log + ", " + nro + ". " + bai + ", " + cid + ". " + u + ".";
        }

        return siglaTipLog + ". " + Log + ", " + nro + ", " + comp + ". " + bai + ", " + cid + ". " + u + ".";
    }

}
