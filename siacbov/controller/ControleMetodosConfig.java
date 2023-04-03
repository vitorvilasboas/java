package br.edu.cio.controller;

import br.edu.cio.model.Configuracao;
import br.edu.cio.model.dao.ConfiguracaoDAO;
import br.edu.cio.util.DAOFactory;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControleMetodosConfig implements ControleMetodosComunsServicos{
    
    ConfiguracaoDAO acessoBD = DAOFactory.gerarConfiguracaoDAO();
    int idConfig = 1;
    
    @Override
    public void carregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Configuracao cfgVigente = acessoBD.carregarDAO(idConfig);
        
        if(cfgVigente != null){
            HttpSession sessao = request.getSession();
            sessao.setAttribute("configVigente", cfgVigente);
            request.getRequestDispatcher("principal?d=forms&a=load&f=config").forward(request, response);//despacha para a proxima página
        } else {
            request.setAttribute("mensagem", "Erro no carregamento da configuração vigente");
            request.getRequestDispatcher("principal?d=&f=home").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    @Override
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoDataBr = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoCompletoUSA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatoHoraBr = new SimpleDateFormat("HH:mm:ss");
        Configuracao novaConfig = new Configuracao();
        novaConfig.setIntervalo_anestro(Integer.parseInt(request.getParameter("cmp_edt_config_1")));
        novaConfig.setQtd_oco_pra_cio(Integer.parseInt(request.getParameter("cmp_edt_config_2")));
        novaConfig.setDuracao_cio(Integer.parseInt(request.getParameter("cmp_edt_config_3")));
        novaConfig.setPerc_min_atv_media(Integer.parseInt(request.getParameter("cmp_edt_config_4")));
        novaConfig.setPerc_min_atv_alta(Integer.parseInt(request.getParameter("cmp_edt_config_14")));
        novaConfig.setIntervalo_entre_leituras(Integer.parseInt(request.getParameter("cmp_edt_config_5")));
        novaConfig.setAlerta_quando(request.getParameter("cmp_edt_config_6"));
        novaConfig.setEnvio_celular(request.getParameter("cmp_edt_config_7"));
        novaConfig.setEnvio_email(request.getParameter("cmp_edt_config_8"));
        novaConfig.setQtd_alertas(Integer.parseInt(request.getParameter("cmp_edt_config_9")));
        novaConfig.setTempo_entre_alertas(request.getParameter("cmp_edt_config_10"));
        novaConfig.setMsg_alerta_ocorrencia(request.getParameter("cmp_edt_config_11"));
        novaConfig.setMsg_alerta_cio(request.getParameter("cmp_edt_config_12"));
        novaConfig.setReg_sensores(request.getParameter("cmp_edt_config_13"));
        novaConfig.setMin_anestro_normal(Integer.parseInt(request.getParameter("cmp_edt_config_cta1")));
        novaConfig.setMin_anestro_longo(Integer.parseInt(request.getParameter("cmp_edt_config_cta2")));
        novaConfig.setMin_anestro_mlongo(Integer.parseInt(request.getParameter("cmp_edt_config_cta3")));
        
        int i=0;
        String dth = "";
        while(i<request.getParameter("cmp_edt_config_0").length()){
            if(request.getParameter("cmp_edt_config_0").charAt(i) == 'T')
                dth += " ";
            else
                dth +=request.getParameter("cmp_edt_config_0").charAt(i);
            i++;
        }
        dth += ":00";
        
        i=0;
        String dth_ult_cap = "";
        while(i<request.getParameter("cmp_edt_config_100").length()){
            if(request.getParameter("cmp_edt_config_100").charAt(i) == 'T')
                dth_ult_cap += " ";
            else
                dth_ult_cap +=request.getParameter("cmp_edt_config_100").charAt(i);
            i++;
        }
        dth_ult_cap += ":00";
        
        
        try {
            novaConfig.setData_atual(formatoDataBr.format(formatoCompletoUSA.parse(dth)));
            novaConfig.setHora_atual(formatoHoraBr.format(formatoCompletoUSA.parse(dth)));
            novaConfig.setDt_ult_captura(formatoDataUSA.format(formatoDataUSA.parse(dth_ult_cap)));
            novaConfig.setHr_ult_captura(formatoHoraBr.format(formatoCompletoUSA.parse(dth_ult_cap)));
        } catch (ParseException ex) {
            Logger.getLogger(ControleMetodosConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try{
            acessoBD.atualizarDAO(novaConfig);
            novaConfig = acessoBD.carregarDAO(idConfig);
            HttpSession sessao = request.getSession();
            sessao.setAttribute("configVigente", novaConfig);
            request.setAttribute("mensagem", "Configuração atualizada com sucesso!");
            request.getRequestDispatcher("principal?d=forms&a=load&f=config").forward(request, response);//retorna para a principal apresentando o erro
        } catch (ServletException | IOException ex){
            request.setAttribute("mensagem", "Atualização não realizada.");
            request.getRequestDispatcher("principal?d=forms&a=load&f=config").forward(request, response);//retorna para a principal apresentando o erro
        }
    }

    @Override
    public void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Configuracao cfg = acessoBD.carregarDAO(idConfig);
        if(cfg != null){
            HttpSession sessao = request.getSession();
            sessao.setAttribute("conf", cfg);
        }
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editarSalvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
