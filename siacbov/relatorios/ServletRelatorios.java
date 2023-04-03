package br.edu.cio.relatorios;

import br.edu.cio.util.Conexao;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

public class ServletRelatorios extends HttpServlet {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conexao;
        ResultSet rs = null;
        JRResultSetDataSource jrRS = null;
        String tipo_rel = request.getParameter("cmp_relatorio_1");
        switch(tipo_rel){
            case "ocorrencias":{
                String id = request.getParameter("cmp_relatorio_2");
                conexao = Conexao.getConnection();
                try{
                    String  sql = "SELECT" +
                        "     cio.`cio_id` AS cio_cio_id," +
                        "     cio.`cio_codigo` AS cio_cio_codigo," +
                        "     cio.`cio_dt_registro` AS cio_cio_dt_registro," +
                        "     cio.`cio_hr_registro` AS cio_cio_hr_registro," +
                        "     cio.`cio_status` AS cio_cio_status," +
                        "     cio.`cio_mtd_id` AS cio_cio_mtd_id," +
                        "     cio.`cio_mtd_registro` AS cio_cio_mtd_registro," +
                        "     cio.`cio_dt_inicio` AS cio_cio_dt_inicio," +
                        "     cio.`cio_hr_inicio` AS cio_cio_hr_inicio," +
                        "     cio.`cio_dt_prev_termino` AS cio_cio_dt_prev_termino," +
                        "     cio.`cio_hr_prev_termino` AS cio_cio_hr_prev_termino," +
                        "     cio.`cio_duracao` AS cio_cio_duracao," +
                        "     cio.`cio_observacao` AS cio_cio_observacao," +
                        "     cio.`cio_animal_fk` AS cio_cio_animal_fk," +
                        "     cio.`cio_leitura_fk` AS cio_cio_leitura_fk," +
                        "     cio.`cio_atividade_fk` AS cio_cio_atividade_fk," +
                        "     animal.`ani_id` AS animal_ani_id," +
                        "     animal.`ani_codigo` AS animal_ani_codigo," +
                        "     animal.`ani_apelido` AS animal_ani_apelido," +
                        "     animal.`ani_monitorando` AS animal_ani_monitorando," +
                        "     animal.`ani_sensor_fk` AS animal_ani_sensor_fk," +
                        "     atividade_animal.`atv_id` AS atividade_animal_atv_id," +
                        "     atividade_animal.`atv_classificacao` AS atividade_animal_atv_classificacao," +
                        "     atividade_animal.`atv_animal_fk` AS atividade_animal_atv_animal_fk," +
                        "     cio.`cio_hr_termino` AS cio_cio_hr_termino," +
                        "     cio.`cio_dt_termino` AS cio_cio_dt_termino," +
                        "     sensor.`sen_id` AS sensor_sen_id," +
                        "     sensor.`sen_codigo` AS sensor_sen_codigo " +
                        "FROM" +
                        "     `animal` animal INNER JOIN `cio` cio ON animal.`ani_id` = cio.`cio_animal_fk`" +
                        "     INNER JOIN `atividade_animal` atividade_animal ON animal.`ani_id` = atividade_animal.`atv_animal_fk`" +
                        "     AND atividade_animal.`atv_id` = cio.`cio_atividade_fk`" +
                        "     INNER JOIN `sensor` sensor ON animal.`ani_sensor_fk` = sensor.`sen_id` " +
                        "WHERE" +
                        "     animal.`ani_codigo` = " + id +"  AND ( cio.`cio_status` = 'CONFIRMADO' OR cio.`cio_status` = 'ENCERRADO')";
                    PreparedStatement ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    jrRS = new JRResultSetDataSource(rs);
                } catch(Exception e){
                    System.out.println("Erro Banco: " + e.getMessage());
                }

                byte[] bytes = null;
                try {
                    InputStream url = getServletContext().getResourceAsStream("/relatorios/ocorrenciasCioAnimal.jasper");
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
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ServletRelatorios.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            case "emcio":{
                String id = request.getParameter("cmp_relatorio_2");
                conexao = Conexao.getConnection();
                try{
                    String  sql = "SELECT" +
                        "     cio.`cio_id` AS cio_cio_id," +
                        "     cio.`cio_codigo` AS cio_cio_codigo," +
                        "     cio.`cio_dt_registro` AS cio_cio_dt_registro," +
                        "     cio.`cio_hr_registro` AS cio_cio_hr_registro," +
                        "     cio.`cio_dt_inicio` AS cio_cio_dt_inicio," +
                        "     cio.`cio_hr_inicio` AS cio_cio_hr_inicio," +
                        "     cio.`cio_dt_prev_termino` AS cio_cio_dt_prev_termino," +
                        "     cio.`cio_hr_prev_termino` AS cio_cio_hr_prev_termino," +
                        "     cio.`cio_animal_fk` AS cio_cio_animal_fk," +
                        "     animal.`ani_id` AS animal_ani_id," +
                        "     animal.`ani_codigo` AS animal_ani_codigo," +
                        "     animal.`ani_apelido` AS animal_ani_apelido," +
                        "     animal.`ani_sensor_fk` AS animal_ani_sensor_fk," +
                        "     sensor.`sen_id` AS sensor_sen_id," +
                        "     sensor.`sen_codigo` AS sensor_sen_codigo" +
                        " FROM" +
                        "     `animal` animal INNER JOIN `cio` cio ON animal.`ani_id` = cio.`cio_animal_fk`" +
                        "     INNER JOIN `sensor` sensor ON animal.`ani_sensor_fk` = sensor.`sen_id`" +
                        " WHERE cio.`cio_status` = 'CONFIRMADO'";
                    PreparedStatement ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    jrRS = new JRResultSetDataSource(rs);
                    
                } catch(Exception e){
                    System.out.println("Erro Banco: " + e.getMessage());
                }

                byte[] bytes = null;
                try {
                    InputStream url = getServletContext().getResourceAsStream("/relatorios/animaisEmCio.jasper");
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
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ServletRelatorios.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            default: {
                break;
            }    
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
