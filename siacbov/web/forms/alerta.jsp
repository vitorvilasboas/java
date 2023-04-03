
<%@page import="br.edu.cio.model.AlertaEmitido"%>
<%@page import="br.edu.cio.model.dao.mysql.MetodosUteisDAOMySQL"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="br.edu.cio.model.Cio"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--<//jsp:useBean id="cicloEstral" scope="request" class="br.edu.cio.model.Cio" />-->

<%      List<AlertaEmitido> listaAlertas = (ArrayList<AlertaEmitido>) request.getAttribute("alertasBusca"); %>

<%  switch (request.getParameter("a")) {
        case "detalhar": {
            AlertaEmitido alert = (AlertaEmitido) request.getAttribute("alertaDetalhes");
%>
            <div class="divCabecalho">        
                <ul>
                    <li class="li_titulo"><h1>Alerta Registrado/Emitido</h1></li>
                    <li></li>
                </ul>        
            </div>
            <div class="tabDetalhes" style="width:750px;">
                <table>
                    <tr><th>CÓDIGO DO ALERTA</th>
                        <td><% if(alert.getAlerta() != null)
                                out.write(alert.getAlerta().getNumero()); %>
                        </td>
                    </tr>
                    <tr><th>CÓDIGO DE EMISSÃO</th>
                        <td><% if(alert.getId() != 0)
                                out.write(alert.getId()); %>
                        </td>
                    </tr>
                    <tr><th>DATA/HORA EMISSÃO</th>
                        <td><% if(alert.getData() != null)
                                out.write(alert.getData() + " " + alert.getHora()); %>
                        </td>
                    </tr>
                    <tr><th>CIO RELACIONADO</th>
                        <td><% if(alert.getAlerta().getCio() != null)
                                out.write(alert.getAlerta().getCio().getCodigo()); %>
                        </td>
                    </tr>
                    <tr><th>STATUS CIO RELACIONADO</th>
                        <td><% if(alert.getAlerta().getCio() != null)
                                out.write(alert.getAlerta().getCio().getStatus()); %>
                        </td>
                    </tr>
                    <tr><th>ANIMAL RELACIONADO</th>
                        <td><% if(alert.getAlerta().getAnimal() != null)
                                out.write(alert.getAlerta().getAnimal().getCodigo()+ " " + alert.getAlerta().getAnimal().getApelido()); %>
                        </td>
                    </tr>
                    <tr><th>DESTINATÁRIO</th>
                        <td><% if(alert.getDestinatario().getFuncionario() != null)
                                out.write(alert.getDestinatario().getFuncionario().getNome()); %>
                        </td>
                    </tr>
                    <tr><th>MENSAGEM</th>
                        <td><% if(alert.getAlerta() != null)
                                out.write(alert.getAlerta().getMsg()); %>
                        </td>
                    </tr>
                </table>
            </div>
            <div id="divDetalhar">        
                <form class="form_padrao" name="formDetalhar" action="" method="POST">
                    <ul>
                        <li><button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarForm" value="Cancelar" onclick="javascript:history.back(1)">Voltar</button></li>
                    </ul>
                </form>
            </div>
            <br>
<%        
            break;
        }
        case "buscar": {
%>
            <div id="formBuscar">        
                <form class="form_padrao"  name="formBuscar" action="/siac/control?cls=Alerta&mtd=buscar" method="POST">
                    <ul>
                        <div id="consultaCio1">
                            <li class="li_titulo"><h1>Consultar Alertas Registrados/Emitidos</h1></li>
                            <li class="li_corpo"><label>Método de busca:</label> 
                                <select name="cmp_mtd_busca_alerta" style="width: 23.5em" autofocus onchange="mostra_campos_especificos_da_busca()" required="">
                                    <option value="">Selecione...</option>
                                    <option value="BUSCARPORANIMAL">Buscar por Animal Relacionado</option>
                                    <option value="BUSCARPORCODIGOCIO">Buscar por Código Cio Relacionado</option>
                                    <option value="BUSCARPORDATAREGISTRO">Buscar por Data/Hora de Registro</option>
                                </select>
                            </li>
                        </div>
                        <div id="consultaCio2">
                            <li  class="li_corpo"  id="metodo_busca"><label>Código ou Apelido do Animal:</label>
                            <input id="cmp_alerta_busca" type="text" style="width: 20.2em" name="cmp_alerta_busca" required=""/>
                            &nbsp;&nbsp;&nbsp;&nbsp;<button class="submit_incluir" type="submit" name="btn_bsc_cio">Buscar Registro</button></li>
                        </div>
                    </ul>
                </form>
            </div>    
<%
            break;
        }
        case "resultBusca": {
%>
            <div id="formBuscar">        
                <form class="form_padrao"  name="formBuscar" action="/siac/control?cls=Alerta&mtd=buscar" method="POST" >
                    <ul>
                        <div id="consultaCio1">
                            <li class="li_titulo"><h1>Consultar Alertas Registrados/Emitidos</h1></li>
                            <li class="li_corpo"><label>Método de busca:</label> 
                                <select name="cmp_mtd_busca_alerta" style="width: 23.5em" autofocus onchange="mostra_campos_especificos_da_busca()" required="">
                                    <option value="">Selecione...</option>
                                    <option value="BUSCARPORANIMAL">Buscar por Animal Relacionado</option>
                                    <option value="BUSCARPORCODIGOCIO">Buscar por Código Cio Relacionado</option>
                                    <option value="BUSCARPORDATAREGISTRO">Buscar por Data/Hora de Registro</option>
                                </select>
                            </li>
                        </div>
                        <div id="consultaCio2">
                        </div>
                    </ul>
                </form>
            </div>
            <br>
            <div class="tabResultBusca">
                <table>
                    <tr><td colspan="15"><center>Alertas Registrados/Emitidos</center></td></tr>    
                    <tr>
                        <th>Código do Alerta</th>
                        <th>Data/Hora Emissão</th>
                        <th>Animal Relacionado</th>
                        <th>Cio Relacionado</th>
                        <th>Operações</th>
                    </tr>
                    <%  for (AlertaEmitido alemi : listaAlertas) {  %>
                            <tr>
                                <td><% if(alemi.getAlerta() != null) %>
                                        <%= alemi.getAlerta().getNumero() %></td>
                                <td><% if(alemi.getData() != null)
                                        out.write(alemi.getData() + " " + alemi.getHora()); %></td>
                                <td><% if(alemi.getAlerta().getAnimal() != null )
                                        out.write(alemi.getAlerta().getAnimal().getApelido()); %></td>
                                <td><% if(alemi.getAlerta().getCio() != null)
                                        out.write(alemi.getAlerta().getCio().getCodigo()); %></td> 
                                <td style="width: 20px;"><a href="/siac/control?cls=Alerta&mtd=detalhar&id=<%= alemi.getId()%>" title="Detalhar"><center><img src="imagens/icones/btn_detalhar.png" alt="Detalhes"/></center></a></td>
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
    function mostra_campos_especificos_da_busca() {
        var cmp_mtd_busca_alerta = document.formBuscar.cmp_mtd_busca_alerta[document.formBuscar.cmp_mtd_busca_alerta.selectedIndex].value     //variavel q recebe o valor do select campo_perfil
        switch(cmp_mtd_busca_alerta){   //analisa a variavel q contém o valor do select campo_perfil
            case 'BUSCARPORCODIGOCIO':{   //caso educador...
                    $("#metodo_busca").remove();     //remove a div id=dados_perfil caso ela já exista
                    var  novoscampos =   '<li  class="li_corpo" id="metodo_busca"><label>Código do Ciclo:</label>';      //variavel com os campos especificos
                        novoscampos +=  '<input id="cperfil" type="text" name="cmp_alerta_busca"  style="width: 20.2em" required=""/>';    //concatena a variavel novoscampos
                        novoscampos += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="submit_incluir" type="submit" name="btn_bsc_cio">Buscar Registro</button>';
                    $("#consultaCio2").append(novoscampos);    //executa a variavel novoscampos e insere os campos na div id perfil
                    break; 
            }
            case 'BUSCARPORDATAREGISTRO':{  //caso estudante...
                    $("#metodo_busca").remove();     //remove a div id=dados_perfil caso ela já exista
                    var  novoscampos =   '<li  class="li_corpo"  id="metodo_busca"><label>Data de Registro:</label>';     //variavel com os campos especificos
                        novoscampos +=  '<input name="cmp_alerta_busca" style="width: 20em" type="datetime-local" autofocus required="" />';     //concatena a variavel novoscampos
                        novoscampos += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="submit_incluir" type="submit" name="btn_bsc_cio">Buscar Registro</button></li>';
                    $("#consultaCio2").append(novoscampos);    //executa a variavel novoscampos e insere os campos na div id=perfil
                    break;
            }
            case 'BUSCARPORANIMAL':{   //caso educador...
                    $("#metodo_busca").remove();     //remove a div id=dados_perfil caso ela já exista
                    var  novoscampos =   '<li  class="li_corpo"  id="metodo_busca"><label>Código ou Apelido do Animal:</label>';      //variavel com os campos especificos
                        novoscampos +=  '<input id="cmp_alerta_busca" type="text" style="width: 20.2em" name="cmp_alerta_busca" required=""/>';    //concatena a variavel novoscampos
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