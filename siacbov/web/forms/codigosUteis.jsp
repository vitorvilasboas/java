/



<% List<Sensor> s = (List<Sensor>) session.getAttribute("listaSensores"); %>

<table>
    
            
                <%
                    for (int i=0; i<= s.size(); i++){
                %>
                        <tr>
                            <td><%= s.get(i).getDescricao() %></td>
                        </tr>
                <%
                    }
        
                %>
            }
            
        
</table>
