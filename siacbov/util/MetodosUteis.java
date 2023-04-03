package br.edu.cio.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.BASE64Encoder;

public class MetodosUteis {
    public static String encrypta(String senha){  
        try{  
            MessageDigest digest = MessageDigest.getInstance("MD5");  
            digest.update(senha.getBytes());  
            BASE64Encoder encoder = new BASE64Encoder();  
            return encoder.encode(digest.digest());  
        }catch(NoSuchAlgorithmException ns){  
            ns.printStackTrace();  
        }  
        return senha;  
    }
    public void datas(){
        try {
            SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatoDataBr = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoHoraBr = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat formatoCompletoUSA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat formatoCompletoBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dtn1 = "2010-12-3";// data em Texto conforme padrão datetime-local //System.out.println("DTN 1 - Texto Request: "+dtn1);
            Date dtn2 = formatoDataUSA.parse(dtn1); //convertendo String para o padrão de datas americano(SQL) yyyy-MM-dd //System.out.println("DTN 2 - Date USA/SQL: "+dtn2);
            String dtn3 = formatoDataBr.format(dtn2); //formatando um tipo date em padrão americano(SQL) yyy-MM-dd para o padrão brasileiro dd-MM-yyyy, e casting para String //System.out.println("DTN 3 - Texto Br: "+dtn3);
            Date dtn4 = formatoDataBr.parse(dtn3); //convertendo uma String ja formatada para um tipo Date //System.out.println("DTN 4 - Date Br: "+dtn4+"\n\n");
            /* dataCalendario.setTime(formatoDtaUSA.parse(dtn1));*/
            /*
            Calendar data = Calendar.getInstance();  
            System.out.println(data.getTime());  
            Calendar data2 = Calendar.getInstance();  
            data2.set(Calendar.DAY_OF_MONTH, 1);  
            data2.set(Calendar.MONTH, 1);  
            data2.set(Calendar.YEAR, 2014);  
            data2.set(Calendar.HOUR_OF_DAY, 00);  
            data2.set(Calendar.MINUTE, 00);  
            data2.set(Calendar.SECOND, 00);  
            System.out.println(data2.getTime());  
            long d1 = data.getTimeInMillis();  
            long d2 = data2.getTimeInMillis(); 
            long milissegundos = d1 - d2;
            idade = (int) (milissegundos/(24*60*60*1000));
            System.out.println("N° dias: " + (int)((d2 - d1)/ (24*60*60*1000))); */
            
            /*
            SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
            Date dataLeituraPosCadastro = new Date(formata.parse(leituraPosCadastro.getData()).getTime());
            Time horaLeituraPosCadastro = new Time(formataHora.parse(leituraPosCadastro.getHora()).getTime());
            Date data_fim_ult_cio = new Date(formata.parse(infoAnimalSensor.getDt_fim_ult_cio()).getTime());
            Time hora_fim_ult_cio = new Time(formataHora.parse(infoAnimalSensor.getHr_fim_ult_cio()).getTime()); 
            Date dataPrev_ini_prox_cio = new Date(formata.parse(infoAnimalSensor.getDt_prev_ini_prox_cio()).getTime());
            Time horaPrev_ini_prox_cio = new Time(formataHora.parse(infoAnimalSensor.getHr_prev_ini_prox_cio()).getTime());
            */
            
        } catch (ParseException ex) {
            Logger.getLogger(MetodosUteis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

