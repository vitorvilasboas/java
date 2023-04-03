<%@page import="javax.swing.JOptionPane"%>
<%@page import="java.util.List"%>
<%@page import="br.edu.cio.model.Sensor"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="br.edu.cio.model.Propriedade"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.edu.cio.model.Animal"%>
<%@page import="br.edu.cio.model.AnimalInfo"%>
<%@page import="br.edu.cio.model.dao.mysql.MetodosUteisDAOMySQL"%>
<%@page import="br.edu.cio.model.Lote"%>
<%@page import="java.sql.ResultSet"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="animalenc" scope="request" class="br.edu.cio.model.AnimalInfo"/>

<%
    Animal p = new Animal();
    ArrayList<AnimalInfo> listaAnimalInfo = (ArrayList<AnimalInfo>) request.getAttribute("listaanimaisinfo");
    SimpleDateFormat formatoBr = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatoBD = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatoHrBr = new SimpleDateFormat("HH:mm:ss");
    AnimalInfo infoAni = new AnimalInfo();
    infoAni = (AnimalInfo) request.getAttribute("animalenc");
    String ini_ultimo_cio = "";
    String fim_ultimo_cio = "";
    String prev_prox_cio = "";
    if(infoAni.getDt_ini_ult_cio() != null && infoAni.getHr_ini_ult_cio() != null){
        ini_ultimo_cio = (formatoBD.format(formatoBr.parse(infoAni.getDt_ini_ult_cio())) + "T" + infoAni.getHr_ini_ult_cio());
    } 
    if(infoAni.getDt_fim_ult_cio() != null && infoAni.getHr_fim_ult_cio() != null){
        fim_ultimo_cio = (formatoBD.format(formatoBr.parse(infoAni.getDt_fim_ult_cio())) + "T" + infoAni.getHr_fim_ult_cio());
    } 
    if(infoAni.getDt_prev_ini_prox_cio() != null && infoAni.getHr_prev_ini_prox_cio() != null){
        prev_prox_cio = (formatoBD.format(formatoBr.parse(infoAni.getDt_prev_ini_prox_cio())) + "T" + infoAni.getHr_prev_ini_prox_cio());
    }
    
    ResultSet rs3 = MetodosUteisDAOMySQL.getSensorInativos();
    List<Sensor> listaSensores = new ArrayList<>();
    while (rs3.next()) {
        Sensor s = new Sensor();
        s.setId(rs3.getInt("sen_id"));
        s.setCodigo(rs3.getString("sen_codigo"));
        s.setDescricao(rs3.getString("sen_descricao"));
        listaSensores.add(s);
    }
%>

<script lang="javascript" type="text/javascript">  
    function ativa_campo_sensor() {
        var cmp_anim_monitoramento = document.formCadastrar.cmp_anim_monitoramento[document.formCadastrar.cmp_anim_monitoramento.selectedIndex].value 
        switch(cmp_anim_monitoramento){  
            case '1':{  
                $("#mostra_sensor").remove(); 
                var campo =  '<li class="li_corpo" id="mostra_sensor"><label>Sensor:</label>';
                    campo += '<select name="cmp_anim_sensor" type="text" autofocus required=""><option value="">Selecione...</option>';
                    campo += '<%  for(Sensor sen : listaSensores) {    %>';
                    campo += '<option value="<%= sen.getId() %>"><%= sen.getCodigo() %> - <%= sen.getDescricao() %>';
                    campo += '<%  }   %>';
                    campo += '</option></select></li>';
                $("#divSensor").append(campo);
                break;
            }
            case '0':{
                $("#mostra_sensor").remove(); 
                var campo =  '<li class="li_corpo" id="mostra_sensor"><label>Sensor:</label><select name="cmp_anim_sensor" autofocus disabled=""><option value="">Selecione o Monitoramento</option></select></li>';
                $("#divSensor").append(campo);
                break;
            }
            case '':{
                $("#mostra_sensor").remove();
                var campo =  '<li class="li_corpo" id="mostra_sensor"><label>Sensor:</label><select name="cmp_anim_sensor" autofocus disabled=""><option value="">Selecione o Monitoramento</option></select></li>';
                $("#divSensor").append(campo);
                break;
            }           
        }
    }
</script>

