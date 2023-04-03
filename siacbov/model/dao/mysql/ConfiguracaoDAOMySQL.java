package br.edu.cio.model.dao.mysql;

import br.edu.cio.model.Configuracao;
import br.edu.cio.model.dao.ConfiguracaoDAO;
import br.edu.cio.util.Conexao;
import br.edu.cio.util.DAOFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConfiguracaoDAOMySQL implements ConfiguracaoDAO{
    
    Connection conexao;
    public ConfiguracaoDAOMySQL(){//construtor
        //conexao = Conexao.getConnection();//start na conexão com banco de dados
    }
    
    @Override
    public Configuracao carregarDAO(int id) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
        SimpleDateFormat dfh = new SimpleDateFormat("HH:mm:ss");
        Configuracao c = new Configuracao();
        try {
            conexao = Conexao.getConnection();
            String sql = "SELECT * FROM config WHERE cfg_id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {//resultSet sempre inicia antes do primeiro registro, aqui verifica se o registro foi ou não foi encontrado
                c = null;
            } else {
                if(rs.getDate("cfg_data_atual") != null)
                    c.setData_atual(df.format(rs.getDate("cfg_data_atual")));
                else
                    c.setData_atual(null);
                
                if(rs.getDate("cfg_hora_atual") != null)
                    c.setHora_atual(dfh.format(rs.getTime("cfg_hora_atual")));
                else
                    c.setHora_atual(null);
                
                if(rs.getDate("cfg_dt_ult_captura") != null)
                    c.setDt_ult_captura(df.format(rs.getDate("cfg_dt_ult_captura")));
                else
                    c.setDt_ult_captura(null);
                
                if(rs.getDate("cfg_hr_ult_captura") != null)
                    c.setHr_ult_captura(dfh.format(rs.getTime("cfg_hr_ult_captura")));
                else
                    c.setHr_ult_captura(null);
                
                c.setId(rs.getInt("cfg_id"));
                c.setIntervalo_anestro(rs.getInt("cfg_intervalo_anestro"));
                c.setDuracao_cio(rs.getInt("cfg_duracao_cio"));
                c.setIntervalo_entre_leituras(rs.getInt("cfg_intervalo_entre_leituras"));
                c.setQtd_alertas(rs.getInt("cfg_qtd_alertas_ocorrencia"));
                c.setQtd_oco_pra_cio(rs.getInt("cfg_oco_pra_cio"));
                c.setPerc_min_atv_media(rs.getInt("cfg_perc_min_atv_media"));
                c.setPerc_min_atv_alta(rs.getInt("cfg_perc_min_atv_alta"));
                c.setEnvio_celular(rs.getString("cfg_envio_celular"));
                c.setEnvio_email(rs.getString("cfg_envio_email"));
                c.setMsg_alerta_cio(rs.getString("cfg_msg_alerta_cio"));
                c.setMsg_alerta_ocorrencia(rs.getString("cfg_msg_alerta_ocorrencia"));
                c.setTempo_entre_alertas(rs.getString("cfg_tempo_entre_alertas"));
                c.setAlerta_quando(rs.getString("cfg_alerta_quando"));
                c.setReg_sensores(rs.getString("cfg_reg_sensores"));
                c.setMin_anestro_normal(rs.getInt("cfg_min_anestro_normal"));
                c.setMin_anestro_longo(rs.getInt("cfg_min_anestro_longo"));
                c.setMin_anestro_mlongo(rs.getInt("cfg_min_anestro_mlongo"));
            }
            ps.close();
            rs.close();
            conexao.close();
            return c;      
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: CARREGAMENTO DE CONFIGURAÇÃO NÃO REALIZADA.", ex);
        }
    }

    @Override
    public void atualizarDAO(Configuracao config) {
        try {
            conexao = Conexao.getConnection();
            SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatoDataBr = new SimpleDateFormat("dd/MM/yyyy");
            Date data = null;
            Time hora = null;
            Date dataCap = null;
            Time horaCap = null;
            if(config.getData_atual() != null)
                data = new java.sql.Date(formatoDataBr.parse(config.getData_atual()).getTime());
            if(config.getHora_atual() != null)
                hora = new java.sql.Time(formato.parse(config.getHora_atual()).getTime());
            
            if(config.getDt_ult_captura() != null)
                dataCap = new java.sql.Date(formatoDataUSA.parse(config.getDt_ult_captura()).getTime());
                
            if(config.getHr_ult_captura() != null)
                horaCap = new java.sql.Time(formato.parse(config.getHr_ult_captura()).getTime());
            
            String sql = "UPDATE config SET cfg_intervalo_anestro=?, cfg_duracao_cio=?, cfg_intervalo_entre_leituras=?, cfg_qtd_alertas_ocorrencia=?, cfg_oco_pra_cio=?, cfg_perc_min_atv_media=?, cfg_perc_min_atv_alta=?,"
                    + "cfg_envio_celular=?, cfg_envio_email=?, cfg_msg_alerta_cio=?, cfg_msg_alerta_ocorrencia=?, cfg_tempo_entre_alertas=?, cfg_alerta_quando=?, cfg_reg_sensores=?, cfg_data_atual=?, cfg_hora_atual=?,"
                    + " cfg_dt_ult_captura=?, cfg_hr_ult_captura=?, cfg_min_anestro_normal=?, cfg_min_anestro_longo=?, cfg_min_anestro_mlongo=? where cfg_id=?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, config.getIntervalo_anestro());
            ps.setInt(2, config.getDuracao_cio());
            ps.setInt(3, config.getIntervalo_entre_leituras());
            ps.setInt(4, config.getQtd_alertas());
            ps.setInt(5, config.getQtd_oco_pra_cio());
            ps.setInt(6, config.getPerc_min_atv_media());
            ps.setInt(7, config.getPerc_min_atv_alta());
            ps.setString(8, config.getEnvio_celular());
            ps.setString(9, config.getEnvio_email());
            ps.setString(10, config.getMsg_alerta_cio());
            ps.setString(11, config.getMsg_alerta_ocorrencia());
            ps.setTime(12, Time.valueOf(config.getTempo_entre_alertas()));
            ps.setString(13, config.getAlerta_quando());
            ps.setString(14, config.getReg_sensores());
            ps.setDate(15, data);
            ps.setTime(16, hora);
            ps.setDate(17, dataCap);
            ps.setTime(18, horaCap);
            ps.setInt(19, config.getMin_anestro_normal());
            ps.setInt(20, config.getMin_anestro_longo());
            ps.setInt(21, config.getMin_anestro_mlongo());
            ps.setInt(22, 1);
            ps.executeUpdate();
            ps.close();
            conexao.close();            
        } catch (SQLException ex) {
            Logger.getLogger(ConfiguracaoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("ERRO DE SQL: ATUALIZAÇÃO DE CONFIGURAÇÃO NÃO REALIZADA.", ex);
        } catch (ParseException ex) {
            Logger.getLogger(ConfiguracaoDAOMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}
