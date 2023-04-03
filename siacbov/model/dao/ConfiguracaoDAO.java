package br.edu.cio.model.dao;

import br.edu.cio.model.Configuracao;

public interface ConfiguracaoDAO {
    public Configuracao carregarDAO(int id);
    public void atualizarDAO(Configuracao config);
}