<%  switch (request.getParameter("a")) {
        case "cadastrar": {
%>
            <div id="divCadastrar">        
                <form class="form_modelo1" name="formCadastrar" action="/siac/control?cls=Animal&mtd=cadastrar" method="POST">
                    <ul>
                        <li class="li_titulo" style="border-bottom:1px solid #777; margin-top: 0px; padding:7px; position:relative;"><h1>Animal - Cadastrar</h1></li>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Apelido:</label><input name="cmp_anim_apelido" type="text" autofocus required=""/></li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>RGN:</label><input name="cmp_anim_rgn" type="text" onkeypress='return soNumeros(event)' autofocus required="" /></li>
                        </div>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Raça:</label><input name="cmp_anim_raca" type="text" autofocus required=""/></li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Data de Nascimento:</label><input name="cmp_anim_dtNascimento" style="width: 16em" type="date" autofocus required=""></li>
                        </div>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Sexo:</label>
                                <select name="cmp_anim_sexo" autofocus required="">
                                    <option value="">Selecione...</option><option>M</option><option>F</option>
                                </select>
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Gº Sangue:</label> 
                                <select name="cmp_anim_Gsangue" type="text" autofocus >
                                    <option value="">Selecione...</option><option>PURO ORIGEM (PO)</option><option>PURO ORIGEM INTERNACIONAL (POI)</option>
                                </select>
                            </li>
                        </div>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Tipo:</label>
                                <select name="cmp_anim_tipo" autofocus required="">
                                    <option value="">Selecione...</option><option >BEZERRO</option><option >BOI PARA ENGORDA</option><option >NOVILHA</option><option >TOURO</option><option>MATRIZ LEITEIRA</option>
                                </select>
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Tipo de Geração:</label>
                                <select name="cmp_anim_tipo_geracao" autofocus required="">
                                    <option value="">Selecione...</option><option value="NORMAL">NORMAL (Boi/Touro)</option><option value="SEMEN">SEMEN (Inseminação Artificial)</option>
                                </select>
                            </li>
                        </div>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Pelagem:</label><input name="cmp_anim_pelagem" type="text" autofocus /></li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Preço de compra:</label><input name="cmp_anim_preco_compra" type="text" onkeypress='return soDecimais(event)' autofocus /></li>
                        </div>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Peso nascimento:</label><input name="cmp_anim_peso_nascimento" type="text" onkeypress='return soDecimais(event)' autofocus /></li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Peso atual:</label><input name="cmp_anim_peso_atual" type="text" onkeypress='return soDecimais(event)' autofocus /></li>
                        </div>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Estado atual:</label>
                                <select name="cmp_anim_estado_atual" autofocus required="">
                                    <option value="">Selecione...</option><option value="INSEMINADA">INSEMINADA</option><option value="PRE-PARTO">PRÉ-PARTO</option><option value="VACA SECA">VACA SECA</option>
                                    <option value="NOVILHA JOVEM">NOVILHA JOVEM</option><option value="NOVILHA APTA PARA REPRODUCAO">NOVILHA APTA PARA REPRODUÇÃO</option><option value="EM CIO">EM CIO</option>
                                    <option value="EM LACTACAO">EM LACTAÇÃO</option><option value="GESTANTE">GESTANTE</option>
                                </select>
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Monitoramento:</label>
                                <select name="cmp_anim_monitoramento" autofocus required="" onchange="ativa_campo_sensor()">
                                    <option value="">Selecione...</option><option value="1">ATIVO</option><option value="0">INATIVO</option>
                                </select>
                            </li>
                        </div>
                        
                        <div class="coluna1">
                            <div id="divSensor">
                                <li class="li_corpo" id="mostra_sensor"><label >Sensor:</label>
                                    <select name="cmp_anim_sensor" autofocus disabled=""><option value="">Selecione o Monitoramento</option></select>
                                </li>          
                            </div>          
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Propriedade:</label> 
                                <select name="cmp_anim_propriedade" type="text" autofocus required="">
                                    <option value="">Selecione...</option>
                                    <%  ResultSet rs = MetodosUteisDAOMySQL.getPropriedade();
                                        while (rs.next()) { %>
                                            <option value="<%= rs.getString("prop_id")%>"> <%= rs.getString("prop_codigo")%> - <%= rs.getString("prop_nome")%> </option>
                                    <%  }   %>
                                </select>
                            </li>
                        </div>       
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Lote:</label> 
                                <select name="cmp_anim_lote" type="text" autofocus required="">
                                    <option value="">Selecione...</option>
                                    <%  ResultSet rs1 = MetodosUteisDAOMySQL.getLote();
                                        while (rs1.next()) {    %>
                                            <option value="<%= rs1.getString("lot_id")%>"> <%= rs1.getString("lot_codigo")%> - <%= rs1.getString("lot_descricao")%> </option>
                                    <%  } %>
                                </select>
                            </li>
                        </div>     
                        <div class="coluna2">
                            <li class="li_corpo"><label>Pasto:</label> 
                            <select name="cmp_anim_pasto" type="text" autofocus required="">
                                <option value="">Selecione...</option>
                                <%  ResultSet rs2 = MetodosUteisDAOMySQL.getPasto();
                                    while (rs2.next()) {    %>
                                        <option value="<%= rs2.getString("pas_id")%>"> <%= rs2.getString("pas_codigo")%> - <%= rs2.getString("pas_nome")%> </option>
                                <%  }   %>
                            </select>
                        </li>
                        </div>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Duração Média do cio:</label><input name="cmp_anim_duracao_cio" style="width: 16em;" type="text" onkeypress='return soNumeros(event)' autofocus required="" /></li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Intervalo Médio de Anestro:</label>
                                <select name="cmp_anim_intervalo_anestro" autofocus required="">
                                    <option value="">Selecione...</option>
                                    <c:forEach var="i" begin="10" end="36" step="1">
                                        <option value=<c:out value="${i}"/> ><c:out value="${i}"/> dias</option>
                                    </c:forEach>
                                </select>
                            </li>
                        </div>
                                
                        <div class="coluna1">
                            <li class="li_corpo"><label>Perfil de Atividade do Animal:</label>
                                <select name="cmp_anim_perfil_atividade" autofocus required="">
                                    <option value="">Selecione...</option><option value="MUITO CALMO">MUITO CALMO</option><option value="CALMO">CALMO</option>
                                    <option value="NORMAL">NORMAL</option><option value="AGITADO">AGITADO</option><option value="MUITO AGITADO">MUITO AGITADO</option>
                                </select>
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Data/Hora de Fim do Último Cio:</label><input name="cmp_anim_dt_fim_ult_cio" style="width: 16em;" type="datetime-local" autofocus required=""/></li>
                        </div>
                                
                        <div class="coluna1">
                            <li class="li_corpo"><label>Observações:</label><textarea name="cmp_anim_observacao" type="text" autofocus rows="7" cols="30" ></textarea></li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label style="width: 16em;">Imagem:</label>
                                <input name="cmp_anim_imagem" type="file" accept="image/*" onchange="openDialogFunc(this)" autofocus /><br>  
                            </li>
                            <div ><img style=" padding-left: 7px; width: 5em; height: 4.8em; border: 0px;" name="imagem" src=""/></div>
                        </div>
                                
                        <li class="li_corpo">&nbsp;</li>
                        <li class="li_ultimo" style="border-top:1px solid #eee; padding:7px;">
                            <button class="submit_incluir" type="submit" name="enviarFormCadAnimal" >Salvar</button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarFormCadLote" value="Cancelar" onclick="javascript:history.back(1)">Cancelar</button>
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
                <form class="form_modelo1" name="formEditar" action="/siac/control?cls=Animal&mtd=editarSalvar&id=${animalenc.animal.id}" method="POST">
                    <ul>
                        <li class="li_titulo" style="border-bottom:1px solid #777; margin-top: 0px; padding:7px; position:relative;"><h1>Animal - Editar</h1></li>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Codigo:</label><input name="cmp_anim_codigo" type="text" autofocus required="" value="${animalenc.animal.codigo}" readonly=""/></li>            
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Data de cadastro:</label>
                                <input name="cmp_anim_dt_cadastro" style="width: 16em" type="datetime-local" autofocus readonly="" value="${animalenc.animal.dt_cadastro}T${animalenc.animal.hr_cadastro}" />
                            </li>            
                        </div>
                            
                        <div class="coluna1">
                            <li class="li_corpo"><label>Apelido:</label><input name="cmp_anim_apelido" type="text" autofocus required="" value="${animalenc.animal.apelido}"/></li>           
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>RGN:</label><input name="cmp_anim_rgn" type="text" autofocus required="" value="${animalenc.animal.rgn}" onkeypress='return soNumeros(event)'/></li>            
                        </div>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Raça:</label><input name="cmp_anim_raca" type="text" autofocus required="" value="${animalenc.animal.raca}"/></li>            
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Data de Nascimento:</label><input name="cmp_anim_dtNascimento" style="width: 16em" type="date" autofocus required="" value="${animalenc.animal.dt_nascimento}"/></li>            
                        </div>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Sexo:</label>
                                <select name="cmp_anim_sexo" type="text" autofocus>
                                    <c:if test="${animalenc.animal.sexo != null}"><option value="${animalenc.animal.sexo}">${animalenc.animal.sexo}</option></c:if>
                                    <c:if test="${animalenc.animal.sexo == null}"><option value="">Selecione...</option></c:if>
                                    <c:if test="${animalenc.animal.sexo ne 'M'}"><option value="M">M</option></c:if><!-- ne=not equals -->
                                    <c:if test="${animalenc.animal.sexo ne 'F'}"><option value="F">F</option></c:if>           
                                </select>
                            </li>
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Gº sangue:</label>
                                <select name="cmp_anim_Gsangue" type="text" autofocus>
                                    <c:if test="${animalenc.animal.grau_sangue != null}"><option value="${animalenc.animal.grau_sangue}">${animalenc.animal.grau_sangue}</option></c:if>
                                    <c:if test="${animalenc.animal.grau_sangue == null}"><option value="">Selecione...</option></c:if>
                                    <c:if test="${animalenc.animal.grau_sangue ne 'PURO ORIGEM (PO)'}"><option value="PURO ORIGEM (PO)">PURO ORIGEM (PO)</option></c:if><!-- ne=not equals -->
                                    <c:if test="${animalenc.animal.grau_sangue ne 'PURO ORIGEM INTERNACIONAL (POI)'}"><option value="PURO ORIGEM INTERNACIONAL (POI)">PURO ORIGEM INTERNACIONAL (POI)</option></c:if>
                                </select>
                            </li>            
                        </div>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Tipo:</label>
                                <select name="cmp_anim_tipo" autofocus required="">
                                    <c:if test="${animalenc.animal.tipo != null}"><option value="${animalenc.animal.tipo}">${animalenc.animal.tipo}</option></c:if>
                                    <c:if test="${animalenc.animal.tipo == null}"><option value="">Selecione...</option></c:if>
                                    <c:if test="${animalenc.animal.tipo ne 'BEZERRO'}"><option value="BEZERRO">BEZERRO</option></c:if><!-- ne=not equals -->
                                    <c:if test="${animalenc.animal.tipo ne 'BOI PARA ENGORDA'}"><option value="BOI PARA ENGORDA">BOI PARA ENGORDA</option></c:if>    
                                    <c:if test="${animalenc.animal.tipo ne 'NOVILHA'}"><option value="NOVILHA">NOVILHA</option></c:if>  
                                    <c:if test="${animalenc.animal.tipo ne 'TOURO'}"><option value="TOURO">TOURO</option></c:if>  
                                    <c:if test="${animalenc.animal.tipo ne 'MATRIZ LEITEIRA'}"><option value="MATRIZ LEITEIRA">MATRIZ LEITEIRA</option></c:if>  
                                </select>
                            </li>            
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Tipo de geração:</label>
                                <select name="cmp_anim_tipo_geracao" autofocus required="">
                                    <c:if test="${animalenc.animal.tipo_geracao != null}"><option value="${animalenc.animal.tipo_geracao}">${animalenc.animal.tipo_geracao}</option></c:if>
                                    <c:if test="${animalenc.animal.tipo_geracao == null}"><option value="">Selecione...</option></c:if>
                                    <c:if test="${animalenc.animal.tipo_geracao ne 'NORMAL'}"><option value="NORMAL">NORMAL (Boi/Touro)</option></c:if><!-- ne=not equals -->
                                    <c:if test="${animalenc.animal.tipo_geracao ne 'SEMEN'}"><option value="SEMEN">SEMEN (Inseminação Artificial)</option></c:if>
                                </select>
                            </li>            
                        </div>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Pelagem:</label><input   name="cmp_anim_pelagem" type="text" autofocus value="${animalenc.animal.pelagem}"/></li>            
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Preço de compra:</label><input name="cmp_anim_preco_compra" type="text" value="${animalenc.animal.preco_compra}" onkeypress='return soDecimais(event)' autofocus /></li>            
                        </div>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Peso nascimento:</label><input name="cmp_anim_peso_nascimento" type="text" value="${animalenc.animal.peso_nascimento}" onkeypress='return soDecimais(event)' autofocus /></li>            
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Peso atual:</label><input name="cmp_anim_peso_atual" type="text" value="${animalenc.animal.peso_atual}" onkeypress='return soDecimais(event)' autofocus /></li>            
                        </div>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Estado atual:</label>
                                <select name="cmp_anim_estado_atual" autofocus required="">
                                    <c:if test="${animalenc.animal.estado_atual != null}"><option value="${animalenc.animal.estado_atual}">${animalenc.animal.estado_atual}</option></c:if>
                                    <c:if test="${animalenc.animal.estado_atual == null}"><option value="">Selecione...</option></c:if>
                                    <c:if test="${animalenc.animal.estado_atual ne 'INSEMINADA'}"><option value="INSEMINADA">INSEMINADA</option></c:if><!-- ne=not equals -->
                                    <c:if test="${animalenc.animal.estado_atual ne 'PRE-PARTO'}"><option value="PRE-PARTO">PRÉ-PARTO</option></c:if>    
                                    <c:if test="${animalenc.animal.estado_atual ne 'VACA SECA'}"><option value="VACA SECA">VACA SECA</option></c:if>  
                                    <c:if test="${animalenc.animal.estado_atual ne 'NOVILHA JOVEM'}"><option value="NOVILHA JOVEM">NOVILHA JOVEM</option></c:if>  
                                    <c:if test="${animalenc.animal.estado_atual ne 'NOVILHA APTA PARA REPRODUCAO'}"><option value="NOVILHA APTA PARA REPRODUCAO">NOVILHA APTA PARA REPRODUÇÃO</option></c:if>  
                                    <c:if test="${animalenc.animal.estado_atual ne 'EM CIO'}"><option value="EM CIO">EM CIO</option></c:if>  
                                    <c:if test="${animalenc.animal.estado_atual ne 'EM LACTACAO'}"><option value="EM LACTACAO">EM LACTAÇÃO</option></c:if>  
                                    <c:if test="${animalenc.animal.estado_atual ne 'GESTANTE'}"><option value="GESTANTE">GESTANTE</option></c:if>
                                </select>
                            </li>            
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Monitoramento</label>
                                <select name="cmp_anim_monitoramento" autofocus required="" onchange="ativa_campo_sensor()">
                                    <c:if test="${animalenc.animal.monitorando == 0}"><option value="0">INATIVO</option></c:if>
                                    <c:if test="${animalenc.animal.monitorando == 1}"><option value="1">ATIVO</option></c:if>
                                    <c:if test="${animalenc.animal.monitorando == null}"><option value="">Selecione...</option></c:if>
                                    <c:if test="${animalenc.animal.monitorando != 0}"><option value="0">INATIVO</option></c:if><!-- ne=not equals -->
                                    <c:if test="${animalenc.animal.monitorando != 1}"><option value="1">ATIVO</option></c:if>
                                </select>
                            </li>            
                        </div>
                        
                        <div class="coluna1">
                            <div id="divSensor">
                                <li class="li_corpo" id="mostra_sensor"><label >Sensor:</label>
                                    <select name="cmp_anim_sensor" autofocus >
                                        <c:if test="${animalenc.animal.sensor != null}"><option value="${animalenc.animal.sensor.id}">${animalenc.animal.sensor.codigo} - ${animalenc.animal.sensor.descricao}</option></c:if>
                                        <option value="">Selecione...</option>
                                        <%  for(Sensor sens : listaSensores) {    %>
                                                <option value="<%= sens.getId() %>"><%= sens.getCodigo() %> - <%= sens.getDescricao() %> </option>
                                        <%  }   %>
                                    </select>
                                </li>          
                            </div>            
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Propriedade:</label> 
                                <select name="cmp_anim_propriedade" autofocus required="" value="">
                                    <c:if test="${animalenc.animal.propriedade != null}"><option value="${animalenc.animal.propriedade.id}">${animalenc.animal.propriedade.codigo} - ${animalenc.animal.propriedade.nome}</option></c:if>
                                    <c:if test="${animalenc.animal.propriedade == null}"><option value="">Selecione...</option></c:if>
                                    <%  ResultSet rs = MetodosUteisDAOMySQL.getPropriedade();
                                        while (rs.next()) { %>
                                            <option value="<%= rs.getString("prop_id")%>"> <%= rs.getString("prop_codigo")%> - <%= rs.getString("prop_nome")%> </option>
                                    <%  }   %>
                                </select>
                            </li>            
                        </div>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Lote:</label> 
                                <select name="cmp_anim_lote" autofocus required="">
                                    <c:if test="${animalenc.animal.lote != null}"><option value="${animalenc.animal.lote.id}">${animalenc.animal.lote.codigo} - ${animalenc.animal.lote.descricao}</option></c:if>
                                    <c:if test="${animalenc.animal.lote == null}"><option value="">Selecione...</option></c:if>
                                    <%  ResultSet rs1 = MetodosUteisDAOMySQL.getLote();
                                        while (rs1.next()) { %>
                                            <option value="<%= rs1.getString("lot_id")%>"> <%= rs1.getString("lot_codigo")%> - <%= rs1.getString("lot_descricao")%> </option>
                                    <%  }   %>
                                </select>
                            </li>            
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Pasto:</label> 
                                <select name="cmp_anim_pasto" autofocus required="">
                                    <c:if test="${animalenc.animal.pasto != null}"><option value="${animalenc.animal.pasto.id}">${animalenc.animal.pasto.codigo} - ${animalenc.animal.pasto.nome}</option></c:if>
                                    <c:if test="${animalenc.animal.pasto == null}"><option value="">Selecione...</option></c:if>
                                    <%  ResultSet rs2 = MetodosUteisDAOMySQL.getPasto();
                                        while (rs2.next()) {    %>
                                            <option value="<%= rs2.getString("pas_id")%>"> <%= rs2.getString("pas_codigo")%> - <%= rs2.getString("pas_nome")%> </option>
                                    <%  } %>
                                </select>
                            </li>            
                        </div>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Status atual de Cio:</label>
                                <input name="cmp_anim_status_cio" type="text" autofocus value="${animalenc.status_cio}" readonly=""/>
                                <!--<select style="width: 23.5em" name="cmp_anim_status_cio" style="width: 24.5em" autofocus required="" >
                                    <//c:if test="$/{animalenc.status_cio == 0}"><option value="0">NÃO</option><//c:if>
                                    <//c:if test="$/{animalenc.status_cio == 1}"><option value="1">SIM</option><//c:if>
                                    <//c:if test="$/{animalenc.status_cio == null}"><option value="">Selecione...</option><//c:if>
                                    <//c:if test="$/{animalenc.status_cio != 0}"><option value="0">NÃO</option><//c:if>
                                    <//c:if test="$/{animalenc.status_cio != 1}"><option value="1">SIM</option><//c:if>
                                </select>-->
                            </li>          
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Previsão do Próximo Cio:</label>
                                <input name="cmp_anim_dt_prev_ini_prox_cio" style="width: 16em" type="datetime-local" autofocus value="<%= prev_prox_cio %>" readonly=""/>
                            </li>            
                        </div>
                            
                        <div class="coluna1">
                            <li class="li_corpo"><label>Duração Média do cio:</label><input name="cmp_anim_duracao_cio" type="text" onkeypress='return soNumeros(event)' autofocus required="" value="${animalenc.duracao_media_cio}"/></li>            
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Intervalo Medio de Anestro:</label>
                                <select name="cmp_anim_intervalo_anestro" autofocus required="">
                                    <option value="${animalenc.intervalo_padrao_anestro}">${animalenc.intervalo_padrao_anestro} dias</option>
                                    <c:forEach var="i" begin="10" end="36" step="1">
                                        <c:if test="${animalenc.intervalo_padrao_anestro != i}">
                                            <option value="<c:out value="${i}"/>" ><c:out value="${i}"/> dias </option>
                                        </c:if>
                                    </c:forEach>    
                                </select>
                            </li>            
                        </div>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Perfil de Atividade do Animal:</label>
                                <select name="cmp_anim_perfil_atividade" autofocus required="">
                                    <c:if test="${animalenc.media_passos_hora < 30}"><option value="">Selecione...</option></c:if><!-- MOSTRA O NULO ....-->
                                    <c:if test="${animalenc.media_passos_hora == 30}"><option value="MUITO CALMO">MUITO CALMO</option></c:if><!-- ...MOSTRA O QUE É ....-->
                                    <c:if test="${animalenc.media_passos_hora == 40}"><option value="CALMO">CALMO</option></c:if>
                                    <c:if test="${animalenc.media_passos_hora == 60}"><option value="NORMAL">NORMAL</option></c:if>
                                    <c:if test="${animalenc.media_passos_hora == 80}"><option value="AGITADO">AGITADO</option></c:if>
                                    <c:if test="${animalenc.media_passos_hora == 110}"><option value="MUITO AGITADO">MUITO AGITADO</option></c:if>
                                    <c:if test="${animalenc.media_passos_hora != 30}"><option value="MUITO CALMO">MUITO CALMO</option></c:if><!-- ...MOSTRA O QUE NÃO É.-->
                                    <c:if test="${animalenc.media_passos_hora != 40}"><option value="CALMO">CALMO</option></c:if>
                                    <c:if test="${animalenc.media_passos_hora != 60}"><option value="NORMAL">NORMAL</option></c:if>
                                    <c:if test="${animalenc.media_passos_hora != 80}"><option value="AGITADO">AGITADO</option></c:if>
                                    <c:if test="${animalenc.media_passos_hora != 110}"><option value="MUITO AGITADO">MUITO AGITADO</option></c:if>
                                </select>
                            </li>            
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Idade do Animal:</label><input name="cmp_anim_idade" type="text" autofocus value="${animalenc.idade}" readonly=""/></li>            
                        </div>
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Data/Hora Inicio do Último Cio:</label>
                                <input name="cmp_anim_dt_ini_ult_cio" style="width: 16em" type="datetime-local" autofocus value="<%= ini_ultimo_cio %>"/>
                            </li>            
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Data/Hora Fim do Último Cio:</label>
                                <input name="cmp_anim_dt_fim_ult_cio" style="width: 16em" type="datetime-local" autofocus required="" value="<%= fim_ultimo_cio %>"/>
                            </li>            
                        </div>
                             
                        <div class="coluna1">
                            <li class="li_corpo"><label>Alertas em aberto:</label>
                                <select name="cmp_anim_sob_alerta" autofocus >
                                    <option value="${animalenc.sob_alerta}">${animalenc.sob_alerta}</option>
                                    <c:forEach var="i" begin="0" end="50" step="1">
                                        <c:if test="${animalenc.sob_alerta != i}">
                                            <option value="<c:out value="${i}"/>" ><c:out value="${i}"/></option>
                                        </c:if>
                                    </c:forEach>    
                                </select>
                            </li>            
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Status da Atividade:</label>
                                <input name="cmp_anim_status_atividade" type="text" autofocus value="${animalenc.status_atividade}" readonly=""/>
                                <!--<select style="width: 23.5em" name="cmp_anim_status_atividade" style="width: 24.5em" autofocus required="">
                                    <//c:if test="$/{animalenc.status_atividade != null}"><option value="$/{animalenc.status_atividade}">$/{animalenc.status_atividade}</option><//c:if>
                                    <//c:if test="$/{animalenc.status_atividade == null}"><option value="">Selecione...</option><//c:if>
                                    <//c:if test="$/{animalenc.status_atividade ne 'BAIXA'}"><option value="BAIXA">BAIXA</option><//c:if>
                                    <//c:if test="$/{animalenc.status_atividade ne 'MEDIA'}"><option value="MEDIA">MEDIA</option><//c:if>    
                                    <//c:if test="$/{animalenc.status_atividade ne 'ALTA'}"><option value="ALTA">ALTA</option><//c:if>    
                                </select>-->
                            </li>            
                        </div>
                            
                        <div class="coluna1">
                            <li class="li_corpo"><label>Aprox. Próximo Cio Previsto:</label>
                                <input name="cmp_anim_aprox_cio" type="text" autofocus value="${animalenc.aprox_cio}" readonly=""/>
                                <!--<select style="width: 23.5em" name="cmp_anim_aprox_cio" style="width: 24.5em" autofocus >
                                    <option value="$/{animalenc.aprox_cio}">$/{animalenc.aprox_cio}</option>
                                    <//c:forEach var="i" begin="0" end="10" step="1">
                                        <//c:if test="$/{animalenc.aprox_cio != i}">
                                            <option value="<//c:out value="$/{i}"/>" ><//c:out value="$/{i}"/></option>
                                        <//c:if>
                                    <//c:forEach>    
                                </select>-->
                            </li>            
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Ocorrência Reprodutiva:</label>
                                <input name="cmp_anim_oco_reprodutiva" type="text" autofocus value="NORMAL" readonly=""/>
                                <!--<select style="width: 23.5em" name="cmp_anim_oco_reprodutiva" style="width: 24.5em" autofocus >
                                    <//c:if test="$/{animalenc.oco_reprodutiva != null}"><option value="$/{animalenc.oco_reprodutiva}">$/{animalenc.oco_reprodutiva}</option><//c:if>
                                    <//c:if test="$/{animalenc.oco_reprodutiva == null}"><option value="">Selecione...</option><//c:if>
                                    <//c:if test="$/{animalenc.oco_reprodutiva ne 'MUITO BAIXA'}"><option value="MUITO BAIXA">MUITO BAIXA</option><//c:if>
                                    <//c:if test="$/{animalenc.oco_reprodutiva ne 'BAIXA'}"><option value="BAIXA">BAIXA</option><///c:if>    
                                    <//c:if test="$/{animalenc.oco_reprodutiva ne 'NORMAL'}"><option value="NORMAL">NORMAL</option><//c:if>
                                    <//c:if test="$/{animalenc.oco_reprodutiva ne 'ALTA'}"><option value="ALTA">ALTA</option><//c:if>    
                                    <//c:if test="$/{animalenc.oco_reprodutiva ne 'MUITO ALTA'}"><option value="MUITO ALTA">MUITO ALTA</option><//c:if> 
                                </select>-->
                            </li>            
                        </div>       
                                
                        <div class="coluna1">
                            <li class="li_corpo"><label>Dias Atuais em Anestro:</label>
                                <input name="cmp_anim_dias_anestro" type="text" autofocus value="${animalenc.dias_em_anestro}" readonly=""/>
                                <!--<select style="width: 23.5em" name="cmp_anim_dias_anestro" style="width: 24.5em" autofocus >
                                    <option value="$/{animalenc.dias_em_anestro}">$/{animalenc.dias_em_anestro} dias</option>
                                    <//c:forEach var="i" begin="0" end="50" step="1">
                                        <//c:if test="$/{animalenc.dias_em_anestro != i}">
                                            <option value="<//c:out value="$/{i}"/>" ><//c:out value="$/{i}"/> dias</option>
                                        <//c:if>
                                    <//c:forEach>    
                                </select>-->
                            </li>            
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Tempo Atual de Anestro:</label>
                                <input name="cmp_anim_tempo_atual_anestro" type="text" autofocus value="${animalenc.tempo_atual_anestro}" readonly=""/>
                                <!--<select style="width: 23.5em" name="cmp_anim_tempo_atual_anestro" style="width: 24.5em" autofocus  >
                                    <//c:if test="$/{animalenc.tempo_atual_anestro != null}"><option value="$/{animalenc.tempo_atual_anestro}">$/{animalenc.tempo_atual_anestro}</option><//c:if>
                                    <//c:if test="$/{animalenc.tempo_atual_anestro == null}"><option value="">Selecione...</option><//c:if>
                                    <//c:if test="$/{animalenc.tempo_atual_anestro ne 'DIA DO CIO OCORRIDO'}"><option value="DIA DO CIO OCORRIDO">DIA DO CIO OCORRIDO</option><//c:if>
                                    <//c:if test="$/{animalenc.tempo_atual_anestro ne 'CURTO'}"><option value="CURTO">CURTO</option><//c:if>    
                                    <//c:if test="$/{animalenc.tempo_atual_anestro ne 'NORMAL'}"><option value="NORMAL">NORMAL</option><//c:if>  
                                    <//c:if test="$/{animalenc.tempo_atual_anestro ne 'LONGO'}"><option value="LONGO">LONGO</option><//c:if>  
                                    <//c:if test="$/{animalenc.tempo_atual_anestro ne 'MUITO LONGO'}"><option value="MUITO LONGO">MUITO LONGO</option><//c:if>  
                                </select>-->
                            </li>            
                        </div>
                            
                            
                        
                        <div class="coluna1">
                            <li class="li_corpo"><label>Observações:</label><textarea name="cmp_anim_observacao" type="text" autofocus rows="7" cols="30" value="${animalenc.animal.observacao}" ></textarea></li>            
                        </div>
                        <div class="coluna2">
                            <li class="li_corpo"><label>Imagem:</label><input name="cmp_anim_imagem" type="file" accept="image/*" value="${animalenc.animal.imagem}" onchange="openDialogFunc(this)" autofocus /></li>            
                            <div ><img style=" padding-left: 7px; width: 5em; height: 4.8em; border: 0px;" name="imagem" src="${animalenc.animal.imagem}"/></div>
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
        case "detalhar": {
            AnimalInfo aninfo = (AnimalInfo) request.getAttribute("detalhesanimal");
%>
            <div class="divCabecalho">        
                <ul>
                    <li class="li_titulo"><h1>Dados do Animal</h1></li>
                    <li></li>
                </ul>        
            </div>
            <div class="tabDetalhes" style="width:750px;">
                <table>
                    <!--<tr><td colspan="2">DADOS DO ANIMAL</td></tr>-->
                    <tr><th>CÓDIGO</th>
                        <td><% if(aninfo.getAnimal().getCodigo() != null)
                                out.print(aninfo.getAnimal().getCodigo()); %>
                        </td>
                    </tr>
                    <tr><th>APELIDO</th>
                        <td><% if(aninfo.getAnimal().getApelido() != null)
                                out.print(aninfo.getAnimal().getApelido()); %>
                        </td>
                    </tr>
                    <tr><th>RGN</th>
                        <td><% if(aninfo.getAnimal().getRgn() != 0)
                                out.print(aninfo.getAnimal().getRgn()); %>
                        </td>
                    </tr>
                    <tr><th>SEXO</th>
                        <td><% if(aninfo.getAnimal().getSexo() != null)
                                out.print(aninfo.getAnimal().getSexo()); %>
                        </td>
                    </tr>
                    <tr><th>GRAU DE SANGUE</th>
                        <td><% if(aninfo.getAnimal().getGrau_sangue() != null)
                                out.print(aninfo.getAnimal().getGrau_sangue()); %>
                        </td>
                    </tr>
                    <tr><th>TIPO</th>
                        <td><% if(aninfo.getAnimal().getTipo() != null)
                                out.print(aninfo.getAnimal().getTipo()); %>
                        </td>
                    </tr>
                    <tr><th>RAÇA</th>
                        <td><% if(aninfo.getAnimal().getRaca() != null)
                                out.print(aninfo.getAnimal().getRaca()); %>
                        </td>
                    </tr>
                    <tr><th>PELAGEM</th>
                        <td><% if(aninfo.getAnimal().getPelagem() != null)
                                out.print(aninfo.getAnimal().getPelagem()); %>
                        </td>
                    </tr>
                    <tr><th>DATA DE NASCIMENTO</th>
                        <td><% if(aninfo.getAnimal().getDt_nascimento() != null)
                                out.print(aninfo.getAnimal().getDt_nascimento()); %>
                        </td>
                    </tr>
                    <tr><th>DATA DE CADASTRO</th>
                        <td><% if(aninfo.getAnimal().getDt_cadastro() != null)
                                out.print(aninfo.getAnimal().getDt_cadastro()); %>
                        </td>
                    </tr>
                    <tr><th>GERAÇÃO</th>
                        <td><% if(aninfo.getAnimal().getTipo_geracao() != null)
                                out.print(aninfo.getAnimal().getTipo_geracao()); %>
                        </td>
                    </tr>
                    <tr><th>ESTADO ATUAL</th>
                        <td><% if(aninfo.getAnimal().getEstado_atual() != null)
                                out.print(aninfo.getAnimal().getEstado_atual()); %>
                        </td>
                    </tr>
                    <tr><th>PESO AO NASCER</th>
                        <td><% if(aninfo.getAnimal().getPeso_nascimento() != 0)
                                out.print((int) aninfo.getAnimal().getPeso_nascimento()); %>
                        </td>
                    </tr>
                    <tr><th>PESO ATUAL</th>
                        <td><% if(aninfo.getAnimal().getPeso_atual() != 0)
                                out.print((int)aninfo.getAnimal().getPeso_atual()); %>
                        </td>
                    </tr>
                    <tr><th>PREÇO DE COMPRA</th>
                        <td><% if(aninfo.getAnimal().getPreco_compra() != 0)
                                out.print((int)aninfo.getAnimal().getPreco_compra()); %>
                        </td>
                    </tr>
                    <tr><th>OBSERVAÇÕES</th>
                        <td><% if(aninfo.getAnimal().getObservacao() != null)
                                out.print(aninfo.getAnimal().getObservacao()); %>
                        </td>
                    </tr>
                    <tr><th>MONITORAMENTO</th>
                        <td><% if(aninfo.getAnimal().getMonitorando() == 0 )
                                out.print("INATIVO");
                               else
                                out.print("ATIVO"); %>
                        </td>
                    </tr>
                    <tr><th>PROPRIEDADE VINC.</th>
                        <td><% if(aninfo.getAnimal().getPropriedade() != null)
                                out.print(aninfo.getAnimal().getPropriedade().getNome()); %>
                        </td>
                    </tr>
                    <tr><th>LOTE VINC.</th>
                        <td><% if(aninfo.getAnimal().getLote() != null)
                                out.print(aninfo.getAnimal().getLote().getDescricao()); %>
                        </td>
                    </tr>
                    <tr><th>PASTO VINC.</th>
                        <td><% if(aninfo.getAnimal().getPasto()!= null)
                                out.print(aninfo.getAnimal().getPasto().getNome()); %>
                        </td>
                    </tr>
                    <tr><th>SENSOR VINC.</th>
                        <td><% if(aninfo.getAnimal().getSensor()!= null)
                                out.print(aninfo.getAnimal().getSensor().getCodigo()); %>
                        </td>
                    </tr>
                    <tr><th>DATA/HORA INÍCIO DO ÚLTIMO CIO</th>
                        <td><% if(aninfo.getDt_ini_ult_cio() != null)
                                out.print(aninfo.getDt_ini_ult_cio()+" "+ aninfo.getHr_ini_ult_cio()); %>
                        </td>
                    </tr>
                    <tr><th>DATA/HORA FIM DO ÚLTIMO CIO</th>
                        <td><% if(aninfo.getDt_fim_ult_cio() != null)
                                out.print(aninfo.getDt_fim_ult_cio()+" "+ aninfo.getHr_fim_ult_cio()); %>
                        </td>
                    </tr>
                    <tr><th>DATA/HORA PREVISTA DO PRÓXIMO CIO</th>
                        <td><% if(aninfo.getDt_prev_ini_prox_cio() != null)
                                out.print(aninfo.getDt_prev_ini_prox_cio()+" "+ aninfo.getHr_prev_ini_prox_cio()); %>
                        </td>
                    </tr>
                    <tr><th>APROXIMAÇÃO DO PRÓXIMO CIO</th>
                        <td><% out.print(aninfo.getAprox_cio()); %></td>
                    </tr>
                    <tr><th>INTERVALO PADRÃO DE ANESTRO</th>
                        <td><% if(aninfo.getIntervalo_padrao_anestro() != 0)
                                out.print(aninfo.getIntervalo_padrao_anestro() +" dias"); %>
                        </td>
                    </tr>
                    <tr><th>STATUS DO CIO</th>
                        <td><%  if(aninfo.getStatus_cio() == 1 )
                                    out.print("EM ESTRO");
                                else
                                    out.print("EM ANESTRO"); %>
                        </td>
                    </tr>
                    <tr><th>DURAÇÃO MÉDIA DE CIO</th>
                        <td><% if(aninfo.getDuracao_media_cio()!= 0)
                                out.print(aninfo.getDuracao_media_cio() +" dias"); %>
                        </td>
                    </tr>
                    <tr><th>MÉDIA DE PASSOS/HORA</th>
                        <td><% if(aninfo.getMedia_passos_hora() != 0)
                                out.print(aninfo.getMedia_passos_hora() +" passos/h"); %>
                        </td>
                    </tr>
                    <tr><th>TEMPO ATUAL EM ANESTRO</th>
                        <td><% if(aninfo.getTempo_atual_anestro() != null)
                                out.print(aninfo.getTempo_atual_anestro()+ " dias"); %>
                        </td>
                    </tr>
                    <tr><th>STATUS DA MOVIMENTAÇÃO</th>
                        <td><% if(aninfo.getStatus_atividade()!= null)
                                out.print(aninfo.getStatus_atividade()); %>
                        </td>
                    </tr>
                    <tr><th>ALERTAS EM ABERTO</th>
                        <td><% out.print(aninfo.getSob_alerta()); %></td>
                    </tr>       
                </table>
            </div>
            <div id="divDetalhar"> 
                <form class="form_padrao" name="formDetalhar" action="" method="POST">
                    <ul>
                        <li class="li_corpo"></li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" type="submit"><a href="/siac/control?cls=Animal&mtd=editar&id=<%= aninfo.getAnimal().getId() %>"/>Editar</a></button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="submit" onclick="return confirm('Confirma a exclusão do Animal ${aninfo.animal.apelido }?')"><a href="/siac/control?cls=Animal&mtd=excluir&id=<%= aninfo.getAnimal().getId() %>"/>Excluir</a></button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarFormCadAnimal" value="Cancelar" onclick="javascript:history.back(1)">Voltar</button>
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
                <form class="form_padrao"  name="formBuscar" action="/siac/control?cls=Animal&mtd=buscar" method="POST" >
                    <ul>
                        <li class="li_titulo"><h1>Animal</h1></li>
                        <li class="li_corpo">
                            <input placeholder="informe o código do Animal" type="text" name="cmp_bsc_animal" size="50" />
                            <button class="submit_buscar" type="submit" name="btn_buscar">Buscar Animal</button>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir"><a href="principal?d=forms&a=cadastrar&f=animal">Incluir Animal</a></button>
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
                <form class="form_padrao" name="formResultBusca" action="/siac/control?cls=Animal&mtd=buscar" method="POST">
                    <ul>
                        <li class="li_titulo"><h1>Animais</h1></li>
                        <li class="li_corpo">
                            <input placeholder="informe o código do Animal" type="text" name="cmp_bsc_animal" value="" size="50" />
                            <button class="submit_buscar" type="submit" name="btn_buscar">Buscar Animal</button>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir"><a href="principal?d=forms&a=cadastrar&f=animal">Incluir Animal</a></button>
                        </li>
                        <li class="li_corpo"></li>
                    </ul>
                </form>
            </div>
            <div class="tabResultBusca" style="width: 850px; ">
                <table>
                    <tr><td colspan="11"><center>Animais</center></td></tr>    
                    <tr>
                        <th>Codigo</th>
                        <th>Apelido</th>
                        <th>Situação</th>
                        <th>Monitoramento</th>
                        <th>Pasto</th>
                        <th>Lote</th>
                        <th>Previsão de Cio</th>
                        <th>Movimentação</th>
                        <th colspan="3">Ações</th>
                    </tr>
                    <%  for (AnimalInfo anim : listaAnimalInfo) {%>
                            <tr>
                                <td><%  if(anim.getAnimal().getCodigo() != null)
                                            out.write(anim.getAnimal().getCodigo()); %></td>
                                <td><%  if(anim.getAnimal().getApelido() != null)
                                            out.write(anim.getAnimal().getApelido()); %></td>
                                <td><%  if(anim.getStatus_cio() == 1 )
                                            out.write("ESTRO");
                                        else
                                            out.write("ANESTRO"); %></td>
                                <td><%  if(anim.getAnimal().getMonitorando() == 1)
                                            out.write("ATIVO");
                                        else
                                            out.write("INATIVO"); %></td>
                                <td><%  if(anim.getAnimal().getPasto()!= null)
                                            out.write(anim.getAnimal().getPasto().getNome()); %></td>
                                <td><%  if(anim.getAnimal().getLote()!= null)
                                            out.write(anim.getAnimal().getLote().getDescricao()); %></td>
                                <td><%  if(anim.getAnimal().getMonitorando() == 1) {
                                            if(anim.getDt_prev_ini_prox_cio() != null)
                                                out.write(anim.getDt_prev_ini_prox_cio()+" "+ anim.getHr_prev_ini_prox_cio()); 
                                        } %></td>
                                <td><%  if(anim.getAnimal().getMonitorando() == 1) {
                                            if(anim.getStatus_atividade() != null)
                                                out.write(anim.getStatus_atividade()); 
                                        } %></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=Animal&mtd=detalhar&id=<%= anim.getAnimal().getId()%>" title="Detalhar"><center><img src="imagens/icones/btn_detalhar.png" alt="Detalhes"/></center></a></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=Animal&mtd=editar&id=<%= anim.getAnimal().getId()%>" title="Editar"><center><img src="imagens/icones/btn_editar.png" alt="Editar"/></center></a></td>
                                <td style="width: 20px;"><a href="/siac/control?cls=Animal&mtd=excluir&id=<%= anim.getAnimal().getId()%>" title="Excluir" onclick="return confirm('Confirma a exclusão do Animal <%= anim.getAnimal().getCodigo()%>?')"><center><img src="imagens/icones/btn_excluir.png" alt="Excluir"/></center></a></td>
                            </tr>
                    <%  }   %>             
                </table> 
            </div>
            <br>        
<%
            break;
        }
        default:
            break;
    }
%>