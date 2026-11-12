/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.contas.model;

/**
 *
 * @author molsousa
 */
public class EnderecoEspecifico {

    private long nroEndereco;
    private String complemento;
    private Endereco endereco;

    public EnderecoEspecifico(long nroEndereco, String complemento) {
        this.nroEndereco = nroEndereco;
        this.complemento = complemento;
    }

    public EnderecoEspecifico(long nroEndereco, String complemento, Endereco endereco) {
        this.nroEndereco = nroEndereco;
        this.complemento = complemento;
        this.endereco = endereco;
    }

    public long getNroEndereco() {
        return nroEndereco;
    }

    public void setNroEndereco(long nroEndereco) {
        this.nroEndereco = nroEndereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

}
