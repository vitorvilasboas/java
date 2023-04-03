package br.edu.cio.model.dao.mysql;

import br.edu.cio.model.AreaPastagem;
import br.edu.cio.model.dao.AreaPastagemDAO;
import br.edu.cio.model.dao.PropriedadeDAO;
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

public class AreaPastagemDAOMySQL implements AreaPastagemDAO{
    
    Connection conexao;
    public AreaPastagemDAOMySQL(){//construtor
        //conexao = Conexao.getConnection();//start na conexão com banco de dados
    }
    
    PropriedadeDAO acessoBDPropriedade = DAOFactory.gerarPropriedadeDAO();
    
    @Override
    public void cadastrarDAO(AreaPastagem pasto) {
        AreaPastagem aux = buscarUltimoRegistroDAO();
        int cod = Integer.parseInt(aux.getCodigo()) + 1;
        String codigoPasto = null;
        if(cod > 99)
            codigoPasto = "0"+String.valueOf(cod);
        else if (cod > 9)
            codigoPasto = "00"+String.valueOf(cod);
        else    
            codigoPasto = "000"+String.valueOf(cod);
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "INSERT INTO  pasto (pas_codigo ,  pas_nome ,  pas_area ,  pas_observacao , pas_imagem , pas_propriedade_fk) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, codigoPasto);
            ps.setString(2, pasto.getNome());
            ps.setDouble(3, pasto.getArea());
            ps.setString(4, pasto.getObservacao());
            ps.setString(5, pasto.getImagem());
            ps.setInt(6, pasto.getPropriedade().getId());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(PropriedadeDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: INSERÇÃO DE ÁREA DE PASTAGEM NÃO REALIZADA.", ex);
        }
    }
    
