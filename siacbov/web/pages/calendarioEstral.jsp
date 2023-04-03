<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="br.edu.cio.model.AnimalInfo"%>
<%@page import="br.edu.cio.model.Configuracao"%>
<%@page import="br.edu.cio.model.Cio"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<script type="text/javascript" src="js/cal/jquery-core/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="js/cal/jquery-ui/smoothness/jquery-ui-1.8.1.custom.min.js"></script>
<script type="text/javascript" src="js/cal/colorpicker/colorpicker.js"></script> <!-- Include color picker plugin (Not required for calendar plugin. Used for example.) -->
<script type="text/javascript" src="js/cal/jquery-qtip-1.0.0-rc3140944/jquery.qtip-1.0.js"></script>
    <!-- Causa problema no Monitoramento por Previsão de Cio -->
<!--     ** jshashtable-2.1.js MUST BE INCLUDED BEFORE THE FRONTIER CALENDAR PLUGIN. ** -->
<script type="text/javascript" src="js/cal/lib/jshashtable-2.1.js"></script>
<script type="text/javascript" src="js/cal/frontierCalendar/jquery-frontier-cal-1.3.2.min.js"></script>

<%      int i = 0;
                    //Leitura leituras = new Leitura();
                    List<AnimalInfo> listaAnimaisInfo = (ArrayList<AnimalInfo>) session.getAttribute("infoAnimaisMonitorados");
                    Configuracao con = (Configuracao) session.getAttribute("conf");
                    List<Cio> ciosReg = (ArrayList<Cio>) request.getAttribute("ciclosCadastrados");
                    
                    SimpleDateFormat formatoDataUSA = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formatoDataBr = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat formatoHoraBr = new SimpleDateFormat("HH:mm:ss");
                    SimpleDateFormat formatoCompletoUSA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat formatoCompletoBr = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            %>
            <ul style="margin: 10px; list-style-type:none; list-style-position:outside; "><li class="li_titulo"><h1>Calendário Estral</h1></li><li class="li_ultimo"></li></ul>
<div id="example" style="margin-left: 10px; width:70%;">
    <!--<div class="shadow" style="border: 1px solid #aaaaaa; padding: 3px;"></div>-->
    <div id="toolbar" class="ui-widget-header ui-corner-all" style="padding:3px; vertical-align: middle; white-space:nowrap; overflow: hidden;">
        <ul>
            <li class="li_corpo">
                <button id="BtnPreviousMonth">Mês Anterior</button>
                <button id="BtnNextMonth">Próximo Mês</button>&nbsp;&nbsp;&nbsp;
                Data: <input type="text" id="dateSelect" style="width: 8em; height: 2.5em;" /> 
            </li>
        </ul>
    </div><br>	
    <div id="mycal"></div>            
</div>

