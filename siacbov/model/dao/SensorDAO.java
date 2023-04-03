package br.edu.cio.model.dao;

import br.edu.cio.model.Animal;
import br.edu.cio.model.Sensor;
import java.util.List;

public interface SensorDAO {
    public void inserirDAO(Sensor sensor);
    public void editarDAO(Sensor sensor);
    public void editarStatusDAO(int status, Sensor sensor, Animal animal);
    public Sensor buscarPorIdDAO(int id);
    public Sensor buscarPorCodigoDAO(String codigo);
    public Sensor buscarPorDescricaoDAO(String nome);
    public Sensor buscarPorNSerieDAO(String nserie);
    public Sensor buscarUltimoRegistroDAO();
    public List<Sensor> listarDAO();
    public boolean excluirDAO(int id);
    public List<Sensor> buscarDAO(String valorBusca);
}
