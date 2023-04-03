package br.edu.cio.model.dao;

import br.edu.cio.model.AlertaEmitido;
import java.util.List;

public interface AlertaEmitidoDAO {
    public void cadastrarDAO(AlertaEmitido alem);
    public AlertaEmitido buscarPorId(int id);
    public AlertaEmitido buscarPorIdDestinatario(int idDestinatario);
    public AlertaEmitido buscarPorIdAlerta(int idAlerta);
    public List<AlertaEmitido> buscarDAO();
}
