<%@page import="br.edu.cio.model.AtividadeAnimal"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="br.edu.cio.model.AnimalInfo"%>
<%@page import="br.edu.cio.model.Configuracao"%>
<%@page import="javax.swing.JOptionPane"%>
<%@page import="br.edu.cio.model.GraficoAtividade"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="br.edu.cio.model.Leitura"%>
<%@page import="br.edu.cio.model.dao.mysql.MetodosUteisDAOMySQL"%>
<%@page import="java.sql.ResultSet"%>
<jsp:useBean id="conf" scope="session" class="br.edu.cio.model.Configuracao"/>
<jsp:useBean id="gratv" scope="request" class="br.edu.cio.model.GraficoAtividade"/>
<jsp:useBean id="aninfo" scope="request" class="br.edu.cio.model.AnimalInfo"/>

<%  switch (request.getParameter("a")) {
        case "buscar": {
%>
            <div id="formBuscar">        
                <form class="form_padrao"  name="formBuscar" action="/siac/control?cls=Monitoramento&mtd=buscar" method="POST" >
                    <ul>
                        <div id="consulta1" >
                        <li class="li_titulo"><h1>Monitoramento da Atividade Animal</h1></li>
                        <li class="li_corpo"><label>Animal Monitorado:</label> 
                            <select name="cmp_grafico_1" style="width: 23.5em" id="cmp_grafico_1" autofocus required="">
                                <option value="">Selecione...</option>
                                <%  ResultSet rs1 = MetodosUteisDAOMySQL.getAnimaisMonitorados();
                                    while (rs1.next()) {    %>
                                        <option value="<%= rs1.getInt("ani_id")%>"><%= rs1.getInt("ani_id")%> - <%= rs1.getString("ani_apelido")%></option>
                                <%  }   %>
                            </select>
                        </li>
                        <li class="li_corpo" id="limite_busca">
                            <label>Início do Período:</label>
                            <input name="cmp_grafico_2" style="width: 20em" type="datetime-local" autofocus required="" />
                        </li> 
                        <li class="li_corpo" id="limite_busca2">
                            <label>Fim do Período:</label><input name="cmp_grafico_3" style="width: 20em" type="datetime-local" autofocus required="" />
                        </li>
                        <li class="li_corpo"><label>Intervalo:</label> 
                            <select name="cmp_grafico_4" id="cmp_grafico_2" style="width: 23.5em" autofocus required="" >
                                <option value="">Selecione...</option>
                                <option value="hour">Hora</option>
                                <option value="day">Dia</option>
                                <option value="week">Semana</option>
                                <option value="month">Mês</option>
                                <option value="year">Ano</option>
                            </select>
                        </li>
                        </div>
                        <div id="consulta2"></div>
                        <li class="li_corpo"><label>Escala:</label> 
                            <select name="cmp_grafico_5" style="width: 23.5em" autofocus required="">
                                <option value="">Selecione...</option>
                                <option value="PASSADASHORA">PASSADAS/HORA</option>
                                <option value="NIVELATIVIDADE">NÍVEL DE ATIVIDADE</option>
                            </select>
                        </li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" type="submit" name="btn_gerar">Gerar Gráfico</button>
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarForm" value="Cancelar" onclick="javascript:history.back(1)">Cancelar</button>
                        </li>
                    </ul>
                </form>
            </div>
<%
            break;
        }
        case "gerar": {
%>
            <%      int i = 0;
                    Leitura leituras = new Leitura();
                    AnimalInfo ainfo = (AnimalInfo) request.getAttribute("aninfoGrafico");
                    Configuracao con = (Configuracao) session.getAttribute("conf");
                    GraficoAtividade ga = (GraficoAtividade) request.getAttribute("paramGeraGrafico");
                    List<Leitura> leiturasGrafico = (ArrayList<Leitura>) request.getAttribute("leiturasGrafico");
                    List<AtividadeAnimal> atvLeiturasGrafico = (ArrayList<AtividadeAnimal>) request.getAttribute("AtvLeiturasGrafico");
                    /*
                    SimpleDateFormat formatoBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    SimpleDateFormat formatoBD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dth_inicio = "";
                    String dth_fim = "";
                    if(ga.getInicioPeriodo() != null)
                        dth_inicio = (formatoBD.format(formatoBr.parse(ga.getInicioPeriodo())));
                    if(ga.getFimPeriodo() != null)
                        dth_fim = (formatoBD.format(formatoBr.parse(ga.getFimPeriodo())));
                    */
            %>
            <div id="formBuscar">        
                <form class="form_padrao"  name="formBuscar" action="/siac/control?cls=Monitoramento&mtd=buscar" method="POST" >
                    <ul>
                        <li class="li_titulo"><h1>Monitoramento da Atividade Animal</h1></li>
                        <li class="li_ultimo">
                            <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarForm" value="Cancelar" onclick="javascript:history.back(1)">Redefinir Gráfico</button>
                        </li>
                    </ul>
                </form>
            </div>
            <div style="width: 800px; float: left; margin-left: 20px;">
                <ul class="ul_monitoramento">
                    <div id="chartContainer" style="height: 350px; width: 100%;"></div>
                    <!--<div id="container" style="height: 400px; min-width: 310px"></div>-->
                </ul>
            </div>
            <script lang="javascript" type="text/javascript">
                window.onload = function () {
                    var intervalo = 0;
                    <% if(ga.getIntervalo().equals("hour")) {%>
                            intervalo = <%= con.getIntervalo_entre_leituras() %>;
                    <% } else { %>
                            intervalo = 1;    
                    <% } %>
                    var chart = new CanvasJS.Chart("chartContainer",
                    { 
                        theme: "theme4",//theme1
                        title:{
                            text: "Gráfico de Atividade Animal (Passos/hora)"
                        },
                        axisY :{
                            //minimum: 0,
                            //maximum: 1000,
                            //valueFormatString: "#0,,.",
                            //suffix: "",
                            //labelFontColor: "rgb(0,75,141)",
                            /*plotLines: [{
                                    value: 0,
                                    width: 1,
                                    interval: 100,
                                    color: '#BBAF4B'//'#022729'
                                }]*/
                            title: "Passos/hora",
                            includeZero: false
                        },
                        axisX: {
                            labelAngle: -45,
                            valueFormatString: "DD/MM/YYYY HH:mm:ss",//"MMM",
                            interval: intervalo,
                            intervalType: "<%= ga.getIntervalo() %>"//"month"
                            //title: "</%= "Período" %>",
                            //minimum: new Date(</%= dt0[2] %>, </%= Integer.parseInt(dt0[1])-1 %>, </%= dt0[0] %>, </%= hr0[0] %>, </%= hr0[1] %>, </%= hr0[2] %>) ,
                        },
                        legend: {
                            verticalAlign: "bottom"
                        },        
                                
                        data: [
                        {        
                            toolTipContent: "{x}'<br/>'{y} passos/hora",
                            type: "spline",//"splineArea"
                            name: "views",
                            lineThickness: 2,
                            showInLegend: true,
                            legendText: "Fonte: SIAC Bovino",
                            markerSize: 10,
                            color: "rgba(2,39,41,.9)",
                            indexLabelFontColor: "orangered", 
                            dataPoints: ( function () {
                                var dataPoints = [];
                                <% 
                                    int variacao = 0;
                                    double perc_variacao = 0;
                                    for(AtividadeAnimal atv : atvLeiturasGrafico) { 
                                        String[] dt = (atv.getLeitura().getData()).split("/");
                                        String[] hr = (atv.getLeitura().getHora()).split(":");
                                        variacao = (atv.getLeitura().getnPassadas()/atv.getLeitura().getIntervalo_config()) - ainfo.getMedia_passos_hora(); 
                                        perc_variacao = variacao * 100 / ainfo.getMedia_passos_hora();
                                        if((int)perc_variacao > con.getPerc_min_atv_media()){ %>
                                            dataPoints.push({
                                                x: new Date(<%= dt[2] %>, <%= Integer.parseInt(dt[1])-1 %>, <%= dt[0] %>, <%= hr[0] %>, <%= hr[1] %>, <%= hr[2] %>),
                                                y: <%= (atv.getLeitura().getnPassadas()/atv.getLeitura().getIntervalo_config()) %>,
                                                indexLabel: "", indexLabelOrientation: "vertical", indexLabelFontColor: "orangered", markerColor: "#840043", markerType: "circle", toolTipContent: "CIO SUSPEITO'<br/>'{x}'<br/>'{y} passos/hora'<br/>'Nível de Atividade: <%= atv.getClassificacao() %>"
                                            });
                                <%      } else { %>
                                            dataPoints.push({
                                                x: new Date(<%= dt[2] %>, <%= Integer.parseInt(dt[1])-1 %>, <%= dt[0] %>, <%= hr[0] %>, <%= hr[1] %>, <%= hr[2] %>),
                                                y: <%= (atv.getLeitura().getnPassadas()/atv.getLeitura().getIntervalo_config()) %>,
                                                toolTipContent: "{x}'<br/>'{y} passos/hora'<br/>'Nível de Atividade: <%= atv.getClassificacao() %>",
                                            });
                                <%      }
                                    }   %>        
                                return dataPoints;
                            }())
                        }
                        ]
                    });
                    chart.render();
                }
            </script>    
<%
            break;
        }
        default:
            break;
    }
