package SensoriamentoSimulado;

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
import javax.swing.JOptionPane;

public class Transmissor {
    /*
    static final int qtdAnimais = 10;
    static final double intervalo_transmissao = 2;//2 horas --> (1 transmissao a cada 30 seg - Agendador) --> 1m = 4h, 6m = 1 dia, 30m = 5dias
    static final String id_up = "00000001";
    static final String url_arquivo = "C:\\Users\\JV\\Documents\\NetBeansProjects\\SistemaCio\\simuladorRSSF\\registrosSensores.txt";
    
    /**
     * @param args the command line arguments
     */
    /*
    public static void main(String[] args) throws ParseException {
        AnimalMonitorado[] aniMon = new AnimalMonitorado[qtdAnimais]; 
        //id, padPassadas, duracao_cio(em horas), tempo_anestro (em dias), percAumentoAtividade
        aniMon[0]=instanciaAnimais("00000001",100,24,5,90);//Transmissões--> Anestro: 60  Estro: 12         | aniMon[0]=instanciaAnimais("00000001",100,24,20,90);  
        aniMon[1]=instanciaAnimais("00000002",100,22,6,80);//Transmissões--> Anestro: 72  Estro: 11         | aniMon[1]=instanciaAnimais("00000002",100,22,21,80);
        aniMon[2]=instanciaAnimais("00000003",120,20,6,100);//Transmissões--> Anestro: 72  Estro: 10        | aniMon[2]=instanciaAnimais("00000003",120,20,21,100);
        aniMon[3]=instanciaAnimais("00000004",90,20,4,120);//Transmissões--> Anestro: 48   Estro: 10        | aniMon[3]=instanciaAnimais("00000004",90,20,19,120);
        aniMon[4]=instanciaAnimais("00000005",100,12,3,95);//Transmissões--> Anestro: 36  Estro: 6          | aniMon[4]=instanciaAnimais("00000005",100,12,17,95);
        aniMon[5]=instanciaAnimais("00000006",140,24,6,90);//Transmissões--> Anestro: 72  Estro: 12         | aniMon[5]=instanciaAnimais("00000006",140,24,21,90);
        aniMon[6]=instanciaAnimais("00000007",120,16,4,100);//Transmissões--> Anestro: 48  Estro: 8         | aniMon[6]=instanciaAnimais("00000007",120,16,18,100);
        aniMon[7]=instanciaAnimais("00000008",100,24,6,110);//Transmissões--> Anestro: 72  Estro: 12        | aniMon[7]=instanciaAnimais("00000008",100,24,21,110);
        aniMon[8]=instanciaAnimais("00000009",150,18,6,140);//Transmissões--> Anestro: 72  Estro: 9         | aniMon[8]=instanciaAnimais("00000009",150,18,22,140);
        aniMon[9]=instanciaAnimais("00000010",110,18,5,100);//Transmissões--> Anestro: 60  Estro: 9         | aniMon[9]=instanciaAnimais("00000010",110,18,20,100);
        int qtdPassadas;
        double qtd_transmissoes_estro;
        double qtd_transmissoes_anestro;
        String ultimo_registro;
        String dadosLinha[] = null;
        //int proxRegistroAnestro;//representa o numero do proximo de registros do sensor no arquivo que não indicam estro
        int contIntervalo;
        for(int i=0; i<qtdAnimais;i++){
            //Adotando média: para cada 42 leituras, 2 a 4 indicam cio
            qtd_transmissoes_estro = aniMon[i].getDuracao_cio()/intervalo_transmissao;//Qtd de horas de duração do cio dividido pelo intervalo entre as transmissoes
            qtd_transmissoes_anestro = aniMon[i].getTempo_anestro()* (24/intervalo_transmissao);//Produto entre a 'Qtd de dias em anestro' e a 'Qtd de transmissoes por dia' 

            ultimo_registro = buscaUltimoRegistro(aniMon[i].getId());
            dadosLinha = ultimo_registro.split("&");
            
            //proxRegistroAnestro = contaRegistros(aniMon[i].getId(), aniMon[i].getPadrao_passadas(), aniMon[i].getPercentual_aumento_atividade()) + 1;//cont ultimo registro do sensor + 1
            contIntervalo = Integer.parseInt(dadosLinha[5])+1;//ultima transmissão + 1
            if(contIntervalo > qtd_transmissoes_anestro && contIntervalo <= (qtd_transmissoes_anestro + qtd_transmissoes_estro)){
                qtdPassadas = calculaPassadas(aniMon[i].getPadrao_passadas(), aniMon[i].getPercentual_aumento_atividade());
                JOptionPane.showMessageDialog(null, aniMon[i].getId()+" AUMENTOU!!"+qtdPassadas);
            } else {
                if(contIntervalo > (qtd_transmissoes_anestro + qtd_transmissoes_estro)){
                    contIntervalo = 1;
                }
                qtdPassadas = calculaPassadas(aniMon[i].getPadrao_passadas(), 0);
            }
            
            Date t = calculaTempo(dadosLinha[2], dadosLinha[3]);
            DateFormat formataData = new SimpleDateFormat("ddMMyyyy");
            DateFormat formataHora = new SimpleDateFormat("HHmmss");
            String data = formataData.format(t);
            String hora = formataHora.format(t);
            
            transmiteDadosContexto(id_up, aniMon[i].getId(), data, hora, qtdPassadas, contIntervalo, url_arquivo);  
        }
        buscaUltimoRegistro(aniMon[1].getId());
    }
    
    public static AnimalMonitorado instanciaAnimais(String id, int padPassadas, int duracao_cio, int tempo_entre_cios, int percAumentoAtividade)
    {
        AnimalMonitorado a = new AnimalMonitorado();
        a.setId(id);
        a.setPadrao_passadas(padPassadas);
        a.setDuracao_cio(duracao_cio);
        a.setTempo_anestro(tempo_entre_cios);
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

    public static Date calculaTempo(String ultimaData, String ultimaHora) throws ParseException{//Método que captura HORA atual do sistema
        String tempo = ultimaData + " " + ultimaHora;
        SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyy HHmmss");
        Date data = formato.parse(tempo);
        
        GregorianCalendar gc = new GregorianCalendar();  
        gc.setTime(data);  
        gc.add(Calendar.HOUR,2); //somando duas horas à data anterior capturada do arquivo de leitura 
        return gc.getTime();
    }

    public static int calculaPassadas(int padrao, int aumento){
        Random geraPassada = new Random();
        int media_pos_aumento = aumento+padrao;//(padrao*(aumento/100))calcula a nova média de passadas a partir do aumento (aumento ocorrerá somente no periodo de cio)
        int minimo = (int)(media_pos_aumento-(media_pos_aumento*0.1));//calcula o minimo do random de passadas após o aumento
        int maximo = (int)(media_pos_aumento+(media_pos_aumento*0.1));//calcula o máximo do random de passadas após o aumento
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
            Logger.getLogger(Transmissor.class.getName()).log(Level.SEVERE, null, ex);
        }       
    } 
    
    /*
    public static int contaRegistros(String codSensor, int padraoPassadas, int aumentoPassadas){//usado para contar quantas leituras o animal já teve, zerando o contador a cada leitura que indique cio
        String dadosLinha[];
        int contRegistros = 0;
        String ultimoRegistro = "";
        File arquivo = new File(url_arquivo);
        List<String> registrosArquivo = new ArrayList<>();
        
        int media_pos_aumento = padraoPassadas+(padraoPassadas*(aumentoPassadas/100));//calcula a nova média de passadas a partir do aumento (aumento ocorrerá somente no periodo de cio)
        int maximo = (int)(media_pos_aumento+(media_pos_aumento*0.1));//calcula o máximo de passadas após o aumento
       
        try( InputStream entrada = new FileInputStream(arquivo)){
            Scanner leitor = new Scanner(entrada);
            while( leitor.hasNext() )
                registrosArquivo.add(leitor.nextLine());
        }catch(IOException ex){}
        for(String linhaRegistro : registrosArquivo){//percorre os registros do array registrosArquivo enquanto houver informações e armazena cada registro na string linhaRegistro
            if(!linhaRegistro.isEmpty()){//verifica se a linha não está em branco
                dadosLinha=linhaRegistro.split("&");//separa a linha a cada vez que o delimitador (&) é encontrado e armazena temporariamente cada atributo em uma posição do vetor de string dadosLinha
                if(dadosLinha[1].equals(codSensor)){//se o id da linha em questão for igual ao solicitado por parâmetro...
                    if((Integer.parseInt(dadosLinha[4]) < maximo)){//se a linha de registro não indica cio... incrementa o contador de linhas do sensor
                        contRegistros++;
                    } else {
                        contRegistros = 0;
                    }
                }
            }
        }       
        return contRegistros;//retorna a contador de registros do sensor
    }
    */
    
    /*
    public static String calculaData(String ultimaData){//Método que captura DATA atual do sistema   
        DateFormat formataData = new SimpleDateFormat("ddMMyyyy");
        Date data = new Date();
        return formataData.format(data);
    }
    */
}
