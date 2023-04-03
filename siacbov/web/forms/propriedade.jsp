<%@page import="java.util.ArrayList"%>
<%@page import="br.edu.cio.model.Propriedade"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="propriedade" scope="request" class="br.edu.cio.model.Propriedade"/>

<% 
    Propriedade p = new Propriedade();
    ArrayList<Propriedade> listaPropriedades = (ArrayList<Propriedade>) request.getAttribute("propEncontradas");
%>

<%  switch (request.getParameter("a")) {
        case "cadastrar": {
%>
            <div id="divCadastrar">    
                <form class="form_padrao" name="formCadastrar" action="/siac/control?cls=Propriedade&mtd=cadastrar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Propriedade - Cadastrar</h1></li>
                        <li class="li_corpo"><label> Nome:</label><input type="text" name="cmp_prop_nome" required=""/></li>
                        <li class="li_corpo"><label> CNPJ:</label><input type="text" name="cmp_prop_cnpj" /></li>
                        <li class="li_corpo"><label> Inscrição Estadual:</label><input type="text" name="cmp_prop_iestadual" /></li>
                        <li class="li_corpo"><label> Endereco:</label><input type="text" name="cmp_prop_endereco" required=""/></li>
                        <li class="li_corpo"><label> Municipio:</label><input type="text" name="cmp_prop_cidade" required=""/></li>
                        <li class="li_corpo"><label> UF:</label>
                            <select name="cmp_prop_uf" required="">
                                <option value="AC">AC</option><option value="AL">AL</option><option value="AM">AM</option><option value="AP">AP</option><option value="BA">BA</option><option value="CE">CE</option>
                                <option value="DF">DF</option><option value="ES">ES</option><option value="GO">GO</option><option value="MA">MA</option><option value="MG">MG</option><option value="MS">MS</option>
                                <option value="MT">MT</option><option value="PA">PA</option><option value="PB">PB</option><option value="PE">PE</option><option value="PI">PI</option><option value="PR">PR</option>
                                <option value="RJ">RJ</option><option value="RN">RN</option><option value="RO">RO</option><option value="RR">RR</option><option value="RS">RS</option><option value="SC">SC</option>
                                <option value="SE">SE</option><option value="SP">SP</option><option value="TO">TO</option>
                            </select>
                        </li>
                        <li class="li_corpo"><label> Proprietario:</label><input type="text" name="cmp_prop_dono"  required=""/></li>
                        <li class="li_corpo"><label> Area:</label><input type="text" name="cmp_prop_area" /></li>
                        <li class="li_corpo"><label> Atividade Principal:</label><input type="text" name="cmp_prop_ativ_principal" /></li>        
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
                <form class="form_padrao" name="formEditar" action="/siac/control?cls=Propriedade&mtd=editarSalvar&id=${propriedade.id}" method="POST">
                    <ul>
                        <li class="li_corpo"><h1>Propriedade - Editar</h1></li>
                        <li class="li_corpo"><label> Codigo:</label><input type="text" name="cmp_edt_prop_codigo" size="30" value="${propriedade.codigo}" readonly=""/></li>
                        <li class="li_corpo"><label> Nome:</label><input type="text" name="cmp_edt_prop_nome" size="30" value="${propriedade.nome}" /></li>
                        <li class="li_corpo"><label> CNPJ:</label><input type="text" name="cmp_edt_prop_cnpj" size="30" value="${propriedade.cnpj}" /></li>
                        <li class="li_corpo"><label> Inscrição Estadual:</label><input type="text" name="cmp_edt_prop_iestadual" size="30" value="${propriedade.inscricao_estadual}" /></li>
                        <li class="li_corpo"><label> Endereco:</label><input type="text" name="cmp_edt_prop_endereco" size="30" value="${propriedade.endereco}" /></li>
                        <li class="li_corpo"><label> Municipio:</label><input type="text" name="cmp_edt_prop_cidade" size="30" value="${propriedade.municipio}" /></li>
                        <li class="li_corpo"><label> UF:</label>
                            <select name="cmp_edt_prop_uf">
                                <option value="${propriedade.uf}">${propriedade.uf}</option>
                                <option value="AC">AC</option><option value="AL">AL</option><option value="AM">AM</option><option value="AP">AP</option><option value="BA">BA</option><option value="CE">CE</option>
                                <option value="DF">DF</option><option value="ES">ES</option><option value="GO">GO</option><option value="MA">MA</option><option value="MG">MG</option><option value="MS">MS</option>
                                <option value="MT">MT</option><option value="PA">PA</option><option value="PB">PB</option><option value="PE">PE</option><option value="PI">PI</option><option value="PR">PR</option>
                                <option value="RJ">RJ</option><option value="RN">RN</option><option value="RO">RO</option><option value="RR">RR</option><option value="RS">RS</option><option value="SC">SC</option>
                                <option value="SE">SE</option><option value="SP">SP</option><option value="TO">TO</option>
                            </select>
                        </li>
                        <li class="li_corpo"><label> Proprietario:</label><input type="text" name="cmp_edt_prop_dono" size="30" value="${propriedade.proprietario}" /></li>
                        <li class="li_corpo"><label> Area:</label><input type="text" name="cmp_edt_prop_area" size="30" value="${propriedade.area}" /></li>
                        <li class="li_corpo"><label> Atividade Principal:</label><input type="text" name="cmp_edt_prop_ativ_principal" size="30" value="${propriedade.atividade_principal}" /></li>        
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
                <form class="form_padrao" name="formBuscar" action="/siac/control?cls=Propriedade&mtd=buscar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Propriedade</h1></li>
                        <li class="li_corpo">
                            <input  placeholder="Informe o código ou nome da Propriedade" type="text" name="cmp_buscar_prop" size="50" />
                            <button class="submit_buscar" type="submit" name="btn_buscar">Buscar Propriedade</button>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir"><a href="principal?d=forms&a=cadastrar&f=propriedade"/>Incluir Propriedade</a></button>
                        </li>
                    </ul>
                </form>
            </div>
<%
            break;
        }
        case "detalhar": {
            Propriedade prop = (Propriedade) request.getAttribute("detalhesprop");
%>
            <div class="divCabecalho">        
                <ul>
                    <li class="li_titulo"><h1>Dados da Propriedade</h1></li>
                    <li></li>
                </ul>        
            </div>
            <div class="tabDetalhes" style="width:650px;">
                <table>
                    <tr><th>NOME</th>
                        <td><% if(prop.getNome() != null) 
                                out.print(prop.getNome()); %>
                        </td>
                    </tr>
                    <tr><th>CÓDIGO</th>
                        <td><% if(prop.getCodigo()!= null) 
                                out.print(prop.getCodigo()); %>
                        </td>
                    </tr>
                    <tr><th>PROPRIETÁRIO</th>
                        <td><% if(prop.getProprietario()!= null) 
                                out.print(prop.getProprietario()); %>
                        </td>
                    </tr>
                    <tr><th>ENDEREÇO</th>
                        <td><% if(prop.getEndereco()!= null) 
                                out.print(prop.getEndereco()); %>
                        </td>
                    </tr>
                    <tr><th>CIDADE</th>
                        <td><% if(prop.getMunicipio()!= null) 
                                out.print(prop.getMunicipio()); %>
                        </td>
                    </tr>
                    <tr><th>UF</th>
                        <td><% if(prop.getUf()!= null) 
                                out.print(prop.getUf()); %>
                        </td>
                    </tr>
                    <tr><th>INSCRIÇÃO ESTADUAL</th>
                        <td><% if(prop.getInscricao_estadual()!= null) 
                                out.print(prop.getInscricao_estadual()); %>
                        </td>
                    </tr>
                    <tr><th>CNPJ</th>
                        <td><% if(prop.getCnpj()!= null) 
                                out.print(prop.getCnpj()); %>
                        </td>
                    </tr>
                    <tr><th>ÁREA</th>
                        <td><% if(prop.getArea()!= 0) 
                                out.print(prop.getArea()); %>
                        </td>
                    </tr>
                    <tr><th>ATIVIDADE PRINCIPAL</th>
                        <td><% if(prop.getAtividade_principal()!= null) 
                                out.print(prop.getAtividade_principal()); %>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="divDetalhar">        
                <form class="form_padrao" name="formDetalhar" action="" method="POST">
                    <ul>
                        <li class="li_corpo"></li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" type="submit"><a href="/siac/control?cls=Propriedade&mtd=editar&id=<%= prop.getId() %>"/>Editar</a></button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="submit" onclick="return confirm('Confirma a exclusão da Propriedade ${propriedade.nome}?')"><a href="/siac/control?cls=Propriedade&mtd=excluir&id=<%= prop.getId()%>"/>Excluir</a></button>
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
                <form class="form_padrao" name="formResultBusca" action="/siac/control?cls=Propriedade&mtd=buscar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Propriedade</h1></li>
                        <li class="li_corpo">
                            <input  placeholder="Informe o código ou nome da Propriedade" type="text" name="cmp_buscar_prop" size="50" />
                            <button class="submit_buscar" type="submit" name="btn_buscar">Buscar Propriedade</button>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir"><a href="principal?d=forms&a=cadastrar&f=propriedade"/>Incluir Propriedade</a></button>
                        </li>
                    </ul>
                </form> 
            </div>
            <br>
            <div class="tabResultBusca">
                <table>
                    <tr><td colspan="6"><center>Propriedades</center></td></tr>    
                    <tr>
                        <th>Codigo</th>
                        <th>Nome</th>
                        <th>Proprietário</th>
                        <th colspan="3">Ações</th>
                    </tr>
                    <%  for(Propriedade prop : listaPropriedades) { %>
                            <tr>
                                <td><%= prop.getCodigo() %></td>
                                <td><%= prop.getNome() %></td>
                                <td><%= prop.getProprietario()%></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=Propriedade&mtd=detalhar&id=<%= prop.getId()%>" title="Detalhar"><center><img src="imagens/icones/btn_detalhar.png" alt="Detalhes"/></center></a></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=Propriedade&mtd=editar&id=<%= prop.getId() %>" title="Editar"><center><img src="imagens/icones/btn_editar.png" alt="Editar"/></center></a></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=Propriedade&mtd=excluir&id=<%= prop.getId() %>" title="Excluir" onclick="return confirm('Confirma a exclusão da Propriedade <%= prop.getNome() %>?')"><center><img src="imagens/icones/btn_excluir.png" alt="Excluir"/></center></a></td>
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