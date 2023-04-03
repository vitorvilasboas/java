
package SensoriamentoSimulado;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class arquivoTeste {/*
    public void arquivo(){
        File arquivo = new File("C:\\Users\\JV\\Desktop\\arquivoTeste.txt");
        try {
            
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);
        
            String linha = br.readLine();
            ArrayList<String> vetorRegistros = new ArrayList();
            
            while(linha != null){
                if(!linha.equals("linha3")){
                    vetorRegistros.add(linha);
                }
                linha = br.readLine();
            }
            
            br.close();
            fr.close();
            FileWriter fw2 = new FileWriter(arquivo, true);
            fw2.close();
            
            FileWriter fw = new FileWriter(arquivo);
            BufferedWriter bw = new BufferedWriter(fw);
            
            for(int i=0;i<vetorRegistros.size();i++){
                bw.write(vetorRegistros.get(i));
                bw.newLine();
            }
            
            bw.close();
            fw.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(arquivoTeste.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(arquivoTeste.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   */ 
}
