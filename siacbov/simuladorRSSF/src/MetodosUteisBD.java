

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class MetodosUteisBD {
    static Connection conexao;
    
    public static List<Vaca> getAnimal() {
        Random percentualAumento = new Random();
        List<Vaca> animais = new ArrayList<>();
        
        try {
            conexao = Conexao.getConnection();
            String sql = "SELECT * FROM animal where ani_monitorando=?";
            PreparedStatement pst = conexao.prepareStatement(sql);
            pst.setInt(1, 1);
            ResultSet rs = pst.executeQuery();
            if (!rs.next()) 
                animais = null;
            else {
                do {
                    Vaca vaca = new Vaca();
                    vaca.setPercentual_aumento_atividade(percentualAumento.nextInt(400 - 150) + 150);
                    try{
                        String sql2 = "SELECT sen_codigo FROM sensor where sen_id=?";
                        PreparedStatement pst2 = conexao.prepareStatement(sql2);
                        pst2.setInt(1, rs.getInt("ani_sensor_fk"));
                        ResultSet rs2 = pst2.executeQuery();
                        if (rs2.next()) 
                            vaca.setId(rs2.getString("sen_codigo"));
                        pst2.close();
                        rs2.close();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    try{
                        String sql3 = "SELECT info_media_passos_hora, info_intervalo_padrao_anestro, info_duracao_media_cio FROM info_animal where info_animal_fk=?";
                        PreparedStatement pst3 = conexao.prepareStatement(sql3);
                        pst3.setInt(1, rs.getInt("ani_id"));
                        ResultSet rs3 = pst3.executeQuery();
                        if (rs3.next()) 
                            vaca.setPadrao_passadas(rs3.getInt("info_media_passos_hora"));
                            vaca.setDias_anestro(rs3.getInt("info_intervalo_padrao_anestro"));
                            vaca.setHoras_duracao_cio(rs3.getInt("info_duracao_media_cio"));
                        pst3.close();
                        rs3.close();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    animais.add(vaca);
                } while(rs.next());         
            }
            pst.close();
            rs.close();
            conexao.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return animais;
    }

    public static List<String> getConfiguracoes() {
        SimpleDateFormat df = new SimpleDateFormat("ddMMYYYY");
        SimpleDateFormat dfh = new SimpleDateFormat("HHmmss");
        List<String> confs = new ArrayList<>();
        try {
            conexao = Conexao.getConnection();
            String sql = "SELECT * FROM config where cfg_id=?";
            PreparedStatement pst = conexao.prepareStatement(sql);
            pst.setInt(1, 1);
            ResultSet rs = pst.executeQuery();
            if (!rs.next()){ 
                confs = null;
            } else {
                if(rs.getDate("cfg_dt_ult_captura") != null)
                    confs.add(df.format(rs.getDate("cfg_dt_ult_captura")));
                else
                    confs.add(null);
                
                if(rs.getTime("cfg_hr_ult_captura") != null)
                    confs.add(dfh.format(rs.getTime("cfg_hr_ult_captura")));
                else
                    confs.add(null);
                
                confs.add(String.valueOf(rs.getInt("cfg_intervalo_entre_leituras")));
            }
            pst.close();
            rs.close();
            conexao.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return confs;
    }
    
    public static String getCentral() {
        String codCentral = "00000001";
        try {
            conexao = Conexao.getConnection();
            String sql = "SELECT * FROM central where cen_ativa=?";
            PreparedStatement pst = conexao.prepareStatement(sql);
            pst.setInt(1, 1);
            ResultSet rs = pst.executeQuery();
            if (rs.next())          
                codCentral = rs.getString("cen_codigo");
            pst.close();
            rs.close();
            conexao.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return codCentral;
    }
}
