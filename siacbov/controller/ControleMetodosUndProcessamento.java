package br.edu.cio.controller;

import br.edu.cio.controller.ControleMetodosComuns;
import br.edu.cio.model.Funcionario;
import br.edu.cio.model.UndProcessamento;
import br.edu.cio.model.dao.UndProcessamentoDAO;
import br.edu.cio.util.DAOFactory;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControleMetodosUndProcessamento implements ControleMetodosComuns {
    UndProcessamentoDAO acessoBDUndProcessamento = DAOFactory.gerarUndProcessamentoDAO();

    @Override
    public void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UndProcessamento u = new UndProcessamento();
        // u.setId(id);
        // u.setCodigo(request.getParameter(""));
        u.setQtd_sensores(Integer.parseInt(request.getParameter("cmp_sensor_quantidade")));
        u.setDescricao(request.getParameter("cmp_sensor_descricao"));
        u.setNserie(request.getParameter("cmp_sensor_Nseries"));
        u.setLocal_fixacao(request.getParameter("cmp_sensor_local_fixacao"));
        u.setTecnologia(request.getParameter("cmp_sensor_tecnologia"));
        u.setObservacao(request.getParameter("cmp_sensor_observacao"));
        try {
            acessoBDUndProcessamento.inserirDAO(u);
            request.setAttribute("mensagem", "Unidade de Processamento cadastrada com sucesso!");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=undProcessamento").forward(request, response);//retorna para a index apresentando o erro
        } catch (ServletException | IOException ex) {
            request.setAttribute("mensagem", "Unidade de Processamento não cadastrado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=undProcessamento").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    @Override
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição
        UndProcessamento und = acessoBDUndProcessamento.buscarPorIdDAO(id);
        request.setAttribute("undProcessamentos", und);
        request.getRequestDispatcher("principal?d=forms&a=editar&f=undProcessamento").forward(request, response);//despacha para a proxima página 
    }
 
    @Override
    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição  
        if (!acessoBDUndProcessamento.excluirDAO(id)) {
            request.setAttribute("mensagem", "Erro ao tentar excluir Unidade de Processamento.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=undProcessamento").forward(request, response);
        } else {
            request.setAttribute("mensagem", "Unidade de Processamento excluida com sucesso.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=undProcessamento").forward(request, response);
        }
    }

    @Override
    public void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String valorBusca = request.getParameter("cmp_bsc_undProcessamento");//recebe o dado informado no formLogin do index
        List<UndProcessamento> resultado = acessoBDUndProcessamento.buscarDAO(valorBusca);
        if (resultado != null) {
            request.setAttribute("undProcessamentoEncontrados", resultado);
            request.getRequestDispatcher("principal?d=forms&a=resultBusca&f=undProcessamento").forward(request, response);//despacha para a proxima página 
        } else {
            request.setAttribute("mensagem", "Unidade de Processamento não cadastrada.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=undProcessamento").forward(request, response);//retorna para a index apresentando o erro
        }
    }

   
    @Override
    public void editarSalvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição
        UndProcessamento u = new UndProcessamento();
        u.setId(id);
        // u.setCodigo(request.getParameter(""));
        u.setQtd_sensores(Integer.parseInt(request.getParameter("cmp_sensor_quantidade")));
        u.setDescricao(request.getParameter("cmp_sensor_descricao"));
        u.setNserie(request.getParameter("cmp_sensor_Nseries"));
        u.setLocal_fixacao(request.getParameter("cmp_sensor_local_fixacao"));
        u.setTecnologia(request.getParameter("cmp_sensor_tecnologia"));
        u.setObservacao(request.getParameter("cmp_sensor_observacao"));
        try {
            acessoBDUndProcessamento.editarDAO(u);
            //f = acessoBDFuncionario.buscarPorIdDAO(id2);
            request.setAttribute("undProcessamentos", u);
            request.setAttribute("mensagem", "Unidade de Processamento atualizada com sucesso!");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=undProcessamento").forward(request, response);//retorna para a index apresentando o erro
        } catch (ServletException | IOException ex) {
            request.setAttribute("mensagem", "Unidade de Processamento  não atualizada.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=undProcessamento").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    @Override
    public void detalhar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UndProcessamento resultado = acessoBDUndProcessamento.buscarPorIdDAO(Integer.parseInt(request.getParameter("id")));
        if (resultado != null) {
            request.setAttribute("detalhesUP", resultado);
            request.getRequestDispatcher("principal?d=forms&a=detalhar&f=undProcessamento").forward(request, response);//despacha para a proxima página 
        } else {
            request.setAttribute("mensagem", "Unidade de Processamento não cadastrada.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=undProcessamento").forward(request, response);//retorna para a index apresentando o erro
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
