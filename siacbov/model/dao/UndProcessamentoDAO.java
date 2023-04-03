package br.edu.cio.model.dao;

import br.edu.cio.model.AreaPastagem;
import br.edu.cio.model.UndProcessamento;
import java.util.List;

public interface UndProcessamentoDAO {
   public void inserirDAO(UndProcessamento undProcessamento);
    public boolean excluirDAO(int id);
   public void editarDAO(UndProcessamento undProcessamento);
   public UndProcessamento buscarPorIdDAO(int id);
   
   public UndProcessamento buscarPorDescricaoDAO(String descricao);
   public UndProcessamento buscarPorCodigoDAO(String codigo);
   public UndProcessamento buscarPorNSerieDAO(String nserie);
   public UndProcessamento buscarUltimoRegistroDAO();
   public List<UndProcessamento> listarDAO();
   public List<UndProcessamento> buscarDAO(String valorBusca);
}
