package br.edu.cio.controller;

import br.edu.cio.model.Animal;
import br.edu.cio.model.AnimalInfo;
import br.edu.cio.model.AtividadeAnimal;
import br.edu.cio.model.Cio;
import br.edu.cio.model.Configuracao;
import br.edu.cio.model.Leitura;
import br.edu.cio.model.LogCio;
import br.edu.cio.model.dao.AnimalDAO;
import br.edu.cio.model.dao.AnimalInfoDAO;
import br.edu.cio.model.dao.CioDAO;
import br.edu.cio.model.dao.LeituraDAO;
import br.edu.cio.model.dao.MetodosInferenciaCioDAO;
import br.edu.cio.util.DAOFactory;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

public class ControleMetodosCio implements ControleMetodosComunsServicos {
    CioDAO acessoBDCio = DAOFactory.gerarCioDAO();
    AnimalDAO acessoBDAnimal = DAOFactory.gerarAnimalDAO();
    AnimalInfoDAO acessoBDAnimalInfo = DAOFactory.gerarAnimalInfoDAO();
    MetodosInferenciaCioDAO acessoBDMIC = DAOFactory.gerarMetodosInferenciaCioDAO();
    LeituraDAO acessoBDLeitura = DAOFactory.gerarLeituraDAO();
    SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatoDataBr = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatoHoraBr = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat formatoCompletoUSA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat formatoCompletoBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    @Override
    public void carregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição
        Cio cio = acessoBDCio.buscarPorIdDAO(id);
        request.setAttribute("cicloEstral", cio);
        request.getRequestDispatcher("principal?d=forms&a=avaliarEditar&f=cio").forward(request, response);//despacha para a proxima página 
    }

    @Override
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição
       Cio cio = acessoBDCio.buscarPorIdDAO(id);
       request.setAttribute("cicloEstral", cio);
       request.getRequestDispatcher("principal?d=forms&a=editar&f=cio").forward(request, response);//despacha para a proxima página 
    }

    @Override
    public void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String valorBusca = request.getParameter("cmp_cio_busca");
        List<Cio> ciclos = acessoBDCio.buscarDAO(valorBusca);
        if(ciclos != null){
            HttpSession sessao = request.getSession();
            request.setAttribute("ciclosBusca", ciclos);
            request.getRequestDispatcher("principal?d=forms&a=resultBusca&f=cio").forward(request, response);//despacha para a proxima página
        } else {
            request.setAttribute("mensagem", "Não existem ciclos estrais cadastrados com o código informado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=cio").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    @Override
    public void listarPendentes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cio> ciclos = acessoBDCio.listarPendentesDAO();
        if(ciclos != null){
            HttpSession sessao = request.getSession();
            sessao.setAttribute("ciclosPendentes", ciclos);
            request.getRequestDispatcher("principal?d=forms&a=avaliar&f=cio").forward(request, response);//despacha para a proxima página
        } else {
            request.setAttribute("mensagem", "Não existem ciclos com avaliação pendente.");
            request.getRequestDispatcher("principal?d=&f=home").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    @Override
    public void avaliarSalvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        Configuracao config = (Configuracao) sessao.getAttribute("conf");//recupera configuração atual da sessão
        Cio c = new Cio();
        c.setId(Integer.parseInt(request.getParameter("id")));
        c.setStatus(request.getParameter("cmp_cio_status"));
        c.setObservacao(request.getParameter("cmp_cio_observacao"));
        String inicio_cio = "";
        String termino_cio = "";
        int i=0;
        try {
            c.setData_ultima_alteracao(formatoDataBr.format(formatoDataBr.parse(config.getDt_ult_captura())));
            c.setHora_ultima_alteracao(config.getHr_ult_captura());
        } catch (ParseException ex) {   Logger.getLogger(ControleMetodosCio.class.getName()).log(Level.SEVERE, null, ex);   }
        switch(c.getStatus()){
            case "CONFIRMADO":{
                c.setData_inicio(null);
                c.setHora_inicio(null);
                if(request.getParameter("cmp_cio_dthInicio") != null && !"".equals(request.getParameter("cmp_cio_dthInicio"))){
                    while(i<request.getParameter("cmp_cio_dthInicio").length()){
                        if(request.getParameter("cmp_cio_dthInicio").charAt(i) == 'T')
                            inicio_cio += " ";
                        else
                            inicio_cio +=request.getParameter("cmp_cio_dthInicio").charAt(i);
                        i++;
                    }
                    inicio_cio += ":00";
                    try {
                        c.setData_inicio(formatoDataBr.format(formatoCompletoUSA.parse(inicio_cio)));
                        c.setHora_inicio(formatoHoraBr.format(formatoCompletoUSA.parse(inicio_cio)));
                    } catch (ParseException ex) {   Logger.getLogger(ControleMetodosCio.class.getName()).log(Level.SEVERE, null, ex);   }
                }
                break;
            }
            case "ENCERRADO":{
                c.setData_inicio(null);
                c.setHora_inicio(null);
                if(request.getParameter("cmp_cio_dthInicio") != null && !"".equals(request.getParameter("cmp_cio_dthInicio"))){
                    while(i<request.getParameter("cmp_cio_dthInicio").length()){
                        if(request.getParameter("cmp_cio_dthInicio").charAt(i) == 'T')
                            inicio_cio += " ";
                        else
                            inicio_cio +=request.getParameter("cmp_cio_dthInicio").charAt(i);
                        i++;
                    }
                    inicio_cio += ":00";
                    try {
                        c.setData_inicio(formatoDataBr.format(formatoCompletoUSA.parse(inicio_cio)));
                        c.setHora_inicio(formatoHoraBr.format(formatoCompletoUSA.parse(inicio_cio)));
                    } catch (ParseException ex) {   Logger.getLogger(ControleMetodosCio.class.getName()).log(Level.SEVERE, null, ex);   }
                }
                c.setData_termino(null);
                c.setHora_termino(null);
                if(request.getParameter("cmp_cio_dthTermino") != null && !"".equals(request.getParameter("cmp_cio_dthTermino"))){
                    i = 0;
                    while(i<request.getParameter("cmp_cio_dthTermino").length()){
                        if(request.getParameter("cmp_cio_dthTermino").charAt(i) == 'T')
                            termino_cio += " ";
                        else
                            termino_cio +=request.getParameter("cmp_cio_dthTermino").charAt(i);
                        i++;
                    }
                    termino_cio += ":00";
                    try {
                        c.setData_termino(formatoDataBr.format(formatoCompletoUSA.parse(termino_cio)));
                        c.setHora_termino(formatoHoraBr.format(formatoCompletoUSA.parse(termino_cio)));
                    } catch (ParseException ex) {   Logger.getLogger(ControleMetodosCio.class.getName()).log(Level.SEVERE, null, ex);   }
                }
                break;
            }
            default:
                break;
        }
        try {
            acessoBDCio.avaliarDAO(c);
            LogCio lc = ControleMetodosBCC.instanciaLogCio(c.getData_ultima_alteracao(), c.getHora_ultima_alteracao(), c, null, "Avaliação de cio: "+c.getStatus(), c.getStatus());
            acessoBDCio.inserirLogDAO(lc);
            request.setAttribute("mensagem", "Avaliação de Ciclo Estral Registrada com Sucesso");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=cio").forward(request, response);//retorna para a index apresentando o erro
        } catch (ServletException | IOException ex) {
            request.setAttribute("mensagem", " ERRO: Não foi possível salvar a Avaliação do Ciclo Estral.");
            request.getRequestDispatcher("principal?d=forms&a=avaliar&f=cio").forward(request, response);//retorna para a index apresentando o erro
            Logger.getLogger(ControleMetodosCio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            HttpSession sessao = request.getSession();
            Cio c = new Cio();
            
            GregorianCalendar dthAtual = new GregorianCalendar();
            GregorianCalendar dthInicioCio = new GregorianCalendar();
            GregorianCalendar dthTerminoCio = new GregorianCalendar();
            
            c.setData_inicio(null);
            c.setHora_inicio(null);
            c.setData_previsao_termino(null);
            c.setHora_previsao_termino(null);
            c.setData_termino(null);
            c.setHora_termino(null);
            c.setDuracao(0);

            Configuracao config = (Configuracao) sessao.getAttribute("conf");//recupera configuração atual da sessão
            c.setData_ultima_alteracao(formatoDataBr.format(formatoDataBr.parse(config.getDt_ult_captura())));
            c.setHora_ultima_alteracao(config.getHr_ult_captura());
            c.setData_registro(formatoDataBr.format(formatoDataBr.parse(config.getDt_ult_captura())));
            c.setHora_registro(config.getHr_ult_captura());
            c.setMetodo_id(request.getParameter("cmp_cio_mtd_id"));
            c.setMetodo_registro("MANUAL");
            c.setObservacao(request.getParameter("cmp_cio_observacao"));
            String estado = request.getParameter("cmp_cio_status");
            c.setStatus(estado);
            int duracao_cio;
            long dif_ms;
            int i=0;
            if(estado.equals("CONFIRMADO") || estado.equals("ENCERRADO")){
                String inicio_cio = "";
                if(request.getParameter("cmp_cio_dthInicio") != null && !"".equals(request.getParameter("cmp_cio_dthInicio"))){
                    while(i<request.getParameter("cmp_cio_dthInicio").length()){
                        if(request.getParameter("cmp_cio_dthInicio").charAt(i) == 'T')
                            inicio_cio += " ";
                        else
                            inicio_cio +=request.getParameter("cmp_cio_dthInicio").charAt(i);
                        i++;
                    }
                    inicio_cio += ":00";
                    c.setData_inicio(formatoDataBr.format(formatoCompletoUSA.parse(inicio_cio)));
                    c.setHora_inicio(formatoHoraBr.format(formatoCompletoUSA.parse(inicio_cio)));
                }

                AnimalInfo aninfoCio = acessoBDAnimalInfo.buscarPorIdAnimalDAO(Integer.parseInt(request.getParameter("cmp_cio_animal")));
                int duracao_media_cio_animal = aninfoCio.getDuracao_media_cio();
                
                GregorianCalendar dthPrevTerminoCio = new GregorianCalendar();
                dthPrevTerminoCio.setTime(formatoCompletoBr.parse(c.getData_inicio()+ " " + c.getHora_inicio()));
                dthPrevTerminoCio.add(Calendar.HOUR, duracao_media_cio_animal);
                formatoDataBr.setCalendar(dthPrevTerminoCio);
                c.setData_previsao_termino(formatoDataBr.format(dthPrevTerminoCio.getTime()));
                c.setHora_previsao_termino(formatoHoraBr.format(dthPrevTerminoCio.getTime()));
                
                dthInicioCio.setTime(formatoCompletoBr.parse(c.getData_inicio()+ " " + c.getHora_inicio()));
                dthAtual.setTime(formatoCompletoBr.parse(formatoDataBr.format(formatoDataBr.parse(config.getDt_ult_captura()))+ " " + config.getHr_ult_captura()));
                dif_ms = dthAtual.getTimeInMillis() - dthInicioCio.getTimeInMillis();
                duracao_cio = (int) (dif_ms/(60*60*1000));
                c.setDuracao(duracao_cio);
                
            }
            if(estado.equals("ENCERRADO")){
                i=0;
                String termino_cio = "";
                if(request.getParameter("cmp_cio_dthTermino") != null && !"".equals(request.getParameter("cmp_cio_dthTermino"))){
                    while(i<request.getParameter("cmp_cio_dthTermino").length()){
                        if(request.getParameter("cmp_cio_dthTermino").charAt(i) == 'T')
                            termino_cio += " ";
                        else
                            termino_cio +=request.getParameter("cmp_cio_dthTermino").charAt(i);
                        i++;
                    }
                    termino_cio += ":00";
                    c.setData_termino(formatoDataBr.format(formatoCompletoUSA.parse(termino_cio)));
                    c.setHora_termino(formatoHoraBr.format(formatoCompletoUSA.parse(termino_cio)));
                }
                
                dthInicioCio.setTime(formatoCompletoBr.parse(c.getData_inicio()+ " " + c.getHora_inicio()));
                dthTerminoCio.setTime(formatoCompletoBr.parse(c.getData_termino()+ " " + c.getHora_termino()));
                dif_ms = dthTerminoCio.getTimeInMillis() - dthInicioCio.getTimeInMillis();
                duracao_cio = (int) (dif_ms/(60*60*1000));
                c.setDuracao(duracao_cio);
                
            }

            Animal anicio = acessoBDAnimal.buscarPorIdDAO(Integer.parseInt(request.getParameter("cmp_cio_animal")));
            c.setAnimal(anicio);
            
            c.setLeitura(null);
            c.setAtividadeAnimal(null);
            
            Random r1 = new Random();
            Random r2 = new Random();
            int bloco1 = r1.nextInt((999 - 0)) + 0;
            int bloco2 = r1.nextInt((99 - 0)) + 0;
            String codigo_cio = "1" + String.valueOf(bloco1) + String.valueOf(anicio.getId()) + String.valueOf(bloco2); //Inicia com: 9 - Cadastro Automático, 1 - Cadastro Manual
            c.setCodigo(codigo_cio);
            
            if(acessoBDCio.buscarPendentesAnimalDAO(anicio.getId()) == null){
                acessoBDCio.cadastrarDAO(c);
                c = acessoBDCio.buscarUltimoRegistroDAO();
                LogCio lc = ControleMetodosBCC.instanciaLogCio(c.getData_ultima_alteracao(), c.getHora_ultima_alteracao(), c, null, "Cadastro Manual de Cio "+c.getStatus(), c.getStatus());
                acessoBDCio.inserirLogDAO(lc);
                request.setAttribute("mensagem", "Ciclo Estral Registrado com Sucesso");
                request.getRequestDispatcher("principal?d=forms&a=buscar&f=cio").forward(request, response);//retorna para a index apresentando o erro
            } else {
                request.setAttribute("mensagem", " ERRO: Não foi possível cadastrar o Ciclo Estral. O animal já possui um ciclo recente com avaliação pendente.");
                request.getRequestDispatcher("principal?d=forms&a=cadastrar&f=cio").forward(request, response);//retorna para a index apresentando o erro
            }
        } catch (ParseException ex){
            request.setAttribute("mensagem", " ERRO: Não foi possível cadastrar Ciclo Estral.");
            request.getRequestDispatcher("principal?d=forms&a=cadastrar&f=cio").forward(request, response);//retorna para a index apresentando o erro
            Logger.getLogger(ControleMetodosCio.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }

    @Override
    public void filtrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String valorFiltro = request.getParameter("cmp_cio_filtro");
        String tipoFiltro = request.getParameter("cmp_cio_filtrar");
        Cio c = new Cio();
        String data = "";
        int i = 0;
        HttpSession sessao = request.getSession();
        List<Cio> ciclos = (List<Cio>) sessao.getAttribute("ciclosPendentes");
        switch(tipoFiltro){
            case "DTREGISTRO":{
                try {
                    if(valorFiltro != null && !"".equals(valorFiltro)){
                        while(i<valorFiltro.length()){
                            if(valorFiltro.charAt(i) == 'T')
                                data += " ";
                            else
                                data +=valorFiltro.charAt(i);
                            i++;
                        }
                        data += ":00";
                        c.setData_termino(formatoDataBr.format(formatoCompletoUSA.parse(data)));
                        c.setHora_termino(formatoHoraBr.format(formatoCompletoUSA.parse(data)));
                    }

                    for(Cio cio : ciclos){
                        //Date dataRegistro = formatoCompletoUSA.parse(data);
                        //String dataRegistro = formatoCompletoBr.format(data);
                        //Date dataRegLista = formatoCompletoUSA.parse();
                        String dataRegLista = formatoCompletoUSA.format(formatoCompletoBr.parse(cio.getData_registro()+" "+cio.getHora_registro()));
                        int t = dataRegLista.compareTo(data);//se a data do cio (na lista) for maior que a data informada no filtro dá um valor positivo
                        if(t >= 0){
                            
                        }
                    }


                    if(ciclos != null){
                        request.setAttribute("ciclosPendentes", ciclos);
                        request.getRequestDispatcher("principal?d=forms&a=avaliar&f=cio").forward(request, response);//despacha para a proxima página
                    } else {
                        request.setAttribute("mensagem", "Não existem ciclos com avaliação pendente.");
                        request.getRequestDispatcher("principal?d=&f=home").forward(request, response);//retorna para a index apresentando o erro
                    }
                } catch (ParseException ex) {   Logger.getLogger(ControleMetodosCio.class.getName()).log(Level.SEVERE, null, ex);   }  
                break;
            } case "DTALTERACAO":{
                
            } case "ANIMAL":{
                
            } case "STATUS":{
                
            } case "CODCIO":{
                
            }
        }
    }

    @Override
    public void detalhar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cio resultado = acessoBDCio.buscarPorIdDAO(Integer.parseInt(request.getParameter("id")));
        if (resultado != null) {
            request.setAttribute("cicloDetalhes", resultado);
            request.getRequestDispatcher("principal?d=forms&a=detalhar&f=cio").forward(request, response);//despacha para a proxima página 
        } else {
            request.setAttribute("mensagem", "Ciclo Estral não cadastrado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=cio").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    @Override
    public void editarSalvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        Configuracao config = (Configuracao) sessao.getAttribute("conf");//recupera configuração atual da sessão
        Cio c = new Cio();
        c.setId(Integer.parseInt(request.getParameter("id")));
        c.setStatus(request.getParameter("cmp_cio_status"));
        c.setObservacao(request.getParameter("cmp_cio_observacao"));
        String inicio_cio = "";
        String termino_cio = "";
        int i=0;
        try {
            c.setData_ultima_alteracao(formatoDataBr.format(formatoDataBr.parse(config.getDt_ult_captura())));
            c.setHora_ultima_alteracao(config.getHr_ult_captura());
        } catch (ParseException ex) {   Logger.getLogger(ControleMetodosCio.class.getName()).log(Level.SEVERE, null, ex);   }
        switch(c.getStatus()){
            case "CONFIRMADO":{
                c.setData_inicio(null);
                c.setHora_inicio(null);
                if(request.getParameter("cmp_cio_dthInicio") != null && !"".equals(request.getParameter("cmp_cio_dthInicio"))){
                    while(i<request.getParameter("cmp_cio_dthInicio").length()){
                        if(request.getParameter("cmp_cio_dthInicio").charAt(i) == 'T')
                            inicio_cio += " ";
                        else
                            inicio_cio +=request.getParameter("cmp_cio_dthInicio").charAt(i);
                        i++;
                    }
                    inicio_cio += ":00";
                    try {
                        c.setData_inicio(formatoDataBr.format(formatoCompletoUSA.parse(inicio_cio)));
                        c.setHora_inicio(formatoHoraBr.format(formatoCompletoUSA.parse(inicio_cio)));
                    } catch (ParseException ex) {   Logger.getLogger(ControleMetodosCio.class.getName()).log(Level.SEVERE, null, ex);   }
                }
                break;
            }
            case "ENCERRADO":{
                c.setData_inicio(null);
                c.setHora_inicio(null);
                if(request.getParameter("cmp_cio_dthInicio") != null && !"".equals(request.getParameter("cmp_cio_dthInicio"))){
                    while(i<request.getParameter("cmp_cio_dthInicio").length()){
                        if(request.getParameter("cmp_cio_dthInicio").charAt(i) == 'T')
                            inicio_cio += " ";
                        else
                            inicio_cio +=request.getParameter("cmp_cio_dthInicio").charAt(i);
                        i++;
                    }
                    inicio_cio += ":00";
                    try {
                        c.setData_inicio(formatoDataBr.format(formatoCompletoUSA.parse(inicio_cio)));
                        c.setHora_inicio(formatoHoraBr.format(formatoCompletoUSA.parse(inicio_cio)));
                    } catch (ParseException ex) {   Logger.getLogger(ControleMetodosCio.class.getName()).log(Level.SEVERE, null, ex);   }
                }
                c.setData_termino(null);
                c.setHora_termino(null);
                if(request.getParameter("cmp_cio_dthTermino") != null && !"".equals(request.getParameter("cmp_cio_dthTermino"))){
                    i = 0;
                    while(i<request.getParameter("cmp_cio_dthTermino").length()){
                        if(request.getParameter("cmp_cio_dthTermino").charAt(i) == 'T')
                            termino_cio += " ";
                        else
                            termino_cio +=request.getParameter("cmp_cio_dthTermino").charAt(i);
                        i++;
                    }
                    termino_cio += ":00";
                    try {
                        c.setData_termino(formatoDataBr.format(formatoCompletoUSA.parse(termino_cio)));
                        c.setHora_termino(formatoHoraBr.format(formatoCompletoUSA.parse(termino_cio)));
                    } catch (ParseException ex) {   Logger.getLogger(ControleMetodosCio.class.getName()).log(Level.SEVERE, null, ex);   }
                }
                break;
            }
            default:
                break;
        }
        try {
            acessoBDCio.editarManualDAO(c);
            LogCio lc = ControleMetodosBCC.instanciaLogCio(c.getData_ultima_alteracao(), c.getHora_ultima_alteracao(), c, null, "Alteração de cio: "+c.getStatus(), c.getStatus());
            acessoBDCio.inserirLogDAO(lc);
            request.setAttribute("mensagem", "Alteração Registrada com Sucesso");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=cio").forward(request, response);//retorna para a index apresentando o erro
        } catch (ServletException | IOException ex) {
            request.setAttribute("mensagem", " ERRO: Não foi possível salvar a Avaliação do Ciclo Estral.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=cio").forward(request, response);//retorna para a index apresentando o erro
            Logger.getLogger(ControleMetodosCio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
/*
try {
    c.setDuracao(0);
    if(request.getParameter("cmp_cio_duracao") != null){
        c.setDuracao(Integer.parseInt(request.getParameter("cmp_cio_duracao")));
    }
    c.setStatus(request.getParameter("cmp_cio_status"));
    c.setId(id);
    c.setMetodo_id(request.getParameter("cmp_cio_mtd_id"));
    c.setMetodo_registro(request.getParameter("cmp_cio_mtd_registro"));
    c.setObservacao(request.getParameter("cmp_cio_mtd_registro"));
    //acessoBDCio.avaliarDAO(c);
    //request.setAttribute("mensagem", "Avaliação de Ciclo Estral Registrada com Sucesso");
    //request.getRequestDispatcher("principal?d=forms&a=buscar&f=cio").forward(request, response);//retorna para a index apresentando o erro
} catch (ParseException /*| ServletException | IOException ex) {
    request.setAttribute("mensagem", "Animal  não atualizado.");
    request.getRequestDispatcher("principal?d=forms&a=avaliar&f=cio").forward(request, response);//retorna para a index apresentando o erro
    Logger.getLogger(ControleMetodosCio.class.getName()).log(Level.SEVERE, null, ex);
}
*/