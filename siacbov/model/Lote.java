package br.edu.cio.model;

import java.io.Serializable;

public class Lote implements Serializable{
    private int id;
    private String codigo;
    private String descricao;
    private String tipo;
    private String dt_cadastro;
    private String observacao;
    private Propriedade propriedade;
    private AreaPastagem pasto;
    
    public Lote(){
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDt_cadastro() {
        return dt_cadastro;
    }

    public void setDt_cadastro(String dt_cadastro) {
        this.dt_cadastro = dt_cadastro;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Propriedade getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(Propriedade propriedade) {
        this.propriedade = propriedade;
    }

    public AreaPastagem getPasto() {
        return pasto;
    }

    public void setPasto(AreaPastagem pasto) {
        this.pasto = pasto;
    }
    
    
}
