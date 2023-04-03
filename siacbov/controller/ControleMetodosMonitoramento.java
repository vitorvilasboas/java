package br.edu.cio.controller;

import br.edu.cio.model.Animal;
import br.edu.cio.model.AnimalInfo;
import br.edu.cio.model.AtividadeAnimal;
import br.edu.cio.model.Cio;
import br.edu.cio.model.GraficoAtividade;
import br.edu.cio.model.Leitura;
import br.edu.cio.model.Sensor;
import br.edu.cio.model.dao.AnimalDAO;
import br.edu.cio.model.dao.AnimalInfoDAO;
import br.edu.cio.model.dao.CioDAO;
import br.edu.cio.model.dao.LeituraDAO;
import br.edu.cio.model.dao.MetodosInferenciaCioDAO;
import br.edu.cio.model.dao.SensorDAO;
import br.edu.cio.util.DAOFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

public class ControleMetodosMonitoramento implements ControleMetodosComunsServicos{
    
    LeituraDAO acessoBD = DAOFactory.gerarLeituraDAO();
    SensorDAO acessoBDSensor = DAOFactory.gerarSensorDAO();
    AnimalDAO acessoBDAnimal = DAOFactory.gerarAnimalDAO();
    AnimalInfoDAO acessoBDAnimalInfo = DAOFactory.gerarAnimalInfoDAO();
    CioDAO acessoBDCio = DAOFactory.gerarCioDAO();
    MetodosInferenciaCioDAO acessoBDMIC = DAOFactory.gerarMetodosInferenciaCioDAO();
    SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatoDataBr = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatoHoraBr = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat formatoCompletoUSA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat formatoCompletoBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    @Override
    public void carregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {/* MONTAR CALENDÁRIO ESTRAL */
        List<Cio> cios = acessoBDCio.buscarDAO("");     
        if(cios != null){
            request.setAttribute("ciclosCadastrados", cios);
            request.getRequestDispatcher("principal?d=pages&f=calendarioEstral").forward(request, response);
        } else {
            request.setAttribute("mensagem", "Não existem ocorrências de cio no Calendário");
            request.getRequestDispatcher("principal?d=pages&f=calendarioEstral").forward(request, response);
        }
    }

    @Override
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void gravarLeituraManual(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        gravarLeitura(request, response);
        request.setAttribute("mensagem", "Registros Atualizados com sucesso");
        request.getRequestDispatcher("principal?d=&a=&f=home").forward(request, response);//retorna para a principal apresentando o erro
        //response.sendRedirect("principal?d=&a=&f=home");
    }
    
    public void gravarLeitura(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //grava cada linha do arquivo de simulação no BD
        String url_arquivo = "C:\\Users\\JV\\Documents\\NetBeansProjects\\siac\\regsensores\\regs.txt";
        File arquivo = new File(url_arquivo);
        
        List<String> registrosArquivo = new ArrayList<>();
        String dadosLinha[];//vetor de String usado para separar os dados de cada linha do arquivo
        
        Leitura le = new Leitura();//usada para instanciar os atributos de cada linha do arquivo
        
        boolean cadastrado = false;
        
        String dataLeitura;
        String horaLeitura;

        try( InputStream entrada = new FileInputStream(arquivo)){
            Scanner leitor = new Scanner(entrada);
            while( leitor.hasNext() )
                registrosArquivo.add(leitor.nextLine());//adiciona cada linha do arquivo em um array de strings
        }catch(IOException ex){}
        
        Collections.reverse(registrosArquivo);//inverte array
        
        for(String linhaRegistro : registrosArquivo){//percorre o array até o fim e armazena cada registro temporariamente na string linhaRegistro
            if(!linhaRegistro.isEmpty()){//verifica se a linha não está em branco
                
                dadosLinha=linhaRegistro.split("&");//separa a linha a cada vez que o delimitador (&) é encontrado e armazena temporariamente cada atributo em uma posição do vetor de string dadosLinha
                
                le.setCodigo_central(dadosLinha[0]);
                le.setCodigo_sensor(dadosLinha[1]);
                le.setnPassadas(Integer.parseInt(dadosLinha[4]));
                le.setnLeitura(Integer.parseInt(dadosLinha[5]));
                
                dataLeitura = "";
                for(int i=0; i < dadosLinha[2].length(); i++){//adicionando as / na data da Leitura
                    dataLeitura = dataLeitura + dadosLinha[2].charAt(i);
                    if(i==1 || i==3){
                        dataLeitura = dataLeitura + "/";
                    }
                }
                le.setData(dataLeitura);
                
                horaLeitura = "";
                for(int i=0; i < dadosLinha[3].length(); i++){//adicionando as : na hora da Leitura
                    horaLeitura = horaLeitura + dadosLinha[3].charAt(i);
                    if(i==1 || i==3){
                        horaLeitura = horaLeitura + ":";
                    }
                }
                le.setHora(horaLeitura);
                
                Sensor s = acessoBDSensor.buscarPorCodigoDAO(le.getCodigo_sensor());//busca o sensor da leitura pelo código
                if((s != null) && (s.getAtivo() == 1)){//verifica se o sensor existe e se está ativo
                    //cadastrado = acessoBD.buscarDAO(le, s.getId());//verifica no banco se o registro já foi cadastrado
                    if(!cadastrado)// se a leitura ainda não foi cadastrada no banco...
                        acessoBD.inserirDAO(le);// insere leitura no banco
                    /* else
                        System.out.println(">> Leitura "+ le.getnLeitura() +" do sensor de código "+ le.getCodigo_sensor() +" já cadastrada"); 
                } else {
                    System.out.println("\n>> Leitura "+ le.getnLeitura() +" é de um sensor inativo.");
                */
                }
            }
        }
        //System.out.println("ÚLTIMA LEITURA GRAVADA: " +le.getnLeitura());
        //JOptionPane.showMessageDialog(null, "FOI");
    } 

