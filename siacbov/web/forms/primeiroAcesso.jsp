<% request.getSession().invalidate(); %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta charset="utf-8">
        <link href="css/estilo.css" rel="stylesheet" type="text/css">
        <title>Sistema Cio</title>
        <%  if(request.getAttribute("mensagem")!=null){ %>
            <script type="text/javascript"> alert("<%= request.getAttribute("mensagem") %>"); </script>
        <% } %>
    </head><!DOCTYPE html>

    <body>       
        <div id="box_geral"> 
            <div id="barra_superior">
                <div class="msg"></div>
                <div class="menu_superior"> <a href="index.jsp">Home</a> | Sobre </div>
            </div>
            <header id="topo">
                <div class="logo">
                      <img src="imagens/logo.png" style="width: 200px; padding-left: 5px; padding-bottom: 15px;">
                </div>
                <h1 style="padding-top: 14px; padding-left: 5px;">Módulo Bovino</h1>            
                <h2>Sistema Inteligente de Idenficação de Cio em Animais Ruminantes</h2>
                <div class="info"></div>  
            </header>
            <hr><div id="faixa_superior"></div><hr>                              
            <div id="conteudo">
                <div class="paginas"><jsp:include page="forms/FormlarioPrimeiroAcesso.jsp" /></div> 
            </div>
            <div id="rodape">
                <hr><div id="faixa_inferior"></div><hr>
                <p>Copyright &COPY; 2014-2015 | Todos os Direitos Reservados</p>
            </div>
        </div>
    </body>
</html>


