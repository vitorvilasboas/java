<%@page import="java.util.ArrayList"%>
<%@page import="br.edu.cio.model.Destinatario"%>
<%@page import="br.edu.cio.model.dao.mysql.MetodosUteisDAOMySQL"%>
<%@page import="java.sql.ResultSet"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="destinatarios" scope="request" class="br.edu.cio.model.Destinatario"/>

<%
    Destinatario des = new Destinatario();
    ArrayList<Destinatario> listaDestinatario = (ArrayList<Destinatario>) request.getAttribute("DestinatarioEncontrados");
%>

<%  switch (request.getParameter("a")) {
        case "cadastrar": {
%>
            <div id="divCadastrar">        
                <form class="form_padrao" name="formCadastrar" action="/siac/control?cls=Destinatario&mtd=cadastrar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Destinatário - Cadastrar</h1></li>
                        <li class="li_corpo"><label for="email">E-mail:</label>
                            <input type="email" name="email" required="" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" placeholder="exemplo_email@dominio.com"/>
                        </li>
                        <li class="li_corpo"><label>Celular:</label><input name="celular" style="width: 20em" type="text" autofocus required="" onkeypress='return soNumeros(event)'/></li>
                        <li class="li_corpo"><label>Funcionário:</label>
                            <select name="vinc_func" id="vinc_func" required="">
                                <option value="">Selecione...</option>
                                <%  ResultSet rs1 = MetodosUteisDAOMySQL.getFuncionario();
                                    while (rs1.next()) {    %>
                                        <option value="<%= rs1.getString("func_id")%>">  <%= rs1.getString("func_nome")%> </option>
                                <%  }   %>
                            </select>
                        </li>
                        <li class="li_corpo"><label for="status">Status:</label>
                            <select name="status" required="" >
                                <option value="">Selecione...</option><option value="1"> ON </option><option value="0"> OFF </option>
                            </select>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" type="submit" name="enviarFormCadastrar" value="Salvar">Salvar</button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarFormCadastrar" value="Cancelar" onclick="javascript:history.back(1)">Cancelar</button>
                        </li>
                    </ul>
                </form>
            </div>
<%
            break;
        }
        case "editar": {
%>
            <div id="divEditar">        
                <form class="form_padrao"  name="formEditar" action="/siac/control?cls=Destinatario&mtd=editarSalvar&id=${destinatarios.id}" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Destinatário - Editar</h1></li>
                        <li class="li_corpo"><label>ID:</label><input name="cmp_lote_numero" style="width: 20em" type="text" autofocus required="" value="${destinatarios.id}" readonly=""/></li>
                        <li class="li_corpo"><label for="email">E-mail:</label>
                            <input value="${destinatarios.email}" type="email" name="email" required="" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" placeholder="exemplo_email@dominio.com"/>
                        </li>
                        <li class="li_corpo"><label>Celular:</label><input name="celular" style="width: 20em" type="text" autofocus required="" value="${destinatarios.celular}" onkeypress='return soNumeros(event)'/></li>
                        <li class="li_corpo"><label>Funcionário:</label>
                            <select name="vinc_func" id="vinc_func" style="width: 23.2em" autofocus required="">
                                <c:if test="${destinatarios.funcionario != null}"><option value="${destinatarios.funcionario.id}">${destinatarios.funcionario.nome}</option></c:if>
                                <c:if test="${destinatarios.funcionario == null}"><option value="">Selecione...</option></c:if>
                                <%  ResultSet rs1 = MetodosUteisDAOMySQL.getFuncionario();
                                    while (rs1.next()) {    %>
                                        <option value="<%= rs1.getString("func_id")%>">  <%= rs1.getString("func_nome")%> </option>
                                <%  }   %>
                            </select>
                        </li>
                        <li class="li_corpo"><label for="status">Status:</label>
                            <select name="status" required="" >
                                <option value="">Selecione...</option><option value="1"> ON </option><option value="0"> OFF </option>
                            </select>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" type="submit" name="enviarFormEditar" value="Salvar">Salvar</button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarFormEditar" value="Cancelar" onclick="javascript:history.back(1)">Cancelar</button>
                        </li>
                    </ul>
                </form>
            </div>
<%
            break;
        }
        case "detalhar": {
            Destinatario dess = (Destinatario) request.getAttribute("detalhesdest");
%>
            <div class="divCabecalho">        
                <ul>
                    <li class="li_titulo"><h1>Dados do Destinatario</h1></li>
                    <li></li>
                </ul>        
            </div>
            <div class="tabDetalhes" >
                <table>
                    <tr><th>CÓDIGO</th>
                        <td><% if(dess.getId()!= 0) 
                                out.print(dess.getId()); %>
                        </td>
                    </tr>
                    <tr><th>FUNCIONÁRIO</th>
                        <td><% if(dess.getFuncionario() != null) 
                                out.print(dess.getFuncionario().getNome()); %>
                        </td>
                    </tr>
                    <tr><th>STATUS</th>
                        <td><% if(dess.getAtivo() == 1) 
                                out.print("ATIVO"); 
                               else
                                out.print("INATIVO"); %>
                        </td>
                    </tr>
                    <tr><th>EMAIL</th>
                        <td><% if(dess.getEmail()!= null) 
                                out.print(dess.getEmail()); %>
                        </td>
                    </tr>
                    <tr><th>CELULAR</th>
                        <td><% if(dess.getCelular()!= null) 
                                out.print(dess.getCelular()); %>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="divBuscar">
                <form class="form_padrao" name="formDetalhar" action="" method="POST">
                    <ul>
                        <li class="li_corpo"></li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" type="submit"><a href="/siac/control?cls=Destinatario&mtd=editar&id=<%= dess.getId()%>"/>Editar</a></button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="submit" onclick="return confirm('Confirma a exclusão do Destinatario ${Destinatario.descricao}?')"><a href="/siac/control?cls=Destinatario&mtd=excluir&id=<%= dess.getId()%>"/>Excluir</a></button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarForm" onclick="javascript:history.back(1)">Voltar</button>
                        </li>
                    </ul>
                </form>
            </div>
            <br>
<%
            break;
        }
        case "buscar": {
%>
            <div id="divBuscar">        
                <form class="form_padrao" name="formBuscar" action="/siac/control?cls=Destinatario&mtd=buscar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Destinatários</h1></li>
                        <li class="li_corpo">
                            <input placeholder="Informe o email do Destinatario" type="text" name="cmp_bsc_des"  size="50" />
                            <button class="submit_buscar" type="submit" name="btn_buscar">Buscar Destinatario</button>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir"><a href="principal?d=forms&a=cadastrar&f=destinatario"> Incluir Destinatario </a></button>
                        </li>
                    </ul>
                </form>
            </div>
<%
            break;
        }
        case "resultBusca": {
%>   
            <div id="divResultBusca">        
                <form class="form_padrao" name="formResultBusca" action="/siac/control?cls=Destinatario&mtd=buscar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Destinatários</h1></li>
                        <li class="li_corpo">
                            <input placeholder="Informe o email do Destinatario" type="text" name="cmp_bsc_des"  size="50" />
                            <button class="submit_buscar" type="submit" name="btn_bsc_des">Buscar Destinatario</button>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" ><a href="principal?d=forms&a=cadastrar&f=destinatario"> Incluir Destinatario </a></button>
                        </li>
                    </ul>
                </form>
            </div>
            <br>
            <div class="tabResultBusca" style="width: 750px;">
                <table>
                    <tr>
                        <td colspan="7"><center>Destinatarios de Animais</center></td>
                    </tr>    
                    <tr>
                        <th>ID</th>
                        <th>Funcionario</th>
                        <th>Email</th>
                        <th>Celular</th>
                        <th>Status</th>
                        <th colspan="3">Ações</th>
                    </tr>
                    <%  for (Destinatario d : listaDestinatario) {%>
                            <tr>
                                <td><%= d.getId()%></td>
                                <td><%= d.getFuncionario().getNome()%></td>
                                <td><%= d.getEmail()%></td>
                                <td><%= d.getCelular()%></td>
                                <td><% if(d.getAtivo() == 1 ) { %>
                                            <%= "ON" %>
                                    <% } else { %>
                                            <%= "OFF" %>
                                    <% } %></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=Destinatario&mtd=editar&id=<%= d.getId()%>" title="Editar"><center><img src="imagens/icones/btn_editar.png" alt="Editar"/></center></a></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=Destinatario&mtd=excluir&id=<%= d.getId()%>" title="Excluir" onclick="return confirm('Confirma a exclusão da Destinatario <%= d.getId()%>?')"><center><img src="imagens/icones/btn_excluir.png" alt="Excluir"/></center></a></td>
                            </tr>
                    <%  }   %>             
                </table>
            </div>
<%
            break;
        }
        default:
            break;
    }
%>
