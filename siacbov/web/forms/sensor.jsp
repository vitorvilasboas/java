<%@page import="br.edu.cio.model.UndProcessamento"%>
<%@page import="br.edu.cio.model.Animal"%>
<%@page import="br.edu.cio.model.dao.mysql.MetodosUteisDAOMySQL"%>
<%@page import="java.sql.ResultSet"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.edu.cio.model.Sensor"%>
<%@page import="br.edu.cio.model.dao.mysql.SensorDAOMySQL"%>
<%@page import="br.edu.cio.model.dao.SensorDAO"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="sensores" scope="request" class="br.edu.cio.model.Sensor"/>
<%
    Sensor sen = new Sensor();
    ArrayList<Sensor> listaSensor = (ArrayList<Sensor>) request.getAttribute("SensoresEncontrados");
%>

<%  switch (request.getParameter("a")) {
        case "cadastrar": {
%>
            <div id="divCadastrar">        
                <form class="form_padrao" name="formCadastrar" action="/siac/control?cls=Sensor&mtd=cadastrar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Sensor - Cadastrar</h1></li>
                        <li class="li_corpo"><label>Nº de Série:</label><input type="text" name="cmp_Serie_sensor" /></li>
                        <li class="li_corpo"><label>Tipo:</label><input type="text" name="cmp_tipo_sensor" required="" /></li>
                        <li class="li_corpo"><label>Descrição:</label><input type="text" name="cmp_descricao_sensor" required="" /></li>
                        <li class="li_corpo"><label>Medição:</label><input type="text" name="cmp_medicao_sensor" required="" /></li>
                        <li class="li_corpo"><label>Alcance:</label><input type="text" name="cmp_alcance_sensor"  onkeypress='return soNumeros(event)'/></li>
                        <li class="li_corpo"><label>Tecnologia:</label><input type="text" name="cmp_tecnologia_sensor" /></li>
                        <li class="li_corpo"><label>Central:</label>
                            <select name="cmp_vinc_central" id="vinc_central" required="" >
                                <option value="">Selecione...</option>
                                <%  ResultSet rs2 = MetodosUteisDAOMySQL.getCentral();
                                    while (rs2.next()) {    %>
                                        <option value="<%= rs2.getString("cen_id")%>">  <%= rs2.getString("cen_codigo")%> - <%= rs2.getString("cen_descricao")%> </option>
                                <%  }   %>
                            </select>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" type="submit" name="enviarFormCadastrar" value="Salvar">Salvar</button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarFormCadastrar" value="Cancelar" onclick="javascript:history.back(1)">Cancelar</button>
                        </li>
                    </ul>  
                </form>   
            </div>
<%          break;
        }   case "editar": {
%>
            <div id="divEditar">        
                <form class="form_padrao" name="formEditar" action="/siac/control?cls=Sensor&mtd=editarSalvar&id=${sensores.id}" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Sensor - Editar</h1></li>
                        <li class="li_corpo"><label>Código:</label><input type="text" name="codigo" required="" value="${sensores.codigo}" readonly="" /></li>
                        <li class="li_corpo"><label>Nº de Série:</label><input type="text" name="cmp_nSerie_sensor" value="${sensores.nserie}"/></li>
                        <li class="li_corpo"><label>Tipo:</label><input type="text" name="cmp_tipo_sensor" required="" value="${sensores.tipo}"/></li>
                        <li class="li_corpo"><label>Descrição:</label><input type="text" name="cmp_descricao_sensor" required="" value="${sensores.descricao}"/></li>
                        <li class="li_corpo"><label>Medição:</label><input type="text" name="cmp_medicao_sensor" required="" value="${sensores.medicao}"/></li>
                        <li class="li_corpo"><label>Alcance:</label><input type="text" name="cmp_alcance_sensor"  onkeypress='return soNumeros(event)' value="${sensores.alcance}"/></li>
                        <li class="li_corpo"><label>Tecnologia:</label><input type="text" name="cmp_tecnologia_sensor" value="${sensores.tecnologia}" /></li>
                        <li class="li_corpo"><label>Central:</label>
                            <select name="vinc_central" id="vinc_central" required="" >
                                <c:if test="${sensores.central != null}"><option value="${sensores.central.id}">${sensores.central.codigo} - ${sensores.central.descricao}</option></c:if>
                                <option value="">Selecione...</option>
                                <%  ResultSet rs2 = MetodosUteisDAOMySQL.getCentral();
                                    while (rs2.next()) {    %>
                                        <option value="<%= rs2.getString("cen_id")%>">  <%= rs2.getString("cen_codigo")%> - <%= rs2.getString("cen_descricao")%> </option>
                                <%  }   %>
                            </select>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" type="submit" name="enviarFormEditar" value="Salvar">Salvar</button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarFormCadastrar" value="Cancelar" onclick="javascript:history.back(1)">Cancelar</button>
                        </li>
                    </ul>  
                </form>   
            </div> 
<%          break;
        }   case "excluir": {
%>

<%              break;
        }   case "listar": {
%>

<%          break;
        }   case "buscar": {
%>
            <div id="divBuscar">        
                <form class="form_padrao" name="formBuscar" action="/siac/control?cls=Sensor&mtd=buscar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Sensor</h1></li>
                        <li class="li_corpo">
                            <input placeholder="Informe o Código para pesquisa do sensor" type="text" name="cmp_bsc_sensor"  size="50" />
                            <button class="submit_buscar" type="submit" name="btn_bsc_sensor">Buscar sensor</button>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir"><a href="principal?d=forms&a=cadastrar&f=sensor">Incluir Sensor</a></button>
                        </li>
                    </ul>
                </form>
            </div>
<%          break;
        }   case "detalhar": {
            Sensor sens = (Sensor) request.getAttribute("detalhessensor");
%>
            <div class="divCabecalho">        
                <ul>
                    <li class="li_titulo"><h1>Dados do Sensor</h1></li>
                    <li></li>
                </ul>        
            </div>
            <div class="tabDetalhes" style="width:650px;">
                <table>
                    <tr><th>CÓDIGO</th>
                        <td><% if(sens.getCodigo()!= null) 
                                out.print(sens.getCodigo()); %>
                        </td>
                    </tr>
                    <tr><th>DESCRIÇÃO</th>
                        <td><% if(sens.getDescricao()!= null) 
                                out.print(sens.getDescricao()); %>
                        </td>
                    </tr>
                    <tr><th>Nº SÉRIE</th>
                        <td><% if(sens.getNserie()!= null) 
                                out.print(sens.getNserie()); %>
                        </td>
                    </tr>
                    <tr><th>TIPO</th>
                        <td><% if(sens.getTipo()!= null) 
                                out.print(sens.getTipo()); %>
                        </td>
                    </tr>
                    <tr><th>MEDIÇÃO</th>
                        <td><% if(sens.getMedicao()!= null) 
                                out.print(sens.getMedicao()); %>
                        </td>
                    </tr>
                    <tr><th>ALCANCE</th>
                        <td><% if(sens.getAlcance()!= 0) 
                                out.print(sens.getAlcance()); %>
                        </td>
                    </tr>
                    <tr><th>TECNOLOGIA</th>
                        <td><% if(sens.getTecnologia()!= null) 
                                out.print(sens.getTecnologia()); %>
                        </td>
                    </tr>
                    <tr><th>STATUS</th>
                        <td><% if(sens.getAtivo() == 0 )
                                out.print("INATIVO");
                               else
                                out.print("ATIVO"); %>
                        </td>
                    </tr>       
                    <tr><th>CENTRAL</th>
                        <td><% if(sens.getCentral() != null) 
                                out.print(sens.getCentral().getCodigo()); %>
                        </td>
                    </tr>  
                </table>
            </div>
            <div id="divDetalhar">        
                <form class="form_padrao" name="formDetalhar" action="" method="POST">
                    <ul>
                        <li class="li_corpo"></li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" type="submit"><a  style="text-decoration: none;" href="/siac/control?cls=Sensor&mtd=editar&id=<%= sens.getId()%>"/>Editar</a></button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="submit" onclick="return confirm('Confirma a exclusão de Sensor ${sensores.descricao}?')"><a  style="text-decoration: none;" href="/siac/control?cls=Sensor&mtd=excluir&id=<%= sens.getId()%>"/>Excluir</a></button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarForm" onclick="javascript:history.back(1)">Voltar</button>
                        </li>
                    </ul>
                </form>
            </div>
            <br>
<%          break;
        }   case "resultBusca": {
%>
            <div id="divResultBusca">        
                <form class="form_padrao" action="/siac/control?cls=Sensor&mtd=buscar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Sensores</h1></li>
                        <li class="li_corpo">
                            <input placeholder="Informe o Código para pesquisa do sensor" type="text" name="cmp_bsc_sensor"  size="50" />
                            <button class="submit_buscar" type="submit" name="btn_bsc_sensor">Buscar sensor</button>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir"><a href="principal?d=forms&a=cadastrar&f=sensor"> Incluir Sensor </a></button>
                        </li>
                    </ul>
                </form>
            </div>
            <br>
            <div class="tabResultBusca" style="width: 750px;">
                <table>
                    <tr><td colspan="6"><center>Sensores</center></td></tr>    
                    <tr>
                        <th>Código</th>
                        <th>Descrição</th>
                        <th>Status</th>
                        <th colspan="3">Ações</th>
                    </tr>
                    <%  for (Sensor sens : listaSensor) {%>
                            <tr>
                                <td><%= sens.getCodigo()%></td>
                                <td><%= sens.getDescricao()%></td>
                                <td><% if(sens.getAtivo() == 1)
                                            out.write("ATIVO");
                                        else
                                            out.write("INATIVO"); %></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=Sensor&mtd=detalhar&id=<%= sens.getId()%>" title="Detalhar"><center><img src="imagens/icones/btn_detalhar.png" alt="Detalhes"/></center></a></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=Sensor&mtd=editar&id=<%= sens.getId()%>" title="Editar"><center><img src="imagens/icones/btn_editar.png" alt="Editar"/></center></a></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=Sensor&mtd=excluir&id=<%= sens.getId()%>" title="Excluir" onclick="return confirm('Confirma a exclusão da Sensor<%= sens.getDescricao()%>?')"><center><img src="imagens/icones/btn_excluir.png" alt="Excluir"/></center></a></td>
                            </tr>
                    <%  }   %>             
                </table>
            </div>
<%          break;
        }   default:
            break;
        }
%>