%>

<script lang="javascript" type="text/javascript">  
    function mostra_campos_especificos_da_busca() {
        var cmp_grafico_2 = document.formBuscar.cmp_grafico_2[document.formBuscar.cmp_grafico_2.selectedIndex].value 
        switch(cmp_grafico_2){  
            case 'PORMES':{  
                    $("#limite_busca").remove();
                    $("#limite_busca2").remove(); 
                    var  novoscampos =  '<li class="li_corpo" id="limite_busca"><label>Informe o Mês:</label><input id="cmp_grafico_3" name="cmp_grafico_3" type="month" style="width: 20.2em"  autofocus required="" /></li>';  
                    $("#consulta2").append(novoscampos);
                    break;
            }
            case 'PORANO':{
                    $("#limite_busca").remove();
                    $("#limite_busca2").remove();
                    var  novoscampos =  '<li class="li_corpo" id="limite_busca"><label>Informe o Ano:</label><input name="cmp_grafico_3" style="width: 20em" type="text" autofocus required="" /></li>'; 
                    $("#consulta2").append(novoscampos);
                    break;
            }
            case 'PORPERIODO':{ 
                    $("#limite_busca").remove();
                    $("#limite_busca2").remove();
                    var  novoscampos =  '<li class="li_corpo" id="limite_busca"><label>Início do Período:</label><input name="cmp_grafico_3" style="width: 20em" type="datetime-local" autofocus required="" /></li>'; 
                        novoscampos +=  '<li class="li_corpo" id="limite_busca2"><label>Fim do Período:</label><input name="cmp_grafico_32" style="width: 20em" type="datetime-local" autofocus required="" /></li>'; 
                    $("#consulta2").append(novoscampos);
                    break;
            }
            case '':{
                    $("#limite_busca").remove();
                    $("#limite_busca2").remove();
                    break;
            }
        }
    }
    
    </script>
    <script>
        $(function(){

            $(document).ready(function () {
                Highcharts.setOptions({
                    global: {
                        useUTC: false
                    }
                });
                $('#container').highcharts({
                    chart: {
                        type: 'spline',
                        animation: Highcharts.svg, // don't animate in old IE
                        marginRight: 10,
                        events: {
                            load: function () {
                                // set up the updating of the chart each second
                                //var series = this.series[0];
                                //setInterval(function () {
                                    //var x = (new Date()).getTime(), // current time
                                        //y = Math.random();
                                    //series.addPoint([x, y], true, true);
                                //}, 5000);
                            }
                        }
                    },
                    title: {
                        text: 'Monitoramento de passadas por animal'
                    },
                    xAxis: {
                        valueFormatString: "DD/MM/YYYY",//"MMM",
                        interval: 100,
                        type: '',
                        tickPixelInterval: 100,
                    },
                    yAxis: {
                        title: {
                            text: 'Value'
                        },
                        plotLines: [{
                            value: 0,
                            width: 1,
                            interval: 100,
                            color: '#022729'
                        }]
                    },
                    tooltip: {
                        //formatter: function () {
                           // return '<b>' + this.series.name + '</b><br/>' +
                            //    Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                             //   Highcharts.numberFormat(this.y, 2);
                        //}
                    },
                    legend: {
                        enabled: false
                    },
                    exporting: {
                        enabled: false
                    },  
                });
            });
        });           
    </script>
