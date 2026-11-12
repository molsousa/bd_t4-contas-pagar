/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contas.model;

import java.util.List;

/**
 *
 * @author molsousa
 */
public class Fornecedor {

    private int idFornecedor;
    private String nomeFornecedor;
    private String cnpjFornecedor;
    private double saldoTotal;
    private EnderecoEspecifico enderecoEspecifico;
    private List<FoneFornecedor> fones;
    private List<EmailFornecedor> emails;

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public String getCnpjFornecedor() {
        return cnpjFornecedor;
    }

    public void setCnpjFornecedor(String cnpjFornecedor) {
        this.cnpjFornecedor = cnpjFornecedor;
    }

    public double getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(double saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    public EnderecoEspecifico getEnderecoEspecifico() {
        return enderecoEspecifico;
    }

    public void setEnderecoEspecifico(EnderecoEspecifico enderecoEspecifico) {
        this.enderecoEspecifico = enderecoEspecifico;
    }

    public List<FoneFornecedor> getFones() {
        return fones;
    }

    public void setFones(List<FoneFornecedor> fones) {
        this.fones = fones;
    }

    public List<EmailFornecedor> getEmails() {
        return emails;
    }

    public void setEmails(List<EmailFornecedor> emails) {
        this.emails = emails;
    }

}
