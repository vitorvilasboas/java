package br.edu.cio.model;

import java.io.Serializable;

public class LogCio implements Serializable {
    private int id;
    private String data;
    private String hora;
    private String operacao;
    private String status_cio;
    private String descricao;
    private Funcionario funcionario;
    private Cio cio;
    
    public LogCio(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getStatus_cio() {
        return status_cio;
    }

    public void setStatus_cio(String status_cio) {
        this.status_cio = status_cio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Cio getCio() {
        return cio;
    }

    public void setCio(Cio cio) {
        this.cio = cio;
    }
    
    
}
