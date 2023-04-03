<%@page import="br.edu.cio.model.Configuracao"%>
<%@page import="javax.swing.JOptionPane"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.edu.cio.model.Animal"%>
<%@page import="br.edu.cio.model.AnimalInfo"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="conf" scope="session" class="br.edu.cio.model.Configuracao"/>
<jsp:useBean id="infoanimon" scope="session" class="br.edu.cio.model.AnimalInfo"/>
<!--<//jsp:include page="control?cls=Animal&mtd=carregar"/>CARREGA OS DADOS NECESSÁRIOS PARA A PÁGINA -->

<jsp:include page="../control?cls=Animal&mtd=carregar"/>

<c:if test="${conf != null}" >
    <c:if test="${conf.reg_sensores == 'manual'}">
        <form name="formCarregarDadosSensores" action="control?cls=BCC&mtd=atualizarManual" method="POST">
            <ul>
                <li class="li_ultimo">
                    <button class="submit_incluir" type="submit" name="btn_carregar_dados_sensores">Carregar Dados de Sensores</button>
                </li>
            </ul>
            <!--<input type="submit" value="Carregar Dados de Sensores" name="btn_carregar_dados_sensores" />-->
        </form>
    </c:if>
</c:if>

<div style="width: 800px; float: left;">
    <ul class="ul_monitoramento">
<%      int i = 0;
        AnimalInfo animali = new AnimalInfo();
        List<AnimalInfo> listaAnimaisInfo = (ArrayList<AnimalInfo>) session.getAttribute("infoAnimaisMonitorados");
        int x[] = new int[listaAnimaisInfo.size()+1];
        if(listaAnimaisInfo != null){
            for (AnimalInfo aniInfo : listaAnimaisInfo) {
                animali = aniInfo; 
%>
                <script lang="javascript" type="text/javascript">
                    $(document).ready(function () {

                        var rangeGradient = {
                            type: 'linearGradient',
                            x0: 0.1,//0.5
                            y0: 0,
                            x1: 1,
                            y1: 1,
                            colorStops: [{ offset: 0, color: '#474747' },
                                         { offset: 1, color: '#990000'}]
                        };

                        var needleGradient = {
                            type: 'linearGradient',
                            x0: 0,
                            y0: 0.5,
                            x1: 1,
                            y1: 0.5,
                            colorStops: [{ offset: 0, color: '#ffffff' },
                                         { offset: 1, color: '#FFFF66'}]
                        };
                        var i = <%= i %>;
                        var valor = <%= aniInfo.getAprox_cio() %>
                            var nome = "#jqLinearGauge"+i;
                            $(nome).jqLinearGauge({
                                orientation: 'horizontal',
                                //background: '#F7F7F7',
                                border: {
                                    padding: 2,
                                    lineWidth: 0,
                                    strokeStyle: '#76786A'//76786A
                                },
                                tooltips: {
                                    disabled: false,
                                    highlighting: true
                                },
                                animation: {
                                    duration: 1
                                },
                                scales: [
                                         {
                                             minimum: 0,
                                             maximum: 10,
                                             labels: {
                                                 offset: 0.10,
                                                 fillStyle: '#B2183E',
                                                 stringFormat: '%d',
                                                 font: '12px sans-serif'
                                             },
                                             majorTickMarks: {
                                                 length: 2,
                                                 offset: 0.18,
                                                 lineWidth: 2
                                             },
                                             minorTickMarks: {
                                                 length: 6,
                                                 visible: true,
                                                 interval: 2,
                                                 offset: 0.34,
                                                 lineWidth: 2
                                             },
                                             ranges: [
                                                         {
                                                             startValue: 0,
                                                             endValue: 10,
                                                             innerOffset: 0.23,
                                                             outerStartOffset: 0.55,
                                                             outerEndOffset: 0.55,
                                                             fillStyle: rangeGradient
                                                         }
                                                     ],
                                             needles: [
                                                         {
                                                             value: valor,
                                                             fillStyle: needleGradient,
                                                             innerOffset: 0.33,
                                                             outerOffset: 0.55
                                                         }
                                                      ]
                                         }
                                        ]
                            });
                            //i = i+1;
                        //}
                    });
                </script>
                <li class="li_monitoramento" style="width: 800px; height: 90px; ">
                    <div style=" width:350px; height: 85px; float: left;">
                        <b>Animal:</b> <%= aniInfo.getAnimal().getRgn()+" - "+aniInfo.getAnimal().getApelido()%><br>
                        <b>Último Cio Encerrado:</b> <%= aniInfo.getDt_fim_ult_cio()+" "+aniInfo.getHr_fim_ult_cio() %><br>
                        <b>Previsão do Próximo Cio:</b> <%= aniInfo.getDt_prev_ini_prox_cio()+" "+aniInfo.getHr_prev_ini_prox_cio() %><br>
                        <b>Tempo Atual em Anestro:</b> <%= aniInfo.getDias_em_anestro()+" dias ("+aniInfo.getTempo_atual_anestro()+")" %>
                    </div>
                    <div id="<%="jqLinearGauge"+i %>" style="width: 400px; height: 85px; padding: 0px; float: left;" ></div>
                </li>
<%              i++;
            }
        } else
            out.write("Nenhum animal monitorado.");
%>
    </ul>

    <br>
    <div style="float: end;"> 
        <%  SimpleDateFormat formatoCompletoBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Configuracao configuration = (Configuracao) session.getAttribute("conf");
            Date dth_sistema = formatoCompletoBr.parse(configuration.getDt_ult_captura()+ " " + configuration.getHr_ult_captura());
            out.write("Hora do Sistema: " + formatoCompletoBr.format(dth_sistema));
        %>
        <br>
        <%
            dth_sistema = formatoCompletoBr.parse(configuration.getData_atual()+ " " + configuration.getHora_atual());
            out.write("Hora Real: " + formatoCompletoBr.format(dth_sistema));
        %>
    </div>   
</div>    

<!--<//c:forEach items="$/{animaisMonitorados}" var="corrente">
    <//c:out value="$/{corrente.apelido}" />
    <//c:out value="$/{corrente.codigo}" />
<//c:forEach>
<script> var proxCio = new Array();//declarando um array no javascrypt</script>
-->    

    