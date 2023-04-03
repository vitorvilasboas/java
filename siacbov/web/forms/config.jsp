<%@page import="java.util.List" %>
<%@page import="br.edu.cio.model.Sensor" %>
<%@page import="javax.swing.JOptionPane" %>
<%@page import="java.text.DateFormat" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page session="true" %>
<%@page import="br.edu.cio.model.Configuracao" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="configVigente" scope="session" class="br.edu.cio.model.Configuracao"/>

<% 
    SimpleDateFormat formatoBr = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatoBD = new SimpleDateFormat("yyyy-MM-dd");
    Configuracao configuration = (Configuracao) session.getAttribute("configVigente");
    String dth_atual_load = "";
    String dth_atual = "";
    String dth_ult_captura = "";
    String dth_ult_captura_load = "";
    if(configuration.getData_atual() != null && configuration.getHora_atual() != null){
        dth_atual = (formatoBD.format(formatoBr.parse(configuration.getData_atual())) + "T" + configuration.getHora_atual());
        dth_atual_load = (formatoBD.format(formatoBr.parse(configuration.getData_atual())) + " " + configuration.getHora_atual());
    }
    
    if(configuration.getDt_ult_captura()!= null && configuration.getHr_ult_captura()!= null){
        dth_ult_captura = (formatoBD.format(formatoBr.parse(configuration.getDt_ult_captura())) + "T" + configuration.getHr_ult_captura());
        dth_ult_captura_load = (formatoBD.format(formatoBr.parse(configuration.getDt_ult_captura())) + " " + configuration.getHr_ult_captura());
    }
%>

