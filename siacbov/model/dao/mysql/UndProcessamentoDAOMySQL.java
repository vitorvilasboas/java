package br.edu.cio.model.dao.mysql;

import br.edu.cio.model.AreaPastagem;
import br.edu.cio.model.UndProcessamento;
import br.edu.cio.model.dao.UndProcessamentoDAO;
import br.edu.cio.model.dao.mysql.LeituraDAOMySQL;
import br.edu.cio.util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UndProcessamentoDAOMySQL implements UndProcessamentoDAO {
    Connection conexao;

    public UndProcessamentoDAOMySQL() {//construtor
        //conexao = Conexao.getConnection();//start na conexão com banco de dados
    }

    @Override
    public void inserirDAO(UndProcessamento undProcessamento) {
        UndProcessamento aux = buscarUltimoRegistroDAO();
        int cod = Integer.parseInt(aux.getCodigo()) + 1;
        String codigoCentral = null;
        if (cod > 99) {
            codigoCentral = "0" + String.valueOf(cod);
        } else if (cod > 9) {
            codigoCentral = "00" + String.valueOf(cod);
        } else {
            codigoCentral = "000" + String.valueOf(cod);
        }
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "INSERT INTO central (cen_codigo, cen_descricao, cen_nserie, cen_local_fixacao, cen_tecnologia, cen_observacao, cen_qtd_sensores) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, codigoCentral);
            ps.setString(2, undProcessamento.getDescricao());
            ps.setString(3, undProcessamento.getNserie());
            ps.setString(4, undProcessamento.getLocal_fixacao());
            ps.setString(5, undProcessamento.getTecnologia());
            ps.setString(6, undProcessamento.getObservacao());
            ps.setInt(7, undProcessamento.getQtd_sensores());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(UndProcessamentoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: INSERÇÃO DE UNIDADE DE PROCESSAMETO NÃO REALIZADA.", ex);
        }
    }

    @Override
    public void editarDAO(UndProcessamento undProcessamento) {
        try {
            conexao = Conexao.getConnection();
            String sql1 = "UPDATE central SET cen_descricao=?,cen_nserie=?,cen_local_fixacao=?,"
                    + "cen_tecnologia=?,cen_observacao=?,cen_qtd_sensores=? WHERE cen_id = ?";
            PreparedStatement ps2 = conexao.prepareStatement(sql1);
            ps2.setString(1, undProcessamento.getDescricao());
            ps2.setString(2, undProcessamento.getNserie());
            ps2.setString(3, undProcessamento.getLocal_fixacao());
            ps2.setString(4, undProcessamento.getTecnologia());
            ps2.setString(5, undProcessamento.getObservacao());
            ps2.setInt(6, undProcessamento.getQtd_sensores());
            ps2.setInt(7,undProcessamento.getId());
            ps2.executeUpdate();
            ps2.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(UndProcessamentoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EDIÇÃO DE UNIDADE DE PROCESSAMENTO NÃO REALIZADA.", ex);
        }
    }

    @Override
    public UndProcessamento buscarPorIdDAO(int id) {
        try {
            conexao = Conexao.getConnection();
            UndProcessamento up = new UndProcessamento();
            String sql = "SELECT * FROM central WHERE cen_id = ? ";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs2 = ps.executeQuery();
            if (!rs2.next()) {
                up = null;
            } else {
                up.setId(rs2.getInt("cen_id"));
                up.setCodigo(rs2.getString("cen_codigo"));
                up.setDescricao(rs2.getString("cen_descricao"));
                up.setLocal_fixacao(rs2.getString("cen_local_fixacao"));
                up.setNserie(rs2.getString("cen_nserie"));
                up.setObservacao(rs2.getString("cen_observacao"));
                up.setQtd_sensores(rs2.getInt("cen_qtd_sensores"));
                up.setTecnologia(rs2.getString("cen_tecnologia"));
            }
            ps.close();
            rs2.close();
            conexao.close();
            return up;
        } catch (SQLException ex) {
            Logger.getLogger(UndProcessamentoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE CENTRAL NÃO REALIZADA.", ex);
        }
    }

    @Override
    public UndProcessamento buscarPorDescricaoDAO(String descricao) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UndProcessamento buscarPorNSerieDAO(String nserie) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UndProcessamento> listarDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UndProcessamento buscarPorCodigoDAO(String codigo) {
        UndProcessamento up = new UndProcessamento();
        try {
            conexao = Conexao.getConnection();
            String sql2 = "SELECT * FROM central WHERE cen_codigo=?";
            PreparedStatement ps2 = conexao.prepareStatement(sql2);
            ps2.setString(1, codigo);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                up.setId(rs2.getInt("cen_id"));
                up.setCodigo(rs2.getString("cen_codigo"));
                up.setDescricao(rs2.getString("cen_descricao"));
                up.setLocal_fixacao(rs2.getString("cen_local_fixacao"));
                up.setNserie(rs2.getString("cen_nserie"));
                up.setObservacao(rs2.getString("cen_observacao"));
                up.setQtd_sensores(rs2.getInt("cen_qtd_sensores"));
                up.setTecnologia(rs2.getString("cen_tecnologia"));
            } else {
                up = null;
            }
            ps2.close();
            rs2.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(LeituraDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return up;
    }

    @Override
    public boolean excluirDAO(int id) {
        boolean excluido = false;
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "DELETE FROM central where cen_id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            excluido = true;
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(UndProcessamentoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EXCLUSÃO DE UNIDADE DE PROCESSAMENTO NÃO REALIZADA.", ex);
        }
        return excluido;
    }

    @Override
    public List<UndProcessamento> buscarDAO(String valorBusca) {
        try {
            conexao = Conexao.getConnection();
            List<UndProcessamento> central = new ArrayList<>();
            String sql;
            PreparedStatement ps;
            if ("".equals(valorBusca)) {  
                sql = "SELECT * FROM central";
                ps = conexao.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM central where cen_codigo like ? "; 
                ps = conexao.prepareStatement(sql);
                ps.setString(1, "%" + valorBusca + "%");
            }
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                central = null;
            } else {
                do {
                    UndProcessamento up = new UndProcessamento();
                    up.setId(rs.getInt("cen_id"));
                    up.setCodigo(rs.getString("cen_codigo"));
                    up.setDescricao(rs.getString("cen_descricao"));
                    up.setLocal_fixacao(rs.getString("cen_local_fixacao"));
                    up.setNserie(rs.getString("cen_nserie"));
                    up.setObservacao(rs.getString("cen_observacao"));
                    up.setQtd_sensores(rs.getInt("cen_qtd_sensores"));
                    up.setTecnologia(rs.getString("cen_tecnologia"));
                    central.add(up);
                } while (rs.next());
            }
            ps.close();
            rs.close();
            conexao.close();
            return central;
        } catch (SQLException ex) {
            Logger.getLogger(UndProcessamentoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DA UNIDADE DE PROCESSAMENTO NÃO REALIZADA.", ex);
        }
    }

    @Override
    public UndProcessamento buscarUltimoRegistroDAO() {
        try {
            conexao = Conexao.getConnection();
            UndProcessamento up = new UndProcessamento();
            String sql = "SELECT * FROM central ORDER BY cen_id DESC LIMIT 1";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                up = null;
            } else {
                up.setId(rs.getInt("cen_id"));
                up.setCodigo(rs.getString("cen_codigo"));
                up.setDescricao(rs.getString("cen_descricao"));
                up.setLocal_fixacao(rs.getString("cen_local_fixacao"));
                up.setNserie(rs.getString("cen_nserie"));
                up.setObservacao(rs.getString("cen_observacao"));
                up.setQtd_sensores(rs.getInt("cen_qtd_sensores"));
                up.setTecnologia(rs.getString("cen_tecnologia"));
            }
            ps.close();
            rs.close();
            conexao.close();
            return up;
        } catch (SQLException ex) {
            Logger.getLogger(UndProcessamentoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: BUSCA DE UNIDADE DE PROCESSAMENTO NÃO REALIZADA.", ex);
        }
    }
}
