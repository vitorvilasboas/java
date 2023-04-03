<%@page import="br.edu.cio.model.Configuracao"%>
<%@page import="java.util.TimerTask"%>
<%@page import="java.util.Timer"%>
<%@page import="br.edu.cio.controller.ControleMetodosMonitoramento"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="true"%>
<%@page import="br.edu.cio.model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="configVigente" scope="session" class="br.edu.cio.model.Configuracao"/>
<jsp:useBean id="conf" scope="session" class="br.edu.cio.model.Configuracao"/>
<!DOCTYPE html>

<html>
    <head>
        <meta charset="utf-8">
        <!--<meta HTTP-EQUIV="Refresh" CONTENT="5">-->
        <link href="css/estilo.css" rel="stylesheet" type="text/css">
        <link href="css/jquery.jqGauges.css" rel="stylesheet" type="text/css"  />
        <link rel="stylesheet" type="text/css" href="css/jqchart/jquery.jqChart.css" />
        <link rel="stylesheet" type="text/css" href="css/jqchart/jquery.jqRangeSlider.css" />
        <link rel="stylesheet" type="text/css" href="css/jqchart/themes/smoothness/jquery-ui-1.10.4.css" />
        <link rel="stylesheet" type="text/css" href="css/cal/frontierCalendar/jquery-frontier-cal-1.3.2.css" />
        <link rel="stylesheet" type="text/css" href="css/cal/colorpicker/colorpicker.css" /> <!-- Include CSS for color picker plugin (Not required for calendar plugin. Used for example.) -->
        <link rel="stylesheet" type="text/css" href="css/cal/jquery-ui/smoothness/jquery-ui-1.8.1.custom.css" />
        <link rel="icon" type="image/png" href="/imagens/icone.png" />
        <title>SIAC Bovino</title>

        <script type="text/javascript" src="js/jquery-2.1.1.js"></script>
        <script type="text/javascript" src="js/jquery-latest.pack.js"></script>
        <script src="js/jqchart/jquery-1.11.1.min.js" type="text/javascript"></script>
        <script src="js/jqchart/.mousewheel.js" type="text/javascript"></script>
        <script src="js/jqchart/jquery.jqChart.min.js" type="text/javascript"></script>
        <script src="js/jqchart/jquery.jqRangeSlider.min.js" type="text/javascript"></script>
        <script src="js/canvasjs/canvasjs.min.js" type="text/javascript"></script>
        <script src="js/jqchart/jquery.jqGauges.min.js" type="text/javascript"></script>       
        <script src="js/highcharts/highcharts.js" type="text/javascript"></script>
        <!--<script src="js/highcharts/heatmap.js"></script>-->
        <script src="js/highcharts/exporting.js" type="text/javascript"></script>
        <script src="js/highcharts/highstock.js" type="text/javascript"></script>
        
        <jsp:include page="control?cls=Config&mtd=buscar"/>
        
        <script language="javascript" type="text/javascript">
            var auto_refresh = setInterval(    
            function reload(){/* função inicia o procedimento para gravação das leituras no banco de dados */
                    <c:if test="${conf != null}" >
                        <c:if test="${conf.reg_sensores == 'manual'}"></c:if>
                        <c:if test="${conf.reg_sensores == 'auto'}">
                            var oRequest = new XMLHttpRequest();
                            var sURL = "control?cls=BCC&mtd=atualizarManual";
                            //var sURL = "control?cls=Monitoramento&mtd=gravarLeitura";//chama o método de gravação de leituras a cada 10 segundos
                            oRequest.open("GET",sURL,false); 
                            oRequest.setRequestHeader("User-Agent",navigator.userAgent); 
                            oRequest.send(null) 
                            //document.getElementById("new").innerHTML = oRequest.responseText; 
                            //setTimeout("reload()",4000);
                        </c:if>
                    </c:if>
            }, 60000); // refresh a cada 1 min (60000)*/
            
            function soNumeros(evt){
                var tecla=(window.event)?event.keyCode:evt.which; 
                if((tecla>47 && tecla<58)) return true;
                else{
                    if (tecla==8 || tecla==0 || tecla==13) return true;
                    else  return false;
                }
            }
            
            function soDecimais(evt){
                var tecla=(window.event)?event.keyCode:evt.which; 
                if((tecla>47 && tecla<58)) return true;
                else{
                    if (tecla==8 || tecla==0 || tecla==13 || tecla==44 || tecla==46) return true;
                    else  return false;
                }
            }
            
            function openDialogFunc(input) {
                var files = input.files;
                var file = URL.createObjectURL(files[0]);
                imagem.src = file;
            }
                        
        </script>
        
        <%  if (request.getAttribute("alerta_cio") != null) {   %>
                <script type="text/javascript"> alert("<%= request.getAttribute("alerta_cio")%>");</script>
                <% request.setAttribute("alerta_cio", null); %>
        <%  } %>
        
        <%  if (request.getAttribute("mensagem") != null) {     %>
                <script type="text/javascript"> alert("<%= request.getAttribute("mensagem")%>");</script>
        <%  } %>
        
        
        
    </head> 

    <body>
        <div id="box_geral">
            <%@include file="util/validaSessao.jsp" %>
            <% Usuario usuarioLogado = (Usuario) session.getAttribute("usuario_logado");%>

            <div id="topo">
                <div class="logo">
                    <img src="imagens/logo.png" style="width: 300px; padding-left: 5px; padding-bottom: 15px;">
                </div>
                <div class="logo-desc">
                    <h1>Bovino</h1>            
                    <h2>Sistema de Idenficação Automática de Cio</h2>
                </div>
                <div class="barra_superior">
                    <div class="menu_superior"> 
                        <a href="principal.jsp?d=&f=home">Home</a> | 
                        <a href="principal.jsp?d=forms&f=meuPerfil">Meu Perfil</a> | 
                        <a href="principal.jsp?d=&f=documentacao"></a>Documentação | 
                        <a href="principal.jsp?d=&f=sobre"></a>Sobre
                    </div>
                    <div class="info" >
                        Bem vindo: <%= usuarioLogado.getFuncionario().getNome()%>
                        | <a href="javascript:if(confirm('Tem certeza que deseja sair do sistema?')){window.location = 'sair.jsp'}">Sair</a>
                    </div>
                </div>
            </div>

            <hr><div id="faixa_superior"></div><hr>

            <div id="conteudo">
                <div class="menu_lateral">  <%@include file="menu.jsp" %>   </div>
                <div class="paginas">
                    <%
                        try {
                            String diretorio = request.getParameter("d");
                            String arquivo = request.getParameter("f");
                            String pg = diretorio + "/" + arquivo + ".jsp"; //out.println(pg);
%>
                    <jsp:include page="<%=pg%>"/>
                    <%
                    } catch (Exception e) {
                    %>
                    <jsp:include page="home.jsp"/><!--util/erro2.jsp-->
                    <%
                        }
                    %>
                </div>
            </div>

            <div id="rodape"><%@include file="footer.jsp" %></div>

        </div>
    </body>
</html>