<%  
    switch(request.getParameter("a")){
        case "load":
        {
%>
            <div id="divEditar"> 
                    <form class="form_modelo1" name="formCadastrar" action="principal?d=forms&a=editar&f=config" method="POST">
                        <ul>                             
                            <li class="li_titulo" style="border-bottom:1px solid #777; margin-top: 0px; padding:7px; position:relative;"><h1>Configurações</h1></li>
                            <div class="coluna1">
                                <li class="li_corpo"><label>Data Atual do Sistema</label>
                                    <input name="cmp_edt_config_0" style="width: 16em" type="datetime-local" autofocus value="<%= dth_atual %>" disabled=""/>
                                </li> 
                            </div>
                            <div class="coluna2">
                                <li class="li_corpo"><label>Data da Última Captura</label>
                                    <input name="cmp_edt_config_100" style="width: 16em" type="datetime-local" autofocus value="<%= dth_ult_captura %>" disabled=""/>
                                </li> 
                            </div>
                            <div class="coluna1">
                                <li class="li_corpo"><label>Carregamento de Leituras</label>
                                    <c:if test="${configVigente.reg_sensores == 'manual'}"><input type="text" name="cmp_edt_config_13" value="MANUAL" disabled=""/></c:if>
                                    <c:if test="${configVigente.reg_sensores == 'auto'}"><input type="text" name="cmp_edt_config_13" value="AUTOMÁTICO" disabled=""/></c:if> 
                                </li>
                            </div>
                            <div class="coluna2">
                                <li class="li_corpo"><label>Intervalo de Carregamento</label>
                                    <input type="text" name="cmp_edt_config_5" value="${configVigente.intervalo_entre_leituras} horas" disabled=""/>
                                </li>
                            </div>
                            <div class="coluna1">
                                <li class="li_corpo"><label>Ocorrências necessárias p/ Cio</label>
                                    <input type="text" name="cmp_edt_config_2" value="${configVigente.qtd_oco_pra_cio}" disabled=""/>
                                </li>
                            </div>             
                            <div class="coluna2">
                                <li class="li_corpo"><label>Quando Alertar</label>
                                    <input type="text" name="cmp_edt_config_6" value="${configVigente.alerta_quando}" disabled=""/> 
                                </li>
                            </div>
                            <div class="coluna1">
                                <li class="li_corpo"><label>Intervalo entre alertas</label>
                                    <input type="text" name="cmp_edt_config_10" value="${configVigente.tempo_entre_alertas}" disabled=""/> 
                                </li>
                            </div>
                            <div class="coluna2">
                                <li class="li_corpo"><label>Quantidade de Alertas a Emitir</label>
                                    <input type="text" name="cmp_edt_config_9" value="${configVigente.qtd_alertas}" disabled=""/> 
                                </li>
                            </div>         
                            <div class="coluna1">
                                <li class="li_corpo"><label style="width: 115px;">Alertas por Email: </label>
                                    <c:if  test="${configVigente.envio_email == 's'}">
                                        <input style="width:20px; display: block;" type="checkbox" name="cmp_edt_config_8" value="s" checked="" disabled="">
                                    </c:if>
                                    <c:if  test="${configVigente.envio_email == 'n'}">
                                        <input style="width:20px;" type="checkbox" name="cmp_edt_config_8" value="n" checked="" disabled="">
                                    </c:if>
                                </li>
                            </div>
                            <div class="coluna2">
                                <li class="li_corpo"><label style="width: 110px;">Alertas por SMS: </label>
                                    <c:if  test="${configVigente.envio_celular == 's'}">
                                        <input style="width:20px;" type="checkbox" name="cmp_edt_config_7" value="s" checked="" disabled="">
                                    </c:if>
                                    <c:if  test="${configVigente.envio_celular == 'n'}">
                                        <input style="width:20px;" type="checkbox" name="cmp_edt_config_7" value="n" checked="" disabled="">
                                    </c:if>
                                </li>
                            </div>
                            <div class="coluna1">
                                <li class="li_corpo"><label>Intervalo Padrão de Anestro</label>
                                    <input type="text" name="cmp_edt_config_1" value="${configVigente.intervalo_anestro}" disabled=""/> 
                                </li>
                            </div>
                            <div class="coluna2">
                                <li class="li_corpo"><label>Duração Padrão de Cio</label>
                                    <input type="text" name="cmp_edt_config_3" value="${configVigente.duracao_cio}" disabled=""/> 
                                </li>
                            </div>
                            <!--<li class="li_corpo"><label>Classificação da Atividade Animal</label></li>-->
                            <div class="coluna1">
                                <li class="li_corpo"><label>Percentual Minimo p/ Atividade Media (Padrão 75%)</label>
                                    <input type="text" name="cmp_edt_config_4" value="${configVigente.perc_min_atv_media}" disabled="" />
                                </li>
                            </div>
                            <div class="coluna2">
                                <li class="li_corpo"><label>Percentual Minimo p/ Atividade Alta (Padrão 125%)</label>
                                    <input type="text" name="cmp_edt_config_14" value="${configVigente.perc_min_atv_alta}" disabled=""/>
                                </li>
                            </div> 
                            <!--<li class="li_corpo"><label>Classificação do Tempo em Anestro</label></li>-->
                            <div class="coluna1">
                                <li class="li_corpo"><label>Período Mínimo de Anestro NORMAL</label>
                                    <input type="text" name="cmp_edt_config_cta1" value="${configVigente.min_anestro_normal}" disabled=""/>
                                </li>
                            </div>
                            <div class="coluna2">
                                <li class="li_corpo"><label>Período Mínimo de Anestro LONGO</label>
                                    <input type="text" name="cmp_edt_config_cta2" value="${configVigente.min_anestro_longo}" disabled=""/>
                                </li>
                            </div>
                            <div class="coluna1" style="width: 100%;">
                                <li class="li_corpo" ><label style="width: 100%;">Período Mínimo de Anestro <br>MUITO LONGO</label>
                                    <input type="text" name="cmp_edt_config_cta3" value="${configVigente.min_anestro_mlongo}" disabled=""/>
                                </li>
                            </div>
                            <div class="coluna1">
                                <li class="li_corpo"><label>Alerta p/ Cio Suspeito</label>
                                    <textarea name="cmp_edt_config_11" rows="5" cols="30" disabled="">${configVigente.msg_alerta_ocorrencia}</textarea>
                                </li>
                            </div>
                            <div class="coluna2">
                                <li class="li_corpo"><label>Alerta p/ Cio Confirmado</label>
                                    <textarea name="cmp_edt_config_12" rows="5" cols="30" disabled="">${configVigente.msg_alerta_cio}</textarea>
                                </li>
                            </div>
                            <li class="li_corpo">&nbsp;</li>
                            <li class="li_ultimo" style="border-top:1px solid #eee; padding:7px;">
                                <button class="submit_buscar" type="submit" name="btn_editar">Editar Parâmetros</button>
                                <br>&nbsp;
                            </li>                         
                        </ul>
                    </form>
                </div>
<%
            break;
        }
        case "editar":
        {
%>           
            <c:if test="${not empty configVigente.id}">
                <div id="divEditar"> 
                    <form class="form_modelo1" name="formEditarConfiguracoes" action="/siac/control?cls=Config&mtd=editar" method="POST">
                        <ul>                             
                            <li class="li_titulo" style="border-bottom:1px solid #777; margin-top: 0px; padding:7px; position:relative;"><h1>Configurações - Editar</h1></li>
                            <div class="coluna1">
                                <li class="li_corpo"><label>Data Atual do Sistema</label>
                                    <input name="cmp_edt_config_0" style="width: 16em" type="datetime-local" autofocus value="<%= dth_atual %>"/>
                                </li> 
                            </div>
                            <div class="coluna2">
                                <li class="li_corpo"><label>Data da Última Captura</label>
                                    <input name="cmp_edt_config_100" style="width: 16em" type="datetime-local" autofocus value="<%= dth_ult_captura %>"/>
                                </li> 
                            </div>
                            <div class="coluna1">
                                <li class="li_corpo"><label>Carregamento de Leituras</label>
                                    <select name="cmp_edt_config_13">
                                        <option value="${configVigente.reg_sensores}">${configVigente.reg_sensores}</option>
                                        <c:if test="${configVigente.reg_sensores ne 'manual'}"><option value="manual" >Manual</option></c:if><!-- ne=not equals -->
                                        <c:if test="${configVigente.reg_sensores ne 'auto'}"><option value="auto" >Automático</option></c:if><!-- ne=not equals -->
                                    </select> 
                                </li>
                            </div>
                            <div class="coluna2">
                                <li class="li_corpo"><label>Intervalo de Carregamento</label>
                                    <select name="cmp_edt_config_5">
                                        <option value="${configVigente.intervalo_entre_leituras}">${configVigente.intervalo_entre_leituras} horas</option>
                                        <c:forEach var="i" begin="2" end="24" step="2">
                                            <c:if test="${configVigente.intervalo_entre_leituras != i}">
                                                <option value="<c:out value="${i}"/>" ><c:out value="${i}"/> horas</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </li>
                            </div>
                            <div class="coluna1">
                                <li class="li_corpo"><label>Ocorrências necessárias p/ Cio</label>
                                    <select name="cmp_edt_config_2">
                                            <option value="${configVigente.qtd_oco_pra_cio}">${configVigente.qtd_oco_pra_cio}</option>
                                            <c:forEach var="i" begin="1" end="10" step="1">
                                                <c:if test="${configVigente.qtd_oco_pra_cio != i}">
                                                    <option value="<c:out value="${i}"/>" ><c:out value="${i}"/></option>
                                                </c:if>
                                            </c:forEach>
                                    </select>
                                </li>
                            </div>             
                            <div class="coluna2">
                                <li class="li_corpo"><label>Quando Alertar</label>
                                    <select name="cmp_edt_config_6">
                                        <option value="${configVigente.alerta_quando}">${configVigente.alerta_quando}</option>
                                        <c:if test="${configVigente.alerta_quando ne 'Suspeitar Cio'}"><option value="Suspeitar Cio" >Suspeitar Cio</option></c:if><!-- ne=not equals -->
                                        <c:if test="${configVigente.alerta_quando ne 'Constatar Cio'}"><option value="Constatar Cio" >Constatar Cio</option></c:if>
                                        <c:if test="${configVigente.alerta_quando ne 'Suspeitar e Constatar Cio'}"><option value="Suspeitar e Constatar Cio" >Suspeitar e Constatar Cio</option></c:if>
                                    </select> 
                                </li>
                            </div>
                            <div class="coluna1">
                                <li class="li_corpo"><label>Intervalo entre alertas</label>
                                    <select name="cmp_edt_config_10">
                                        <option value="${configVigente.tempo_entre_alertas}">${configVigente.tempo_entre_alertas}</option>
                                        <c:if test="${configVigente.tempo_entre_alertas ne '00:05:00'}"><option value="00:05:00" >5 minutos</option></c:if>
                                        <c:if test="${configVigente.tempo_entre_alertas ne '00:15:00'}"><option value="00:15:00" >15 minutos</option></c:if>
                                        <c:if test="${configVigente.tempo_entre_alertas ne '00:30:00'}"><option value="00:30:00" >30 minutos</option></c:if>
                                        <c:if test="${configVigente.tempo_entre_alertas ne '01:00:00'}"><option value="01:00:00" >1 hora</option></c:if>
                                        <c:if test="${configVigente.tempo_entre_alertas ne '02:00:00'}"><option value="02:00:00" >2 horas</option></c:if>
                                        <c:if test="${configVigente.tempo_entre_alertas ne '04:00:00'}"><option value="04:00:00" >4 horas</option></c:if>
                                        <c:if test="${configVigente.tempo_entre_alertas ne '06:00:00'}"><option value="06:00:00" >6 horas</option></c:if>
                                        <c:if test="${configVigente.tempo_entre_alertas ne '08:00:00'}"><option value="08:00:00" >8 horas</option></c:if>
                                        <c:if test="${configVigente.tempo_entre_alertas ne '12:00:00'}"><option value="12:00:00" >12 horas</option></c:if>
                                    </select>
                                </li>
                            </div>
                            <div class="coluna2">
                                <li class="li_corpo"><label>Quantidade de Alertas a Emitir</label>
                                    <select name="cmp_edt_config_9">
                                        <option value="${configVigente.qtd_alertas}">${configVigente.qtd_alertas}</option>
                                        <c:forEach var="i" begin="2" end="10" step="1">
                                            <c:if test="${configVigente.qtd_alertas != i}">
                                                <option value="<c:out value="${i}"/>" ><c:out value="${i}"/></option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </li>
                            </div>         
                            <div class="coluna1">
                                <li class="li_corpo"><label style="width: 115px;">Alertas por Email: </label>
                                    <c:if  test="${configVigente.envio_email == 's'}">
                                        <input style="width:20px; display: block;" type="checkbox" name="cmp_edt_config_8" value="s" checked="">
                                    </c:if>
                                    <c:if  test="${configVigente.envio_email == 'n'}">
                                        <input style="width:20px;" type="checkbox" name="cmp_edt_config_8" value="n" checked="">
                                    </c:if>
                                </li>
                            </div>
                            <div class="coluna2">
                                <li class="li_corpo"><label style="width: 110px;">Alertas por SMS: </label>
                                    <c:if  test="${configVigente.envio_celular == 's'}">
                                        <input style="width:20px;" type="checkbox" name="cmp_edt_config_7" value="s" checked="">
                                    </c:if>
                                    <c:if  test="${configVigente.envio_celular == 'n'}">
                                        <input style="width:20px;" type="checkbox" name="cmp_edt_config_7" value="n" checked="">
                                    </c:if>
                                </li>
                            </div>
                            <div class="coluna1">
                                <li class="li_corpo"><label>Intervalo Padrão de Anestro</label>
                                    <select name="cmp_edt_config_1">
                                        <option value="${configVigente.intervalo_anestro}">${configVigente.intervalo_anestro} dias</option>
                                        <c:forEach var="i" begin="17" end="24" step="1">
                                            <c:if test="${configVigente.intervalo_anestro != i}">
                                                <option value="<c:out value="${i}"/>" ><c:out value="${i}"/> dias </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </li>
                            </div>
                            <div class="coluna2">
                                <li class="li_corpo"><label>Duração Padrão de Cio</label>
                                    <select name="cmp_edt_config_3">
                                        <option value="${configVigente.duracao_cio}">${configVigente.duracao_cio} horas</option>
                                        <c:forEach var="i" begin="10" end="36" step="2">
                                            <c:if test="${configVigente.duracao_cio != i}">
                                                <option value="<c:out value="${i}"/>" ><c:out value="${i}"/> horas</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </li>
                            </div>
                            <!--<li class="li_corpo"><label>Classificação da Atividade Animal</label></li>-->
                            <div class="coluna1">
                                <li class="li_corpo"><label>Percentual Minimo p/ Atividade Media (Padrão 75%)</label>
                                    <input type="text" name="cmp_edt_config_4" value="${configVigente.perc_min_atv_media}" />
                                </li>
                            </div>
                            <div class="coluna2">
                                <li class="li_corpo"><label>Percentual Minimo p/ Atividade Alta (Padrão 125%)</label>
                                    <input type="text" name="cmp_edt_config_14" value="${configVigente.perc_min_atv_alta}"/>
                                </li>
                            </div> 
                            <!--<li class="li_corpo"><label>Classificação do Tempo em Anestro</label></li>-->
                            <div class="coluna1">
                                <li class="li_corpo"><label>Período Mínimo de Anestro NORMAL</label>
                                    <select name="cmp_edt_config_cta1">
                                        <option value="${configVigente.min_anestro_normal}">${configVigente.min_anestro_normal} dias</option>
                                        <c:forEach var="i" begin="12" end="17" step="1">
                                            <c:if test="${configVigente.min_anestro_normal != i}">
                                                <option value="<c:out value="${i}"/>" ><c:out value="${i}"/> dias</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </li>
                            </div>
                            <div class="coluna2">
                                <li class="li_corpo"><label>Período Mínimo de Anestro LONGO</label>
                                    <select name="cmp_edt_config_cta2">
                                        <option value="${configVigente.min_anestro_longo}">${configVigente.min_anestro_longo} dias</option>
                                        <c:forEach var="i" begin="23" end="26" step="1">
                                            <c:if test="${configVigente.min_anestro_longo != i}">
                                                <option value="<c:out value="${i}"/>" ><c:out value="${i}"/> dias</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </li>
                            </div>
                            <div class="coluna1" style="width: 100%;">
                                <li class="li_corpo" ><label style="width: 100%;">Período Mínimo de Anestro <br>MUITO LONGO</label>
                                    <select name="cmp_edt_config_cta3">
                                        <option value="${configVigente.min_anestro_mlongo}">${configVigente.min_anestro_mlongo} dias</option>
                                        <c:forEach var="i" begin="29" end="35" step="1">
                                            <c:if test="${configVigente.min_anestro_mlongo != i}">
                                                <option value="<c:out value="${i}"/>" ><c:out value="${i}"/> dias</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </li>
                            </div>
                            <div class="coluna1">
                                <li class="li_corpo"><label>Alerta p/ Cio Suspeito</label>
                                    <textarea name="cmp_edt_config_11" rows="5" cols="30">${configVigente.msg_alerta_ocorrencia}</textarea>
                                </li>
                            </div>
                            <div class="coluna2">
                                <li class="li_corpo"><label>Alerta p/ Cio Confirmado</label>
                                    <textarea name="cmp_edt_config_12" rows="5" cols="30">${configVigente.msg_alerta_cio}</textarea>
                                </li>
                            </div>
                                <li class="li_corpo">&nbsp;</li>
                                <li class="li_ultimo" style="border-top:1px solid #eee; padding:7px;">
                                <button class="submit_incluir" type="submit" name="btn_edt_config" >Atualizar Configuração</button>
                                <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarFormCadLote" value="Cancelar" onclick="javascript:history.back(1)">Cancelar</button>
                                <br>&nbsp;
                            </li>                         
                        </ul>
                    </form>
                </div>   
            </c:if> 
<%
            break;
        }     
        default:
            break;
    } 
%>

<!--
<div class="onoffswitch">
    <input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox" id="myonoffswitch" checked>
    <label class="onoffswitch-label" for="myonoffswitch">
        <span class="onoffswitch-inner"></span>
        <span class="onoffswitch-switch"></span>
    </label>
</div>
-->