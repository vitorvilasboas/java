package br.edu.cio.controller;

import br.edu.cio.model.Alerta;
import br.edu.cio.model.AlertaEmitido;
import br.edu.cio.model.Animal;
import br.edu.cio.model.AnimalInfo;
import br.edu.cio.model.AtividadeAnimal;
import br.edu.cio.model.Cio;
import br.edu.cio.model.Configuracao;
import br.edu.cio.model.Destinatario;
import br.edu.cio.model.Funcionario;
import br.edu.cio.model.Leitura;
import br.edu.cio.model.LogCio;
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
import java.util.Random;

public class ControleMetodosBCC implements ControleMetodosComunsServicos{
    
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
    
    SimpleDateFormat formatoDataBr = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatoHoraBr = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatoCompletoBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    SimpleDateFormat formatoCompletoUSA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public static Cio instanciaCio(Animal animal, AtividadeAnimal atv, Leitura leitura, String status, String obs, String metodo_reg, String metodo_ident){
        SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoDataBr = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHoraBr = new SimpleDateFormat("HH:mm:ss");
        java.util.Date agora = new java.util.Date();//capturando data e hora atual do sistema
        Cio cio = new Cio();
        cio.setAnimal(animal);
        cio.setAtividadeAnimal(atv);
        cio.setLeitura(leitura);
        cio.setObservacao(obs);
        cio.setStatus(status);
        cio.setData_registro(leitura.getData());
        cio.setHora_registro(leitura.getHora());
        cio.setData_ultima_alteracao(formatoDataUSA.format(agora));//ativar em ambiente real
        cio.setHora_ultima_alteracao(formatoHoraBr.format(agora));//ativar em ambiente real
        cio.setMetodo_registro(metodo_reg);
        cio.setMetodo_id(metodo_ident);
        
        Random r1 = new Random();
        Random r2 = new Random();
        int bloco1 = r1.nextInt((999 - 0)) + 0;
        int bloco2 = r1.nextInt((99 - 0)) + 0;
        String codigo_cio = "9" + String.valueOf(bloco1) + String.valueOf(animal.getId()) + String.valueOf(bloco2);//Inicia com: 9 - Cadastro Automático, 1 - Cadastro Manual
        cio.setCodigo(codigo_cio);
        return cio;
    }
    
    public static Alerta instanciaAlerta(String msg, Animal animal, Leitura leitura, AtividadeAnimal atv, Cio cio){
        Alerta novoAlerta = new Alerta();
        //int num = Integer.parseInt(String.valueOf(animal.getId()) + String.valueOf(atv.getId()) + String.valueOf(leitura.getId()) + String.valueOf(cio.getId()));
        int num = Integer.parseInt(String.valueOf(animal.getId()) + String.valueOf(cio.getId()));
        novoAlerta.setMsg(msg);
        novoAlerta.setNumero(num);
        novoAlerta.setAnimal(animal);
        novoAlerta.setLeitura(leitura);
        novoAlerta.setAtv_animal(atv);
        novoAlerta.setCio(cio);
        return novoAlerta;
    }
    
    public AlertaEmitido instanciaAlertaEmitido(String data, String hora, Alerta alerta, Destinatario destinatario){
        AlertaEmitido novoAlerta = new AlertaEmitido();
        novoAlerta.setData(data);
        novoAlerta.setHora(hora);
        novoAlerta.setAlerta(alerta);
        novoAlerta.setDestinatario(destinatario);
        return novoAlerta;
    }
    
    public static LogCio instanciaLogCio(String data, String hora, Cio cio, Funcionario func, String descricao, String operacao){
        LogCio lc = new LogCio();
        lc.setData(data);
        lc.setHora(hora);
        lc.setDescricao(descricao);
        lc.setOperacao(operacao);
        lc.setFuncionario(func);
        lc.setCio(cio);
        return lc;
    }
    
