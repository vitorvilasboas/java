
package br.edu.cio.model;

import java.io.Serializable;

public class AnimalInfo implements Serializable{
    private int id;
    private int idade;
    private String dt_ini_ult_cio;
    private String dt_fim_ult_cio;
    private String hr_ini_ult_cio;
    private String hr_fim_ult_cio;
    private String dt_prev_ini_prox_cio;
    private String hr_prev_ini_prox_cio;
    private int intervalo_padrao_anestro;
    private String tempo_atual_anestro;
    private int media_passos_hora;
    private String status_atividade;
    private String oco_reprodutiva;
    private String oco_produtiva;
    private int aprox_cio;
    private int sob_alerta;
    private int status_cio;
    private int dias_em_anestro;
    private int duracao_media_cio;
    private Animal animal;
    
    public AnimalInfo(){
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getDt_ini_ult_cio() {
        return dt_ini_ult_cio;
    }

    public void setDt_ini_ult_cio(String dt_ini_ult_cio) {
        this.dt_ini_ult_cio = dt_ini_ult_cio;
    }

    public String getDt_fim_ult_cio() {
        return dt_fim_ult_cio;
    }

    public void setDt_fim_ult_cio(String dt_fim_ult_cio) {
        this.dt_fim_ult_cio = dt_fim_ult_cio;
    }

    public String getDt_prev_ini_prox_cio() {
        return dt_prev_ini_prox_cio;
    }

    public void setDt_prev_ini_prox_cio(String dt_prev_ini_prox_cio) {
        this.dt_prev_ini_prox_cio = dt_prev_ini_prox_cio;
    }

    public int getIntervalo_padrao_anestro() {
        return intervalo_padrao_anestro;
    }

    public void setIntervalo_padrao_anestro(int intervalo_padrao_anestro) {
        this.intervalo_padrao_anestro = intervalo_padrao_anestro;
    }

    public String getTempo_atual_anestro() {
        return tempo_atual_anestro;
    }

    public void setTempo_atual_anestro(String tempo_atual_anestro) {
        this.tempo_atual_anestro = tempo_atual_anestro;
    }

    public int getMedia_passos_hora() {
        return media_passos_hora;
    }

    public void setMedia_passos_hora(int media_passos_hora) {
        this.media_passos_hora = media_passos_hora;
    }

    public String getStatus_atividade() {
        return status_atividade;
    }

    public void setStatus_atividade(String status_atividade) {
        this.status_atividade = status_atividade;
    }

    public String getOco_reprodutiva() {
        return oco_reprodutiva;
    }

    public void setOco_reprodutiva(String oco_reprodutiva) {
        this.oco_reprodutiva = oco_reprodutiva;
    }

    public String getOco_produtiva() {
        return oco_produtiva;
    }

    public void setOco_produtiva(String oco_produtiva) {
        this.oco_produtiva = oco_produtiva;
    }

    public int getAprox_cio() {
        return aprox_cio;
    }

    public void setAprox_cio(int aprox_cio) {
        this.aprox_cio = aprox_cio;
    }

    public int getStatus_cio() {
        return status_cio;
    }

    public void setStatus_cio(int status_cio) {
        this.status_cio = status_cio;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getHr_ini_ult_cio() {
        return hr_ini_ult_cio;
    }

    public void setHr_ini_ult_cio(String hr_ini_ult_cio) {
        this.hr_ini_ult_cio = hr_ini_ult_cio;
    }

    public String getHr_fim_ult_cio() {
        return hr_fim_ult_cio;
    }

    public void setHr_fim_ult_cio(String hr_fim_ult_cio) {
        this.hr_fim_ult_cio = hr_fim_ult_cio;
    }

    public String getHr_prev_ini_prox_cio() {
        return hr_prev_ini_prox_cio;
    }

    public void setHr_prev_ini_prox_cio(String hr_prev_ini_prox_cio) {
        this.hr_prev_ini_prox_cio = hr_prev_ini_prox_cio;
    }

    public int getDias_em_anestro() {
        return dias_em_anestro;
    }

    public void setDias_em_anestro(int dias_em_anestro) {
        this.dias_em_anestro = dias_em_anestro;
    }

    public int getSob_alerta() {
        return sob_alerta;
    }

    public void setSob_alerta(int sob_alerta) {
        this.sob_alerta = sob_alerta;
    }

    public int getDuracao_media_cio() {
        return duracao_media_cio;
    }

    public void setDuracao_media_cio(int duracao_media_cio) {
        this.duracao_media_cio = duracao_media_cio;
    }
    
    
}
