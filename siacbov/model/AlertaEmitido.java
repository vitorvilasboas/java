
package br.edu.cio.model;

import java.io.Serializable;

public class AlertaEmitido implements Serializable{
    
    private int id;
    private String data;
    private String hora;
    private Destinatario destinatario;
    private Alerta alerta;
    
    public AlertaEmitido(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Destinatario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Destinatario destinatario) {
        this.destinatario = destinatario;
    }

    public Alerta getAlerta() {
        return alerta;
    }

    public void setAlerta(Alerta alerta) {
        this.alerta = alerta;
    }
    
    
}
