package br.edu.cio.model.dao;

import br.edu.cio.model.Funcionario;
import br.edu.cio.model.Lote;
import br.edu.cio.model.Usuario;
import java.util.List;

public interface FuncionarioDAO {
   public void cadastrarDAO(Funcionario funcionario);
   public void editarDAO(Funcionario funcionario);
     public boolean excluirDAO(int id);
   public Funcionario buscarPorIdDAO(int id);
   public List<Funcionario> buscarDAO(String valorBusca);
   public List<Funcionario> listarDAO();
   public List<Funcionario> listarPorPropriedadeDAO(int idPropriedade);
   
    public Funcionario buscarUltimoRegistroDAO();
    public Funcionario autenticarDAO(String cpf, String email);
}