    @Override
    public void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int idAnimal = Integer.parseInt(request.getParameter("cmp_grafico_1"));
            String intervalo = request.getParameter("cmp_grafico_4");
            String escala = request.getParameter("cmp_grafico_5");
            
            int i=0;
            String inicioPeriodo = "";
            String inicioPeriodoData = "";
            String inicioPeriodoHora = "";
            if(request.getParameter("cmp_grafico_2") != null && !"".equals(request.getParameter("cmp_grafico_2"))){
                while(i < request.getParameter("cmp_grafico_2").length() ){
                    if( request.getParameter("cmp_grafico_2").charAt(i) == 'T' )
                        inicioPeriodo += " ";
                    else
                        inicioPeriodo +=request.getParameter("cmp_grafico_2").charAt(i);
                    i++;
                }
                inicioPeriodo += ":00";
                inicioPeriodoData = formatoDataBr.format(formatoCompletoUSA.parse(inicioPeriodo));
                inicioPeriodoHora = formatoHoraBr.format(formatoCompletoUSA.parse(inicioPeriodo));
                inicioPeriodo = formatoCompletoBr.format(formatoCompletoUSA.parse(inicioPeriodo));
            }
            
            String fimPeriodo = "";
            String fimPeriodoData = "";
            String fimPeriodoHora = "";
            if(request.getParameter("cmp_grafico_3") != null && !"".equals(request.getParameter("cmp_grafico_3"))){
                i=0;
                while(i < request.getParameter("cmp_grafico_3").length() ){
                    if( request.getParameter("cmp_grafico_3").charAt(i) == 'T' )
                        fimPeriodo += " ";
                    else
                        fimPeriodo +=request.getParameter("cmp_grafico_3").charAt(i);
                    i++;
                }
                fimPeriodo += ":00";
                fimPeriodoData = formatoDataBr.format(formatoCompletoUSA.parse(fimPeriodo));
                fimPeriodoHora = formatoHoraBr.format(formatoCompletoUSA.parse(fimPeriodo));
                fimPeriodo = formatoCompletoBr.format(formatoCompletoUSA.parse(fimPeriodo));
            }
            
            GraficoAtividade ga = new GraficoAtividade();
            ga.setEscala(escala);
            ga.setFimPeriodo(fimPeriodo);
            ga.setInicioPeriodo(inicioPeriodo);
            ga.setIdAnimal(idAnimal);
            ga.setIntervalo(intervalo);
            
            Date dtInicioPeriodo = formatoCompletoBr.parse(inicioPeriodo);
            Date dtFimPeriodo = formatoCompletoBr.parse(fimPeriodo);
            Date dtTempLeitura = null;
            
