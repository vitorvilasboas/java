package br.edu.cio.model.dao;

import br.edu.cio.model.Animal;
import br.edu.cio.model.Lote;
import java.util.List;

public interface LoteDAO {

    public void cadastrarDAO(Lote lote);

    public void editarDAO(Lote lote);

    public boolean excluirDAO(int id);

    public Lote buscarPorIdDAO(int id);

    public List<Lote> buscarDAO(String valorBusca);

    public List<Lote> listarDAO();

    public List<Lote> listarPorPropriedadeDAO(int idPropriedade);

    public Lote buscarUltimoRegistroDAO();
}
