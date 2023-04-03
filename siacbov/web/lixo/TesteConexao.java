
package br.edu.cio.testes;

import br.edu.cio.util.Conexao;
import javax.swing.JOptionPane;

/**
 *
 * @author VilasBoas
 */
public class TesteConexao {
    public static void main(String[] args) {
        try{
            Conexao.getConnection();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getCause());
        }
    }
}
