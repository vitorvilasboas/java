package projetoum;
import javax.swing.JOptionPane;
public class ProjetoUm {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Cachorro[] vetorCachorro = new Cachorro[3];
        for(int i=0; i<3;i++){
            Cachorro cao = new Cachorro();
            Pessoa p = new Pessoa();
            cao.nome = JOptionPane.showInputDialog("Informe o nome do cachorro: ");
            p.nome = JOptionPane.showInputDialog("Informe o nome do Dono: ");
            cao.dono = p;
            vetorCachorro[i] = cao;
        }
        for(int i=0; i<3;i++){
            JOptionPane.showMessageDialog(null, "Nome: "+vetorCachorro[i].nome);
            JOptionPane.showMessageDialog(null, "Nome: "+vetorCachorro[i].dono.nome);
        }
     }
}       
        
        /*
        
        //atribuido valores ao objeto cao
        cao.nome = "Lessi";
        cao.raca = "Pastor";
        cao.cor = "Preto/Branco";
        cao.tamanho = 1.10;
        cao.apelido = "Les";
        cao.tipo_patas = "quadrupede";
        cao.idade = 2;
        cao.cio = false;
        p.nome = "Seu Fulano";
        cao.dono = p;

        */
        
