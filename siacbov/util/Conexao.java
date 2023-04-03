
package br.edu.cio.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author VilasBoas
 */
public class Conexao {
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");//registrando o driver/classe do mysql
            return DriverManager.getConnection("jdbc:mysql://localhost/siac", "root", "root");
        } catch (SQLException ex) {//Excessão de Erro nos dados do banco
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);// Faz com que a excessão seja apresentada no log do servidor de aplicação
            throw  new RuntimeException("Erro SQLException ao abrir a conexao em Conexão", ex);//lança a excessão para quem invoca este método, sem obrigação de tratamento
        } catch (ClassNotFoundException ex) {//Excessão de erro na procura da classe JDBC (driver MySQL)
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            throw  new RuntimeException("Erro ClassNotFoundException em Conexão", ex);
        }
    }
}
