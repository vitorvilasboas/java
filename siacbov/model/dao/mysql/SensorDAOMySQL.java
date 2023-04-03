package br.edu.cio.model.dao.mysql;

import br.edu.cio.model.Animal;
import br.edu.cio.model.Sensor;
import br.edu.cio.model.UndProcessamento;
import br.edu.cio.model.dao.AnimalDAO;
import br.edu.cio.model.dao.SensorDAO;
import br.edu.cio.model.dao.UndProcessamentoDAO;
import br.edu.cio.model.dao.mysql.LeituraDAOMySQL;
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

public class SensorDAOMySQL implements SensorDAO {
    Connection conexao;
    public SensorDAOMySQL() {//construtor
    }

    @Override
    public void inserirDAO(Sensor sensor) {
        Sensor aux = buscarUltimoRegistroDAO();
        int cod = Integer.parseInt(aux.getCodigo()) + 1;
        String codigoSensor = null;
        if (cod > 99) {
            codigoSensor = "0" + String.valueOf(cod);
        } else if (cod > 9) {
            codigoSensor = "00" + String.valueOf(cod);
        } else {
            codigoSensor = "000" + String.valueOf(cod);
        }
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "INSERT INTO sensor ( sen_codigo, sen_descricao, sen_nserie, sen_tipo, sen_medicao, sen_alcance, sen_tecnologia, sen_ativo, sen_central_fk) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, codigoSensor);
            ps.setString(2, sensor.getDescricao());
            ps.setString(3, sensor.getNserie());
            ps.setString(4, sensor.getTipo());
            ps.setString(5, sensor.getMedicao());
            ps.setInt(6, sensor.getAlcance());
            ps.setString(7, sensor.getTecnologia());
            ps.setInt(8, sensor.getAtivo());
            ps.setInt(9, sensor.getCentral().getId());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(PropriedadeDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: INSERÇÃO DE SENSOR NÃO REALIZADA.", ex);
        }
    }

