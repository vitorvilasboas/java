package br.edu.cio.model;

import java.io.Serializable;

public class Cio implements Serializable{

    private int id;
    private String codigo;
    private String data_registro;
    private String hora_registro;
    private String status;
    private String data_ultima_alteracao;
    private String hora_ultima_alteracao;
    private String metodo_id;
    private String metodo_registro;
    private String data_inicio;
    private String hora_inicio;
    private String data_previsao_termino;
    private String hora_previsao_termino;
    private String data_termino;
    private String hora_termino;
    private int duracao;
    private String observacao;
    private Animal animal;
    private Leitura leitura;
    private AtividadeAnimal atividadeAnimal;
    
    public Cio(){
        
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData_registro() {
        return data_registro;
    }

    public void setData_registro(String data_registro) {
        this.data_registro = data_registro;
    }

    public String getHora_registro() {
        return hora_registro;
    }

    public void setHora_registro(String hora_registro) {
        this.hora_registro = hora_registro;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData_ultima_alteracao() {
        return data_ultima_alteracao;
    }

    public void setData_ultima_alteracao(String data_ultima_alteracao) {
        this.data_ultima_alteracao = data_ultima_alteracao;
    }

    public String getHora_ultima_alteracao() {
        return hora_ultima_alteracao;
    }

    public void setHora_ultima_alteracao(String hora_ultima_alteracao) {
        this.hora_ultima_alteracao = hora_ultima_alteracao;
    }

    public String getMetodo_id() {
        return metodo_id;
    }

    public void setMetodo_id(String metodo_id) {
        this.metodo_id = metodo_id;
    }

    public String getMetodo_registro() {
        return metodo_registro;
    }

    public void setMetodo_registro(String metodo_registro) {
        this.metodo_registro = metodo_registro;
    }

    public String getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(String data_inicio) {
        this.data_inicio = data_inicio;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getData_previsao_termino() {
        return data_previsao_termino;
    }

    public void setData_previsao_termino(String data_previsao_termino) {
        this.data_previsao_termino = data_previsao_termino;
    }

    public String getHora_previsao_termino() {
        return hora_previsao_termino;
    }

    public void setHora_previsao_termino(String hora_previsao_termino) {
        this.hora_previsao_termino = hora_previsao_termino;
    }

    public String getData_termino() {
        return data_termino;
    }

    public void setData_termino(String data_termino) {
        this.data_termino = data_termino;
    }

    public String getHora_termino() {
        return hora_termino;
    }

    public void setHora_termino(String hora_termino) {
        this.hora_termino = hora_termino;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Leitura getLeitura() {
        return leitura;
    }

    public void setLeitura(Leitura leitura) {
        this.leitura = leitura;
    }

    public AtividadeAnimal getAtividadeAnimal() {
        return atividadeAnimal;
    }

    public void setAtividadeAnimal(AtividadeAnimal atividadeAnimal) {
        this.atividadeAnimal = atividadeAnimal;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
        
}
