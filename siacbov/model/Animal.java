package br.edu.cio.model;

import java.io.Serializable;

public class Animal implements Serializable { 
    private int id;
    private String codigo;
    private int rgn;
    private int id_mae;
    private int id_pai;
    private double peso_nascimento;
    private double peso_cadastro;
    private double peso_atual;
    private double preco_compra;
    private int monitorando;//sim ou não
    private String tipo;// boi para engorda...
    private String tipo_geracao;//normal(boi ou touro)...
    private String estado_atual;// inseminada...
    private String apelido;
    private String sexo;
    private String grau_sangue;
    private String raca;
    private String pelagem;
    private String imagem;
    private String dt_nascimento;
    private String dt_cadastro;
    private String hr_cadastro;
    private String observacao;
    private Lote lote;
    private AreaPastagem pasto;
    private Propriedade propriedade;
    private Sensor sensor;
    
    public Animal() {  
    }

    public String getHr_cadastro() {
        return hr_cadastro;
    }

    public void setHr_cadastro(String hr_cadastro) {
        this.hr_cadastro = hr_cadastro;
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

    public int getRgn() {
        return rgn;
    }

    public void setRgn(int rgn) {
        this.rgn = rgn;
    }

    public int getId_mae() {
        return id_mae;
    }

    public void setId_mae(int id_mae) {
        this.id_mae = id_mae;
    }

    public int getId_pai() {
        return id_pai;
    }

    public void setId_pai(int id_pai) {
        this.id_pai = id_pai;
    }

    public double getPeso_nascimento() {
        return peso_nascimento;
    }

    public void setPeso_nascimento(double peso_nascimento) {
        this.peso_nascimento = peso_nascimento;
    }

    public double getPeso_cadastro() {
        return peso_cadastro;
    }

    public void setPeso_cadastro(double peso_cadastro) {
        this.peso_cadastro = peso_cadastro;
    }

    public double getPeso_atual() {
        return peso_atual;
    }

    public void setPeso_atual(double peso_atual) {
        this.peso_atual = peso_atual;
    }

    public double getPreco_compra() {
        return preco_compra;
    }

    public void setPreco_compra(double preco_compra) {
        this.preco_compra = preco_compra;
    }

    public int getMonitorando() {
        return monitorando;
    }

    public void setMonitorando(int monitorando) {
        this.monitorando = monitorando;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo_geracao() {
        return tipo_geracao;
    }

    public void setTipo_geracao(String tipo_geracao) {
        this.tipo_geracao = tipo_geracao;
    }

    public String getEstado_atual() {
        return estado_atual;
    }

    public void setEstado_atual(String estado_atual) {
        this.estado_atual = estado_atual;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getGrau_sangue() {
        return grau_sangue;
    }

    public void setGrau_sangue(String grau_sangue) {
        this.grau_sangue = grau_sangue;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getPelagem() {
        return pelagem;
    }

    public void setPelagem(String pelagem) {
        this.pelagem = pelagem;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getDt_nascimento() {
        return dt_nascimento;
    }

    public void setDt_nascimento(String dt_nascimento) {
        this.dt_nascimento = dt_nascimento;
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

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public AreaPastagem getPasto() {
        return pasto;
    }

    public void setPasto(AreaPastagem pasto) {
        this.pasto = pasto;
    }

    public Propriedade getPropriedade() {
        return propriedade;
    }

    public void setPropriedade(Propriedade propriedade) {
        this.propriedade = propriedade;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }


    
}
