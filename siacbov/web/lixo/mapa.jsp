<%-- 
    Document   : mapa
    Created on : 30/08/2014, 11:04:55
    Author     : JV
--%>

<%@page import="br.edu.cio.model.ContextoPercebido"%>
<%@page import="br.edu.cio.util.verificaContexto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
    int qtdSensores = 5;
    int cont = 1;
    while(cont <= qtdSensores){
        ContextoPercebido contexto = verificaContexto.buscaUltimoRegistro(cont);
        if(contexto != null){
            session.setAttribute("contextoP", contexto);
        }
%>    
        <input type="hidden" id="posicao<%=cont%>" value="${contextoP.posicao}" />
        <input type="hidden" id="idAnimal<%=cont%>" value="${contextoP.id_animal}" />
        <input type="hidden" id="data<%=cont%>" value="${contextoP.data}" />
        <input type="hidden" id="hora<%=cont%>" value="${contextoP.hora}" />
<%
        cont+=1;
    }
%>
<div id="area_mapa">   
    <canvas id="canvas" width="600" height="500"></canvas><!-- area de 30m x 20 m, sendo q cada metro corresponde a 20 px -->
    <script type="text/javascript" src="js/mapa_canvas.js"></script>
    <!--<//jsp:useBean id="contextoP" scope="session" class="br.edu.cio.model.ContextoPercebido"/>
    Posicao: <//jsp:getProperty name="contextoP" property="posicao"/>-->   
</div>