package br.edu.cio.model.dao.mysql;

import br.edu.cio.model.Animal;
import br.edu.cio.model.AreaPastagem;
import br.edu.cio.model.Lote;
import br.edu.cio.model.Propriedade;
import br.edu.cio.model.dao.AreaPastagemDAO;
import br.edu.cio.model.dao.LoteDAO;
import br.edu.cio.model.dao.PropriedadeDAO;
import br.edu.cio.util.Conexao;
import br.edu.cio.util.DAOFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoteDAOMySQL implements LoteDAO {
    Connection conexao;
    PropriedadeDAO acessoBDPropriedade = DAOFactory.gerarPropriedadeDAO();
    AreaPastagemDAO acessoBDPasto = DAOFactory.gerarAreaPastagemDAO();
    public LoteDAOMySQL() {//construtor
        //conexao = Conexao.getConnection();//start na conexão com banco de dados
    }

    @Override
    public void cadastrarDAO(Lote lote) {
        Lote aux = buscarUltimoRegistroDAO();
        int cod = Integer.parseInt(aux.getCodigo()) + 1;
        String codigoLote = null;
        if (cod > 99) {
            codigoLote = "0" + String.valueOf(cod);
        } else if (cod > 9) {
            codigoLote = "00" + String.valueOf(cod);
        } else {
            codigoLote = "000" + String.valueOf(cod);
        }
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "INSERT INTO lote (lot_codigo, lot_descricao, lot_tipo, lot_observacao, lot_propriedade_fk, lot_pasto_fk) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, codigoLote);
            ps.setString(2, lote.getDescricao());
            ps.setString(3, lote.getTipo());
            ps.setString(4, lote.getObservacao());
            ps.setInt(5, lote.getPropriedade().getId());
            ps.setInt(6, lote.getPasto().getId());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoteDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: INSERÇÃO DE LOTE NÃO REALIZADA.", ex);
        }
    }
    
    @Override
    public void editarDAO(Lote lote) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql1 = "UPDATE lote SET lot_descricao = ? , lot_tipo = ? , lot_observacao = ? , lot_propriedade_fk = ? , lot_pasto_fk = ? WHERE lot_id = ? ";
            PreparedStatement ps2 = conexao.prepareStatement(sql1);
            ps2.setString(1, lote.getDescricao());
            ps2.setString(2, lote.getTipo());
            ps2.setString(3, lote.getObservacao());
            ps2.setInt(4, lote.getPropriedade().getId());
            ps2.setInt(5, lote.getPasto().getId());
            ps2.setInt(6, lote.getId());
            ps2.executeUpdate();
            ps2.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoteDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EDIÇÃO DE LOTE NÃO REALIZADA.", ex);
        }
    }

    @Override
    public boolean excluirDAO(int id) {
       boolean excluido = false;
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "DELETE FROM lote where lot_id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            excluido = true;
            ps.close();
            conexao.close();           
        } catch (SQLException ex) {
            Logger.getLogger(LoteDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EXCLUSÃO DE LOTE NÃO REALIZADA.", ex);
        }
        return excluido;
    }

    @Override
    public Lote buscarPorIdDAO(int id) {
        SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            Lote lote = new Lote();
            String sql = "SELECT * FROM lote WHERE lot_id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                lote = null;
            } else {
                lote.setId(rs.getInt("lot_id"));
                lote.setCodigo(rs.getString("lot_codigo"));
                lote.setDescricao(rs.getString("lot_descricao"));
                lote.setTipo(rs.getString("lot_tipo"));
                lote.setObservacao(rs.getString("lot_observacao"));
                lote.setDt_cadastro(formata.format(rs.getDate("lot_dt_cadastro")));
                lote.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(rs.getInt("lot_propriedade_fk")));
                lote.setPasto(acessoBDPasto.buscarPorIdDAO(rs.getInt("lot_pasto_fk")));
            }
            ps.close();
            rs.close();
            conexao.close();
            return lote;
        } catch (SQLException ex) {
            Logger.getLogger(LoteDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE LOTE NÃO REALIZADA.", ex);
        }
    }

    @Override
    public List<Lote> listarDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Lote> listarPorPropriedadeDAO(int idPropriedade) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
            List<Lote> lotes = new ArrayList<>();
            String sql;
            sql = "SELECT * FROM lote where lot_propriedade_fk = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idPropriedade);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                lotes = null;
            } else {
                do {
                    Lote l = new Lote();
                    l.setId(rs.getInt("lot_id"));
                    l.setCodigo(rs.getString("lot_codigo"));
                    l.setDescricao(rs.getString("lot_descricao"));
                    l.setTipo(rs.getString("lot_tipo"));
                    l.setObservacao(rs.getString("lot_observacao"));
                    //l.setDt_cadastro(formata.format(rs.getDate("lot_dt_cadastro")));
                    l.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(rs.getInt("lot_propriedade_fk")));
                    l.setPasto(acessoBDPasto.buscarPorIdDAO(rs.getInt("lot_pasto_fk")));
                    lotes.add(l);
                } while (rs.next());
            }
            ps.close();
            rs.close();
            conexao.close();
            return lotes;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE LOTES NÃO REALIZADA.", ex);
        }
    }

    @Override
    public List<Lote> buscarDAO(String valorBusca) {
        //SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            List<Lote> lot = new ArrayList<>();
            String sql;
            PreparedStatement ps;
            if ("".equals(valorBusca)) {
                sql = "SELECT * FROM lote";
                ps = conexao.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM lote where lot_codigo like ? or lot_descricao like ?";
                ps = conexao.prepareStatement(sql);
                ps.setString(1, "%" + valorBusca + "%");
                ps.setString(2, "%" + valorBusca + "%");
            }
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                lot = null;
            } else {
                do {
                    Lote l = new Lote();
                    l.setId(rs.getInt("lot_id"));
                    l.setCodigo(rs.getString("lot_codigo"));
                    l.setDescricao(rs.getString("lot_descricao"));
                    l.setTipo(rs.getString("lot_tipo"));
                    l.setObservacao(rs.getString("lot_observacao"));
                    //l.setDt_cadastro(formata.format(rs.getDate("lot_dt_cadastro")));
                    //l.setDt_cadastro(rs.getString("lot_dt_cadastro"));
                    l.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(rs.getInt("lot_propriedade_fk")));
                    l.setPasto(acessoBDPasto.buscarPorIdDAO(rs.getInt("lot_pasto_fk")));
                    lot.add(l);
                } while (rs.next());
            }
            ps.close();
            rs.close();
            conexao.close();
            return lot;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE LOTE NÃO REALIZADA.", ex);
        }
    }

    @Override
    public Lote buscarUltimoRegistroDAO() {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            Lote l = new Lote();
            String sql = "SELECT * FROM lote ORDER BY lot_id DESC LIMIT 1";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                l = null;
            } else {
                l.setId(rs.getInt("lot_id"));
                l.setCodigo(rs.getString("lot_codigo"));
                l.setDescricao(rs.getString("lot_descricao"));
                l.setTipo(rs.getString("lot_tipo"));
                l.setObservacao(rs.getString("lot_observacao"));
                //l.setDt_cadastro(formata.format(rs.getDate("lot_dt_cadastro")));
                //l.setDt_cadastro(rs.getString("lot_dt_cadastro"));
                l.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(rs.getInt("lot_propriedade_fk")));
                l.setPasto(acessoBDPasto.buscarPorIdDAO(rs.getInt("lot_pasto_fk")));
            }
            ps.close();
            rs.close();
            conexao.close();
            return l;
        } catch (SQLException ex) {
            Logger.getLogger(LoteDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: BUSCA DE LOTE NÃO REALIZADA.", ex);
        }
    }
}
