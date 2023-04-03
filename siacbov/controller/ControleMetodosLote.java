package br.edu.cio.controller;

import br.edu.cio.controller.ControleMetodosComuns;
import br.edu.cio.model.AreaPastagem;
import br.edu.cio.model.Lote;
import br.edu.cio.model.Propriedade;
import br.edu.cio.model.dao.AnimalDAO;
import br.edu.cio.model.dao.AreaPastagemDAO;
import br.edu.cio.model.dao.LoteDAO;
import br.edu.cio.model.dao.PropriedadeDAO;
import br.edu.cio.util.DAOFactory;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControleMetodosLote implements ControleMetodosComuns{
    LoteDAO acessoBDLote = DAOFactory.gerarLoteDAO();
    AnimalDAO acessoBDAnimal = DAOFactory.gerarAnimalDAO();
    
    @Override
    public void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Lote l = new Lote();
        //l.setCodigo(request.getParameter("cmp_lote_codigo"));
        l.setDescricao(request.getParameter("cmp_lote_descricao"));
        l.setTipo(request.getParameter("cmp_lote_tipo"));
        l.setObservacao(request.getParameter("cmp_lote_observacao"));
        //l.setDt_cadastro(request.getParameter("cmp_lote_dt_cadastro"));
        PropriedadeDAO acessoBDPropriedade = DAOFactory.gerarPropriedadeDAO();
        l.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(Integer.parseInt(request.getParameter("cmp_lote_propriedade"))));
        AreaPastagemDAO acessoBDPasto = DAOFactory.gerarAreaPastagemDAO();
        l.setPasto(acessoBDPasto.buscarPorIdDAO(Integer.parseInt(request.getParameter("cmp_lote_pasto"))));
        try{
            acessoBDLote.cadastrarDAO(l);
            request.setAttribute("mensagem", "Lote cadastrado com sucesso!");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=lote").forward(request, response);//retorna para a index apresentando o erro
        } catch (ServletException | IOException ex){
            request.setAttribute("mensagem", "Lote não cadastrado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=lote").forward(request, response);//retorna para a index apresentando o erro
        } 
    }

    @Override
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição
        Lote lote = acessoBDLote.buscarPorIdDAO(id);
        request.setAttribute("lotes", lote);
        request.getRequestDispatcher("principal?d=forms&a=editar&f=lote").forward(request, response);//despacha para a proxima página 
    }

    @Override
    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição       
        if (acessoBDAnimal.listarPorPropriedadeDAO(id) != null) {
           request.setAttribute("mensagem", "Lote NÃO pode ser excluido. Existem animais vinculados.");
           request.getRequestDispatcher("principal?d=forms&a=buscar&f=lote").forward(request, response);
        }else 

            if (!acessoBDLote.excluirDAO(id)){
           request.setAttribute("mensagem", "Erro ao tentar excluir Lote.");
           request.getRequestDispatcher("principal?d=forms&a=buscar&f=lote").forward(request, response);
        } else {
           request.setAttribute("mensagem", "lote excluido com sucesso.");
           request.getRequestDispatcher("principal?d=forms&a=buscar&f=lote").forward(request, response);
        }
    }

    @Override
    public void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String valorBusca = request.getParameter("cmp_bsc_lote");//recebe o dado informado no formLogin do index
        List<Lote> resultado = acessoBDLote.buscarDAO(valorBusca);
        if(resultado != null){
            request.setAttribute("lotesEncontrados", resultado);
            request.getRequestDispatcher("principal?d=forms&a=resultBusca&f=lote").forward(request, response);//despacha para a proxima página 
        } else {
            request.setAttribute("mensagem", "Propriedade não cadastrada.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=lote").forward(request, response);//retorna para a index apresentando o erro
        }
    }
    
    @Override
    public void editarSalvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição
        Lote l = new Lote();
        l.setId(id);
        l.setDescricao(request.getParameter("cmp_lote_descricao"));
        l.setTipo(request.getParameter("cmp_lote_tipo"));
        l.setObservacao(request.getParameter("cmp_lote_observacao"));
        //l.setDt_cadastro(request.getParameter("cmp_lote_dt_cadastro"));
        PropriedadeDAO acessoBDPropriedade = DAOFactory.gerarPropriedadeDAO();
        l.setPropriedade(acessoBDPropriedade.buscarPorIdDAO(Integer.parseInt(request.getParameter("cmp_lote_propriedade"))));
        AreaPastagemDAO acessoBDPasto = DAOFactory.gerarAreaPastagemDAO();
        l.setPasto(acessoBDPasto.buscarPorIdDAO(Integer.parseInt(request.getParameter("cmp_lote_pasto"))));
        try{
            acessoBDLote.editarDAO(l);
            l = acessoBDLote.buscarPorIdDAO(id);
            request.setAttribute("lotes", l);
            request.setAttribute("mensagem", "Lote atualizado com sucesso!");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=lote").forward(request, response);//retorna para a index apresentando o erro
        } catch (ServletException | IOException ex){
            request.setAttribute("mensagem", "Lote não cadastrada.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=lote").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    @Override
    public void detalhar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Lote resultado = acessoBDLote.buscarPorIdDAO(Integer.parseInt(request.getParameter("id")));
        if ( resultado != null ) {
            request.setAttribute("detalheslote", resultado);
            request.getRequestDispatcher("principal?d=forms&a=detalhar&f=lote").forward(request, response);//despacha para a proxima página 
        } else {
            request.setAttribute("mensagem", "Lote não cadastrado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=lote").forward(request, response);//retorna para a index apresentando o erro
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