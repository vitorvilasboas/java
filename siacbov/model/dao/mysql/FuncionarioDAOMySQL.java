package br.edu.cio.model.dao.mysql;

import br.edu.cio.model.AreaPastagem;
import br.edu.cio.model.Funcionario;
import br.edu.cio.model.Lote;
import br.edu.cio.model.Propriedade;
import br.edu.cio.model.Usuario;
import br.edu.cio.model.dao.FuncionarioDAO;
import br.edu.cio.model.dao.PropriedadeDAO;
import br.edu.cio.util.Conexao;
import br.edu.cio.util.DAOFactory;
import br.edu.cio.util.MetodosUteis;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FuncionarioDAOMySQL implements FuncionarioDAO {
    Connection conexao;
    public FuncionarioDAOMySQL() {//construtor
    }
    PropriedadeDAO acessoBDPropriedade = DAOFactory.gerarPropriedadeDAO();
    @Override
    public void cadastrarDAO(Funcionario funcionario) {
        Funcionario aux = buscarUltimoRegistroDAO();
        int cod = Integer.parseInt(aux.getMatricula()) + 1;
        String codigoFunc = null;
        if (cod > 99) {
            codigoFunc = "0" + String.valueOf(cod);
        } else if (cod > 9) {
            codigoFunc = "00" + String.valueOf(cod);
        } else {
            codigoFunc = "000" + String.valueOf(cod);
        }
        conexao = Conexao.getConnection();//start na conexão com banco de dados
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "INSERT INTO funcionario (func_matricula, func_nome, func_cpf, func_rg, func_orgao_emissor, func_pis, func_pai, func_mae, func_endereco, func_cidade, func_uf, func_dt_nascimento, func_dt_admissao, func_cargo, func_fone, func_email, func_login , func_senha , func_propriedade_fk) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, codigoFunc);
            ps.setString(2, funcionario.getNome());
            ps.setString(3, funcionario.getCpf());
            ps.setString(4, funcionario.getRg());
            ps.setString(5, funcionario.getOrgao_emissor());
            ps.setString(6, funcionario.getPis());
            ps.setString(7, funcionario.getPai());
            ps.setString(8, funcionario.getMae());
            ps.setString(9, funcionario.getEndereco());
            ps.setString(10, funcionario.getCidade());
            ps.setString(11, funcionario.getUf());
            ps.setString(12, funcionario.getDt_nascimento());
            ps.setString(13, funcionario.getDt_admissao());
            ps.setString(14, funcionario.getCargo());
            ps.setString(15, funcionario.getFone());
            ps.setString(16, funcionario.getEmail());
            ps.setString(17, funcionario.getLogin());
            ps.setString(18, MetodosUteis.encrypta(funcionario.getSenha()));
            ps.setInt(19, funcionario.getPropriedade().getId());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: INSERÇÃO DE FUNCIONARIO NÃO REALIZADA.", ex);
        }
    }

    @Override
    public void editarDAO(Funcionario funcionario) {
        conexao = Conexao.getConnection();//start na conexão com banco de dados
        try {
            String sql1 = "UPDATE funcionario SET func_nome=?,"
                    + "func_cpf=?,func_rg=?,func_orgao_emissor=?,"
                    + "func_pis=?,func_pai=?,func_mae=?,func_endereco=?,"
                    + "func_cidade=?,func_uf=?,func_dt_nascimento=?,"
                    + "func_dt_admissao=?,func_cargo=?,func_fone=?,"
                    + "func_email=?,func_login=?,func_senha=?,func_propriedade_fk=? WHERE func_id = ?";
            PreparedStatement ps2 = conexao.prepareStatement(sql1);
            ps2.setString(1, funcionario.getNome());
            ps2.setString(2, funcionario.getCpf());
            ps2.setString(3, funcionario.getRg());
            ps2.setString(4, funcionario.getOrgao_emissor());
            ps2.setString(5, funcionario.getPis());
            ps2.setString(6, funcionario.getPai());
            ps2.setString(7, funcionario.getMae());
            ps2.setString(8, funcionario.getEndereco());
            ps2.setString(9, funcionario.getCidade());
            ps2.setString(10, funcionario.getUf());
            ps2.setString(11, funcionario.getDt_nascimento());
            ps2.setString(12, funcionario.getDt_admissao());
            ps2.setString(13, funcionario.getCargo());
            ps2.setString(14, funcionario.getFone());
            ps2.setString(15, funcionario.getEmail());
            ps2.setString(16, funcionario.getLogin());
            ps2.setString(17, funcionario.getSenha());
            ps2.setInt(18, funcionario.getPropriedade().getId());
            ps2.setInt(19, funcionario.getId());
            ps2.executeUpdate();
            ps2.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EDIÇÃO DE FUNCIONARIO NÃO REALIZADA.", ex);
        }
    }

    @Override
    public List<Funcionario> listarDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Funcionario buscarPorIdDAO(int id) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            Funcionario func = new Funcionario();
            String sql = "SELECT * FROM funcionario WHERE func_id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                func = null;
                System.out.println("Erro. Funcionário não encontrado. ID não cadastrado.");
            } else {
                //rs.beforeFirst();
                func.setId(rs.getInt("func_id"));
                func.setMatricula(rs.getString("func_matricula"));
                func.setNome(rs.getString("func_nome"));
                func.setCpf(rs.getString("func_cpf"));
                func.setRg(rs.getString("func_rg"));
                func.setOrgao_emissor(rs.getString("func_orgao_emissor"));
                func.setPis(rs.getString("func_pis"));
                func.setPai(rs.getString("func_pai"));
                func.setMae(rs.getString("func_mae"));
                func.setEndereco(rs.getString("func_endereco"));
                func.setCidade(rs.getString("func_cidade"));
                func.setUf(rs.getString("func_uf"));
                func.setDt_nascimento(rs.getString("func_dt_nascimento"));
                func.setDt_admissao(rs.getString("func_dt_admissao"));
                //func.setDt_nascimento(df.format(rs.getDate("func_dt_nascimento")));
                //func.setDt_admissao(df.format(rs.getDate("func_dt_admissao")));
                func.setCargo(rs.getString("func_cargo"));
                func.setFone(rs.getString("func_fone"));
                func.setEmail(rs.getString("func_email"));
                func.setLogin(rs.getString("func_login"));
                func.setSenha(rs.getString("func_senha"));
                try {
                    PropriedadeDAO acessoBDPropriedade = DAOFactory.gerarPropriedadeDAO();
                    func.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(rs.getInt("func_propriedade_fk")));
                } catch (SQLException ex) {
                    Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                    throw new RuntimeException("Erro. Não foi possível procurar a Propriedade a qual o funcionário pertence.", ex);
                }
            }
            ps.close();
            rs.close();
            conexao.close();
            return func;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE FUNCIONÁRIO NÃO REALIZADA 12345678910.", ex);
        }
    }

    @Override
    public List<Funcionario> listarPorPropriedadeDAO(int idPropriedade) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
            List<Funcionario> funcionarios = new ArrayList<>();
            String sql;
            sql = "SELECT * FROM funcionario where func_propriedade_fk=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idPropriedade);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                funcionarios = null;
            } else {
                do {
                    Funcionario func = new Funcionario();
                    func.setId(rs.getInt("func_id"));
                    func.setMatricula(rs.getString("func_matricula"));
                    func.setNome(rs.getString("func_nome"));
                    func.setCpf(rs.getString("func_cpf"));
                    func.setRg(rs.getString("func_rg"));
                    func.setOrgao_emissor(rs.getString("func_orgao_emissor"));
                    func.setPis(rs.getString("func_pis"));
                    func.setPai(rs.getString("func_pai"));
                    func.setMae(rs.getString("func_mae"));
                    func.setEndereco(rs.getString("func_endereco"));
                    func.setCidade(rs.getString("func_cidade"));
                    func.setUf(rs.getString("func_uf"));
                    func.setDt_nascimento(rs.getString("func_dt_nascimento"));
                    func.setDt_admissao(rs.getString("func_dt_admissao"));
                    //func.setDt_nascimento(df.format(rs.getDate("func_dt_nascimento")));
                    //func.setDt_admissao(df.format(rs.getDate("func_dt_admissao")));
                    func.setCargo(rs.getString("func_cargo"));
                    func.setFone(rs.getString("func_fone"));
                    func.setEmail(rs.getString("func_email"));
                    func.setLogin(rs.getString("func_login"));
                    func.setSenha(rs.getString("func_senha"));
                    try {
                        PropriedadeDAO acessoBDPropriedade = DAOFactory.gerarPropriedadeDAO();
                        func.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(rs.getInt("func_propriedade_fk")));
                    } catch (SQLException ex) {
                        Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                        throw new RuntimeException("Erro. Não foi possível procurar a Propriedade a qual o funcionário pertence.", ex);
                    }
                    funcionarios.add(func);
                } while (rs.next());
            }
            ps.close();
            rs.close();
            conexao.close();
            return funcionarios;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE FUNCIONÁRIOS NÃO REALIZADA.", ex);
        }
    }

    @Override
    public List<Funcionario> buscarDAO(String valorBusca) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            List<Funcionario> funcionario = new ArrayList<>();
            String sql;
            PreparedStatement ps;
            if ("".equals(valorBusca)) {
                sql = "SELECT * FROM funcionario";
                ps = conexao.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM funcionario where func_matricula like ? or func_cpf like  ?";
                ps = conexao.prepareStatement(sql);
                ps.setString(1, "%" + valorBusca + "%");
                ps.setString(2, "%" + valorBusca + "%"); 
            }
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                funcionario = null;
            } else {
                do {
                    Funcionario func = new Funcionario();
                    func.setId(rs.getInt("func_id"));
                    func.setMatricula(rs.getString("func_matricula"));
                    func.setNome(rs.getString("func_nome"));
                    func.setCpf(rs.getString("func_cpf"));
                    func.setRg(rs.getString("func_rg"));
                    func.setOrgao_emissor(rs.getString("func_orgao_emissor"));
                    func.setPis(rs.getString("func_pis"));
                    func.setPai(rs.getString("func_pai"));
                    func.setMae(rs.getString("func_mae"));
                    func.setEndereco(rs.getString("func_endereco"));
                    func.setCidade(rs.getString("func_cidade"));
                    func.setUf(rs.getString("func_uf"));
                    func.setDt_nascimento(rs.getString("func_dt_nascimento"));
                    func.setDt_admissao(rs.getString("func_dt_admissao"));
                    //func.setDt_nascimento(df.format(rs.getDate("func_dt_nascimento")));
                    //func.setDt_admissao(df.format(rs.getDate("func_dt_admissao")));
                    func.setCargo(rs.getString("func_cargo"));
                    func.setFone(rs.getString("func_fone"));
                    func.setEmail(rs.getString("func_email"));
                    func.setLogin(rs.getString("func_login"));
                    func.setSenha(rs.getString("func_senha"));
                    try {
                        PropriedadeDAO acessoBDPropriedade = DAOFactory.gerarPropriedadeDAO();
                        func.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(rs.getInt("func_propriedade_fk")));
                    } catch (SQLException ex) {
                        Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                        throw new RuntimeException("Erro. Não foi possível procurar a Propriedade a qual o funcionário pertence.", ex);
                    }
                    funcionario.add(func);
                } while (rs.next());
            }
            ps.close();
            rs.close();
            conexao.close();
            return funcionario;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE FUNCIONARIO NÃO REALIZADA.", ex);
        }
    }

    @Override
    public Funcionario buscarUltimoRegistroDAO() {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            Funcionario func = new Funcionario();
            String sql = "SELECT * FROM funcionario ORDER BY func_id DESC LIMIT 1";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                func = null;
            } else {
                func.setId(rs.getInt("func_id"));
                func.setMatricula(rs.getString("func_matricula"));
                func.setNome(rs.getString("func_nome"));
                func.setCpf(rs.getString("func_cpf"));
                func.setRg(rs.getString("func_rg"));
                func.setOrgao_emissor(rs.getString("func_orgao_emissor"));
                func.setPis(rs.getString("func_pis"));
                func.setPai(rs.getString("func_pai"));
                func.setMae(rs.getString("func_mae"));
                func.setEndereco(rs.getString("func_endereco"));
                func.setCidade(rs.getString("func_cidade"));
                func.setUf(rs.getString("func_uf"));
                func.setDt_nascimento(rs.getString("func_dt_nascimento"));
                func.setDt_admissao(rs.getString("func_dt_admissao"));
                //func.setDt_nascimento(df.format(rs.getDate("func_dt_nascimento")));
                //func.setDt_admissao(df.format(rs.getDate("func_dt_admissao")));
                func.setCargo(rs.getString("func_cargo"));
                func.setFone(rs.getString("func_fone"));
                func.setEmail(rs.getString("func_email"));
                func.setLogin(rs.getString("func_login"));
                func.setSenha(rs.getString("func_senha"));
                try {
                    PropriedadeDAO acessoBDPropriedade = DAOFactory.gerarPropriedadeDAO();
                    func.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(rs.getInt("func_propriedade_fk")));
                } catch (SQLException ex) {
                    Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                    throw new RuntimeException("Erro. Não foi possível procurar a Propriedade a qual o funcionário pertence.", ex);
                }
            }
            ps.close();
            rs.close();
            conexao.close();
            return func;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: BUSCA DE FUNCIONARIO NÃO REALIZADA.", ex);
        }
    }

    @Override
    public boolean excluirDAO(int id) {
        boolean excluido = false;
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "DELETE FROM funcionario WHERE func_id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            excluido = true;
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EXCLUSÃO DE FUNCIONÁRIO NÃO REALIZADA.", ex);
        }
        return excluido;
    }

    @Override
    public Funcionario autenticarDAO(String cpf, String email) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            Funcionario func = new Funcionario();
            String sql = "SELECT * FROM funcionario WHERE func_cpf=? AND func_email=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, cpf);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {//resultSet sempre inicia antes do primeiro registro, aqui verifica se o registro foi ou não foi encontrado
                func = null;
            } else {
                func.setId(rs.getInt("func_id"));
                func.setMatricula(rs.getString("func_matricula"));
                func.setNome(rs.getString("func_nome"));
                func.setCpf(rs.getString("func_cpf"));
                func.setRg(rs.getString("func_rg"));
                func.setOrgao_emissor(rs.getString("func_orgao_emissor"));
                func.setPis(rs.getString("func_pis"));
                func.setPai(rs.getString("func_pai"));
                func.setMae(rs.getString("func_mae"));
                func.setEndereco(rs.getString("func_endereco"));
                func.setCidade(rs.getString("func_cidade"));
                func.setUf(rs.getString("func_uf"));
                func.setDt_nascimento(rs.getString("func_dt_nascimento"));
                func.setDt_admissao(rs.getString("func_dt_admissao"));
                func.setCargo(rs.getString("func_cargo"));
                func.setFone(rs.getString("func_fone"));
                func.setEmail(rs.getString("func_email"));
                func.setLogin(rs.getString("func_login"));
                func.setSenha(rs.getString("func_senha"));
            }
            ps.close();
            rs.close();
            conexao.close();
            return func;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE Funcionario NÃO REALIZADA.", ex);
        }
    }
}