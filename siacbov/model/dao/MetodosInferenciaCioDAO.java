package br.edu.cio.model.dao;

import br.edu.cio.model.Animal;
import br.edu.cio.model.AtividadeAnimal;
import br.edu.cio.model.GraficoAtividade;
import br.edu.cio.model.Leitura;
import java.util.List;

public interface MetodosInferenciaCioDAO {
    public void classificarAtividadeAnimalDAO(Leitura leitura, Animal animal, double perc_variacao_passadas, int variacao_passadas, String classificacao);
    public AtividadeAnimal buscarAtividadeAnimalPorIdLeituraDAO(int idLeitura);
    public AtividadeAnimal buscarAtividadeAnimalPorId(int idAtividade);
    public AtividadeAnimal buscarPorGraficoAtividade(GraficoAtividade ga);
    public List<AtividadeAnimal> listarPorIdAnimal(int idAnimal);
}
