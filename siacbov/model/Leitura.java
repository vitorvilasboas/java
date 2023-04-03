package br.edu.cio.model;

import java.io.Serializable;


public class Leitura implements Serializable{
    private int id;
    private UndProcessamento central;
    private Sensor sensor;
    private String codigo_sensor;
    private String codigo_central;
    private int nLeitura;
    private int nPassadas;
    private String data;
    private String hora;
    private String observacao;
    private int intervalo_config;
    
    public Leitura(){
    }
    
    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo_sensor() {
        return codigo_sensor;
    }

    public void setCodigo_sensor(String codigo_sensor) {
        this.codigo_sensor = codigo_sensor;
    }

    public String getCodigo_central() {
        return codigo_central;
    }

    public void setCodigo_central(String codigo_central) {
        this.codigo_central = codigo_central;
    }

    public int getnLeitura() {
        return nLeitura;
    }

    public void setnLeitura(int nLeitura) {
        this.nLeitura = nLeitura;
    }

    public int getnPassadas() {
        return nPassadas;
    }

    public void setnPassadas(int nPassadas) {
        this.nPassadas = nPassadas;
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

    public UndProcessamento getCentral() {
        return central;
    }

    public void setCentral(UndProcessamento central) {
        this.central = central;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public int getIntervalo_config() {
        return intervalo_config;
    }

    public void setIntervalo_config(int intervalo_config) {
        this.intervalo_config = intervalo_config;
    }
    
}
