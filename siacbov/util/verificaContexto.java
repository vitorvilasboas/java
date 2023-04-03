
package br.edu.cio.util;

//import br.edu.cio.model.ContextoPercebido;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JV
 */
public class verificaContexto {

    public verificaContexto() {
    }
/*
    public static ContextoPercebido buscaUltimoRegistro(int idBuscado){
         List<String> registrosArquivo = (leArquivo());//invok a função para ler arquivo e armazena cada linha em um array de strings
         Collections.reverse(registrosArquivo);//inverte array 
         ContextoPercebido linhaDados = new ContextoPercebido();//conterá temporariamente cada grupo de informações contextuais dispostas em cada linha do arquivo
         ContextoPercebido ultimoRegistro = new ContextoPercebido();
         for(String linhaRegistro : registrosArquivo){//percorre os registros do array de strings enquanto houver informações e armazena cada posição em uma string
             if(!linhaRegistro.isEmpty()){//verifica se a string é nula (se a linha está em branco)
                 linhaDados = separaLinha(linhaRegistro);//invoka a função separaLinha e armazena temporariamente as informações de contexto já instanciadas em linhaDados
                 if(linhaDados.getId_animal()== idBuscado){//se o id da linha em questão for igual ao solicitado por parâmetro...
                     ultimoRegistro = linhaDados;
                     break;
                 }
             }
         }
         return ultimoRegistro;
     }
   
    public static List<ContextoPercebido> buscaHistoricoSensor(int idBuscado){
         List<String> registrosArquivo = (leArquivo());//invok a função para ler arquivo e armazena cada linha em um array de strings
         Collections.reverse(registrosArquivo);//inverte array 
         List<ContextoPercebido> historicoSensor = new ArrayList<>();//armazenará todos os registros do sensor encontrado no arquivo de registros
         ContextoPercebido linhaDados = new ContextoPercebido();//conterá temporariamente cada grupo de informações contextuais dispostas em cada linha do arquivo
         for(String linhaRegistro : registrosArquivo){//percorre os registros do array de strings enquanto houver informações e armazena cada posição em uma string
             if(!linhaRegistro.isEmpty()){//verifica se a string é nula (se a linha está em branco)
                 linhaDados = separaLinha(linhaRegistro);//invoka a função separaLinha e armazena temporariamente as informações de contexto já instanciadas em linhaDados
                 if(linhaDados.getId_animal()== idBuscado){//se o id da linha em questão for igual ao solicitado por parâmetro...
                     historicoSensor.add(linhaDados);//as informações são acrescentadas no array do histório do sensor
                     System.out.println("ID:"+linhaDados.getId_animal()+"   Posicao:"+linhaDados.getPosicao()+"   Data:"+linhaDados.getData()+"   Hora:"+linhaDados.getHora());
                 }
             }
         }
         return historicoSensor;
    }  
    
    public static List<String> leArquivo(){
          File arquivo = new File("C:\\Users\\JV\\Documents\\NetBeansProjects\\siac\\simuladorRSSF\\registrosSensores.txt");
          List<String> linhas = new ArrayList<>();
          try( InputStream entrada = new FileInputStream(arquivo)){
              Scanner leitor = new Scanner(entrada);
              while( leitor.hasNext() ){
                  linhas.add(leitor.nextLine());
              }
          }catch(IOException ex){
              ex.printStackTrace();
          }
          return linhas;
      }
  
    public static ContextoPercebido separaLinha(String registroLinha){//Recebe cada registro do arquivo, separa as variaveis e instancia o objeto do tipo ContextoPercebido
             ContextoPercebido contextoLinha = new ContextoPercebido();
             String id = "";
             String posicao = "";
             String data = "";
             String hora = "";
             Scanner sc = new Scanner(registroLinha);
             while (sc.hasNext()){//enquanto não encontrar o primeiro espaço " "  
                 id += sc.next();  
                 break;  
             }  
             while (sc.hasNext()){//enquanto não encontrar o segundo espaço " "   
                 posicao += sc.next();  
                 break;  
             }
             while (sc.hasNext()){//enquanto não encontrar o terceiro espaço " "   
                 data += sc.next();  
                 break;  
             }
             while (sc.hasNext()){//enquanto não encontrar o quarto espaço " "   
                 hora += sc.next();  
                 break;  
             }
             contextoLinha.setId_animal(Integer.parseInt(id));
             contextoLinha.setPosicao(Integer.parseInt(posicao));
             contextoLinha.setHora(hora);
             contextoLinha.setData(data);

             return contextoLinha;
     }*/
}


/*for (int i =0; i< registrosBois.length(); i++) {
    charAtual =  String.valueOf(registrosBois.charAt(i));            
}*/