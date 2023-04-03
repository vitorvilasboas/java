<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.edu.cio.model.Lote"%>
<%@page import="br.edu.cio.model.dao.mysql.MetodosUteisDAOMySQL"%>
<%@page import="java.sql.ResultSet"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="lotes" scope="request" class="br.edu.cio.model.Lote"/>

<%
    Lote l = new Lote();
    ArrayList<Lote> listaLote = (ArrayList<Lote>) request.getAttribute("lotesEncontrados");
%>
<link rel="stylesheet" media="screen" href="css/cad_form.css" >

<%  switch (request.getParameter("a")) {
        case "cadastrar": {
%>
            <div id="divCadastrar">        
                <form class="form_padrao" name="formCadastrar" action="/siac/control?cls=Lote&mtd=cadastrar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Lote - Cadastrar</h1></li>
                        <li class="li_corpo"><label>Descrição:</label><input style="width: 18.5em;" name="cmp_lote_descricao" type="text" autofocus required=""/></li>
                        <li class="li_corpo"><label>Tipo:</label> 
                            <select name="cmp_lote_tipo" autofocus required="">
                                <option value="">Selecione...</option><option>REBANHO DE CORTE</option><option>REBANHO LEITEIRO</option>
                            </select>
                        </li>
                        <li class="li_corpo"><label>Propriedade:</label>
                            <select name="cmp_lote_propriedade" >
                                <option>Selecione...</option>
                                <%  ResultSet rs = MetodosUteisDAOMySQL.getPropriedade();
                                    while (rs.next()) { %>
                                        <option value="<%= rs.getString("prop_id")%>">  <%= rs.getString("prop_nome")%> </option>
                                <%  }   %>
                            </select>
                        </li>
                        <li class="li_corpo"><label>Pasto:</label>
                            <select name="cmp_lote_pasto" >
                                <option>Selecione...</option>
                                <%  ResultSet rs1 = MetodosUteisDAOMySQL.getPasto();
                                    while (rs1.next()) {    %>
                                        <option value="<%= rs1.getString("pas_id")%>">  <%= rs1.getString("pas_nome")%> </option>
                                <%  }   %>
                            </select>
                        </li>
                        <li class="li_corpo"><label>Observação:</label><textarea name="cmp_lote_observacao" rows="5" cols="34"></textarea></li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" type="submit" name="enviarFormCadastrar" value="Salvar">Salvar</button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarFormCadastrar" value="Cancelar" onclick="javascript:history.back(1)">Cancelar</button>
                        </li>
                    </ul>
                </form>
            </div>
<%          break;
        }
        case "editar": {
%>
            <div id="divEditar">        
                <form class="form_padrao" name="formEditar" action="/siac/control?cls=Lote&mtd=editarSalvar&id=${lotes.id}" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Lote - Editar</h1></li>
                        <li class="li_corpo"><label>Codigo:</label><input name="cmp_lote_numero" style="width: 18.5em;" type="text" autofocus required="" value="${lotes.codigo}" readonly=""/></li>
                        <li class="li_corpo"><label>Descrição:</label><input name="cmp_lote_descricao" style="width: 18.5em;" type="text" autofocus required="" value="${lotes.descricao}" /></li>
                        <li class="li_corpo"><label>Tipo:</label>
                            <select name="cmp_lote_tipo" autofocus>
                                <c:if test="${lotes.tipo != null}"><option value="${lotes.tipo}">${lotes.tipo}</option></c:if>
                                <c:if test="${lotes.tipo == null}"><option value="">Selecione...</option></c:if>
                                <c:if test="${lotes.tipo ne 'REBANHO DE CORTE'}"><option value="REBANHO DE CORTE">REBANHO DE CORTE</option></c:if><!-- ne=not equals -->
                                <c:if test="${lotes.tipo ne 'REBANHO LEITEIRO'}"><option value="REBANHO LEITEIRO">REBANHO LEITEIRO</option></c:if>
                            </select>
                        </li>
                        <li class="li_corpo"><label>Propriedade:</label>
                            <select name="cmp_lote_propriedade" >
                                <c:if test="${lotes.propriedade != null}"><option value="${lotes.propriedade.id}">${lotes.propriedade.nome}</option></c:if>
                                <option>Selecione...</option>
                                <%  ResultSet rs = MetodosUteisDAOMySQL.getPropriedade();
                                    while (rs.next()) { %>
                                        <option value="<%= rs.getString("prop_id")%>">  <%= rs.getString("prop_nome")%> </option>
                                <%  }   %>
                            </select>
                        </li>
                        <li class="li_corpo"><label>Pasto:</label>
                            <select name="cmp_lote_pasto" >
                                <c:if test="${lotes.pasto != null}"><option value="${lotes.pasto.id}">${lotes.pasto.nome}</option></c:if>
                                <option>Selecione...</option>
                                <%  ResultSet rs1 = MetodosUteisDAOMySQL.getPasto();
                                    while (rs1.next()) {    %>
                                        <option value="<%= rs1.getString("pas_id")%>">  <%= rs1.getString("pas_nome")%> </option>
                                <%  }   %>
                            </select>
                        </li>
                        <li class="li_corpo"><label>Observação:</label><textarea name="cmp_lote_observacao" rows="5"  cols="34">${lotes.observacao}</textarea></li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" type="submit" name="enviarFormEditar" value="Salvar">Salvar</button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarFormCadastrar" value="Cancelar" onclick="javascript:history.back(1)">Cancelar</button>
                        </li>
                    </ul>
                </form>
            </div>
<%          break;
        }
        case "excluir": {
%>

<%
        break;
    }
    case "listar": {
%>

<%           break;
        }
        case "detalhar": {
            Lote lot = (Lote) request.getAttribute("detalheslote");
%>
            <div class="divCabecalho">        
                <ul>
                    <li class="li_titulo"><h1>Dados do Lote de Animais</h1></li>
                    <li></li>
                </ul>        
            </div>
            <div class="tabDetalhes" style="width:650px;">
                <table>
                    <tr><th>CÓDIGO</th>
                        <td><%  if(lot.getCodigo()!= null) 
                                    out.print(lot.getCodigo()); %>
                        </td>
                    </tr>
                    <tr><th>DESCRIÇÃO</th>
                        <td><%  if(lot.getDescricao()!= null) 
                                    out.print(lot.getDescricao()); %>
                        </td>
                    </tr>
                    <tr><th>TIPO</th>
                        <td><%  if(lot.getTipo()!= null) 
                                    out.print(lot.getTipo()); %>
                        </td>
                    </tr>
                    <tr><th>OBSERVAÇÃO</th>
                        <td><%  if(lot.getObservacao()!= null) 
                                    out.print(lot.getObservacao()); %>
                        </td>
                    </tr>
                    <tr><th>PROPRIEDADE</th>
                        <td><%  if(lot.getPropriedade()!= null) 
                                    out.print(lot.getPropriedade().getNome()); %>
                        </td>
                    </tr>
                    <tr><th>ÁREA DE PASTAGEM</th>
                        <td><%  if(lot.getPasto()!= null) 
                                    out.print(lot.getPasto().getNome()); %>
                        </td>
                    </tr>   
                </table>
            </div>
            <div id="formBuscarLote">        
                <form class="form_padrao" name="formDetalhar" action="" method="POST">
                    <ul>
                        <li class="li_corpo"></li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" type="submit"><a  style="text-decoration: none;" href="/siac/control?cls=Lote&mtd=editar&id=<%= lot.getId()%>"/>Editar</a></button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="submit" onclick="return confirm('Confirma a exclusão do Lote ${lotes.descricao}?')"><a  style="text-decoration: none;" href="/siac/control?cls=Lote&mtd=excluir&id=<%= lot.getId()%>"/>Excluir</a></button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarForm" onclick="javascript:history.back(1)">Voltar</button>
                        </li>
                    </ul>
                </form>
            </div>
            <br>
<%          break;
        }
        case "buscar": {
%>
            <div id="divBuscar">        
                <form class="form_padrao" name="formBuscar" action="/siac/control?cls=Lote&mtd=buscar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Lote de Animais</h1></li>
                        <li class="li_corpo">
                            <input placeholder="Informe o Código ou a Descrição do Lote de Animais" type="text" name="cmp_bsc_lote"  size="50" />
                            <button class="submit_buscar" type="submit" name="btn_bsc_lote">Buscar Lote de Animais</button>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" ><a href="principal?d=forms&a=cadastrar&f=lote">Incluir Lote</a></button>
                        </li>
                    </ul>
                </form>
            </div>
<%          break;
        }
        case "resultBusca": {
%>   
            <div id="divBuscar">        
                <form class="form_padrao" name="formBuscar" action="/siac/control?cls=Lote&mtd=buscar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Lote de Animais</h1></li>
                        <li class="li_corpo">
                            <input placeholder="Informe o Código ou a Descrição do Lote de Animais" type="text" name="cmp_bsc_lote"  size="50" />
                            <button class="submit_buscar" type="submit" name="btn_bsc_lote">Buscar Lote de Animais</button>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" ><a href="principal?d=forms&a=cadastrar&f=lote">Incluir Lote</a></button>
                        </li>
                    </ul>
                </form>
            </div>
            <br>
            <div class="tabResultBusca" style="width: 650px;">
                <table>
                    <tr><td colspan="7"><center>Lotes de Animais</center></td></tr>    
                    <tr>
                        <th>Código</th>
                        <th>Descrição</th>
                        <th>Tipo</th>
                        <th>Pasto</th>
                        <th colspan="3">Ações</th>
                    </tr>
                    <%  for (Lote lots : listaLote) {%>
                            <tr>
                                <td><%= lots.getCodigo()%></td>
                                <td><%= lots.getDescricao()%></td>
                                <td><%= lots.getTipo()%></td>
                                <td><%= lots.getPasto().getNome() %></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=Lote&mtd=detalhar&id=<%= lots.getId()%>" title="Detalhar"><center><img src="imagens/icones/btn_detalhar.png" alt="Detalhes"/></center></a></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=Lote&mtd=editar&id=<%= lots.getId()%>" title="Editar"><center><img src="imagens/icones/btn_editar.png" alt="Editar"/></center></a></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=Lote&mtd=excluir&id=<%= lots.getId()%>" title="Excluir" onclick="return confirm('Confirma a exclusão da Lote <%= lots.getDescricao()%>?')"><center><img src="imagens/icones/btn_excluir.png" alt="Excluir"/></center></a></td>
                            </tr>
                    <%  }   %>             
                </table>
            </div>
<%          break;
        }
        default:
            break;
    }
%>