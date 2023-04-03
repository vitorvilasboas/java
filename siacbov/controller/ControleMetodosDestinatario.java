package br.edu.cio.controller;

import br.edu.cio.controller.ControleMetodosComuns;
import br.edu.cio.model.Destinatario;
import br.edu.cio.model.Funcionario;
import br.edu.cio.model.dao.DestinatarioDAO;
import br.edu.cio.model.dao.FuncionarioDAO;
import br.edu.cio.util.DAOFactory;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControleMetodosDestinatario implements ControleMetodosComuns {
    DestinatarioDAO acessoBDDestinatario = DAOFactory.gerarDestinatarioDAO();
    public void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Destinatario d = new Destinatario();  
        d.setEmail(request.getParameter("email"));
        FuncionarioDAO acessobbFuncionario = DAOFactory.gerarFuncionarioDAO();
        Funcionario func = acessobbFuncionario.buscarPorIdDAO(Integer.parseInt(request.getParameter("vinc_func")));
        d.setFuncionario(func);
        d.setCelular(request.getParameter("celular"));
        d.setAtivo(Integer.parseInt(request.getParameter("status")));
        try {
            acessoBDDestinatario.inserirDAO(d);
            request.setAttribute("mensagem", "Destinatario cadastrado com sucesso!");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=destinatario").forward(request, response);//retorna para a index apresentando o erro
        } catch (ServletException | IOException ex) {
            request.setAttribute("mensagem", "Destinatario não cadastrado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=destinatario").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    @Override
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição
        Destinatario destinatario= acessoBDDestinatario.buscarPorIdDAO(id);
        request.setAttribute("destinatarios", destinatario);
        request.getRequestDispatcher("principal?d=forms&a=editar&f=destinatario").forward(request, response);//despacha para a proxima página 
    }

    @Override
    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição         
        if (!acessoBDDestinatario.excluirDAO(id)) {
            request.setAttribute("mensagem", "Erro ao tentar excluir Destinatario.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=destinatario").forward(request, response);
        } else {
            request.setAttribute("mensagem", "Destinatario excluido com sucesso.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=destinatario").forward(request, response);
        }
    }

    @Override
    public void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String valorBusca = request.getParameter("cmp_bsc_des");//recebe o dado informado no formLogin do index
        List<Destinatario> resultado = acessoBDDestinatario.buscarDAO(valorBusca);
        if (resultado != null) {
            request.setAttribute("DestinatarioEncontrados", resultado);
            request.getRequestDispatcher("principal?d=forms&a=resultBusca&f=destinatario").forward(request, response);//despacha para a proxima página 
        } else {
            request.setAttribute("mensagem", "Destinatario não cadastrado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=destinatario").forward(request, response);//retorna para a index apresentando o erro
        }
    }


    @Override
    public void editarSalvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição
        Destinatario d = new Destinatario();
        d.setId(id);
        d.setEmail(request.getParameter("email"));
        FuncionarioDAO acessobbFuncionario = DAOFactory.gerarFuncionarioDAO();
        Funcionario func = acessobbFuncionario.buscarPorIdDAO(Integer.parseInt(request.getParameter("vinc_func")));
        d.setFuncionario(func);
        d.setCelular(request.getParameter("celular"));
        d.setAtivo(Integer.parseInt(request.getParameter("status")));
        try {
            acessoBDDestinatario.editarDAO(d);
//            d = acessoBDDestinatario.buscarPorIdDAO(id);
//            request.setAttribute("destinatarios", d);
            request.setAttribute("mensagem", "Destinatario atualizado com sucesso!");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=destinatario").forward(request, response);//retorna para a index apresentando o erro
        } catch (ServletException | IOException ex) {
            request.setAttribute("mensagem", "Destinatario não cadastrada.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=destinatario").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    @Override
    public void detalhar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {      
        Destinatario resultado = acessoBDDestinatario.buscarPorIdDAO(Integer.parseInt(request.getParameter("id")));
        if ( resultado != null ) {
            request.setAttribute("detalhesdest", resultado);
            request.getRequestDispatcher("principal?d=forms&a=detalhar&f=destinatario").forward(request, response);//despacha para a proxima página  
        } else {
            request.setAttribute("mensagem", "Destinatario não cadastrado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=destinatario").forward(request, response);//retorna para a index apresentando o erro
        } 
    }

    @Override
    public void recuperarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void carregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}


