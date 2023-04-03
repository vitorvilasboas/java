
<%@page import="br.edu.cio.model.dao.mysql.MetodosUteisDAOMySQL"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="br.edu.cio.model.Cio"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="cicloEstral" scope="request" class="br.edu.cio.model.Cio" />


    
<%      
    List<Cio> listaCio = (ArrayList<Cio>) session.getAttribute("ciclosPendentes");
    List<Cio> listaBusca = (ArrayList<Cio>) request.getAttribute("ciclosBusca");
    SimpleDateFormat formatoBr = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatoBD = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatoHrBr = new SimpleDateFormat("HH:mm:ss");
    Cio cio = new Cio();
    cio = (Cio) request.getAttribute("cicloEstral");
    String registro_cio = "";
    String ult_alteracao_cio = "";
    String inicio_cio = "";
    String prev_termino_cio = "";
    String termino_cio = "";
        
    if(cio.getData_registro() != null && cio.getHora_registro() != null)
        registro_cio = (formatoBD.format(formatoBr.parse(cio.getData_registro())) + "T" + cio.getHora_registro());
    if(cio.getData_ultima_alteracao()!= null && cio.getHora_ultima_alteracao() != null)
        ult_alteracao_cio = (formatoBD.format(formatoBr.parse(cio.getData_ultima_alteracao())) + "T" + cio.getHora_ultima_alteracao());
    if(cio.getData_inicio()!= null && cio.getHora_inicio() != null)
        inicio_cio = (formatoBD.format(formatoBr.parse(cio.getData_inicio())) + "T" + cio.getHora_inicio());
    if(cio.getData_previsao_termino()!= null && cio.getHora_previsao_termino()!= null)
        prev_termino_cio = (formatoBD.format(formatoBr.parse(cio.getData_previsao_termino())) + "T" + cio.getHora_previsao_termino());
    if(cio.getData_termino()!= null && cio.getHora_termino()!= null)
        termino_cio = (formatoBD.format(formatoBr.parse(cio.getData_termino())) + "T" + cio.getHora_termino());
    
%>

