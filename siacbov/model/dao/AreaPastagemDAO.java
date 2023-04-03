package br.edu.cio.model.dao;

import br.edu.cio.model.AreaPastagem;
import java.util.List;

public interface AreaPastagemDAO {
    public void cadastrarDAO(AreaPastagem pasto);
    public void editarDAO(AreaPastagem pasto);
    public boolean excluirDAO(int id);
    public List<AreaPastagem> buscarDAO(String valorBusca);
    public AreaPastagem buscarPorIdDAO(int id);
    public AreaPastagem buscarUltimoRegistroDAO();
    public List<AreaPastagem> listarDAO();
    public List<AreaPastagem> listarPorPropriedadeDAO(int idPropriedade);  
}
