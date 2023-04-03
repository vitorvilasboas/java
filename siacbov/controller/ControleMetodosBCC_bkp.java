package br.edu.cio.controller;

import br.edu.cio.model.Alerta;
import br.edu.cio.model.AlertaEmitido;
import br.edu.cio.model.Animal;
import br.edu.cio.model.AnimalInfo;
import br.edu.cio.model.AtividadeAnimal;
import br.edu.cio.model.Cio;
import br.edu.cio.model.Configuracao;
import br.edu.cio.model.Destinatario;
import br.edu.cio.model.Leitura;
import br.edu.cio.model.Sensor;
import br.edu.cio.model.UndProcessamento;
import br.edu.cio.model.dao.AlertaDAO;
import br.edu.cio.model.dao.AlertaEmitidoDAO;
import br.edu.cio.model.dao.AnimalDAO;
import br.edu.cio.model.dao.AnimalInfoDAO;
import br.edu.cio.model.dao.CioDAO;
import br.edu.cio.model.dao.ConfiguracaoDAO;
import br.edu.cio.model.dao.DestinatarioDAO;
import br.edu.cio.model.dao.LeituraDAO;
import br.edu.cio.model.dao.MetodosInferenciaCioDAO;
import br.edu.cio.model.dao.SensorDAO;
import br.edu.cio.model.dao.UndProcessamentoDAO;
import br.edu.cio.util.Celular;
import br.edu.cio.util.DAOFactory;
import br.edu.cio.util.Email;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import br.edu.cio.model.dao.mysql.MetodosUteisDAOMySQL;

public class ControleMetodosBCC_bkp implements ControleMetodosComunsServicos{
    
    AnimalDAO acessoBDAnimal = DAOFactory.gerarAnimalDAO();
    LeituraDAO acessoBD = DAOFactory.gerarLeituraDAO();
    SensorDAO acessoBDSensor = DAOFactory.gerarSensorDAO();
    UndProcessamentoDAO acessoBDCentral = DAOFactory.gerarUndProcessamentoDAO();
    ConfiguracaoDAO acessoBDConfig = DAOFactory.gerarConfiguracaoDAO();
    AnimalInfoDAO acessoBDAnimalInfo = DAOFactory.gerarAnimalInfoDAO();
    MetodosInferenciaCioDAO acessoBDmic = DAOFactory.gerarMetodosInferenciaCioDAO();
    CioDAO acessoBDCio = DAOFactory.gerarCioDAO();
    AlertaDAO acessoBDAlerta = DAOFactory.gerarAlertaDAO();
    AlertaEmitidoDAO acessoBDAlem = DAOFactory.gerarAlertaEmitidoDAO();
    DestinatarioDAO acessoBDDestinatario = DAOFactory.gerarDestinatarioDAO();
    
