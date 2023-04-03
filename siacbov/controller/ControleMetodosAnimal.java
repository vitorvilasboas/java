package br.edu.cio.controller;

import br.edu.cio.controller.ControleMetodosComuns;
import br.edu.cio.model.Animal;
import br.edu.cio.model.AnimalInfo;
import br.edu.cio.model.AreaPastagem;
import br.edu.cio.model.Configuracao;
import br.edu.cio.model.Lote;
import br.edu.cio.model.Propriedade;
import br.edu.cio.model.Sensor;
import br.edu.cio.model.dao.AnimalDAO;
import br.edu.cio.model.dao.AnimalInfoDAO;
import br.edu.cio.model.dao.AreaPastagemDAO;
import br.edu.cio.model.dao.ConfiguracaoDAO;
import br.edu.cio.model.dao.LoteDAO;
import br.edu.cio.model.dao.PropriedadeDAO;
import br.edu.cio.model.dao.SensorDAO;
import br.edu.cio.util.DAOFactory;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.swing.JOptionPane;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

public class ControleMetodosAnimal implements ControleMetodosComuns {

    AnimalDAO acessoBDAnimal = DAOFactory.gerarAnimalDAO();

    PropriedadeDAO acessoBDPropriedade = DAOFactory.gerarPropriedadeDAO();
    LoteDAO acessoBDLote = DAOFactory.gerarLoteDAO();
    SensorDAO acessoBDSensor = DAOFactory.gerarSensorDAO();
    AreaPastagemDAO acessoBDPasto = DAOFactory.gerarAreaPastagemDAO();
    AnimalInfoDAO acessoBDAnimalInfo = DAOFactory.gerarAnimalInfoDAO();
    ConfiguracaoDAO acessoBDConfig = DAOFactory.gerarConfiguracaoDAO();
    
    
    @Override
    public void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoDataBr = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHoraBr = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatoCompletoUSA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatoCompletoBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Animal vaca = new Animal();
        AnimalInfo vacaInfo = new AnimalInfo();
        //vaca.setCodigo("");
        vaca.setApelido(request.getParameter("cmp_anim_apelido"));
        vaca.setDt_nascimento(request.getParameter("cmp_anim_dtNascimento"));
        vaca.setRaca(request.getParameter("cmp_anim_raca"));
        vaca.setSexo(request.getParameter("cmp_anim_sexo"));
        vaca.setObservacao(request.getParameter("cmp_anim_observacao"));
        vaca.setRgn(Integer.parseInt(request.getParameter("cmp_anim_rgn")));
        vaca.setGrau_sangue(request.getParameter("cmp_anim_Gsangue"));
        vaca.setTipo(request.getParameter("cmp_anim_tipo"));
        vaca.setPelagem(request.getParameter("cmp_anim_pelagem"));
        vaca.setTipo_geracao(request.getParameter("cmp_anim_tipo_geracao"));
        vaca.setImagem(request.getParameter("cmp_anim_imagem"));
        //vaca.setDt_cadastro(request.getParameter("cmp_anim_dt_cadastro"));
        vaca.setEstado_atual(request.getParameter("cmp_anim_estado_atual"));
        if(!"".equals(request.getParameter("cmp_anim_peso_atual")))
            vaca.setPeso_atual(Double.parseDouble(request.getParameter("cmp_anim_peso_atual")));
        if(!"".equals(request.getParameter("cmp_anim_peso_nascimento")))
            vaca.setPeso_nascimento(Double.parseDouble(request.getParameter("cmp_anim_peso_nascimento")));
        if(!"".equals(request.getParameter("cmp_anim_preco_compra")))
            vaca.setPreco_compra(Double.parseDouble(request.getParameter("cmp_anim_preco_compra")));
        if(!"".equals(request.getParameter("cmp_anim_monitoramento")))
            vaca.setMonitorando(Integer.parseInt(request.getParameter("cmp_anim_monitoramento")));

        Propriedade p = acessoBDPropriedade.buscarPorIdDAO(Integer.parseInt(request.getParameter("cmp_anim_propriedade")));
        vaca.setPropriedade(p);

        Lote l = acessoBDLote.buscarPorIdDAO(Integer.parseInt(request.getParameter("cmp_anim_lote")));
        vaca.setLote(l);

