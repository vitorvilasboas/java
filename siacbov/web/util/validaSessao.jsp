
<%
    if(session.getAttribute("idLogado") == null){
        response.sendRedirect("index.jsp");//n�o mostra a mensagem
    } else {
        //out.println("ok");   
%>