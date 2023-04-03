
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Simulador {
    
    public static void simular() throws ParseException {
        List<String> confs = MetodosUteisBD.getConfiguracoes();
        List<Vaca> animais = MetodosUteisBD.getAnimal();
        String codigo_central = MetodosUteisBD.getCentral();
        String url_arquivo = "C:\\Users\\JV\\Documents\\NetBeansProjects\\siac\\regsensores\\regs.txt";
        File arquivo = new File(url_arquivo);
        List<String> leituras = percorrerArquivo(arquivo);
        
        SimpleDateFormat formatoDataArquivo = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat formatoHoraArquivo = new SimpleDateFormat("HHmmss");
        SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoHoraUni = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatoCompletoUSA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataProximaLeitura = confs.get(0);
        String horaProximaLeitura = confs.get(1);
        int intervalo_leituras = Integer.parseInt(confs.get(2));
        for(Vaca vaca : animais){  
            vaca.setPadrao_passadas(vaca.getPadrao_passadas()*intervalo_leituras);
            int max_leituras_anestro = (int)(vaca.getDias_anestro()*(24/intervalo_leituras));
            int max_leit_em_cio = (int) (vaca.getHoras_duracao_cio() / intervalo_leituras);
            int qtdPassadas = calculaPassadas(vaca.getPadrao_passadas(), 0);
            int id = 0;
            int cont = 0;
            int i = 0;
            int dd_ult_lei = 0;
            if(leituras != null){
                for(String linha : leituras){
                    if(!"********* REGISTROS GRAVADOS **********".equals(linha)){
                        if(dd_ult_lei == 0){
                            String dadosUltimaLeitura[] = leituras.get(i).split("&");
                            String dataUltimaLeitura = formatoDataUSA.format(formatoDataArquivo.parse(dadosUltimaLeitura[2]));
                            String horaUltimaLeitura = formatoHoraUni.format(formatoHoraArquivo.parse(dadosUltimaLeitura[3]));
                            Date dth = formatoCompletoUSA.parse(dataUltimaLeitura + " " + horaUltimaLeitura);
                            GregorianCalendar dthProximaLeitura = new GregorianCalendar();
                            dthProximaLeitura.setTime(dth);
                            dthProximaLeitura.add(Calendar.HOUR, intervalo_leituras);
                            dataProximaLeitura = formatoDataArquivo.format(dthProximaLeitura.getTime());
                            horaProximaLeitura = formatoHoraArquivo.format(dthProximaLeitura.getTime());
                            dd_ult_lei = 1;
                        }
                        String dadosLinha[] = linha.split("&");
                        if(dadosLinha[1].equals(vaca.getId())){//se a linha for do animal
                            if("0".equals(dadosLinha[5])){
                                cont++;
                            } else {
                                break;
                            }
                        }
                    }
                    i++;
                }
                if((cont > max_leituras_anestro) && (cont <= (max_leituras_anestro + max_leit_em_cio))){
                    id = 0;
                    qtdPassadas = calculaPassadas(vaca.getPadrao_passadas(), vaca.getPercentual_aumento_atividade());
                } else {
                    if(cont > (max_leituras_anestro + max_leit_em_cio)){
                        id = 1;
                    }
                    qtdPassadas = calculaPassadas(vaca.getPadrao_passadas(), 0);
                }  
            }
            registraNovaLeitura(codigo_central, vaca.getId(), dataProximaLeitura, horaProximaLeitura, qtdPassadas, url_arquivo, id);
        }
    }
    
    public static void registraNovaLeitura(String idUP, String idSensor, String data, String hora, int passadas, String urlArquivo, int idLeitura) {       
        FileWriter fw = null;
        try {
            fw = new FileWriter(urlArquivo, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.print("\n"+idUP+"&"+idSensor+"&"+data+"&"+hora+"&"+passadas+"&"+idLeitura);
            pw.flush();
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(Simulador.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    
    public static List<String> percorrerArquivo(File arquivo) {
        List<String> linhas = new ArrayList<>();
        try( InputStream entrada = new FileInputStream(arquivo)){
            Scanner leitor = new Scanner(entrada);
            while( leitor.hasNext() ){
                String leitura = leitor.nextLine();
                linhas.add(leitura);//adiciona cada linha do arquivo em um array de strings
            }  
        }catch(IOException ex){} 
        Collections.reverse(linhas);//inverte array
        return linhas;
    }
    
    public static int calculaPassadas(int padrao, int aumento){
        Random geraPassada = new Random();
	double pAumento = (aumento/100) + 1;
        int media_pos_aumento = (int)(pAumento * padrao);//calcula a nova média de passadas a partir do aumento (aumento ocorrerá somente no periodo de cio)
	int minimo = (int)(media_pos_aumento-(media_pos_aumento*0.1));//calcula o minimo do random de passadas após o aumento
        int maximo = (int)(media_pos_aumento+(media_pos_aumento*0.1));//calcula o máximo do random de passadas após o aumento
        return geraPassada.nextInt((maximo - minimo)) + minimo;
    }
 
     
}
 /*System.out.println(">>> Vaca: " + vaca.getId());
            System.out.println("    Dias Anestro: " + vaca.getDias_anestro());
            System.out.println("    Leituras em Anestro: " + ((int)(vaca.getDias_anestro() * (24/intervalo_leituras))));
            System.out.println("    Duração Cio: " + vaca.getHoras_duracao_cio());
            System.out.println("    Leituras em Cio: " + (vaca.getHoras_duracao_cio() / intervalo_leituras));
            System.out.println("    Padrão de Passadas: " + vaca.getPadrao_passadas());
            System.out.println("    Percentagem de Variação: " + vaca.getPercentual_aumento_atividade());
            System.out.println("    Passadas Alteradas: " + (vaca.getPadrao_passadas() * (1 + (vaca.getPercentual_aumento_atividade()/100))));*/