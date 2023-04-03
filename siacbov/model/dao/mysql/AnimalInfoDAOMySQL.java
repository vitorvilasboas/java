
package br.edu.cio.model.dao.mysql;

import br.edu.cio.model.Animal;
import br.edu.cio.model.AnimalInfo;
import br.edu.cio.model.Configuracao;
import br.edu.cio.model.dao.AnimalDAO;
import br.edu.cio.model.dao.AnimalInfoDAO;
import br.edu.cio.model.dao.ConfiguracaoDAO;
import br.edu.cio.util.Conexao;
import br.edu.cio.util.DAOFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class AnimalInfoDAOMySQL implements AnimalInfoDAO{
    ConfiguracaoDAO acessoBDConfig = DAOFactory.gerarConfiguracaoDAO();
    AnimalDAO acessoBDAnimal = DAOFactory.gerarAnimalDAO();
    
    Connection conexao;
    public AnimalInfoDAOMySQL(){//construtor
        //conexao = Conexao.getConnection();//start na conexão com banco de dados
    }
    
    @Override
    public void cadastrarDAO(AnimalInfo infoAnimal) {
        SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoDataBr = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHoraBr = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatoCompletoUSA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatoCompletoBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Animal ultimoAnimalCad = acessoBDAnimal.buscarUltimoRegistroDAO();
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "INSERT INTO info_animal (info_idade, info_dt_fim_ult_cio, info_hr_fim_ult_cio, info_dt_prev_ini_prox_cio, info_hr_prev_ini_prox_cio,"
                + " info_intervalo_padrao_anestro, info_tempo_atual_anestro, info_media_passos_hora, info_dias_em_anestro, info_status_atividade, info_status_cio,"
                + " info_oco_reprodutiva, info_oco_produtiva, info_sob_alerta, info_aprox_cio, info_duracao_media_cio, info_animal_fk)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, infoAnimal.getIdade());
            ps.setDate(2, new java.sql.Date(formatoDataUSA.parse(infoAnimal.getDt_fim_ult_cio()).getTime()));
            ps.setTime(3, new java.sql.Time(formatoHoraBr.parse(infoAnimal.getHr_fim_ult_cio()).getTime()));
            ps.setDate(4, new java.sql.Date(formatoDataUSA.parse(infoAnimal.getDt_prev_ini_prox_cio()).getTime()));
            ps.setTime(5, new java.sql.Time(formatoHoraBr.parse(infoAnimal.getHr_prev_ini_prox_cio()).getTime()));
            ps.setInt(6, infoAnimal.getIntervalo_padrao_anestro());
            ps.setString(7, infoAnimal.getTempo_atual_anestro());
            ps.setInt(8, infoAnimal.getMedia_passos_hora());
            ps.setInt(9, infoAnimal.getDias_em_anestro());
            ps.setString(10, infoAnimal.getStatus_atividade());
            ps.setInt(11, infoAnimal.getStatus_cio());
            ps.setString(12, infoAnimal.getOco_reprodutiva());
            ps.setString(13, infoAnimal.getOco_produtiva());
            ps.setInt(14, infoAnimal.getSob_alerta());
            ps.setInt(15, infoAnimal.getAprox_cio());
            ps.setInt(16, infoAnimal.getDuracao_media_cio());
            if(ultimoAnimalCad != null)
                ps.setInt(17, ultimoAnimalCad.getId());
            else
                ps.setString(17, null);
            ps.executeUpdate();
            try {
                String sql2 = "INSERT INTO previsao_cio (prevc_data, prevc_hora, prevc_animal_fk) VALUES (?, ?, ?)";
                PreparedStatement ps2 = conexao.prepareStatement(sql2);
                ps2.setDate(1, new java.sql.Date(formatoDataUSA.parse(infoAnimal.getDt_prev_ini_prox_cio()).getTime()));
                ps2.setTime(2, new java.sql.Time(formatoHoraBr.parse(infoAnimal.getHr_prev_ini_prox_cio()).getTime()));
                if(ultimoAnimalCad != null)
                    ps2.setInt(3, ultimoAnimalCad.getId());
                else
                    ps2.setString(3, null);
                ps2.executeUpdate();
                ps2.close();
            } catch (SQLException | ParseException ex) {
                Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException("ERRO DE SQL: CADASTRO DE PREVISÃO DE CIO DO ANIMAL NÃO REALIZADO.", ex);
            }
            ps.close();
            conexao.close();
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CADASTRO DE INFORMAÇÕES DO ANIMAL NÃO REALIZADO.", ex);
        }
    }

    @Override
    public void excluirDAO(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editarDAO(AnimalInfo infoAnimal) {
        SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoDataBr = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHoraBr = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatoCompletoUSA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatoCompletoBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            
            Date dt_fim_ult_cio = null;
            Time hr_fim_ult_cio = null;
            Date dt_ini_ult_cio = null;
            Time hr_ini_ult_cio = null;
           
            if(infoAnimal.getDt_fim_ult_cio() != null)
                dt_fim_ult_cio = new java.sql.Date(formatoDataBr.parse(infoAnimal.getDt_fim_ult_cio()).getTime());
            if(infoAnimal.getHr_fim_ult_cio() != null)
                hr_fim_ult_cio = new java.sql.Time(formatoHoraBr.parse(infoAnimal.getHr_fim_ult_cio()).getTime());
            if(infoAnimal.getDt_ini_ult_cio() != null)
                dt_ini_ult_cio = new java.sql.Date(formatoDataBr.parse(infoAnimal.getDt_ini_ult_cio()).getTime());
            if(infoAnimal.getHr_ini_ult_cio() != null)
                hr_ini_ult_cio = new java.sql.Time(formatoHoraBr.parse(infoAnimal.getHr_ini_ult_cio()).getTime());
            
            Date dt_prev_prox_cio = null;
            Time hr_prev_prox_cio = null;
            if(infoAnimal.getDt_prev_ini_prox_cio() != null)
                dt_prev_prox_cio = new java.sql.Date(formatoDataUSA.parse(infoAnimal.getDt_prev_ini_prox_cio()).getTime());
            if(infoAnimal.getHr_prev_ini_prox_cio() != null)
                hr_prev_prox_cio = new java.sql.Time(formatoHoraBr.parse(infoAnimal.getHr_prev_ini_prox_cio()).getTime());
            String sql = "UPDATE info_animal SET info_idade=?, info_dt_fim_ult_cio=?, info_hr_fim_ult_cio=?, info_dt_prev_ini_prox_cio=?, info_hr_prev_ini_prox_cio=?,"
                + " info_intervalo_padrao_anestro=?, info_tempo_atual_anestro=?, info_media_passos_hora=?, info_dias_em_anestro=?, info_status_atividade=?, info_status_cio=?,"
                + " info_oco_reprodutiva=?, info_oco_produtiva=?, info_sob_alerta=?, info_aprox_cio=?, info_animal_fk=?, info_dt_ini_ult_cio=?, info_hr_ini_ult_cio=?, info_duracao_media_cio=? WHERE info_id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, infoAnimal.getIdade());
            ps.setDate(2, dt_fim_ult_cio);
            ps.setTime(3, hr_fim_ult_cio);
            ps.setDate(4, dt_prev_prox_cio);
            ps.setTime(5, hr_prev_prox_cio);
            ps.setInt(6, infoAnimal.getIntervalo_padrao_anestro());
            ps.setString(7, infoAnimal.getTempo_atual_anestro());
            ps.setInt(8, infoAnimal.getMedia_passos_hora());
            ps.setInt(9, infoAnimal.getDias_em_anestro());
            ps.setString(10, infoAnimal.getStatus_atividade());
            ps.setInt(11, infoAnimal.getStatus_cio());
            ps.setString(12, infoAnimal.getOco_reprodutiva());
            ps.setString(13, infoAnimal.getOco_produtiva());
            ps.setInt(14, infoAnimal.getSob_alerta());
            ps.setInt(15, infoAnimal.getAprox_cio());
            ps.setInt(16, infoAnimal.getAnimal().getId());
            ps.setDate(17, dt_ini_ult_cio);
            ps.setTime(18, hr_ini_ult_cio);
            ps.setInt(19, infoAnimal.getDuracao_media_cio());
            ps.setInt(20, infoAnimal.getId());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException | ParseException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EDIÇÃO DE INFORMAÇÕES DO ANIMAL NÃO REALIZADA.", ex);
        }
    }

    @Override
    public AnimalInfo buscarPorIdDAO(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AnimalInfo buscarDAO(String valorBusca) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AnimalInfo> listarDAO() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AnimalInfo buscarPorIdAnimalDAO(int idAnimal) {
        try {
            conexao = Conexao.getConnection();
            AnimalInfo info = new AnimalInfo();
            String sql = "SELECT * FROM info_animal WHERE info_animal_fk=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, idAnimal);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                info = null;
            } else {              
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
                SimpleDateFormat dfh = new SimpleDateFormat("HH:mm:ss");
                info.setId(rs.getInt("info_id"));
                info.setIdade(rs.getInt("info_idade"));
                info.setSob_alerta(rs.getInt("info_sob_alerta"));
                if(rs.getDate("info_dt_ini_ult_cio") != null)
                    info.setDt_ini_ult_cio(df.format(rs.getDate("info_dt_ini_ult_cio")));
                else
                    info.setDt_ini_ult_cio(null);
                
                if(rs.getDate("info_hr_ini_ult_cio") != null)
                    info.setHr_ini_ult_cio(dfh.format(rs.getTime("info_hr_ini_ult_cio")));
                else
                    info.setHr_ini_ult_cio(null);

                if(rs.getDate("info_dt_fim_ult_cio") != null)
                    info.setDt_fim_ult_cio(df.format(rs.getDate("info_dt_fim_ult_cio")));
                else
                    info.setDt_fim_ult_cio(null);
                
                if(rs.getDate("info_hr_fim_ult_cio") != null)
                    info.setHr_fim_ult_cio(dfh.format(rs.getTime("info_hr_fim_ult_cio")));
                else
                    info.setHr_fim_ult_cio(null);
                         
                if(rs.getDate("info_dt_prev_ini_prox_cio") != null){
                    info.setDt_prev_ini_prox_cio(df.format(rs.getDate("info_dt_prev_ini_prox_cio")));
                } else
                    info.setDt_prev_ini_prox_cio(null);
                
                if(rs.getTime("info_hr_prev_ini_prox_cio") != null){
                    info.setHr_prev_ini_prox_cio(dfh.format(rs.getTime("info_hr_prev_ini_prox_cio")));
                } else
                    info.setHr_prev_ini_prox_cio(null);
                
                info.setIntervalo_padrao_anestro(rs.getInt("info_intervalo_padrao_anestro"));
                info.setDuracao_media_cio(rs.getInt("info_duracao_media_cio"));
                info.setTempo_atual_anestro(rs.getString("info_tempo_atual_anestro"));
                info.setMedia_passos_hora(rs.getInt("info_media_passos_hora"));
                info.setStatus_atividade(rs.getString("info_status_atividade"));
                info.setStatus_cio(rs.getInt("info_status_cio"));
                info.setOco_produtiva(rs.getString("info_oco_produtiva"));
                info.setOco_reprodutiva(rs.getString("info_oco_reprodutiva"));
                info.setAprox_cio(rs.getInt("info_aprox_cio"));
                info.setDias_em_anestro(rs.getInt("info_dias_em_anestro"));
                try {
                    info.setAnimal(acessoBDAnimal.buscarPorIdDAO(rs.getInt("info_animal_fk")));
                } catch (SQLException ex) {
                    Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                    throw new RuntimeException("Erro. Não foi possível procurar o Animal vinculado às informações.", ex);
                }
            }
            ps.close();
            rs.close();
            conexao.close();
            return info;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CONSULTA DE INFORMAÇÕES DO ANIMAL NÃO REALIZADA.", ex);
        }
    }

    @Override
    public void atualizarTempoAnestroDAO(AnimalInfo infoAnimal, int diasAnestroAtual) {
        Configuracao config = acessoBDConfig.carregarDAO(1);
        if(diasAnestroAtual == 0)
            infoAnimal.setTempo_atual_anestro("PRINCIPIO");
        if(diasAnestroAtual > 0 && diasAnestroAtual < config.getMin_anestro_normal())
            infoAnimal.setTempo_atual_anestro("CURTO");
        if(diasAnestroAtual >= config.getMin_anestro_normal() && diasAnestroAtual < config.getMin_anestro_longo())
            infoAnimal.setTempo_atual_anestro("NORMAL");
        if(diasAnestroAtual >= config.getMin_anestro_longo() && diasAnestroAtual < config.getMin_anestro_mlongo())
            infoAnimal.setTempo_atual_anestro("LONGO"); 
        if(diasAnestroAtual >= config.getMin_anestro_mlongo())
            infoAnimal.setTempo_atual_anestro("MUITO LONGO");
        try{//atualiza campo "info_status_atividade" da tabela info_animal
            conexao = Conexao.getConnection();
            String sql = "UPDATE info_animal SET info_tempo_atual_anestro=?, info_dias_em_anestro=?, info_aprox_cio=? where info_id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, infoAnimal.getTempo_atual_anestro());
            ps.setInt(2, diasAnestroAtual);
            ps.setInt(3, infoAnimal.getAprox_cio());
            ps.setInt(4, infoAnimal.getId());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(MetodosInferenciaCioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: ATUALIZAÇÃO DAS INFORMAÇÕES DO ANIMAL NÃO REALIZADA.", ex);
        }
    }
    
    public void atualizarDAO(AnimalInfo infoAnimal, Configuracao configRef) {
        SimpleDateFormat formatoDataBr = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHoraBr = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoCompletoBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat formatoCompletoUSA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        try {
            java.util.Date dth_last_captura = formatoCompletoBr.parse(configRef.getDt_ult_captura() + " " + configRef.getHr_ult_captura());
            java.util.Date dth_last_cio = formatoCompletoBr.parse(infoAnimal.getDt_fim_ult_cio() + " " + infoAnimal.getHr_fim_ult_cio());
            GregorianCalendar dth1 = new GregorianCalendar(); 
            GregorianCalendar dth2 = new GregorianCalendar();
            dth1.setTime(dth_last_captura);  
            dth2.setTime(dth_last_cio);
            long milissegundos = dth1.getTimeInMillis() - dth2.getTimeInMillis();
            int diasAnestro = (int) (milissegundos/(24*60*60*1000)); 
            infoAnimal.setDias_em_anestro(diasAnestro);
            int aproximacaoCio = (int)((diasAnestro*10)/infoAnimal.getIntervalo_padrao_anestro());
            if(aproximacaoCio <= 10)
                infoAnimal.setAprox_cio(aproximacaoCio);
            else
                infoAnimal.setAprox_cio(10);
            
            GregorianCalendar dataPrevIniProxCio = new GregorianCalendar();
            GregorianCalendar horaPrevIniProxCio = new GregorianCalendar();
            dataPrevIniProxCio.setTime(formatoCompletoBr.parse(formatoCompletoBr.format(dth_last_cio)));
            dataPrevIniProxCio.add(Calendar.HOUR, 24*infoAnimal.getIntervalo_padrao_anestro());/* SOMANDO */
            formatoDataBr.setCalendar(dataPrevIniProxCio);
            horaPrevIniProxCio.setTime(formatoCompletoBr.parse(formatoCompletoBr.format(dth_last_cio))); 
            formatoHoraBr.setCalendar(horaPrevIniProxCio);
            infoAnimal.setDt_prev_ini_prox_cio(formatoDataUSA.format(dataPrevIniProxCio.getTime()));
            infoAnimal.setHr_prev_ini_prox_cio(formatoHoraBr.format(horaPrevIniProxCio.getTime()));
            
            if(diasAnestro == 0)
                infoAnimal.setTempo_atual_anestro("PRINCIPIO");
            if(diasAnestro > 0 && diasAnestro < configRef.getMin_anestro_normal())
                infoAnimal.setTempo_atual_anestro("CURTO");
            if(diasAnestro >= configRef.getMin_anestro_normal() && diasAnestro < configRef.getMin_anestro_longo())
                infoAnimal.setTempo_atual_anestro("NORMAL");
            if(diasAnestro >= configRef.getMin_anestro_longo() && diasAnestro < configRef.getMin_anestro_mlongo())
                infoAnimal.setTempo_atual_anestro("LONGO"); 
            if(diasAnestro >= configRef.getMin_anestro_mlongo())
                infoAnimal.setTempo_atual_anestro("MUITO LONGO");

            try{//atualiza campo "info_status_atividade" da tabela info_animal
                conexao = Conexao.getConnection();
                              
                String sql = "UPDATE info_animal SET info_tempo_atual_anestro=?, info_dias_em_anestro=?, info_aprox_cio=?, info_dt_prev_ini_prox_cio=?, "
                        + "info_hr_prev_ini_prox_cio=? where info_id=?";
                PreparedStatement ps = conexao.prepareStatement(sql);
                ps.setString(1, infoAnimal.getTempo_atual_anestro());
                ps.setInt(2, infoAnimal.getDias_em_anestro());
                ps.setInt(3, infoAnimal.getAprox_cio());
                ps.setDate(4, new java.sql.Date(formatoDataUSA.parse(infoAnimal.getDt_prev_ini_prox_cio()).getTime()));
                ps.setTime(5, new java.sql.Time(formatoHoraBr.parse(infoAnimal.getHr_prev_ini_prox_cio()).getTime()));
                ps.setInt(6, infoAnimal.getId());
                ps.executeUpdate();
                ps.close();
                conexao.close();
            } catch (SQLException ex) {
                Logger.getLogger(MetodosInferenciaCioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException("ERRO DE SQL: ATUALIZAÇÃO DAS INFORMAÇÕES DO ANIMAL NÃO REALIZADA.", ex);
            }
            
        } catch (ParseException ex) {
            Logger.getLogger(AnimalInfoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void atualizarMediaPassosHoraDAO(AnimalInfo infoAnimal) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "UPDATE info_animal SET info_media_passos_hora=? WHERE info_id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, infoAnimal.getMedia_passos_hora());
            ps.setInt(2, infoAnimal.getId());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EDIÇÃO DE INFORMAÇÕES DO ANIMAL NÃO REALIZADA.", ex);
        }
    }

    @Override
    public void atualizarSobAlertaDAO(AnimalInfo infoAnimal) {
        try {
            conexao = Conexao.getConnection();//start na conexão com banco de dados
            String sql = "UPDATE info_animal SET info_sob_alerta=? WHERE info_id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, infoAnimal.getSob_alerta());
            ps.setInt(2, infoAnimal.getId());
            ps.executeUpdate();
            ps.close();
            conexao.close();
        } catch (SQLException ex) {
            Logger.getLogger(AnimalDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: EDIÇÃO DE INFORMAÇÕES DO ANIMAL NÃO REALIZADA.", ex);
        }
    }
    
}
