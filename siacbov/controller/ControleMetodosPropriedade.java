package br.edu.cio.controller;

import br.edu.cio.controller.ControleMetodosComuns;
import br.edu.cio.model.Animal;
import br.edu.cio.model.AreaPastagem;
import br.edu.cio.model.Funcionario;
import br.edu.cio.model.Lote;
import br.edu.cio.model.Propriedade;
import br.edu.cio.model.dao.AnimalDAO;
import br.edu.cio.model.dao.AreaPastagemDAO;
import br.edu.cio.model.dao.FuncionarioDAO;
import br.edu.cio.model.dao.LoteDAO;
import br.edu.cio.model.dao.PropriedadeDAO;
import br.edu.cio.util.DAOFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

public class ControleMetodosPropriedade implements ControleMetodosComuns{
    PropriedadeDAO acessoBD = DAOFactory.gerarPropriedadeDAO();//objeto global para trafego de informações com a classe que manipula o banco de dados
    LoteDAO acessoBDLote = DAOFactory.gerarLoteDAO();
    AreaPastagemDAO acessoBDPasto = DAOFactory.gerarAreaPastagemDAO();
    FuncionarioDAO acessoBDFuncionario = DAOFactory.gerarFuncionarioDAO();
    AnimalDAO acessoBDAnimal = DAOFactory.gerarAnimalDAO();
    
    @Override
    public void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Propriedade p = new Propriedade();
        p.setNome(request.getParameter("cmp_prop_nome"));
        p.setCnpj(request.getParameter("cmp_prop_cnpj"));
        p.setInscricao_estadual(request.getParameter("cmp_prop_iestadual"));
        p.setEndereco(request.getParameter("cmp_prop_endereco"));
        p.setMunicipio(request.getParameter("cmp_prop_cidade"));
        p.setUf(request.getParameter("cmp_prop_uf"));
        p.setProprietario(request.getParameter("cmp_prop_dono"));
        p.setArea(Double.parseDouble(request.getParameter("cmp_prop_area")));
        p.setAtividade_principal(request.getParameter("cmp_prop_ativ_principal"));
        try{
            acessoBD.cadastrarDAO(p);
            request.setAttribute("mensagem", "Propriedade cadastrada com sucesso!");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=propriedade").forward(request, response);//retorna para a index apresentando o erro
        } catch (ServletException | IOException ex){
            request.setAttribute("mensagem", "Propriedade não cadastrada.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=propriedade").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    @Override
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição
        Propriedade propriedade = acessoBD.buscarPorIdDAO(id);
        request.setAttribute("propriedade", propriedade);
        request.getRequestDispatcher("principal?d=forms&a=editar&f=propriedade").forward(request, response);//despacha para a proxima página 
    }
    
    @Override
    public void editarSalvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição
        Propriedade p = new Propriedade();
        p.setId(id);
        p.setCodigo(request.getParameter("cmp_edt_prop_codigo"));
        p.setNome(request.getParameter("cmp_edt_prop_nome"));
        p.setCnpj(request.getParameter("cmp_edt_prop_cnpj"));
        p.setInscricao_estadual(request.getParameter("cmp_edt_prop_iestadual"));
        p.setEndereco(request.getParameter("cmp_edt_prop_endereco"));
        p.setMunicipio(request.getParameter("cmp_edt_prop_cidade"));
        p.setUf(request.getParameter("cmp_edt_prop_uf"));
        p.setProprietario(request.getParameter("cmp_edt_prop_dono"));
        p.setArea(Double.parseDouble(request.getParameter("cmp_edt_prop_area")));
        p.setAtividade_principal(request.getParameter("cmp_edt_prop_ativ_principal"));
        try{
            acessoBD.editarDAO(p);
            p = acessoBD.buscarPorIdDAO(id);
            request.setAttribute("propriedade", p);
            request.setAttribute("mensagem", "Propriedade atualizada com sucesso!");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=propriedade").forward(request, response);//retorna para a index apresentando o erro
        } catch (ServletException | IOException ex){
            request.setAttribute("mensagem", "Propriedade não cadastrada.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=propriedade").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    @Override
    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição       
        if(acessoBDFuncionario.listarPorPropriedadeDAO(id) != null){
            request.setAttribute("mensagem", "Propriedade NÃO pode ser excluida. Existem funcionários vinculados.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=propriedade").forward(request, response);
        } else if(acessoBDAnimal.listarPorPropriedadeDAO(id) != null){
            request.setAttribute("mensagem", "Propriedade NÃO pode ser excluida. Existem animais vinculados.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=propriedade").forward(request, response);
        } else if(acessoBDLote.listarPorPropriedadeDAO(id) != null){
            request.setAttribute("mensagem", "Propriedade NÃO pode ser excluida. Existem lotes vinculados.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=propriedade").forward(request, response);
        } else if (acessoBDPasto.listarPorPropriedadeDAO(id) != null){
            request.setAttribute("mensagem", "Propriedade NÃO pode ser excluida. Existem pastos vinculados.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=propriedade").forward(request, response);
        } else if (!acessoBD.excluirDAO(id)){
            request.setAttribute("mensagem", "Erro ao tentar excluir Propriedade.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=propriedade").forward(request, response);
        } else {
            request.setAttribute("mensagem", "Propriedade excluida com sucesso.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=propriedade").forward(request, response);
        }
    }

    @Override
    public void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String valorBusca = request.getParameter("cmp_buscar_prop");//recebe o dado informado no formLogin do index
        List<Propriedade> resultado = acessoBD.buscarDAO(valorBusca);
        if(resultado != null){
            request.setAttribute("propEncontradas", resultado);
            request.getRequestDispatcher("principal?d=forms&a=resultBusca&f=propriedade").forward(request, response);//despacha para a proxima página 
        } else {
            request.setAttribute("mensagem", "Propriedade não cadastrada.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=propriedade").forward(request, response);//retorna para a index apresentando o erro
        }
    }   

    @Override
    public void detalhar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Propriedade resultado = acessoBD.buscarPorIdDAO(Integer.parseInt(request.getParameter("id")));
        if ( resultado != null ) {
            request.setAttribute("detalhesprop", resultado);
            request.getRequestDispatcher("principal?d=forms&a=detalhar&f=propriedade").forward(request, response);//despacha para a proxima página 
        } else {
            request.setAttribute("mensagem", "Propriedade não cadastrada.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=propriedade").forward(request, response);//retorna para a index apresentando o erro
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
