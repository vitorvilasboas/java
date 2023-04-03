package br.edu.cio.model;

import br.edu.cio.model.Funcionario;
import java.io.Serializable;

public class Destinatario implements Serializable{
    private int id;
    private int ativo;
    private String email;
    private String celular;
    private Funcionario funcionario;
    
    public int getAtivo() {
        return ativo;
    }
    
    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }
    
    public Destinatario(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
    
}
