package br.edu.cio.model;

import java.io.Serializable;

public class UndProcessamento implements Serializable{
    private int id;
    private String codigo;
    private String descricao;
    private String nserie;
    private String local_fixacao;
    private String tecnologia;
    private String observacao;
    private int qtd_sensores;
    
    public UndProcessamento(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNserie() {
        return nserie;
    }

    public void setNserie(String nserie) {
        this.nserie = nserie;
    }

    public String getLocal_fixacao() {
        return local_fixacao;
    }

    public void setLocal_fixacao(String local_fixacao) {
        this.local_fixacao = local_fixacao;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getQtd_sensores() {
        return qtd_sensores;
    }

    public void setQtd_sensores(int qtd_sensores) {
        this.qtd_sensores = qtd_sensores;
    }  
    
}
