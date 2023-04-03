package br.edu.cio.model.dao.mysql;

import br.edu.cio.model.Animal;
import br.edu.cio.model.AnimalInfo;
import br.edu.cio.model.AtividadeAnimal;
import br.edu.cio.model.Cio;
import br.edu.cio.model.Configuracao;
import br.edu.cio.model.LogCio;
import br.edu.cio.model.dao.AnimalDAO;
import br.edu.cio.model.dao.AnimalInfoDAO;
import br.edu.cio.model.dao.CioDAO;
import br.edu.cio.model.dao.ConfiguracaoDAO;
import br.edu.cio.model.dao.LeituraDAO;
import br.edu.cio.model.dao.MetodosInferenciaCioDAO;
import br.edu.cio.util.Conexao;
import br.edu.cio.util.DAOFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class CioDAOMySQL implements CioDAO {
    //CioDAO acessoBDCio = DAOFactory.gerarCioDAO();
    AnimalInfoDAO acessoBDAnimalInfo = DAOFactory.gerarAnimalInfoDAO();
    AnimalDAO acessoBDAnimal = DAOFactory.gerarAnimalDAO();
    LeituraDAO acessoBDLeitura = DAOFactory.gerarLeituraDAO();
    MetodosInferenciaCioDAO acessoBDmic = DAOFactory.gerarMetodosInferenciaCioDAO();
    ConfiguracaoDAO acessoBDConfig = DAOFactory.gerarConfiguracaoDAO();
    
    SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatoDataBr = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatoHoraBr = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat formatoCompletoUSA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat formatoCompletoBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    Connection conexao;
    public CioDAOMySQL(){//construtor
        //conexao = Conexao.getConnection();//start na conexão com banco de dados
    }
    @Override
    public void cadastrarAutomaticoDAO(Cio cio) {
        try {
            conexao = Conexao.getConnection();
            SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
            java.sql.Date dataRegistro = new java.sql.Date(formata.parse(cio.getData_registro()).getTime());
            java.sql.Time horaRegistro = new java.sql.Time(formataHora.parse(cio.getHora_registro()).getTime());
            java.sql.Date dataAlteracao = new java.sql.Date(formata.parse(cio.getData_registro()).getTime());
            java.sql.Time horaAlteracao = new java.sql.Time(formataHora.parse(cio.getHora_registro()).getTime());
            String sql = "INSERT INTO  cio (cio_dt_registro, cio_hr_registro, cio_dt_ult_alteracao, cio_hr_ult_alteracao, cio_observacao, cio_status, cio_mtd_registro, "
                    + "cio_mtd_id, cio_animal_fk, cio_leitura_fk, cio_atividade_fk, cio_codigo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setDate(1, dataRegistro);
            ps.setTime(2, horaRegistro);
            ps.setDate(3, dataAlteracao);
            ps.setTime(4, horaAlteracao);
            ps.setString(5, cio.getObservacao());
            ps.setString(6, cio.getStatus());
            ps.setString(7, cio.getMetodo_registro());
            ps.setString(8, cio.getMetodo_id());
            ps.setInt(9, cio.getAnimal().getId());
            ps.setInt(10, cio.getLeitura().getId());
            ps.setInt(11, cio.getAtividadeAnimal().getId());
            ps.setString(12, cio.getCodigo());
            ps.executeUpdate();
            System.out.println("!!! CIO DO ANIMAL DE CÓDIGO "+cio.getAnimal().getCodigo()+" REGISTRADO COM SUCESSO.");
            ps.close();
            conexao.close();
        } catch (ParseException | SQLException ex) {
            Logger.getLogger(LeituraDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: REGISTRO DE CIO NÃO REALIZADO.", ex);
        }
    }

    @Override
    public Cio buscarUltimoRegistroDAO() {
        try {
            SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
            conexao = Conexao.getConnection();
            Cio cio = new Cio();
            String  sql = "SELECT * FROM cio ORDER BY cio_id DESC LIMIT 1";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                cio = null;
            else {
                cio.setId(rs.getInt("cio_id"));
                cio.setStatus(rs.getString("cio_status"));
                cio.setObservacao(rs.getString("cio_observacao"));
                cio.setMetodo_id(rs.getString("cio_mtd_id"));
                cio.setMetodo_registro(rs.getString("cio_mtd_registro"));
                cio.setData_inicio(null);
                cio.setData_termino(null);
                cio.setData_previsao_termino(null);
                cio.setData_registro(null);
                cio.setData_ultima_alteracao(null);
                cio.setHora_inicio(null);
                cio.setHora_termino(null);
                cio.setHora_previsao_termino(null);
                cio.setHora_ultima_alteracao(null);
                cio.setHora_registro(null);
                cio.setDuracao(rs.getInt("cio_duracao"));
                if(rs.getDate("cio_dt_inicio") != null)
                    cio.setData_inicio(formata.format(rs.getDate("cio_dt_inicio")));

                if(rs.getDate("cio_dt_termino") != null)
                    cio.setData_termino(formata.format(rs.getDate("cio_dt_termino")));

                if(rs.getDate("cio_dt_prev_termino") != null)
                    cio.setData_previsao_termino(formata.format(rs.getDate("cio_dt_prev_termino")));

                if(rs.getDate("cio_dt_registro") != null)
                    cio.setData_registro(formata.format(rs.getDate("cio_dt_registro")));

                if(rs.getDate("cio_dt_ult_alteracao") != null)
                    cio.setData_ultima_alteracao(formata.format(rs.getDate("cio_dt_ult_alteracao")));

                if(rs.getTime("cio_hr_inicio") != null)
                    cio.setHora_inicio(formataHora.format(rs.getTime("cio_hr_inicio")));

                if(rs.getTime("cio_hr_termino") != null)
                    cio.setHora_termino(formataHora.format(rs.getTime("cio_hr_termino")));

                if(rs.getTime("cio_hr_prev_termino") != null)
                    cio.setHora_previsao_termino(formataHora.format(rs.getTime("cio_hr_prev_termino")));

                if(rs.getTime("cio_hr_registro") != null)
                    cio.setHora_registro(formataHora.format(rs.getTime("cio_hr_registro")));

                if(rs.getTime("cio_hr_ult_alteracao") != null)
                    cio.setHora_ultima_alteracao(formataHora.format(rs.getTime("cio_hr_ult_alteracao")));


                cio.setAnimal(acessoBDAnimal.buscarPorIdDAO(rs.getInt("cio_animal_fk")));
                cio.setLeitura(acessoBDLeitura.buscarPorIdDAO(rs.getInt("cio_leitura_fk")));
                cio.setAtividadeAnimal(acessoBDmic.buscarAtividadeAnimalPorIdLeituraDAO(rs.getInt("cio_leitura_fk")));
            }          
            ps.close();
            rs.close();
            conexao.close();
            return cio;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: BUSCA DE CIO NÃO REALIZADA.", ex);
        }
    }

    @Override
    public Cio buscarUltimoDoAnimalDAO(int idAnimal) {
        try {
            SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
            conexao = Conexao.getConnection();
            Cio cio = new Cio();
            String  sql = "SELECT * FROM cio WHERE cio_animal_fk=? ORDER BY cio_id DESC LIMIT 1";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idAnimal);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                cio = null;
            else {
                cio.setId(rs.getInt("cio_id"));
                cio.setStatus(rs.getString("cio_status"));
                cio.setObservacao(rs.getString("cio_observacao"));
                cio.setMetodo_id(rs.getString("cio_mtd_id"));
                cio.setMetodo_registro(rs.getString("cio_mtd_registro"));
                cio.setCodigo(rs.getString("cio_codigo"));
                cio.setData_inicio(null);
                cio.setData_termino(null);
                cio.setData_previsao_termino(null);
                cio.setData_registro(null);
                cio.setData_ultima_alteracao(null);
                cio.setHora_inicio(null);
                cio.setHora_termino(null);
                cio.setHora_previsao_termino(null);
                cio.setHora_ultima_alteracao(null);
                cio.setHora_registro(null);
                cio.setDuracao(rs.getInt("cio_duracao"));
                if(rs.getDate("cio_dt_inicio") != null)
                    cio.setData_inicio(formata.format(rs.getDate("cio_dt_inicio")));

                if(rs.getDate("cio_dt_termino") != null)
                    cio.setData_termino(formata.format(rs.getDate("cio_dt_termino")));

                if(rs.getDate("cio_dt_prev_termino") != null)
                    cio.setData_previsao_termino(formata.format(rs.getDate("cio_dt_prev_termino")));

                if(rs.getDate("cio_dt_registro") != null)
                    cio.setData_registro(formata.format(rs.getDate("cio_dt_registro")));

                if(rs.getDate("cio_dt_ult_alteracao") != null)
                    cio.setData_ultima_alteracao(formata.format(rs.getDate("cio_dt_ult_alteracao")));

                if(rs.getTime("cio_hr_inicio") != null)
                    cio.setHora_inicio(formataHora.format(rs.getTime("cio_hr_inicio")));

                if(rs.getTime("cio_hr_termino") != null)
                    cio.setHora_termino(formataHora.format(rs.getTime("cio_hr_termino")));

                if(rs.getTime("cio_hr_prev_termino") != null)
                    cio.setHora_previsao_termino(formataHora.format(rs.getTime("cio_hr_prev_termino")));

                if(rs.getTime("cio_hr_registro") != null)
                    cio.setHora_registro(formataHora.format(rs.getTime("cio_hr_registro")));

                if(rs.getTime("cio_hr_ult_alteracao") != null)
                    cio.setHora_ultima_alteracao(formataHora.format(rs.getTime("cio_hr_ult_alteracao")));

                cio.setAnimal(acessoBDAnimal.buscarPorIdDAO(rs.getInt("cio_animal_fk")));
                cio.setLeitura(acessoBDLeitura.buscarPorIdDAO(rs.getInt("cio_leitura_fk")));
                cio.setAtividadeAnimal(acessoBDmic.buscarAtividadeAnimalPorIdLeituraDAO(rs.getInt("cio_leitura_fk")));
            }          
            ps.close();
            rs.close();
            conexao.close();
            return cio;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: BUSCA DE CIO NÃO REALIZADA.", ex);
        }
    }

    @Override
    public void editarAutomaticoDAO(Cio cio) {
        SimpleDateFormat formata = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat formataBr = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
        try {
            conexao = Conexao.getConnection();
            java.sql.Date dataRegistro = new java.sql.Date(formataBr.parse(cio.getData_registro()).getTime());
            java.sql.Time horaRegistro = new java.sql.Time(formataHora.parse(cio.getHora_registro()).getTime());
            java.sql.Date dataAlteracao = new java.sql.Date(formataBr.parse(cio.getData_ultima_alteracao()).getTime());
            java.sql.Time horaAlteracao = new java.sql.Time(formataHora.parse(cio.getHora_ultima_alteracao()).getTime());
            String sql = "UPDATE cio SET cio_dt_registro=?, cio_hr_registro=?, cio_dt_ult_alteracao=?, cio_hr_ult_alteracao=?, cio_observacao=?,"
                    + " cio_status=?, cio_mtd_registro=?, cio_mtd_id=?, cio_animal_fk=?, cio_leitura_fk=?, cio_atividade_fk=? WHERE cio_id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setDate(1, dataRegistro);
            ps.setTime(2, horaRegistro);
            ps.setDate(3, dataAlteracao);
            ps.setTime(4, horaAlteracao);
            ps.setString(5, cio.getObservacao());
            ps.setString(6, cio.getStatus());
            ps.setString(7, cio.getMetodo_registro());
            ps.setString(8, cio.getMetodo_id());
            ps.setInt(9, cio.getAnimal().getId());
            ps.setInt(10, cio.getLeitura().getId());
            ps.setInt(11, cio.getAtividadeAnimal().getId());
            ps.setInt(12, cio.getId());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EDIÇÃO DE CIO NÃO REALIZADA.", ex);
        } catch (ParseException ex) {
            Logger.getLogger(CioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Cio buscarPorIdDAO(int idCio) {
        try {
            SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
            conexao = Conexao.getConnection();
            Cio cio = new Cio();
            String  sql = "SELECT * FROM cio WHERE cio_id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idCio);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                cio = null;
            else {
                cio.setId(rs.getInt("cio_id"));
                cio.setStatus(rs.getString("cio_status"));
                cio.setObservacao(rs.getString("cio_observacao"));
                cio.setMetodo_id(rs.getString("cio_mtd_id"));
                cio.setMetodo_registro(rs.getString("cio_mtd_registro"));
                cio.setCodigo(rs.getString("cio_codigo"));
                cio.setData_inicio(null);
                cio.setData_termino(null);
                cio.setData_previsao_termino(null);
                cio.setData_registro(null);
                cio.setData_ultima_alteracao(null);
                cio.setHora_inicio(null);
                cio.setHora_termino(null);
                cio.setHora_previsao_termino(null);
                cio.setHora_ultima_alteracao(null);
                cio.setHora_registro(null);
                cio.setDuracao(rs.getInt("cio_duracao"));
                if(rs.getDate("cio_dt_inicio") != null)
                    cio.setData_inicio(formata.format(rs.getDate("cio_dt_inicio")));

                if(rs.getDate("cio_dt_termino") != null)
                    cio.setData_termino(formata.format(rs.getDate("cio_dt_termino")));

                if(rs.getDate("cio_dt_prev_termino") != null)
                    cio.setData_previsao_termino(formata.format(rs.getDate("cio_dt_prev_termino")));

                if(rs.getDate("cio_dt_registro") != null)
                    cio.setData_registro(formata.format(rs.getDate("cio_dt_registro")));

                if(rs.getDate("cio_dt_ult_alteracao") != null)
                    cio.setData_ultima_alteracao(formata.format(rs.getDate("cio_dt_ult_alteracao")));

                if(rs.getTime("cio_hr_inicio") != null)
                    cio.setHora_inicio(formataHora.format(rs.getTime("cio_hr_inicio")));

                if(rs.getTime("cio_hr_termino") != null)
                    cio.setHora_termino(formataHora.format(rs.getTime("cio_hr_termino")));

                if(rs.getTime("cio_hr_prev_termino") != null)
                    cio.setHora_previsao_termino(formataHora.format(rs.getTime("cio_hr_prev_termino")));

                if(rs.getTime("cio_hr_registro") != null)
                    cio.setHora_registro(formataHora.format(rs.getTime("cio_hr_registro")));

                if(rs.getTime("cio_hr_ult_alteracao") != null)
                    cio.setHora_ultima_alteracao(formataHora.format(rs.getTime("cio_hr_ult_alteracao")));

                cio.setAnimal(acessoBDAnimal.buscarPorIdDAO(rs.getInt("cio_animal_fk")));
                cio.setLeitura(acessoBDLeitura.buscarPorIdDAO(rs.getInt("cio_leitura_fk")));
                cio.setAtividadeAnimal(acessoBDmic.buscarAtividadeAnimalPorId(rs.getInt("cio_atividade_fk")));
            }          
            ps.close();
            rs.close();
            conexao.close();
            return cio;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: BUSCA DE CIO NÃO REALIZADA.", ex);
        }
    }
    
    @Override
    public void inserirLogDAO(LogCio lc) {
        try {
            conexao = Conexao.getConnection();
            SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
            java.sql.Date dataRegistro = new java.sql.Date(formata.parse(lc.getData()).getTime());
            java.sql.Time horaRegistro = new java.sql.Time(formataHora.parse(lc.getHora()).getTime());
            String sql = "INSERT INTO  log_cio (log_data, log_hora, log_operacao, log_status_cio, log_descricao, log_cio_fk) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setDate(1, dataRegistro);
            ps.setTime(2, horaRegistro);
            ps.setString(3, lc.getOperacao());
            ps.setString(4, lc.getCio().getStatus());
            ps.setString(5, lc.getDescricao());;
            ps.setInt(6, lc.getCio().getId());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (ParseException | SQLException ex) {
            Logger.getLogger(LeituraDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: LOG DE CIO NÃO ATUALIZADO.", ex);
        }
    }

    @Override
    public void cadastrarDAO(Cio cio) {
        SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
        try {
            conexao = Conexao.getConnection();
            java.sql.Date dtRegistro = null;
            java.sql.Time hrRegistro = null;
            java.sql.Date dtAlteracao = null;
            java.sql.Time hrAlteracao = null;
            java.sql.Date dtInicio = null;
            java.sql.Time hrInicio = null;
            java.sql.Date dtPrevTermino = null;
            java.sql.Time hrPrevTermino = null;
            java.sql.Date dtTermino = null;
            java.sql.Time hrTermino = null;
       
            if(cio.getData_registro() != null)
                dtRegistro = new java.sql.Date(formata.parse(cio.getData_registro()).getTime());
            if(cio.getHora_registro() != null)        
                hrRegistro = new java.sql.Time(formataHora.parse(cio.getHora_registro()).getTime());
            if(cio.getData_ultima_alteracao()!= null)
                dtAlteracao = new java.sql.Date(formata.parse(cio.getData_ultima_alteracao()).getTime());
            if(cio.getHora_ultima_alteracao() != null)
                hrAlteracao = new java.sql.Time(formataHora.parse(cio.getHora_ultima_alteracao()).getTime());
            if(cio.getData_inicio() != null)
                dtInicio = new java.sql.Date(formata.parse(cio.getData_inicio()).getTime());
            if(cio.getHora_inicio()!= null)        
                hrInicio = new java.sql.Time(formataHora.parse(cio.getHora_inicio()).getTime());
            if(cio.getData_previsao_termino()!= null)
                dtPrevTermino = new java.sql.Date(formata.parse(cio.getData_previsao_termino()).getTime());
            if(cio.getHora_previsao_termino()!= null)
                hrPrevTermino = new java.sql.Time(formataHora.parse(cio.getHora_previsao_termino()).getTime());
            if(cio.getData_termino()!= null)
                dtTermino = new java.sql.Date(formata.parse(cio.getData_termino()).getTime());
            if(cio.getHora_termino()!= null)
                hrTermino = new java.sql.Time(formataHora.parse(cio.getHora_termino()).getTime());
            
            String sql = "INSERT INTO cio (cio_dt_registro, cio_hr_registro, cio_status, cio_dt_ult_alteracao, cio_hr_ult_alteracao, cio_mtd_id, cio_mtd_registro,"
                    + " cio_dt_inicio, cio_hr_inicio, cio_dt_prev_termino, cio_hr_prev_termino, cio_dt_termino, cio_hr_termino, cio_duracao, cio_observacao,"
                    + " cio_animal_fk, cio_leitura_fk, cio_atividade_fk, cio_codigo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setDate(1, dtRegistro);
            ps.setTime(2, hrRegistro);
            ps.setString(3, cio.getStatus());
            ps.setDate(4, dtAlteracao);
            ps.setTime(5, hrAlteracao);
            ps.setString(6, cio.getMetodo_id());
            ps.setString(7, cio.getMetodo_registro());
            ps.setDate(8, dtInicio);
            ps.setTime(9, hrInicio);
            ps.setDate(10, dtPrevTermino);
            ps.setTime(11, hrPrevTermino);
            ps.setDate(12, dtTermino);
            ps.setTime(13, hrTermino);
            ps.setInt(14, cio.getDuracao());
            ps.setString(15, cio.getObservacao());
            if(cio.getAnimal() != null)
                ps.setInt(16, cio.getAnimal().getId());
            else
                ps.setString(16, null);
            
            if(cio.getLeitura() !=  null)
                ps.setInt(17, cio.getLeitura().getId());
            else 
                ps.setString(17, null);

            if(cio.getAtividadeAnimal() != null)
                ps.setInt(18, cio.getAtividadeAnimal().getId());
            else
                ps.setString(18, null);
            ps.setString(19, cio.getCodigo());
            ps.executeUpdate();
            ps.close();
            conexao.close();
            System.out.println("!!! CIO DO ANIMAL DE CÓDIGO "+cio.getAnimal().getCodigo()+" REGISTRADO COM SUCESSO.");
        } catch (ParseException | SQLException ex) {
            Logger.getLogger(LeituraDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CIO NÃO CADASTRADO.", ex);
        }
    }

    @Override
    public void editarDAO(Cio cio) {
        SimpleDateFormat formata = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formataBr = new SimpleDateFormat("dd/MM/yyyy");
        try {
            conexao = Conexao.getConnection();
            java.sql.Date dtRegistro = null;
            java.sql.Time hrRegistro = null;
            java.sql.Date dtAlteracao = null;
            java.sql.Time hrAlteracao = null;
            java.sql.Date dtInicio = null;
            java.sql.Time hrInicio = null;
            java.sql.Date dtPrevTermino = null;
            java.sql.Time hrPrevTermino = null;
            java.sql.Date dtTermino = null;
            java.sql.Time hrTermino = null;
       
            if(cio.getData_registro() != null)
                dtRegistro = new java.sql.Date(formataBr.parse(cio.getData_registro()).getTime());
            if(cio.getHora_registro() != null)        
                hrRegistro = new java.sql.Time(formataHora.parse(cio.getHora_registro()).getTime());
            if(cio.getData_ultima_alteracao()!= null)
                dtAlteracao = new java.sql.Date(formataBr.parse(cio.getData_ultima_alteracao()).getTime());
            if(cio.getHora_ultima_alteracao() != null)
                hrAlteracao = new java.sql.Time(formataHora.parse(cio.getHora_ultima_alteracao()).getTime());
            if(cio.getData_inicio() != null)
                dtInicio = new java.sql.Date(formataBr.parse(cio.getData_inicio()).getTime());
            if(cio.getHora_inicio()!= null)        
                hrInicio = new java.sql.Time(formataHora.parse(cio.getHora_inicio()).getTime());
            if(cio.getData_previsao_termino()!= null)
                dtPrevTermino = new java.sql.Date(formataBr.parse(cio.getData_previsao_termino()).getTime());
            if(cio.getHora_previsao_termino()!= null)
                hrPrevTermino = new java.sql.Time(formataHora.parse(cio.getHora_previsao_termino()).getTime());
            if(cio.getData_termino()!= null)
                dtTermino = new java.sql.Date(formataBr.parse(cio.getData_termino()).getTime());
            if(cio.getHora_termino()!= null)
                hrTermino = new java.sql.Time(formataHora.parse(cio.getHora_termino()).getTime());
            
            String sql = "UPDATE cio SET cio_dt_registro=?, cio_hr_registro=?, cio_status=?, cio_dt_ult_alteracao=?, cio_hr_ult_alteracao=?, cio_mtd_id=?, cio_mtd_registro=?,"
                    + " cio_dt_inicio=?, cio_hr_inicio=?, cio_dt_prev_termino=?, cio_hr_prev_termino=?, cio_dt_termino=?, cio_hr_termino=?, cio_duracao=?, cio_observacao=?,"
                    + " cio_animal_fk=?, cio_leitura_fk=?, cio_atividade_fk=? WHERE cio_id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setDate(1, dtRegistro);
            ps.setTime(2, hrRegistro);
            ps.setString(3, cio.getStatus());
            ps.setDate(4, dtAlteracao);
            ps.setTime(5, hrAlteracao);
            ps.setString(6, cio.getMetodo_id());
            ps.setString(7, cio.getMetodo_registro());
            ps.setDate(8, dtInicio);
            ps.setTime(9, hrInicio);
            ps.setDate(10, dtPrevTermino);
            ps.setTime(11, hrPrevTermino);
            ps.setDate(12, dtTermino);
            ps.setTime(13, hrTermino);
            ps.setInt(14, cio.getDuracao());
            ps.setString(15, cio.getObservacao());
            ps.setInt(16, cio.getAnimal().getId());
            ps.setInt(17, cio.getLeitura().getId());
            ps.setInt(18, cio.getAtividadeAnimal().getId());
            ps.setInt(19, cio.getId());
            ps.executeUpdate();
            ps.close();
            conexao.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EDIÇÃO DE CIO NÃO REALIZADA.", ex);
        } catch (ParseException ex) {
            Logger.getLogger(CioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Cio> listarPendentesDAO() {
        SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
        try {
            conexao = Conexao.getConnection();
            List<Cio> ciclosPendentes = new ArrayList<>();
            String  sql = "SELECT * FROM cio WHERE cio_status='SUSPEITO' OR cio_status='PREVISTO' ORDER BY cio_dt_ult_alteracao DESC";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                ciclosPendentes = null;
            else {
                do {
                    Cio cio = new Cio();
                    cio.setId(rs.getInt("cio_id"));
                    cio.setStatus(rs.getString("cio_status"));
                    cio.setObservacao(rs.getString("cio_observacao"));
                    cio.setMetodo_id(rs.getString("cio_mtd_id"));
                    cio.setMetodo_registro(rs.getString("cio_mtd_registro"));
                    cio.setCodigo(rs.getString("cio_codigo"));
                    cio.setData_inicio(null);
                    cio.setData_termino(null);
                    cio.setData_previsao_termino(null);
                    cio.setData_registro(null);
                    cio.setData_ultima_alteracao(null);
                    cio.setHora_inicio(null);
                    cio.setHora_termino(null);
                    cio.setHora_previsao_termino(null);
                    cio.setHora_ultima_alteracao(null);
                    cio.setHora_registro(null);
                    cio.setDuracao(rs.getInt("cio_duracao"));
                    if(rs.getDate("cio_dt_inicio") != null)
                        cio.setData_inicio(formata.format(rs.getDate("cio_dt_inicio")));

                    if(rs.getDate("cio_dt_termino") != null)
                        cio.setData_termino(formata.format(rs.getDate("cio_dt_termino")));

                    if(rs.getDate("cio_dt_prev_termino") != null)
                        cio.setData_previsao_termino(formata.format(rs.getDate("cio_dt_prev_termino")));

                    if(rs.getDate("cio_dt_registro") != null)
                        cio.setData_registro(formata.format(rs.getDate("cio_dt_registro")));

                    if(rs.getDate("cio_dt_ult_alteracao") != null)
                        cio.setData_ultima_alteracao(formata.format(rs.getDate("cio_dt_ult_alteracao")));

                    if(rs.getTime("cio_hr_inicio") != null)
                        cio.setHora_inicio(formataHora.format(rs.getTime("cio_hr_inicio")));

                    if(rs.getTime("cio_hr_termino") != null)
                        cio.setHora_termino(formataHora.format(rs.getTime("cio_hr_termino")));

                    if(rs.getTime("cio_hr_prev_termino") != null)
                        cio.setHora_previsao_termino(formataHora.format(rs.getTime("cio_hr_prev_termino")));

                    if(rs.getTime("cio_hr_registro") != null)
                        cio.setHora_registro(formataHora.format(rs.getTime("cio_hr_registro")));

                    if(rs.getTime("cio_hr_ult_alteracao") != null)
                        cio.setHora_ultima_alteracao(formataHora.format(rs.getTime("cio_hr_ult_alteracao")));

                    cio.setAnimal(acessoBDAnimal.buscarPorIdDAO(rs.getInt("cio_animal_fk")));
                    cio.setLeitura(acessoBDLeitura.buscarPorIdDAO(rs.getInt("cio_leitura_fk")));
                    cio.setAtividadeAnimal(acessoBDmic.buscarAtividadeAnimalPorIdLeituraDAO(rs.getInt("cio_leitura_fk")));

                    ciclosPendentes.add(cio);
                
                } while (rs.next());
            }         
            ps.close();
            rs.close();
            conexao.close();
            return ciclosPendentes;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: BUSCA DE CIO NÃO REALIZADA.", ex);
        }
    }

    @Override
    public void avaliarDAO(Cio cio) {
        try {
            java.sql.Date dtAlteracao = null;
            java.sql.Time hrAlteracao = null;
            java.sql.Date dtInicio = null;
            java.sql.Time hrInicio = null;
            java.sql.Date dtPrevTermino = null;
            java.sql.Time hrPrevTermino = null;
            java.sql.Date dtPrevProxCio = null;
            java.sql.Time hrPrevProxCio = null;
            java.sql.Date dtTermino = null;
            java.sql.Time hrTermino = null;
            if(cio.getData_ultima_alteracao()!= null)
                dtAlteracao = new java.sql.Date(formatoDataBr.parse(cio.getData_ultima_alteracao()).getTime());
            if(cio.getHora_ultima_alteracao() != null)
                hrAlteracao = new java.sql.Time(formatoHoraBr.parse(cio.getHora_ultima_alteracao()).getTime());
            
            GregorianCalendar dthInicioCio = new GregorianCalendar();
            GregorianCalendar dthTerminoCio = new GregorianCalendar();
            GregorianCalendar dthAtual = new GregorianCalendar();
            GregorianCalendar dthPrevTerminoCio = new GregorianCalendar();
            GregorianCalendar dthFimCioAnterior = new GregorianCalendar();
            GregorianCalendar dthPrevProxCio = new GregorianCalendar();
            
            Cio cioCadastrado = buscarPorIdDAO(cio.getId());
            AnimalInfo aninfoCio = acessoBDAnimalInfo.buscarPorIdAnimalDAO(cioCadastrado.getAnimal().getId());
            int duracao_media_cio_animal = aninfoCio.getDuracao_media_cio();
            
            Configuracao config = acessoBDConfig.carregarDAO(1);
            
            switch(cio.getStatus()){
                case "CONFIRMADO":{
                    conexao = Conexao.getConnection();
                    if(cio.getData_inicio() != null)
                        dtInicio = new java.sql.Date(formatoDataBr.parse(cio.getData_inicio()).getTime());
                    if(cio.getHora_inicio()!= null)        
                        hrInicio = new java.sql.Time(formatoHoraBr.parse(cio.getHora_inicio()).getTime());

                    dthPrevTerminoCio.setTime(formatoCompletoBr.parse(cio.getData_inicio()+ " " + cio.getHora_inicio()));
                    dthPrevTerminoCio.add(Calendar.HOUR, duracao_media_cio_animal);
                    formatoDataBr.setCalendar(dthPrevTerminoCio);
                    cio.setData_previsao_termino(formatoDataBr.format(dthPrevTerminoCio.getTime()));//obrigatório
                    cio.setHora_previsao_termino(formatoHoraBr.format(dthPrevTerminoCio.getTime()));//obrigatório
                    dtPrevTermino = new java.sql.Date(formatoDataBr.parse(cio.getData_previsao_termino()).getTime());
                    hrPrevTermino = new java.sql.Time(formatoHoraBr.parse(cio.getHora_previsao_termino()).getTime());

                    dthInicioCio.setTime(formatoCompletoBr.parse(cio.getData_inicio()+ " " + cio.getHora_inicio()));
                    dthAtual.setTime(formatoCompletoBr.parse(formatoDataBr.format(formatoDataBr.parse(config.getDt_ult_captura()))+ " " + config.getHr_ult_captura()));
                    long dif_ms = dthAtual.getTimeInMillis() - dthInicioCio.getTimeInMillis();
                    int duracao_cio = (int) (dif_ms/(60*60*1000));

                    String sql = "UPDATE cio SET cio_status=?, cio_dt_ult_alteracao=?, cio_hr_ult_alteracao=?, cio_dt_inicio=?, cio_hr_inicio=?, cio_dt_prev_termino=?, "
                            + "cio_hr_prev_termino=?, cio_duracao=?, cio_observacao=? WHERE cio_id = ?";
                    PreparedStatement ps = conexao.prepareStatement(sql);
                    ps.setString(1, cio.getStatus());
                    ps.setDate(2, dtAlteracao);
                    ps.setTime(3, hrAlteracao);
                    ps.setDate(4, dtInicio);
                    ps.setTime(5, hrInicio);
                    ps.setDate(6, dtPrevTermino);
                    ps.setTime(7, hrPrevTermino);
                    ps.setInt(8, duracao_cio);
                    ps.setString(9, cio.getObservacao());
                    ps.setInt(10, cio.getId());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    /*  ATUALIZANDO INFO ANIMAL */
                    try{
                        conexao = Conexao.getConnection();
                        /* Calculando Novo Intervalo_padrão_anestro do animal */
                        dthFimCioAnterior.setTime(formatoCompletoBr.parse(aninfoCio.getDt_fim_ult_cio()+ " " + aninfoCio.getHr_fim_ult_cio()));
                        dthInicioCio.setTime(formatoCompletoBr.parse(cio.getData_inicio()+ " " + cio.getHora_inicio()));
                        dif_ms = dthInicioCio.getTimeInMillis() - dthFimCioAnterior.getTimeInMillis();
                        int ultimo_dias_em_anestro = (int) (dif_ms/(24*60*60*1000));
                        int novo_inter_pad_anestro = (int)((aninfoCio.getIntervalo_padrao_anestro() + ultimo_dias_em_anestro) / 2);
                        /* Calculando A previsão do próximo cio */
                        dthPrevProxCio.setTime(formatoCompletoBr.parse(cio.getData_inicio()+ " " + cio.getHora_inicio()));
                        dthPrevProxCio.add(Calendar.HOUR, duracao_media_cio_animal + (24*novo_inter_pad_anestro));
                        formatoDataBr.setCalendar(dthPrevProxCio);
                        dtPrevProxCio = new java.sql.Date(formatoDataBr.parse(formatoDataBr.format(dthPrevProxCio.getTime())).getTime());
                        hrPrevProxCio = new java.sql.Time(formatoHoraBr.parse(formatoHoraBr.format(dthPrevProxCio.getTime())).getTime());

                        sql = "UPDATE info_animal SET info_status_cio=?, info_sob_alerta=?, info_intervalo_padrao_anestro=?, info_dt_prev_ini_prox_cio=?, info_hr_prev_ini_prox_cio=?,"
                            + "info_dias_em_anestro=?, info_dt_ini_ult_cio=?, info_hr_ini_ult_cio=?, info_tempo_atual_anestro=? WHERE info_animal_fk=?";
                        ps = conexao.prepareStatement(sql);
                        ps.setInt(1, 1);
                        ps.setInt(2, 0);
                        ps.setInt(3, novo_inter_pad_anestro);
                        ps.setDate(4, dtPrevProxCio);
                        ps.setTime(5, hrPrevProxCio);
                        ps.setInt(6, 0);
                        ps.setDate(7, dtInicio);
                        ps.setTime(8, hrInicio);
                        ps.setString(9, "PRINCIPIO");
                        ps.setInt(10, cioCadastrado.getAnimal().getId());
                        ps.executeUpdate();
                        ps.close();
                        conexao.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                        throw new RuntimeException("ERRO DE SQL: INFORMAÇÕES DO ANIMAL NÃO FORAM ATUALIZADAS.", ex);
                    }
                    break;
                }
                
                case "ENCERRADO":{
                    conexao = Conexao.getConnection();
                    if(cio.getData_inicio() != null)
                        dtInicio = new java.sql.Date(formatoDataBr.parse(cio.getData_inicio()).getTime());
                    if(cio.getHora_inicio()!= null)        
                        hrInicio = new java.sql.Time(formatoHoraBr.parse(cio.getHora_inicio()).getTime());

                    dthPrevTerminoCio.setTime(formatoCompletoBr.parse(cio.getData_inicio()+ " " + cio.getHora_inicio()));
                    dthPrevTerminoCio.add(Calendar.HOUR, duracao_media_cio_animal);
                    formatoDataBr.setCalendar(dthPrevTerminoCio);
                    cio.setData_previsao_termino(formatoDataBr.format(dthPrevTerminoCio.getTime()));//obrigatório
                    cio.setHora_previsao_termino(formatoHoraBr.format(dthPrevTerminoCio.getTime()));//obrigatório
                    dtPrevTermino = new java.sql.Date(formatoDataBr.parse(cio.getData_previsao_termino()).getTime());
                    hrPrevTermino = new java.sql.Time(formatoHoraBr.parse(cio.getHora_previsao_termino()).getTime());

                    if(cio.getData_termino()!= null)
                        dtTermino = new java.sql.Date(formatoDataBr.parse(cio.getData_termino()).getTime());
                    if(cio.getHora_termino()!= null)
                        hrTermino = new java.sql.Time(formatoHoraBr.parse(cio.getHora_termino()).getTime());

                    dthInicioCio.setTime(formatoCompletoBr.parse(cio.getData_inicio()+ " " + cio.getHora_inicio()));
                    dthTerminoCio.setTime(formatoCompletoBr.parse(cio.getData_termino()+ " " + cio.getHora_termino()));
                    long dif_ms = dthTerminoCio.getTimeInMillis() - dthInicioCio.getTimeInMillis();
                    int duracao_cio = (int) (dif_ms/(60*60*1000));
                    
                    String sql = "UPDATE cio SET cio_status=?, cio_dt_ult_alteracao=?, cio_hr_ult_alteracao=?, cio_dt_inicio=?, cio_hr_inicio=?, cio_dt_prev_termino=?, "
                            + "cio_hr_prev_termino=?, cio_dt_termino=?, cio_hr_termino=?, cio_duracao=?, cio_observacao=? WHERE cio_id = ?";
                    PreparedStatement ps = conexao.prepareStatement(sql);
                    ps.setString(1, cio.getStatus());
                    ps.setDate(2, dtAlteracao);
                    ps.setTime(3, hrAlteracao);
                    ps.setDate(4, dtInicio);
                    ps.setTime(5, hrInicio);
                    ps.setDate(6, dtPrevTermino);
                    ps.setTime(7, hrPrevTermino);
                    ps.setDate(8, dtTermino);
                    ps.setTime(9, hrTermino);
                    ps.setInt(10, duracao_cio);
                    ps.setString(11, cio.getObservacao());
                    ps.setInt(12, cio.getId());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    /*  ATUALIZANDO INFO ANIMAL */
                    try{
                        conexao = Conexao.getConnection();
                        /* Calculando Novo Intervalo_padrão_anestro do animal */
                        int novo_inter_pad_anestro = aninfoCio.getIntervalo_padrao_anestro();
                        if(aninfoCio.getDt_fim_ult_cio() != null){
                            dthFimCioAnterior.setTime(formatoCompletoBr.parse(aninfoCio.getDt_fim_ult_cio()+ " " + aninfoCio.getHr_fim_ult_cio()));
                            dthInicioCio.setTime(formatoCompletoBr.parse(cio.getData_inicio()+ " " + cio.getHora_inicio()));
                            dif_ms = dthInicioCio.getTimeInMillis() - dthFimCioAnterior.getTimeInMillis();
                            int ultimo_dias_em_anestro = (int) (dif_ms/(24*60*60*1000));
                            novo_inter_pad_anestro = (int)((aninfoCio.getIntervalo_padrao_anestro() + ultimo_dias_em_anestro) / 2);
                        } 
                        
                        /* Calculando A previsão do próximo cio */
                        dthPrevProxCio.setTime(formatoCompletoBr.parse(cio.getData_termino()+ " " + cio.getHora_termino()));
                        dthPrevProxCio.add(Calendar.HOUR, 24*(novo_inter_pad_anestro));
                        formatoDataBr.setCalendar(dthPrevProxCio);
                        dtPrevProxCio = new java.sql.Date(formatoDataBr.parse(formatoDataBr.format(dthPrevProxCio.getTime())).getTime());
                        hrPrevProxCio = new java.sql.Time(formatoHoraBr.parse(formatoHoraBr.format(dthPrevProxCio.getTime())).getTime());

                        int duracao_media = duracao_cio;
                        if(duracao_media_cio_animal > 10){
                            duracao_media = (int)(duracao_cio + duracao_media_cio_animal)/2;
                        }
                        
                        sql = "UPDATE info_animal SET info_status_cio=?, info_sob_alerta=?, info_intervalo_padrao_anestro=?, info_dt_prev_ini_prox_cio=?, info_hr_prev_ini_prox_cio=?,"
                            + "info_dias_em_anestro=?, info_dt_ini_ult_cio=?, info_hr_ini_ult_cio=?, info_dt_fim_ult_cio=?, info_hr_fim_ult_cio=?, info_tempo_atual_anestro=?, "
                            + "info_aprox_cio=?, info_duracao_media_cio=? WHERE info_animal_fk=?";
                        ps = conexao.prepareStatement(sql);
                        ps.setInt(1, 0);
                        ps.setInt(2, 0);
                        ps.setInt(3, novo_inter_pad_anestro);
                        ps.setDate(4, dtPrevProxCio);
                        ps.setTime(5, hrPrevProxCio);
                        ps.setInt(6, 0);
                        ps.setDate(7, dtInicio);
                        ps.setTime(8, hrInicio);
                        ps.setDate(9, dtTermino);
                        ps.setTime(10, hrTermino);
                        ps.setString(11, "PRINCIPIO");
                        ps.setInt(12, 0);
                        ps.setInt(13, duracao_media);
                        ps.setInt(14, cioCadastrado.getAnimal().getId());
                        ps.executeUpdate();
                        ps.close();
                        conexao.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                        throw new RuntimeException("ERRO DE SQL: INFORMAÇÕES DO ANIMAL NÃO FORAM ATUALIZADAS.", ex);
                    }
                    break;
                }
                
                case "DESCARTADO":{
                    conexao = Conexao.getConnection();
                    String sql = "UPDATE cio SET cio_status=?, cio_dt_ult_alteracao=?, cio_hr_ult_alteracao=?, cio_observacao=? WHERE cio_id = ?";
                    PreparedStatement ps = conexao.prepareStatement(sql);
                    ps.setString(1, cio.getStatus());
                    ps.setDate(2, dtAlteracao);
                    ps.setTime(3, hrAlteracao);
                    ps.setString(4, cio.getObservacao());
                    ps.setInt(5, cio.getId());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    /*  ATUALIZANDO INFO ANIMAL */
                    try{
                        conexao = Conexao.getConnection();
                        /* Calculando Novo Intervalo_padrão_anestro do animal */
                        sql = "UPDATE info_animal SET info_status_cio=?, info_sob_alerta=? where info_animal_fk=?";
                        ps = conexao.prepareStatement(sql);
                        ps.setInt(1, 0);
                        ps.setInt(2, 0);
                        ps.setInt(3, cioCadastrado.getAnimal().getId());
                        ps.executeUpdate();
                        ps.close();
                        conexao.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                        throw new RuntimeException("ERRO DE SQL: INFORMAÇÕES DO ANIMAL NÃO FORAM ATUALIZADAS.", ex);
                    }
                    break;
                }
                default:{
                    conexao = Conexao.getConnection();
                    String sql = "UPDATE cio SET cio_status=?, cio_dt_ult_alteracao=?, cio_hr_ult_alteracao=?, cio_observacao=? WHERE cio_id = ?";
                    PreparedStatement ps1 = conexao.prepareStatement(sql);
                    ps1.setString(1, cio.getStatus());
                    ps1.setDate(2, dtAlteracao);
                    ps1.setTime(3, hrAlteracao);
                    ps1.setString(4, cio.getObservacao());
                    ps1.setInt(5, cio.getId());
                    ps1.executeUpdate();
                    ps1.close();
                    conexao.close();
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: AVALIAÇÃO DE CIO NÃO REALIZADA.", ex);
        } catch (ParseException ex) {
            Logger.getLogger(CioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Cio> buscarPendentesAnimalDAO(int idAnimal) {
        SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
        try {
            conexao = Conexao.getConnection();
            List<Cio> ciclosPendentes = new ArrayList<>();
            String  sql = "SELECT * FROM cio WHERE cio_animal_fk=? AND (cio_status='SUSPEITO' OR cio_status='PREVISTO') ORDER BY cio_dt_ult_alteracao DESC";            
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idAnimal);
            ResultSet rs = ps.executeQuery();
                       
            if (!rs.next()) 
                ciclosPendentes = null;
            else {
                do {
                    Cio cio = new Cio();
                    cio.setId(rs.getInt("cio_id"));
                    cio.setStatus(rs.getString("cio_status"));
                    cio.setObservacao(rs.getString("cio_observacao"));
                    cio.setMetodo_id(rs.getString("cio_mtd_id"));
                    cio.setMetodo_registro(rs.getString("cio_mtd_registro"));
                    cio.setCodigo(rs.getString("cio_codigo"));
                    cio.setData_inicio(null);
                    cio.setData_termino(null);
                    cio.setData_previsao_termino(null);
                    cio.setData_registro(null);
                    cio.setData_ultima_alteracao(null);
                    cio.setHora_inicio(null);
                    cio.setHora_termino(null);
                    cio.setHora_previsao_termino(null);
                    cio.setHora_ultima_alteracao(null);
                    cio.setHora_registro(null);
                    cio.setDuracao(rs.getInt("cio_duracao"));
                    if(rs.getDate("cio_dt_inicio") != null)
                        cio.setData_inicio(formata.format(rs.getDate("cio_dt_inicio")));
                    if(rs.getDate("cio_dt_termino") != null)
                        cio.setData_termino(formata.format(rs.getDate("cio_dt_termino")));
                    if(rs.getDate("cio_dt_prev_termino") != null)
                        cio.setData_previsao_termino(formata.format(rs.getDate("cio_dt_prev_termino")));
                    if(rs.getDate("cio_dt_registro") != null)
                        cio.setData_registro(formata.format(rs.getDate("cio_dt_registro")));
                    if(rs.getDate("cio_dt_ult_alteracao") != null)
                        cio.setData_ultima_alteracao(formata.format(rs.getDate("cio_dt_ult_alteracao")));
                    if(rs.getTime("cio_hr_inicio") != null)
                        cio.setHora_inicio(formataHora.format(rs.getTime("cio_hr_inicio")));
                    if(rs.getTime("cio_hr_termino") != null)
                        cio.setHora_termino(formataHora.format(rs.getTime("cio_hr_termino")));
                    if(rs.getTime("cio_hr_prev_termino") != null)
                        cio.setHora_previsao_termino(formataHora.format(rs.getTime("cio_hr_prev_termino")));
                    if(rs.getTime("cio_hr_registro") != null)
                        cio.setHora_registro(formataHora.format(rs.getTime("cio_hr_registro")));
                    if(rs.getTime("cio_hr_ult_alteracao") != null)
                        cio.setHora_ultima_alteracao(formataHora.format(rs.getTime("cio_hr_ult_alteracao")));
                    cio.setAnimal(acessoBDAnimal.buscarPorIdDAO(rs.getInt("cio_animal_fk")));
                    cio.setLeitura(acessoBDLeitura.buscarPorIdDAO(rs.getInt("cio_leitura_fk")));
                    cio.setAtividadeAnimal(acessoBDmic.buscarAtividadeAnimalPorIdLeituraDAO(rs.getInt("cio_leitura_fk")));
                    ciclosPendentes.add(cio);                
                } while (rs.next());
            }         
            ps.close();
            rs.close();
            conexao.close();
            return ciclosPendentes;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: BUSCA DE CIO NÃO REALIZADA.", ex);
        }
    }

    @Override
    public List<Cio> buscarDAO(String valorBusca) {
        SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
        try {
            conexao = Conexao.getConnection();
            List<Cio> ciclosBusca = new ArrayList<>();
            String sql;
            PreparedStatement ps;
            if ("".equals(valorBusca)) {
                sql = "SELECT * FROM cio ORDER BY cio_dt_registro DESC";
                ps = conexao.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM cio WHERE cio_codigo like ? ORDER BY cio_dt_registro DESC";
                ps = conexao.prepareStatement(sql);
                ps.setString(1, "%" + valorBusca + "%");
            }
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) 
                ciclosBusca = null;
            else {
                do {
                    Cio cio = new Cio();
                    cio.setId(rs.getInt("cio_id"));
                    cio.setStatus(rs.getString("cio_status"));
                    cio.setObservacao(rs.getString("cio_observacao"));
                    cio.setMetodo_id(rs.getString("cio_mtd_id"));
                    cio.setMetodo_registro(rs.getString("cio_mtd_registro"));
                    cio.setCodigo(rs.getString("cio_codigo"));
                    cio.setData_inicio(null);
                    cio.setData_termino(null);
                    cio.setData_previsao_termino(null);
                    cio.setData_registro(null);
                    cio.setData_ultima_alteracao(null);
                    cio.setHora_inicio(null);
                    cio.setHora_termino(null);
                    cio.setHora_previsao_termino(null);
                    cio.setHora_ultima_alteracao(null);
                    cio.setHora_registro(null);
                    cio.setDuracao(rs.getInt("cio_duracao"));
                    if(rs.getDate("cio_dt_inicio") != null)
                        cio.setData_inicio(formata.format(rs.getDate("cio_dt_inicio")));
                    if(rs.getDate("cio_dt_termino") != null)
                        cio.setData_termino(formata.format(rs.getDate("cio_dt_termino")));
                    if(rs.getDate("cio_dt_prev_termino") != null)
                        cio.setData_previsao_termino(formata.format(rs.getDate("cio_dt_prev_termino")));
                    if(rs.getDate("cio_dt_registro") != null)
                        cio.setData_registro(formata.format(rs.getDate("cio_dt_registro")));
                    if(rs.getDate("cio_dt_ult_alteracao") != null)
                        cio.setData_ultima_alteracao(formata.format(rs.getDate("cio_dt_ult_alteracao")));
                    if(rs.getTime("cio_hr_inicio") != null)
                        cio.setHora_inicio(formataHora.format(rs.getTime("cio_hr_inicio")));
                    if(rs.getTime("cio_hr_termino") != null)
                        cio.setHora_termino(formataHora.format(rs.getTime("cio_hr_termino")));
                    if(rs.getTime("cio_hr_prev_termino") != null)
                        cio.setHora_previsao_termino(formataHora.format(rs.getTime("cio_hr_prev_termino")));
                    if(rs.getTime("cio_hr_registro") != null)
                        cio.setHora_registro(formataHora.format(rs.getTime("cio_hr_registro")));
                    if(rs.getTime("cio_hr_ult_alteracao") != null)
                        cio.setHora_ultima_alteracao(formataHora.format(rs.getTime("cio_hr_ult_alteracao")));
                    cio.setAnimal(acessoBDAnimal.buscarPorIdDAO(rs.getInt("cio_animal_fk")));
                    cio.setLeitura(acessoBDLeitura.buscarPorIdDAO(rs.getInt("cio_leitura_fk")));
                    cio.setAtividadeAnimal(acessoBDmic.buscarAtividadeAnimalPorIdLeituraDAO(rs.getInt("cio_leitura_fk")));
                    ciclosBusca.add(cio);                
                } while (rs.next());
            }         
            ps.close();
            rs.close();
            conexao.close();
            return ciclosBusca;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: BUSCA DE CIO NÃO REALIZADA.", ex);
        }
    }

    @Override
    public void editarManualDAO(Cio cio) {
        try {
            java.sql.Date dtAlteracao = null;
            java.sql.Time hrAlteracao = null;
            java.sql.Date dtInicio = null;
            java.sql.Time hrInicio = null;
            java.sql.Date dtPrevTermino = null;
            java.sql.Time hrPrevTermino = null;
            java.sql.Date dtPrevProxCio = null;
            java.sql.Time hrPrevProxCio = null;
            java.sql.Date dtTermino = null;
            java.sql.Time hrTermino = null;
            if(cio.getData_ultima_alteracao()!= null)
                dtAlteracao = new java.sql.Date(formatoDataBr.parse(cio.getData_ultima_alteracao()).getTime());
            if(cio.getHora_ultima_alteracao() != null)
                hrAlteracao = new java.sql.Time(formatoHoraBr.parse(cio.getHora_ultima_alteracao()).getTime());
            
            GregorianCalendar dthInicioCio = new GregorianCalendar();
            GregorianCalendar dthTerminoCio = new GregorianCalendar();
            GregorianCalendar dthAtual = new GregorianCalendar();
            GregorianCalendar dthPrevTerminoCio = new GregorianCalendar();
            GregorianCalendar dthFimCioAnterior = new GregorianCalendar();
            GregorianCalendar dthPrevProxCio = new GregorianCalendar();
            
            Cio cioCadastrado = buscarPorIdDAO(cio.getId());
            AnimalInfo aninfoCio = acessoBDAnimalInfo.buscarPorIdAnimalDAO(cioCadastrado.getAnimal().getId());
            int duracao_media_cio_animal = aninfoCio.getDuracao_media_cio();
            
            Configuracao config = acessoBDConfig.carregarDAO(1);
            
            switch(cio.getStatus()){
                case "CONFIRMADO":{
                    conexao = Conexao.getConnection();
                    if(cio.getData_inicio() != null)
                        dtInicio = new java.sql.Date(formatoDataBr.parse(cio.getData_inicio()).getTime());
                    if(cio.getHora_inicio()!= null)        
                        hrInicio = new java.sql.Time(formatoHoraBr.parse(cio.getHora_inicio()).getTime());

                    dthPrevTerminoCio.setTime(formatoCompletoBr.parse(cio.getData_inicio()+ " " + cio.getHora_inicio()));
                    dthPrevTerminoCio.add(Calendar.HOUR, duracao_media_cio_animal);
                    formatoDataBr.setCalendar(dthPrevTerminoCio);
                    cio.setData_previsao_termino(formatoDataBr.format(dthPrevTerminoCio.getTime()));//obrigatório
                    cio.setHora_previsao_termino(formatoHoraBr.format(dthPrevTerminoCio.getTime()));//obrigatório
                    dtPrevTermino = new java.sql.Date(formatoDataBr.parse(cio.getData_previsao_termino()).getTime());
                    hrPrevTermino = new java.sql.Time(formatoHoraBr.parse(cio.getHora_previsao_termino()).getTime());

                    dthInicioCio.setTime(formatoCompletoBr.parse(cio.getData_inicio()+ " " + cio.getHora_inicio()));
                    dthAtual.setTime(formatoCompletoBr.parse(formatoDataBr.format(formatoDataBr.parse(config.getDt_ult_captura()))+ " " + config.getHr_ult_captura()));
                    long dif_ms = dthAtual.getTimeInMillis() - dthInicioCio.getTimeInMillis();
                    int duracao_cio = (int) (dif_ms/(60*60*1000));

                    String sql = "UPDATE cio SET cio_status=?, cio_dt_ult_alteracao=?, cio_hr_ult_alteracao=?, cio_dt_inicio=?, cio_hr_inicio=?, cio_dt_prev_termino=?, "
                            + "cio_hr_prev_termino=?, cio_duracao=?, cio_observacao=? WHERE cio_id = ?";
                    PreparedStatement ps = conexao.prepareStatement(sql);
                    ps.setString(1, cio.getStatus());
                    ps.setDate(2, dtAlteracao);
                    ps.setTime(3, hrAlteracao);
                    ps.setDate(4, dtInicio);
                    ps.setTime(5, hrInicio);
                    ps.setDate(6, dtPrevTermino);
                    ps.setTime(7, hrPrevTermino);
                    ps.setInt(8, duracao_cio);
                    ps.setString(9, cio.getObservacao());
                    ps.setInt(10, cio.getId());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    /*  ATUALIZANDO INFO ANIMAL */
                    try{
                        conexao = Conexao.getConnection();
                        /* Calculando Novo Intervalo_padrão_anestro do animal */
                        dthFimCioAnterior.setTime(formatoCompletoBr.parse(aninfoCio.getDt_fim_ult_cio()+ " " + aninfoCio.getHr_fim_ult_cio()));
                        dthInicioCio.setTime(formatoCompletoBr.parse(cio.getData_inicio()+ " " + cio.getHora_inicio()));
                        dif_ms = dthInicioCio.getTimeInMillis() - dthFimCioAnterior.getTimeInMillis();
                        int ultimo_dias_em_anestro = (int) (dif_ms/(24*60*60*1000));
                        int novo_inter_pad_anestro = (int)((aninfoCio.getIntervalo_padrao_anestro() + ultimo_dias_em_anestro) / 2);
                        /* Calculando A previsão do próximo cio */
                        dthPrevProxCio.setTime(formatoCompletoBr.parse(cio.getData_inicio()+ " " + cio.getHora_inicio()));
                        dthPrevProxCio.add(Calendar.HOUR, duracao_media_cio_animal + (24*novo_inter_pad_anestro));
                        formatoDataBr.setCalendar(dthPrevProxCio);
                        dtPrevProxCio = new java.sql.Date(formatoDataBr.parse(formatoDataBr.format(dthPrevProxCio.getTime())).getTime());
                        hrPrevProxCio = new java.sql.Time(formatoHoraBr.parse(formatoHoraBr.format(dthPrevProxCio.getTime())).getTime());

                        sql = "UPDATE info_animal SET info_status_cio=?, info_sob_alerta=?, info_intervalo_padrao_anestro=?, info_dt_prev_ini_prox_cio=?, info_hr_prev_ini_prox_cio=?,"
                            + "info_dias_em_anestro=?, info_dt_ini_ult_cio=?, info_hr_ini_ult_cio=?, info_tempo_atual_anestro=? WHERE info_animal_fk=?";
                        ps = conexao.prepareStatement(sql);
                        ps.setInt(1, 1);
                        ps.setInt(2, 0);
                        ps.setInt(3, novo_inter_pad_anestro);
                        ps.setDate(4, dtPrevProxCio);
                        ps.setTime(5, hrPrevProxCio);
                        ps.setInt(6, 0);
                        ps.setDate(7, dtInicio);
                        ps.setTime(8, hrInicio);
                        ps.setString(9, "PRINCIPIO");
                        ps.setInt(10, cioCadastrado.getAnimal().getId());
                        ps.executeUpdate();
                        ps.close();
                        conexao.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                        throw new RuntimeException("ERRO DE SQL: INFORMAÇÕES DO ANIMAL NÃO FORAM ATUALIZADAS.", ex);
                    }
                    break;
                }
                
                case "ENCERRADO":{
                    conexao = Conexao.getConnection();
                    if(cio.getData_inicio() != null)
                        dtInicio = new java.sql.Date(formatoDataBr.parse(cio.getData_inicio()).getTime());
                    if(cio.getHora_inicio()!= null)        
                        hrInicio = new java.sql.Time(formatoHoraBr.parse(cio.getHora_inicio()).getTime());

                    dthPrevTerminoCio.setTime(formatoCompletoBr.parse(cio.getData_inicio()+ " " + cio.getHora_inicio()));
                    dthPrevTerminoCio.add(Calendar.HOUR, duracao_media_cio_animal);
                    formatoDataBr.setCalendar(dthPrevTerminoCio);
                    cio.setData_previsao_termino(formatoDataBr.format(dthPrevTerminoCio.getTime()));//obrigatório
                    cio.setHora_previsao_termino(formatoHoraBr.format(dthPrevTerminoCio.getTime()));//obrigatório
                    dtPrevTermino = new java.sql.Date(formatoDataBr.parse(cio.getData_previsao_termino()).getTime());
                    hrPrevTermino = new java.sql.Time(formatoHoraBr.parse(cio.getHora_previsao_termino()).getTime());

                    if(cio.getData_termino()!= null)
                        dtTermino = new java.sql.Date(formatoDataBr.parse(cio.getData_termino()).getTime());
                    if(cio.getHora_termino()!= null)
                        hrTermino = new java.sql.Time(formatoHoraBr.parse(cio.getHora_termino()).getTime());

                    dthInicioCio.setTime(formatoCompletoBr.parse(cio.getData_inicio()+ " " + cio.getHora_inicio()));
                    dthTerminoCio.setTime(formatoCompletoBr.parse(cio.getData_termino()+ " " + cio.getHora_termino()));
                    long dif_ms = dthTerminoCio.getTimeInMillis() - dthInicioCio.getTimeInMillis();
                    int duracao_cio = (int) (dif_ms/(60*60*1000));
                    
                    String sql = "UPDATE cio SET cio_status=?, cio_dt_ult_alteracao=?, cio_hr_ult_alteracao=?, cio_dt_inicio=?, cio_hr_inicio=?, cio_dt_prev_termino=?, "
                            + "cio_hr_prev_termino=?, cio_dt_termino=?, cio_hr_termino=?, cio_duracao=?, cio_observacao=? WHERE cio_id = ?";
                    PreparedStatement ps = conexao.prepareStatement(sql);
                    ps.setString(1, cio.getStatus());
                    ps.setDate(2, dtAlteracao);
                    ps.setTime(3, hrAlteracao);
                    ps.setDate(4, dtInicio);
                    ps.setTime(5, hrInicio);
                    ps.setDate(6, dtPrevTermino);
                    ps.setTime(7, hrPrevTermino);
                    ps.setDate(8, dtTermino);
                    ps.setTime(9, hrTermino);
                    ps.setInt(10, duracao_cio);
                    ps.setString(11, cio.getObservacao());
                    ps.setInt(12, cio.getId());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    /*  ATUALIZANDO INFO ANIMAL */
                    try{
                        conexao = Conexao.getConnection();
                        /* Calculando Novo Intervalo_padrão_anestro do animal */
                        int novo_inter_pad_anestro = aninfoCio.getIntervalo_padrao_anestro();
                        if(aninfoCio.getDt_fim_ult_cio() != null){
                            dthFimCioAnterior.setTime(formatoCompletoBr.parse(aninfoCio.getDt_fim_ult_cio()+ " " + aninfoCio.getHr_fim_ult_cio()));
                            dthInicioCio.setTime(formatoCompletoBr.parse(cio.getData_inicio()+ " " + cio.getHora_inicio()));
                            dif_ms = dthInicioCio.getTimeInMillis() - dthFimCioAnterior.getTimeInMillis();
                            int ultimo_dias_em_anestro = (int) (dif_ms/(24*60*60*1000));
                            novo_inter_pad_anestro = (int)((aninfoCio.getIntervalo_padrao_anestro() + ultimo_dias_em_anestro) / 2);
                        } 
                        
                        /* Calculando A previsão do próximo cio */
                        dthPrevProxCio.setTime(formatoCompletoBr.parse(cio.getData_termino()+ " " + cio.getHora_termino()));
                        dthPrevProxCio.add(Calendar.HOUR, 24*(novo_inter_pad_anestro));
                        formatoDataBr.setCalendar(dthPrevProxCio);
                        dtPrevProxCio = new java.sql.Date(formatoDataBr.parse(formatoDataBr.format(dthPrevProxCio.getTime())).getTime());
                        hrPrevProxCio = new java.sql.Time(formatoHoraBr.parse(formatoHoraBr.format(dthPrevProxCio.getTime())).getTime());

                        int duracao_media = duracao_cio;
                        if(duracao_media_cio_animal > 10){
                            duracao_media = (int)(duracao_cio + duracao_media_cio_animal)/2;
                        }
                        
                        sql = "UPDATE info_animal SET info_status_cio=?, info_sob_alerta=?, info_intervalo_padrao_anestro=?, info_dt_prev_ini_prox_cio=?, info_hr_prev_ini_prox_cio=?,"
                            + "info_dias_em_anestro=?, info_dt_ini_ult_cio=?, info_hr_ini_ult_cio=?, info_dt_fim_ult_cio=?, info_hr_fim_ult_cio=?, info_tempo_atual_anestro=?, "
                            + "info_aprox_cio=?, info_duracao_media_cio=? WHERE info_animal_fk=?";
                        ps = conexao.prepareStatement(sql);
                        ps.setInt(1, 0);
                        ps.setInt(2, 0);
                        ps.setInt(3, novo_inter_pad_anestro);
                        ps.setDate(4, dtPrevProxCio);
                        ps.setTime(5, hrPrevProxCio);
                        ps.setInt(6, 0);
                        ps.setDate(7, dtInicio);
                        ps.setTime(8, hrInicio);
                        ps.setDate(9, dtTermino);
                        ps.setTime(10, hrTermino);
                        ps.setString(11, "PRINCIPIO");
                        ps.setInt(12, 0);
                        ps.setInt(13, duracao_media);
                        ps.setInt(14, cioCadastrado.getAnimal().getId());
                        ps.executeUpdate();
                        ps.close();
                        conexao.close();
                        try {
                            conexao = Conexao.getConnection();
                            sql = "INSERT INTO previsao_cio (prevc_data, prevc_hora, prevc_animal_fk) VALUES (?, ?, ?)";
                            ps = conexao.prepareStatement(sql);
                            ps.setDate(1, dtPrevProxCio);
                            ps.setTime(2, hrPrevProxCio);
                            ps.setInt(3, cioCadastrado.getAnimal().getId());
                            ps.executeUpdate();
                            ps.close();
                            conexao.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                            throw new RuntimeException("ERRO DE SQL: PREVISÃO DE CIO NÃO CADASTRADA.", ex);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                        throw new RuntimeException("ERRO DE SQL: INFORMAÇÕES DO ANIMAL NÃO FORAM ATUALIZADAS.", ex);
                    }
                    break;
                }
                
                case "DESCARTADO":{
                    conexao = Conexao.getConnection();
                    String sql = "UPDATE cio SET cio_status=?, cio_dt_ult_alteracao=?, cio_hr_ult_alteracao=?, cio_observacao=? WHERE cio_id = ?";
                    PreparedStatement ps = conexao.prepareStatement(sql);
                    ps.setString(1, cio.getStatus());
                    ps.setDate(2, dtAlteracao);
                    ps.setTime(3, hrAlteracao);
                    ps.setString(4, cio.getObservacao());
                    ps.setInt(5, cio.getId());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    /*  ATUALIZANDO INFO ANIMAL */
                    try{
                        conexao = Conexao.getConnection();
                        /* Calculando Novo Intervalo_padrão_anestro do animal */
                        sql = "UPDATE info_animal SET info_status_cio=?, info_sob_alerta=? where info_animal_fk=?";
                        ps = conexao.prepareStatement(sql);
                        ps.setInt(1, 0);
                        ps.setInt(2, 0);
                        ps.setInt(3, cioCadastrado.getAnimal().getId());
                        ps.executeUpdate();
                        ps.close();
                        conexao.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                        throw new RuntimeException("ERRO DE SQL: INFORMAÇÕES DO ANIMAL NÃO FORAM ATUALIZADAS.", ex);
                    }
                    break;
                }
                
                case "CANCELADO":{
                    conexao = Conexao.getConnection();
                    String sql = "UPDATE cio SET cio_status=?, cio_dt_ult_alteracao=?, cio_hr_ult_alteracao=?, cio_observacao=? WHERE cio_id = ?";
                    PreparedStatement ps = conexao.prepareStatement(sql);
                    ps.setString(1, cio.getStatus());
                    ps.setDate(2, dtAlteracao);
                    ps.setTime(3, hrAlteracao);
                    ps.setString(4, cio.getObservacao());
                    ps.setInt(5, cio.getId());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    /*  ATUALIZANDO INFO ANIMAL */
                    try{
                        conexao = Conexao.getConnection();
                        GregorianCalendar dthInicioUltCio = new GregorianCalendar();
                        dthInicioUltCio.setTime(formatoCompletoBr.parse(aninfoCio.getDt_fim_ult_cio()+ " " + aninfoCio.getHr_fim_ult_cio()));
                        dthInicioUltCio.add(Calendar.HOUR, -aninfoCio.getDuracao_media_cio());
                        formatoCompletoBr.setCalendar(dthInicioUltCio);
                        dtInicio = new java.sql.Date(formatoDataBr.parse(formatoDataBr.format(dthInicioUltCio.getTime())).getTime());      //infoanimal 
                        hrInicio = new java.sql.Time(formatoHoraBr.parse(formatoHoraBr.format(dthInicioUltCio.getTime())).getTime());      //infoanimal 
                        
                        GregorianCalendar dthFimUltCio = new GregorianCalendar();
                        dthFimUltCio.setTime(formatoCompletoBr.parse(aninfoCio.getDt_fim_ult_cio()+ " " + aninfoCio.getHr_fim_ult_cio()));
                        formatoCompletoBr.setCalendar(dthFimUltCio);
                        dtTermino = new java.sql.Date(formatoDataBr.parse(formatoDataBr.format(dthFimUltCio.getTime())).getTime());      //infoanimal 
                        hrTermino = new java.sql.Time(formatoHoraBr.parse(formatoHoraBr.format(dthFimUltCio.getTime())).getTime());      //infoanimal 
                                             
                        /* Calculando A previsão do próximo cio */
                        dthPrevProxCio.setTime(formatoCompletoBr.parse(formatoDataBr.format(dthFimUltCio.getTime())+ " " + formatoHoraBr.format(dthFimUltCio.getTime())));
                        dthPrevProxCio.add(Calendar.HOUR, 24*aninfoCio.getIntervalo_padrao_anestro());
                        formatoCompletoBr.setCalendar(dthPrevProxCio);
                        dtPrevProxCio = new java.sql.Date(formatoDataBr.parse(formatoDataBr.format(dthPrevProxCio.getTime())).getTime());
                        hrPrevProxCio = new java.sql.Time(formatoHoraBr.parse(formatoHoraBr.format(dthPrevProxCio.getTime())).getTime());
                        
                        //JOptionPane.showMessageDialog(null, "Cancel - Inicio: "+dtInicio+" "+hrInicio+" | Termino: "+dtTermino+" "+hrTermino+" | Prev Prox: "+dtPrevProxCio+" "+hrPrevProxCio);
                        
                        dthTerminoCio.setTime(formatoCompletoBr.parse(formatoDataBr.format(dthFimUltCio.getTime())+ " " + formatoHoraBr.format(dthFimUltCio.getTime())));
                        dthAtual.setTime(formatoCompletoBr.parse(formatoDataBr.format(formatoDataBr.parse(config.getDt_ult_captura()))+ " " + config.getHr_ult_captura()));
                        long dif_ms = dthAtual.getTimeInMillis() - dthTerminoCio.getTimeInMillis();
                        int dias_anestro = (int) (dif_ms/(24*60*60*1000));
                        String tempo_anestro = "CURTO";
                        if(dias_anestro == 0)
                            tempo_anestro = "PRINCIPIO";
                        if(dias_anestro > 0 && dias_anestro <= 12)
                            tempo_anestro = "CURTO";
                        if(dias_anestro > 12 && dias_anestro <= 24)
                            tempo_anestro = "NORMAL";
                        if(dias_anestro > 24 && dias_anestro <= 30)
                            tempo_anestro = "LONGO";
                        if(dias_anestro > 30)
                            tempo_anestro = "MUITO LONGO";
                        
                        int aproximacaoCio = (int)((dias_anestro*10)/aninfoCio.getIntervalo_padrao_anestro());
                        if(aproximacaoCio > 10)
                            aproximacaoCio = 10;
                        if(aproximacaoCio < 0)
                            aproximacaoCio = 0;
                        
                        sql = "UPDATE info_animal SET info_status_cio=?, info_sob_alerta=?, info_dt_prev_ini_prox_cio=?, info_hr_prev_ini_prox_cio=?,"
                            + "info_dias_em_anestro=?, info_dt_ini_ult_cio=?, info_hr_ini_ult_cio=?, info_dt_fim_ult_cio=?, info_hr_fim_ult_cio=?, info_tempo_atual_anestro=?, "
                            + "info_aprox_cio=? WHERE info_animal_fk=?";
                        ps = conexao.prepareStatement(sql);
                        ps.setInt(1, 0);
                        ps.setInt(2, 0);
                        ps.setDate(3, dtPrevProxCio);
                        ps.setTime(4, hrPrevProxCio);
                        ps.setInt(5, dias_anestro);
                        ps.setDate(6, dtInicio);
                        ps.setTime(7, hrInicio);
                        ps.setDate(8, dtTermino);
                        ps.setTime(9, hrTermino);
                        ps.setString(10, tempo_anestro);
                        ps.setInt(11, aproximacaoCio);
                        ps.setInt(12, cioCadastrado.getAnimal().getId());
                        ps.executeUpdate();
                        ps.close();
                        conexao.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                        throw new RuntimeException("ERRO DE SQL: INFORMAÇÕES DO ANIMAL NÃO FORAM ATUALIZADAS.", ex);
                    }
                    break;
                }
                
                default:{
                    conexao = Conexao.getConnection();
                    String sql = "UPDATE cio SET cio_status=?, cio_dt_ult_alteracao=?, cio_hr_ult_alteracao=?, cio_observacao=? WHERE cio_id = ?";
                    PreparedStatement ps1 = conexao.prepareStatement(sql);
                    ps1.setString(1, cio.getStatus());
                    ps1.setDate(2, dtAlteracao);
                    ps1.setTime(3, hrAlteracao);
                    ps1.setString(4, cio.getObservacao());
                    ps1.setInt(5, cio.getId());
                    ps1.executeUpdate();
                    ps1.close();
                    conexao.close();
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: AVALIAÇÃO DE CIO NÃO REALIZADA.", ex);
        } catch (ParseException ex) {
            Logger.getLogger(CioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void inserirPrevisaoCio(String data, String hora, int idAnimal) {
        try {
            conexao = Conexao.getConnection();
            java.sql.Date dt = new java.sql.Date(formatoDataBr.parse(data).getTime()); 
            java.sql.Time hr = new java.sql.Time(formatoHoraBr.parse(hora).getTime());
            String sql = "INSERT INTO previsao_cio (prevc_data, prevc_hora, prevc_animal_fk) VALUES (?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setDate(1, dt);
            ps.setTime(2, hr);
            ps.setInt(3, idAnimal);
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: PREVISÃO DE CIO NÃO CADASTRADA.", ex);
        } catch (ParseException ex) {
            Logger.getLogger(CioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}