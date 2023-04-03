

import java.io.Serializable;

public class Vaca implements Serializable{
    private String id;
    private int padrao_passadas;
    private int horas_duracao_cio;
    private int dias_anestro;
    private int percentual_aumento_atividade;
    
    public Vaca(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPadrao_passadas() {
        return padrao_passadas;
    }

    public void setPadrao_passadas(int padrao_passadas) {
        this.padrao_passadas = padrao_passadas;
    }

    public int getPercentual_aumento_atividade() {
        return percentual_aumento_atividade;
    }

    public void setPercentual_aumento_atividade(int percentual_aumento_atividade) {
        this.percentual_aumento_atividade = percentual_aumento_atividade;
    }

    public int getHoras_duracao_cio() {
        return horas_duracao_cio;
    }

    public void setHoras_duracao_cio(int horas_duracao_cio) {
        this.horas_duracao_cio = horas_duracao_cio;
    }

    public int getDias_anestro() {
        return dias_anestro;
    }

    public void setDias_anestro(int dias_anestro) {
        this.dias_anestro = dias_anestro;
    }

   
}
