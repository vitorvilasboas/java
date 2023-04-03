package br.edu.cio.model.dao.mysql;

import br.edu.cio.model.Alerta;
import br.edu.cio.model.AlertaEmitido;
import br.edu.cio.model.Animal;
import br.edu.cio.model.AtividadeAnimal;
import br.edu.cio.model.Cio;
import br.edu.cio.model.Destinatario;
import br.edu.cio.model.Leitura;
import br.edu.cio.util.Conexao;
import static br.edu.cio.util.Conexao.getConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class MetodosUteisDAOMySQL extends Conexao {

    public static PreparedStatement pst = null;

    public static ResultSet getAnimal() {
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM animal";
            pst = getConnection().prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
    
    public static ResultSet getAnimaisMonitorados() {
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM animal WHERE ani_monitorando=1";
            pst = getConnection().prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    public static ResultSet getPasto() {
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM pasto";
            pst = getConnection().prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    public static ResultSet getLote() {
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM lote";
            pst = getConnection().prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    public static ResultSet getPropriedade() {
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM propriedade";
            pst = getConnection().prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    public static ResultSet getFuncionario() {
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM FUNCIONARIO";
            pst = getConnection().prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    public static ResultSet getCentral() {
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM CENTRAL";
            pst = getConnection().prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    public static ResultSet getSensor() {
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM sensor";
            pst = getConnection().prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
    
    public static ResultSet getSensorInativos() {
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM sensor where sen_ativo=0";
            pst = getConnection().prepareStatement(sql);
            rs = pst.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }

    public static ResultSet getFuncionarioPorCPF(String cpf1) {
        ResultSet rs = null;
        try {
            String sql;
            PreparedStatement ps;
            if ("".equals(cpf1)) {
                sql = "SELECT * FROM funcionario";
                pst = getConnection().prepareStatement(sql);
            } else {
                sql = "SELECT * FROM FUNCIONARIO WHERE FUNC_CPF LIKE ?";
                pst = getConnection().prepareStatement(sql);
                pst.setString(1, "%" + cpf1 + "%");
            }
            rs = pst.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rs;
    }
    
    

}
