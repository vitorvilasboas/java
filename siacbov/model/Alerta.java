package br.edu.cio.model;

import java.io.Serializable;

public class Alerta implements Serializable{
    
    private int id;
    private int numero;
    private String msg;
    private Animal animal;
    private Leitura leitura;
    private AtividadeAnimal atv_animal;
    private Cio cio;
    
    public Alerta(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public AtividadeAnimal getAtv_animal() {
        return atv_animal;
    }

    public void setAtv_animal(AtividadeAnimal atv_animal) {
        this.atv_animal = atv_animal;
    }

    public Cio getCio() {
        return cio;
    }

    public void setCio(Cio cio) {
        this.cio = cio;
    }
    
    
}
