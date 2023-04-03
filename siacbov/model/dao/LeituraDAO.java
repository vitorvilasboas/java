package br.edu.cio.model.dao;

import br.edu.cio.model.Leitura;
import java.util.List;

public interface LeituraDAO {
    public Leitura buscarDAO(Leitura l);
    public void inserirDAO(Leitura l);
    public Leitura buscarPorIdDAO(int id);
    public List<Leitura> listarPorIdSensor(int idSensor);
    public List<Leitura> listarPorIdSensorEDatas(int idSensor, String dtInicio, String dtFim);
}
