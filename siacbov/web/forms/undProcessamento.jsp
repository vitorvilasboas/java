<%@page import="java.util.ArrayList"%>
<%@page import="br.edu.cio.model.UndProcessamento"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="undProcessamentos" scope="request" class="br.edu.cio.model.UndProcessamento"/>
<%
    UndProcessamento u = new UndProcessamento();
    ArrayList<UndProcessamento> listaUndProcessamentos = (ArrayList<UndProcessamento>) request.getAttribute("undProcessamentoEncontrados");
%>

<%  switch (request.getParameter("a")) {
        case "cadastrar": {
%>
            <div id="divCadastrar">        
                <br>
                <form class="form_padrao" name="formCadastrar" action="/siac/control?cls=UndProcessamento&mtd=cadastrar" method="POST"> 
                    <ul>
                        <li class="li_titulo"><h1>Unidade de Processamento - Cadastrar </h1></li>
                        <li class="li_corpo"><label>Quantidade de Sensores:</label>
                            <input type="text" name="cmp_sensor_quantidade" required="" onkeypress='return soNumeros(event)'/>
                        </li>
                        <li class="li_corpo"><label>Descrição:</label>
                            <input type="text" name="cmp_sensor_descricao" required="" />
                        </li>
                        <li class="li_corpo"><label>Número de Série:</label>
                            <input type="text" name="cmp_sensor_Nseries" required="" />
                        </li>
                        <li class="li_corpo"><label>Local Fixação:</label>
                            <input type="text" name="cmp_sensor_local_fixacao" />
                        </li>
                        <li class="li_corpo"><label>Tecnologia:</label>
                            <input type="text" name="cmp_sensor_tecnologia" required="" />
                        </li>
                        <li class="li_corpo"><label>Observação:</label>
                            <input type="text" name="cmp_sensor_observacao" />
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
                <form class="form_padrao" name="formEditar" action="/siac/control?cls=UndProcessamento&mtd=editarSalvar&id=${undProcessamentos.id}" method="POST">
                    <ul>
                        <li class="li_corpo"><h1>Unidade de Processamento - Editar</h1></li>
                        <li class="li_corpo"><label>Código:</label>
                            <input type="text" name="codigo_central" required="" value="${undProcessamentos.codigo}" readonly=""/>
                        </li>
                        <li class="li_corpo"><label>Quantidade de Sensores:</label>
                            <input type="text" name="cmp_sensor_quantidade" value="${undProcessamentos.qtd_sensores}" onkeypress='return soNumeros(event)'/>
                        </li>
                        <li class="li_corpo"><label>Descrição:</label>
                            <input type="text" name="cmp_sensor_descricao" required="" value="${undProcessamentos.descricao}"/>
                        </li>
                        <li class="li_corpo"><label>Número de Série:</label>
                            <input type="text" name="cmp_sensor_Nseries" required="" value="${undProcessamentos.nserie}"/>
                        </li>
                        <li class="li_corpo"><label>Local de Fixação:</label>
                            <input type="text" name="cmp_sensor_local_fixacao" value="${undProcessamentos.local_fixacao}" />
                        </li>
                        <li class="li_corpo"><label>Tecnologia:</label>
                            <input type="text" name="cmp_sensor_tecnologia" required="" value="${undProcessamentos.tecnologia}"/>
                        </li>
                        <li class="li_corpo"><label>Observação:</label>
                            <input type="text" name="cmp_sensor_observacao" value="${undProcessamentos.observacao}"/>
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
        case "excluir": {
%>

<%
            break;
        }
        case "detalhar": {
            UndProcessamento und = (UndProcessamento) request.getAttribute("detalhesUP");
%>
            <div class="divCabecalho">        
                <ul>
                    <li class="li_titulo"><h1>Dados da Unidade de Processamento</h1></li>
                    <li></li>
                </ul>        
            </div>
            <div class="tabDetalhes" style="width:650px;">
                <table>
                    <tr><th>CÓDIGO</th>
                        <td><%  if(und.getCodigo()!= null) 
                                    out.print(und.getCodigo()); %>
                        </td>
                    </tr>
                    <tr><th>QUANTIDADE DE SENSORES</th>
                        <td><%  if(und.getQtd_sensores()!= 0) 
                                    out.print(und.getQtd_sensores()); %>
                        </td>
                    </tr>
                    <tr><th>DESCRIÇÃO</th>
                        <td><%  if(und.getDescricao()!= null) 
                                    out.print(und.getDescricao()); %>
                        </td>
                    </tr>
                    <tr><th>Nº SÉRIE</th>
                        <td><%  if(und.getNserie()!= null) 
                                    out.print(und.getNserie()); %>
                        </td>
                    </tr>
                    <tr><th>LOCAL DE FIXAÇÃO</th>
                        <td><%  if(und.getLocal_fixacao()!= null) 
                                    out.print(und.getLocal_fixacao()); %>
                        </td>
                    </tr>
                    <tr><th>TECNOLOGIA</th>
                        <td><%  if(und.getTecnologia()!= null) 
                                    out.print(und.getTecnologia()); %>
                        </td>
                    </tr>
                    <tr><th>OBSERVAÇÕES</th>
                        <td><%  if(und.getObservacao()!= null) 
                                    out.print(und.getObservacao()); %>
                        </td>
                    </tr>   
                </table>
            </div>
            <div id="divDetalhar">        
                <form class="form_padrao" name="formDetalhar" action="" method="POST">
                    <ul>
                        <li class="li_corpo"></li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" type="submit"><a  style="text-decoration: none;" href="/siac/control?cls=UndProcessamento&mtd=editar&id=<%= und.getId()%>"/>Editar</a></button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="submit" onclick="return confirm('Confirma a exclusão da Unidade de Processamento${undProcessamentos.descricao}?')"><a  style="text-decoration: none;" href="/siac/control?cls=UndProcessamento&mtd=excluir&id=<%= und.getId()%>"/>Excluir</a></button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarForm" onclick="javascript:history.back(1)">Voltar</button>
                        </li>
                    </ul>
                </form>
                <br>
            </div>
            <br>
<%
            break;
        }
        case "buscar": {
%>
            <div id="divBuscar">        
                <form class="form_padrao" name="formBuscar" action="/siac/control?cls=UndProcessamento&mtd=buscar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Unidade de Processamento</h1></li>
                        <li class="li_corpo">
                            <input placeholder="Informe o código da unidade" type="text" name="cmp_bsc_undProcessamento"  size="50" />
                            <button class="submit_buscar" type="submit" name="btn_buscar">Buscar Central</button>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" ><a href="principal?d=forms&a=cadastrar&f=undProcessamento">Incluir Unidade</a></button>
                        </li>
                    </ul>
                </form>
            </div>
<%
            break;
        }
        case "resultBusca": {
%>
            <div id="divBuscar">        
                <form class="form_padrao" name="formBuscar" action="/siac/control?cls=UndProcessamento&mtd=buscar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Unidade de Processamento</h1></li>
                        <li class="li_corpo">
                            <input placeholder="Informe o código da unidade" type="text" name="cmp_bsc_undProcessamento"  size="50" />
                            <button class="submit_buscar" type="submit" name="btn_buscar">Buscar Central</button>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" ><a href="principal?d=forms&a=cadastrar&f=undProcessamento">Incluir Unidade</a></button>
                        </li>
                    </ul>
                </form>
            </div>
            <br>
            <div class="tabResultBusca">
                <table>
                    <tr><td colspan="7"><center>Unidade de Processamento</center></td></tr> 
                    <tr>
                        <th>Codigo</th>
                        <th>Descrição</th>
                        <th>Tecnologia</th>
                        <th>Nº Série</th>
                        <th colspan="3">Ações</th>
                    </tr>
                    <%  for (UndProcessamento und : listaUndProcessamentos) {%>
                            <tr>
                                <td><%=und.getCodigo()%></td>
                                <td><%=und.getDescricao()%></td>
                                <td><%  if(und.getTecnologia()!= null) %> 
                                            <%= und.getTecnologia() %></td>
                                <td><%=und.getNserie()%></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=UndProcessamento&mtd=detalhar&id=<%= und.getId()%>" title="Detalhar"><center><img src="imagens/icones/btn_detalhar.png" alt="Detalhes"/></center></a></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=UndProcessamento&mtd=editar&id=<%= und.getId()%>" title="Editar"><center><img src="imagens/icones/btn_editar.png" alt="Editar"/></center></a></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=UndProcessamento&mtd=excluir&id=<%= und.getId()%>" title="Excluir" onclick="return confirm('Confirma a exclusão da Unidade de Processamento <%= und.getCodigo()%>?')"><center><img src="imagens/icones/btn_excluir.png" alt="Excluir"/></center></a></td>
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

