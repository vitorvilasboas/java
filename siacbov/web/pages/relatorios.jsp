<%@page import="br.edu.cio.model.Sensor"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.edu.cio.model.Animal"%>
<%@page import="java.util.List"%>
<%@page import="br.edu.cio.model.dao.mysql.MetodosUteisDAOMySQL"%>
<%@page import="java.sql.ResultSet"%>
<% 
    ResultSet rs1 = MetodosUteisDAOMySQL.getAnimaisMonitorados();
    List<Animal> animais = new ArrayList<>();
    while (rs1.next()) {
        Animal a = new Animal();
        a.setId(rs1.getInt("ani_id"));
        a.setCodigo(rs1.getString("ani_codigo"));
        a.setApelido(rs1.getString("ani_apelido"));
        animais.add(a);
    }
%>

<div id="formBuscar">        
    <form class="form_padrao"  name="formBuscar" action="/siac/ServletRelatorios" target="_blank" method="POST" >
        <ul>
            <div id="consulta1" >
                <li class="li_titulo"><h1>Relatórios Personalizados</h1></li>
                <li class="li_corpo"><label>Relatório:</label> 
                    <select name="cmp_relatorio_1" id="cmp_grafico_2" style="width: 23.5em" autofocus required="" onchange="javascript:mostra_campo_animal()">
                        <option value="">Selecione...</option><option value="ocorrencias">Ocorrência de cio do animal</option><option value="emcio">Animais em Cio</option>
                    </select>
                </li>
            </div>    
            <div id="consulta2"></div>    
            <li class="li_ultimo">
                <button class="submit_incluir" type="submit" name="btn_gerar">Gerar</button>
                <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarForm" value="Cancelar" onclick="javascript:history.back(1)">Cancelar</button>
            </li>
        </ul>
    </form>
</div>
<script lang="javascript" type="text/javascript"> 
    function mostra_campo_animal() {
        var cmp_relatorio_1 = document.formBuscar.cmp_relatorio_1[document.formBuscar.cmp_relatorio_1.selectedIndex].value 
        switch(cmp_relatorio_1){  
            case 'emcio':{  
                    $("#mostra_animal").remove();
                    break;
            }
            case 'ocorrencias':{ 
                    $("#mostra_animal").remove(); 
                    var campo =  '<li class="li_corpo" id="mostra_animal"><label>Animal:</label>';
                        campo += '<select name="cmp_relatorio_2" type="text" autofocus required=""><option value="">Selecione...</option>';
                        campo += '<%  for(Animal aa : animais) {    %>';
                        campo += '<option value="<%= aa.getId() %>"><%= aa.getCodigo() %> - <%= aa.getApelido()%>';
                        campo += '<%  }   %>';
                        campo += '</option></select></li>';
                    $("#consulta2").append(campo);
                    break;
            }
            case '':{
                    $("#mostra_animal").remove();
                    break;
            }
        }
    }
</script>