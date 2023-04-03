<%
    session.invalidate();
    //request.setAttribute("mensagem", "Logoff efetuado com sucesso!");
    response.sendRedirect("index.jsp");
    //request.getRequestDispatcher("index.jsp").forward(request, response);//mostra a mensagem
%>