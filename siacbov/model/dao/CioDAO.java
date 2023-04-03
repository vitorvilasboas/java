package br.edu.cio.model.dao;

import br.edu.cio.model.Animal;
import br.edu.cio.model.Cio;
import br.edu.cio.model.LogCio;
import java.util.List;

public interface CioDAO {
    public void cadastrarDAO(Cio cio);
    public void editarDAO(Cio cio);
    public void editarManualDAO(Cio cio);
    public void cadastrarAutomaticoDAO(Cio cio);
    public Cio buscarUltimoRegistroDAO();
    public Cio buscarUltimoDoAnimalDAO(int idAnimal);
    public Cio buscarPorIdDAO(int idCio);
    public List<Cio> buscarPendentesAnimalDAO(int idAnimal);
    public void editarAutomaticoDAO(Cio cio);
    public void avaliarDAO(Cio cio);
    public void inserirLogDAO(LogCio lc);
    public List<Cio> listarPendentesDAO();
    public List<Cio> buscarDAO(String valorBusca);
    public void inserirPrevisaoCio(String data, String hora, int idAnimal);
}
