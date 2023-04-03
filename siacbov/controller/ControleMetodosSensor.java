package br.edu.cio.controller;

import br.edu.cio.controller.ControleMetodosComuns;
import br.edu.cio.model.Animal;
import br.edu.cio.model.Propriedade;
import br.edu.cio.model.Sensor;
import br.edu.cio.model.UndProcessamento;
import br.edu.cio.model.dao.AnimalDAO;
import br.edu.cio.model.dao.SensorDAO;
import br.edu.cio.model.dao.UndProcessamentoDAO;
import br.edu.cio.util.DAOFactory;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControleMetodosSensor implements ControleMetodosComuns {

    SensorDAO acessoBDSensor = DAOFactory.gerarSensorDAO();
    UndProcessamentoDAO acessoBDCentral = DAOFactory.gerarUndProcessamentoDAO();
    AnimalDAO acessoBDAnimal = DAOFactory.gerarAnimalDAO();

    public void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Sensor s = new Sensor();
        s.setNserie(request.getParameter("cmp_Serie_sensor"));
        s.setTipo(request.getParameter("cmp_tipo_sensor"));
        s.setDescricao(request.getParameter("cmp_descricao_sensor"));
        s.setMedicao(request.getParameter("cmp_medicao_sensor"));
        s.setAlcance(Integer.parseInt(request.getParameter("cmp_alcance_sensor")));
        s.setTecnologia(request.getParameter("cmp_tecnologia_sensor"));
        s.setAtivo(0);
        UndProcessamento c = acessoBDCentral.buscarPorIdDAO(Integer.parseInt(request.getParameter("cmp_vinc_central")));
        s.setCentral(c);
        try {
            acessoBDSensor.inserirDAO(s);
            request.setAttribute("mensagem", "Sensor cadastrado com sucesso!");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=sensor").forward(request, response);//retorna para a index apresentando o erro
        } catch (ServletException | IOException ex) {
            request.setAttribute("mensagem", "Sensor não cadastrado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=sensor").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    @Override
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição
        Sensor sensor = acessoBDSensor.buscarPorIdDAO(id);
        request.setAttribute("sensores", sensor);
        request.getRequestDispatcher("principal?d=forms&a=editar&f=sensor").forward(request, response);//despacha para a proxima página 
    }

    @Override
    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição       
        if (!acessoBDSensor.excluirDAO(id)) {
            request.setAttribute("mensagem", "Erro ao tentar excluir Sensor.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=sensor").forward(request, response);
        } else {
            request.setAttribute("mensagem", "sensor excluido com sucesso.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=sensor").forward(request, response);
        }
    }

    @Override
    public void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String valorBusca = request.getParameter("cmp_bsc_sensor");//recebe o dado informado no formLogin do index
        List<Sensor> resultado = acessoBDSensor.buscarDAO(valorBusca);
        if (resultado != null) {
            request.setAttribute("SensoresEncontrados", resultado);
            request.getRequestDispatcher("principal?d=forms&a=resultBusca&f=sensor").forward(request, response);//despacha para a proxima página 
        } else {
            request.setAttribute("mensagem", "Sensor não cadastrado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=sensor").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    @Override
    public void editarSalvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição
        Sensor s = new Sensor();
        s.setId(id);
        s.setCodigo(request.getParameter("codigo"));
        s.setNserie(request.getParameter("cmp_nSerie_sensor"));
        s.setTipo(request.getParameter("cmp_tipo_sensor"));
        s.setDescricao(request.getParameter("cmp_descricao_sensor"));
        s.setMedicao(request.getParameter("cmp_medicao_sensor"));
        s.setAlcance(Integer.parseInt(request.getParameter("cmp_alcance_sensor")));
        s.setTecnologia(request.getParameter("cmp_tecnologia_sensor"));
        UndProcessamento c = acessoBDCentral.buscarPorIdDAO(Integer.parseInt(request.getParameter("vinc_central")));
        s.setCentral(c);
        try {
            acessoBDSensor.editarDAO(s);
            s = acessoBDSensor.buscarPorIdDAO(id);
            request.setAttribute("sensors", s);
            request.setAttribute("mensagem", "Sensor atualizado com sucesso!");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=sensor").forward(request, response);//retorna para a index apresentando o erro
        } catch (ServletException | IOException ex) {
            request.setAttribute("mensagem", "Sensor não cadastrado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=sensor").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    @Override
    public void detalhar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {      
        Sensor resultado = acessoBDSensor.buscarPorIdDAO(Integer.parseInt(request.getParameter("id")));
        if ( resultado != null ) {
            request.setAttribute("detalhessensor", resultado);
            request.getRequestDispatcher("principal?d=forms&a=detalhar&f=sensor").forward(request, response);//despacha para a proxima página 
        } else {
            request.setAttribute("mensagem", "Sensor não cadastrado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=sensor").forward(request, response);//retorna para a index apresentando o erro
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
