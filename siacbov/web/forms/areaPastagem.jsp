<%@page import="br.edu.cio.model.dao.mysql.MetodosUteisDAOMySQL"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="br.edu.cio.model.dao.mysql.PropriedadeDAOMySQL"%>
<%@page import="br.edu.cio.model.Propriedade"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.edu.cio.model.AreaPastagem"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    AreaPastagem ap = new AreaPastagem();
    ArrayList<AreaPastagem> listaAreasPastagem = (ArrayList<AreaPastagem>) request.getAttribute("pastosEncontrados");
%>

<%  switch (request.getParameter("a")) {
        case "cadastrar": {
%>
            <div id="divCadastrar"><!--    -->
                <form class="form_padrao" name="formCadastrar" action="/siac/control?cls=AreaPastagem&mtd=cadastrar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Área de Pastagem - Cadastrar</h1></li>
                        <li class="li_corpo"><label> Nome:</label><input type="text" name="cmp_pasto_nome" size="30" required=""/></li>
                        <li class="li_corpo"><label> Área:</label><input type="text" name="cmp_pasto_area" size="30" required=""/></li>
                        <li class="li_corpo">
                            <label class="li_corpo"> Propriedade: </label>
                            <select name="cmp_pasto_propriedade">
                                <option value="">Selecione...</option>
                                <%  ResultSet rs = MetodosUteisDAOMySQL.getPropriedade();
                                    while (rs.next()) {%>
                                        <option value="<%= rs.getString("prop_id")%>">  <%= rs.getString("prop_codigo")%> - <%= rs.getString("prop_nome")%> </option>
                                <%  }   %>
                            </select>
                        </li>
                        <li class="li_corpo"><label> Observação:</label><textarea name="cmp_pasto_observacao" rows="5"  cols="34"></textarea></li>
                        <!--<li class="li_corpo"><label> Imagem:</label><input type="text" name="cmp_pasto_imagem" size="30" /></li>-->
                        <li class="li_corpo"><label style="width: 16em;">Imagem:</label>
                            <input name="cmp_pasto_imagem" type="file" accept="image/*" onchange="openDialogFunc(this)" autofocus /><br>  
                        </li>
                        <div ><img style=" padding-left: 7px; width: 5em; height: 4.8em; border: 0px;" name="imagem" src=""/></div>
                                  
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
                <form class="form_padrao" name="formEditar" action="/siac/control?cls=AreaPastagem&mtd=editarSalvar&id=${pasto.id}" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Área de Pastagem - Editar</h1></li>
                        <li class="li_corpo"><label> Codigo:</label><input type="text" name="cmp_edt_pasto_codigo" size="30" value="${pasto.codigo}" readonly="yes"/></li>
                        <li class="li_corpo"><label> Nome:</label><input type="text" name="cmp_edt_pasto_nome" size="30" value="${pasto.nome}" required="" /></li>
                        <li class="li_corpo"><label> Área:</label><input type="text" name="cmp_edt_pasto_area" size="30" value="${pasto.area}" required="" /></li>
                        <li class="li_corpo"><label>Propriedade:</label> 
                            <select name="cmp_edt_pasto_propriedade" style="width: 23.2em" type="text" autofocus required="" value="">
                                <c:if test="${pasto.propriedade != null}"><option value="${pasto.propriedade.id}">${pasto.propriedade.codigo} - ${pasto.propriedade.nome}</option></c:if>
                                <c:if test="${pasto.propriedade == null}"><option value="">Selecione...</option></c:if>
                                <%  ResultSet rs = MetodosUteisDAOMySQL.getPropriedade();
                                    while (rs.next()) { %>
                                        <option value="<%= rs.getString("prop_id")%>"> <%= rs.getString("prop_codigo")%> - <%= rs.getString("prop_nome")%> </option>
                                <%  }   %>
                            </select>
                        </li>
                        <li class="li_corpo"><label> Observação:</label><textarea type="text" name="cmp_edt_pasto_observacao"  rows="5"  cols="30.9" >${pasto.observacao}</textarea></li>
                        <!--<li class="li_corpo"><label> Imagem:</label><input  type="file" name="cmp_edt_pasto_imagem" size="30" value="src=$/{pasto.imagem}"/></li>--> 
                        <li class="li_corpo"><label>Imagem:</label><input name="cmp_edt_pasto_imagem" type="file" accept="image/*" value="${pasto.imagem}" onchange="openDialogFunc(this)" autofocus /></li>            
                        <div ><img style=" padding-left: 7px; width: 5em; height: 4.8em; border: 0px;" name="imagem" src="${pasto.imagem}"/></div>
                        
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
        case "buscar": {
%>
            <div id="divBuscar">        
                <form class="form_padrao" name="formBuscar" action="/siac/control?cls=AreaPastagem&mtd=buscar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Área de Pastagem</h1></li>
                        <li class="li_corpo">
                            <input  placeholder="Informe o código ou nome do pasto"type="text" name="cmp_buscar_pasto" size="60" />
                            <button class="submit_buscar" type="submit" name="btn_buscar">Buscar Pasto</button>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir"><a href="principal?d=forms&a=cadastrar&f=areaPastagem"/>Incluir Pasto</a></button>
                        </li>
                    </ul>
                </form>
                <br>
            </div>
<%
            break;
        }
        case "detalhar": {
            AreaPastagem pasto = (AreaPastagem) request.getAttribute("detalhespasto");
%>
            <div class="divCabecalho">        
                <ul>
                    <li class="li_titulo"><h1>Dados do Pasto</h1></li>
                    <li></li>
                </ul>        
            </div>
            <div class="tabDetalhes" style="width:650px;">
                <table>
                    <tr><th>CÓDIGO</th>
                        <td><% if(pasto.getCodigo()!= null) 
                                out.print(pasto.getCodigo()); %>
                        </td>
                    </tr>
                    <tr><th>DESCRIÇÃO</th>
                        <td><% if(pasto.getNome()!= null) 
                                out.print(pasto.getNome()); %>
                        </td>
                    </tr>
                    <tr><th>ÁREA</th>
                        <td><% if(pasto.getArea()!= 0) 
                                out.print(pasto.getArea()); %>
                        </td>
                    </tr>
                    <tr><th>Propriedade</th>
                        <td><% if(pasto.getPropriedade()!= null) 
                                out.print(pasto.getPropriedade().getNome()); %>
                        </td>
                    </tr>
                    <tr><th>OBSERVAÇÃO</th>
                        <td><% if(pasto.getObservacao()!= null) 
                                out.print(pasto.getObservacao()); %>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="divDetalhar">        
                <form class="form_padrao" name="formDetalhar" action="/siac/control?cls=AreaPastagem&mtd=buscar" method="POST">
                    <ul>
                        <li class="li_corpo"></li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" type="submit" ><a href="/siac/control?cls=AreaPastagem&mtd=editar&id=<%= pasto.getId()%>"/>Editar</a></button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="submit" onclick="return confirm('Confirma a exclusão de Area de Pastagem ${pasto.nome}?')"><a href="/siac/control?cls=AreaPastagem&mtd=excluir&id=<%= pasto.getId()%>"/>Excluir</a></button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarForm" onclick="javascript:history.back(1)">Voltar</button>
                        </li>
                    </ul>
                </form>
            </div>
            <br>
<%
            break;
        }
        case "resultBusca": {
%>
            <div id="divResultBusca">        
                <form class="form_padrao" name="formResultBusca" action="/siac/control?cls=AreaPastagem&mtd=buscar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Área de Pastagem</h1></li>
                        <li class="li_corpo">
                            <input placeholder="Informe o código ou nome do pasto"type="text" name="cmp_buscar_pasto" size="60" />
                            <button class="submit_buscar" type="submit" name="btn_buscar">Buscar Pasto</button>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir"><a href="principal?d=forms&a=cadastrar&f=areaPastagem"/>Incluir Pasto</a></button>
                        </li>
                    </ul>
                </form>
            </div>
            <br>
            <div class="tabResultBusca" style="width: 650px;">
                <table >
                    <tr>
                        <td colspan="7"><center>Áreas de Pastagem</center></td>
                    </tr>
                    <tr>
                        <th>Código</th>
                        <th>Nome</th>
                        <th>Área</th>
                        <th>Propriedade</th>
                        <th colspan="3"></th>
                    </tr>
                    <%  for (AreaPastagem pasto : listaAreasPastagem) {%>
                            <tr>
                                <td><%= pasto.getCodigo()%></td>
                                <td><%= pasto.getNome()%></td>
                                <td><%= pasto.getArea()%></td>
                                <td><%= pasto.getPropriedade().getNome()%></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=AreaPastagem&mtd=detalhar&id=<%= pasto.getId()%>" title="Detalhar"><center><img src="imagens/icones/btn_detalhar.png" alt="Detalhes"/></center></a></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=AreaPastagem&mtd=editar&id=<%= pasto.getId()%>" title="Editar"><center><img src="imagens/icones/btn_editar.png" alt="Editar"/></center></a></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=AreaPastagem&mtd=excluir&id=<%= pasto.getId()%>"  title="Excluir" onclick="return confirm('Confirma a exclusão do Pasto <%= pasto.getNome()%>?')"><center><img src="imagens/icones/btn_excluir.png" alt="Excluir"/></center></a></td>
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