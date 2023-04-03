
package br.edu.cio.model.dao;

import br.edu.cio.model.Usuario;
import java.util.List;

public interface UsuarioDAO {
   public void inserirDAO(Usuario usuario);
   public void editarDAO(Usuario usuario);
   public void excluirDAO(int id);
   public Usuario buscarDAO(int id);
   public List<Usuario> listarDAO();
   public Usuario autenticarDAO(String login, String senha);
}
