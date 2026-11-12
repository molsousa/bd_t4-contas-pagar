/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contas.model;

import java.sql.Date;

/**
 *
 * @author molsousa
 */
public class Fatura {

    private long nroFatura;
    private Fornecedor fornecedor;
    private Date dataLancamento;
    private Date dataVencimento;
    private MotivoFatura motivoFatura;
    private double valorFatura;
    private boolean pagamentoFeito;

    public long getNroFatura() {
        return nroFatura;
    }

    public void setNroFatura(long nroFatura) {
        this.nroFatura = nroFatura;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public MotivoFatura getMotivoFatura() {
        return motivoFatura;
    }

    public void setMotivoFatura(MotivoFatura motivoFatura) {
        this.motivoFatura = motivoFatura;
    }

    public double getValorFatura() {
        return valorFatura;
    }

    public void setValorFatura(double valorFatura) {
        this.valorFatura = valorFatura;
    }

    public boolean isPagamentoFeito() {
        return pagamentoFeito;
    }

    public void setPagamentoFeito(boolean pagamentoFeito) {
        this.pagamentoFeito = pagamentoFeito;
    }

}
