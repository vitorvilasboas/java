<%@page import="br.edu.cio.model.dao.mysql.MetodosUteisDAOMySQL"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.edu.cio.model.Funcionario"%>
<%@page import="br.edu.cio.model.dao.mysql.FuncionarioDAOMySQL"%>
<%@page import="br.edu.cio.model.dao.FuncionarioDAO"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="funcionarios" scope="request" class="br.edu.cio.model.Funcionario"/>

<%
    Funcionario f = new Funcionario();
    ArrayList<Funcionario> listaFuncionario = (ArrayList<Funcionario>) request.getAttribute("funcionariosEncontrados");
%>

<%  switch (request.getParameter("a")) {
        case "cadastrar": {
%>
            <div id="divCadastrar">        
                <form class="form_modelo1" name="formCadastrar" action="/siac/control?cls=Funcionario&mtd=cadastrar" method="POST">
                    <ul>
                        <li class="li_titulo" style="border-bottom:1px solid #777; margin-top: 0px; padding:7px; position:relative;"><h1>Funcionário - Cadastrar</h1></li>

                        <div class="coluna1">
                            <li class="li_corpo"><label>Nome:</label><input type="text" name="func_nome" required="" /></li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>CPF:</label>
                                <input type="text" name="func_cpf" required pattern="^(\d{3}\.\d{3}\.\d{3}-\d{2})|(\d{11})$" placeholder="888.888.888-88" /><br />
                            </li>
                        </div>

                        <div class="coluna1">
                            <li class="li_corpo"><label>RG:</label>
                                <input type="text" name="func_rg" required="" name="numbers" pattern="[0-9]+$" max="35" min="1" title="Preencha com números!" />
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Órgão emissor:</label>
                                <input type="text" name="func_orgao_emissor" required="" />
                            </li>
                        </div>

                        <div class="coluna1">
                            <li class="li_corpo"><label>Nome do Pai:</label>
                                <input type="text" name="func_pai" required="" />
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Nome da Mãe:</label>
                                <input type="text" name="func_mae" required="" />
                            </li>
                        </div>

                        <div class="coluna1">
                            <li class="li_corpo"><label>Data de Nascimento:</label>
                                <input type="date" name="func_dt_nascimento" required="" />
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Data de Admissão:</label>
                                <input type="date" name="func_dt_admissao" required="" />
                            </li>
                        </div>

                        <div class="coluna1">
                            <li class="li_corpo"><label>Telefone:</label><br />
                                <input type="tel" name="func_fone" required="" maxlength="15"  pattern="\([0-9]{2}\) [0-9]{4,6}-[0-9]{3,4}$" /> 
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Endereço:</label>
                                <input type="text" name="func_endereco" required="" />
                            </li>
                        </div>

                        <div class="coluna1">
                            <li class="li_corpo"><label>Cidade:</label>
                                <input type="text" name="func_cidade" required="" />
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>UF:</label>
                                <select name="func_uf" id="func_uf" required="">
                                    <option value="AC">AC</option><option value="AL">AL</option><option value="AM">AM</option><option value="AP">AP</option><option value="BA">BA</option><option value="CE">CE</option>
                                    <option value="DF">DF</option><option value="ES">ES</option><option value="GO">GO</option><option value="MA">MA</option><option value="MG">MG</option><option value="MS">MS</option>
                                    <option value="MT">MT</option><option value="PA">PA</option><option value="PB">PB</option><option value="PE">PE</option><option value="PI">PI</option><option value="PR">PR</option>
                                    <option value="RJ">RJ</option><option value="RN">RN</option><option value="RO">RO</option><option value="RR">RR</option><option value="RS">RS</option><option value="SC">SC</option>
                                    <option value="SE">SE</option><option value="SP">SP</option><option value="TO">TO</option>
                                </select>
                            </li>
                        </div>

                        <div class="coluna1">
                            <li class="li_corpo"><label>Cargo:</label>
                                <input type="text" name="func_cargo" required="" />
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>PIS:</label>
                                <input type="text" name="func_pis" required="" name="numbers" title="Preencha com números!" />
                            </li>
                        </div>

                        <div class="coluna1">
                            <li class="li_corpo"><label>Propriedade:</label>
                                <select name="func_propriedade" autofocus required="">
                                    <option value="null">Selecione...</option>
                                    <%  ResultSet rs = MetodosUteisDAOMySQL.getPropriedade();
                                        while (rs.next()) { %>
                                            <option value="<%= rs.getString("prop_id")%>"> <%= rs.getString("prop_codigo")%> - <%= rs.getString("prop_nome")%></option>
                                    <%  }   %>
                                </select>
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>E-mail:</label>
                                <input type="email" name="func_email" required="" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" placeholder="john_doe@example.com"/>
                            </li>
                        </div>

                        <div class="coluna1">
                            <li class="li_corpo"><label>Nome de usuário:</label>
                                <input type="text" name="func_login"  />
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Senha de Acesso:</label>
                                <input type="password" name="func_senha"  />
                            </li>
                        </div>
                        <li class="li_corpo">&nbsp;</li>
                        <li class="li_ultimo" style="border-top:1px solid #eee; padding:7px;">
                            <button class="submit_incluir" type="submit" name="enviarFormCadFunc" >Salvar</button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarFormCadFunc" value="Cancelar" onclick="javascript:history.back(1)">Cancelar</button>
                            <br>&nbsp;
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
                <form class="form_padrao" name="formEditar" action="/siac/control?cls=Funcionario&mtd=editarSalvar&id=${funcionarios.id}" method="POST">
                    <ul>
                        <li class="li_titulo" style="border-bottom:1px solid #777; margin-top: 0px; padding:7px; position:relative;"><h1>Funcionário - Editar</h1></li> 
                        <div class="coluna1">
                            <li class="li_corpo"><label>ID:</label>
                                <input type="text" name="func_func" required="" name="numbers" value="${funcionarios.id}" readonly="" />
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Matricula:</label>
                                <input type="text" name="func_matricula" required="" name="numbers" value="${funcionarios.matricula}" readonly="" />
                            </li>
                        </div>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Nome:</label><input type="text" name="func_nome" required="" value="${funcionarios.nome}"/></li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>CPF:</label>
                                <input type="text" name="func_cpf" required pattern="^(\d{3}\.\d{3}\.\d{3}-\d{2})|(\d{11})$" placeholder="888.888.888-88" value="${funcionarios.cpf}"/><br />
                            </li>
                        </div>

                        <div class="coluna1">
                            <li class="li_corpo"><label>RG:</label>
                                <input type="text" name="func_rg" required="" name="numbers" pattern="[0-9]+$" max="35" min="1" title="Preencha com números!" value="${funcionarios.rg}"/>
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Órgão emissor:</label>
                                <input type="text" name="func_orgao_emissor" required="" value="${funcionarios.orgao_emissor}"/>
                            </li>
                        </div>

                        <div class="coluna1">
                            <li class="li_corpo"><label>Nome do Pai:</label>
                                <input type="text" name="func_pai" required="" value="${funcionarios.pai}"/>
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Nome da Mãe:</label>
                                <input type="text" name="func_mae" required="" value="${funcionarios.mae}"/>
                            </li>
                        </div>

                        <div class="coluna1">
                            <li class="li_corpo"><label>Data de Nascimento:</label>
                                <input type="date" name="func_dt_nascimento" required="" value="${funcionarios.dt_nascimento}"/>
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Data de Admissão:</label>
                                <input type="date" name="func_dt_admissao" required="" value="${funcionarios.dt_admissao}"/>
                            </li>
                        </div>

                        <div class="coluna1">
                            <li class="li_corpo"><label>Telefone:</label><br />
                                <input type="tel" name="func_fone" required="" maxlength="15"  pattern="\([0-9]{2}\) [0-9]{4,6}-[0-9]{3,4}$" value="${funcionarios.fone}"/> 
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Endereço:</label>
                                <input type="text" name="func_endereco" required="" value="${funcionarios.endereco}"/>
                            </li>
                        </div>

                        <div class="coluna1">
                            <li class="li_corpo"><label>Cidade:</label>
                                <input type="text" name="func_cidade" required="" value="${funcionarios.cidade}"/>
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>UF:</label>
                                <select name="func_uf" id="func_uf" required="">
                                    <option value="${funcionarios.uf}">${funcionarios.uf}</option>
                                    <option value="">Selecione...</option><option value="AC">AC</option><option value="AL">AL</option><option value="AM">AM</option><option value="AP">AP</option><option value="BA">BA</option><option value="CE">CE</option>
                                    <option value="DF">DF</option><option value="ES">ES</option><option value="GO">GO</option><option value="MA">MA</option><option value="MG">MG</option><option value="MS">MS</option>
                                    <option value="MT">MT</option><option value="PA">PA</option><option value="PB">PB</option><option value="PE">PE</option><option value="PI">PI</option><option value="PR">PR</option>
                                    <option value="RJ">RJ</option><option value="RN">RN</option><option value="RO">RO</option><option value="RR">RR</option><option value="RS">RS</option><option value="SC">SC</option>
                                    <option value="SE">SE</option><option value="SP">SP</option><option value="TO">TO</option>
                                </select>
                            </li>
                        </div>

                        <div class="coluna1">
                            <li class="li_corpo"><label>Cargo:</label>
                                <input type="text" name="func_cargo" required="" value="${funcionarios.cargo}"/>
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>PIS:</label>
                                <input type="text" name="func_pis" required="" title="Preencha com números!" value="${funcionarios.pis}"/><!-- name="numbers"-->
                            </li>
                        </div>

                        <div class="coluna1">
                            <li class="li_corpo"><label>Propriedade:</label>
                                <select name="cmp_anim_propriedade" autofocus required="" value="">
                                        <c:if test="${funcionarios.propriedade != null}"><option value="${funcionarios.propriedade.id}">${funcionarios.propriedade.codigo} - ${funcionarios.propriedade.nome}</option></c:if>
                                        <c:if test="${funcionarios.propriedade == null}"><option value="">Selecione...</option></c:if>
                                        <%  ResultSet rs = MetodosUteisDAOMySQL.getPropriedade();
                                            while (rs.next()) { %>
                                                <option value="<%= rs.getString("prop_id")%>"> <%= rs.getString("prop_codigo")%> - <%= rs.getString("prop_nome")%> </option>
                                        <%  }   %>
                                </select>
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>E-mail:</label>
                                <input type="email" name="func_email" required="" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" placeholder="vitor@examplo.com" value="${funcionarios.email}"/>
                            </li>
                        </div>

                        <div class="coluna1">
                            <li class="li_corpo"><label>Nome de usuário:</label>
                                <input type="text" name="func_login"  value="${funcionarios.login}"/>
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Senha de Acesso:</label>
                                <input type="password" name="func_senha"  value="${funcionarios.senha}"/>
                            </li>
                        </div>
                       
                        <li class="li_corpo">&nbsp;</li>
                        <li class="li_ultimo" style="border-top:1px solid #eee; padding:7px;">
                            <button class="submit_incluir" type="submit" name="enviarFormEditar" value="Salvar">Salvar</button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarFormEditar" value="Cancelar" onclick="javascript:history.back(1)">Cancelar</button>
                            <br>&nbsp;
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
        case "listar": {
%>

<%
            break;
        }
        case "detalhar": {
            Funcionario func = (Funcionario) request.getAttribute("detalhesfunc");
%>
            <div class="divCabecalho">        
                <ul>
                    <li class="li_titulo"><h1>Dados do Funcionário</h1></li>
                    <li></li>
                </ul>        
            </div>
            <div class="tabDetalhes" style="width:650px;">
                <table>
                    <tr><th>NOME</th>
                        <td><% if(func.getNome() != null) 
                                out.print(func.getNome()); %>
                        </td>
                    </tr>
                    <tr><th>MATRÍCULA</th>
                        <td><% if(func.getMatricula()!= null) 
                                out.print(func.getMatricula()); %>
                        </td>
                    </tr>
                    <tr><th>CPF</th>
                        <td><% if(func.getCpf()!= null) 
                                out.print(func.getCpf()); %>
                        </td>
                    </tr>
                    <tr><th>RG</th>
                        <td><% if(func.getRg() != null) 
                                out.print(func.getRg()); %>
                        </td>
                    </tr>
                    <tr><th>ÓRGÃO EXPEDIDOR</th>
                        <td><% if(func.getOrgao_emissor()!= null) 
                                out.print(func.getOrgao_emissor()); %>
                        </td>
                    </tr>
                    <tr><th>PIS</th>
                        <td><% if(func.getPis()!= null) 
                                out.print(func.getPis()); %>
                        </td>
                    </tr>
                    <tr><th>NOME DO PAI</th>
                        <td><% if(func.getPai()!= null) 
                                out.print(func.getPai()); %>
                        </td>
                    </tr>
                    <tr><th>NOME DA MÃE</th>
                        <td><% if(func.getMae()!= null) 
                                out.print(func.getMae()); %>
                        </td>
                    </tr>
                    <tr><th>ENDEREÇO</th>
                        <td><% if(func.getEndereco()!= null) 
                                out.print(func.getEndereco()); %>
                        </td>
                    </tr>
                    <tr><th>CIDADE</th>
                        <td><% if(func.getCidade()!= null) 
                                out.print(func.getCidade()); %>
                        </td>
                    </tr>
                    <tr><th>UF</th>
                        <td><% if(func.getUf()!= null) 
                                out.print(func.getUf()); %>
                        </td>
                    </tr>
                    <tr><th>DATA DE NASCIMENTO</th>
                        <td><% if(func.getDt_nascimento()!= null) 
                                out.print(func.getDt_nascimento()); %>
                        </td>
                    </tr>
                    <tr><th>DATA DE ADMISSÃO</th>
                        <td><% if(func.getDt_admissao()!= null) 
                                out.print(func.getDt_admissao()); %>
                        </td>
                    </tr>
                    <tr><th>CARGO</th>
                        <td><% if(func.getCargo()!= null) 
                                out.print(func.getCargo()); %>
                        </td>
                    </tr>
                    <tr><th>TELEFONE</th>
                        <td><% if(func.getFone()!= null) 
                                out.print(func.getFone()); %>
                        </td>
                    </tr>
                    <tr><th>NOME DE USUÁRIO</th>
                        <td><% if(func.getLogin()!= null) 
                                out.print(func.getLogin()); %>
                        </td>
                    </tr>   
                </table>
            </div>
            <div id="divDetalhar">        
                <form class="form_padrao" name="formDetalhar" action="" method="POST">
                    <ul>
                        <li class="li_corpo"></li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" type="submit"><a  style="text-decoration: none;" href="/siac/control?cls=Funcionario&mtd=editar&id=<%= func.getId()%>"/>Editar</a></button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="submit" onclick="return confirm('Confirma a exclusão do Funcionário ${funcionarios.nome}?')"><a  style="text-decoration: none;" href="/siac/control?cls=Funcionario&mtd=excluir&id=<%= func.getId()%>"/>Excluir</a></button>
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
                <form class="form_padrao" name="formBuscar" action="/siac/control?cls=Funcionario&mtd=buscar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Funcionario</h1></li>
                        <li class="li_corpo">
                            <input placeholder="Informe a matricula ou o CPF" type="text" name="cmp_bsc_funcionario"  size="50" />
                            <button class="submit_buscar" type="submit" >Buscar Funcionário</button>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" ><a href="principal?d=forms&a=cadastrar&f=funcionario"> Incluir Funcionario </a></button>
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
                <form class="form_padrao" name="formBuscar" action="/siac/control?cls=Funcionario&mtd=buscar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Funcionario</h1></li>
                        <li class="li_corpo">
                            <input placeholder="Informe a matricula ou o CPF" type="text" name="cmp_bsc_funcionario"  size="50" />
                            <button class="submit_buscar" type="submit" >Buscar Funcionário</button>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" ><a href="principal?d=forms&a=cadastrar&f=funcionario"> Incluir Funcionario </a></button>
                        </li>
                    </ul>
                </form>
            </div>
            <br>
            <div class="tabResultBusca">
                <table>
                    <tr><td colspan="9"><center>Funcionarios</center></td></tr>    
                    <tr>
                        <th>Nome</th>
                        <th>Matricula</th>
                        <th>CPF</th>
                        <th>Telefone</th>
                        <th>Email</th>
                        <th>Cargo</th>
                        <th colspan="3">Ações</th>
                    </tr>
                    <%  for (Funcionario func : listaFuncionario) {%>
                            <tr>
                                <td><%=func.getNome()%></td>
                                <td><%=func.getMatricula()%></td>
                                <td><%=func.getCpf()%></td>
                                <td><%=func.getFone()%></td>
                                <td><%=func.getEmail()%></td>
                                <td><%=func.getCargo()%></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=Funcionario&mtd=detalhar&id=<%= func.getId()%>" title="Detalhar"><center><img src="imagens/icones/btn_detalhar.png" alt="Detalhes"/></center></a></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=Funcionario&mtd=editar&id=<%= func.getId()%>" title="Editar"><center><img src="imagens/icones/btn_editar.png" alt="Editar"/></center></a></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=Funcionario&mtd=excluir&id=<%= func.getId()%>" title="Excluir" onclick="return confirm('Confirma a exclusão do Funcionario <%= func.getNome()%>?')"><center><img src="imagens/icones/btn_excluir.png" alt="Excluir"/></center></a></td>
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
