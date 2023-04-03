
package br.edu.cio.model.dao;

import br.edu.cio.model.Animal;
import br.edu.cio.model.AreaPastagem;
import br.edu.cio.model.Propriedade;
import java.util.List;

public interface AnimalDAO {
   public void cadastrarDAO(Animal animal);
   public boolean excluirDAO(int id);
   public void editarDAO(Animal animal);
   public Animal buscarPorIdDAO(int id);
   public List<Animal> buscarDAO(String valorBusca);
   public List<Animal> listarPorPropriedadeDAO(int idPropriedade);
   public Animal buscarUltimoRegistroDAO();
   public Animal buscarPorIdSensorDAO(int idSensor);
   public List<Animal> listarDAO();
   public List<Animal> carregarDAO();
}