            switch(escala){
                case "PASSADASHORA":{
                    AnimalInfo ainfo = acessoBDAnimalInfo.buscarPorIdAnimalDAO(idAnimal);
                    List<AtividadeAnimal> listaGeralAtividade  = acessoBDMIC.listarPorIdAnimal(idAnimal);
                    if(listaGeralAtividade != null){
                        List<AtividadeAnimal> atividadesPeriodo = new ArrayList<>();
                        for(AtividadeAnimal atv : listaGeralAtividade){
                            dtTempLeitura = formatoCompletoBr.parse(atv.getLeitura().getData()+" "+atv.getLeitura().getHora());
                            if( dtTempLeitura.after(dtInicioPeriodo) && dtTempLeitura.before(dtFimPeriodo) ){
                                atividadesPeriodo.add(atv);
                            }
                        }
                        request.setAttribute("aninfoGrafico", ainfo);
                        request.setAttribute("AtvLeiturasGrafico", atividadesPeriodo);
                        request.setAttribute("paramGeraGrafico", ga);
                        request.getRequestDispatcher("principal?d=pages&f=gfcAtividade&a=gerar").forward(request, response);
                    } else {
                        request.setAttribute("mensagem", "Não há leituras registradas para o período.");
                        request.setAttribute("paramGeraGrafico", ga);
                        request.getRequestDispatcher("principal?d=pages&f=gfcAtividade&a=buscar").forward(request, response);
                    }                   
                    break;
                    /*
                    Animal animal = acessoBDAnimal.buscarPorIdDAO(idAnimal);//buscar sensor pelo id-animal
                    AnimalInfo ainfo = acessoBDAnimalInfo.buscarPorIdAnimalDAO(idAnimal);
                    List<Leitura> leiturasGeral  = acessoBD.listarPorIdSensor(animal.getSensor().getId());//buscar leituras pelo id-sensor
                    if(leiturasGeral != null){
                        List<Leitura> leiturasPeriodo = new ArrayList<>();
                        for(Leitura l : leiturasGeral){
                            dtTempLeitura = formatoCompletoBr.parse(l.getData()+" "+l.getHora());
                            if( dtTempLeitura.after(dtInicioPeriodo) && dtTempLeitura.before(dtFimPeriodo) ){
                                //l.setData(formatoDataUSA.format(formatoDataBr.parse(l.getData())));
                                leiturasPeriodo.add(l);
                            }
                        }
                        request.setAttribute("aninfoGrafico", ainfo);
                        request.setAttribute("leiturasGrafico", leiturasPeriodo);
                        request.setAttribute("paramGeraGrafico", ga);
                        request.getRequestDispatcher("principal?d=pages&f=gfcAtividade&a=gerar").forward(request, response);
                    } else {
                        request.setAttribute("mensagem", "Não há leituras registradas para o período.");
                        request.setAttribute("paramGeraGrafico", ga);
                        request.getRequestDispatcher("principal?d=pages&f=gfcAtividade&a=buscar").forward(request, response);
                    }                   
                    break;
                    */
                } case "NIVELATIVIDADE":{
                    AnimalInfo ainfo = acessoBDAnimalInfo.buscarPorIdAnimalDAO(idAnimal);
                    List<AtividadeAnimal> listaGeralAtividade  = acessoBDMIC.listarPorIdAnimal(idAnimal);
                    if(listaGeralAtividade != null){
                        List<AtividadeAnimal> atividadesPeriodo = new ArrayList<>();
                        for(AtividadeAnimal atv : listaGeralAtividade){
                            dtTempLeitura = formatoCompletoBr.parse(atv.getLeitura().getData()+" "+atv.getLeitura().getHora());
                            if( dtTempLeitura.after(dtInicioPeriodo) && dtTempLeitura.before(dtFimPeriodo) ){
                                atividadesPeriodo.add(atv);
                            }
                        }
                        request.setAttribute("aninfoGrafico", ainfo);
                        request.setAttribute("AtvLeiturasGrafico", atividadesPeriodo);
                        request.setAttribute("paramGeraGrafico", ga);
                        request.getRequestDispatcher("principal?d=pages&f=gfcAtividade&a=gerar").forward(request, response);
                    } else {
                        request.setAttribute("mensagem", "Não há leituras registradas para o período.");
                        request.setAttribute("paramGeraGrafico", ga);
                        request.getRequestDispatcher("principal?d=pages&f=gfcAtividade&a=buscar").forward(request, response);
                    }                   
                    break;
                }
                
                default:
                    //GregorianCalendar gcTempLeitura = new GregorianCalendar();
                    //gcFimPeriodo.setTime(formatoCompletoBr.parse(fimPeriodo));
                    //dthPrevTerminoCio.add(Calendar.HOUR, duracao_media_cio_animal);
                    //formatoDataBr.setCalendar(dthPrevTerminoCio);
                    //c.setData_previsao_termino(formatoDataBr.format(dthPrevTerminoCio.getTime()));
                    //c.setHora_previsao_termino(formatoHoraBr.format(dthPrevTerminoCio.getTime()));
                    break;
            }
            
        } catch (ParseException ex) {
            Logger.getLogger(ControleMetodosMonitoramento.class.getName()).log(Level.SEVERE, null, ex);
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