    public void emitirAlerta(String data, String hora, Alerta alertaCad, String msg_alerta, HttpServletRequest request){
        /* Preparando lista de destinatários */
        List<Destinatario> destinatarios = acessoBDDestinatario.buscarDAO("");
        List<Destinatario> destinatariosAtivos = new ArrayList<>();
        for(Destinatario d : destinatarios){
            if(d.getAtivo() == 1){
                destinatariosAtivos.add(d);
            }
        }
        for(Destinatario d : destinatariosAtivos){//emitir um alerta (cadastrar alerta emitido no bd) a cada destinatario ativo
            AlertaEmitido alemCad = instanciaAlertaEmitido(data, hora, alertaCad, d);
            acessoBDAlem.cadastrarDAO(alemCad);
            
        }
        
        Celular.simple(destinatariosAtivos, msg_alerta);
        Email.enviar(destinatariosAtivos, "SIAC Bovino Alerta", msg_alerta);//enviando email para os destinatarios ativos
        JOptionPane.showMessageDialog(null, msg_alerta);
        request.setAttribute("alerta_cio", msg_alerta);//mostra na tela o alerta
    }
    
    public void decretaCio(Animal animalSensor, AtividadeAnimal atvAnimal, Leitura leituraPosCadastro, AnimalInfo infoAnimalSensor, String status_cio, String observacao_cio, HttpServletRequest request){
        String mtd_registro_cio = "AUTOMATICO";
        String mtd_identificacao_cio = "INFERENCIA";
        String msg_alerta = "NOVO CICLO PERCEBIDO: Ciclo Estral cadastrado com o status " +status_cio+ " para o animal "+animalSensor.getCodigo()+" - " +animalSensor.getApelido()
                    + ". Proceda com a Avaliacao.\n Hora de Cadastro: "+leituraPosCadastro.getData()+" "+leituraPosCadastro.getHora();
        
        Cio cioCad = instanciaCio(animalSensor, atvAnimal, leituraPosCadastro, status_cio, observacao_cio, mtd_registro_cio, mtd_identificacao_cio);
              
        acessoBDCio.cadastrarAutomaticoDAO(cioCad);//cadastra cio no BD
        Cio ultCioAnimal = acessoBDCio.buscarUltimoRegistroDAO();//recupera o cio recém cadastrado
        
        LogCio lc = instanciaLogCio(leituraPosCadastro.getData(), leituraPosCadastro.getHora(), ultCioAnimal, null, "Cadastro de um novo cio por inferência automática", "CADASTRO");
        acessoBDCio.inserirLogDAO(lc);
        
        Alerta alertaCad = instanciaAlerta(msg_alerta, animalSensor, leituraPosCadastro, atvAnimal, ultCioAnimal);
        acessoBDAlerta.cadastrarDAO(alertaCad);//cadastra alerta no BD
        alertaCad = acessoBDAlerta.buscarUltimoDoAnimal(animalSensor.getId());

        infoAnimalSensor.setSob_alerta(infoAnimalSensor.getSob_alerta() + 1);
        //acessoBDAnimalInfo.editarDAO(infoAnimalSensor);
        acessoBDAnimalInfo.atualizarSobAlertaDAO(infoAnimalSensor);//edita-se as informações do animal a fim de atualizar o campo sob_alerta
        infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as informações do animal após atualização do campo sob_alerta

        emitirAlerta(leituraPosCadastro.getData(), leituraPosCadastro.getHora(), alertaCad, msg_alerta, request);//invoka o método que emita o alerta
    }
    
