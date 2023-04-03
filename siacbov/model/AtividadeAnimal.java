
package br.edu.cio.model;

import java.io.Serializable;

public class AtividadeAnimal implements Serializable{
    
    private int id;
    private String classificacao;
    private int variacao_passadas;
    private double passadas_variacao_perc;
    private Leitura leitura;
    private Animal animal;
    
    public AtividadeAnimal(){
        
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public double getPassadas_variacao_perc() {
        return passadas_variacao_perc;
    }

    public void setPassadas_variacao_perc(double passadas_variacao_perc) {
        this.passadas_variacao_perc = passadas_variacao_perc;
    }

    public Leitura getLeitura() {
        return leitura;
    }

    public void setLeitura(Leitura leitura) {
        this.leitura = leitura;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public int getVariacao_passadas() {
        return variacao_passadas;
    }

    public void setVariacao_passadas(int variacao_passadas) {
        this.variacao_passadas = variacao_passadas;
    }

}
