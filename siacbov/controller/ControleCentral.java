package br.edu.cio.controller;

import br.edu.cio.controller.ControleMetodosComunsServicos;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControleCentral extends HttpServlet {

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
            throws ServletException, IOException {//Controlador que receberá todas as requisições
        HttpSession sessao = request.getSession();
        if(request.getParameter("cls")==null || request.getParameter("mtd")==null){
            request.setAttribute("mensagem", "Parâmetros não informados");
            request.getRequestDispatcher("principal.jsp?d=&f=home").forward(request, response);//retorna para home apresentando o erro
        } else {
            try {
                Class classe_destino = Class.forName("br.edu.cio.controller.ControleMetodos"+request.getParameter("cls"));//captura a classe de destino de forma dinâmica
                Method metodo_destino = classe_destino.getMethod(request.getParameter("mtd"), HttpServletRequest.class, HttpServletResponse.class);//captura o método de destino de forma dinâmica
                if("Config".equals(request.getParameter("cls")) || "Monitoramento".equals(request.getParameter("cls")) || "BCC".equals(request.getParameter("cls")) || "Cio".equals(request.getParameter("cls")) || "Alerta".equals(request.getParameter("cls"))){
                    ControleMetodosComunsServicos aciona_controle = (ControleMetodosComunsServicos) classe_destino.newInstance();            
                    metodo_destino.invoke(aciona_controle, request, response);//invoca o método capturado dinâmicamente
                } else {
                    ControleMetodosComuns aciona_controle = (ControleMetodosComuns) classe_destino.newInstance();            
                    metodo_destino.invoke(aciona_controle, request, response);//invoca o método capturado dinâmicamente
                }
            } catch (InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ControleCentral.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Ocorreu um erro no controlador central: "+ex.getCause()+" "+ex.getMessage());
                log(ex.getMessage());//joga mensagem de erro no log
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
       /* if(sessao.getAttribute("idLogado") == null && !request.getParameter("mtd").equals("autenticar")){
            request.setAttribute("mensagem", "Ops. Você deve efetuar Login para ter acesso à este serviço!");
            request.getRequestDispatcher("index.jsp").forward(request, response);//retorna para a index apresentando o erro
            //PrintWriter out = response.getWriter();
            //out.print("<script type='text/javascript'> alert('<%= request.getAttribute('mensagem') %>'); </script>");
            //response.sendRedirect("index.jsp");
        } else {*/