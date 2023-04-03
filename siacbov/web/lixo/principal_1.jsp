<%@page import="br.edu.cio.model.Configuracao"%>
<%@page import="java.util.TimerTask"%>
<%@page import="java.util.Timer"%>
<%@page import="br.edu.cio.controller.ControleMetodosMonitoramento"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
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
        <title>Sistema Cio</title>
        <script type="text/javascript" src="js/jquery-2.1.1.js"></script>
        
        <!--<//jsp:include page="control?cls=Config&mtd=buscar"/>-->
        <!--<script type="text/javascript">
            /*
            function buscaConfig(){ 
                var r = new XMLHttpRequest(); 
                var URL = "control?cls=Config&mtd=buscar";
                r.open("GET",URL,false); 
                r.setRequestHeader("User-Agent",navigator.userAgent); 
                r.send(null);
            }*/
        </script>-->
        
        <!-- Código abaixo inicia o procedimento para gravação das leituras no banco de dados --> 
        <script type="text/javascript">
            var auto_refresh = setInterval(    
                function reload
                    alert("teste");
                    /*
                            var oRequest = new XMLHttpRequest(); 
                            var sURL = "control?cls=Config&mtd=gravarLeitura";//chama o método de gravação de leituras a cada 10 segundos
                            oRequest.open("GET",sURL,false); 
                            oRequest.setRequestHeader("User-Agent",navigator.userAgent); 
                            oRequest.send(null); 
                            //document.getElementById("new").innerHTML = oRequest.responseText; 
                            //setTimeout("reload()",4000);

                        */
                     /*                             <//c:if test="${conf != null}" >
                        <//c:if test="${conf.reg_sensores == 'auto'}">
                        <//c:if>
                    <//c:if>*/
                    
                }
            }, 10000); // refresh a cada 10 seg*/
        </script>
        
        
        <%  if(request.getAttribute("mensagem")!=null){ %>
            <script type="text/javascript"> alert("<%= request.getAttribute("mensagem") %>"); </script>
        <% } %>
        
    </head> 
    
    <body>
        <div id="box_geral">

            
            <%@include file="util/validaSessao.jsp" %>
            <% Usuario usuarioLogado = (Usuario) session.getAttribute("usuario_logado"); %>     
            <% //Configuracao c = (Configuracao) session.getAttribute("conf"); %>  
            
            <div id="barra_superior">
                <div class="msg"></div>
                <div class="menu_superior"> 
                    <a href="principal.jsp?d=&f=home">Home</a> | 
                    Meu Perfil | 
                    <a href="principal.jsp?d=&f=documentacao"></a>Documentação | 
                    <a href="principal.jsp?d=&f=sobre"></a>Sobre
                </div>
            </div>
            
            <header id="topo">
                <div class="logo">
                    <h1>Sistema de Identificação de Cio</h1>
                    <h1>BOVINO</h1>
                </div>
                <div class="info">
                    <!--<div class="iduser">-->
                        Bem vindo: <%= usuarioLogado.getFuncionario().getNome() %>
                        | <a href="javascript:if(confirm('Tem certeza que deseja sair do sistema?')){window.location = 'principal?d=&f=sair'}">Sair</a>
                    <!--</div>-->
                </div>     
            </header>
            
            <hr><div id="faixa_superior"></div><hr>
                                
            <div id="conteudo">
                <div class="menu_lateral">  <%@include file="menu.jsp" %>   </div>
                <div class="paginas">
                    <% 
                        try {
                            String diretorio = request.getParameter("d");
                            String arquivo = request.getParameter("f");
                            String pg =  diretorio + "/" + arquivo + ".jsp"; //out.println(pg);
                    %>
                            <jsp:include page="<%=pg%>"/>
                    <%
                        } catch (Exception e) {
                    %>
                            <jsp:include page="util/erro2.jsp"/>
                    <%
                        }
                    %>
                </div>
            </div>
                    
                    <div id="rodape"><%@include file="footer.jsp" %></div>
            
        </div>
    </body>
</html>

<!--
<//jsp:useBean id="usuario_logado" scope="session" class="br.edu.cio.model.Usuario"/>
Bem vindo: <//jsp:getProperty name="usuario_logado" property="nome" />
<p>Bem vindo, </%= usuarioLogado.getNome() %></p>
<p>Status do Usuário: </%= usuarioLogado.getStatus() %></p>
<p>Propriedade em que trabalha: </%= usuarioLogado.getPropriedade().getNome() %></p>-->