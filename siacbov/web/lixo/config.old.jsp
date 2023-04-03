<%@page import="java.util.List" %>
<%@page import="br.edu.cio.model.Sensor" %>
<%@page import="javax.swing.JOptionPane" %>
<%@page import="java.text.DateFormat" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page session="true" %>
<%@page import="br.edu.cio.model.Configuracao" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="configVigente" scope="session" class="br.edu.cio.model.Configuracao"/>

<script>
    //estudando
    window.onload = function() {  
       window.location("/siac/control?cls=Config&mtd=carregar");
    }
</script>

<%  switch(request.getParameter("a")){ 
        case "load":
        {
%>
            <% Configuracao conf = (Configuracao) session.getAttribute("configVigente"); %> 
            <div id="formCadastrarConfig">        
                <h1>Configurações</h1>
                <br>
                <table border="1">
                    <tr>
                        <td>Intervalo Padrão de Anestro</td>
                        <td> ${configVigente.intervalo_anestro} -  <%= conf.getIntervalo_anestro() %> </td>
                    </tr>
                    <tr>
                        <td>Quantidade de Ocorrências Suspeitas necessárias para constatação do cio</td>
                        <td> <%= conf.getQtd_oco_pra_cio() %> </td>
                    </tr>
                    <tr>
                        <td>Duração Padrão de Cio</td>
                        <td> <%= conf.getDuracao_cio() %> </td>
                    </tr>
                    <tr>
                        <td>Percentual Padrão de Aumento de Atividades em Cio </td>
                        <td> <%= conf.getPerc_passadas_pra_cio()%> </td>
                    </tr>
                    <tr>
                        <td>Intervalo de Carregamento de Leituras</td>
                        <td> <%= conf.getIntervalo_carrega_leitura() %> </td>
                    </tr>
                    <tr>
                        <td>Alertar quando</td>
                        <td> <%= conf.getAlerta_quando() %> </td>
                    </tr>
                    <tr>
                        <td>Alerta no Celular?</td>
                        <td> <%= conf.getEnvio_celular() %> </td>
                    </tr>
                    <tr>
                        <td>Alerta no Email?</td>
                        <td> <%= conf.getEnvio_email() %> </td>
                    </tr>
                    <tr>
                        <td>Quantidade de Alertas a Emitir</td>
                        <td> <%= conf.getQtd_alertas() %> </td>
                    </tr>
                    <tr>
                        <td>Intervalo entre alertas</td>
                        <td> <%= conf.getTempo_entre_alertas()%> </td>
                    </tr>
                    <tr>
                        <td>Mensagem de Alerta de Suspeita de Cio</td>
                        <td> <%= conf.getMsg_alerta_ocorrencia()%> </td>
                    </tr>
                    <tr>
                        <td>Mensagem de Alerta de Cio</td>
                        <td> <%= conf.getMsg_alerta_cio() %> </td>
                    </tr>
                    
                    
                </table>
                <br>
                <form name="formCarregarConfiguracao" action="principal?d=forms&a=edt&f=config" method="POST">
                    <input type="submit" value="Editar Parâmetros" name="btn_edt_config" />
                </form>
            </div>
<%
            break;
        }
        case "edt":
        {
%>
            <% Configuracao conf = (Configuracao) session.getAttribute("configVigente"); %>
            
            
            
            <div id="formEditarConfig">        
                <h1>Configuraçoes - Editar</h1>
                <br>
                <form name="formEditarConfiguracoes" action="/siac/control?cls=Config&mtd=editar" method="POST">
                    <table class="tbl_bsc" >
                        <tr>
                            <td>Intervalo Padrão de Anestro</td>
                            <td> 
                                <select name="cmp_edt_config_1">
                                    <option value="<%= conf.getIntervalo_anestro() %>"><%= conf.getIntervalo_anestro() %> dias</option>
                                    <%
                                        for(int i=17; i<=24; i++){
                                            if( conf.getIntervalo_anestro()  != i){
                                                %>
                                                    <option value="<%= i %>" ><%= i %> dias</option>
                                                <%
                                            }
                                        }
                                    %>
                                </select> 
                            </td>
                        </tr>
                        <tr>
                            <td>Quantidade de Ocorrências Suspeitas necessárias para constatação do cio</td>
                            <td> 
                                <select name="cmp_edt_config_2">
                                    <option value="<%= conf.getQtd_oco_pra_cio()%>"><%= conf.getQtd_oco_pra_cio()%></option>
                                    <%
                                        for(int i=1; i<=10; i++){
                                            if( conf.getQtd_oco_pra_cio()  != i){
                                                %>
                                                    <option value="<%= i %>" ><%= i %></option>
                                                <%
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>Duração Padrão de Cio</td>
                            <td> 
                                <select name="cmp_edt_config_3">
                                    <option value="<%= conf.getDuracao_cio()%>"><%= conf.getDuracao_cio()%> horas</option>
                                    <%
                                        for(int i=10; i<=36; i+=2){
                                            if( conf.getDuracao_cio()!= i){
                                                %>
                                                    <option value="<%= i %>" ><%= i %> horas</option>
                                                <%
                                            }
                                        }
                                    %>
                                </select> 
                            </td>
                        </tr>
                        <tr>
                            <td>Percentual Padrão de Aumento de Atividades em Cio (%)</td>
                            <td> <input type="text" name="cmp_edt_config_4" value="<%= conf.getPerc_passadas_pra_cio()%>" size="15" />  </td>
                        </tr>
                        <tr>
                            <td>Intervalo de Carregamento de Leituras</td>
                            <td> 
                                <select name="cmp_edt_config_5">
                                    <option value="<%= conf.getIntervalo_carrega_leitura()%>"><%= conf.getIntervalo_carrega_leitura()%> horas</option>
                                    <%
                                        for(int i=2; i<=24; i+=2){
                                            if( conf.getIntervalo_carrega_leitura()!= i){
                                                %>
                                                    <option value="<%= i %>" ><%= i %> horas</option>
                                                <%
                                            }
                                        }
                                    %>
                                </select> 
                            </td>
                        </tr>
                        <tr>
                            <td>Alertar quando</td>
                            <td> 
                                <select name="cmp_edt_config_6">
                                    <option value="<%= conf.getAlerta_quando()%>"><%= conf.getAlerta_quando()%></option>
                                    <%
                                        String selecionada = conf.getAlerta_quando();
                                        if( !selecionada.equals("Suspeitar Cio")){
                                            %><option value="Suspeitar Cio" >Suspeitar Cio</option><% }
                                        if( !selecionada.equals(selecionada != "Constatar Cio")){
                                            %><option value="Constatar Cio" >Constatar Cio</option><% }
                                        if( !selecionada.equals("Suspeitar e Constatar Cio")){
                                            %><option value="Suspeitar e Constatar Cio" >Suspeitar e Constatar Cio</option><% }
                                    %>
                                </select> 
                            </td>
                        </tr>
                        <tr>
                            <td>Alerta no Celular?</td>
                            <td>
                                <%
                                    String celular = conf.getEnvio_celular();
                                    if(celular.equals("s")){
                                        %>
                                        <input type="radio" name="cmp_edt_config_7" value="s" checked="">Sim
                                        <input type="radio" name="cmp_edt_config_7" value="n">Não 
                                        <% 
                                    } else {
                                        %>
                                        <input type="radio" name="cmp_edt_config_7" value="s">Sim
                                        <input type="radio" name="cmp_edt_config_7" value="n" checked="">Não 
                                        <%
                                    }
                                %>
                                
                            </td>
                        </tr>
                        <tr>
                            <td>Alerta no Email?</td>
                            <td>
                                <%
                                    String email = conf.getEnvio_email();
                                    if( email.equals("s")){
                                        %>
                                        <input type="radio" name="cmp_edt_config_8" value="s" checked="">Sim
                                        <input type="radio" name="cmp_edt_config_8" value="n">Não 
                                        <% 
                                    } else {
                                        %>
                                        <input type="radio" name="cmp_edt_config_8" value="s">Sim
                                        <input type="radio" name="cmp_edt_config_8" value="n" checked="">Não 
                                        <%
                                    }
                                %> 
                            </td> 
                        </tr>
                        <tr>
                            <td>Quantidade de Alertas a Emitir</td>
                            <td> 
                                <select name="cmp_edt_config_9">
                                    <option value="<%= conf.getQtd_alertas()%>"><%= conf.getQtd_alertas()%></option>
                                    <%
                                        for(int i=1; i<=10; i++){
                                            if( conf.getQtd_alertas()!= i){
                                                %>
                                                    <option value="<%= i %>" ><%= i %></option>
                                                <%
                                            }
                                        }
                                    %>
                                </select> 
                            </td>
                        </tr>
                        <tr>
                            <td>Intervalo entre alertas</td>
                            <td> 
                                <select name="cmp_edt_config_10">
                                    <% String intervalo = conf.getTempo_entre_alertas(); %>
                                    <option value="<%= intervalo %>"><%= intervalo %></option>
                                    <%
                                        if( !intervalo.equals("00:05:00")){
                                            %><option value="00:05:00" >5 minutos</option><% }
                                        if( !intervalo.equals("00:15:00")){
                                            %><option value="00:15:00" >15 minutos</option><% }
                                        if( !intervalo.equals("00:30:00")){
                                            %><option value="00:30:00" >30 minutos</option><% }
                                        if( !intervalo.equals("01:00:00")){
                                            %><option value="01:00:00" >1 hora</option><% }
                                        if( !intervalo.equals("02:00:00")){
                                            %><option value="02:00:00" >2 horas</option><% }
                                        if( !intervalo.equals("04:00:00")){
                                            %><option value="04:00:00" >4 horas</option><% }
                                        if( !intervalo.equals("06:00:00")){
                                            %><option value="06:00:00" >6 horas</option><% }
                                        if( !intervalo.equals("08:00:00")){
                                            %><option value="08:00:00" >8 horas</option><% }
                                        if( !intervalo.equals("12:00:00")){
                                            %><option value="12:00:00" >12 horas</option><% }
                                    %>
                                </select> 
                            </td>
                        </tr>
                        <tr>
                            <td>Mensagem de Alerta de Suspeita de Cio</td>
                            <td> <textarea name="cmp_edt_config_11" rows="5"><%= conf.getMsg_alerta_ocorrencia() %></textarea>  </td>
                        </tr>
                        <tr>
                            <td>Mensagem de Alerta de Cio</td>
                            <td> <textarea name="cmp_edt_config_12" rows="5"><%= conf.getMsg_alerta_cio() %></textarea>  </td>
                        </tr>
                    </table>
                    <br>
                    <input type="submit" value="Atualizar Configuração" name="btn_edt_config" />
                </form>
            </div>
<%
            break;
        }
               
        default:
            break;
} 
%>
