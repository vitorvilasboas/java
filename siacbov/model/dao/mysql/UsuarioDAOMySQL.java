package br.edu.cio.model.dao.mysql;

import br.edu.cio.model.Funcionario;
import br.edu.cio.model.Usuario;
import br.edu.cio.model.dao.FuncionarioDAO;
import br.edu.cio.model.dao.UsuarioDAO;
import br.edu.cio.util.Conexao;
import br.edu.cio.util.DAOFactory;
import br.edu.cio.util.MetodosUteis;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UsuarioDAOMySQL implements UsuarioDAO {//implementa a interface do usuario que contem os métodos

    Connection conexao;
    public UsuarioDAOMySQL() {//construtor
    }

    @Override
    public void inserirDAO(Usuario usuario) {
//      Converte a String da data de admissao para o formato do banco de dados
//      SimpleDateFormat dataString = new SimpleDateFormat("yyyy/MM/dd");
//      Date  dataUtil = dataString.parse(usuario.getData_admissao());
//      java.sql.Date dataBanco = new java.sql.Date(dataUtil.getTime());
        conexao = Conexao.getConnection();//start na conexão com banco de dados
        try {
            String sql = "INSERT INTO usuario (usu_login, usu_senha, usu_funcionario_fk) VALUES (?,?,?);";
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, usuario.getLogin());
            ps.setString(2, MetodosUteis.encrypta(usuario.getSenha()));
            ps.setInt(3, usuario.getFuncionario().getId());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: INSERÇÃO DE USUARIO NÃO REALIZADA.", ex);
        }
    }

    @Override
    public void excluirDAO(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editarDAO(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario buscarDAO(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Usuario> listarDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario autenticarDAO(String login, String senha) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            Usuario usu = new Usuario();
            String sql = "SELECT * FROM usuario WHERE usu_login=? AND usu_senha=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {//resultSet sempre inicia antes do primeiro registro, aqui verifica se o registro foi ou não foi encontrado
                usu = null;
            } else {
                usu.setId(rs.getInt("usu_id"));
                usu.setLogin(rs.getString("usu_login"));
                usu.setSenha(rs.getString("usu_senha"));
                try {//as próximas instruções buscam o funcionário dono do usuário
                    FuncionarioDAO acessoBD = DAOFactory.gerarFuncionarioDAO();
                    Funcionario funcAutenticado = acessoBD.buscarPorIdDAO(rs.getInt("usu_funcionario_fk"));
                    usu.setFuncionario(funcAutenticado);
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                    throw new RuntimeException("Erro. Não foi possível procurar um funcionário associado às credenciais informadas.", ex);
                }
            }
            ps.close();
            rs.close();
            conexao.close();
            return usu;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE USUÁRIO NÃO REALIZADA.", ex);
        }
    }

}
