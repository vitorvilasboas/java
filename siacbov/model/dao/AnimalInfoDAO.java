package br.edu.cio.model.dao;

import br.edu.cio.model.AnimalInfo;
import br.edu.cio.model.Configuracao;
import java.util.List;

public interface AnimalInfoDAO {
   public void cadastrarDAO(AnimalInfo infoAnimal);
   public void excluirDAO(int id);
   public void editarDAO(AnimalInfo animal);
   public AnimalInfo buscarPorIdDAO(int id);
   public AnimalInfo buscarDAO(String valorBusca);
   public List<AnimalInfo> listarDAO();
   public AnimalInfo buscarPorIdAnimalDAO(int idAnimal);
   public void atualizarTempoAnestroDAO(AnimalInfo infoAnimal, int diasAnestro);
   public void atualizarMediaPassosHoraDAO(AnimalInfo animal);
   public void atualizarSobAlertaDAO(AnimalInfo animal);
   public void atualizarDAO(AnimalInfo infoAnimal, Configuracao config);
}
