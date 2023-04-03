package br.edu.cio.model.dao.mysql;

import br.edu.cio.model.Propriedade;
import br.edu.cio.model.dao.PropriedadeDAO;
import br.edu.cio.util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class PropriedadeDAOMySQL implements PropriedadeDAO {
    
    Connection conexao;
    public PropriedadeDAOMySQL(){//construtor
        //conexao = Conexao.getConnection();//start na conexão com banco de dados
    }

    @Override
    public void cadastrarDAO(Propriedade propriedade) {
        Propriedade aux = buscarUltimoRegistroDAO();
        int cod = Integer.parseInt(aux.getCodigo()) + 1;
        String codigoProp = null;
        if(cod > 99)
            codigoProp = "0"+String.valueOf(cod);
        else if (cod > 9)
            codigoProp = "00"+String.valueOf(cod);
        else    
            codigoProp = "000"+String.valueOf(cod);
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "INSERT INTO  propriedade (prop_codigo ,  prop_nome ,  prop_endereco ,  prop_municipio , prop_uf , prop_inscricao_estadual ,  prop_cnpj ,  prop_proprietario ,  prop_area ,  prop_atividade_principal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, codigoProp);
            ps.setString(2, propriedade.getNome());
            ps.setString(3, propriedade.getEndereco());
            ps.setString(4, propriedade.getMunicipio());
            ps.setString(5, propriedade.getUf());
            ps.setString(6, propriedade.getInscricao_estadual());
            ps.setString(7, propriedade.getCnpj());
            ps.setString(8, propriedade.getProprietario());
            ps.setDouble(9, propriedade.getArea());
            ps.setString(10, propriedade.getAtividade_principal());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(PropriedadeDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: INSERÇÃO DE PROPRIEDADE NÃO REALIZADA.", ex);
        }
    }

    @Override
    public Propriedade buscarPorIdDAO(int id) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            Propriedade prop = new Propriedade();
            String sql = "SELECT * FROM propriedade WHERE prop_id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                prop = null;
            else {
                prop.setId(rs.getInt("prop_id"));
                prop.setCodigo(rs.getString("prop_codigo"));
                prop.setNome(rs.getString("prop_nome"));
                prop.setEndereco(rs.getString("prop_endereco"));
                prop.setMunicipio(rs.getString("prop_municipio"));
                prop.setUf(rs.getString("prop_uf"));
                prop.setInscricao_estadual(rs.getString("prop_inscricao_estadual"));
                prop.setCnpj(rs.getString("prop_cnpj"));
                prop.setProprietario(rs.getString("prop_proprietario"));
                prop.setArea(rs.getDouble("prop_area"));
                prop.setAtividade_principal(rs.getString("prop_atividade_principal"));
            }
            ps.close();
            rs.close();
            conexao.close();
            return prop;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE PROPRIEDADE NÃO REALIZADA.", ex);
        }
    }

    @Override
    public List<Propriedade> buscarDAO(String valorBusca) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            List<Propriedade> p = new ArrayList<>();
            String sql;
            PreparedStatement ps;
            if("".equals(valorBusca)){
                sql = "SELECT * FROM propriedade";
                ps = conexao.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM propriedade where prop_nome like ? or prop_codigo like ?";
                ps = conexao.prepareStatement(sql);
                ps.setString(1, "%"+valorBusca+"%");
                ps.setString(2, "%"+valorBusca+"%");    
            }
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                p = null;
            else {
                do {
                    Propriedade prop = new Propriedade();
                    prop.setId(rs.getInt("prop_id"));
                    prop.setCodigo(rs.getString("prop_codigo"));
                    prop.setNome(rs.getString("prop_nome"));
                    prop.setEndereco(rs.getString("prop_endereco"));
                    prop.setMunicipio(rs.getString("prop_municipio"));
                    prop.setUf(rs.getString("prop_uf"));
                    prop.setInscricao_estadual(rs.getString("prop_inscricao_estadual"));
                    prop.setCnpj(rs.getString("prop_cnpj"));
                    prop.setProprietario(rs.getString("prop_proprietario"));
                    prop.setArea(rs.getDouble("prop_area"));
                    prop.setAtividade_principal(rs.getString("prop_atividade_principal"));
                    p.add(prop);
                } while(rs.next());         
            }
            ps.close();
            rs.close();
            conexao.close();
            return p;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE PROPRIEDADE NÃO REALIZADA.", ex);
        }
    }
    
    @Override
    public void editarDAO(Propriedade propriedade) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql1 = "UPDATE propriedade SET prop_codigo=?, prop_nome=?, prop_endereco=?, prop_municipio=?, prop_uf=?, prop_inscricao_estadual=?, prop_cnpj=?, prop_proprietario=?, prop_area=?, prop_atividade_principal=? where prop_id=?";
            PreparedStatement ps2 = conexao.prepareStatement(sql1);
            ps2.setString(1, propriedade.getCodigo());
            ps2.setString(2, propriedade.getNome());
            ps2.setString(3, propriedade.getEndereco());
            ps2.setString(4, propriedade.getMunicipio());
            ps2.setString(5, propriedade.getUf());
            ps2.setString(6, propriedade.getInscricao_estadual());
            ps2.setString(7, propriedade.getCnpj());
            ps2.setString(8, propriedade.getProprietario());
            ps2.setDouble(9, propriedade.getArea());
            ps2.setString(10, propriedade.getAtividade_principal());
            ps2.setInt(11, propriedade.getId());
            ps2.executeUpdate();
            ps2.close();
            conexao.close();           
        } catch (SQLException ex) {
            Logger.getLogger(PropriedadeDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EDIÇÃO DE PROPRIEDADE NÃO REALIZADA.", ex);
        }  
    }
    
    @Override
    public boolean excluirDAO(int id) {
        boolean excluido = false;
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "DELETE FROM propriedade where prop_id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            excluido = true;
            ps.close();
            conexao.close();           
        } catch (SQLException ex) {
            Logger.getLogger(PropriedadeDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EXCLUSÃO DE PROPRIEDADE NÃO REALIZADA.", ex);
        }
        return excluido;
    }
    
    @Override
    public List<Propriedade> listarDAO() {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            List<Propriedade> p = new ArrayList<>();
            String  sql = "SELECT * FROM propriedade";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                p = null;
            else {
                do {
                    Propriedade prop = new Propriedade();
                    prop.setId(rs.getInt("prop_id"));
                    prop.setCodigo(rs.getString("prop_codigo"));
                    prop.setNome(rs.getString("prop_nome"));
                    prop.setEndereco(rs.getString("prop_endereco"));
                    prop.setMunicipio(rs.getString("prop_municipio"));
                    prop.setUf(rs.getString("prop_uf"));
                    prop.setInscricao_estadual(rs.getString("prop_inscricao_estadual"));
                    prop.setCnpj(rs.getString("prop_cnpj"));
                    prop.setProprietario(rs.getString("prop_proprietario"));
                    prop.setArea(rs.getDouble("prop_area"));
                    prop.setAtividade_principal(rs.getString("prop_atividade_principal"));
                    p.add(prop);
                } while(rs.next());         
            }
            ps.close();
            rs.close();
            conexao.close();
            return p;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: LISTAGEM DE PROPRIEDADES NÃO REALIZADA.", ex);
        }
    }
    
    @Override
    public Propriedade buscarUltimoRegistroDAO() {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            Propriedade prop = new Propriedade();
            String  sql = "SELECT * FROM propriedade ORDER BY prop_id DESC LIMIT 1";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                prop = null;
            else {
                prop.setId(rs.getInt("prop_id"));
                prop.setCodigo(rs.getString("prop_codigo"));
                prop.setNome(rs.getString("prop_nome"));
                prop.setEndereco(rs.getString("prop_endereco"));
                prop.setMunicipio(rs.getString("prop_municipio"));
                prop.setUf(rs.getString("prop_uf"));
                prop.setInscricao_estadual(rs.getString("prop_inscricao_estadual"));
                prop.setCnpj(rs.getString("prop_cnpj"));
                prop.setProprietario(rs.getString("prop_proprietario"));
                prop.setArea(rs.getDouble("prop_area"));
                prop.setAtividade_principal(rs.getString("prop_atividade_principal"));
            }          
            ps.close();
            rs.close();
            conexao.close();
            return prop;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: BUSCA DE PROPRIEDADE NÃO REALIZADA.", ex);
        }
    }

}