        AreaPastagem pa = acessoBDPasto.buscarPorIdDAO(Integer.parseInt(request.getParameter("cmp_anim_pasto")));
        vaca.setPasto(pa);
        Sensor s = null;
        String sensorForm = request.getParameter("cmp_anim_sensor");
        //JOptionPane.showMessageDialog(null, sensorForm);
        if(!"".equals(sensorForm) && sensorForm != null){
            s = acessoBDSensor.buscarPorIdDAO(Integer.parseInt(request.getParameter("cmp_anim_sensor")));
            vaca.setSensor(s);
        } else 
            vaca.setSensor(null);
        
       
        
        //vacaInfo.setDt_ini_ult_cio(request.getParameter("cmp_anim_dt_ini_ult_cio"));
        vacaInfo.setIntervalo_padrao_anestro(Integer.parseInt(request.getParameter("cmp_anim_intervalo_anestro")));
        vacaInfo.setDuracao_media_cio(Integer.parseInt(request.getParameter("cmp_anim_duracao_cio")));
        switch(request.getParameter("cmp_anim_perfil_atividade")){
            case "MUITO CALMO": {
                vacaInfo.setMedia_passos_hora(30);
                break;
            }
            case "CALMO": {
                vacaInfo.setMedia_passos_hora(40);
                break;
            }
            case "NORMAL": {
                vacaInfo.setMedia_passos_hora(60);
                break;
            }
            case "AGITADO": {
                vacaInfo.setMedia_passos_hora(80);
                break;
            }
            case "MUITO AGITADO": {
                vacaInfo.setMedia_passos_hora(110);
                break;
            }
            default: {
                vacaInfo.setMedia_passos_hora(0);
                break;
            }      
        }
        /* CALCULANDO A IDADE DO ANIMAL A PARTIR DA DATA DE NASCIMENTO E A DATA ATUAL*/
        GregorianCalendar dataNascimento = new GregorianCalendar();
        GregorianCalendar dataAtual = new GregorianCalendar();
        Date agora = new Date();//capturando data e hora atual do sistema
        //dataAtual = null;
        Configuracao config = acessoBDConfig.carregarDAO(1);
        try {
            //JOptionPane.showMessageDialog(null, formatoCompletoUSA.parse(formatoCompletoUSA.format(formatoCompletoBr.parse(config.getDt_ult_captura() +" "+config.getHr_ult_captura()))));
            dataAtual.setTime(formatoCompletoUSA.parse(formatoCompletoUSA.format(formatoCompletoBr.parse(config.getDt_ult_captura() +" "+config.getHr_ult_captura()))));
        } catch (ParseException ex) {
            Logger.getLogger(ControleMetodosAnimal.class.getName()).log(Level.SEVERE, null, ex);
        }
        int idade = -1;
        try {
            String dtn1 = request.getParameter("cmp_anim_dtNascimento");// data em Texto conforme padrão datetime-local //System.out.println("DTN 1 - Texto Request: "+dtn1);
            /*Date dtn2 = formatoDataUSA.parse(dtn1); //convertendo String para o padrão de datas americano(SQL) yyyy-MM-dd //System.out.println("DTN 2 - Date USA/SQL: "+dtn2);
            String dtn3 = formatoDataBr.format(dtn2); //formatando um tipo date em padrão americano(SQL) yyy-MM-dd para o padrão brasileiro dd-MM-yyyy, e casting para String //System.out.println("DTN 3 - Texto Br: "+dtn3);
            Date dtn4 = formatoDataBr.parse(dtn3); //convertendo uma String ja formatada para um tipo Date //System.out.println("DTN 4 - Date Br: "+dtn4+"\n\n");*/
            dataNascimento.setTime(formatoDataUSA.parse(dtn1));
            long milissegundos = dataAtual.getTimeInMillis() - dataNascimento.getTimeInMillis();
            idade = (int) ((milissegundos/(24*60*60*1000))/365);
        } catch (ParseException ex) {
            Logger.getLogger(ControleMetodosAnimal.class.getName()).log(Level.SEVERE, null, ex);
        }
        vacaInfo.setIdade(idade);
        /* ^^^^^^ */
        int diasAnestroAtual = 0;
        GregorianCalendar dataUltCio = new GregorianCalendar();
        GregorianCalendar dataPrevIniProxCio = new GregorianCalendar();
        GregorianCalendar horaPrevIniProxCio = new GregorianCalendar();
        //Configuracao config = (Configuracao) sessao.getAttribute("conf");//recupera configuração atual da sessão
        //String duracaoPadraoCio = String.valueOf(config.getDuracao_cio()) + ":00:00";
        String StrDataHoraUltCio = "";
        String StrDataUltCioFormatBr = "";
        String StrHoraUltCioFormat = "";
        try {
            /* INFERINDO A QTD DE DIAS EM ANESTRO A PARTIR DA DATA DO ULTIMO CIO E A DATA ATUAL */
            int i=0;
            while(i<request.getParameter("cmp_anim_dt_fim_ult_cio").length()){
                if(request.getParameter("cmp_anim_dt_fim_ult_cio").charAt(i) == 'T')
                    StrDataHoraUltCio += " ";
                else
                    StrDataHoraUltCio +=request.getParameter("cmp_anim_dt_fim_ult_cio").charAt(i);
                i++;
            }
            StrDataHoraUltCio += ":00";
            dataUltCio.setTime((formatoDataUSA.parse(StrDataHoraUltCio)));
            long milissegundos = dataAtual.getTimeInMillis() - dataUltCio.getTimeInMillis();
            diasAnestroAtual = (int) (milissegundos/(24*60*60*1000));
            if(diasAnestroAtual == 0)
                /*
                vacaInfo.setTempo_atual_anestro("PRINCIPIO");
            if(diasAnestroAtual > 0 && diasAnestroAtual <= 15)
                vacaInfo.setTempo_atual_anestro("CURTO");
            if(diasAnestroAtual > 15 && diasAnestroAtual <= 24)
                vacaInfo.setTempo_atual_anestro("NORMAL");
            if(diasAnestroAtual > 24 && diasAnestroAtual <= 30)
                vacaInfo.setTempo_atual_anestro("LONGO");
            if(diasAnestroAtual > 30)
                vacaInfo.setTempo_atual_anestro("MUITO LONGO");
            */
            if(diasAnestroAtual == 0)
                vacaInfo.setTempo_atual_anestro("PRINCIPIO");
            if(diasAnestroAtual > 0 && diasAnestroAtual < config.getMin_anestro_normal())
                vacaInfo.setTempo_atual_anestro("CURTO");
            if(diasAnestroAtual >= config.getMin_anestro_normal() && diasAnestroAtual < config.getMin_anestro_longo())
                vacaInfo.setTempo_atual_anestro("NORMAL");
            if(diasAnestroAtual >= config.getMin_anestro_longo() && diasAnestroAtual < config.getMin_anestro_mlongo())
                vacaInfo.setTempo_atual_anestro("LONGO"); 
            if(diasAnestroAtual >= config.getMin_anestro_mlongo())
                vacaInfo.setTempo_atual_anestro("MUITO LONGO");
            /* ^^^^^^ */
            /* CALCULANDO DATA E HORA DE PREVISÃO DO PRÓXIMO CIO A PARTIR DA SOMA DO INTERVALO PADRÃO DE ANESTRO E A DATA DO ULTIMO CIO*/
            dataPrevIniProxCio.setTime(formatoDataUSA.parse(StrDataHoraUltCio));
            dataPrevIniProxCio.add(Calendar.HOUR, 24*vacaInfo.getIntervalo_padrao_anestro());/* SOMANDO */
            formatoDataBr.setCalendar(dataPrevIniProxCio);
            horaPrevIniProxCio.setTime(formatoCompletoUSA.parse(StrDataHoraUltCio)); 
            formatoHoraBr.setCalendar(horaPrevIniProxCio);
            /* ^^^^^^ */
            StrDataUltCioFormatBr = formatoDataUSA.format(formatoDataUSA.parse(StrDataHoraUltCio));
            StrHoraUltCioFormat = formatoHoraBr.format(formatoCompletoUSA.parse(StrDataHoraUltCio));
        } catch (ParseException ex) {
            Logger.getLogger(ControleMetodosAnimal.class.getName()).log(Level.SEVERE, null, ex);
        }
        vacaInfo.setDt_fim_ult_cio(StrDataUltCioFormatBr);
        vacaInfo.setHr_fim_ult_cio(StrHoraUltCioFormat);
        vacaInfo.setDias_em_anestro(diasAnestroAtual);
        vacaInfo.setDt_prev_ini_prox_cio(formatoDataUSA.format(dataPrevIniProxCio.getTime()));
        vacaInfo.setHr_prev_ini_prox_cio(formatoHoraBr.format(horaPrevIniProxCio.getTime()));
        vacaInfo.setSob_alerta(0);
        vacaInfo.setStatus_atividade("MEDIA");
        vacaInfo.setOco_produtiva("NORMAL");
        vacaInfo.setOco_reprodutiva("NORMAL");
        vacaInfo.setStatus_cio(0);
        int aproximacaoCio = (int)((vacaInfo.getDias_em_anestro()*10)/vacaInfo.getIntervalo_padrao_anestro());
        if(aproximacaoCio <= 10 && aproximacaoCio >= 0){
            vacaInfo.setAprox_cio(aproximacaoCio);  
        } else {
            if(aproximacaoCio > 10)
               vacaInfo.setAprox_cio(10);  
            if(aproximacaoCio < 0)
                vacaInfo.setAprox_cio(0);
        }
        vacaInfo.setAnimal(null);
        //vaca.setSexo(request.getParameter("sexo_novoAnimal").charAt(0));//vaca.setPeso(Double.parseDouble(request.getParameter("peso_novoAnimal")));//vaca.setDt_nascimento(request.getParameter("dtNascimento_novoAnimal"));//DateFormat formataData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//vaca.setDt_cadastro(formataData.format(new Date()));//  Propriedade fazenda = new Propriedade(); 
        try {
            acessoBDAnimal.cadastrarDAO(vaca);
            acessoBDAnimalInfo.cadastrarDAO(vacaInfo);
            if(vaca.getSensor() != null){
                Animal arc = acessoBDAnimal.buscarUltimoRegistroDAO();
                acessoBDSensor.editarStatusDAO(1, s, arc);//mudando o status do sensor para ATIVO
            }
            /*
            System.out.println("\n\n\n***************************");
            System.out.println("***************************");
            System.out.println("***************************");
            System.out.println("Idade: "+ vacaInfo.getIdade());
            System.out.println("Intervalo padrão anestro: "+ vacaInfo.getIntervalo_padrao_anestro());
            System.out.println("Data Fim ultimo Cio: "+ vacaInfo.getDt_fim_ult_cio());
            System.out.println("Hora Fim ultimo Cio: "+ vacaInfo.getHr_fim_ult_cio());
            System.out.println("Dias em Anestro: "+ vacaInfo.getDias_em_anestro());
            System.out.println("Tempo Atual em Anestro: "+ vacaInfo.getTempo_atual_anestro());
            System.out.println("Data Prev Prox Cio: "+ vacaInfo.getDt_prev_ini_prox_cio());
            System.out.println("Hora Prev Prox Cio: "+ vacaInfo.getHr_prev_ini_prox_cio());
            System.out.println("Média de Passos por hora: "+ vacaInfo.getMedia_passos_hora());
            System.out.println("***************************");
            System.out.println("***************************");
            System.out.println("***************************\n\n\n");
            */
            request.setAttribute("mensagem", "Animal cadastrado com sucesso!");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=animal").forward(request, response);//retorna para a index apresentando o erro
        } catch (ServletException | IOException ex) {
            request.setAttribute("mensagem", "Animal não cadastrado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=animal").forward(request, response);//retorna para a index apresentando o erro
            Logger.getLogger(ControleMetodosAnimal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição
        //Animal vaca = acessoBDAnimal.buscarPorIdDAO(id);
        AnimalInfo vacaInfo = acessoBDAnimalInfo.buscarPorIdAnimalDAO(id);
        request.setAttribute("animalenc", vacaInfo);
        request.getRequestDispatcher("principal?d=forms&a=editar&f=animal").forward(request, response);//despacha para a proxima página 
    }

    @Override
    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição       
        if(!acessoBDAnimal.excluirDAO(id)) {
            request.setAttribute("mensagem", "Erro ao tentar excluir Animal.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=animal").forward(request, response);
        } else {
            request.setAttribute("mensagem", "Animal excluido com sucesso.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=animal").forward(request, response);
        }
    }

