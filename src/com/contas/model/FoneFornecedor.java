/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contas.model;

/**
 *
 * @author molsousa
 */
public class FoneFornecedor {

    private int idFoneFornecedor;
    private String nroFone;
    private DDD ddd;
    private DDI ddi;

    public int getIdFoneFornecedor() {
        return idFoneFornecedor;
    }

    public void setIdFoneFornecedor(int idFoneFornecedor) {
        this.idFoneFornecedor = idFoneFornecedor;
    }

    public String getNroFone() {
        return nroFone;
    }

    public void setNroFone(String nroFone) {
        this.nroFone = nroFone;
    }

    public DDD getDdd() {
        return ddd;
    }

    public void setDdd(DDD ddd) {
        this.ddd = ddd;
    }

    public DDI getDdi() {
        return ddi;
    }

    public void setDdi(DDI ddi) {
        this.ddi = ddi;
    }

}