<script type="text/javascript">
$(document).ready(function(){	
    var clickDate = "";
    var clickAgendaItem = "";

    var jfcalplugin = $("#mycal").jFrontierCal({ /* Inicializa o calendário com a data atual (real) */
        date: new Date(),
        dayClickCallback: myDayClickHandler,
        agendaClickCallback: myAgendaClickHandler,
       // agendaDropCallback: myAgendaDropHandler,
        agendaMouseoverCallback: myAgendaMouseoverHandler,
        applyAgendaTooltipCallback: myApplyTooltip,
      //  agendaDragStartCallback : myAgendaDragStart,
      //  agendaDragStopCallback : myAgendaDragStop,
        dragAndDropEnabled: true
    }).data("plugin");

    jfcalplugin.setAspectRatio("#mycal",0.75); /* proporção de altura da parte editável do dia */

    function myDayClickHandler(eventObj){ /* Chamado quando o usuário clica em um dia para adicionar um evento */
        var date = eventObj.data.calDayDate; // captura a data clicada
        // atribuir as datas dos eventos de cio à date
        //clickDate = date.getFullYear() + "-" + (date.getMonth()+1) + "-" + date.getDate(); // store date in our global js variable for access later
        //$('#add-event-form').dialog('open'); // chama o formulário de adição de evento
        var dtini = "";
        var dtfim = "";
        var msg = "";
        var corFundo = "";
        var corTexto = "";
        var hrini = "";;
        var hrfim = "";;
        
        <%  for(Cio c : ciosReg) {
                if(c.getStatus().equals("CONFIRMADO") || c.getStatus().equals("ENCERRADO")){
                    if(c.getStatus().equals("CONFIRMADO")){
                    %>
                        dtini = "<%= c.getData_inicio() %>";
                        dtfim = "<%= c.getData_previsao_termino() %>";
                        var hrini = "<%= c.getHora_inicio() %>";
                        var hrfim = "<%= c.getHora_previsao_termino()%>";
                        msg = "Animal: <%= c.getAnimal().getCodigo()%> - <%= c.getAnimal().getApelido() %><br>CIO CONFIRMADO.";
                        corFundo = "#4F5867";
                        corTexto = "#2D291E";
                    <%        
                    } else if (c.getStatus().equals("ENCERRADO")){
                    %>
                        dtini = "<%= c.getData_inicio() %>";
                        dtfim = "<%= c.getData_termino() %>";
                        var hrini = "<%= c.getHora_inicio() %>";
                        var hrfim = "<%= c.getHora_termino()%>";
                        msg = "Animal: <%= c.getAnimal().getCodigo()%> - <%= c.getAnimal().getApelido() %><br>CIO ENCERRADO.";
                        corFundo = "#BBAF4B";//"#022729"
                        corTexto = "#022729";
                    <%        
                    } 
                    %>

                    var what = msg; //jQuery.trim($("#what").val());
                    var startDate = dtini;//$("#startDate").val();
                    var startDtArray = startDate.split("/");
                    var startYear = startDtArray[2];		
                    var startMonth = startDtArray[1];// jquery datepicker months start at 1 (1=January)		
                    var startDay = startDtArray[0];		
                    startMonth = startMonth.replace(/^[0]+/g,"");// strip any preceeding 0's
                    startDay = startDay.replace(/^[0]+/g,"");
                    var startHrArray = hrini.split(":");
                    var startHour = startHrArray[0];//jQuery.trim($("#startHour").val());
                    var startMin = startHrArray[1];//jQuery.trim($("#startMin").val());
                    if(startHour > 12){
                        var startMeridiem = "AM";//jQuery.trim($("#startMeridiem").val());
                    } else {
                        var startMeridiem = "PM";//jQuery.trim($("#startMeridiem").val());
                    }
                    
                    startHour = parseInt(startHour.replace(/^[0]+/g,""));
                    if(startMin == "0" || startMin == "00"){
                        startMin = 0;
                    }else{
                        startMin = parseInt(startMin.replace(/^[0]+/g,""));
                    }
                    if(startMeridiem == "AM" && startHour == 12){
                        startHour = 0;
                    }else if(startMeridiem == "PM" && startHour < 12){
                        startHour = parseInt(startHour) + 12;
                    }

                    var endDate = dtfim;//$("#endDate").val();
                    var endDtArray = endDate.split("/");
                    var endYear = endDtArray[2];		
                    var endMonth = endDtArray[1]; // jquery date    picker months start at 1 (1=January)		
                    var endDay = endDtArray[0];		
                    endMonth = endMonth.replace(/^[0]+/g,""); // strip any preceeding 0's
                    endDay = endDay.replace(/^[0]+/g,"");
                    var endHrArray = hrfim.split(":");
                    var endHour = endHrArray[0];//jQuery.trim($("#endHour").val());
                    var endMin = endHrArray[1];//jQuery.trim($("#endMin").val());
                    if(endHour > 12){
                        var endMeridiem = "AM";//jQuery.trim($("#startMeridiem").val());
                    } else {
                        var endMeridiem = "PM";//jQuery.trim($("#startMeridiem").val());
                    }
                    endHour = parseInt(endHour.replace(/^[0]+/g,""));
                    if(endMin == "0" || endMin == "00"){
                        endMin = 0;
                    }else{
                        endMin = parseInt(endMin.replace(/^[0]+/g,""));
                    }
                    if(endMeridiem == "AM" && endHour == 12){
                            endHour = 0;
                    }else if(endMeridiem == "PM" && endHour < 12){
                            endHour = parseInt(endHour) + 12;
                    }
                    //alert("Start time: " + startHour + ":" + startMin + " " + startMeridiem + ", End time: " + endHour + ":" + endMin + " " + endMeridiem);

                    // Dates use integers
                    var startDateObj = new Date(parseInt(startYear),parseInt(startMonth)-1,parseInt(startDay),startHour,startMin,0,0);
                    var endDateObj = new Date(parseInt(endYear),parseInt(endMonth)-1,parseInt(endDay),endHour,endMin,0,0);

                    // add new event to the calendar
                    jfcalplugin.addAgendaItem(
                        "#mycal",
                        what,
                        startDateObj,
                        endDateObj,
                        false,
                        {
                          //  fname: "Santa",
                          //  lname: "Claus",
                          //  leadReindeer: "Rudolph",
                          //  myDate: new Date(),
                           // myNum: 42
                        },
                        {
                            backgroundColor: corFundo,//$("#colorBackground").val(),
                            foregroundColor: corTexto//$("#colorForeground").val()
                        }
                    );
        <%      } 
            } 
        %>
                    
        <%  
            GregorianCalendar dthPrevTerminoCio = new GregorianCalendar();
            for(AnimalInfo ai : listaAnimaisInfo) {
                dthPrevTerminoCio.setTime(formatoCompletoBr.parse(ai.getDt_prev_ini_prox_cio()+ " " + ai.getHr_prev_ini_prox_cio()));
                dthPrevTerminoCio.add(Calendar.HOUR, ai.getDuracao_media_cio());
                formatoDataBr.setCalendar(dthPrevTerminoCio);
        %>
                    dtini = "<%= ai.getDt_prev_ini_prox_cio() %>";
                    dtfim = "<%= formatoDataBr.format(dthPrevTerminoCio.getTime()) %>";
                    var hrini = "<%= ai.getHr_prev_ini_prox_cio() %>";
                    var hrfim = "<%= formatoHoraBr.format(dthPrevTerminoCio.getTime()) %>";
                    msg = "Animal: <%= ai.getAnimal().getCodigo()%> - <%= ai.getAnimal().getApelido() %>.<br>PREVISÃO DE CIO. ";
                    corFundo = "#AA160E";
                    corTexto = "#fffff0";

                    var what = msg; //jQuery.trim($("#what").val());
                    var startDate = dtini;//$("#startDate").val();
                    var startDtArray = startDate.split("/");
                    var startYear = startDtArray[2];		
                    var startMonth = startDtArray[1];// jquery datepicker months start at 1 (1=January)		
                    var startDay = startDtArray[0];		
                    startMonth = startMonth.replace(/^[0]+/g,"");// strip any preceeding 0's
                    startDay = startDay.replace(/^[0]+/g,"");
                    var startHrArray = hrini.split(":");
                    var startHour = startHrArray[0];//jQuery.trim($("#startHour").val());
                    var startMin = startHrArray[1];//jQuery.trim($("#startMin").val());
                    if(startHour > 12){
                        var startMeridiem = "AM";//jQuery.trim($("#startMeridiem").val());
                    } else {
                        var startMeridiem = "PM";//jQuery.trim($("#startMeridiem").val());
                    }
                    
                    startHour = parseInt(startHour.replace(/^[0]+/g,""));
                    if(startMin == "0" || startMin == "00"){
                        startMin = 0;
                    }else{
                        startMin = parseInt(startMin.replace(/^[0]+/g,""));
                    }
                    if(startMeridiem == "AM" && startHour == 12){
                        startHour = 0;
                    }else if(startMeridiem == "PM" && startHour < 12){
                        startHour = parseInt(startHour) + 12;
                    }

                    var endDate = dtfim;//$("#endDate").val();
                    var endDtArray = endDate.split("/");
                    var endYear = endDtArray[2];		
                    var endMonth = endDtArray[1]; // jquery date    picker months start at 1 (1=January)		
                    var endDay = endDtArray[0];		
                    endMonth = endMonth.replace(/^[0]+/g,""); // strip any preceeding 0's
                    endDay = endDay.replace(/^[0]+/g,"");
                    var endHrArray = hrfim.split(":");
                    var endHour = endHrArray[0];//jQuery.trim($("#endHour").val());
                    var endMin = endHrArray[1];//jQuery.trim($("#endMin").val());
                    if(endHour > 12){
                        var endMeridiem = "AM";//jQuery.trim($("#startMeridiem").val());
                    } else {
                        var endMeridiem = "PM";//jQuery.trim($("#startMeridiem").val());
                    }
                    endHour = parseInt(endHour.replace(/^[0]+/g,""));
                    if(endMin == "0" || endMin == "00"){
                        endMin = 0;
                    }else{
                        endMin = parseInt(endMin.replace(/^[0]+/g,""));
                    }
                    if(endMeridiem == "AM" && endHour == 12){
                            endHour = 0;
                    }else if(endMeridiem == "PM" && endHour < 12){
                            endHour = parseInt(endHour) + 12;
                    }
                    //alert("Start time: " + startHour + ":" + startMin + " " + startMeridiem + ", End time: " + endHour + ":" + endMin + " " + endMeridiem);
                    // Dates use integers
                    var startDateObj = new Date(parseInt(startYear),parseInt(startMonth)-1,parseInt(startDay),startHour,startMin,0,0);
                    var endDateObj = new Date(parseInt(endYear),parseInt(endMonth)-1,parseInt(endDay),endHour,endMin,0,0);

                    // add new event to the calendar
                    jfcalplugin.addAgendaItem(
                        "#mycal",
                        what,
                        startDateObj,
                        endDateObj,
                        false,
                        {
                          //  fname: "Santa",
                          //  lname: "Claus",
                          //  leadReindeer: "Rudolph",
                          //  myDate: new Date(),
                           // myNum: 42
                        },
                        {
                            backgroundColor: corFundo,//$("#colorBackground").val(),
                            foregroundColor: corTexto//$("#colorForeground").val()
                        }
                    );
        <%       
            } 
        %>
    };

    /** Do something when dragging starts on agenda div */
    function myAgendaDragStart(eventObj,divElm,agendaItem){
        if(divElm.data("qtip")){// destroy our qtip tooltip
            divElm.qtip("destroy");
        }	
    };

    /** * Do something when dragging stops on agenda div */
    function myAgendaDragStop(eventObj,divElm,agendaItem){
        //alert("drag stop"); 
    };

    /** Custom tooltip - use any tooltip library you want to display the agenda data. for this example we use qTip - http://craigsworks.com/projects/qtip/
     * @param divElm - jquery object for agenda div element @param agendaItem - javascript object containing agenda data. */
    function myApplyTooltip(divElm,agendaItem){
        if(divElm.data("qtip")){// Destroy currrent tooltip if present
            divElm.qtip("destroy");
        }
        var displayData = "";
        var title = agendaItem.title;
        var startDate = agendaItem.startDate;
        var endDate = agendaItem.endDate;
        var allDay = agendaItem.allDay;
        var data = agendaItem.data;
        displayData += "<br><b>" + title+ "</b><br><br>";
        if(allDay){
            displayData += "(All day event)<br><br>";
        }else{
            displayData += "<b>Início:</b> " + startDate + "<br>" + "<b>PrevFim/Fim:</b> " + endDate + "<br><br>";
        }
        for (var propertyName in data) {
            displayData += "<b>" + propertyName + ":</b> " + data[propertyName] + "<br>"
        }
        var backgroundColor = '#ffffff';//agendaItem.displayProp.backgroundColor; // use the user specified colors from the agenda item.
        var foregroundColor = '#022729';//agendaItem.displayProp.foregroundColor; // use the user specified colors from the agenda item.
        var myStyle = {
            border: {
                width: 2,
                //color: '#022729',
                radius: 5
            },
            padding: 10, 
            textAlign: "left",
            tip: true,
            name: "dark" // other style properties are inherited from dark theme		
        };
        if(backgroundColor != null && backgroundColor != ""){
            myStyle["backgroundColor"] = backgroundColor;
        }
        if(foregroundColor != null && foregroundColor != ""){
            myStyle["color"] = foregroundColor;
        }
        divElm.qtip({// apply tooltip
            content: displayData,
            position: {
                corner: {
                    tooltip: "bottomMiddle",
                    target: "topMiddle"			
                },
                adjust: { 
                    mouse: true,
                    x: 0,
                    y: -15
                },
                target: "mouse"
            },
            show: { 
                when: { 
                    event: 'mouseover'
                }
            },
            style: myStyle
        });
    };

    /** Called when user clicks and agenda item use reference to plugin object to edit agenda item */
    function myAgendaClickHandler(eventObj){
        var agendaId = eventObj.data.agendaId;	// Get ID of the agenda item from the event object	
        var agendaItem = jfcalplugin.getAgendaItemById("#mycal",agendaId); // pull agenda item from calendar
        clickAgendaItem = agendaItem;
        $("#display-event-form").dialog('open');
    };

    /** Called when user drops an agenda item into a day cell. */
    /*function myAgendaDropHandler(eventObj){
        var agendaId = eventObj.data.agendaId; // Get ID of the agenda item from the event object
        var date = eventObj.data.calDayDate; // date agenda item was dropped onto
        var agendaItem = jfcalplugin.getAgendaItemById("#mycal",agendaId); // Pull agenda item from calendar		
        alert("You dropped agenda item " + agendaItem.title + " onto " + date.toString() + ". Here is where you can make an AJAX call to update your database.");
    };*/

    /**Called when a user mouses over an agenda item*/
    function myAgendaMouseoverHandler(eventObj){
        var agendaId = eventObj.data.agendaId;
        var agendaItem = jfcalplugin.getAgendaItemById("#mycal",agendaId);
        //alert("You moused over agenda item " + agendaItem.title + " at location (X=" + eventObj.pageX + ", Y=" + eventObj.pageY + ")");
    };

    $("#dateSelect").datepicker({/** Initialize jquery ui datepicker. set date format to dd/mm/yyyy for easy parsing */
        showOtherMonths: true,
        selectOtherMonths: true,
        changeMonth: true,
        changeYear: true,
        showButtonPanel: true,
        dateFormat: 'dd/mm/yy'
    });

    $("#dateSelect").datepicker('setDate', new Date()); /** Set datepicker to current date */

    $("#dateSelect").bind('change', function() { /** * Use reference to plugin object to a specific year/month */
        var selectedDate = $("#dateSelect").val();
        var dtArray = selectedDate.split("/");
        var year = dtArray[2];		
        var month = dtArray[1]; // jquery datepicker months start at 1 (1=January)
        month = month.replace(/^[0]+/g,"") // strip any preceeding 0's		
        var day = dtArray[0];
        jfcalplugin.showMonth("#mycal",year,parseInt(month-1).toString()); // plugin uses 0-based months so we subtrac 1
    });	

    $("#BtnPreviousMonth").button(); /** Initialize previous month button */
    $("#BtnPreviousMonth").click(function() {
        jfcalplugin.showPreviousMonth("#mycal");
        var calDate = jfcalplugin.getCurrentDate("#mycal"); // update the jqeury datepicker value , returns Date object
        var cyear = calDate.getFullYear();
        var cmonth = calDate.getMonth(); // Date month 0-based (0=January)
        var cday = calDate.getDate();
        $("#dateSelect").datepicker("setDate",cday+"/"+(cmonth+1)+"/"+cyear); // jquery datepicker month starts at 1 (1=January) so we add 1
        return false;
    });

    $("#BtnNextMonth").button(); /** Initialize next month button */
    $("#BtnNextMonth").click(function() {
        jfcalplugin.showNextMonth("#mycal");
        var calDate = jfcalplugin.getCurrentDate("#mycal"); // update the jqeury datepicker value, returns Date object
        var cyear = calDate.getFullYear();
        var cmonth = calDate.getMonth(); // Date month 0-based (0=January)
        var cday = calDate.getDate();
        $("#dateSelect").datepicker("setDate",cday+"/"+(cmonth+1)+"/"+cyear); // jquery datepicker month starts at 1 (1=January) so we add 1		
        return false;
    });

    $("#BtnDeleteAll").button(); /** Initialize delete all agenda items button */
    $("#BtnDeleteAll").click(function() {	
        jfcalplugin.deleteAllAgendaItems("#mycal");	
        return false;
    });		

    $("#BtnICalTest").button(); /** Initialize iCal test button */
    $("#BtnICalTest").click(function() {// Please note that in Google Chrome this will not work with a local file. Chrome prevents AJAX calls from reading local files on disk.		
        jfcalplugin.loadICalSource("#mycal",$("#iCalSource").val(),"html");	
        return false;
    });	
    /*
    $("#add-event-form").dialog({/** Initialize add event modal form */
    /*    autoOpen: false,
        height: 400,
        width: 400,
        modal: true,
        buttons: {
            'Add Event': function() {
                var what = jQuery.trim($("#what").val());
                if(what == ""){
                    alert("Please enter a short event description into the \"what\" field.");
                }else{
                    
                    var startDate = $("#startDate").val();
                    var startDtArray = startDate.split("-");
                    var startYear = startDtArray[0];		
                    var startMonth = startDtArray[1];// jquery datepicker months start at 1 (1=January)		
                    var startDay = startDtArray[2];		
                    startMonth = startMonth.replace(/^[0]+/g,"");// strip any preceeding 0's
                    startDay = startDay.replace(/^[0]+/g,"");
                    var startHour = jQuery.trim($("#startHour").val());
                    var startMin = jQuery.trim($("#startMin").val());
                    var startMeridiem = jQuery.trim($("#startMeridiem").val());
                    startHour = parseInt(startHour.replace(/^[0]+/g,""));
                    if(startMin == "0" || startMin == "00"){
                        startMin = 0;
                    }else{
                        startMin = parseInt(startMin.replace(/^[0]+/g,""));
                    }
                    if(startMeridiem == "AM" && startHour == 12){
                        startHour = 0;
                    }else if(startMeridiem == "PM" && startHour < 12){
                        startHour = parseInt(startHour) + 12;
                    }

                    var endDate = $("#endDate").val();
                    var endDtArray = endDate.split("-");
                    var endYear = endDtArray[0];		
                    var endMonth = endDtArray[1]; // jquery datepicker months start at 1 (1=January)		
                    var endDay = endDtArray[2];		
                    endMonth = endMonth.replace(/^[0]+/g,""); // strip any preceeding 0's
                    endDay = endDay.replace(/^[0]+/g,"");
                    var endHour = jQuery.trim($("#endHour").val());
                    var endMin = jQuery.trim($("#endMin").val());
                    var endMeridiem = jQuery.trim($("#endMeridiem").val());
                    endHour = parseInt(endHour.replace(/^[0]+/g,""));
                    if(endMin == "0" || endMin == "00"){
                        endMin = 0;
                    }else{
                        endMin = parseInt(endMin.replace(/^[0]+/g,""));
                    }
                    if(endMeridiem == "AM" && endHour == 12){
                            endHour = 0;
                    }else if(endMeridiem == "PM" && endHour < 12){
                            endHour = parseInt(endHour) + 12;
                    }
                    //alert("Start time: " + startHour + ":" + startMin + " " + startMeridiem + ", End time: " + endHour + ":" + endMin + " " + endMeridiem);

                    // Dates use integers
                    var startDateObj = new Date(parseInt(startYear),parseInt(startMonth)-1,parseInt(startDay),startHour,startMin,0,0);
                    var endDateObj = new Date(parseInt(endYear),parseInt(endMonth)-1,parseInt(endDay),endHour,endMin,0,0);

                    // add new event to the calendar
                    jfcalplugin.addAgendaItem(
                        "#mycal",
                        what,
                        startDateObj,
                        endDateObj,
                        false,
                        {
                            fname: "Santa",
                            lname: "Claus",
                            leadReindeer: "Rudolph",
                            myDate: new Date(),
                            myNum: 42
                        },
                        {
                            backgroundColor: $("#colorBackground").val(),
                            foregroundColor: $("#colorForeground").val()
                        }
                    );
                    $(this).dialog('close');
                }
            },
            Cancel: function() {
                $(this).dialog('close');
            }
        },
        open: function(event, ui){
            // initialize start date picker
            $("#startDate").datepicker({
                showOtherMonths: true,
                selectOtherMonths: true,
                changeMonth: true,
                changeYear: true,
                showButtonPanel: true,
                dateFormat: 'yy-mm-dd'
            });
            $("#endDate").datepicker({// initialize end date picker
                showOtherMonths: true,
                selectOtherMonths: true,
                changeMonth: true,
                changeYear: true,
                showButtonPanel: true,
                dateFormat: 'yy-mm-dd'
            });
            $("#startDate").val(clickDate);// initialize with the date that was clicked
            $("#endDate").val(clickDate);
            $("#colorSelectorBackground").ColorPicker({// initialize color pickers
                color: "#333333",
                onShow: function (colpkr) {
                    $(colpkr).css("z-index","10000");
                    $(colpkr).fadeIn(500);
                    return false;
                },
                onHide: function (colpkr) {
                    $(colpkr).fadeOut(500);
                    return false;
                },
                onChange: function (hsb, hex, rgb) {
                    $("#colorSelectorBackground div").css("backgroundColor", "#" + hex);
                    $("#colorBackground").val("#" + hex);
                }
            });
            //$("#colorBackground").val("#1040b0");		
            $("#colorSelectorForeground").ColorPicker({
                color: "#ffffff",
                onShow: function (colpkr) {
                    $(colpkr).css("z-index","10000");
                    $(colpkr).fadeIn(500);
                    return false;
                },
                onHide: function (colpkr) {
                    $(colpkr).fadeOut(500);
                    return false;
                },
                onChange: function (hsb, hex, rgb) {
                    $("#colorSelectorForeground div").css("backgroundColor", "#" + hex);
                    $("#colorForeground").val("#" + hex);
                }
            });
            //$("#colorForeground").val("#ffffff");				
            // put focus on first form input element
            $("#what").focus();
        },
        close: function() {
            // reset form elements when we close so they are fresh when the dialog is opened again.
            $("#startDate").datepicker("destroy");
            $("#endDate").datepicker("destroy");
            $("#startDate").val("");
            $("#endDate").val("");
            $("#startHour option:eq(0)").attr("selected", "selected");
            $("#startMin option:eq(0)").attr("selected", "selected");
            $("#startMeridiem option:eq(0)").attr("selected", "selected");
            $("#endHour option:eq(0)").attr("selected", "selected");
            $("#endMin option:eq(0)").attr("selected", "selected");
            $("#endMeridiem option:eq(0)").attr("selected", "selected");			
            $("#what").val("");
            //$("#colorBackground").val("#1040b0");
            //$("#colorForeground").val("#ffffff");
        }
    });*/

    $("#display-event-form").dialog({/** Initialize display event form. */
        autoOpen: false,
        height: 300,
        width: 400,
        modal: true,
        /*buttons: {		
            /*Cancel: function() {
                $(this).dialog('close');
            },
            'Edit': function() {
                alert("Make your own edit screen or dialog!");
            },
            'Delete': function() {
                if(confirm("Are you sure you want to delete this agenda item?")){
                    if(clickAgendaItem != null){
                        jfcalplugin.deleteAgendaItemById("#mycal",clickAgendaItem.agendaId);
                        //jfcalplugin.deleteAgendaItemByDataAttr("#mycal","myNum",42);
                    }
                    $(this).dialog('close');
                }
            }			
        },*/
        open: function(event, ui){
            if(clickAgendaItem != null){
                var title = clickAgendaItem.title;
                var startDate = clickAgendaItem.startDate;
                var endDate = clickAgendaItem.endDate;
                var allDay = clickAgendaItem.allDay;
                var data = clickAgendaItem.data;
                // in our example add agenda modal form we put some fake data in the agenda data. we can retrieve it here.
                $("#display-event-form").append("<br><b>" + title+ "</b><br><br>");				
                if(allDay){
                    $("#display-event-form").append("(All day event)<br><br>");				
                }else{
                    $("#display-event-form").append("<b>Inicio:</b> " + startDate + "<br>" + "<b>PrevFim/Fim:</b> " + endDate + "<br><br>");				
                }
                for (var propertyName in data) {
                    $("#display-event-form").append("<b>" + propertyName + ":</b> " + data[propertyName] + "<br>");
                }			
            }		
        },
        close: function() {
            // clear agenda data
            $("#display-event-form").html("");
        }
    });	 

    $("#tabs").tabs({/** Initialize our tabs */
        /* Our calendar is initialized in a closed tab so we need to resize it when the example tab opens. */
        show: function(event, ui){
            if(ui.index == 1){
                    jfcalplugin.doResize("#mycal");
            }
        }	
    });	
});
</script>