    @Override
    public void carregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void atualizarManual(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, Exception {
        //Celular c = new Celular();
        atualizarAuto(request, response);
        request.setAttribute("mensagem", "Registros Atualizados com sucesso");
        request.getRequestDispatcher("principal?d=&a=&f=home").forward(request, response);//retorna para a principal apresentando o erro
        //response.sendRedirect("principal?d=&a=&f=home");
    }
    
    public void atualizarAuto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {//grava cada linha do arquivo de simulação no BD
        HttpSession sessao = request.getSession();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat formatoHoraBr = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoCompletoBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat formatoCompletoUSA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /*  LEITURA DOS DADOS NO ARQUIVO DE MIDLLEWARE  */
        String url_arquivo = "C:\\Users\\JV\\Documents\\NetBeansProjects\\siac\\regsensores\\regs.txt";
        File arquivo = new File(url_arquivo);
        List<String> registrosArquivoTemp = new ArrayList<>();
        List<String> registrosArquivo = new ArrayList<>();
        String dadosLinha[];//vetor de String usado para separar os dados de cada linha do arquivo
        String dataLeitura;
        String horaLeitura;
        Leitura leitura = new Leitura();//usada para instanciar os atributos de cada linha do arquivo
        Leitura leituraCadastrada = new Leitura();//usada na verificação se a leitura já está cadastrada
        Leitura leituraPosCadastro = new Leitura();
        Cio ultCioAnimal = new Cio();
        Cio cioCad = new Cio();
        Alerta alertaCad = new Alerta();
        AlertaEmitido alemCad = new AlertaEmitido();
        Email email = new Email();
        java.util.Date agora = new java.util.Date();//capturando data e hora atual do sistema
        Configuracao config = (Configuracao) sessao.getAttribute("conf");//recupera configuração atual da sessão
        config.setData_atual(formatoDataUSA.format(agora));
        config.setHora_atual(formatoHoraBr.format(agora));
        acessoBDConfig.atualizarDAO(config);
        config = acessoBDConfig.carregarDAO(config.getId());
        try( InputStream entrada = new FileInputStream(arquivo)){
            Scanner leitor = new Scanner(entrada);
            while( leitor.hasNext() )
                registrosArquivoTemp.add(leitor.nextLine());//adiciona cada linha do arquivo em um array de strings     
        }catch(IOException ex){} 
        Collections.reverse(registrosArquivoTemp);//inverte array temporário
        for(String linhaRegistroTemp : registrosArquivoTemp){
            if(!linhaRegistroTemp.equals("********* REGISTROS GRAVADOS **********"))
                registrosArquivo.add(linhaRegistroTemp);//alimenta arquivo OFICIAL até encontrar a linha com o flag acima
            else 
                break;
        }
        int cont=0;//contador de linhas a cadastrar
        Collections.reverse(registrosArquivo);//inverte array OFICIAL
        for(String linhaRegistro : registrosArquivo){//percorre o array até o fim e armazena cada registro temporariamente na string linhaRegistro
            cont++;
            if(!linhaRegistro.isEmpty()){//verifica se a linha não está em branco
                dadosLinha=linhaRegistro.split("&");//separa a linha a cada vez que o delimitador (&) é encontrado e armazena temporariamente cada atributo em uma posição do vetor de string dadosLinha
                dataLeitura = "";
                horaLeitura = "";
                for(int i=0; i < dadosLinha[2].length(); i++){//adicionando as barras(/) na data da Leitura
                    dataLeitura = dataLeitura + dadosLinha[2].charAt(i);
                    if(i==1 || i==3)
                        dataLeitura = dataLeitura + "/";
                }
                for(int i=0; i < dadosLinha[3].length(); i++){//adicionando : na hora da Leitura
                    horaLeitura = horaLeitura + dadosLinha[3].charAt(i);
                    if(i==1 || i==3)
                        horaLeitura = horaLeitura + ":";
                }
                leitura.setCodigo_central(dadosLinha[0]);
                leitura.setCodigo_sensor(dadosLinha[1]);
                leitura.setnPassadas(Integer.parseInt(dadosLinha[4]));
                leitura.setnLeitura(Integer.parseInt(dadosLinha[5]));
                leitura.setData(dataLeitura);
                leitura.setHora(horaLeitura);
                leitura.setSensor(acessoBDSensor.buscarPorCodigoDAO(leitura.getCodigo_sensor()));//busca o sensor da leitura pelo código
                leitura.setCentral(acessoBDCentral.buscarPorCodigoDAO(leitura.getCodigo_central()));//busca a central da leitura pelo código
    /* CADASTRAMENTO DE LEITURAS NA BASE DE CONHECIMENTO */
                if((leitura.getSensor() != null) && (leitura.getSensor().getAtivo() == 1)){//verifica se o sensor existe e se está ativo
                    leituraCadastrada = acessoBD.buscarDAO(leitura);//verifica no banco se o registro já foi cadastrado
                    if(leituraCadastrada == null){// se a leitura ainda não foi cadastrada no banco...
                        acessoBD.inserirDAO(leitura);// insere leitura no banco
                        leituraPosCadastro = acessoBD.buscarDAO(leitura);//recupera leitura recem cadastrada, através da data, hora e sensor
    /* CLASSIFICAÇÃO DO NÍVEL DE ATIVIDADE ANIMAL */                    
                        
                        GregorianCalendar datahoraSistema = new GregorianCalendar();
                        java.util.Date dthSistema = formato.parse(config.getData_atual()+ " " + config.getHora_atual());
                        datahoraSistema.setTime(dthSistema);
                        Animal animalSensor = acessoBDAnimal.buscarPorIdSensorDAO(leituraPosCadastro.getSensor().getId());//recupera o animal da leitura recem cadastrada a partir do id do sensor
                        AnimalInfo infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//recupera as informações do animal
                        int variacao = (int)(leituraPosCadastro.getnPassadas()/config.getIntervalo_entre_leituras()) - infoAnimalSensor.getMedia_passos_hora(); //calcula a variação de passadas com base em seu padrão
                        double perc_variacao = variacao * 100 / infoAnimalSensor.getMedia_passos_hora(); //calcula o percentual de variação/desvio do padrão de passadas
                        String classificacao = null;
                        if( ((int) perc_variacao) < config.getPerc_min_atv_media() ){
                            classificacao = "BAIXA";
                        } else if ( ( ((int) perc_variacao) >= config.getPerc_min_atv_media() ) && ( ((int) perc_variacao) < config.getPerc_min_atv_alta() ) ) {
                            classificacao = "MEDIA";
                        } else {
                            classificacao = "ALTA";
                        }
            /* >>>> ATUALIZAR MÉDIA DE PASSOS POR HORA na tabela info_animal */
            // Buscar todas as leituras do animal, tirar a media por hora de acordo com o intervalo_leituras, somar as medias e dividir pela qtd de leituras
            // converter para int e atualizar a média de passos no banco
                        acessoBDmic.classificarAtividadeAnimalDAO(leituraPosCadastro, animalSensor, perc_variacao, variacao, classificacao);//cadastra a classificação da atividade do animal no BD
                        AtividadeAnimal atvAnimal = acessoBDmic.buscarAtividadeAnimalPorIdLeituraDAO(leituraPosCadastro.getId());//recupera registro de atividade animal recém classificada e cadastrado
    /* VERIFICAÇÃO E ATUALIZAÇÃO DO PERIODO ATUAL DE ANESTRO EM DIAS */ 
                        infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as Informações do animal após a classificação da atividade
                        
                        java.util.Date tempoLeituraPosCadastro = formato.parse(leituraPosCadastro.getData() + " " + leituraPosCadastro.getHora());
                        java.util.Date tempoFimUltimoCio = formato.parse(infoAnimalSensor.getDt_fim_ult_cio() + " " + infoAnimalSensor.getHr_fim_ult_cio());
                        GregorianCalendar data1 = new GregorianCalendar(); 
                        GregorianCalendar data2 = new GregorianCalendar();
                        data1.setTime(tempoLeituraPosCadastro);  
                        data2.setTime(tempoFimUltimoCio);
                        long milissegundos = data1.getTimeInMillis() - data2.getTimeInMillis();
                        int diasAnestro = (int) (milissegundos/(24*60*60*1000));
                        int aproximacaoCio = (int)((diasAnestro*10)/infoAnimalSensor.getIntervalo_padrao_anestro());
                        if(aproximacaoCio <= 10)
                            infoAnimalSensor.setAprox_cio(aproximacaoCio);
                        else
                            infoAnimalSensor.setAprox_cio(10);
                        if((int)diasAnestro > infoAnimalSensor.getDias_em_anestro()){//se o periodo de anestro atual for maior que o periodo cadastrado...
                            acessoBDAnimalInfo.atualizarTempoAnestroDAO(infoAnimalSensor, diasAnestro);//atualiza o tempo em anestro
                            infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as Informações do animal novamente após a atualização do tempo em anestro
                        }
                        //JOptionPane.showMessageDialog(null, "ANIMAL "+ animalSensor.getCodigo() +":\n dias em anestro: "+infoAnimalSensor.getDias_em_anestro()+"\n Tempo Atual em Anestro: "+infoAnimalSensor.getTempo_atual_anestro());
    /* INFERÊNCIA SOBRE O CIO -- TOMADA DE DECISÃO -- CADASTRAMENTO DO CIO -- CADASTRO E EMISSÃO DE ALERTA  */
                        GregorianCalendar dataAtual = new GregorianCalendar();
                        
                        dataAtual.setTime(agora);
                        String status_cio = null;
                        String observacao_cio = null;
                        String msg_alerta = null;
                        String mtd_registro_cio = "AUTOMATICO";
                        String mtd_identificacao_cio = "INFERENCIA";
                        /* Preparando lista de destinatários */
                        List<Destinatario> destinatarios = new ArrayList<>();
                        destinatarios = acessoBDDestinatario.buscarDAO("");
                        List<Destinatario> destinatariosAtivos = new ArrayList<>();
                        for(Destinatario d : destinatarios){
                            if(d.getAtivo() == 1){
                                destinatariosAtivos.add(d);
                            }
                        }
                        /* ^^^^^ */
                        if(infoAnimalSensor.getStatus_cio() == 0){//Se o animal não está em cio..
                            ultCioAnimal = acessoBDCio.buscarUltimoDoAnimalDAO(animalSensor.getId());//captura o ultimo cio cadastrado relacionado ao animal
                            int diasCadUltCioAni = 999999999;
                            GregorianCalendar dataUltCioAnimal = new GregorianCalendar();
                            if(ultCioAnimal != null){//se já foi cadastrado algum cio para o animal...
                                dataUltCioAnimal.setTime(formatoCompletoBr.parse(ultCioAnimal.getData_registro() + " " + ultCioAnimal.getHora_registro()));
                                //long milissegundos2 = dataAtual.getTimeInMillis() - dataUltCioAnimal.getTimeInMillis();//ativar em ambiente real
                                //long milissegundos2 = data1.getTimeInMillis() - dataUltCioAnimal.getTimeInMillis();//data1 é o tempoLeituraPosCadastro
                                long milissegundos2 = datahoraSistema.getTimeInMillis() - dataUltCioAnimal.getTimeInMillis();
                                diasCadUltCioAni = (int) (milissegundos2/(24*60*60*1000));
                            } else {}//... então nunca foi cadastrado nenhum cio para o animal 
                            if (diasCadUltCioAni > 2){ //... e ainda se nenhum cio foi cadastrado para o animal nos ultimos 2 dias
                                if(infoAnimalSensor.getStatus_atividade().equals("MEDIA")){//Se a variação de atividade animal é MÉDIA
                                    if(!infoAnimalSensor.getTempo_atual_anestro().equals("DIA DO CIO OCORRIDO") && infoAnimalSensor.getDt_prev_ini_prox_cio().equals(leituraPosCadastro.getData())){//... e se dt_prev_cio == dt_atual
                                        status_cio = "SUSPEITO";
                                        observacao_cio = "Desvio MÉDIO da Atividade animal e Data de Previsão de Cio é igual a data atual";
                                        //cioCad = instanciaCio(animalSensor, atvAnimal, leituraPosCadastro, status_cio, observacao_cio, mtd_registro_cio, mtd_identificacao_cio);
                                        acessoBDCio.cadastrarAutomaticoDAO(cioCad);
                                        ultCioAnimal = acessoBDCio.buscarUltimoRegistroDAO();
                                        /* ALTERAR TABELA LOG_CIO */
                                        msg_alerta = "Novo Cio cadastrado com o status " +status_cio+ " para o animal "+animalSensor.getCodigo()+" - " +animalSensor.getCodigo()
                                                + ".\n Hora de Cadastro: "+formatoCompletoBr.format(agora);
                                        //alertaCad = instanciaAlerta(msg_alerta, animalSensor, leituraPosCadastro, atvAnimal, ultCioAnimal);
                                        acessoBDAlerta.cadastrarDAO(alertaCad);
                                        infoAnimalSensor.setSob_alerta(infoAnimalSensor.getSob_alerta() + 1);
                                        acessoBDAnimalInfo.editarDAO(infoAnimalSensor);//edita-se as informações do animal a fim de atualizar o campo sob_alerta
                                        infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as informações do animal após atualização do campo sob_alerta
                                        for(Destinatario d : destinatariosAtivos){//emitir um alerta (cadastrar alerta emitido no bd) a cada destinatario ativo
                                            //alemCad = instanciaAlertaEmitido(leituraPosCadastro.getData(), leituraPosCadastro.getHora(), alertaCad, d);
                                            acessoBDAlem.cadastrarDAO(alemCad);
                                        }
                                        //email.enviar(destinatariosAtivos, "SIAC BOVINO - Alerta de Cio", msg_alerta);//enviando email para os destinatarios ativos
                                        request.setAttribute("alerta_cio", msg_alerta);
                                        JOptionPane.showMessageDialog(null, msg_alerta);
                                    } else if(infoAnimalSensor.getAprox_cio() >= 8){//... e se o dia previsto do próximo cio está se aproximando
                                        status_cio = "SUSPEITO";
                                        observacao_cio = "Desvio MÉDIO da Atividade animal e VÉSPERA da Data de Previsão de Cio";
                                        //cioCad = MetodosUteisDAOMySQL.instanciaCio(animalSensor, atvAnimal, leituraPosCadastro, status_cio, observacao_cio, mtd_registro_cio, mtd_identificacao_cio);
                                        acessoBDCio.cadastrarAutomaticoDAO(cioCad);
                                        ultCioAnimal = acessoBDCio.buscarUltimoRegistroDAO();
                                        /* ALTERAR TABELA LOG_CIO */
                                        msg_alerta = "Novo Cio cadastrado com o status " +status_cio+ " para o animal "+animalSensor.getCodigo()+" - " +animalSensor.getCodigo()
                                                + ".\n Hora de Cadastro: "+formatoCompletoBr.format(agora);
                                        //alertaCad = MetodosUteisDAOMySQL.instanciaAlerta(msg_alerta, animalSensor, leituraPosCadastro, atvAnimal, ultCioAnimal);
                                        acessoBDAlerta.cadastrarDAO(alertaCad);
                                        infoAnimalSensor.setSob_alerta(infoAnimalSensor.getSob_alerta() + 1);
                                        acessoBDAnimalInfo.editarDAO(infoAnimalSensor);//edita-se as informações do animal a fim de atualizar o campo sob_alerta
                                        infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as informações do animal após atualização do campo sob_alerta
                                        for(Destinatario d : destinatariosAtivos){//emitir um alerta (cadastrar alerta emitido no bd) a cada destinatario ativo
                                            //alemCad = MetodosUteisDAOMySQL.instanciaAlertaEmitido(leituraPosCadastro.getData(), leituraPosCadastro.getHora(), alertaCad, d);
                                            acessoBDAlem.cadastrarDAO(alemCad);
                                        }
                                        //email.enviar(destinatariosAtivos, "SIAC BOVINO - Alerta de Cio", msg_alerta);//enviando email para os destinatarios ativos
                                        request.setAttribute("alerta_cio", msg_alerta);
                                        JOptionPane.showMessageDialog(null, msg_alerta);
                                    } else if(infoAnimalSensor.getTempo_atual_anestro().equals("NORMAL")){//... e se o tempo atual em anestro está entre 16 e 24 dias
                                        status_cio = "SUSPEITO";
                                        observacao_cio = "Desvio MÉDIO da Atividade animal e ÉPOCA de Previsão de Cio";
                                        //cioCad = MetodosUteisDAOMySQL.instanciaCio(animalSensor, atvAnimal, leituraPosCadastro, status_cio, observacao_cio, mtd_registro_cio, mtd_identificacao_cio);
                                        acessoBDCio.cadastrarAutomaticoDAO(cioCad);
                                        ultCioAnimal = acessoBDCio.buscarUltimoRegistroDAO();
                                        /* ALTERAR TABELA LOG_CIO */
                                        msg_alerta = "Novo Cio cadastrado com o status " +status_cio+ " para o animal "+animalSensor.getCodigo()+" - " +animalSensor.getCodigo()
                                                + ".\n Hora de Cadastro: "+formatoCompletoBr.format(agora);
                                        //alertaCad = MetodosUteisDAOMySQL.instanciaAlerta(msg_alerta, animalSensor, leituraPosCadastro, atvAnimal, ultCioAnimal);
                                        acessoBDAlerta.cadastrarDAO(alertaCad);
                                        infoAnimalSensor.setSob_alerta(infoAnimalSensor.getSob_alerta() + 1);
                                        acessoBDAnimalInfo.editarDAO(infoAnimalSensor);//edita-se as informações do animal a fim de atualizar o campo sob_alerta
                                        infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as informações do animal após atualização do campo sob_alerta
                                        for(Destinatario d : destinatariosAtivos){//emitir um alerta (cadastrar alerta emitido no bd) a cada destinatario ativo
                                            //alemCad = MetodosUteisDAOMySQL.instanciaAlertaEmitido(leituraPosCadastro.getData(), leituraPosCadastro.getHora(), alertaCad, d);
                                            acessoBDAlem.cadastrarDAO(alemCad);
                                        }
                                        //email.enviar(destinatariosAtivos, "SIAC BOVINO - Alerta de Cio", msg_alerta);//enviando email para os destinatarios ativos
                                        request.setAttribute("alerta_cio", msg_alerta);
                                        JOptionPane.showMessageDialog(null, msg_alerta);
                                    } else if(infoAnimalSensor.getTempo_atual_anestro().equals("MUITO LONGO")){//... e se o tempo atual em anestro está acima de 30 dias
                                        status_cio = "PREVISTO";
                                        observacao_cio = "Desvio MÉDIO da Atividade animal e Constatado período MUITO LONGO de Anestro";
                                        //cioCad = MetodosUteisDAOMySQL.instanciaCio(animalSensor, atvAnimal, leituraPosCadastro, status_cio, observacao_cio, mtd_registro_cio, mtd_identificacao_cio);
                                        acessoBDCio.cadastrarAutomaticoDAO(cioCad);
                                        ultCioAnimal = acessoBDCio.buscarUltimoRegistroDAO();
                                        /* ALTERAR TABELA LOG_CIO */
                                        msg_alerta = "Novo Cio cadastrado com o status " +status_cio+ " para o animal "+animalSensor.getCodigo()+" - " +animalSensor.getCodigo()
                                                + ".\n Hora de Cadastro: "+formatoCompletoBr.format(agora);
                                        //alertaCad = MetodosUteisDAOMySQL.instanciaAlerta(msg_alerta, animalSensor, leituraPosCadastro, atvAnimal, ultCioAnimal);
                                        acessoBDAlerta.cadastrarDAO(alertaCad);
                                        infoAnimalSensor.setSob_alerta(infoAnimalSensor.getSob_alerta() + 1);
                                        acessoBDAnimalInfo.editarDAO(infoAnimalSensor);//edita-se as informações do animal a fim de atualizar o campo sob_alerta
                                        infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as informações do animal após atualização do campo sob_alerta
                                        for(Destinatario d : destinatariosAtivos){//emitir um alerta (cadastrar alerta emitido no bd) a cada destinatario ativo
                                            //alemCad = MetodosUteisDAOMySQL.instanciaAlertaEmitido(leituraPosCadastro.getData(), leituraPosCadastro.getHora(), alertaCad, d);
                                            acessoBDAlem.cadastrarDAO(alemCad);
                                        }
                                        //email.enviar(destinatariosAtivos, "SIAC BOVINO - Alerta de Cio", msg_alerta);//enviando email para os destinatarios ativos
                                        request.setAttribute("alerta_cio", msg_alerta);
                                        JOptionPane.showMessageDialog(null, msg_alerta);
                                    }
                                } else if(infoAnimalSensor.getStatus_atividade().equals("ALTA")){//Se a variação de atividade animal é ALTA
                                    if(!infoAnimalSensor.getTempo_atual_anestro().equals("DIA DO CIO OCORRIDO") && !infoAnimalSensor.getTempo_atual_anestro().equals("CURTO")){//... e se o tempo atual em anestro é normal, longo ou muito longo
                                        status_cio = "SUSPEITO";
                                        observacao_cio = "Desvio ALTO da Atividade animal (Aumento Expressivo) e Constatado período NORMAL, LONGO ou MUITO LONGO de Anestro";
                                        //cioCad = MetodosUteisDAOMySQL.instanciaCio(animalSensor, atvAnimal, leituraPosCadastro, status_cio, observacao_cio, mtd_registro_cio, mtd_identificacao_cio);
                                        acessoBDCio.cadastrarAutomaticoDAO(cioCad);
                                        ultCioAnimal = acessoBDCio.buscarUltimoRegistroDAO();
                                        /* ALTERAR TABELA LOG_CIO */
                                        msg_alerta = "Novo Cio cadastrado com o status " +status_cio+ " para o animal "+animalSensor.getCodigo()+" - " +animalSensor.getCodigo()
                                                + ".\n Hora de Cadastro: "+formatoCompletoBr.format(agora);
                                       // alertaCad = MetodosUteisDAOMySQL.instanciaAlerta(msg_alerta, animalSensor, leituraPosCadastro, atvAnimal, ultCioAnimal);
                                        acessoBDAlerta.cadastrarDAO(alertaCad);
                                        infoAnimalSensor.setSob_alerta(infoAnimalSensor.getSob_alerta() + 1);
                                        acessoBDAnimalInfo.editarDAO(infoAnimalSensor);//edita-se as informações do animal a fim de atualizar o campo sob_alerta
                                        infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as informações do animal após atualização do campo sob_alerta
                                        for(Destinatario d : destinatariosAtivos){//emitir um alerta (cadastrar alerta emitido no bd) a cada destinatario ativo
                                            //alemCad = MetodosUteisDAOMySQL.instanciaAlertaEmitido(leituraPosCadastro.getData(), leituraPosCadastro.getHora(), alertaCad, d);
                                            acessoBDAlem.cadastrarDAO(alemCad);
                                        }
                                        //email.enviar(destinatariosAtivos, "SIAC BOVINO - Alerta de Cio", msg_alerta);//enviando email para os destinatarios ativos
                                        request.setAttribute("alerta_cio", msg_alerta);
                                        JOptionPane.showMessageDialog(null, msg_alerta);
                                    }
                                } else {//... então a variação de atividade animal é BAIXA...
                                    if(!infoAnimalSensor.getTempo_atual_anestro().equals("DIA DO CIO OCORRIDO") && infoAnimalSensor.getDt_prev_ini_prox_cio().equals(leituraPosCadastro.getData())){//... nesse caso, se dt_prev_cio == dt_atual
                                        status_cio = "PREVISTO";
                                        observacao_cio = "Desvio BAIXO da Atividade animal, mas a Data de Previsão de Cio é igual a data atual";
                                        //cioCad = MetodosUteisDAOMySQL.instanciaCio(animalSensor, atvAnimal, leituraPosCadastro, status_cio, observacao_cio, mtd_registro_cio, mtd_identificacao_cio);
                                        acessoBDCio.cadastrarAutomaticoDAO(cioCad);
                                        ultCioAnimal = acessoBDCio.buscarUltimoRegistroDAO();
                                        /* ALTERAR TABELA LOG_CIO */
                                        msg_alerta = "Novo Cio cadastrado com o status " +status_cio+ " para o animal "+animalSensor.getCodigo()+" - " +animalSensor.getCodigo()
                                                + ".\n Hora de Cadastro: "+formatoCompletoBr.format(agora);
                                        //alertaCad = MetodosUteisDAOMySQL.instanciaAlerta(msg_alerta, animalSensor, leituraPosCadastro, atvAnimal, ultCioAnimal);
                                        acessoBDAlerta.cadastrarDAO(alertaCad);
                                        infoAnimalSensor.setSob_alerta(infoAnimalSensor.getSob_alerta() + 1);
                                        acessoBDAnimalInfo.editarDAO(infoAnimalSensor);//edita-se as informações do animal a fim de atualizar o campo sob_alerta
                                        infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as informações do animal após atualização do campo sob_alerta
                                        for(Destinatario d : destinatariosAtivos){//emitir um alerta (cadastrar alerta emitido no bd) a cada destinatario ativo
                                            //alemCad = MetodosUteisDAOMySQL.instanciaAlertaEmitido(leituraPosCadastro.getData(), leituraPosCadastro.getHora(), alertaCad, d);
                                            acessoBDAlem.cadastrarDAO(alemCad);
                                        }
                                        //email.enviar(destinatariosAtivos, "SIAC BOVINO - Alerta de Cio", msg_alerta);//enviando email para os destinatarios ativos
                                        request.setAttribute("alerta_cio", msg_alerta);
                                        JOptionPane.showMessageDialog(null, msg_alerta);
                                    }
                                }
                            } else { //... então, foi cadastrado um cio para este animal nos ultimos 2 dias
                                if(ultCioAnimal.getStatus().equals("DESCARTADO")){
                                    if(infoAnimalSensor.getStatus_atividade().equals("ALTA")){//Se a variação de atividade animal é ALTA
                                        if(!infoAnimalSensor.getTempo_atual_anestro().equals("DIA DO CIO OCORRIDO") && !infoAnimalSensor.getTempo_atual_anestro().equals("CURTO")){//... e se o tempo atual em anestro é normal, longo ou muito longo
                                            status_cio = "SUSPEITO";
                                            observacao_cio = "Desvio ALTO da Atividade animal (Aumento Expressivo) e Constatado período NORMAL, LONGO ou MUITO LONGO de Anestro";
                                            //cioCad = MetodosUteisDAOMySQL.instanciaCio(animalSensor, atvAnimal, leituraPosCadastro, status_cio, observacao_cio, mtd_registro_cio, mtd_identificacao_cio);
                                            acessoBDCio.cadastrarAutomaticoDAO(cioCad);
                                            ultCioAnimal = acessoBDCio.buscarUltimoRegistroDAO();
                                            /* >>>> ALTERAR TABELA LOG_CIO */
                                            msg_alerta = "Novo Cio cadastrado com o status " +status_cio+ " para o animal "+animalSensor.getCodigo()+" - " +animalSensor.getCodigo()
                                                + ".\n Hora de Cadastro: "+formatoCompletoBr.format(agora);
                                            //alertaCad = MetodosUteisDAOMySQL.instanciaAlerta(msg_alerta, animalSensor, leituraPosCadastro, atvAnimal, ultCioAnimal);
                                            acessoBDAlerta.cadastrarDAO(alertaCad);
                                            infoAnimalSensor.setSob_alerta(infoAnimalSensor.getSob_alerta() + 1);
                                            acessoBDAnimalInfo.editarDAO(infoAnimalSensor);//edita-se as informações do animal a fim de atualizar o campo sob_alerta
                                            infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as informações do animal após atualização do campo sob_alerta
                                            for(Destinatario d : destinatariosAtivos){//emitir um alerta (cadastrar alerta emitido no bd) a cada destinatario ativo
                                                //alemCad = MetodosUteisDAOMySQL.instanciaAlertaEmitido(leituraPosCadastro.getData(), leituraPosCadastro.getHora(), alertaCad, d);
                                                acessoBDAlem.cadastrarDAO(alemCad);
                                            }
                                            //email.enviar(destinatariosAtivos, "SIAC BOVINO - Alerta de Cio", msg_alerta);//enviando email para os destinatarios ativos
                                            request.setAttribute("alerta_cio", msg_alerta);
                                            JOptionPane.showMessageDialog(null, msg_alerta);
                                        }
                                    } else {//... então a variação de atividade animal é BAIXA...
                                        if(!infoAnimalSensor.getTempo_atual_anestro().equals("DIA DO CIO OCORRIDO") && infoAnimalSensor.getDt_prev_ini_prox_cio().equals(leituraPosCadastro.getData())){//... nesse caso, se dt_prev_cio == dt_atual
                                            status_cio = "PREVISTO";
                                            observacao_cio = "Desvio BAIXO da Atividade animal, mas a Data de Previsão de Cio é igual a data atual";
                                            //cioCad = MetodosUteisDAOMySQL.instanciaCio(animalSensor, atvAnimal, leituraPosCadastro, status_cio, observacao_cio, mtd_registro_cio, mtd_identificacao_cio);
                                            acessoBDCio.cadastrarAutomaticoDAO(cioCad);
                                            ultCioAnimal = acessoBDCio.buscarUltimoRegistroDAO();
                                            /* >>>> ALTERAR TABELA LOG_CIO */
                                            msg_alerta = "Novo Cio cadastrado com o status " +status_cio+ " para o animal "+animalSensor.getCodigo()+" - " +animalSensor.getCodigo()
                                                + ".\n Hora de Cadastro: "+formatoCompletoBr.format(agora);
                                            //alertaCad = MetodosUteisDAOMySQL.instanciaAlerta(msg_alerta, animalSensor, leituraPosCadastro, atvAnimal, ultCioAnimal);
                                            acessoBDAlerta.cadastrarDAO(alertaCad);
                                            infoAnimalSensor.setSob_alerta(infoAnimalSensor.getSob_alerta() + 1);
                                            acessoBDAnimalInfo.editarDAO(infoAnimalSensor);//edita-se as informações do animal a fim de atualizar o campo sob_alerta
                                            infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as informações do animal após atualização do campo sob_alerta
                                            for(Destinatario d : destinatariosAtivos){//emitir um alerta (cadastrar alerta emitido no bd) a cada destinatario ativo
                                                //alemCad = MetodosUteisDAOMySQL.instanciaAlertaEmitido(leituraPosCadastro.getData(), leituraPosCadastro.getHora(), alertaCad, d);
                                                acessoBDAlem.cadastrarDAO(alemCad);
                                            }
                                            //email.enviar(destinatariosAtivos, "SIAC BOVINO - Alerta de Cio", msg_alerta);//enviando email para os destinatarios ativos
                                            request.setAttribute("alerta_cio", msg_alerta);
                                            JOptionPane.showMessageDialog(null, msg_alerta);
                                        }
                                    }
                                } else if(ultCioAnimal.getStatus().equals("PREVISTO")){
                                    if(infoAnimalSensor.getStatus_atividade().equals("MEDIA")){//Se a variação de atividade animal é MÉDIA
                                        if(!infoAnimalSensor.getTempo_atual_anestro().equals("DIA DO CIO OCORRIDO") && infoAnimalSensor.getDt_prev_ini_prox_cio().equals(leituraPosCadastro.getData())){//... e se dt_prev_cio == dt_atual
                                            status_cio = "SUSPEITO";
                                            observacao_cio = "Desvio MÉDIO da Atividade animal e Data de Previsão de Cio é igual a data atual";
                                            ultCioAnimal.setStatus(status_cio);
                                            ultCioAnimal.setObservacao(observacao_cio);
                                            ultCioAnimal.setData_ultima_alteracao(leituraPosCadastro.getData());
                                            ultCioAnimal.setHora_ultima_alteracao(leituraPosCadastro.getHora());
                                            acessoBDCio.editarAutomaticoDAO(ultCioAnimal);
                                            ultCioAnimal = acessoBDCio.buscarUltimoRegistroDAO();
                                            /* ALTERAR TABELA LOG_CIO */
                                            msg_alerta = "O ultimo registro de Cio estabelecido sob o nº "+ultCioAnimal.getId()+" referente ao animal "+animalSensor.getCodigo()+" - " +animalSensor.getCodigo()
                                                + " sofreu alterações.\n Hora de Atualização: "+formatoCompletoBr.format(agora);
                                            //alertaCad = MetodosUteisDAOMySQL.instanciaAlerta(msg_alerta, animalSensor, leituraPosCadastro, atvAnimal, ultCioAnimal);
                                            acessoBDAlerta.cadastrarDAO(alertaCad);
                                            infoAnimalSensor.setSob_alerta(infoAnimalSensor.getSob_alerta() + 1);
                                            acessoBDAnimalInfo.editarDAO(infoAnimalSensor);//edita-se as informações do animal a fim de atualizar o campo sob_alerta
                                            infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as informações do animal após atualização do campo sob_alerta
                                            for(Destinatario d : destinatariosAtivos){//emitir um alerta (cadastrar alerta emitido no bd) a cada destinatario ativo
                                                //alemCad = MetodosUteisDAOMySQL.instanciaAlertaEmitido(leituraPosCadastro.getData(), leituraPosCadastro.getHora(), alertaCad, d);
                                                acessoBDAlem.cadastrarDAO(alemCad);
                                            }
                                            //email.enviar(destinatariosAtivos, "SIAC BOVINO - Alerta de Cio", msg_alerta);//enviando email para os destinatarios ativos
                                            request.setAttribute("alerta_cio", msg_alerta);
                                            JOptionPane.showMessageDialog(null, msg_alerta);
                                        } else if(infoAnimalSensor.getAprox_cio() >= 8){//... e se o dia previsto do próximo cio está se aproximando
                                            status_cio = "SUSPEITO";
                                            observacao_cio = "Desvio MÉDIO da Atividade animal e VÉSPERA da Data de Previsão de Cio";
                                            ultCioAnimal.setStatus(status_cio);
                                            ultCioAnimal.setObservacao(observacao_cio);
                                            ultCioAnimal.setData_ultima_alteracao(leituraPosCadastro.getData());
                                            ultCioAnimal.setHora_ultima_alteracao(leituraPosCadastro.getHora());
                                            acessoBDCio.editarAutomaticoDAO(ultCioAnimal);
                                            ultCioAnimal = acessoBDCio.buscarUltimoRegistroDAO();
                                            /* ALTERAR TABELA LOG_CIO */
                                            msg_alerta = "O ultimo registro de Cio estabelecido sob o nº "+ultCioAnimal.getId()+" referente ao animal "+animalSensor.getCodigo()+" - " +animalSensor.getCodigo()
                                                + " sofreu alterações.\n Hora de Atualização: "+formatoCompletoBr.format(agora);
                                            //alertaCad = MetodosUteisDAOMySQL.instanciaAlerta(msg_alerta, animalSensor, leituraPosCadastro, atvAnimal, ultCioAnimal);
                                            acessoBDAlerta.cadastrarDAO(alertaCad);
                                            infoAnimalSensor.setSob_alerta(infoAnimalSensor.getSob_alerta() + 1);
                                            acessoBDAnimalInfo.editarDAO(infoAnimalSensor);//edita-se as informações do animal a fim de atualizar o campo sob_alerta
                                            infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as informações do animal após atualização do campo sob_alerta
                                            for(Destinatario d : destinatariosAtivos){//emitir um alerta (cadastrar alerta emitido no bd) a cada destinatario ativo
                                                //alemCad = MetodosUteisDAOMySQL.instanciaAlertaEmitido(leituraPosCadastro.getData(), leituraPosCadastro.getHora(), alertaCad, d);
                                                acessoBDAlem.cadastrarDAO(alemCad);
                                            }
                                            //email.enviar(destinatariosAtivos, "SIAC BOVINO - Alerta de Cio", msg_alerta);//enviando email para os destinatarios ativos
                                            request.setAttribute("alerta_cio", msg_alerta);
                                            JOptionPane.showMessageDialog(null, msg_alerta);
                                        } else if(infoAnimalSensor.getTempo_atual_anestro().equals("NORMAL")){//... e se o tempo atual em anestro está entre 16 e 24 dias
                                            status_cio = "SUSPEITO";
                                            observacao_cio = "Desvio MÉDIO da Atividade animal e ÉPOCA de Previsão de Cio";
                                            ultCioAnimal.setStatus(status_cio);
                                            ultCioAnimal.setObservacao(observacao_cio);
                                            ultCioAnimal.setData_ultima_alteracao(leituraPosCadastro.getData());
                                            ultCioAnimal.setHora_ultima_alteracao(leituraPosCadastro.getHora());
                                            acessoBDCio.editarAutomaticoDAO(ultCioAnimal);
                                            ultCioAnimal = acessoBDCio.buscarUltimoRegistroDAO();
                                            /* ALTERAR TABELA LOG_CIO */
                                            msg_alerta = "O ultimo registro de Cio estabelecido sob o nº "+ultCioAnimal.getId()+" referente ao animal "+animalSensor.getCodigo()+" - " +animalSensor.getCodigo()
                                                + " sofreu alterações.\n Hora de Atualização: "+formatoCompletoBr.format(agora);
                                            //alertaCad = MetodosUteisDAOMySQL.instanciaAlerta(msg_alerta, animalSensor, leituraPosCadastro, atvAnimal, ultCioAnimal);
                                            acessoBDAlerta.cadastrarDAO(alertaCad);
                                            infoAnimalSensor.setSob_alerta(infoAnimalSensor.getSob_alerta() + 1);
                                            acessoBDAnimalInfo.editarDAO(infoAnimalSensor);//edita-se as informações do animal a fim de atualizar o campo sob_alerta
                                            infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as informações do animal após atualização do campo sob_alerta
                                            //email.enviar(destinatariosAtivos, "SIAC BOVINO - Alerta de Cio", msg_alerta);//enviando email para os destinatarios ativos
                                            for(Destinatario d : destinatariosAtivos){//emitir um alerta (cadastrar alerta emitido no bd) a cada destinatario ativo
                                                //alemCad = MetodosUteisDAOMySQL.instanciaAlertaEmitido(leituraPosCadastro.getData(), leituraPosCadastro.getHora(), alertaCad, d);
                                                acessoBDAlem.cadastrarDAO(alemCad);
                                            }
                                            request.setAttribute("alerta_cio", msg_alerta);
                                            JOptionPane.showMessageDialog(null, msg_alerta);
                                        } 
                                    } else if(infoAnimalSensor.getStatus_atividade().equals("ALTA")){//Se a variação de atividade animal é ALTA
                                        if(!infoAnimalSensor.getTempo_atual_anestro().equals("DIA DO CIO OCORRIDO") && !infoAnimalSensor.getTempo_atual_anestro().equals("CURTO")){//... e se o tempo atual em anestro é normal, longo ou muito longo
                                            status_cio = "SUSPEITO";
                                            observacao_cio = "Desvio ALTO da Atividade animal (Aumento Expressivo) e Constatado período NORMAL, LONGO ou MUITO LONGO de Anestro";
                                            ultCioAnimal.setStatus(status_cio);
                                            ultCioAnimal.setObservacao(observacao_cio);
                                            ultCioAnimal.setData_ultima_alteracao(leituraPosCadastro.getData());
                                            ultCioAnimal.setHora_ultima_alteracao(leituraPosCadastro.getHora());
                                            acessoBDCio.editarAutomaticoDAO(ultCioAnimal);
                                            ultCioAnimal = acessoBDCio.buscarUltimoRegistroDAO();
                                            /* ALTERAR TABELA LOG_CIO */
                                            msg_alerta = "O ultimo registro de Cio estabelecido sob o nº "+ultCioAnimal.getId()+" referente ao animal "+animalSensor.getCodigo()+" - " +animalSensor.getCodigo()
                                                + " sofreu alterações.\n Hora de Atualização: "+formatoCompletoBr.format(agora);
                                            //alertaCad = MetodosUteisDAOMySQL.instanciaAlerta(msg_alerta, animalSensor, leituraPosCadastro, atvAnimal, ultCioAnimal);
                                            acessoBDAlerta.cadastrarDAO(alertaCad);
                                            infoAnimalSensor.setSob_alerta(infoAnimalSensor.getSob_alerta() + 1);
                                            acessoBDAnimalInfo.editarDAO(infoAnimalSensor);//edita-se as informações do animal a fim de atualizar o campo sob_alerta
                                            infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as informações do animal após atualização do campo sob_alerta
                                            //email.enviar(destinatariosAtivos, "SIAC BOVINO - Alerta de Cio", msg_alerta);//enviando email para os destinatarios ativos
                                            for(Destinatario d : destinatariosAtivos){//emitir um alerta (cadastrar alerta emitido no bd) a cada destinatario ativo
                                                //alemCad = MetodosUteisDAOMySQL.instanciaAlertaEmitido(leituraPosCadastro.getData(), leituraPosCadastro.getHora(), alertaCad, d);
                                                acessoBDAlem.cadastrarDAO(alemCad);
                                            }
                                            request.setAttribute("alerta_cio", msg_alerta);
                                            JOptionPane.showMessageDialog(null, msg_alerta);
                                        }
                                    }
                                } else if(ultCioAnimal.getStatus().equals("SUSPEITO")){
                                    infoAnimalSensor.setSob_alerta(infoAnimalSensor.getSob_alerta() + 1);
                                    acessoBDAnimalInfo.editarDAO(infoAnimalSensor);//edita-se as informações do animal a fim de atualizar o campo sob_alerta
                                    infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as informações do animal após atualização do campo sob_alerta  
                                    alertaCad = acessoBDAlerta.buscarUltimoDoAnimal(animalSensor.getId());
                                    for(Destinatario d : destinatariosAtivos){//emitir um alerta (cadastrar alerta emitido no bd) a cada destinatario ativo
                                        //alemCad = MetodosUteisDAOMySQL.instanciaAlertaEmitido(leituraPosCadastro.getData(), leituraPosCadastro.getHora(), alertaCad, d);
                                        acessoBDAlem.cadastrarDAO(alemCad);
                                    }
                                    //email.enviar(destinatariosAtivos, "SIAC BOVINO - Alerta de Cio", alertaCad.getMsg());//enviando email para os destinatarios ativos
                                    //JOptionPane.showMessageDialog(null, alertaCad.getMsg());
                                } 
                            }  
                        } else {//... então o animal ja está em cio.
                            System.out.println(">> O animal "+animalSensor.getCodigo()+" - " +animalSensor.getCodigo()+" referenciado pela Leitura nº "+ leitura.getnLeitura() +" do sensor "+ leitura.getCodigo_sensor() +" está em CIO Atualmente.");
                            //break;
                        }
/* FIM DO CÓDIGO DE INFERÊNCIA SOBRE O CIO */
                    } else {//... então a leitura já foi cadastrada.
                        System.out.println(">> A Leitura nº "+ leitura.getnLeitura() +" do sensor "+ leitura.getCodigo_sensor() +" já foi cadastrada anteriormente");
                        //break;
                    }
                } else {//... então o sensor não existe ou não está com o monitoramento ativo
                    System.out.println("\n>> O Sensor "+leitura.getSensor().getCodigo()+" com leitura nº "+ leitura.getnLeitura() +" não está cadastrado ou está inativo.");
                    //break;
                } 
            }
            
        }// FIM do FOR que percorre o vetor com as informações de cada leitura(linha do arquivo) a ser cadastrada
        SimpleDateFormat formatoAgora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date agorinha = new java.util.Date();
        System.out.println("\n>>> "+formatoAgora.format(agorinha)+" - CONSULTA AO MIDDLEWARE COMPLETADA.\n");
        if(cont != 0){//se existiam linhas a ser cadastradas
            try {/*INICIO: INSERINDO PONTO DE PARADA DE GRAVAÇÃO NO ARQUIVO*/
                FileWriter fw = new FileWriter(url_arquivo, true);
                PrintWriter pw = new PrintWriter(fw);
                pw.print("\n********* REGISTROS GRAVADOS **********");
                pw.flush();
                pw.close();
            } catch (IOException ex) {
                Logger.getLogger(ControleMetodosBCC.class.getName()).log(Level.SEVERE, null, ex);
            }/*FIM: INSERINDO PONTO DE PARADA DE GRAVAÇÃO NO ARQUIVO*/
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


/*  QDO O CIO JÁ FOI CONFIRMADO:
    zerar o info_sob_alerta;
    continuar atualizando a atividade animal
    detectar a redução das passadas e INTERROMPER cio se for o caso (Atualizar tabelas cio, info_animal, log_cio)
    Atualizar a data e hora de previsão do prox cio e zerar o info_dias_em_anestro
    QDO O CIO AINDA NÃO FOI CONFIRMADO:
    incrementar o info_sob_alerta;
    continuar atualizando a atividade animal
    detectar a redução das passadas e CANCELAR cio se for o caso (Atualizar tabelas cio, info_animal, log_cio)
    Atualizar a data e hora de previsão do prox cio e zerar o info_dias_em_anestro 
    
    if(infoAnimalSensor.getSob_alerta() == 0){//... e se o animal não provocou nenhum alerta atualmente
    } else {//... então o animal já está sob alerta e o cio não foi constatado pelo usuário
        cioCad = acessoBDCio.buscarUltimoDoAnimalDAO(animalSensor.getId());
        GregorianCalendar dataUltCioAnimal = new GregorianCalendar();
        dataUltCioAnimal.setTime(formatoCompletoUSA.parse(ultCioAnimal.getData_registro() + " " + ultCioAnimal.getHora_registro()));
        long milissegundos2 = dataAtual.getTimeInMillis() - dataUltCioAnimal.getTimeInMillis();
        int diasCadUltCioAni = (int) (milissegundos2/(24*60*60*1000));
        int horasCadIltCioAni = (int) (milissegundos2/(60*60*1000));
        if(!cioCad.getStatus().equals("PREVISTO") && !cioCad.getStatus().equals("SUSPEITO")){//se o cio gerado para esse animal ja foi avaliado, senão...
            //
            //se o alerta foi emitido a mais de uma hora e a menos de 48 horas
            //então... reemitir alerta nos dois casos alterando a mensagem (Ainda não foi avaliado ou algo assim
        }
    } */
    
    
    
    