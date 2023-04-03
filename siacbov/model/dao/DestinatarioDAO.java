package br.edu.cio.model.dao;

import br.edu.cio.model.Destinatario;
import br.edu.cio.model.Lote;
import java.util.List;

public interface DestinatarioDAO {

    public void inserirDAO(Destinatario destinatario);

    public void editarDAO(Destinatario destinatario);

    public boolean excluirDAO(int id);

    public Destinatario buscarPorIdDAO(int id);

    public Destinatario buscarPorEmailDAO(String email);

    public List<Destinatario> listarDAO();

    public List<Destinatario> buscarDAO(String valorBusca);

    public List<Destinatario> listarPorFuncionario(int idFuncionario);
}