<%  switch (request.getParameter("a")) {
        case "cadastrar": {
%>
            <div id="formCadastrarCio">        
                <form class="form_padrao" name="formCadastrarCio" action="/siac/control?cls=Cio&mtd=cadastrar" method="POST">
                    <ul>
                        <li class="li_titulo"><h2>Avaliar Ciclo</h2></li>
                        <li class="li_corpo"><label>Status:</label>
                            <select name="cmp_cio_status" id="cmp_cio_status" style="width: 23.2em" autofocus required="" onchange="javascript:liberar_campos_cadastrar()">
                                <option value="">Selecione...</option><option>PREVISTO</option><option>SUSPEITO</option><option>CONFIRMADO</option><option>ENCERRADO</option>
                            </select>
                        </li>
                        <li class="li_corpo"><label>Início:</label><input name="cmp_cio_dthInicio" id="cmp_cio_dthInicio" style="width: 20em" type="datetime-local" autofocus readonly=""  required="" /></li>
                        <li class="li_corpo"><label>Término:</label><input name="cmp_cio_dthTermino" id="cmp_cio_dthTermino" style="width: 20em" type="datetime-local" autofocus readonly=""  required="" /></li>
                        <li class="li_corpo"><label>Identificação:</label>
                            <select name="cmp_cio_mtd_id" id="cmp_cio_mtd_id" style="width: 23.2em" autofocus required="">
                                <option value="">Selecione...</option><option value="INFERENCIA">INFERÊNCIA</option><option value="OBSERVACAO VISUAL">OBSERVAÇÃO VISUAL</option>
                            </select>
                        </li>
                        <li class="li_corpo"><label>Animal:</label>
                            <select name="cmp_cio_animal" id="cmp_cio_animal" style="width: 23.2em" autofocus required="">
                                <option value="">Selecione...</option>
                                <%  ResultSet rs1 = MetodosUteisDAOMySQL.getAnimaisMonitorados();
                                    while (rs1.next()) {    %>
                                        <option value="<%= rs1.getInt("ani_id")%>"><%= rs1.getInt("ani_id")%> - <%= rs1.getString("ani_apelido")%></option>
                                <%  }   %>
                            </select>
                        </li>
                        <li class="li_corpo"><label>Observação:</label><textarea name="cmp_cio_observacao" id="cmp_cio_observacao" rows="5"  cols="31" ></textarea></li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" type="submit" name="enviarFormCadastrar" value="Salvar">Cadastrar</button>
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
            <div id="formAvaliarEditar">        
                <form class="form_padrao"  name="formAvaliarEditar" action="/siac/control?cls=Cio&mtd=editarSalvar&id=${cicloEstral.id}" method="POST">
                    <ul>
                        <li class="li_titulo"><h2>Editar/Reavaliar Ciclo Estral</h2></li>
                        <li class="li_corpo"><label>Status:</label>
                            <select name="cmp_cio_status" id="cmp_cio_status" style="width: 23.2em" autofocus required="" onchange="javascript:liberar_campos_avaliar()">
                                <c:if test="${cicloEstral.status != null}"><option value="${cicloEstral.status}">${cicloEstral.status}</option></c:if>
                                <c:if test="${cicloEstral.status == null}"><option value="">Selecione...</option></c:if>
                                <c:if test="${cicloEstral.status == 'PREVISTO'}">
                                    <option value="CONFIRMADO">CONFIRMADO</option><option value="DESCARTADO">DESCARTADO</option><option value="ENCERRADO">ENCERRADO</option>
                                </c:if>
                                <c:if test="${cicloEstral.status == 'SUSPEITO'}">
                                    <option value="CONFIRMADO">CONFIRMADO</option><option value="DESCARTADO">DESCARTADO</option><option value="ENCERRADO">ENCERRADO</option>
                                </c:if>
                                <c:if test="${cicloEstral.status == 'CONFIRMADO'}">
                                    <option value="ENCERRADO">ENCERRADO</option><option value="CANCELADO">CANCELADO</option>
                                </c:if>
                                <c:if test="${cicloEstral.status == 'DESCARTADO'}">
                                    <option value="CONFIRMADO">CONFIRMADO</option>
                                </c:if>
                            </select>
                        </li>
                        <li class="li_corpo"><label>Codigo:</label><input name="cmp_cio_codigo" style="width: 20em" type="text" autofocus required="" value="${cicloEstral.codigo}" readonly=""/></li>
                        <li class="li_corpo"><label>Data/Hora Registro:</label><input name="cmp_cio_dthRegistro" style="width: 20em" type="datetime-local" autofocus readonly="" required="" value="<%= registro_cio %>"/></li>
                        <li class="li_corpo"><label>Última Alteraçao:</label><input name="cmp_cio_dthUltimaAlteracao" style="width: 20em" type="datetime-local" autofocus readonly=""  required="" value="<%= ult_alteracao_cio %>"/></li>
                        <li class="li_corpo"><label>Início:</label><input name="cmp_cio_dthInicio" id="cmp_cio_dthInicio" style="width: 20em" type="datetime-local" autofocus readonly=""  required="" value="<%= inicio_cio %>"/></li>
                        <li class="li_corpo"><label>Previsão de Término:</label><input name="cmp_cio_dthPrevTermino" style="width: 20em" type="datetime-local" autofocus readonly=""  required="" value="<%= prev_termino_cio %>"/></li>
                        <li class="li_corpo"><label>Término:</label><input name="cmp_cio_dthTermino" id="cmp_cio_dthTermino" style="width: 20em" type="datetime-local" autofocus readonly=""  required="" value="<%= termino_cio %>"/></li>
                        <li class="li_corpo"><label>Duração (Em horas):</label><input name="cmp_cio_duracao" style="width: 20em" type="text" autofocus value="${cicloEstral.duracao}" readonly=""  required="" onkeypress='return soNumeros(event)'/></li>
                        <li class="li_corpo"><label>Identificação:</label><input name="cmp_cio_mtd_id" style="width: 20em" type="text" autofocus readonly=""  required="" value="${cicloEstral.metodo_id}"/></li>
                        <li class="li_corpo"><label>Método de Registro:</label><input name="cmp_cio_mtd_registro" style="width: 20em" type="text" autofocus readonly=""  required="" value="${cicloEstral.metodo_registro}"/></li>
                        <li class="li_corpo"><label>Animal:</label><input name="cmp_cio_animal" style="width: 20em" type="text" autofocus readonly=""  required="" value="${cicloEstral.animal.apelido}"/></li>
                        <li class="li_corpo"><label>Atividade Animal:</label><input name="cmp_cio_atividade" style="width: 20em" type="text" autofocus readonly=""  required="" value="${cicloEstral.atividadeAnimal.classificacao}"/></li>
                        <li class="li_corpo"><label>Observação:</label><textarea name="cmp_cio_observacao" id="cmp_cio_observacao" rows="5"  cols="31" >${cicloEstral.observacao}</textarea></li>
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
%><%
            break;
        }
        case "detalhar": {
            Cio heat = (Cio) request.getAttribute("cicloDetalhes");
%>
            <div id="divDetalhar">        
                <form class="form_padrao" name="formDetalhar" action="" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Ciclo Estral</h1></li>
                        <li>
                            <% if(heat.getStatus().equals("PREVISTO") || heat.getStatus().equals("SUSPEITO")){ %>
                                <button class="submit_incluir" type="submit"><a href="/siac/control?cls=Cio&mtd=carregar&id=<%= heat.getId() %>"/>Avaliar</a></button>
                            <% } %>
                            <button class="submit_incluir" style="margin-left: 20px;" type="submit"><a href="/siac/control?cls=Cio&mtd=editar&id=<%= heat.getId() %>"/>Editar</a></button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarForm" value="Cancelar" onclick="javascript:history.back(1)">Voltar</button>
                        </li>
                    </ul>
                </form>
            </div>
            <div class="tabDetalhes" style="width:750px;">
                <table>
                    <tr><td colspan="2">Dados do Animal</td></tr>
                    <tr><th>Codigo</th>
                        <td><% if(heat.getCodigo() != null)
                                out.write(heat.getCodigo()); %>
                        </td>
                    </tr>
                    <tr><th>Status</th>
                        <td><% if(heat.getStatus() != null)
                                out.write(heat.getStatus()); %>
                        </td>
                    </tr>
                    <tr><th>Animal</th>
                        <td><% if(heat.getAnimal() != null)
                                out.write(heat.getAnimal().getCodigo()+" - "+ heat.getAnimal().getApelido()); %>
                        </td>
                    </tr>
                    <tr><th>Data/Hora Registro</th>
                        <td><% if( heat.getData_registro() != null && heat.getHora_registro() != null )
                                out.write(heat.getData_registro() + " " + heat.getHora_registro()); %>
                        </td>
                    </tr>
                    <tr><th>Última Alteração</th>
                        <td><% if( heat.getData_ultima_alteracao()!= null && heat.getHora_ultima_alteracao()!= null )
                                out.write(heat.getData_ultima_alteracao() + " " + heat.getHora_ultima_alteracao()); %>
                        </td>
                    </tr>
                    <tr><th>Início</th>
                        <td><% if( heat.getData_inicio() != null && heat.getHora_inicio() != null )
                                out.write(heat.getData_inicio() + " " + heat.getHora_inicio()); %>
                        </td>
                    </tr>
                    <tr><th>Previsão de Término</th>
                        <td><% if( heat.getData_previsao_termino() != null && heat.getHora_previsao_termino() != null )
                                out.write(heat.getData_previsao_termino() + " " + heat.getHora_previsao_termino()); %>
                        </td>
                    </tr>
                    <tr><th>Término</th>
                        <td><% if( heat.getData_termino() != null && heat.getHora_termino() != null )
                                out.write(heat.getData_termino() + " " + heat.getHora_termino()); %>
                        </td>
                    </tr>
                    <tr><th>Duração</th>
                        <td><% if(heat.getDuracao() != 0)
                                out.write(heat.getDuracao()); %>
                        </td>
                    </tr>
                    <tr><th>Identificação</th>
                        <td><% if(heat.getMetodo_id() != null)
                                out.write(heat.getMetodo_id()); %>
                        </td>
                    </tr>
                    <tr><th>Método de Registro</th>
                        <td><% if(heat.getMetodo_registro() != null)
                                out.write(heat.getMetodo_registro()); %>
                        </td>
                    </tr>
                    <tr><th>Atividade</th>
                        <td><% if(heat.getAtividadeAnimal() != null)
                                out.write(heat.getAtividadeAnimal().getClassificacao()); %>
                        </td>
                    </tr>
                    <tr><th>Observação</th>
                        <td><% if(heat.getObservacao() != null)
                                out.write(heat.getObservacao()); %>
                        </td>
                    </tr>
                </table>
            </div>
            <br>
<%        
            break;
        }
        case "avaliarEditar": {
%>
            <div id="formAvaliarEditar">        
                <form class="form_padrao"  name="formAvaliarEditar" action="/siac/control?cls=Cio&mtd=avaliarSalvar&id=${cicloEstral.id}" method="POST">
                    <ul>
                        <li class="li_titulo"><h2>Avaliar Ciclo</h2></li>
                        <li class="li_corpo"><label>Status:</label>
                            <select name="cmp_cio_status" id="cmp_cio_status" style="width: 23.2em" autofocus required="" onchange="javascript:liberar_campos_avaliar()">
                                <c:if test="${cicloEstral.status != null}"><option value="${cicloEstral.status}">${cicloEstral.status}</option></c:if>
                                <c:if test="${cicloEstral.status == null}"><option value="">Selecione...</option></c:if>
                                <c:if test="${cicloEstral.status != 'CONFIRMADO'}"><option value="CONFIRMADO">CONFIRMADO</option></c:if>
                                <c:if test="${cicloEstral.status != 'DESCARTADO'}"><option value="DESCARTADO">DESCARTADO</option></c:if>
                                <c:if test="${cicloEstral.status != 'ENCERRADO'}"><option value="ENCERRADO">ENCERRADO</option></c:if>
                            </select>
                        </li>
                        <li class="li_corpo"><label>Codigo:</label><input name="cmp_cio_codigo" style="width: 20em" type="text" autofocus required="" value="${cicloEstral.codigo}" readonly=""/></li>
                        <li class="li_corpo"><label>Data/Hora Registro:</label><input name="cmp_cio_dthRegistro" style="width: 20em" type="datetime-local" autofocus readonly="" required="" value="<%= registro_cio %>"/></li>
                        <li class="li_corpo"><label>Última Alteraçao:</label><input name="cmp_cio_dthUltimaAlteracao" style="width: 20em" type="datetime-local" autofocus readonly=""  required="" value="<%= ult_alteracao_cio %>"/></li>
                        <li class="li_corpo"><label>Início:</label><input name="cmp_cio_dthInicio" id="cmp_cio_dthInicio" style="width: 20em" type="datetime-local" autofocus readonly=""  required="" value="<%= inicio_cio %>"/></li>
                        <li class="li_corpo"><label>Previsão de Término:</label><input name="cmp_cio_dthPrevTermino" style="width: 20em" type="datetime-local" autofocus readonly=""  required="" value="<%= prev_termino_cio %>"/></li>
                        <li class="li_corpo"><label>Término:</label><input name="cmp_cio_dthTermino" id="cmp_cio_dthTermino" style="width: 20em" type="datetime-local" autofocus readonly=""  required="" value="<%= termino_cio %>"/></li>
                        <li class="li_corpo"><label>Duração (Em horas):</label><input name="cmp_cio_duracao" style="width: 20em" type="text" autofocus value="${cicloEstral.duracao}" readonly=""  required="" onkeypress='return soNumeros(event)'/></li>
                        <li class="li_corpo"><label>Identificação:</label><input name="cmp_cio_mtd_id" style="width: 20em" type="text" autofocus readonly=""  required="" value="${cicloEstral.metodo_id}"/></li>
                        <li class="li_corpo"><label>Metodo de Registro:</label><input name="cmp_cio_mtd_registro" style="width: 20em" type="text" autofocus readonly=""  required="" value="${cicloEstral.metodo_registro}"/></li>
                        <li class="li_corpo"><label>Animal:</label><input name="cmp_cio_animal" style="width: 20em" type="text" autofocus readonly=""  required="" value="${cicloEstral.animal.apelido}"/></li>
                        <li class="li_corpo"><label>Atividade Animal:</label><input name="cmp_cio_atividade" style="width: 20em" type="text" autofocus readonly=""  required="" value="${cicloEstral.atividadeAnimal.classificacao}"/></li>
                        <li class="li_corpo"><label>Observação:</label><textarea name="cmp_cio_observacao" id="cmp_cio_observacao" rows="5"  cols="31" >${cicloEstral.observacao}</textarea></li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" type="submit" name="enviarFormAvaliar" value="Salvar">Avaliar</button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarFormAvaliar" value="Cancelar" onclick="javascript:history.back(1)">Cancelar</button>
                        </li>
                    </ul>
                </form>
            </div>
<%
            break;
        }
        case "avaliar": {
        %>
            <div id="divAvaliar">        
                <!--<form class="form_padrao" name="formAvaliar" action="/siac/control?cls=Cio&mtd=filtrar" method="POST"> QUANDO FOR FILTRAR -->
                <form class="form_padrao" name="formAvaliar" action="/siac/control?cls=Cio&mtd=listarPendentes" method="POST">
                    <ul>
                        <div id="filtraCio1">
                            <li class="li_titulo"><h1>Ciclos Estrais - Avaliar</h1></li>
                            <li class="li_corpo"><label>Filtrar Por:</label>
                                <select name="cmp_cio_filtrar" style="width: 23.5em" autofocus onchange="mostrar_campos_filtro()">
                                    <option value="">Selecione o tipo de filtro...</option>
                                    <option value="DTREGISTRO">Data de Registro</option>
                                    <option value="DTALTERACAO">Data da Última Alteração</option>
                                    <option value="ANIMAL">Animal</option>
                                    <option value="STATUS">Status do Cio</option>
                                    <option value="CODCIO">Código do Cio</option>
                                </select>
                            </li>  
                        </div>
                        <div id="filtrarCio2"></div>
                    </ul>
                </form>
            </div>
            <br>
            <div class="tabResultBusca">
                <table>
                    <tr><td colspan="15"><center>Ciclos Estrais com Avaliação Pendentes</center></td></tr>    
                    <tr>
                        <th>Codigo</th>
                        <th>Animal</th>
                        <th>Status</th>
                        <th>Última Alteração</th>
                        <th>Data/Hora Registro</th>
                        <th>Observação</th>
                        <th></th>
                    </tr>
                    <%  for (Cio estro : listaCio) {%>
                        
                            <tr>
                                <td><% if(estro.getId() != 0) %>
                                        <%= estro.getCodigo() %></td>
                                <td><% if(estro.getAnimal().getApelido() != null)
                                        out.write(estro.getAnimal().getApelido()); %></td>
                                <td><% if(estro.getStatus() != null)
                                        out.write(estro.getStatus()); %></td>
                                <td><% if(estro.getData_ultima_alteracao() != null && estro.getHora_ultima_alteracao() !=null )
                                        out.write(estro.getData_ultima_alteracao() +" "+ estro.getHora_ultima_alteracao()); %></td>
                                <td><%  if(estro.getData_registro() != null && estro.getHora_registro() !=null)
                                        out.write(estro.getData_registro() +" "+ estro.getHora_registro()); %></td>    
                                <td><%  if(estro.getObservacao() != null )
                                        out.write(estro.getObservacao()); %></td>         
                                <td><a href="/siac/control?cls=Cio&mtd=carregar&id=<%= estro.getId() %>">Avaliar</a></td>
                            </tr>
                    <% }  %>             
                </table> 
            </div>
<%
            break;
        }
        case "buscar": {
%>
            <div id="formBuscarCio">        
                <form class="form_padrao"  name="formBuscarCio" action="/siac/control?cls=Cio&mtd=buscar" method="POST" >
                    <ul>
                        <div id="consultaCio1" >
                        <li class="li_titulo"><h1>Consultar Ciclos Registrados</h1></li>
                        <li class="li_corpo"><label>Método de busca:</label> 
                            <select name="cmp_mtd_busca_cio" style="width: 23.5em" autofocus onchange="mostra_campos_especificos_da_busca()">
                                <option value="BUSCARPORCODIGOCIO">Buscar pelo Código do Cio</option>
                                <option value="BUSCARPORDATAREGISTRO">Buscar por Data/Hora de Registro</option>
                                <option value="BUSCARPORDATAULTIMAALTERACAO">Buscar por Data da Ultima Alteração</option>
                                <option value="BUSCARPORDATAINICIOCIO">Buscar por Data de Inicio do Cio</option>
                                <option value="BUSCARPORANIMAL">Buscar por Animal Relacionado</option>
                            </select>
                        </li>
                        </div>
                        <div id="consultaCio2">
                            <li class="li_corpo" id="metodo_busca"><label>Código do Ciclo:</label>
                            <input id="cperfil" type="text" name="cmp_cio_busca" style="width: 20.2em"  onkeypress='return soNumeros(event)'/>
                            &nbsp;&nbsp;&nbsp;&nbsp;<button class="submit_incluir" type="submit" name="btn_bsc_cio">Buscar Registro</button>
                        </div>
                        <li class="li_ultimo">
                            <button class="submit_incluir"><a href="principal?d=forms&a=cadastrar&f=cio"/>Novo Ciclo Estral</a></button>
                        </li>
                    </ul>
                </form>
            </div>    
<%
            break;
        }
        case "resultBusca": {
%>
            <div id="formBuscarCio">        
                <form class="form_padrao"  name="formBuscarCio" action="/siac/control?cls=Cio&mtd=buscar" method="POST" >
                    <ul>
                        <div id="consultaCio1" >
                        <li class="li_titulo"><h1>Consultar Ciclos Registrados</h1></li>
                        <li class="li_corpo"><label>Método de busca:</label> 
                            <select name="cmp_mtd_busca_cio" style="width: 23.5em" autofocus onchange="mostra_campos_especificos_da_busca()">
                                <option value="BUSCARPORCODIGOCIO">Buscar pelo Código do Cio</option>
                                <option value="BUSCARPORDATAREGISTRO">Buscar por Data/Hora de Registro</option>
                                <option value="BUSCARPORDATAULTIMAALTERACAO">Buscar por Data da Ultima Alteração</option>
                                <option value="BUSCARPORDATAINICIOCIO">Buscar por Data de Inicio do Cio</option>
                                <option value="BUSCARPORANIMAL">Buscar por Animal Relacionado</option>
                            </select>
                        </li>
                        </div>
                        <div id="consultaCio2">
                            <li class="li_corpo" id="metodo_busca"><label>Código do Ciclo:</label>
                            <input id="cperfil" type="text" name="cmp_cio_busca" style="width: 20.2em"  onkeypress='return soNumeros(event)'/>
                            &nbsp;&nbsp;&nbsp;&nbsp;<button class="submit_incluir" type="submit" name="btn_bsc_cio">Buscar Registro</button>
                        </div>
                        <li class="li_ultimo">
                            <button class="submit_incluir"><a href="principal?d=forms&a=cadastrar&f=cio"/>Novo Ciclo Estral</a></button>
                        </li>
                    </ul>
                </form>
            </div>
            <br>
            <div class="tabResultBusca">
                <table>
                    <tr><td colspan="15"><center>Ciclos Estrais com Avaliação Pendentes</center></td></tr>    
                    <tr>
                        <th>Codigo</th>
                        <th>Animal</th>
                        <th>Status</th>
                        <th>Última Alteração</th>
                        <th>Data/Hora Registro</th>
                        <th colspan="3">Operações</th>
                    </tr>
                    <%  for (Cio estro : listaBusca) {%>
                        
                            <tr>
                                <td><% if(estro.getId() != 0) %>
                                        <%= estro.getCodigo() %></td>
                                <td><% if(estro.getAnimal().getApelido() != null)
                                        out.write(estro.getAnimal().getApelido()); %></td>
                                <td><% if(estro.getStatus() != null)
                                        out.write(estro.getStatus()); %></td>
                                <td><% if(estro.getData_ultima_alteracao() != null && estro.getHora_ultima_alteracao() !=null )
                                        out.write(estro.getData_ultima_alteracao() +" "+ estro.getHora_ultima_alteracao()); %></td>
                                <td><%  if(estro.getData_registro() != null && estro.getHora_registro() !=null)
                                        out.write(estro.getData_registro() +" "+ estro.getHora_registro()); %></td>    
                                <td>
                                    <a href="/siac/control?cls=Cio&mtd=detalhar&id=<%= estro.getId()%>">Detalhes</a></td>
                                <td>
                                    <% if(!estro.getStatus().equals("ENCERRADO") && !estro.getStatus().equals("CANCELADO")) { %>
                                        <a href="/siac/control?cls=Cio&mtd=editar&id=<%= estro.getId()%>">Editar</a>
                                    <% }else{ 
                                        out.write("");
                                    }
                                    %>
                                </td>
                                <td>
                                    <% if(estro.getStatus().equals("PREVISTO") || estro.getStatus().equals("SUSPEITO")) { %>
                                        <a href="/siac/control?cls=Cio&mtd=carregar&id=<%= estro.getId() %>">Avaliar</a>
                                    <% }else{ 
                                        out.write("");
                                    }
                                    %>
                                </td>
                            </tr>
                    <% }  %>             
                </table> 
            </div>
<%
            break;
        }
        default:
            break;
    }
