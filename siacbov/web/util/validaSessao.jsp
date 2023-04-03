
<%
    if(session.getAttribute("idLogado") == null){
        response.sendRedirect("index.jsp");//não mostra a mensagem
    } else {
        //out.println("ok");   
%>