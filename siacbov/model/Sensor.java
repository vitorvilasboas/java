package br.edu.cio.model;

import java.io.Serializable;

public class Sensor implements Serializable{
    private int id;
    private String codigo;
    private String descricao;
    private String nserie;
    private String tipo;
    private String medicao;
    private int alcance;
    private String tecnologia;
    private int ativo;
    private UndProcessamento central;
    
    public Sensor(){
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMedicao() {
        return medicao;
    }

    public void setMedicao(String medicao) {
        this.medicao = medicao;
    }

    public int getAlcance() {
        return alcance;
    }

    public void setAlcance(int alcance) {
        this.alcance = alcance;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public UndProcessamento getCentral() {
        return central;
    }

    public void setCentral(UndProcessamento central) {
        this.central = central;
    }

    
    
    
}
