
package br.edu.cio.model;

import java.io.Serializable;

public class GraficoAtividade implements Serializable {
    private int idAnimal;
    private String intervalo;
    private String escala;
    private String inicioPeriodo;
    private String fimPeriodo;
    
    public GraficoAtividade(){
    
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(String intervalo) {
        this.intervalo = intervalo;
    }

    public String getEscala() {
        return escala;
    }

    public void setEscala(String escala) {
        this.escala = escala;
    }

    public String getInicioPeriodo() {
        return inicioPeriodo;
    }

    public void setInicioPeriodo(String inicioPeriodo) {
        this.inicioPeriodo = inicioPeriodo;
    }

    public String getFimPeriodo() {
        return fimPeriodo;
    }

    public void setFimPeriodo(String fimPeriodo) {
        this.fimPeriodo = fimPeriodo;
    }
    
}