<!-- GERAÇÃO MAIS RÁPIDA
dataPoints: ( function () {
    var dataPoints = [];
    </% /*
        int variacao = 0;
        double perc_variacao = 0;
        for(Leitura l : leiturasGrafico) { 
            String[] dt = (l.getData()).split("/");
            String[] hr = (l.getHora()).split(":");
            variacao = (l.getnPassadas()/l.getIntervalo_config()) - ainfo.getMedia_passos_hora(); 
            perc_variacao = variacao * 100 / ainfo.getMedia_passos_hora();
            if((int)perc_variacao > con.getPerc_min_atv_media()){*/
    %>           
                dataPoints.push({
                    x: new Date(</%= dt[2] %>, </%= Integer.parseInt(dt[1])-1 %>, / dt[0] %>, </%= hr[0] %>, </%= hr[1] %>, </%= hr[2] %>),
                    y: </%= (l.getnPassadas()/l.getIntervalo_config()) %>,
                    indexLabel: "", indexLabelOrientation: "vertical", indexLabelFontColor: "orangered", markerColor: "#840043", markerType: "circle", toolTipContent: "CIO SUSPEITO'<br/>'{x}'<br/>'{y} passos/hora"
                });               

    </%     // } else { %>
                dataPoints.push({
                    x: new Date(</%= dt[2] %>, </%= Integer.parseInt(dt[1])-1 %>, </%= dt[0] %>, </%= hr[0] %>, </%= hr[1] %>, </%= hr[2] %>),
                    y: </%= (l.getnPassadas()/l.getIntervalo_config()) %>
                });
    </%      }
        }
    %>        
    return dataPoints;
}())
-->