    @Override
    public void editarDAO(AreaPastagem pasto) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql1 = "UPDATE pasto SET  pas_nome=?, pas_area=?, pas_observacao=?, pas_imagem=?, pas_propriedade_fk=? where pas_id=?";
            PreparedStatement ps2 = conexao.prepareStatement(sql1);
            ps2.setString(1, pasto.getNome());
            ps2.setDouble(2, pasto.getArea());
            ps2.setString(3, pasto.getObservacao());
            ps2.setString(4, pasto.getImagem());
            ps2.setInt(5, pasto.getPropriedade().getId());
            ps2.setInt(6, pasto.getId());
            ps2.executeUpdate();
            ps2.close();
            conexao.close();           
        } catch (SQLException ex) {
            Logger.getLogger(PropriedadeDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EDIÇÃO DE ÁREA DE PASTAGEM NÃO REALIZADA.", ex);
        }
    }

    @Override
    public boolean excluirDAO(int id) {
        boolean excluido = false;
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "DELETE FROM pasto where pas_id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            excluido = true;
            ps.close();
            conexao.close();           
        } catch (SQLException ex) {
            Logger.getLogger(PropriedadeDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EXCLUSÃO DE ÁREA DE PASTAGEM NÃO REALIZADA.", ex);
        }
        return excluido;
    }

    @Override
    public AreaPastagem buscarPorIdDAO(int id) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            AreaPastagem pasto = new AreaPastagem();
            String sql = "SELECT * FROM pasto WHERE pas_id = ? ";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                pasto = null;
            else {
                pasto.setId(rs.getInt("pas_id"));
                pasto.setCodigo(rs.getString("pas_codigo"));
                pasto.setNome(rs.getString("pas_nome"));
                pasto.setArea(rs.getDouble("pas_area"));
                pasto.setObservacao(rs.getString("pas_observacao"));
                pasto.setImagem(rs.getString("pas_imagem"));
                pasto.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(rs.getInt("pas_propriedade_fk")));
            }
            ps.close();
            rs.close();
            conexao.close();
            return pasto;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE PROPRIEDADE NÃO REALIZADA.", ex);
        }
    }

    @Override
    public List<AreaPastagem> listarDAO() {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            List<AreaPastagem> p = new ArrayList<>();
            String  sql = "SELECT * FROM pasto";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                p = null;
            else {
                do {
                    AreaPastagem pasto = new AreaPastagem();
                    pasto.setId(rs.getInt("pas_id"));
                    pasto.setCodigo(rs.getString("pas_codigo"));
                    pasto.setNome(rs.getString("pas_nome"));
                    pasto.setArea(rs.getDouble("pas_area"));
                    pasto.setObservacao(rs.getString("pas_observacao"));
                    pasto.setImagem(rs.getString("pas_imagem"));
                    pasto.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(rs.getInt("pas_propriedade_fk")));
                    p.add(pasto);
                } while(rs.next());         
            }
            ps.close();
            rs.close();
            conexao.close();
            return p;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: LISTAGEM DE ÁREAS DE PASTAGEM NÃO REALIZADA.", ex);
        }
    }

    @Override
    public List<AreaPastagem> listarPorPropriedadeDAO(int idPropriedade) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            List<AreaPastagem> pastos = new ArrayList<>();
            String sql;
            sql = "SELECT * FROM pasto where pas_propriedade_fk=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idPropriedade);    
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                pastos = null;
            else {
                do {
                    AreaPastagem pasto = new AreaPastagem();
                    pasto.setId(rs.getInt("pas_id"));
                    pasto.setCodigo(rs.getString("pas_codigo"));
                    pasto.setNome(rs.getString("pas_nome"));
                    pasto.setArea(rs.getDouble("pas_area"));
                    pasto.setObservacao(rs.getString("pas_observacao"));
                    pasto.setImagem(rs.getString("pas_imagem"));
                    pasto.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(rs.getInt("pas_propriedade_fk")));
                    pastos.add(pasto);
                } while(rs.next());         
            }
            ps.close();
            rs.close();
            conexao.close();
            return pastos;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE PASTOS NÃO REALIZADA.", ex);
        }
    }

    @Override
    public List<AreaPastagem> buscarDAO(String valorBusca) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            List<AreaPastagem> pastos = new ArrayList<>();
            String sql;
            PreparedStatement ps;
            if("".equals(valorBusca)){
                sql = "SELECT * FROM pasto";
                ps = conexao.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM pasto where pas_nome like ? or pas_codigo like ?";
                ps = conexao.prepareStatement(sql);
                ps.setString(1, "%"+valorBusca+"%");
                ps.setString(2, "%"+valorBusca+"%");    
            }
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                pastos = null;
            else {
                do {
                    AreaPastagem pasto = new AreaPastagem();
                    pasto.setId(rs.getInt("pas_id"));
                    pasto.setCodigo(rs.getString("pas_codigo"));
                    pasto.setNome(rs.getString("pas_nome"));
                    pasto.setArea(rs.getDouble("pas_area"));
                    pasto.setObservacao(rs.getString("pas_observacao"));
                    pasto.setImagem(rs.getString("pas_imagem"));
                    pasto.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(rs.getInt("pas_propriedade_fk")));
                    pastos.add(pasto);
                } while(rs.next());         
            }
            ps.close();
            rs.close();
            conexao.close();
            return pastos;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE ÁREA DE PASTAGEM NÃO REALIZADA.", ex);
        }
    }
    
    @Override
    public AreaPastagem buscarUltimoRegistroDAO() {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            AreaPastagem pasto = new AreaPastagem();
            String  sql = "SELECT * FROM pasto ORDER BY pas_id DESC LIMIT 1";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                pasto = null;
            else {
                pasto.setId(rs.getInt("pas_id"));
                pasto.setCodigo(rs.getString("pas_codigo"));
                pasto.setNome(rs.getString("pas_nome"));
                pasto.setArea(rs.getDouble("pas_area"));
                pasto.setObservacao(rs.getString("pas_observacao"));
                pasto.setImagem(rs.getString("pas_imagem"));
                pasto.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(rs.getInt("pas_propriedade_fk")));
            }          
            ps.close();
            rs.close();
            conexao.close();
            return pasto;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: BUSCA DE ÁREA DE PASTAGEM NÃO REALIZADA.", ex);
        }
    }
    
}