<div id="calDebug"></div>
<!--<div id="add-event-form" title="Add New Event">
    <p class="validateTips">All form fields are required.</p>
    <form>
        <fieldset>
            <label for="name">What?</label>
            <input type="text" name="what" id="what" class="text ui-widget-content ui-corner-all" style="margin-bottom:12px; width:95%; padding: .4em;"/>
            <table style="width:100%; padding:5px;">
                <tr>
                    <td>
                        <label>Start Date</label>
                        <input type="text" name="startDate" id="startDate" value="" class="text ui-widget-content ui-corner-all" style="margin-bottom:12px; width:95%; padding: .4em;"/>				
                    </td>
                    <td>&nbsp;</td>
                    <td>
                        <label>Start Hour</label>
                        <select id="startHour" class="text ui-widget-content ui-corner-all" style="margin-bottom:12px; width:95%; padding: .4em;">
                            <option value="12" SELECTED>12</option><option value="1">1</option><option value="2">2</option><option value="3">3</option>
                            <option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option>
                            <option value="8">8</option><option value="9">9</option><option value="10">10</option><option value="11">11</option>
                        </select>				
                    <td>
                    <td>
                        <label>Start Minute</label>
                        <select id="startMin" class="text ui-widget-content ui-corner-all" style="margin-bottom:12px; width:95%; padding: .4em;">
                            <option value="00" SELECTED>00</option><option value="10">10</option><option value="20">20</option><option value="30">30</option>
                            <option value="40">40</option><option value="50">50</option>
                        </select>				
                    <td>
                        <td>
                            <label>Start AM/PM</label>
                            <select id="startMeridiem" class="text ui-widget-content ui-corner-all" style="margin-bottom:12px; width:95%; padding: .4em;">
                                <option value="AM" SELECTED>AM</option><option value="PM">PM</option>
                            </select>				
                        </td>
                </tr>
                <tr>
                    <td>
                            <label>End Date</label>
                            <input type="text" name="endDate" id="endDate" value="" class="text ui-widget-content ui-corner-all" style="margin-bottom:12px; width:95%; padding: .4em;"/>				
                    </td>
                    <td>&nbsp;</td>
                    <td>
                        <label>End Hour</label>
                        <select id="endHour" class="text ui-widget-content ui-corner-all" style="margin-bottom:12px; width:95%; padding: .4em;">
                            <option value="12" SELECTED>12</option><option value="1">1</option><option value="2">2</option><option value="3">3</option>
                            <option value="4">4</option><option value="5">5</option><option value="6">6</option><option value="7">7</option><option value="8">8</option>
                            <option value="9">9</option><option value="10">10</option><option value="11">11</option>
                        </select>				
                    <td>
                    <td>
                        <label>End Minute</label>
                        <select id="endMin" class="text ui-widget-content ui-corner-all" style="margin-bottom:12px; width:95%; padding: .4em;">
                            <option value="00" SELECTED>00</option><option value="10">10</option><option value="20">20</option><option value="30">30</option>
                            <option value="40">40</option><option value="50">50</option>
                        </select>				
                    <td>
                    <td>
                        <label>End AM/PM</label>
                        <select id="endMeridiem" class="text ui-widget-content ui-corner-all" style="margin-bottom:12px; width:95%; padding: .4em;">
                            <option value="AM" SELECTED>AM</option><option value="PM">PM</option>
                        </select>				
                    </td>				
                </tr>			
            </table>
            <table>
                <tr>
                    <td><label>Background Color</label></td>
                    <td>
                        <div id="colorSelectorBackground"><div style="background-color: #333333; width:30px; height:30px; border: 2px solid #000000;"></div></div>
                        <input type="hidden" id="colorBackground" value="#333333">
                    </td>
                    <td>&nbsp;&nbsp;&nbsp;</td>
                    <td><label>Text Color</label></td>
                    <td>
                        <div id="colorSelectorForeground"><div style="background-color: #ffffff; width:30px; height:30px; border: 2px solid #000000;"></div></div>
                        <input type="hidden" id="colorForeground" value="#ffffff">
                    </td>						
                </tr>				
            </table>
        </fieldset>
    </form>
</div>-->
<div id="display-event-form" title="Detalhes da Ocorrência"></div>		
<p>&nbsp;</p>
    
<style type="text/css">
    fieldset { padding:0; border:0; margin-top:25px; }
    .ui-dialog .ui-state-error { padding: .3em; }
    .validateTips { border: 1px solid transparent; padding: 0.3em; }
</style>