    @Override
    public void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String valorBusca = request.getParameter("cmp_bsc_animal");//recebe o dado informado no formLogin do index
        List<Animal> resultado = acessoBDAnimal.buscarDAO(valorBusca);
        List<AnimalInfo> aniMonInfo = new ArrayList<>();
        if (resultado != null) {
            for(Animal animal : resultado){
                aniMonInfo.add(acessoBDAnimalInfo.buscarPorIdAnimalDAO(animal.getId()));
            }
            request.setAttribute("listaanimaisinfo", aniMonInfo);
            request.getRequestDispatcher("principal?d=forms&a=resultBusca&f=animal").forward(request, response);//despacha para a proxima página 
        } else {
            request.setAttribute("mensagem", "Não existem animais cadastrados.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=animal").forward(request, response);//retorna para a index apresentando o erro
        }
    }


    @Override
    public void editarSalvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoDataBr = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHoraBr = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatoCompletoUSA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatoCompletoBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        List<Animal> animalDadosBD = acessoBDAnimal.buscarDAO(request.getParameter("cmp_anim_codigo"));
        Sensor sensorAnimalBD = null;
        if(animalDadosBD != null){
            for(Animal an : animalDadosBD){
                if(an.getMonitorando() == 1){
                    sensorAnimalBD = an.getSensor();
                    break;
                }
            }
        }       
        
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição
        Animal vaca = new Animal();
        AnimalInfo vacaInfo = new AnimalInfo();
        vaca.setId(id);
        vaca.setApelido(request.getParameter("cmp_anim_apelido"));
        vaca.setDt_nascimento(request.getParameter("cmp_anim_dtNascimento"));
        vaca.setRaca(request.getParameter("cmp_anim_raca"));
        vaca.setSexo(request.getParameter("cmp_anim_sexo"));
        vaca.setObservacao(request.getParameter("cmp_anim_observacao"));
        vaca.setRgn(Integer.parseInt(request.getParameter("cmp_anim_rgn")));
        vaca.setGrau_sangue(request.getParameter("cmp_anim_Gsangue"));
        vaca.setTipo(request.getParameter("cmp_anim_tipo"));
        vaca.setPelagem(request.getParameter("cmp_anim_pelagem"));
        vaca.setTipo_geracao(request.getParameter("cmp_anim_tipo_geracao"));
        vaca.setImagem(request.getParameter("cmp_anim_imagem"));
        vaca.setDt_cadastro(request.getParameter("cmp_anim_dt_cadastro"));
        vaca.setEstado_atual(request.getParameter("cmp_anim_estado_atual"));
        vaca.setPeso_atual(Double.parseDouble(request.getParameter("cmp_anim_peso_atual")));
        vaca.setPeso_nascimento(Double.parseDouble(request.getParameter("cmp_anim_peso_nascimento")));
        vaca.setPreco_compra(Double.parseDouble(request.getParameter("cmp_anim_preco_compra")));
        vaca.setMonitorando(Integer.parseInt(request.getParameter("cmp_anim_monitoramento")));

        Propriedade p = acessoBDPropriedade.buscarPorIdDAO(Integer.parseInt(request.getParameter("cmp_anim_propriedade")));
        vaca.setPropriedade(p);

        Lote l = acessoBDLote.buscarPorIdDAO(Integer.parseInt(request.getParameter("cmp_anim_lote")));
        vaca.setLote(l);

        AreaPastagem pa = acessoBDPasto.buscarPorIdDAO(Integer.parseInt(request.getParameter("cmp_anim_pasto")));
        vaca.setPasto(pa);
        Sensor s = null;
        if(!"".equals(request.getParameter("cmp_anim_sensor"))){
            s = acessoBDSensor.buscarPorIdDAO(Integer.parseInt(request.getParameter("cmp_anim_sensor")));
            vaca.setSensor(s);
        } else 
            vaca.setSensor(null);
        
        vacaInfo.setId(acessoBDAnimalInfo.buscarPorIdAnimalDAO(Integer.parseInt(request.getParameter("id"))).getId());//buscando o ID das infos a serem editadas
        vacaInfo.setDuracao_media_cio(Integer.parseInt(request.getParameter("cmp_anim_duracao_cio")));
        vacaInfo.setIntervalo_padrao_anestro(Integer.parseInt(request.getParameter("cmp_anim_intervalo_anestro")));
        vacaInfo.setSob_alerta(Integer.parseInt(request.getParameter("cmp_anim_sob_alerta")));
        vacaInfo.setDt_ini_ult_cio(null);//temp
        vacaInfo.setHr_ini_ult_cio(null);//temp
        vacaInfo.setDt_fim_ult_cio(null);//temp
        vacaInfo.setHr_fim_ult_cio(null);//temp
        vacaInfo.setDt_prev_ini_prox_cio(null);//temp
        vacaInfo.setHr_prev_ini_prox_cio(null);//temp
        switch(request.getParameter("cmp_anim_perfil_atividade")){
            case "MUITO CALMO": {
                vacaInfo.setMedia_passos_hora(30);
                break;
            } case "CALMO": {
                vacaInfo.setMedia_passos_hora(40);
                break;
            } case "NORMAL": {
                vacaInfo.setMedia_passos_hora(60);
                break;
            } case "AGITADO": {
                vacaInfo.setMedia_passos_hora(80);
                break;
            } case "MUITO AGITADO": {
                vacaInfo.setMedia_passos_hora(110);
                break;
            } default: {
                vacaInfo.setMedia_passos_hora(0);
                break;
            }      
        }
        
        GregorianCalendar dataAtual = new GregorianCalendar();
        Date agora = new Date();//capturando data e hora atual do sistema
        Configuracao config = acessoBDConfig.carregarDAO(1);
        try {
            dataAtual.setTime(formatoCompletoUSA.parse(formatoCompletoUSA.format(formatoCompletoBr.parse(config.getDt_ult_captura() +" "+config.getHr_ult_captura()))));
        } catch (ParseException ex) {
            Logger.getLogger(ControleMetodosAnimal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int diasAnestroAtual = 0;
        GregorianCalendar dataPrevIniProxCio = new GregorianCalendar();
        GregorianCalendar horaPrevIniProxCio = new GregorianCalendar();
        GregorianCalendar dataInicioUltCio = new GregorianCalendar();
        GregorianCalendar dataFimUltCio = new GregorianCalendar();
        String StrDataHoraInicioUltCio = "";
        String StrDataHoraFimUltCio = "";
        try {
            int i=0;
            while(i<request.getParameter("cmp_anim_dt_ini_ult_cio").length()){
                if(request.getParameter("cmp_anim_dt_ini_ult_cio").charAt(i) == 'T')
                    StrDataHoraInicioUltCio += " ";
                else
                    StrDataHoraInicioUltCio +=request.getParameter("cmp_anim_dt_ini_ult_cio").charAt(i);
                i++;
            }
            StrDataHoraInicioUltCio += ":00";
            dataInicioUltCio.setTime((formatoDataUSA.parse(StrDataHoraInicioUltCio)));
             
            i=0;
            while(i<request.getParameter("cmp_anim_dt_fim_ult_cio").length()){
                if(request.getParameter("cmp_anim_dt_fim_ult_cio").charAt(i) == 'T')
                    StrDataHoraFimUltCio += " ";
                else
                    StrDataHoraFimUltCio +=request.getParameter("cmp_anim_dt_fim_ult_cio").charAt(i);
                i++;
            }
            StrDataHoraFimUltCio += ":00";
            dataFimUltCio.setTime((formatoDataUSA.parse(StrDataHoraFimUltCio)));
            
            /* INFERINDO A QTD DE DIAS EM ANESTRO A PARTIR DA DATA DO ULTIMO CIO E A DATA ATUAL */
            long milissegundos = dataAtual.getTimeInMillis() - dataFimUltCio.getTimeInMillis();
            diasAnestroAtual = (int) (milissegundos/(24*60*60*1000));
            if(diasAnestroAtual == 0)
                vacaInfo.setTempo_atual_anestro("PRINCIPIO");
            if(diasAnestroAtual > 0 && diasAnestroAtual < config.getMin_anestro_normal())
                vacaInfo.setTempo_atual_anestro("CURTO");
            if(diasAnestroAtual >= config.getMin_anestro_normal() && diasAnestroAtual < config.getMin_anestro_longo())
                vacaInfo.setTempo_atual_anestro("NORMAL");
            if(diasAnestroAtual >= config.getMin_anestro_longo() && diasAnestroAtual < config.getMin_anestro_mlongo())
                vacaInfo.setTempo_atual_anestro("LONGO"); 
            if(diasAnestroAtual >= config.getMin_anestro_mlongo())
                vacaInfo.setTempo_atual_anestro("MUITO LONGO");
            /* CALCULANDO DATA E HORA DE PREVISÃO DO PRÓXIMO CIO A PARTIR DA SOMA DO INTERVALO PADRÃO DE ANESTRO E A DATA DO ULTIMO CIO*/
            dataPrevIniProxCio.setTime(formatoDataUSA.parse(StrDataHoraFimUltCio));
            dataPrevIniProxCio.add(Calendar.HOUR, 24*vacaInfo.getIntervalo_padrao_anestro());/* SOMANDO */
            formatoDataBr.setCalendar(dataPrevIniProxCio);
            horaPrevIniProxCio.setTime(formatoCompletoUSA.parse(StrDataHoraFimUltCio)); 
            formatoHoraBr.setCalendar(horaPrevIniProxCio);
            
            vacaInfo.setDt_ini_ult_cio(formatoDataUSA.format(formatoDataUSA.parse(StrDataHoraInicioUltCio)));
            vacaInfo.setHr_ini_ult_cio(formatoHoraBr.format(formatoCompletoUSA.parse(StrDataHoraInicioUltCio)));
            vacaInfo.setDt_fim_ult_cio(formatoDataUSA.format(formatoDataUSA.parse(StrDataHoraFimUltCio)));
            vacaInfo.setHr_fim_ult_cio(formatoHoraBr.format(formatoCompletoUSA.parse(StrDataHoraFimUltCio)));
            vacaInfo.setDt_prev_ini_prox_cio(formatoDataUSA.format(dataPrevIniProxCio.getTime()));
            vacaInfo.setHr_prev_ini_prox_cio(formatoHoraBr.format(horaPrevIniProxCio.getTime()));
            
        } catch (ParseException ex) {
            Logger.getLogger(ControleMetodosAnimal.class.getName()).log(Level.SEVERE, null, ex);
        }
        vacaInfo.setDias_em_anestro(diasAnestroAtual);
        int aproximacaoCio = (int)((vacaInfo.getDias_em_anestro()*10)/vacaInfo.getIntervalo_padrao_anestro());
        if(aproximacaoCio <= 10 && aproximacaoCio >= 0){
            vacaInfo.setAprox_cio(aproximacaoCio);  
        } else {
            if(aproximacaoCio > 10)
               vacaInfo.setAprox_cio(10);  
            if(aproximacaoCio < 0)
                vacaInfo.setAprox_cio(0);
        }
        vacaInfo.setIdade(Integer.parseInt(request.getParameter("cmp_anim_idade")));
        vacaInfo.setStatus_cio(Integer.parseInt(request.getParameter("cmp_anim_status_cio")));
        vacaInfo.setStatus_atividade(request.getParameter("cmp_anim_status_atividade"));
        vacaInfo.setOco_produtiva("NORMAL");
        vacaInfo.setOco_reprodutiva("NORMAL");
        int aproximacaoCio2 = (int)((vacaInfo.getDias_em_anestro()*10)/vacaInfo.getIntervalo_padrao_anestro());
        if(aproximacaoCio2 <= 10 && aproximacaoCio2 >= 0){
            vacaInfo.setAprox_cio(aproximacaoCio2);  
        } else {
            if(aproximacaoCio2 > 10)
               vacaInfo.setAprox_cio(10);  
            if(aproximacaoCio2 < 0)
                vacaInfo.setAprox_cio(0);
        }
        vacaInfo.setAnimal(vaca);
        
        try {
            acessoBDAnimal.editarDAO(vaca);
            acessoBDAnimalInfo.editarDAO(vacaInfo);
            if(vaca.getSensor() != null && vaca.getMonitorando() == 1){
                if(sensorAnimalBD != null){
                    acessoBDSensor.editarStatusDAO(0, sensorAnimalBD, null);//mudando o status do sensor antigo vinculado ao animal para INATIVO
                    acessoBDSensor.editarStatusDAO(1, s, vaca);//mudando o status do novo sensor vinculado ao animal para ATIVO
                } else {
                   acessoBDSensor.editarStatusDAO(1, s, vaca);//mudando o status do novo sensor vinculado ao animal para ATIVO 
                }
            } else {
                if(sensorAnimalBD != null){
                    acessoBDSensor.editarStatusDAO(0, sensorAnimalBD, null);//mudando o status do sensor antigo vinculado ao animal para INATIVO
                }
            }
            request.setAttribute("mensagem", "Animal atualizado com sucesso!");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=animal").forward(request, response);//retorna para a index apresentando o erro
        } catch (ServletException | IOException ex) {
            request.setAttribute("mensagem", "Animal  não atualizado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=animal").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    public void detalharOLD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String valorBusca = request.getParameter("codigo");//recuperando id para edição
        List<Animal> resultado = acessoBDAnimal.buscarDAO(valorBusca);
        List<AnimalInfo> aniMonInfo = new ArrayList<>();
        if (resultado != null) {
            for(Animal animal : resultado){
                aniMonInfo.add(acessoBDAnimalInfo.buscarPorIdAnimalDAO(animal.getId()));
            }
            request.setAttribute("listaanimaisinfo", aniMonInfo);
            request.getRequestDispatcher("principal?d=forms&a=detalhar&f=animal").forward(request, response);//despacha para a proxima página 
        } else {
            request.setAttribute("mensagem", "Animal não cadastrado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=animal").forward(request, response);//retorna para a index apresentando o erro
        }
    }
    
    @Override
    public void detalhar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Animal resultado = acessoBDAnimal.buscarPorIdDAO(Integer.parseInt(request.getParameter("id")));
        AnimalInfo aninfo = new AnimalInfo();
        if (resultado != null) {
            aninfo = acessoBDAnimalInfo.buscarPorIdAnimalDAO(resultado.getId());
            request.setAttribute("detalhesanimal", aninfo);
            request.getRequestDispatcher("principal?d=forms&a=detalhar&f=animal").forward(request, response);//despacha para a proxima página 
        } else {
            request.setAttribute("mensagem", "Animal não cadastrado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=animal").forward(request, response);//retorna para a index apresentando o erro
        }
    }
    

    @Override
    public void recuperarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void carregarOLD(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Animal> aniMon = acessoBDAnimal.carregarDAO();
        List<AnimalInfo> aniMonInfo = new ArrayList<>();
        if(aniMon != null){
            for(Animal animal : aniMon){
                aniMonInfo.add(acessoBDAnimalInfo.buscarPorIdAnimalDAO(animal.getId()));
            }
            //JOptionPane.showMessageDialog(null, "teste");
            //HttpSession sessao = request.getSession();
            request.setAttribute("infoAnimaisMonitorados", aniMonInfo);
            request.getRequestDispatcher("principal?d=pages&f=previsaoCio").forward(request, response);//despacha para a proxima página
        } else {
            request.setAttribute("mensagem", "Erro no carregamento dos animais monitorados vigente");
            request.getRequestDispatcher("principal?d=&f=home").forward(request, response);//retorna para a index apresentando o erro
        }
    }
    
    @Override
    public void carregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Animal> aniMon = acessoBDAnimal.carregarDAO();
        List<AnimalInfo> aniMonInfo = new ArrayList<>();
        if(aniMon != null){
            for(Animal animal : aniMon){
                aniMonInfo.add(acessoBDAnimalInfo.buscarPorIdAnimalDAO(animal.getId()));
            }
            HttpSession sessao = request.getSession();
            sessao.setAttribute("infoAnimaisMonitorados", aniMonInfo);
        }
    }

}