    @Override
    public void editarDAO(Sensor sensor) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql1 = "UPDATE sensor SET sen_codigo = ?, sen_descricao = ? , sen_nserie = ? , sen_tipo = ? , sen_medicao = ? , sen_alcance = ? , sen_tecnologia = ? , sen_ativo = ? , sen_central_fk = ? WHERE sen_id = ?";
            PreparedStatement ps2 = conexao.prepareStatement(sql1);
            ps2.setString(1, sensor.getCodigo());
            ps2.setString(2, sensor.getDescricao());
            ps2.setString(3, sensor.getNserie());
            ps2.setString(4, sensor.getTipo());
            ps2.setString(5, sensor.getMedicao());
            ps2.setInt(6, sensor.getAlcance());
            ps2.setString(7, sensor.getTecnologia());
            ps2.setInt(8, sensor.getAtivo());
            ps2.setInt(9, sensor.getCentral().getId());
            ps2.setInt(10, sensor.getId());
            ps2.executeUpdate();
            ps2.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(SensorDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EDIÇÃO DE SENSOR NÃO REALIZADA.", ex);
        }
    }

    @Override
    public Sensor buscarPorIdDAO(int id) {
        Sensor sen = new Sensor();
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "SELECT * FROM sensor WHERE sen_id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sen.setId(rs.getInt("sen_id"));
                sen.setCodigo(rs.getString("sen_codigo"));
                sen.setDescricao(rs.getString("sen_descricao"));
                sen.setNserie(rs.getString("sen_nserie"));
                sen.setTipo(rs.getString("sen_tipo"));
                sen.setMedicao(rs.getString("sen_medicao"));
                sen.setAlcance(rs.getInt("sen_alcance"));
                sen.setTecnologia(rs.getString("sen_tecnologia"));
                sen.setAtivo(rs.getInt("sen_ativo"));
                UndProcessamentoDAO acessoBDCentral = DAOFactory.gerarUndProcessamentoDAO();
                //AnimalDAO acessoBDAnimal = DAOFactory.gerarAnimalDAO();
                //sen.setAnimal(acessoBDAnimal.buscarPorIdDAO(rs.getInt("sen_animal_fk"))); //Posteriormente, substituir por código de busca por animal
                sen.setCentral(acessoBDCentral.buscarPorIdDAO(rs.getInt("sen_central_fk")));
            } else {
                sen = null;
            }
            ps.close();
            rs.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(LeituraDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sen;
    }

    @Override
    public Sensor buscarPorDescricaoDAO(String nome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sensor buscarPorNSerieDAO(String nserie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Sensor> listarDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Sensor buscarPorCodigoDAO(String codigo) {
        Sensor sen = new Sensor();
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "SELECT * FROM sensor WHERE sen_codigo=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sen.setId(rs.getInt("sen_id"));
                sen.setCodigo(rs.getString("sen_codigo"));
                sen.setDescricao(rs.getString("sen_descricao"));
                sen.setNserie(rs.getString("sen_nserie"));
                sen.setTipo(rs.getString("sen_tipo"));
                sen.setMedicao(rs.getString("sen_medicao"));
                sen.setAlcance(rs.getInt("sen_alcance"));
                sen.setTecnologia(rs.getString("sen_tecnologia"));
                sen.setAtivo(rs.getInt("sen_ativo"));
                UndProcessamentoDAO acessoBDCentral = DAOFactory.gerarUndProcessamentoDAO();
                //AnimalDAO acessoBDAnimal = DAOFactory.gerarAnimalDAO();
                //sen.setAnimal(acessoBDAnimal.buscarPorIdDAO(rs.getInt("sen_animal_fk")));
                sen.setCentral(acessoBDCentral.buscarPorIdDAO(rs.getInt("sen_central_fk")));
            } else {
                sen = null;
            }
            ps.close();
            rs.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(LeituraDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sen;
    }

    @Override
    public boolean excluirDAO(int id) {
        boolean excluido = false;
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "DELETE FROM sensor where sen_id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            excluido = true;
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(SensorDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EXCLUSÃO DE SENSOR NÃO REALIZADA.", ex);
        }
        return excluido;
    }

    @Override
    public List<Sensor> buscarDAO(String valorBusca) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            List<Sensor> sens = new ArrayList<Sensor>();
            String sql;
            PreparedStatement ps;
            if ("".equals(valorBusca)) {
                sql = "SELECT * FROM sensor";
                ps = conexao.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM sensor where sen_codigo like ?";
                ps = conexao.prepareStatement(sql);
                ps.setString(1, "%" + valorBusca + "%");
            }
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                sens = null;
            } else {
                do {
                    Sensor sen = new Sensor();
                    sen.setId(rs.getInt("sen_id"));
                    sen.setCodigo(rs.getString("sen_codigo"));
                    sen.setDescricao(rs.getString("sen_descricao"));
                    sen.setNserie(rs.getString("sen_nserie"));
                    sen.setTipo(rs.getString("sen_tipo"));
                    sen.setMedicao(rs.getString("sen_medicao"));
                    sen.setAlcance(rs.getInt("sen_alcance"));
                    sen.setTecnologia(rs.getString("sen_tecnologia"));
                    sen.setAtivo(rs.getInt("sen_ativo"));
                    UndProcessamentoDAO acessoBDCentral = DAOFactory.gerarUndProcessamentoDAO();
                    //AnimalDAO acessoBDAnimal = DAOFactory.gerarAnimalDAO();
                    //sen.setAnimal(acessoBDAnimal.buscarPorIdDAO(rs.getInt("sen_animal_fk")));
                    sen.setCentral(acessoBDCentral.buscarPorIdDAO(rs.getInt("sen_central_fk")));
                    sens.add(sen);
                } while (rs.next());
            }
            ps.close();
            rs.close();
            conexao.close();
            return sens;
        } catch (SQLException ex) {
            Logger.getLogger(SensorDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: BUSCA DE SENSOR NÃO REALIZADA.", ex);
        }
    }

    @Override
    public Sensor buscarUltimoRegistroDAO() {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            Sensor sen = new Sensor();
            String sql = "SELECT * FROM sensor ORDER BY sen_codigo DESC LIMIT 1";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                sen = null;
            } else {
                sen.setId(rs.getInt("sen_id"));
                sen.setCodigo(rs.getString("sen_codigo"));
                sen.setDescricao(rs.getString("sen_descricao"));
                sen.setNserie(rs.getString("sen_nserie"));
                sen.setTipo(rs.getString("sen_tipo"));
                sen.setMedicao(rs.getString("sen_medicao"));
                sen.setAlcance(rs.getInt("sen_alcance"));
                sen.setTecnologia(rs.getString("sen_tecnologia"));
                sen.setAtivo(rs.getInt("sen_ativo"));
                //AnimalDAO acessoBDAnimal = DAOFactory.gerarAnimalDAO();
                //sen.setAnimal(acessoBDAnimal.buscarPorIdDAO(rs.getInt("sen_animal_fk")));
                UndProcessamentoDAO acessoBDCentral = DAOFactory.gerarUndProcessamentoDAO();
                sen.setCentral(acessoBDCentral.buscarPorIdDAO(rs.getInt("sen_central_fk")));
            }
            ps.close();
            rs.close();
            conexao.close();
            return sen;
        } catch (SQLException ex) {
            Logger.getLogger(SensorDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: BUSCA DE SENSOR NÃO REALIZADA.", ex);
        }
    }

    @Override
    public void editarStatusDAO(int status, Sensor sensor, Animal animal) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql1 = "UPDATE sensor SET sen_ativo = ?, sen_animal_fk = ? WHERE sen_id = ?";
            PreparedStatement ps2 = conexao.prepareStatement(sql1);
            ps2.setInt(1, status);
            if(animal == null)
                ps2.setInt(2, 0);
            else
                ps2.setInt(2, animal.getId());
            ps2.setInt(3, sensor.getId());
            ps2.executeUpdate();
            ps2.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(SensorDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EDIÇÃO DE SENSOR NÃO REALIZADA.", ex);
        }
    }
}
