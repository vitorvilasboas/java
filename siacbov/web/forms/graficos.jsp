<%-- 
    Document   : graficos
    Author     : tati
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">       
        <link href="css/estilo.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="css/jqchart/jquery.jqChart.css" />
        <link rel="stylesheet" type="text/css" href="css/jqchart/jquery.jqRangeSlider.css" />
        <link rel="stylesheet" type="text/css" href="css/jqchart/themes/smoothness/jquery-ui-1.10.4.css" />
        <title>Sistema Cio</title>
        <!--<script type="text/javascript" src="js/canvasjs/canvasjs.min.js"></script>-->
        <script type="text/javascript" src="js/canvasjs/jquery.canvasjs.min.js"></script>
        <script src="js/jqchart/jquery-1.11.1.min.js" type="text/javascript"></script>
        <script src="js/jqchart/jquery.jqGauges.min.js" type="text/javascript"></script>
        <script src="js/jqchart/jquery.mousewheel.js" type="text/javascript"></script>
        <script src="js/jqchart/jquery.jqChart.min.js" type="text/javascript"></script>
        <script src="js/jqchart/jquery.jqRangeSlider.min.js" type="text/javascript"></script>
    </head>
    <body>
        <h1>Gráfico monitoramento de passadas por animal</h1>
        <div id="chartContainer" style="height: 300px; width: 100%;"> </div>
    </body>
</html>
<script lang="javascript" type="text/javascript">
    
    window.onload = function () {
        var chart = new CanvasJS.Chart("chartContainer",
        {
          theme: "theme2",
          title:{
            text: "Passadas - "
          },
          axisX: {
            valueFormatString: "MMM",
            interval:1,
            intervalType: "month"
          },
          axisY:{
            includeZero: false
          },
          data: [
          {        
            type: "line",
            //lineThickness: 3,        
            dataPoints: [
                { x: new Date(2014, 00, 1), y: 450 },
                { x: new Date(2014, 01, 1), y: 414},
                { x: new Date(2014, 02, 1), y: 520, indexLabel: "Atenção/Cio",markerColor: "red", markerType: "triangle"},
                { x: new Date(2014, 03, 1), y: 460 },
                { x: new Date(2014, 04, 1), y: 450 },
                { x: new Date(2014, 05, 1), y: 500 },
                { x: new Date(2014, 06, 1), y: 480 },
                { x: new Date(2014, 07, 1), y: 480 },
                { x: new Date(2014, 08, 1), y: 410 , indexLabel: "Menor",markerColor: "DarkSlateGrey", markerType: "cross"},
                { x: new Date(2014, 09, 1), y: 500 },
                { x: new Date(2014, 10, 1), y: 480 },
                { x: new Date(2014, 11, 1), y: 510 }
            ]
          }
          ]
        });
        chart.render();
    }
</script> 