%>
<script language="javascript" type="text/javascript">
    function liberar_campos_avaliar() {
        var estado = document.formAvaliarEditar.cmp_cio_status[document.formAvaliarEditar.cmp_cio_status.selectedIndex].value //variavel q recebe o valor do select
        //var estado = document.getElementsByName('cmp_cio_status').value;
        switch(estado){  
            case 'CONFIRMADO':{
                //cmp_cio_dthInicio.readOnly=false;//cmp_cio_observacao.readOnly=false;
                document.getElementById('cmp_cio_dthInicio').readOnly = false;//ou document.getElementById("cmp_cio_observacao").removeAttribute('disabled');
                break;
            }
            case 'SUSPEITO':{
                document.getElementById('cmp_cio_dthInicio').readOnly = true;
                document.getElementById('cmp_cio_dthTermino').readOnly = true;
                break;
            }
            case 'PREVISTO':{
                document.getElementById('cmp_cio_dthInicio').readOnly = true;
                document.getElementById('cmp_cio_dthTermino').readOnly = true;
                break;
            }
            case 'ENCERRADO':{
                document.getElementById('cmp_cio_dthInicio').readOnly = false;
                document.getElementById('cmp_cio_dthTermino').readOnly = false;
                break;
            }
            case 'DESCARTADO':{
                document.getElementById('cmp_cio_dthInicio').readOnly = true;
                document.getElementById('cmp_cio_dthTermino').readOnly = true;
                break;
            }
        }
    }
    
    function liberar_campos_cadastrar() {
        var estado = document.formCadastrarCio.cmp_cio_status[document.formCadastrarCio.cmp_cio_status.selectedIndex].value //variavel q recebe o valor do select
        switch(estado){  
            case 'CONFIRMADO':{
                document.getElementById('cmp_cio_dthInicio').readOnly = false;//ou document.getElementById("cmp_cio_observacao").removeAttribute('disabled');
                break;
            }
            case 'SUSPEITO':{
                document.getElementById('cmp_cio_dthInicio').readOnly = true;
                document.getElementById('cmp_cio_dthTermino').readOnly = true;
                break;
            }
            case 'PREVISTO':{
                document.getElementById('cmp_cio_dthInicio').readOnly = true;
                document.getElementById('cmp_cio_dthTermino').readOnly = true;
                break;
            }
            case 'ENCERRADO':{
                document.getElementById('cmp_cio_dthInicio').readOnly = false;
                document.getElementById('cmp_cio_dthTermino').readOnly = false;
                break;
            }
        }
    }
    
    
    function mostrar_campos_filtro() {
        var cmp_cio_filtrar = document.formAvaliar.cmp_cio_filtrar[document.formAvaliar.cmp_cio_filtrar.selectedIndex].value //variavel q recebe o valor do select
        switch(cmp_cio_filtrar){
            case 'DTREGISTRO':{
                    $("#metodo_filtro").remove();
                    var  novoscampos =   '<li class="li_ultimo" id="metodo_filtro"><label>Data do Registro ou Anterior:</label>';  
                        novoscampos +=  '<input name="cmp_cio_filtro" style="width: 20em" type="datetime-local" autofocus required="" />'; 
                        novoscampos += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="submit_incluir" type="submit" name="btn_filtrar">Filtrar</button></li>';
                    $("#filtrarCio2").append(novoscampos);
                    return false; 
            }
            case 'DTALTERACAO':{ 
                    $("#metodo_filtro").remove();
                    var  novoscampos =   '<li class="li_ultimo" id="metodo_filtro"><label>Data da Última Alteração ou Anterior:</label>';     
                        novoscampos +=  '<input name="cmp_cio_filtro" style="width: 20em" type="datetime-local" autofocus required="" />';
                        novoscampos += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="submit_incluir" type="submit" name="btn_filtrar">Filtrar</button></li>';
                    $("#filtrarCio2").append(novoscampos); 
                    return false;
            }
            case 'ANIMAL':{
                    $("#metodo_filtro").remove();
                    var  novoscampos = '<li class="li_ultimo" id="metodo_filtro"><label>Apelido ou RGN do Animal:</label>'
                        novoscampos += '<input type="text" name="cmp_cio_filtro"  style="width: 20em" autofocus required=""/>';
                        novoscampos += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="submit_incluir" type="submit" name="btn_filtrar">Filtrar</button></li>';
                    $("#filtrarCio2").append(novoscampos);
                    return false;
            }
            case 'STATUS':{
                    $("#metodo_filtro").remove();
                    var  novoscampos =   '<li class="li_ultimo" id="metodo_filtro"><label>Status do Cio:</label>';
                        novoscampos +=  '<select name="cmp_cio_filtro" required="" style="width: 23.5em" ><option value="">Selecione...</option><option>PREVISTO</option><option>SUSPEITO</option></select>';
                        novoscampos += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="submit_incluir" type="submit" name="btn_filtrar">Filtrar</button></li>';
                    $("#filtrarCio2").append(novoscampos);
                    return false;
            }
            case 'CODCIO':{
                    $("#metodo_filtro").remove();
                    var  novoscampos = '<li class="li_ultimo" id="metodo_filtro"><label>Código do Cio:</label>'
                        novoscampos += '<input type="text" name="cmp_cio_filtro"  style="width: 20em" autofocus required=""/>';
                        novoscampos += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="submit_incluir" type="submit" name="btn_filtrar">Filtrar</button></li>';
                    $("#filtrarCio2").append(novoscampos);
                    return false; 
            }
            case '':{
                    $("#metodo_filtro").remove();
            }
        }
    }
    

    function mostra_campos_especificos_da_busca() {
        var cmp_mtd_busca_cio = document.formBuscarCio.cmp_mtd_busca_cio[document.formBuscarCio.cmp_mtd_busca_cio.selectedIndex].value     //variavel q recebe o valor do select campo_perfil
        switch(cmp_mtd_busca_cio){   //analisa a variavel q contém o valor do select campo_perfil
            case 'BUSCARPORCODIGOCIO':{   //caso educador...
                    $("#metodo_busca").remove();     //remove a div id=dados_perfil caso ela já exista
                    var  novoscampos =   '<li  class="li_corpo" id="metodo_busca"><label>Código do Ciclo:</label>';      //variavel com os campos especificos
                        novoscampos +=  '<input id="cperfil" type="text" name="cmp_cio_busca"  style="width: 20.2em" />';    //concatena a variavel novoscampos
                        novoscampos += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="submit_incluir" type="submit" name="btn_bsc_cio">Buscar Registro</button>';
                    $("#consultaCio2").append(novoscampos);    //executa a variavel novoscampos e insere os campos na div id perfil
                    break; 
            }
            case 'BUSCARPORDATAREGISTRO':{  //caso estudante...
                    $("#metodo_busca").remove();     //remove a div id=dados_perfil caso ela já exista
                    var  novoscampos =   '<li  class="li_corpo"  id="metodo_busca"><label>Data de Registro:</label>';     //variavel com os campos especificos
                        novoscampos +=  '<input name="cmp_cio_busca" style="width: 20em" type="datetime-local" autofocus required="" />';     //concatena a variavel novoscampos
                        novoscampos += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="submit_incluir" type="submit" name="btn_bsc_cio">Buscar Registro</button></li>';
                    $("#consultaCio2").append(novoscampos);    //executa a variavel novoscampos e insere os campos na div id=perfil
                    break;
            }
            case 'BUSCARPORDATAULTIMAALTERACAO':{  //caso estudante...
                    $("#metodo_busca").remove();     //remove a div id=dados_perfil caso ela já exista
                    var  novoscampos =   '<li  class="li_corpo"  id="metodo_busca"><label>Data da Última Alteração:</label>';     //variavel com os campos especificos
                        novoscampos +=  '<input name="cmp_cio_busca" style="width: 20em" type="datetime-local" autofocus required="" />';     //concatena a variavel novoscampos
                        novoscampos += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="submit_incluir" type="submit" name="btn_bsc_cio">Buscar Registro</button></li>';
                    $("#consultaCio2").append(novoscampos);    //executa a variavel novoscampos e insere os campos na div id=perfil
                    break;
            }
            case 'BUSCARPORDATAINICIOCIO':{  //caso estudante...
                    $("#metodo_busca").remove();     //remove a div id=dados_perfil caso ela já exista
                    var  novoscampos =   '<li  class="li_corpo"  id="metodo_busca"><label>Início do Ciclo:</label>';     //variavel com os campos especificos
                        novoscampos +=  '<input name="cmp_cio_busca" style="width: 20em" type="datetime-local" autofocus required="" />';     //concatena a variavel novoscampos
                        novoscampos += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="submit_incluir" type="submit" name="btn_bsc_cio">Buscar Registro</button></li>';
                    $("#consultaCio2").append(novoscampos);    //executa a variavel novoscampos e insere os campos na div id=perfil
                    break;
            }
            case 'BUSCARPORANIMAL':{   //caso educador...
                    $("#metodo_busca").remove();     //remove a div id=dados_perfil caso ela já exista
                    var  novoscampos =   '<li  class="li_corpo"  id="metodo_busca"><label>Apelido ou RGN do Animal:</label>';      //variavel com os campos especificos
                        novoscampos +=  '<input id="cmp_cio_busca" type="text" style="width: 20.2em" name="cmp_cio_busca" class="required"/>';    //concatena a variavel novoscampos
                        novoscampos += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="submit_incluir" type="submit" name="btn_bsc_cio">Buscar Registro</button></li>';
                    $("#consultaCio2").append(novoscampos);    //executa a variavel novoscampos e insere os campos na div id perfil
                    break;
            }
            case '':{
                    $("#metodo_busca").remove();     //remove a div id=dados_perfil caso ela já exista
                        var  novoscampos =   '<li id="metodo_busca">&nbsp;&nbsp;&nbsp;&nbsp;<button class="submit_incluir" type="submit" name="btn_bsc_cio">Buscar Registro</button></li>';
                    $("#consultaCio2").append(novoscampos);
            }
        }
    }
        
    
