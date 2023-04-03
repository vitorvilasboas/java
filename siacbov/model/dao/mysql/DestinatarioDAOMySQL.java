package br.edu.cio.model.dao.mysql;

import br.edu.cio.model.Destinatario;
import br.edu.cio.model.Funcionario;
import br.edu.cio.model.dao.DestinatarioDAO;
import br.edu.cio.model.dao.FuncionarioDAO;
import br.edu.cio.model.dao.PropriedadeDAO;
import br.edu.cio.model.dao.mysql.LeituraDAOMySQL;
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

public class DestinatarioDAOMySQL implements DestinatarioDAO {

    Connection conexao;

    public DestinatarioDAOMySQL() {//construtor
        //conexao = Conexao.getConnection();//start na conexão com banco de dados
    }
    FuncionarioDAO acessoBDFuncionario = DAOFactory.gerarFuncionarioDAO();

    @Override
    public void inserirDAO(Destinatario destinatario) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "INSERT INTO destinatario( des_email, des_funcionario_fk, des_ativo, des_celular) VALUES (?,?,?)";
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, destinatario.getEmail());
            ps.setInt(2, destinatario.getFuncionario().getId());
            ps.setInt(3, destinatario.getAtivo());
            ps.setString(4, destinatario.getCelular());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(DestinatarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: INSERÇÃO DE DESTINATARIO NÃO REALIZADA.", ex);
        }
    }

    @Override
    public void editarDAO(Destinatario destinatario) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql1 = "UPDATE destinatario SET des_email=?,des_funcionario_fk=?,des_ativo=?,des_celular=? WHERE des_id = ?";
            PreparedStatement ps2 = conexao.prepareStatement(sql1);
            ps2.setString(1, destinatario.getEmail());
            ps2.setInt(2, destinatario.getFuncionario().getId());
            ps2.setInt(3, destinatario.getAtivo());
            ps2.setString(4, destinatario.getCelular());
            ps2.setInt(5, destinatario.getId());
            ps2.executeUpdate();
            ps2.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(DestinatarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EDIÇÃO DE DESTINATARIO NÃO REALIZADA.", ex);
        }
    }

    @Override
    public Destinatario buscarPorIdDAO(int id) {
        Destinatario des = new Destinatario();
        try {
            conexao = Conexao.getConnection();
            String sql = "SELECT * FROM destinatario WHERE des_id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                des.setId(rs.getInt("des_id"));
                des.setEmail(rs.getString("des_email"));
                FuncionarioDAO acessFunc = DAOFactory.gerarFuncionarioDAO();
                Funcionario f = acessFunc.buscarPorIdDAO(rs.getInt("des_funcionario_fk"));
                des.setFuncionario(f);
                des.setCelular(rs.getString("des_celular"));
                des.setAtivo(rs.getInt("des_ativo"));
            } else {
                des = null;
            }
            ps.close();
            rs.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(LeituraDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return des;
    }

    @Override
    public boolean excluirDAO(int id) {
        boolean excluido = false;
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "DELETE FROM alertas_emitidos where alem_destinatario_fk=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            sql = "DELETE FROM destinatario where des_id=?";
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            excluido = true;
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(DestinatarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EXCLUSÃO DE DESTINATARIO NÃO REALIZADA.", ex);
        }
        return excluido;
    }

    @Override
    public List<Destinatario> buscarDAO(String valorBusca) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            List<Destinatario> d = new ArrayList<>();
            String sql;
            PreparedStatement ps;
            if ("".equals(valorBusca)) {
                sql = "SELECT * FROM destinatario";
                ps = conexao.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM destinatario where des_id like ?";
                ps = conexao.prepareStatement(sql);
                ps.setString(1, "%" + valorBusca + "%");
            }
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                d = null;
            } else {
                do {
                    Destinatario des = new Destinatario();
                    des.setId(rs.getInt("des_id"));
                    des.setEmail(rs.getString("des_email"));
                    des.setCelular(rs.getString("des_celular"));
                    FuncionarioDAO acessFunc = DAOFactory.gerarFuncionarioDAO();
                    Funcionario f = acessFunc.buscarPorIdDAO(rs.getInt("des_funcionario_fk"));
                    des.setFuncionario(f);
                    des.setAtivo(rs.getInt("des_ativo"));
                    d.add(des);
                } while (rs.next());
            }
            ps.close();
            rs.close();
            conexao.close();
            return d;
        } catch (SQLException ex) {
            Logger.getLogger(DestinatarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DA DESTINATARIO NÃO REALIZADA.", ex);
        }
    }

    @Override
    public Destinatario buscarPorEmailDAO(String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Destinatario> listarDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Destinatario> listarPorFuncionario(int idFuncionario) {

        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
            List<Destinatario> destinatario = new ArrayList<>();
            String sql;
            sql = "SELECT * FROM destinatario where des_funcionario_fk = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idFuncionario);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                destinatario = null;
            } else {
                do {
                    Destinatario des = new Destinatario();
                    des.setId(rs.getInt("des_id"));
                    des.setEmail(rs.getString("des_email"));
                    des.setCelular(rs.getString("des_celular"));
                    FuncionarioDAO acessFunc = DAOFactory.gerarFuncionarioDAO();
                    Funcionario f = acessFunc.buscarPorIdDAO(rs.getInt("des_funcionario_fk"));
                    des.setFuncionario(f);
                    des.setAtivo(rs.getInt("des_ativo"));
                    destinatario.add(des);
                } while (rs.next());
            }
            ps.close();
            rs.close();
            conexao.close();
            return destinatario;
        } catch (SQLException ex) {
            Logger.getLogger(DestinatarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE DESTINATÁRIO NÃO REALIZADA.", ex);
        }
    }

}
