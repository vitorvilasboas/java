package br.edu.cio.model.dao;

import br.edu.cio.model.Propriedade;
import java.util.List;

public interface PropriedadeDAO {
    public void cadastrarDAO(Propriedade propriedade);
    public void editarDAO(Propriedade propriedade);
    public boolean excluirDAO(int id);
    public List<Propriedade> buscarDAO(String valorBusca);
    public Propriedade buscarPorIdDAO(int id);
    public Propriedade buscarUltimoRegistroDAO();
    public List<Propriedade> listarDAO();
}

