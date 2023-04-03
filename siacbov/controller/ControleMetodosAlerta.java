package br.edu.cio.controller;

import br.edu.cio.model.Alerta;
import br.edu.cio.model.AlertaEmitido;
import br.edu.cio.model.Cio;
import br.edu.cio.model.dao.AlertaDAO;
import br.edu.cio.model.dao.AlertaEmitidoDAO;
import br.edu.cio.model.dao.CioDAO;
import br.edu.cio.util.DAOFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

public class ControleMetodosAlerta implements ControleMetodosComunsServicos{
    
    CioDAO acessoBDCio = DAOFactory.gerarCioDAO();
    AlertaDAO acessoBDAlerta = DAOFactory.gerarAlertaDAO();
    AlertaEmitidoDAO acessoBDAlem = DAOFactory.gerarAlertaEmitidoDAO();
    
    @Override
    public void carregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editarSalvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println(">> TESTE");
        JOptionPane.showMessageDialog(null, "TESTE");
        request.getRequestDispatcher("principal?d=forms&a=&f=home").forward(request, response);//despacha para a proxima página
        /*String opcaoBusca = request.getParameter("cmp_mtd_busca_alerta");
        List<AlertaEmitido> todosEmitidos = acessoBDAlem.buscarDAO();
        switch(opcaoBusca){
            case "BUSCARPORANIMAL": {
                String dadoAnimal = request.getParameter("cmp_alerta_busca");
                List<AlertaEmitido> alertasAnimal = new ArrayList<>();
                for(AlertaEmitido ae : todosEmitidos){
                    System.out.println(">> " + ae.getId() + " | "+ ae.getAlerta().getAnimal().getApelido() + " | " + ae.getAlerta().getCio().getCodigo());
                    if(ae.getAlerta().getAnimal().getCodigo().equals(dadoAnimal) || ae.getAlerta().getAnimal().getApelido().equals(dadoAnimal))
                        alertasAnimal.add(ae);
                }
                if(alertasAnimal != null){
                    //HttpSession sessao = request.getSession();
                    request.setAttribute("alertasBusca", alertasAnimal);
                    request.getRequestDispatcher("principal?d=forms&a=resultBusca&f=alerta").forward(request, response);//despacha para a proxima página
                } else {
                    request.setAttribute("mensagem", "Não existem alertas cadastrados com o código informado.");
                    request.getRequestDispatcher("principal?d=forms&a=buscar&f=cio").forward(request, response);//retorna para a index apresentando o erro
                }
                break;
            }
            case "BUSCARPORCODIGOCIO": {
                String dadoAnimal = request.getParameter("cmp_alerta_busca");
                List<AlertaEmitido> alertasAnimal = new ArrayList<>();
                for(AlertaEmitido ae : todosEmitidos){
                    if(ae.getAlerta().getCio().getCodigo().equals(dadoAnimal))
                        alertasAnimal.add(ae);
                }
                if(alertasAnimal != null){
                    HttpSession sessao = request.getSession();
                    request.setAttribute("alertasBusca", alertasAnimal);
                    request.getRequestDispatcher("principal?d=forms&a=resultBusca&f=alerta").forward(request, response);//despacha para a proxima página
                } else {
                    request.setAttribute("mensagem", "Não existem alertas cadastrados com o código informado.");
                    request.getRequestDispatcher("principal?d=forms&a=buscar&f=cio").forward(request, response);//retorna para a index apresentando o erro
                }
                break;
            }
            case "BUSCARPORDATAREGISTRO": {
                break;
            }
            default:
                break;
        }*/
    }

    @Override
    public void listarPendentes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void avaliarSalvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void filtrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void detalhar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AlertaEmitido resultado = acessoBDAlem.buscarPorId(Integer.parseInt(request.getParameter("id")));
        if (resultado != null) {
            request.setAttribute("alertaDetalhes", resultado);
            request.getRequestDispatcher("principal?d=forms&a=detalhar&f=alerta").forward(request, response);//despacha para a proxima página 
        } else {
            request.setAttribute("mensagem", "Alerta não cadastrado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=alerta").forward(request, response);//retorna para a index apresentando o erro
        }
    }
    
}
