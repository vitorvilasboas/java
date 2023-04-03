package br.edu.cio.model.dao.mysql;

import br.edu.cio.model.Animal;
import br.edu.cio.model.AtividadeAnimal;
import br.edu.cio.model.GraficoAtividade;
import br.edu.cio.model.Leitura;
import br.edu.cio.model.dao.AnimalDAO;
import br.edu.cio.model.dao.LeituraDAO;
import br.edu.cio.model.dao.MetodosInferenciaCioDAO;
import br.edu.cio.util.Conexao;
import br.edu.cio.util.DAOFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MetodosInferenciaCioDAOMySQL implements MetodosInferenciaCioDAO{
    
    AnimalDAO acessoBDAnimal = DAOFactory.gerarAnimalDAO();
    LeituraDAO acessoBDLeitura = DAOFactory.gerarLeituraDAO();
    
    Connection conexao;
    public MetodosInferenciaCioDAOMySQL(){//construtor
        //Connection conexao = Conexao.getConnection();//start na conexão com banco de dados
    }
    
    @Override
    public void classificarAtividadeAnimalDAO(Leitura leitura, Animal animal, double perc_variacao_passadas, int variacao_passadas, String classificacao) { 
        try {
            conexao = Conexao.getConnection();
            String sql = "INSERT INTO  atividade_animal (atv_classificacao, atv_perc_variacao_passadas, atv_variacao_passadas, atv_animal_fk , atv_leitura_fk) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, classificacao);
            ps.setDouble(2, perc_variacao_passadas);
            ps.setInt(3, variacao_passadas);
            ps.setInt(4, animal.getId());
            ps.setInt(5, leitura.getId());
            ps.executeUpdate();
            try{//atualiza campo "info_status_atividade" da tabela info_animal
                sql = "UPDATE info_animal SET info_status_atividade=? where info_animal_fk=?";
                ps = conexao.prepareStatement(sql);
                ps.setString(1, classificacao);
                ps.setInt(2, animal.getId());
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(MetodosInferenciaCioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(MetodosInferenciaCioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CLASSIFICAÇÃO DE ATIVIDADE ANIMAL NÃO REALIZADA.", ex);
        }
    }

    @Override
    public AtividadeAnimal buscarAtividadeAnimalPorIdLeituraDAO(int idLeitura) {
        AtividadeAnimal atv = new AtividadeAnimal();
        try {
            conexao = Conexao.getConnection();
            String sql = "SELECT * FROM atividade_animal WHERE atv_leitura_fk=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idLeitura);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                atv.setId(rs.getInt("atv_id"));
                atv.setClassificacao(rs.getString("atv_classificacao"));
                atv.setPassadas_variacao_perc(rs.getInt("atv_perc_variacao_passadas"));
                atv.setVariacao_passadas(rs.getInt("atv_variacao_passadas"));
                atv.setLeitura(acessoBDLeitura.buscarPorIdDAO(rs.getInt("atv_leitura_fk")));
                atv.setAnimal(acessoBDAnimal.buscarPorIdDAO(rs.getInt("atv_animal_fk")));
            } else {
                atv = null;
            }
            ps.close();
            rs.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(LeituraDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return atv;
    }

    @Override
    public AtividadeAnimal buscarAtividadeAnimalPorId(int idAtividade) {
        AtividadeAnimal atv = new AtividadeAnimal();
        try {
            conexao = Conexao.getConnection();
            String sql = "SELECT * FROM atividade_animal WHERE atv_id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idAtividade);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                atv.setId(rs.getInt("atv_id"));
                atv.setClassificacao(rs.getString("atv_classificacao"));
                atv.setPassadas_variacao_perc(rs.getInt("atv_perc_variacao_passadas"));
                atv.setVariacao_passadas(rs.getInt("atv_variacao_passadas"));
                atv.setLeitura(acessoBDLeitura.buscarPorIdDAO(rs.getInt("atv_leitura_fk")));
                atv.setAnimal(acessoBDAnimal.buscarPorIdDAO(rs.getInt("atv_animal_fk")));
            } else {
                atv = null;
            }
            ps.close();
            rs.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(LeituraDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return atv;
    }

    @Override
    public AtividadeAnimal buscarPorGraficoAtividade(GraficoAtividade ga) {
        AtividadeAnimal atv = new AtividadeAnimal();
        try {
            conexao = Conexao.getConnection();
            String sql = "SELECT * FROM atividade_animal WHERE atv_animal_fk=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, ga.getIdAnimal());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                atv.setId(rs.getInt("atv_id"));
                atv.setClassificacao(rs.getString("atv_classificacao"));
                atv.setPassadas_variacao_perc(rs.getInt("atv_perc_variacao_passadas"));
                atv.setVariacao_passadas(rs.getInt("atv_variacao_passadas"));
                atv.setLeitura(acessoBDLeitura.buscarPorIdDAO(rs.getInt("atv_leitura_fk")));
                atv.setAnimal(acessoBDAnimal.buscarPorIdDAO(rs.getInt("atv_animal_fk")));
            } else {
                atv = null;
            }
            ps.close();
            rs.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(LeituraDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return atv;
    }

    @Override
    public List<AtividadeAnimal> listarPorIdAnimal(int idAnimal) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            List<AtividadeAnimal> atividades = new ArrayList<>();
            String sql = "SELECT * FROM atividade_animal WHERE atv_animal_fk=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idAnimal);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                atividades = null;
            } else {
                do {
                    AtividadeAnimal atv = new AtividadeAnimal();
                    atv.setId(rs.getInt("atv_id"));
                    atv.setVariacao_passadas(rs.getInt("atv_variacao_passadas"));
                    atv.setPassadas_variacao_perc(rs.getInt("atv_perc_variacao_passadas"));
                    atv.setClassificacao(rs.getString("atv_classificacao"));
                    atv.setAnimal(acessoBDAnimal.buscarPorIdDAO(rs.getInt("atv_animal_fk")));
                    atv.setLeitura(acessoBDLeitura.buscarPorIdDAO(rs.getInt("atv_leitura_fk")));
                    atividades.add(atv);
                } while (rs.next());
            }
            ps.close();
            rs.close();
            conexao.close();
            return atividades;
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE ATIVIDADE DO ANIMAL NÃO REALIZADA.", ex);
        }
    }
    
}
