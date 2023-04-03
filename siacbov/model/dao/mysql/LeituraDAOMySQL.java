package br.edu.cio.model.dao.mysql;

import br.edu.cio.model.Leitura;
import br.edu.cio.model.Sensor;
import br.edu.cio.model.UndProcessamento;
import br.edu.cio.model.dao.LeituraDAO;
import br.edu.cio.model.dao.SensorDAO;
import br.edu.cio.model.dao.UndProcessamentoDAO;
import br.edu.cio.util.Conexao;
import br.edu.cio.util.DAOFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeituraDAOMySQL implements LeituraDAO{
    
    SensorDAO acessoBDSensor = DAOFactory.gerarSensorDAO();
    UndProcessamentoDAO acessoBDCentral = DAOFactory.gerarUndProcessamentoDAO();
    Connection conexao;
    SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
        
    public LeituraDAOMySQL(){//construtor
        //Connection conexao = Conexao.getConnection();//start na conexão com banco de dados
    }
    
    @Override
    public Leitura buscarDAO(Leitura l) {
        int idSensorLeitura = l.getSensor().getId();
        Leitura leitura = new Leitura();
        Boolean encontrado = false;
        try {
            conexao = Conexao.getConnection();
            java.sql.Date data = new java.sql.Date(formata.parse(l.getData()).getTime());
            java.sql.Time hora = new java.sql.Time(formataHora.parse(l.getHora()).getTime());
            String sql2 = "SELECT * FROM leitura WHERE lei_data=? AND lei_hora=? AND lei_sensor_fk=?";
            PreparedStatement ps2 = conexao.prepareStatement(sql2);
            ps2.setDate(1, data);
            ps2.setTime(2, hora);
            ps2.setInt(3, idSensorLeitura);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()){ //resultSet sempre inicia antes do primeiro registro, aqui verifica se o registro foi ou não encontrado
                leitura.setId(rs2.getInt("lei_id"));
                leitura.setnLeitura(rs2.getInt("lei_numero"));
                leitura.setnPassadas(rs2.getInt("lei_qtd_passadas"));
                leitura.setObservacao(rs2.getString("lei_observacao"));
                leitura.setIntervalo_config(rs2.getInt("lei_intervalo_config"));
                leitura.setSensor(acessoBDSensor.buscarPorIdDAO(rs2.getInt("lei_sensor_fk")));//busca o sensor da leitura pelo código
                leitura.setCentral(acessoBDCentral.buscarPorIdDAO(rs2.getInt("lei_central_fk")));//busca a central da leitura pelo código
                leitura.setData(formata.format(rs2.getDate("lei_data")));
                leitura.setHora(formataHora.format(rs2.getTime("lei_hora")));
                encontrado = true;
            } else {
                leitura = null;
            }
            ps2.close();
            rs2.close();
            conexao.close();
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(LeituraDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return leitura;
    }

    @Override
    public void inserirDAO(Leitura l) {
        SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoDataBr = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHoraBr = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatoCompletoUSA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatoCompletoBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            conexao = Conexao.getConnection();
            java.sql.Date data = new java.sql.Date(formatoDataBr.parse(l.getData()).getTime());
            java.sql.Time hora = new java.sql.Time(formatoHoraBr.parse(l.getHora()).getTime());
            String sql = "INSERT INTO  leitura (lei_numero ,  lei_qtd_passadas ,  lei_data , lei_hora , lei_observacao ,  lei_sensor_fk ,  lei_central_fk, lei_intervalo_config ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, l.getnLeitura());
            ps.setInt(2, l.getnPassadas());
            ps.setDate(3, data);
            ps.setTime(4, hora);
            ps.setString(5, l.getObservacao());
            ps.setInt(6, l.getSensor().getId());
            ps.setInt(7, l.getCentral().getId());
            ps.setInt(8, l.getIntervalo_config());
            ps.executeUpdate();
            System.out.println(">> SENSOR "+ l.getCodigo_sensor() +" - LEITURA CADASTRADA COM SUCESSO.");
            ps.close();
            conexao.close();
        } catch (ParseException | SQLException ex) {
            Logger.getLogger(LeituraDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: INSERÇÃO DE LEITURA NÃO REALIZADA.", ex);
        }        
    }

    @Override
    public Leitura buscarPorIdDAO(int id) {
        Leitura leitura = new Leitura();
        try {
            conexao = Conexao.getConnection();
            String sql = "SELECT * FROM leitura WHERE lei_id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                leitura.setId(rs.getInt("lei_id"));
                leitura.setnLeitura(rs.getInt("lei_numero"));
                leitura.setnPassadas(rs.getInt("lei_qtd_passadas"));
                leitura.setObservacao(rs.getString("lei_observacao"));
                leitura.setData(formata.format(rs.getDate("lei_data")));
                leitura.setHora(formataHora.format(rs.getTime("lei_hora")));
                leitura.setIntervalo_config(rs.getInt("lei_intervalo_config"));
                leitura.setSensor(acessoBDSensor.buscarPorIdDAO(rs.getInt("lei_sensor_fk")));//busca o sensor da leitura pelo código
                leitura.setCentral(acessoBDCentral.buscarPorIdDAO(rs.getInt("lei_central_fk")));//busca a central da leitura pelo código
            } else {
                leitura = null;
            }
            ps.close();
            rs.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(LeituraDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return leitura;
    }

    @Override
    public List<Leitura> listarPorIdSensor(int idSensor) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            List<Leitura> leituras = new ArrayList<>();
            String sql;
            sql = "SELECT * FROM leitura where lei_sensor_fk = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idSensor);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                leituras = null;
            } else {
                do {
                Leitura leit = new Leitura();
                leit.setId(rs.getInt("lei_id"));
                leit.setnLeitura(rs.getInt("lei_numero"));
                leit.setnPassadas(rs.getInt("lei_qtd_passadas"));
                leit.setObservacao(rs.getString("lei_observacao"));
                leit.setIntervalo_config(rs.getInt("lei_intervalo_config"));
                leit.setData(formata.format(rs.getDate("lei_data")));
                leit.setHora(formataHora.format(rs.getTime("lei_hora")));
                leit.setSensor(acessoBDSensor.buscarPorIdDAO(rs.getInt("lei_sensor_fk")));//busca o sensor da leitura pelo código
                leit.setCentral(acessoBDCentral.buscarPorIdDAO(rs.getInt("lei_central_fk")));//busca a central da leitura pelo código
                    leituras.add(leit);
                } while (rs.next());
            }
            ps.close();
            rs.close();
            conexao.close();
            return leituras;
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE LEITURAS NÃO REALIZADA.", ex);
        }
    }

    @Override
    public List<Leitura> listarPorIdSensorEDatas(int idSensor, String dtInicio, String dtFim) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            List<Leitura> leituras = new ArrayList<>();
            String sql;
            sql = "SELECT * FROM leitura where lei_sensor_fk = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idSensor);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                leituras = null;
            } else {
                do {
                Leitura leit = new Leitura();
                leit.setId(rs.getInt("lei_id"));
                leit.setnLeitura(rs.getInt("lei_numero"));
                leit.setnPassadas(rs.getInt("lei_qtd_passadas"));
                leit.setObservacao(rs.getString("lei_observacao"));
                leit.setIntervalo_config(rs.getInt("lei_intervalo_config"));
                leit.setData(formata.format(rs.getDate("lei_data")));
                leit.setHora(formataHora.format(rs.getTime("lei_hora")));
                leit.setSensor(acessoBDSensor.buscarPorIdDAO(rs.getInt("lei_sensor_fk")));//busca o sensor da leitura pelo código
                leit.setCentral(acessoBDCentral.buscarPorIdDAO(rs.getInt("lei_central_fk")));//busca a central da leitura pelo código
                    leituras.add(leit);
                } while (rs.next());
            }
            ps.close();
            rs.close();
            conexao.close();
            return leituras;
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE LEITURAS NÃO REALIZADA.", ex);
        }
    }
}