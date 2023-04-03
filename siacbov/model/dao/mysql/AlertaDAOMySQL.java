package br.edu.cio.model.dao.mysql;

import br.edu.cio.model.Alerta;
import br.edu.cio.model.Animal;
import br.edu.cio.model.dao.AlertaDAO;
import br.edu.cio.model.dao.AnimalDAO;
import br.edu.cio.model.dao.CioDAO;
import br.edu.cio.model.dao.LeituraDAO;
import br.edu.cio.model.dao.MetodosInferenciaCioDAO;
import br.edu.cio.util.Conexao;
import br.edu.cio.util.DAOFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlertaDAOMySQL implements AlertaDAO{
    
    AnimalDAO acessoBDAnimal = DAOFactory.gerarAnimalDAO();
    LeituraDAO acessoBDLeitura = DAOFactory.gerarLeituraDAO();
    CioDAO acessoBDCio = DAOFactory.gerarCioDAO();
    MetodosInferenciaCioDAO acessoBDmic = DAOFactory.gerarMetodosInferenciaCioDAO();
    
    Connection conexao;
    public AlertaDAOMySQL(){//construtor
        //conexao = Conexao.getConnection();//start na conexão com banco de dados
    }
    @Override
    public void cadastrarDAO(Alerta alerta) {
        try {
            conexao = Conexao.getConnection();
            String sql = "INSERT INTO  alerta (ale_numero, ale_msg, ale_animal_fk, ale_leitura_fk, ale_cio_fk, ale_atividade_fk) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, alerta.getNumero());
            ps.setString(2, alerta.getMsg());
            ps.setInt(3, alerta.getAnimal().getId());
            ps.setInt(4, alerta.getLeitura().getId());
            ps.setInt(5, alerta.getCio().getId());
            ps.setInt(6, alerta.getAtv_animal().getId());
            ps.executeUpdate();
            System.out.println("!!! ALERTA DE CÓDIGO "+alerta.getNumero()+" REGISTRADO COM SUCESSO.");
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(LeituraDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: REGISTRO DE ALERTA NÃO REALIZADO.", ex);
        }
    }

    @Override
    public Alerta buscarUltimoDoAnimal(int idAnimal) {
        try {
            conexao = Conexao.getConnection();
            Alerta alerta = new Alerta();
            String  sql = "SELECT * FROM alerta WHERE ale_animal_fk=? ORDER BY ale_id DESC LIMIT 1";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idAnimal);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                alerta = null;
            else {
                alerta.setId(rs.getInt("ale_id"));
                alerta.setNumero(rs.getInt("ale_numero"));
                alerta.setMsg(rs.getString("ale_msg"));
                alerta.setAnimal(acessoBDAnimal.buscarPorIdDAO(rs.getInt("ale_animal_fk")));
                alerta.setLeitura(acessoBDLeitura.buscarPorIdDAO(rs.getInt("ale_leitura_fk")));
                alerta.setAtv_animal(acessoBDmic.buscarAtividadeAnimalPorId(rs.getInt("ale_atividade_fk")));
                alerta.setCio(acessoBDCio.buscarPorIdDAO(rs.getInt("ale_cio_fk")));
            }          
            ps.close();
            rs.close();
            conexao.close();
            return alerta;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: BUSCA DE ALERTA NÃO REALIZADA.", ex);
        }
    }

    @Override
    public List<Alerta> buscarDAO(String valorBusca) {
        SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
        try {
            conexao = Conexao.getConnection();
            List<Alerta> alertasBusca = new ArrayList<>();
            String sql;
            PreparedStatement ps;
            if ("".equals(valorBusca)) {
                sql = "SELECT * FROM alerta ORDER BY ale_id DESC";
                ps = conexao.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM alerta WHERE ale_animal_fk=? ORDER BY ale_id";
                ps = conexao.prepareStatement(sql);
                ps.setString(1, "%" + valorBusca + "%");
            }
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                alertasBusca = null;
            else {
                do {
                    Alerta alerta = new Alerta();
                    alerta.setId(rs.getInt("ale_id"));
                    alerta.setNumero(rs.getInt("ale_numero"));
                    alerta.setMsg(rs.getString("ale_msg"));
                    alerta.setAnimal(acessoBDAnimal.buscarPorIdDAO(rs.getInt("ale_animal_fk")));
                    alerta.setLeitura(acessoBDLeitura.buscarPorIdDAO(rs.getInt("ale_leitura_fk")));
                    alerta.setAtv_animal(acessoBDmic.buscarAtividadeAnimalPorId(rs.getInt("ale_atividade_fk")));
                    alerta.setCio(acessoBDCio.buscarPorIdDAO(rs.getInt("ale_cio_fk")));
                    alertasBusca.add(alerta);                
                } while (rs.next());
            }         
            ps.close();
            rs.close();
            conexao.close();
            return alertasBusca;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: BUSCA DE ALERTAS NÃO REALIZADA.", ex);
        }
    }

    @Override
    public Alerta buscarPorIdDAO(int id) {
        try {
            conexao = Conexao.getConnection();
            Alerta alerta = new Alerta();
            String  sql = "SELECT * FROM alerta WHERE ale_id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                alerta = null;
            else {
                alerta.setId(rs.getInt("ale_id"));
                alerta.setNumero(rs.getInt("ale_numero"));
                alerta.setMsg(rs.getString("ale_msg"));
                alerta.setAnimal(acessoBDAnimal.buscarPorIdDAO(rs.getInt("ale_animal_fk")));
                alerta.setLeitura(acessoBDLeitura.buscarPorIdDAO(rs.getInt("ale_leitura_fk")));
                alerta.setAtv_animal(acessoBDmic.buscarAtividadeAnimalPorId(rs.getInt("ale_atividade_fk")));
                alerta.setCio(acessoBDCio.buscarPorIdDAO(rs.getInt("ale_cio_fk")));
            }          
            ps.close();
            rs.close();
            conexao.close();
            return alerta;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: BUSCA DE ALERTA NÃO REALIZADA.", ex);
        }
    }
    
}
