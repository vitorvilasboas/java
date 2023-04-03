
package SensoriamentoSimulado;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransmissorReal {
    
    static final int qtdAnimais = 10;
    static final double intervalo_leituras = 2;//2 horas
    static final String id_up = "c000100";
    static final String url_arquivo = "C:\\Users\\JV\\Documents\\NetBeansProjects\\SistemaCio\\simuladorRSSF\\registrosSensores2.txt";
    
    /**
     * @param args the command line arguments
     */
    /*
    public static void main(String[] args) {
        AnimalMonitorado[] aniMon = new AnimalMonitorado[qtdAnimais]; 
        aniMon[0]=instanciaAnimais("$a01",100,20,20,90);//id, padPassadas, duracao_cio(em horas), tempo_entre_cios (em dias), percAumentoAtividade
        aniMon[1]=instanciaAnimais("$a02",100,24,21,80);
        aniMon[2]=instanciaAnimais("$a03",120,18,21,100);
        aniMon[3]=instanciaAnimais("$a04",90,20,19,120);
        aniMon[4]=instanciaAnimais("$a05",100,22,17,95);
        aniMon[5]=instanciaAnimais("$a06",140,20,21,90);
        aniMon[6]=instanciaAnimais("$a07",120,24,18,100);
        aniMon[7]=instanciaAnimais("$a08",100,24,21,110);
        aniMon[8]=instanciaAnimais("$a09",150,22,22,140);
        aniMon[9]=instanciaAnimais("$a10",110,24,20,100);
        int qtdPassadas;
        double qtd_leituras_em_cio;
        double qtd_leituras_entre_cios;
        String ultimo_registro;
        String dadosLinha[] = null;
        int contIntervalo;
        for(int i=0; i<qtdAnimais;i++){

            qtd_leituras_em_cio = aniMon[i].getDuracao_cio()/intervalo_leituras;
            qtd_leituras_entre_cios = aniMon[i].getTempo_entre_cio() * (24/intervalo_leituras);
            
            ultimo_registro = buscaUltimoRegistro(aniMon[i].getId());
            dadosLinha = ultimo_registro.split("&");
            contIntervalo = Integer.parseInt(dadosLinha[5])+1;//ultima transmissão + 1
            if(contIntervalo > qtd_leituras_entre_cios && contIntervalo <= (qtd_leituras_entre_cios + qtd_leituras_em_cio)){
                qtdPassadas = calculaPassadas(aniMon[i].getPadrao_passadas(), aniMon[i].getPercentual_aumento_atividade());
            } else {
                if(contIntervalo > (qtd_leituras_entre_cios + qtd_leituras_em_cio)){
                    contIntervalo = 1;
                }
                qtdPassadas = calculaPassadas(aniMon[i].getPadrao_passadas(), 0);
            }
            transmiteDadosContexto(id_up, aniMon[i].getId(), capturaData(), capturaHora(), qtdPassadas, contIntervalo, url_arquivo);  
        }
        buscaUltimoRegistro(aniMon[1].getId());
    }
    
    public static AnimalMonitorado instanciaAnimais(String id, int padPassadas, int duracao_cio, int tempo_entre_cios, int percAumentoAtividade)
    {
        AnimalMonitorado a = new AnimalMonitorado();
        a.setId(id);
        a.setPadrao_passadas(padPassadas);
        a.setDuracao_cio(duracao_cio);
        a.setTempo_entre_cio(tempo_entre_cios);
        a.setPercentual_aumento_atividade(percAumentoAtividade);
        return a;
    }
    
    public static String buscaUltimoRegistro(String idBuscado){
        String dadosLinha[];
        String ultimoRegistro = "";
        File arquivo = new File(url_arquivo);
        List<String> registrosArquivo = new ArrayList<>();
        try( InputStream entrada = new FileInputStream(arquivo)){
            Scanner leitor = new Scanner(entrada);
            while( leitor.hasNext() )
                registrosArquivo.add(leitor.nextLine());
        }catch(IOException ex){}
        Collections.reverse(registrosArquivo);//inverte array
        for(String linhaRegistro : registrosArquivo){//percorre os registros do array registrosArquivo enquanto houver informações e armazena cada registro na string linhaRegistro
             if(!linhaRegistro.isEmpty()){//verifica se a linha não está em branco
                dadosLinha=linhaRegistro.split("&");//separa a linha a cada vez que o delimitador (&) é encontrado e armazena temporariamente cada atributo em uma posição do vetor de string dadosLinha
                if(dadosLinha[1].equals(idBuscado)){//se o id da linha em questão for igual ao solicitado por parâmetro...
                   ultimoRegistro = linhaRegistro;
                   break;
                }
             }
         }       
         return ultimoRegistro;//retorna a ultima linha registrada referente ao sensor informado
    }
    
    public static String capturaHora(){//Método que captura HORA atual do sistema
        DateFormat formataHora = new SimpleDateFormat("HHmmss");
        Date hora = new Date();
        return formataHora.format(hora);
    }
    
    public static String capturaData(){//Método que captura DATA atual do sistema   
        DateFormat formataData = new SimpleDateFormat("ddMMyyyy");
        Date data = new Date();
        return formataData.format(data);
    }
    
    public static int calculaPassadas(int padrao, int aumento){
        Random geraPassada = new Random();
        int media_pós_aumento = padrao+(padrao*(aumento/100));//calcula a nova média de passadas a partir do aumento (aumento ocorrerá somente no periodo de cio)
        int minimo = (int)(media_pós_aumento-(media_pós_aumento*0.1));//calcula o minimo do random de passadas após o aumento
        int maximo = (int)(media_pós_aumento+(media_pós_aumento*0.1));//calcula o máximo do random de passadas após o aumento
        return geraPassada.nextInt((maximo - minimo)) + minimo;
    }
       
    public static void transmiteDadosContexto(String idUP, String idSensor, String data, String hora, int passadas, int contIntervalo, String caminhoArquivo) {       
        FileWriter fw = null;
        try {
            fw = new FileWriter(caminhoArquivo, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.print("\n"+idUP+"&"+idSensor+"&"+data+"&"+hora+"&"+passadas+"&"+contIntervalo);
            System.out.println("Informações do sensor "+idSensor+" transmitidas com sucesso e gravadas no arquivo");
            pw.flush();
            pw.close();
        } catch (IOException ex) {
            Logger.getLogger(TransmissorReal.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }  
 */   
}
