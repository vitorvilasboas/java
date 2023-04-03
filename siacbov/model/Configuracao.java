package br.edu.cio.model;

import java.io.Serializable;

public class Configuracao implements Serializable{
    private String data_atual;
    private String hora_atual;
    private String dt_ult_captura;
    private String hr_ult_captura;
    private int id;
    private int intervalo_anestro;
    private int duracao_cio;
    private int intervalo_entre_leituras;//em horas (minimo 2)... intervalo de carregamento de leituras
    private int qtd_alertas; //qtd de alertas por ocorrencia,
    private int qtd_oco_pra_cio; //Qtd de ocorrencias para
    private int perc_min_atv_media; //base para classificação da atividade animal
    private int perc_min_atv_alta; //base para classificação da atividade animal
    private String envio_celular; //s ou n
    private String envio_email; //s ou n,
    private String msg_alerta_cio;
    private String msg_alerta_ocorrencia;
    private String tempo_entre_alertas; //intervalo entre os alertas,
    private String alerta_quando; //Identifica o cio ou Idenfifica ocorrencia e cio,
    private String reg_sensores;//especifica se o processo de gravação de leituras é manual ou automático
    private int min_anestro_normal;
    private int min_anestro_longo;
    private int min_anestro_mlongo;
    
    public Configuracao(){
    }
    
    public String getDt_ult_captura() {
        return dt_ult_captura;
    }

    public void setDt_ult_captura(String dt_ult_captura) {
        this.dt_ult_captura = dt_ult_captura;
    }

    public String getHr_ult_captura() {
        return hr_ult_captura;
    }

    public void setHr_ult_captura(String hr_ult_captura) {
        this.hr_ult_captura = hr_ult_captura;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIntervalo_anestro() {
        return intervalo_anestro;
    }

    public void setIntervalo_anestro(int intervalo_anestro) {
        this.intervalo_anestro = intervalo_anestro;
    }

    public int getDuracao_cio() {
        return duracao_cio;
    }
    
    
    public void setDuracao_cio(int duracao_cio) {
        this.duracao_cio = duracao_cio;
    }

    public int getIntervalo_entre_leituras() {
        return intervalo_entre_leituras;
    }

    public void setIntervalo_entre_leituras(int intervalo_entre_leituras) {
        this.intervalo_entre_leituras = intervalo_entre_leituras;
    }

    public int getQtd_alertas() {
        return qtd_alertas;
    }

    public void setQtd_alertas(int qtd_alertas) {
        this.qtd_alertas = qtd_alertas;
    }

    public int getQtd_oco_pra_cio() {
        return qtd_oco_pra_cio;
    }

    public void setQtd_oco_pra_cio(int qtd_oco_pra_cio) {
        this.qtd_oco_pra_cio = qtd_oco_pra_cio;
    }

    public String getEnvio_celular() {
        return envio_celular;
    }

    public void setEnvio_celular(String envio_celular) {
        this.envio_celular = envio_celular;
    }

    public String getEnvio_email() {
        return envio_email;
    }

    public void setEnvio_email(String envio_email) {
        this.envio_email = envio_email;
    }

    public String getMsg_alerta_cio() {
        return msg_alerta_cio;
    }

    public void setMsg_alerta_cio(String msg_alerta_cio) {
        this.msg_alerta_cio = msg_alerta_cio;
    }

    public String getMsg_alerta_ocorrencia() {
        return msg_alerta_ocorrencia;
    }

    public void setMsg_alerta_ocorrencia(String msg_alerta_ocorrencia) {
        this.msg_alerta_ocorrencia = msg_alerta_ocorrencia;
    }

    public String getTempo_entre_alertas() {
        return tempo_entre_alertas;
    }

    public void setTempo_entre_alertas(String tempo_entre_alertas) {
        this.tempo_entre_alertas = tempo_entre_alertas;
    }

    public String getAlerta_quando() {
        return alerta_quando;
    }

    public void setAlerta_quando(String alerta_quando) {
        this.alerta_quando = alerta_quando;
    }

    public String getReg_sensores() {
        return reg_sensores;
    }

    public void setReg_sensores(String reg_sensores) {
        this.reg_sensores = reg_sensores;
    }

    public int getPerc_min_atv_media() {
        return perc_min_atv_media;
    }

    public void setPerc_min_atv_media(int perc_min_atv_media) {
        this.perc_min_atv_media = perc_min_atv_media;
    }

    public int getPerc_min_atv_alta() {
        return perc_min_atv_alta;
    }

    public void setPerc_min_atv_alta(int perc_min_atv_alta) {
        this.perc_min_atv_alta = perc_min_atv_alta;
    }

    public String getData_atual() {
        return data_atual;
    }

    public void setData_atual(String data_atual) {
        this.data_atual = data_atual;
    }

    public String getHora_atual() {
        return hora_atual;
    }

    public void setHora_atual(String hora_atual) {
        this.hora_atual = hora_atual;
    }

    public int getMin_anestro_normal() {
        return min_anestro_normal;
    }

    public void setMin_anestro_normal(int min_anestro_normal) {
        this.min_anestro_normal = min_anestro_normal;
    }

    public int getMin_anestro_longo() {
        return min_anestro_longo;
    }

    public void setMin_anestro_longo(int min_anestro_longo) {
        this.min_anestro_longo = min_anestro_longo;
    }

    public int getMin_anestro_mlongo() {
        return min_anestro_mlongo;
    }

    public void setMin_anestro_mlongo(int min_anestro_mlongo) {
        this.min_anestro_mlongo = min_anestro_mlongo;
    }
    
    
}