</script>


<!--
                        
































<li class="li_corpo"><label>Animal:</label>
                            <select name="cmp_cio_animal" id="cmp_cio_animal" style="width: 23.2em" autofocus readonly="" >
                                <//c:if test="$/{cicloEstral.animal != null}"><option value="$/{cicloEstral.animal.id}">$/{cicloEstral.animal.apelido}</option><//c:if>
                                <//c:if test="$/{cicloEstral.animal == null}"><option value="">Selecione...</option><//c:if>
                                </%  //ResultSet rs1 = MetodosUteisDAOMySQL.getAnimal();
                                    //while (rs1.next()) {    %>
                                        <option value="</%= rs1.getString("ani_id")%>">  </%= rs1.getString("ani_apelido")%> </option>
                                </%  //}   %>
                            </select>
                        </li>

<li class="li_corpo"><label>Identificação:</label>
                            <select name="cmp_cio_mtd_id" id="cmp_cio_mtd_id" style="width: 23.2em" autofocus readonly="" >
                                <//c:if test="$/{cicloEstral.metodo_id != null}"><option value="$/{cicloEstral.metodo_id}">$/{cicloEstral.metodo_id}</option><//c:if>
                                <//c:if test="$/{cicloEstral.metodo_id == null}"><option value="">Selecione...</option><//c:if>
                                <//c:if test="$/{cicloEstral.metodo_id != 'INFERENCIA'}"><option value="INFERENCIA">INFERENCIA</option><//c:if>
                                <//c:if test="$/{cicloEstral.status != 'OBSERVACAO VISUAL'}"><option value="OBSERVACAO VISUAL">OBSERVAÇÃO VISUAL</option><//c:if>
                            </select>
                        </li>
                        
                        <li class="li_corpo"><label>Método de Registro:</label>
                            <select name="cmp_cio_mtd_registro" id="cmp_mtd_reg" style="width: 23.2em" autofocus readonly="" >
                                <//c:if test="$/{cicloEstral.metodo_registro != null}"><option value="$/{cicloEstral.metodo_registro}">$/{cicloEstral.metodo_registro}</option><//c:if>
                                <//c:if test="$/{cicloEstral.metodo_registro == null}"><option value="">Selecione...</option><//c:if>
                                <//c:if test="$/{cicloEstral.metodo_registro != 'MANUAL'}"><option value="MANUAL">MANUAL</option><//c:if>
                                <//c:if test="$/{cicloEstral.metodo_registro != 'AUTOMATICO'}"><option value="AUTOMATICO">AUTOMATICO</option><//c:if>
                            </select>
                        </li>

-->