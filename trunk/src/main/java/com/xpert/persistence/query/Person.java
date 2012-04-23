/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xpert.persistence.query;

import javax.persistence.Id;

/**
 *
 * @author Ayslan
 */
public class Person {
    
    private String nome;
    private String cpf;

    @Id
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    
}
