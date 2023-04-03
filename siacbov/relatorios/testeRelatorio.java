package br.edu.cio.relatorios;

import br.edu.cio.util.Conexao;
import static br.edu.cio.util.Conexao.getConnection;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class testeRelatorio extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Connection conexao;
        ResultSet rs = null;
        conexao = Conexao.getConnection();
        JRResultSetDataSource jrRS = null;
        try{
            String  sql = "SELECT" +
"     propriedade.`prop_id`," +
"     propriedade.`prop_codigo`," +
"     propriedade.`prop_nome`" +
"FROM" +
"     `propriedade` propriedade";
            PreparedStatement ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            jrRS = new JRResultSetDataSource(rs);
        } catch(Exception e){
            System.out.println("Erro Banco: " + e.getMessage());
        }
        /*
        Map parameters = new HashMap();  
        //parameters.put(0, 3);  
        String path = "C:/Users/JV/Documents/NetBeansProjects/siac/web/relatorios/ciosAnimal.jasper";  
        File file = new File(path);  
        file = file.getAbsoluteFile();  
        String repStr2 = file.getPath();  
        try {  
            JasperFillManager.fillReportToFile(repStr2, null ,conexao);  
            JasperPrint jasperPrint = JasperFillManager.fillReport(repStr2, parameters,conexao);  
            JasperViewer viewer = new JasperViewer(jasperPrint,false);  
            viewer.setVisible(true);     
        } catch (JRException jex) {  
            JOptionPane.showMessageDialog(null,"JasperException"+jex.getMessage());  
        } 
        */
        
        byte[] bytes = null;
        try {
            InputStream url = getServletContext().getResourceAsStream("/relatorios/teste2.jasper");
            System.out.println("URL para o jasper: "+ url);
            JasperReport relatorioJasper = (JasperReport)JRLoader.loadObject(url);
            Map parametros = new HashMap();
            bytes = JasperRunManager.runReportToPdf(relatorioJasper, parametros, jrRS);
        } catch (Exception e) {
            System.out.println("Erro ao carregar o relatorio");
            e.printStackTrace();
        }
        
        if(bytes != null && bytes.length > 0){
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);  
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes, 0, bytes.length);
            outputStream.flush();
            outputStream.close();
        }
                
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}


 
/* Map parameters = new HashMap();  
//parameters.put(0, 3);  
String path = "/relatorios/teste123.jasper";  
File file = new File(path);  
file = file.getAbsoluteFile();  
String repStr2 = file.getPath();  
try {  
    JasperFillManager.fillReportToFile(repStr2, null ,conexao);  
    JasperPrint jasperPrint = JasperFillManager.fillReport(repStr2, parameters,conexao);  
    JasperViewer viewer = new JasperViewer(jasperPrint,false);  
    viewer.setVisible(true);     
} catch (JRException jex) {  
    JOptionPane.showMessageDialog(null,"JasperException"+jex.getMessage());  
} */

/*InputStream stream = getServletContext().getResourceAsStream("/relatorios/teste123.jasper");  
JasperReport relatorio = JasperManager.loadReport(stream);  
JasperPrint impressao = JasperManager.fillReport(relatorio, parametros, jrRS);                         
JasperManager.printReportToPdfStream(impressao, response.getOutputStream());   */