    public void atualizaDecretoCio(Cio ultCioAnimal, Animal animalSensor, AtividadeAnimal atvAnimal, Leitura leituraPosCadastro, AnimalInfo infoAnimalSensor, String status_cio, String observacao_cio, HttpServletRequest request){
        String mtd_registro_cio = "AUTOMATICO";
        String mtd_identificacao_cio = "INFERENCIA";
        String msg_alerta = "";
        try {
            msg_alerta = "ALTERACAO NO CICLO: Ciclo Estral com Avaliacao Pendente - O ultimo registro de Cio estabelecido sob o codigo "+ultCioAnimal.getCodigo()+" referente ao animal "+animalSensor.getCodigo()+" - " +animalSensor.getApelido()
                    + " sofreu alteracoes.\n Hora de Atualizacao: "+formatoCompletoBr.format(formatoCompletoBr.parse(leituraPosCadastro.getData()+" "+leituraPosCadastro.getHora()));
        } catch (ParseException ex) {
            Logger.getLogger(ControleMetodosBCC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ultCioAnimal.setStatus(status_cio);
        ultCioAnimal.setObservacao(observacao_cio);
        ultCioAnimal.setData_ultima_alteracao(leituraPosCadastro.getData());
        ultCioAnimal.setHora_ultima_alteracao(leituraPosCadastro.getHora());
        
        acessoBDCio.editarAutomaticoDAO(ultCioAnimal);
        ultCioAnimal = acessoBDCio.buscarUltimoRegistroDAO();
        
        LogCio lc = instanciaLogCio(leituraPosCadastro.getData(), leituraPosCadastro.getHora(), ultCioAnimal, null, "Atualização do status de um cio pré-cadastrado", "ATUALIZACAO");
        acessoBDCio.inserirLogDAO(lc);

        Alerta alertaCad = instanciaAlerta(msg_alerta, animalSensor, leituraPosCadastro, atvAnimal, ultCioAnimal);
        acessoBDAlerta.cadastrarDAO(alertaCad);
        alertaCad = acessoBDAlerta.buscarUltimoDoAnimal(animalSensor.getId());

        infoAnimalSensor.setSob_alerta(infoAnimalSensor.getSob_alerta() + 1);
        //acessoBDAnimalInfo.editarDAO(infoAnimalSensor);
        acessoBDAnimalInfo.atualizarSobAlertaDAO(infoAnimalSensor);//edita-se as informações do animal a fim de atualizar o campo sob_alerta
        infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as informações do animal após atualização do campo sob_alerta
        
        emitirAlerta(leituraPosCadastro.getData(), leituraPosCadastro.getHora(), alertaCad, msg_alerta, request);//invoka o método que emita o alerta
        //request.setAttribute("alerta_cio", msg_alerta);//mostra na tela o alerta
    }
    
    public void encerrarCio(Cio cio, Animal animalSensor, AnimalInfo infosAnimal, Leitura leitura, AtividadeAnimal atvAnimal, String dtAlter, String hrAlter, HttpServletRequest request){
        try {
            
            GregorianCalendar dthInicioCio = new GregorianCalendar();
            GregorianCalendar dthFimCio = new GregorianCalendar();
            GregorianCalendar dthPrevIniProxCio = new GregorianCalendar();
            dthInicioCio.setTime(formatoCompletoBr.parse(cio.getData_inicio()+ " " + cio.getHora_inicio()));
            dthFimCio.setTime(formatoCompletoBr.parse(formatoCompletoBr.format(formatoCompletoUSA.parse(dtAlter+ " " + hrAlter))));
            long dif_ms = dthFimCio.getTimeInMillis() - dthInicioCio.getTimeInMillis();
            int duracao_cio = (int) (dif_ms/(60*60*1000));
            if(duracao_cio >= 40){
                duracao_cio = 36;
            }
            dtAlter = formatoDataBr.format(formatoDataUSA.parse(dtAlter));
            cio.setData_termino(dtAlter);
            cio.setHora_termino(hrAlter);
            cio.setData_ultima_alteracao(dtAlter);
            cio.setHora_ultima_alteracao(hrAlter);
            cio.setDuracao(duracao_cio);
            cio.setStatus("ENCERRADO");
            acessoBDCio.editarDAO(cio);
            cio = acessoBDCio.buscarPorIdDAO(cio.getId());
            
            LogCio lc = instanciaLogCio(dtAlter, hrAlter, cio, null, "Encerramento automático de cio ", "ENCERRAMENTO");
            acessoBDCio.inserirLogDAO(lc);
            
            infosAnimal.setDuracao_media_cio((infosAnimal.getDuracao_media_cio() + duracao_cio)/2);
            infosAnimal.setStatus_cio(0);//obrigatório - muda o status do animal para "fora do cio"
            infosAnimal.setDias_em_anestro(0);//obrigatório
            infosAnimal.setSob_alerta(0);//opcional
            infosAnimal.setAprox_cio(0);//opcional
            infosAnimal.setTempo_atual_anestro("PRINCIPIO");//obrigatório
            infosAnimal.setDt_fim_ult_cio(dtAlter);
            infosAnimal.setHr_fim_ult_cio(hrAlter);
            dthPrevIniProxCio.setTime(formatoCompletoBr.parse(cio.getData_termino()+ " " + cio.getHora_termino()));
            dthPrevIniProxCio.add(Calendar.HOUR, 24*infosAnimal.getIntervalo_padrao_anestro());/* SOMANDO */
            formatoDataBr.setCalendar(dthPrevIniProxCio);
            infosAnimal.setDt_prev_ini_prox_cio(formatoDataBr.format(dthPrevIniProxCio.getTime()));//obrigatório
            infosAnimal.setHr_prev_ini_prox_cio(formatoHoraBr.format(dthPrevIniProxCio.getTime()));//obrigatório
            acessoBDAnimalInfo.editarDAO(infosAnimal);
            
            acessoBDCio.inserirPrevisaoCio(infosAnimal.getDt_prev_ini_prox_cio(), infosAnimal.getHr_prev_ini_prox_cio(), animalSensor.getId());
            
            String msg_alerta = "ENCERRAMENTO AUTOMÁTICO DE CICLO: O ultimo registro de Cio estabelecido sob o nº "+cio.getId()+" referente ao animal "+cio.getAnimal().getCodigo()+" - " +cio.getAnimal().getApelido()
                      + " foi ENCERRADO.\n Hora de Atualização: "+(dtAlter+" "+hrAlter);
            
            Alerta alertaCad = instanciaAlerta(msg_alerta, cio.getAnimal(), leitura, atvAnimal, cio);
            acessoBDAlerta.cadastrarDAO(alertaCad);
            alertaCad = acessoBDAlerta.buscarUltimoDoAnimal(animalSensor.getId());
            
            emitirAlerta(dtAlter, hrAlter, alertaCad, msg_alerta, request);//invoka o método que emita o alerta
            //request.setAttribute("alerta_cio", msg_alerta);//mostra na tela o alerta
            
        } catch (ParseException ex) {
            Logger.getLogger(ControleMetodosBCC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void atualizarManual(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, Exception {
        atualizarAuto(request, response);
        request.setAttribute("mensagem", "Registros Atualizados com sucesso");
        request.getRequestDispatcher("principal?d=&a=&f=home").forward(request, response);//retorna para a principal apresentando o erro
        //response.sendRedirect("principal?d=&a=&f=home");
    }
    
    public void atualizarAuto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {//grava cada linha do arquivo de simulação no BD
        HttpSession sessao = request.getSession();   
        /* Atualiza a hora do sistema */
        java.util.Date agora = new java.util.Date();//capturando data e hora atual do sistema
        Configuracao config = acessoBDConfig.carregarDAO(1);//(Configuracao) sessao.getAttribute("conf");//recupera configuração atual da sessão
        config.setData_atual(formatoDataBr.format(agora));
        config.setHora_atual(formatoHoraBr.format(agora));
        config.setDt_ult_captura(formatoDataUSA.format(formatoDataBr.parse(config.getDt_ult_captura())));
        acessoBDConfig.atualizarDAO(config);
        config = acessoBDConfig.carregarDAO(config.getId());
        
        /* Atualiza as informações dos animais monitorados na tabela infoAnimal*/
        List<Animal> animais = acessoBDAnimal.buscarDAO("");
        for(Animal a : animais){
            if(a.getMonitorando() == 1){
                AnimalInfo ai = acessoBDAnimalInfo.buscarPorIdAnimalDAO(a.getId());
                acessoBDAnimalInfo.atualizarDAO(ai, config);               
            }   
        }
        
        /*  LEITURA DOS DADOS NO ARQUIVO DE MIDLLEWARE  */
        String url_arquivo = "C:\\Users\\JV\\Documents\\NetBeansProjects\\siac\\regsensores\\regs.txt";
        File arquivo = new File(url_arquivo);
        List<String> registrosArquivoTemp = new ArrayList<>();
        List<String> registrosArquivo = new ArrayList<>();
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
        Collections.reverse(registrosArquivo);//inverte array OFICIAL
        int cont=0;//contador de linhas a cadastrar -> usado para limitar em 1 o número de msgs de "REGISTROS GRAVADOS" é escrito no arquivo em sequência
        for(String linhaRegistro : registrosArquivo){//percorre o array até o fim e armazena cada registro temporariamente na string linhaRegistro
            cont++;
            if(!linhaRegistro.isEmpty()){//verifica se a linha não está em branco
                String dadosLinha[];//vetor de String usado para separar os dados de cada linha do arquivo
                dadosLinha=linhaRegistro.split("&");//separa a linha a cada vez que o delimitador (&) é encontrado e armazena temporariamente cada atributo em uma posição do vetor de string dadosLinha
                String dataLeitura = "";
                String horaLeitura = "";
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
                Leitura leitura = new Leitura();//usada para instanciar os atributos de cada linha do arquivo
                leitura.setCodigo_central(dadosLinha[0]);
                leitura.setCodigo_sensor(dadosLinha[1]);
                leitura.setnPassadas(Integer.parseInt(dadosLinha[4]));
                leitura.setnLeitura(Integer.parseInt(dadosLinha[5]));
                leitura.setData(dataLeitura);
                leitura.setHora(horaLeitura);
                leitura.setIntervalo_config(config.getIntervalo_entre_leituras());
                leitura.setSensor(acessoBDSensor.buscarPorCodigoDAO(leitura.getCodigo_sensor()));//busca o sensor da leitura pelo código
                leitura.setCentral(acessoBDCentral.buscarPorCodigoDAO(leitura.getCodigo_central()));//busca a central da leitura pelo código
        /* CADASTRAMENTO DE LEITURAS NA BASE DE CONHECIMENTO */
                if((leitura.getSensor() != null) && (leitura.getSensor().getAtivo() == 1)){//verifica se o sensor existe e se está ativo
                    Leitura leituraCadastrada = acessoBD.buscarDAO(leitura);//verifica no banco se o registro/leitura já foi cadastrado
                    if(leituraCadastrada == null){// se a leitura ainda não foi cadastrada no banco...
                        acessoBD.inserirDAO(leitura);// insere leitura no banco
                        Leitura leituraPosCadastro = acessoBD.buscarDAO(leitura);//recupera leitura recem cadastrada, através da data, hora e sensor
                        //Atualiza a data e hora da ultima leitura nas configuracoes
                        config = acessoBDConfig.carregarDAO(config.getId());
                        config.setDt_ult_captura(formatoDataUSA.format(formatoDataBr.parse(leituraPosCadastro.getData())));
                        config.setHr_ult_captura(leituraPosCadastro.getHora());
                        acessoBDConfig.atualizarDAO(config);
                        //Depois... atualiza a data de referencia para a inferencia como dataBase
                        GregorianCalendar dthBase = new GregorianCalendar();
                        java.util.Date dthUltCaptura = formatoCompletoUSA.parse(config.getDt_ult_captura()+ " " + config.getHr_ult_captura());
                        dthBase.setTime(dthUltCaptura);
        /* CLASSIFICAÇÃO DO NÍVEL DE ATIVIDADE ANIMAL */                    
                        Animal animalSensor = acessoBDAnimal.buscarPorIdSensorDAO(leituraPosCadastro.getSensor().getId());//recupera o animal da leitura recem cadastrada a partir do id do sensor
                        AnimalInfo infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//recupera as informações do animal
                        int variacao = (leituraPosCadastro.getnPassadas()/config.getIntervalo_entre_leituras()) - infoAnimalSensor.getMedia_passos_hora(); //calcula a variação de passadas com base em seu padrão
                        double perc_variacao = variacao * 100 / infoAnimalSensor.getMedia_passos_hora(); //calcula o percentual de variação/desvio do padrão de passadas
                        String classificacao = null;
                        if( ((int) perc_variacao) < config.getPerc_min_atv_media() ){
                            classificacao = "BAIXA";
                        } else if ( ( ((int) perc_variacao) >= config.getPerc_min_atv_media() ) && ( ((int) perc_variacao) < config.getPerc_min_atv_alta() ) ) {
                            classificacao = "MEDIA";
                        } else {
                            classificacao = "ALTA";
                        }
                        /* ATUALIZAÇÃO DA MÉDIA DE PASSOS POR HORA na tabela info_animal */
                        List<Leitura> leiturasDoAnimal = acessoBD.listarPorIdSensor(leituraPosCadastro.getSensor().getId());
                        int somaPassadasHora = 0;
                        int qtdLeituras = 0;
                        for(Leitura le : leiturasDoAnimal){
                            somaPassadasHora += (int)(le.getnPassadas() / config.getIntervalo_entre_leituras());
                            qtdLeituras += 1;
                        }
                        int media_passos_hora = (int) (somaPassadasHora / qtdLeituras);
                        infoAnimalSensor.setMedia_passos_hora(media_passos_hora);
                        acessoBDAnimalInfo.atualizarMediaPassosHoraDAO(infoAnimalSensor);//edita-se as informações do animal a fim de atualizar o campo media_passos_hora
                        infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());
                        
                        acessoBDmic.classificarAtividadeAnimalDAO(leituraPosCadastro, animalSensor, perc_variacao, variacao, classificacao);//cadastra a classificação da atividade do animal no BD
                        AtividadeAnimal atvAnimal = acessoBDmic.buscarAtividadeAnimalPorIdLeituraDAO(leituraPosCadastro.getId());//recupera registro de atividade animal recém classificada e cadastrado
        /* VERIFICAÇÃO E ATUALIZAÇÃO DO PERIODO ATUAL DE ANESTRO EM DIAS */ 
                        infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as Informações do animal após a classificação da atividade
                        java.util.Date tempoLeituraPosCadastro = formatoCompletoBr.parse(leituraPosCadastro.getData() + " " + leituraPosCadastro.getHora());
                        java.util.Date tempoFimUltimoCio = formatoCompletoBr.parse(infoAnimalSensor.getDt_fim_ult_cio() + " " + infoAnimalSensor.getHr_fim_ult_cio());
                        GregorianCalendar dthLeituraPosCad = new GregorianCalendar(); 
                        GregorianCalendar data2 = new GregorianCalendar();
                        dthLeituraPosCad.setTime(tempoLeituraPosCadastro);  
                        data2.setTime(tempoFimUltimoCio);
                        long milissegundos = dthLeituraPosCad.getTimeInMillis() - data2.getTimeInMillis();
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
        /* INFERÊNCIA SOBRE O CIO -- TOMADA DE DECISÃO -- CADASTRAMENTO DO CIO -- CADASTRO E EMISSÃO DE ALERTA  */
                        String tempo_anestro = infoAnimalSensor.getTempo_atual_anestro();
                        String variacao_movimentacao = infoAnimalSensor.getStatus_atividade();
                        if(infoAnimalSensor.getStatus_cio() == 0){//Se o animal não está em cio..
                            Cio ultCioAnimal = acessoBDCio.buscarUltimoDoAnimalDAO(animalSensor.getId());//captura o ultimo cio cadastrado relacionado ao animal
                            int diasCadUltCioAni = 999999999;
                            GregorianCalendar dataUltCioAnimal = new GregorianCalendar();
                            if(ultCioAnimal != null){//se já foi cadastrado algum cio para o animal...
                                dataUltCioAnimal.setTime(formatoCompletoBr.parse(ultCioAnimal.getData_registro() + " " + ultCioAnimal.getHora_registro()));
                                long milissegundos2 = dthBase.getTimeInMillis() - dataUltCioAnimal.getTimeInMillis();
                                diasCadUltCioAni = (int) (milissegundos2/(24*60*60*1000));
                            } else {}//... então nunca foi cadastrado nenhum cio para o animal 
                            //Se não houver cios em aberto recentes (2 dias) ou se houver (mas tiverem com status DESCARTADO, CANCELADO OU ENCERRADO
                            if ( diasCadUltCioAni > 2 || ( diasCadUltCioAni <= 2 && (ultCioAnimal.getStatus().equals("DESCARTADO") || ultCioAnimal.getStatus().equals("CANCELADO") ) ) ){ //... e ainda se nenhum cio foi cadastrado para o animal nos ultimos 2 dias
                               if( variacao_movimentacao.equals("MEDIA") || variacao_movimentacao.equals("ALTA") ){
                                    if(tempo_anestro.equals("NORMAL") || tempo_anestro.equals("LONGO")){
                                        String observacao_cio = "Constatado desvio no padrão de movimentação do animal que indica o estado de cio; O Tempo em anestro é considerado NORMAL ou LONGO.";
                                        decretaCio(animalSensor, atvAnimal, leituraPosCadastro, infoAnimalSensor, "SUSPEITO", observacao_cio, request);
                                    } else if ( tempo_anestro.equals("CURTO") ) {//SE O ANESTRO É CURTO ... Pode ser q seja a data prevista ou a data está chegando
                                        if( infoAnimalSensor.getDt_prev_ini_prox_cio().equals(leituraPosCadastro.getData()) || infoAnimalSensor.getAprox_cio() >= 8 ){
                                            String observacao_cio = "Constatado desvio no padrão de movimentação do animal que indica o estado de cio; "
                                                    + "O Tempo em anestro é considerado CURTO; Chegada a ÉPOCA PREVISTA para que o animal entre em estado de cio. ";
                                            decretaCio(animalSensor, atvAnimal, leituraPosCadastro, infoAnimalSensor, "PREVISTO", observacao_cio, request);
                                        }
                                    } else if( tempo_anestro.equals("MUITO LONGO") ){//então anestro é "MUITO LONGO"
                                        String observacao_cio = "Constatado desvio no padrão de movimentação do animal que indica o estado de cio; O tempo em anestro é considerado MUITO LONGO.";
                                        decretaCio(animalSensor, atvAnimal, leituraPosCadastro, infoAnimalSensor, "PREVISTO", observacao_cio, request);
                                    }   
                                } else {//... então a variação de atividade animal é BAIXA...
                                    if( !tempo_anestro.equals("PRINCIPIO") ){
                                        if(infoAnimalSensor.getDt_prev_ini_prox_cio().equals(leituraPosCadastro.getData())){//... nesse caso, se dt_prev_cio == dt_atual
                                            String observacao_cio = "Chegada a DATA PREVISTA para que o animal entre em estado de cio, apesar de não haver Desvio no padrão de movimentação do animal.";
                                            decretaCio(animalSensor, atvAnimal, leituraPosCadastro, infoAnimalSensor, "PREVISTO", observacao_cio, request);
                                        }
                                    } 
                                }
                            } else { //... então, foi cadastrado um cio para este animal nos ultimos 2 dias
                                if(ultCioAnimal.getStatus().equals("PREVISTO")){
                                    if( variacao_movimentacao.equals("MEDIA") || variacao_movimentacao.equals("ALTA") ){
                                        if(tempo_anestro.equals("NORMAL") || tempo_anestro.equals("LONGO") ){//... e se o tempo atual em anestro está entre 16 e 24 dias
                                            String observacao_cio = "Constatado desvio no padrão de movimentação do animal que indica o estado de cio; O Tempo em anestro é considerado NORMAL ou LONGO.";
                                            atualizaDecretoCio(ultCioAnimal, animalSensor, atvAnimal, leituraPosCadastro, infoAnimalSensor, "SUSPEITO", observacao_cio, request);
                                        }
                                    }
                                } else if(ultCioAnimal.getStatus().equals("SUSPEITO")){
                                    if(infoAnimalSensor.getSob_alerta() <= config.getQtd_alertas()){//Cessar os alertas no limite estabelecido nas Configurações
                                        infoAnimalSensor.setSob_alerta(infoAnimalSensor.getSob_alerta() + 1);
                                        acessoBDAnimalInfo.atualizarSobAlertaDAO(infoAnimalSensor);//edita-se as informações do animal a fim de atualizar o campo sob_alerta
                                        //infoAnimalSensor = acessoBDAnimalInfo.buscarPorIdAnimalDAO(animalSensor.getId());//atualiza as informações do animal após atualização do campo sob_alerta  
                                        Alerta alertaCad = acessoBDAlerta.buscarUltimoDoAnimal(animalSensor.getId());
                                        alertaCad.setMsg("REEMISSÃO DE ALERTA: "+alertaCad.getMsg());
                                        emitirAlerta(leituraPosCadastro.getData(), leitura.getHora(), alertaCad, alertaCad.getMsg(),request);//reemite o último alerta
                                    } 
                                } 
                            }  
                        } else {//... então o animal ja está em cio.
                            System.out.println(">> O animal "+animalSensor.getCodigo()+" - " +animalSensor.getCodigo()+" referenciado pela Leitura nº "+ leitura.getnLeitura() +" do sensor "+ leitura.getCodigo_sensor() +" está em CIO Atualmente.");
                            // CÓDIGO DE ENCERRAMENTO AUTOMÁTICO DE CIO
                            Cio ultCioAnimal = acessoBDCio.buscarUltimoDoAnimalDAO(animalSensor.getId());//captura o ultimo cio cadastrado relacionado ao animal
                            GregorianCalendar dataInicioUltimoCio = new GregorianCalendar();
                            dataInicioUltimoCio.setTime(formatoCompletoBr.parse(ultCioAnimal.getData_inicio()+ " " + ultCioAnimal.getHora_inicio()));
                            long dif_ms = dthLeituraPosCad.getTimeInMillis() - dataInicioUltimoCio.getTimeInMillis();
                            int duracao_cio_atual = (int) (dif_ms/(60*60*1000));
                            System.out.print(duracao_cio_atual);
                            if( (variacao_movimentacao.equals("BAIXA")) && (infoAnimalSensor.getDuracao_media_cio() < duracao_cio_atual) ){
                                encerrarCio(ultCioAnimal, animalSensor, infoAnimalSensor, leituraPosCadastro, atvAnimal, config.getDt_ult_captura(), config.getHr_ult_captura(), request);                                
                            }
                        }
                    } else {//... então a leitura já foi cadastrada.
                        System.out.println(">> A Leitura nº "+ leitura.getnLeitura() +" do sensor "+ leitura.getCodigo_sensor() +" já foi cadastrada anteriormente");
                    }
                } else {//... então o sensor não existe ou não está com o monitoramento ativo
                    System.out.println("\n>> O Sensor "+leitura.getSensor().getCodigo()+" com leitura nº "+ leitura.getnLeitura() +" não está cadastrado ou está inativo.");
                } 
            }  
        }// FIM do FOR que percorre o vetor com as informações de cada leitura(linha do arquivo) a ser cadastrada
        System.out.println("\n>>> "+formatoCompletoBr.format(agora)+" - CONSULTA AO MIDDLEWARE COMPLETADA.\n");
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