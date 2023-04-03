package br.edu.cio.model.dao.mysql;

import br.edu.cio.model.Alerta;
import br.edu.cio.model.AlertaEmitido;
import br.edu.cio.model.dao.AlertaDAO;
import br.edu.cio.model.dao.AlertaEmitidoDAO;
import br.edu.cio.model.dao.DestinatarioDAO;
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

public class AlertaEmitidoDAOMySQL implements AlertaEmitidoDAO{
    Connection conexao;
    
    AlertaDAO acessoBDAlerta = DAOFactory.gerarAlertaDAO();
    AlertaEmitidoDAO acessoBDAlem = DAOFactory.gerarAlertaEmitidoDAO();
    DestinatarioDAO acessoBDDestinatario = DAOFactory.gerarDestinatarioDAO();
    
    SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatoDataBr = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatoHoraBr = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat formatoCompletoUSA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat formatoCompletoBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    @Override
    public void cadastrarDAO(AlertaEmitido alem) {
        try {
            conexao = Conexao.getConnection();
            String sql = "INSERT INTO  alertas_emitidos (alem_data, alem_hora, alem_alerta_fk, alem_destinatario_fk) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(formatoDataBr.parse(alem.getData()).getTime()));
            ps.setTime(2, new java.sql.Time(formatoHoraBr.parse(alem.getHora()).getTime()));
            ps.setInt(3, alem.getAlerta().getId());
            ps.setInt(4, alem.getDestinatario().getId());
            ps.executeUpdate();
            System.out.println("!!! NOVO ALERTA EMITIDO PARA O EMAIL "+alem.getDestinatario().getEmail()+" DO FUNCIONÁRIO "+alem.getDestinatario().getFuncionario().getNome()+".");
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(LeituraDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: REGISTRO DE ALERTA EMITIDO NÃO REALIZADO.", ex);
        } catch (ParseException ex) {
            Logger.getLogger(AlertaEmitidoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public AlertaEmitido buscarPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AlertaEmitido buscarPorIdDestinatario(int idDestinatario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AlertaEmitido buscarPorIdAlerta(int idAlerta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AlertaEmitido> buscarDAO() {
        try {
            conexao = Conexao.getConnection();
            List<AlertaEmitido> alemis = new ArrayList<>();
            String sql;
            sql = "SELECT * FROM alertas_emitidos ORDER BY alem_data DESC";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                alemis = null;
            else {
                do {
                    AlertaEmitido alerta = new AlertaEmitido();
                    alerta.setId(rs.getInt("alem_id"));
                    alerta.setData(formatoDataBr.format(rs.getDate("alem_data")));
                    alerta.setHora(formatoHoraBr.format(rs.getTime("alem_hora")));
                    alerta.setAlerta(null);//acessoBDAlerta.buscarPorIdDAO(rs.getInt("alem_alerta_fk")));
                    alerta.setDestinatario(acessoBDDestinatario.buscarPorIdDAO(rs.getInt("alem_destinatario_fk")));
                    alemis.add(alerta);                
                } while (rs.next());
            }         
            ps.close();
            rs.close();
            conexao.close();
            return alemis;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: BUSCA DE ALERTAS NÃO REALIZADA.", ex);
        }
    }
    
}
