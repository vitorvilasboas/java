<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <link href="css/estilo.css" rel="stylesheet" type="text/css">
        <link rel="icon" type="image/png" href="imagens/icone.png" />
        <title>SIAC Bovino</title>
        <%  if (request.getAttribute("mensagem")!=null){ %>
            <script type="text/javascript"> alert("<%= request.getAttribute("mensagem") %>"); </script>
        <% } %>
    </head>
    <body>       
        <div id="box_geral"> 
            <header id="topo">
                <div class="logo">
                    <img src="imagens/logo.png" style="width: 300px; padding-left: 5px; padding-bottom: 15px;">
                </div>
                <div class="logo-desc">
                    <h1>Bovino</h1>
                    <h2>Sistema de Idenficação Automática de Cio</h2>
                </div>
                <div class="barra_superior">
                    <div class="menu_superior"> <a href="index.jsp">Home</a> | Sobre </div>
                    <div class="info"></div>
                </div>  
            </header>
            <hr><div id="faixa_superior"></div><hr>                              
            <div id="conteudo">
                <div class="paginas"><jsp:include page="forms/FormularioRecueracaoSenha.jsp" /></div>               
            </div>
            <div id="rodape">
                <hr><div id="faixa_inferior"></div><hr>
                <p>Copyright &COPY; 2014-2015 | Todos os Direitos Reservados</p>
            </div>
        </div>
    </body>
</html>

