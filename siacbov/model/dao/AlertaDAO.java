package br.edu.cio.model.dao;

import br.edu.cio.model.Alerta;
import br.edu.cio.model.Animal;
import java.util.List;

public interface AlertaDAO {
    public void cadastrarDAO(Alerta alerta);
    public Alerta buscarUltimoDoAnimal(int idAnimal);
    public Alerta buscarPorIdDAO(int id);
    public List<Alerta> buscarDAO(String valorBusca);
}
